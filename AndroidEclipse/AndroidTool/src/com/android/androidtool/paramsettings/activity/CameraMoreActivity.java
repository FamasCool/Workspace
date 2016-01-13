package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.R;
import com.android.androidtool.Log;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.ParamSettingsUtils;
import com.android.androidtool.paramsettings.model.Camera;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CameraMoreActivity extends Activity implements OnClickListener {

	private EditText mFrontCameraPix;
	private EditText mBackCameraPix;
	private EditText mVideoFps;
	private Button mOk;
	private Button mCancel;
	private IParamSettings mParamSettings;
	private Camera mCamera;

	private int mFrontCameraMinLimit;
	private int mFrontCameraMaxLimit;
	private int mBackCameraMinLimit;
	private int mBackCameraMaxLimit;
	private int mVideoMinLimit;
	private int mVideoMaxLimit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.camera_title);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_camera_more);

		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_camera_settings, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.param_reset_factory:
			mParamSettings.resetCameraParamToFactory();
			updateViews();
			break;

		case android.R.id.home:
			setResult(RESULT_CANCELED);
			finish();
			break;
		}
		return true;
	}

	private void initViews() {
		mParamSettings = new ParamSettings(this);
		Resources res = getResources();
		mFrontCameraMinLimit = res
				.getInteger(R.integer.camera_front_pixel_min_limit);
		mFrontCameraMaxLimit = res
				.getInteger(R.integer.camera_front_pixel_max_limit);
		mBackCameraMinLimit = res
				.getInteger(R.integer.camera_back_pixel_min_limit);
		mBackCameraMaxLimit = res
				.getInteger(R.integer.camera_back_pixel_max_limit);
		mVideoMinLimit = res.getInteger(R.integer.camera_video_min_limit);
		mVideoMaxLimit = res.getInteger(R.integer.camera_video_max_limit);

		mFrontCameraPix = (EditText) findViewById(R.id.camera_front_pixel);
		mBackCameraPix = (EditText) findViewById(R.id.camera_back_pixel);
		mVideoFps = (EditText) findViewById(R.id.camera_video_fps);
		mCancel = (Button) findViewById(R.id.cancel);
		mOk = (Button) findViewById(R.id.ok);

		mCancel.setOnClickListener(this);
		mOk.setOnClickListener(this);
	}

	private void updateViews() {
		mCamera = mParamSettings.getCameraParam();

		mFrontCameraPix.setText(mCamera.frontCameraPixel);
		mFrontCameraPix.setSelection(mCamera.frontCameraPixel.length());
		mBackCameraPix.setText(mCamera.backCameraPixel);
		mBackCameraPix.setSelection(mCamera.backCameraPixel.length());
		mVideoFps.setText(mCamera.videoQuality);
		mVideoFps.setSelection(mCamera.videoQuality.length());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			setResult(RESULT_CANCELED);
			finish();
			break;

		case R.id.ok:
			Camera camera = checkCamera();
			if (camera != null) {
				if (!mCamera.equals(camera)) {
					mParamSettings.setCameraParam(camera);
					mParamSettings.writeCameraParamToNV(camera);
				}
				setResult(RESULT_OK);
				finish();
			}
			break;
		}
	}

	private Camera checkCamera() {
		Camera camera = null;
		String front = checkFrontPixel();
		if (!TextUtils.isEmpty(front)) {
			String back = checkBackPixel();
			if (!TextUtils.isEmpty(back)) {
				String video = checkVideo();
				if (!TextUtils.isEmpty(video)) {
					camera = new Camera(-1, front, ParamSettingsUtils.calculateWidth(front),
							ParamSettingsUtils.calculateHeight(front), back,
							ParamSettingsUtils.calculateWidth(back),
							ParamSettingsUtils.calculateHeight(back), video, false);
				}
			}
		}
		return camera;
	}

	private String checkFrontPixel() {
		String front = mFrontCameraPix.getText().toString().trim();
		if (!TextUtils.isEmpty(front)) {
			try {
				int value = Integer.valueOf(front);
				if (value >= mFrontCameraMinLimit
						&& value <= mFrontCameraMaxLimit) {
					return front;
				} else {
					Toast.makeText(
							this,
							getString(R.string.camera_front_value_limit,
									mFrontCameraMinLimit, mFrontCameraMaxLimit),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkFrontPixe=>error: ", e);
				Toast.makeText(this, R.string.camera_front_not_a_number,
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.camera_front_not_allow_empty,
					Toast.LENGTH_LONG).show();
		}
		mFrontCameraPix.setText("");
		return null;
	}

	private String checkBackPixel() {
		String back = mBackCameraPix.getText().toString().trim();
		if (!TextUtils.isEmpty(back)) {
			try {
				int value = Integer.valueOf(back);
				if (value >= mBackCameraMinLimit
						&& value <= mBackCameraMaxLimit) {
					return back;
				} else {
					Toast.makeText(
							this,
							getString(R.string.camera_back_value_limit,
									mBackCameraMinLimit, mBackCameraMaxLimit),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.d(this, "checkBackPixel=>error: ", e);
				Toast.makeText(this, R.string.camera_back_not_a_number,
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.camera_back_not_allow_empty,
					Toast.LENGTH_LONG).show();
		}
		mBackCameraPix.setText("");
		return null;
	}

	private String checkVideo() {
		String video = mVideoFps.getText().toString().trim();
		if (!TextUtils.isEmpty(video)) {
			try {
				int value = Integer.valueOf(video);
				if (value >= mVideoMinLimit && value <= mVideoMaxLimit) {
					return video;
				} else {
					Toast.makeText(
							this,
							getString(R.string.camera_video_value_limit,
									mVideoMinLimit, mVideoMaxLimit),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkVideo=>error: ", e);
				Toast.makeText(this, R.string.camera_video_not_a_number,
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.camera_video_not_allow_empty,
					Toast.LENGTH_LONG).show();
		}
		mVideoFps.setText("");
		return null;
	}
}