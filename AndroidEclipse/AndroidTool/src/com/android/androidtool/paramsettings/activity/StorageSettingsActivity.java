package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.ParamSettingsUtils;
import com.android.androidtool.paramsettings.adpater.StorageSettingsAdapter;
import com.android.androidtool.paramsettings.model.Storage;

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

public class StorageSettingsActivity extends Activity implements OnItemClickListener {

	private ListView mListView;
	private StorageSettingsAdapter mAdapter;
	private Storage mLastStorage;
	private Storage mSelectStorage;
	private IParamSettings mParamSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.storage_title);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_storage_settings);
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
			Log.d(this, "onPause=>select storage: " + mLastStorage);
		if (mSelectStorage != null) {
			if (!mSelectStorage.isMore && !mSelectStorage.equals(mLastStorage)) {
				mParamSettings.setStoargeParam(mSelectStorage);
				mParamSettings.writeStorageParamToNV(mSelectStorage);
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
			finish();
			break;
		}
		return true;
	}

	private void initViews() {
		mParamSettings = new ParamSettings(this);
		mListView = (ListView) findViewById(R.id.storage_settings_list);
		mAdapter = new StorageSettingsAdapter(this, ParamSettingsUtils.getStorageList(this));
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	private void updateViews() {
		mLastStorage = mParamSettings.getStorageParam();
		int position = getCurrentSettingPosition();
			Log.d(this, "updateViews=>position: " + position + " last storage: " + mLastStorage);
		mAdapter.setCurrentSelectId(position);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Storage storage = (Storage) mAdapter.getItem(position);
		mSelectStorage = storage;
		if (storage.isMore) {
			startActivityForResult(new Intent(this, StorageMoreActivity.class), 2);
		} else {
			mAdapter.setCurrentSelectId(position);
		}
	}

	private int getCurrentSettingPosition() {
		int position = -1;
		if (mAdapter != null) {
			for (int i = 0; i < mAdapter.getCount(); i++) {
				Storage storage = (Storage) mAdapter.getItem(i);
				if (storage.equals(mLastStorage)) {
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
