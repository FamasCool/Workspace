package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.ParamSettingsUtils;
import com.android.androidtool.paramsettings.model.Memory;

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

public class MemoryMoreActivity extends Activity implements OnClickListener {

	private EditText mMaxRam;
	private EditText mMinRam;
	private Button mOk;
	private Button mCancel;
	private Memory mMemory;
	private IParamSettings mParamSettings;
	private int mMaxRamMinLimit;
	private int mMaxRamMaxLimit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.memory_title);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_memory_more);
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_memory_settings, menu);
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
			mParamSettings.resetMemoryParamToFactory();
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
		mMaxRamMinLimit = res.getInteger(R.integer.memory_max_ram_min_limit);
		mMaxRamMaxLimit = res.getInteger(R.integer.memory_max_ram_max_limit);
		mMaxRam = (EditText) findViewById(R.id.memory_max_ram);
		mMinRam = (EditText) findViewById(R.id.memory_min_ram);
		mCancel = (Button) findViewById(R.id.cancel);
		mOk = (Button) findViewById(R.id.ok);
		mCancel.setOnClickListener(this);
		mOk.setOnClickListener(this);
	}

	private void updateViews() {
		mMemory = mParamSettings.getMemoryParam();
		int value = ParamSettingsUtils.getStorageValue(mMemory.maxRam);
		if (value != -1) {
			String maxRam = value + "";
			mMaxRam.setText(maxRam);
			mMaxRam.setSelection(maxRam.length());
		} else {
			mMaxRam.setText("");
		}
		value = ParamSettingsUtils.getStorageValue(mMemory.minRam);
		if (value != -1) {
			String minRam = value + "";
			mMinRam.setText(minRam);
			mMinRam.setSelection(minRam.length());
		} else {
			mMinRam.setText("");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			setResult(RESULT_CANCELED);
			finish();
			break;

		case R.id.ok:
			Memory memory = checkMemory();
			if (memory != null) {
				if (!mMemory.equals(memory)) {
					mParamSettings.setMemoryParam(memory);
					mParamSettings.writeMemoryParamToNV(memory);
				}
				setResult(RESULT_OK);
				finish();
			}
			break;
		}
	}

	private Memory checkMemory() {
		Memory memory = null;
		String maxRam = checkMaxRam();
		if (!TextUtils.isEmpty(maxRam)) {
			String minRam = checkMinRam(maxRam);
			if (!TextUtils.isEmpty(minRam)) {
				memory = new Memory(
						-1,
						ParamSettingsUtils.formatStorageSize(ParamSettingsUtils.getStorageValue(minRam) * 1024L * 1024L),
						ParamSettingsUtils.formatStorageSize(ParamSettingsUtils.getStorageValue(maxRam) * 1024L * 1024L),
						false);
			}
		}
			Log.d(this, "checkMemory=>memory: " + memory);
		return memory;
	}

	private String checkMaxRam() {
		String max = mMaxRam.getText().toString().trim();
		if (!TextUtils.isEmpty(max)) {
			try {
				int value = Integer.valueOf(max);
				if (value >= 1024 && value <= 32768) {
					if (value % 1024 == 0) {
						return max;
					} else {
						Toast.makeText(this, R.string.memory_max_ram_gb_limit,
								Toast.LENGTH_LONG).show();
					}
				} else if (value >= mMaxRamMinLimit && value < mMaxRamMaxLimit) {
					return max;
				} else {
					Toast.makeText(
							this,
							getString(R.string.memory_max_ram_limit,
									mMaxRamMinLimit, mMaxRamMaxLimit),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkMaxRam=>error: ", e);
				Toast.makeText(this, R.string.memory_max_ram_not_a_number,
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.memory_max_ram_not_allow_empty,
					Toast.LENGTH_LONG).show();
		}
		mMaxRam.setText("");
		return null;
	}

	private String checkMinRam(String max) {
		String min = mMinRam.getText().toString().trim();
		if (!TextUtils.isEmpty(min)) {
			try {
				int value = Integer.valueOf(min);
				int maxValue = Integer.valueOf(max);
				if (value > 0 && value <= (maxValue / 2)) {
					if (value >= 1024) {
						if (value % 1024 == 0) {
							return min;
						} else {
							Toast.makeText(this,
									R.string.memory_min_ram_gb_limit,
									Toast.LENGTH_LONG).show();
						}
					} else {
						return min;
					}
				} else {
					Toast.makeText(this, R.string.memory_min_ram_value_limit,
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkMinRam=>error: ", e);
				Toast.makeText(this, R.string.memory_min_ram_not_a_number,
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.memory_min_ram_not_allow_empty,
					Toast.LENGTH_LONG).show();
		}
		mMinRam.setText("");
		return null;
	}
}