package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.ParamSettingsUtils;
import com.android.androidtool.paramsettings.adpater.MemorySettingsAdapter;
import com.android.androidtool.paramsettings.model.Memory;

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

public class MemorySettingsActivity extends Activity implements OnItemClickListener {

	private ListView mListView;
	private MemorySettingsAdapter mAdapter;
	private Memory mLastMemory;
	private Memory mSelectMemory;
	private IParamSettings mParamSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.memory_title);
		actionBar.setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_memory_settings);
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
		Log.d(this, "onPause=>select memory: " + mSelectMemory);
		if (mSelectMemory != null) {
			if (!mSelectMemory.isMore && !mSelectMemory.equals(mLastMemory)) {
				mParamSettings.setMemoryParam(mSelectMemory);
				mParamSettings.writeMemoryParamToNV(mSelectMemory);
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
			finish();
			break;
		}
		return true;
	}

	private void initViews() {
		mParamSettings = new ParamSettings(this);
		mListView = (ListView) findViewById(R.id.memory_settings_list);
		mAdapter = new MemorySettingsAdapter(this, ParamSettingsUtils.getMemoryList(this));
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	private void updateViews() {
		mLastMemory = mParamSettings.getMemoryParam();
		int position = getCurrentSettingPosition();
		Log.d(this, "updateViews=>position: " + position + " last memory: " + mLastMemory);
		mAdapter.setCurrentSelectId(position);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Memory memory = (Memory) mAdapter.getItem(position);
		mSelectMemory = memory;
		if (memory.isMore) {
			startActivityForResult(new Intent(this, MemoryMoreActivity.class), 1);
		} else {
			mAdapter.setCurrentSelectId(position);
		}
	}

	private int getCurrentSettingPosition() {
		int position = -1;
		if (mAdapter != null) {
			Log.d(this, "getCurrentSettingPosition=>mLastMemory: " + mLastMemory);
			for (int i = 0; i < mAdapter.getCount(); i++) {
				Memory memory = (Memory) mAdapter.getItem(i);
				Log.d(this, "getCurrentSettingPosition=>memory[" + i + "]: " + memory);
				if (memory.equals(mLastMemory)) {
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