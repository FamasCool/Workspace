package com.android.agingtest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public class AgingTestActivity extends Activity implements OnClickListener {
	
	private ToggleButton mVibrationBtn;
	private ToggleButton mSpeakerBtn;
	private ToggleButton mEarpieceBtn;
	private IAgingService mService;
	
	private boolean mServiceConnect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aging_test);
		mServiceConnect = false;
		initViews();
		bindService();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateViewsStatu();
	}
	
	@Override
	protected void onDestroy() {
		unbindService(mServiceConnection);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.aging_test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_connection) {
			if (!mServiceConnect || mService == null) {
				bindService();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		Log.d(this, "onCheckedChanged=>id: " + v.getId());
		switch (v.getId()) {
		case R.id.vibration_test:
			Log.d(this, "onCheckedChanged=>vibration: " + mVibrationBtn.isChecked());
			if (mService != null) {
				try {
					mService.setVibrationEnabled(mVibrationBtn.isChecked());
				} catch (RemoteException e) {
					Log.e(this, "onCheckedChanged=>vibration error: ", e);
				}
			}
			break;
			
		case R.id.speaker_test:
			Log.d(this, "onCheckedChanged=>speaker: " + mSpeakerBtn.isChecked());
			if (mService != null) {
				try {
					mService.setSpeakerTestEnabled(mSpeakerBtn.isChecked());
				} catch (RemoteException e) {
					Log.e(this, "onCheckedChanged=>speaker error: ", e);
				}
			}
			break;
			
		case R.id.earpiece_test:
			Log.d(this, "onCheckedChanged=>earpiece: " + mEarpieceBtn.isChecked());
			if (mService != null) {
				try {
					mService.setEarpieceTestEnabled(mEarpieceBtn.isChecked());
				} catch (RemoteException e) {
					Log.e(this, "onCheckedChanged=>earpicec error: ", e);
				}
			}
			break;
		}
	}
	
	private void initViews() {
		mVibrationBtn = (ToggleButton) findViewById(R.id.vibration_test);
		mSpeakerBtn = (ToggleButton) findViewById(R.id.speaker_test);
		mEarpieceBtn = (ToggleButton) findViewById(R.id.earpiece_test);
		
		mVibrationBtn.setOnClickListener(this);
		mSpeakerBtn.setOnClickListener(this);
		mEarpieceBtn.setOnClickListener(this);
		
		setViewsEnabled(false);
	}
	
	private void setViewsEnabled(boolean enabled) {
		mVibrationBtn.setEnabled(enabled);
		mSpeakerBtn.setEnabled(enabled);
		mEarpieceBtn.setEnabled(enabled);
	}
	
	private void updateViewsStatu() {
		Log.d(this, "updateViewsStatu=>service: " + mService);
		if (mService != null) {
			try {
				mVibrationBtn.setChecked(mService.isVibrationTestOn());
				mSpeakerBtn.setChecked(mService.isSpeakerTestOn());
				mEarpieceBtn.setChecked(mService.isEarpieceTestOn());
			} catch (RemoteException e) {
				Log.e(this, "updateViewsStatu=>error: ", e);
				mVibrationBtn.setChecked(false);
				mSpeakerBtn.setChecked(false);
				mEarpieceBtn.setChecked(false);
			}
		} else {
			mVibrationBtn.setChecked(false);
			mSpeakerBtn.setChecked(false);
			mEarpieceBtn.setChecked(false);
		}
	}
	
	private void bindService() {
		Intent service = new Intent(this, AgingService.class);
		bindService(service, mServiceConnection, Context.BIND_AUTO_CREATE);
	}
	
	private ServiceConnection mServiceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(this, "onServiceDisconnected()...");
			try {
				mService.removeStateChangedCallback(mStateChangedCallback);
			} catch (RemoteException e) {
				Log.e(this, "onServiceDisconnected=>error: ", e);
			}
			setViewsEnabled(false);
			mServiceConnect = false;
			mService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(this, "onServiceConnected()...");
			mService = IAgingService.Stub.asInterface(service);
			mServiceConnect = true;
			setViewsEnabled(true);
			updateViewsStatu();
			try {
				mService.addStateChangedCallback(mStateChangedCallback);
			} catch (RemoteException e) {
				Log.e(this, "onServiceConnected=>error: ", e);
			}
		}
	};

	private OnStateChangedCallback.Stub mStateChangedCallback = new OnStateChangedCallback.Stub() {

		@Override
		public void onStateChanged() throws RemoteException {
			updateViewsStatu();
		}
		
	};

}
