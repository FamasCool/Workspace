package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.ParamSettingsUtils;
import com.android.androidtool.paramsettings.adpater.CameraSettingsAdapter;
import com.android.androidtool.paramsettings.model.Camera;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CameraSettingActivity extends Activity implements OnItemClickListener {

	private ListView mListView;
	private CameraSettingsAdapter mAdapter;
	private Camera mLastCamera;
	private Camera mSelectCamera;
	private IParamSettings mParamSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.camera_title);
		actionBar.setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_camera_settings);
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateViews();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(this, "onPause=>select camera: " + mSelectCamera);
		if (mSelectCamera != null) {
			if (!mSelectCamera.isMore && !mSelectCamera.equals(mLastCamera)) {
				mParamSettings.setCameraParam(mSelectCamera);
				mParamSettings.writeCameraParamToNV(mSelectCamera);
			}
		}
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
			finish();
			break;
		}
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			finish();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Camera camera = (Camera) mAdapter.getItem(position);
		mSelectCamera = camera;
		if (camera.isMore) {
			startActivityForResult(new Intent(this, CameraMoreActivity.class), 4);
		} else {
			mAdapter.setCurrentSelectId(position);
		}
	}

	private void initViews() {
		mParamSettings = new ParamSettings(this);
		mListView = (ListView) findViewById(R.id.camera_settings_list);
		mAdapter = new CameraSettingsAdapter(this, ParamSettingsUtils.getCameraList(this));
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	private void updateViews() {
		mLastCamera = mParamSettings.getCameraParam();
		int position = getCurrentSettingPosition();
		Log.d(this, "updateViews=>position: " + position + " last camera: " + mLastCamera);
		mAdapter.setCurrentSelectId(position);
	}

	private int getCurrentSettingPosition() {
		int position = -1;
		if (mAdapter != null) {
			for (int i = 0; i < mAdapter.getCount(); i++) {
				Camera camera = (Camera) mAdapter.getItem(i);
				if (camera.equals(mLastCamera)) {
					position = i;
					break;
				}
			}
			if (position == -1) {
				position = mAdapter.getCount() - 1;
			}
		}
		return position;
	}
}