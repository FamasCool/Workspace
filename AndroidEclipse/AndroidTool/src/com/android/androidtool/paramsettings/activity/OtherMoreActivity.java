package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.model.Other;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class OtherMoreActivity extends Activity implements OnClickListener {

	private CheckBox mSupportAllSensor;
	private CheckBox mSupportRoot;
	private EditText mScore;
	private Button mOk;
	private Button mCancel;
	private Other mOther;
	private IParamSettings mParamSettings;

	private int mBenchmarkMinLimit;
	private int mBenchmarkMaxLimit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.other_title);
		actionBar.setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_other_more);
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_other_settings, menu);
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
			mParamSettings.resetOtherParamToFactory();
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
		mBenchmarkMinLimit = res.getInteger(R.integer.other_benchmark_min_limit);
		mBenchmarkMaxLimit = res.getInteger(R.integer.other_benchmark_max_limit);
		mSupportAllSensor = (CheckBox) findViewById(R.id.other_support_sensor);
		mSupportRoot = (CheckBox) findViewById(R.id.other_support_root);
		mScore = (EditText) findViewById(R.id.other_score);
		mCancel = (Button) findViewById(R.id.cancel);
		mOk = (Button) findViewById(R.id.ok);
		mCancel.setOnClickListener(this);
		mOk.setOnClickListener(this);
	}

	private void updateViews() {
		mOther = mParamSettings.getOtherParam();
		if ("true".equals(mOther.supportAllSensor)) {
			mSupportAllSensor.setChecked(true);
		} else {
			mSupportAllSensor.setChecked(false);
		}
		if ("true".equals(mOther.supportRoot)) {
			mSupportRoot.setChecked(true);
		} else {
			mSupportRoot.setChecked(false);
		}
		mScore.setText(mOther.benchmark);
		mScore.setSelection(mOther.benchmark.length());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			setResult(RESULT_CANCELED);
			finish();
			break;

		case R.id.ok:
			Other other = checkOther();
			if (other != null) {
				if (!mOther.equals(other)) {
					mParamSettings.setOtherParam(other);
					mParamSettings.writeOtherParamToNV(other);
				}
				setResult(RESULT_OK);
				finish();
			}
			break;
		}
	}

	private Other checkOther() {
		Other other = null;
		String benchmark = checkBenchmark();
		if (!TextUtils.isEmpty(benchmark)) {
			other = new Other(-1, (mSupportAllSensor.isChecked() ? "true" : "false"),
					(mSupportRoot.isChecked() ? "true" : "false"), benchmark, false);
		}
		return other;
	}

	private String checkBenchmark() {
		String benchmark = mScore.getText().toString().trim();
		if (!TextUtils.isEmpty(benchmark)) {
			try {
				int value = Integer.valueOf(benchmark);
				if (value >= mBenchmarkMinLimit && value <= mBenchmarkMaxLimit) {
					return benchmark;
				} else {
					Toast.makeText(this,
							getString(R.string.other_benchmark_value_limit, mBenchmarkMinLimit, mBenchmarkMaxLimit),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkBenchmark=>error: ", e);
				Toast.makeText(this, R.string.other_benchmark_not_a_number, Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.other_benchmark_not_allow_empty, Toast.LENGTH_LONG).show();
		}
		mScore.setText("");
		return null;
	}
}