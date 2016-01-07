package com.android.agingtest;

import java.io.File;
import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.text.TextUtils;

public class AgingService extends Service {

	private static final String sDefaultMusicName = "Test.mp3";

	private Vibrator mVibrator;
	private MediaPlayer mMediaPlayer;
	private AudioManager mAudioManager;
	private StorageManager mStorageManager;
	private ArrayList<OnStateChangedCallback> mCallbacks;

	private boolean mVibrationTestOn;
	private boolean mSpeakerTestOn;
	private boolean mEarpieceTestOn;

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mStorageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
		mCallbacks = new ArrayList<OnStateChangedCallback>();

		mVibrationTestOn = false;
		mSpeakerTestOn = false;
		mEarpieceTestOn = false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopVibration();
		stopMusic();
		mCallbacks.clear();
		mAudioManager.setMode(AudioManager.MODE_NORMAL);
	}

	private final IAgingService.Stub mBinder = new IAgingService.Stub() {

		@Override
		public boolean isVibrationTestOn() throws RemoteException {
			return mVibrationTestOn;
		}

		@Override
		public boolean isSpeakerTestOn() throws RemoteException {
			return mSpeakerTestOn;
		}

		@Override
		public boolean isEarpieceTestOn() throws RemoteException {
			return mEarpieceTestOn;
		}

		@Override
		public void setVibrationEnabled(boolean enabled) throws RemoteException {
			Log.d(AgingService.this, "setVibrationEnabled=>enabled: " + enabled);
			if (enabled) {
				startVibration();
				mVibrationTestOn = true;
			} else {
				stopVibration();
				mVibrationTestOn = false;
			}
			notifyStateChanged();
		}

		@Override
		public void setSpeakerTestEnabled(boolean enabled) throws RemoteException {
			Log.d(AgingService.this, "setSpeakerTestEnabled=>enabled: " + enabled);
			stopMusic();
			resetMusicTestStatu();
			if (enabled) {
				mAudioManager.setSpeakerphoneOn(true);
				mAudioManager.setMicrophoneMute(false);
				mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
						mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
				mAudioManager.setMode(AudioManager.MODE_NORMAL);
				playMusic();
				mSpeakerTestOn = true;
			} else {
				mSpeakerTestOn = false;
			}
			notifyStateChanged();
		}

		@Override
		public void setEarpieceTestEnabled(boolean enabled) throws RemoteException {
			Log.d(AgingService.this, "setEarpieceTestEnabled=>enabled: " + enabled);
			stopMusic();
			resetMusicTestStatu();
			if (enabled) {
				mAudioManager.setSpeakerphoneOn(false);
				mAudioManager.setMicrophoneMute(false);
				mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
						mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), 0);
				mAudioManager.setMode(AudioManager.MODE_IN_CALL);
				playMusic();
				mEarpieceTestOn = true;
			} else {
				mEarpieceTestOn = false;
			}
			notifyStateChanged();
		}

		@Override
		public void addStateChangedCallback(final OnStateChangedCallback callback) throws RemoteException {
			Log.d(AgingService.this, "addStateChangedCallback=>callback: " + callback);
			mCallbacks.add(callback);
			callback.asBinder().linkToDeath(new DeathRecipient() {

				@Override
				public void binderDied() {
					mCallbacks.remove(callback);
				}
			}, 0);
		}

		@Override
		public void removeStateChangedCallback(OnStateChangedCallback callback) throws RemoteException {
			Log.d(AgingService.this, "removeStateChangedCallback=>callback: " + callback);
			mCallbacks.remove(callback);
		}

	};

	private void startVibration() {
		long[] vibrationTime = new long[] { 0, 1000, 0, 1000 };
		mVibrator.vibrate(vibrationTime, 0);
	}

	private void stopVibration() {
		mVibrator.cancel();
	}

	private void playMusic() {
		Log.d(this, "playMusic()...");
		try {
			String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
			String state = Environment.getExternalStorageState();
			Log.d(this, "playMusic=>external path: " + externalPath + " state: " + state);
			String path = "";
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				File musicFile = new File(externalPath + File.separator + sDefaultMusicName);
				if (musicFile != null && musicFile.exists()) {
					path = musicFile.getAbsolutePath();
				}
			}

			Log.d(this, "playMusic=>path: " + path);
			if (!TextUtils.isEmpty(path)) {
				mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setDataSource(path);
				mMediaPlayer.prepare();
			} else {
				mMediaPlayer = MediaPlayer.create(this, R.raw.test_music);
			}
			mMediaPlayer.setLooping(true);
			mMediaPlayer.setVolume(1.0f, 1.0f);
			mMediaPlayer.start();
		} catch (Exception e) {
			Log.e(this, "playMusic=>io error: ", e);
			resetMusicTestStatu();
			stopMusic();
		}
	}

	private void stopMusic() {
		Log.d(this, "stopMusic()...");
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void resetMusicTestStatu() {
		Log.d(this, "resetMusicTestStatu()");
		mSpeakerTestOn = false;
		mEarpieceTestOn = false;
	}

	private void notifyStateChanged() {
		Log.d(this, "notifyStateChanged=>size: " + mCallbacks.size());
		for (OnStateChangedCallback cb : mCallbacks) {
			try {
				cb.onStateChanged();
			} catch (RemoteException e) {
				Log.e(this, "notifyStateChanged=>error: ", e);
			}
		}
	}
}
