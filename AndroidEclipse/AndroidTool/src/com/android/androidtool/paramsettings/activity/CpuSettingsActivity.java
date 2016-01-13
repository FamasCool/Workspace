package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.ParamSettingsUtils;
import com.android.androidtool.paramsettings.adpater.CpuSettingsAdapter;
import com.android.androidtool.paramsettings.model.Cpu;

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

public class CpuSettingsActivity extends Activity implements OnItemClickListener {

	private ListView mListView;
	private CpuSettingsAdapter mAdapter;
	private IParamSettings mParamSettings;
	private Cpu mLastCpu;
	private Cpu mSelectCpu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.cpu_title);
		actionBar.setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_cpu_settings);

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
		Log.d(this, "onPause=>selectCpu: " + mSelectCpu);
		if (mSelectCpu != null) {
			if (!mSelectCpu.isMore && !mLastCpu.equals(mSelectCpu)) {
				mParamSettings.setCpuParam(mSelectCpu);
				mParamSettings.writeCpuParamToNV(mSelectCpu);
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			finish();
		}
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
			finish();
			break;
		}

		return true;
	}

	private void initViews() {
		mParamSettings = new ParamSettings(this);
		mListView = (ListView) findViewById(R.id.cpu_settings_list);
		mAdapter = new CpuSettingsAdapter(this, ParamSettingsUtils.getCpuList(this));
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	private void updateViews() {
		mLastCpu = mParamSettings.getCpuParam();
		int position = getCurrentSettingPosition();
		Log.d(this, "updateViews=>lastCpu: " + mLastCpu + " position: " + position);
		mAdapter.setCurrentSelectId(position);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Cpu cpu = (Cpu) mAdapter.getItem(position);
		mSelectCpu = cpu;
		Log.d(this, "onItemClick=>position: " + position + " cpu: " + cpu);
		if (cpu.isMore) {
			startActivityForResult(new Intent(this, CpuMoreActivity.class), 0);
		} else {
			mAdapter.setCurrentSelectId(position);
		}
	}

	private int getCurrentSettingPosition() {
		int position = -1;
		if (mAdapter != null && mLastCpu != null) {
			for (int i = 0; i < mAdapter.getCount(); i++) {
				Cpu cpu = (Cpu) mAdapter.getItem(i);
				if (cpu.equals(mLastCpu)) {
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
