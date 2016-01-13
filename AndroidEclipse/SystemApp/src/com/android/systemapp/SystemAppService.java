package com.android.systemapp;

import com.android.systemapp.paramsettings.ParamSettings;
import com.android.systemapp.paramsettings.ParamSettingsUtils;
import com.android.systemapp.paramsettings.model.Cpu;
import com.android.systemapp.paramsettings.model.Memory;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.TextUtils;

public class SystemAppService extends Service {

	private static final String ACTION_EXTRA = "action";

	private static final boolean mEnabledCustomParamterSetting = true;
	
	private BootCompletedAsyncTask mBootCompletedAsyncTask;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(this, "onCreate()...");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(this, "onStartCommand=>intent: " + intent + " flags: " + flags + " startId: " + startId);
		if (intent != null) {
			String action = intent.getStringExtra(ACTION_EXTRA);
			Log.d(this, "onStartCommand=>action: " + action);
			if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
				doOnBootCompleted();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(this, "onDestroy()...");
	}
	
	private void doOnBootCompleted() {
		Log.d(this, "doOnBootCompleted()...");
		if (mBootCompletedAsyncTask != null) {
			if (mBootCompletedAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
				mBootCompletedAsyncTask.cancel(true);
			}
			mBootCompletedAsyncTask = null;
		}
		mBootCompletedAsyncTask = new BootCompletedAsyncTask();
		mBootCompletedAsyncTask.execute(new Void[]{});
	}

	private void doCustomParamerSettingOnBootCompleted() {
		updateParamFromNV();
		ParamSettings settings = new ParamSettings(this);
		Cpu cpu = settings.getCpuParam();
		Memory memory = settings.getMemoryParam();
		Log.d(this, "doCustomParamerSettingOnBootCompleted=>cpu: " + cpu + " memory: " + memory);
		ParamSettingsUtils.execCommand(
				"echo " + cpu.core + " " + cpu.maxFreq + " " + cpu.minFreq + " > /sys/devices/system/cpu/kernel_max");
		ParamSettingsUtils.execCommand("echo " + ParamSettingsUtils.getStorageMBString(memory.maxRam) + " "
				+ ParamSettingsUtils.getStorageMBString(memory.minRam) + " > /proc/meminfo");
	}

	private void updateParamFromNV() {
		ParamSettings settings = new ParamSettings(this);
		boolean enabledUpdate = false;
		boolean isFirstStart = android.os.SystemProperties.getBoolean("persist.sys.tt.first_start", true);

		settings.printNvValue();

		Cpu nvCpu = settings.getCpuParamFromNV();
		if ((!TextUtils.isEmpty(nvCpu.core) && TextUtils.isDigitsOnly(nvCpu.core))
				&& (!TextUtils.isEmpty(nvCpu.minFreq) && TextUtils.isDigitsOnly(nvCpu.minFreq))
				&& (!TextUtils.isEmpty(nvCpu.maxFreq) && TextUtils.isDigitsOnly(nvCpu.maxFreq))) {
			enabledUpdate = true;
		}

		if (isFirstStart) {
			android.os.SystemProperties.set("persist.sys.tt.first_start", "false");
		}
		Log.d(this,
				"updateParamFromNV=>isFirstStart: " + isFirstStart + " enabled: " + enabledUpdate + " cpu: " + nvCpu);

		if (enabledUpdate) {
			if (isFirstStart) {
				settings.setCpuParam(settings.getCpuParamFromNV());
				settings.setMemoryParam(settings.getMemoryParamFromNV());
				settings.setStoargeParam(settings.getStorageParamFromNV());
				settings.setLcdParam(settings.getLcdParamFromNV());
				settings.setCameraParam(settings.getCameraParamFromNV());
				settings.setOtherParam(settings.getOtherParamFromNV());
			}
		} else {
			settings.writeCpuParamToNV(settings.getCpuParam());
			settings.writeMemoryParamToNV(settings.getMemoryParam());
			settings.writeStorageParamToNV(settings.getStorageParam());
			settings.writeLcdParamToNV(settings.getLcdParam());
			settings.writeCameraParamToNV(settings.getCameraParam());
			settings.writeOtherParamToNV(settings.getOtherParam());
		}
	}
	
	private class BootCompletedAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Log.d(this, "doInBackground()...");
			if (mEnabledCustomParamterSetting) {
				doCustomParamerSettingOnBootCompleted();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.d(this, "onPostExecute()...");
		}
	}
}
