package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.ParamSettingsUtils;
import com.android.androidtool.paramsettings.model.Storage;

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

public class StorageMoreActivity extends Activity implements OnClickListener {

	private EditText mUserData;
	private EditText mInternalStorage;
	private Button mOk;
	private Button mCancel;
	private Storage mStorage;
	private IParamSettings mParamSettings;
	
	private int mSystemStorageMinLimit;
	private int mSystemStorageMaxLimit;
	private int mInternalStorageMinLimit;
	private int mInternalStorageMaxLimit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.storage_title);
		actionBar.setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_storage_more);
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_storage_settings, menu);
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
			mParamSettings.resetStorageParamToFactory();
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
		mSystemStorageMinLimit = res
				.getInteger(R.integer.storage_system_min_limit);
		mSystemStorageMaxLimit = res
				.getInteger(R.integer.storage_system_max_limit);
		mInternalStorageMinLimit = res
				.getInteger(R.integer.storage_internal_min_limit);
		mInternalStorageMaxLimit = res
				.getInteger(R.integer.storage_internal_max_limit);
		mUserData = (EditText) findViewById(R.id.user_data);
		mInternalStorage = (EditText) findViewById(R.id.internal_storage);
		mCancel = (Button) findViewById(R.id.cancel);
		mOk = (Button) findViewById(R.id.ok);
		mCancel.setOnClickListener(this);
		mOk.setOnClickListener(this);
	}

	private void updateViews() {
		mStorage = mParamSettings.getStorageParam();
		String system = ParamSettingsUtils.getStorageGBString(mStorage.systemStorage);
		mUserData.setText(system);
		mUserData.setSelection(system.length());
		String internal = ParamSettingsUtils.getStorageGBString(mStorage.internalStorage);
		mInternalStorage.setText(internal);
		mInternalStorage.setSelection(internal.length());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			setResult(RESULT_CANCELED);
			finish();
			break;

		case R.id.ok:
			Storage storage = checkStorage();
			if (storage != null) {
				if (!mStorage.equals(storage)) {
					mParamSettings.setStoargeParam(storage);
					mParamSettings.writeStorageParamToNV(storage);
				}
				setResult(RESULT_OK);
				finish();
			}
			break;
		}
	}

	private Storage checkStorage() {
		Storage storage = null;
		String system = checkSystemStorage();
		if (!TextUtils.isEmpty(system)) {
			String internal = checkInternalStorage();
			if (!TextUtils.isEmpty(internal)) {
				storage = new Storage(
						-1,
						ParamSettingsUtils.formatStorageSize(ParamSettingsUtils.getStorageValue(system) * 1024L * 1024L * 1024L),
						ParamSettingsUtils.formatStorageSize(ParamSettingsUtils.getStorageValue(internal) * 1024L * 1024L * 1024L),
						false);
			}
		}
		return storage;
	}

	private String checkSystemStorage() {
		String system = mUserData.getText().toString().trim();
		if (!TextUtils.isEmpty(system)) {
			try {
				int value = Integer.valueOf(system);
				if (value >= mSystemStorageMinLimit
						&& value <= mSystemStorageMaxLimit) {
					return system;
				} else {
					Toast.makeText(
							this,
							getString(R.string.system_storage_value_limit,
									mSystemStorageMinLimit,
									mSystemStorageMaxLimit),
							Toast.LENGTH_LONG)
							.show();
				}
			} catch (Exception e) {
				Log.e(this, "checkSystemStorage=>error: ", e);
				Toast.makeText(this, R.string.system_storage_not_a_number,
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.system_storage_not_allow_empty,
					Toast.LENGTH_LONG).show();
		}
		mUserData.setText("");
		return null;
	}

	private String checkInternalStorage() {
		String internal = mInternalStorage.getText().toString().trim();
		if (!TextUtils.isEmpty(internal)) {
			try {
				int value = Integer.valueOf(internal);
				if (value >= mInternalStorageMinLimit
						&& value <= mInternalStorageMaxLimit) {
					return internal;
				} else {
					Toast.makeText(
							this,
							getString(R.string.internal_storage_value_limit,
									mInternalStorageMinLimit,
									mInternalStorageMaxLimit),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkInternalStorage=>error: ", e);
				Toast.makeText(this, R.string.internal_storage_not_a_number,
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.internal_storage_not_allow_empty,
					Toast.LENGTH_LONG).show();
		}

		mInternalStorage.setText("");
		return null;
	}
}