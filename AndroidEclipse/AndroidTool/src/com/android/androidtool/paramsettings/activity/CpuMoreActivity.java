package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.model.Cpu;

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

public class CpuMoreActivity extends Activity implements OnClickListener {

	private EditText mType;
	private EditText mCore;
	private EditText mMinFreq;
	private EditText mMaxFreq;
	private Button mCancel;
	private Button mOk;
	private IParamSettings mParamSettings;
	private Cpu mCpu;

	private int[] mCoreLimits;
	private int mMaxFreqLessLimit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.cpu_title);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_cpu_more);
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_cpu_settings, menu);
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
			mParamSettings.resetCpuParamToFactory();
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
		mCoreLimits = res.getIntArray(R.array.core_limits);
		mMaxFreqLessLimit = res.getInteger(R.integer.cpu_max_freq_min_limit);
		mType = (EditText) findViewById(R.id.cpu_type);
		mCore = (EditText) findViewById(R.id.cpu_core);
		mMinFreq = (EditText) findViewById(R.id.cpu_min_freq);
		mMaxFreq = (EditText) findViewById(R.id.cpu_max_freq);
		mCancel = (Button) findViewById(R.id.cancel);
		mOk = (Button) findViewById(R.id.ok);
		mCancel.setOnClickListener(this);
		mOk.setOnClickListener(this);
	}

	private void updateViews() {
		mCpu = mParamSettings.getCpuParam();
		mType.setText(mCpu.type);
		mType.setSelection(mCpu.type.length());
		mCore.setText(mCpu.core);
		mCore.setSelection(mCpu.core.length());
		mMinFreq.setText(mCpu.minFreq);
		mMinFreq.setSelection(mCpu.minFreq.length());
		mMaxFreq.setText(mCpu.maxFreq);
		mMaxFreq.setSelection(mCpu.maxFreq.length());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			setResult(RESULT_CANCELED);
			finish();
			break;

		case R.id.ok:
			Cpu cpu = checkCpu();
			Log.d(this, "onClick=>cpu: " + cpu);
			if (cpu != null) {
				if (!mCpu.equals(cpu)) {
					mParamSettings.setCpuParam(cpu);
					mParamSettings.writeCpuParamToNV(cpu);
				}
				setResult(RESULT_OK);
				finish();
			}
			break;
		}
	}

	private Cpu checkCpu() {
		Cpu cpu = null;
		String type = checkType();
		if (!TextUtils.isEmpty(type)) {
			String core = checkCore();
			if (!TextUtils.isEmpty(core)) {
				String maxFreq = checkMaxFreq();
				if (!TextUtils.isEmpty(maxFreq)) {
					String minFreq = checkMinFreq(maxFreq);
					if (!TextUtils.isEmpty(minFreq)) {
						cpu = new Cpu(-1, type, core, minFreq, maxFreq, false);
					}
				}
			}
		}
		return cpu;
	}

	private String checkType() {
		String type = mType.getText().toString().trim();
		if (!TextUtils.isEmpty(type)) {
			return type;
		} else {
			Toast.makeText(this, R.string.cpu_type_not_allow_empty, Toast.LENGTH_LONG).show();
		}
		mType.setText("");
		return null;
	}

	private String checkCore() {
		String core = mCore.getText().toString().trim();
		if (!TextUtils.isEmpty(core)) {
			try {
				int value = Integer.valueOf(core);
				for (int i : mCoreLimits) {
					if (value == i) {
						return core;
					}
				}
				Toast.makeText(this, getString(R.string.cpu_core_value_limit, getArrayString(mCoreLimits)),
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Log.e(this, "checkCore=>error: ", e);
				Toast.makeText(this, R.string.cpu_core_not_a_number, Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.cpu_core_not_allow_empty, Toast.LENGTH_LONG).show();
		}
		mCore.setText("");
		return null;
	}

	private String checkMinFreq(String max) {
		String min = mMinFreq.getText().toString().trim();
		if (!TextUtils.isEmpty(min)) {
			try {
				int value = Integer.valueOf(min);
				int maxValue = Integer.valueOf(max);
				if (value > 0 && value < maxValue) {
					return min;
				} else {
					Toast.makeText(this, R.string.cpu_min_freq_value_limit, Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkMinFreq=>error: ", e);
				Toast.makeText(this, R.string.cpu_min_freq_not_a_number, Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.cpu_min_freq_not_allow_empty, Toast.LENGTH_LONG).show();
		}
		mMinFreq.setText("");
		return null;
	}

	private String checkMaxFreq() {
		String max = mMaxFreq.getText().toString().trim();
		if (!TextUtils.isEmpty(max)) {
			try {
				int value = Integer.valueOf(max);
				if (value >= mMaxFreqLessLimit) {
					return max;
				} else {
					Toast.makeText(this, getString(R.string.cpu_max_freq_value_limit, mMaxFreqLessLimit),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkMaxFreq=>error: ", e);
				Toast.makeText(this, R.string.cpu_max_freq_not_a_number, Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.cpu_max_freq_not_allow_empty, Toast.LENGTH_LONG).show();
		}
		mMaxFreq.setText("");
		return null;
	}

	private String getArrayString(int[] array) {
		String str = "";
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				str += array[i];
				if (i + 1 < array.length) {
					str += ", ";
				}
			}
		}
		return str;
	}
}