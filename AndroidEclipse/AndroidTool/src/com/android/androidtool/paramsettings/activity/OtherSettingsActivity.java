package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.ParamSettingsUtils;
import com.android.androidtool.paramsettings.adpater.OtherSettingsAdapter;
import com.android.androidtool.paramsettings.model.Other;

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

public class OtherSettingsActivity extends Activity implements OnItemClickListener {

	private ListView mListView;
	private OtherSettingsAdapter mAdapter;
	private Other mLastOther;
	private Other mSelectOther;
	private IParamSettings mParamSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.other_title);
		actionBar.setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_other_settings);
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
			Log.d(this, "onPause=>select other: " + mSelectOther);
		if (mSelectOther != null) {
			if (!mSelectOther.isMore && !mSelectOther.equals(mLastOther)) {
				mParamSettings.setOtherParam(mSelectOther);
				mParamSettings.writeOtherParamToNV(mSelectOther);
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
			finish();
			break;
		}

		return true;
	}

	private void initViews() {
		mParamSettings = new ParamSettings(this);
		mListView = (ListView) findViewById(R.id.other_settings_list);
		mAdapter = new OtherSettingsAdapter(this, ParamSettingsUtils.getOtherList(this));
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	private void updateViews() {
		mLastOther = mParamSettings.getOtherParam();
		int position = getCurrentSettingPosition();
			Log.d(this, "updateViews=>position: " + position + " last other: " + mLastOther);
		mAdapter.setCurrentSelectId(position);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Other other = (Other) mAdapter.getItem(position);
		mSelectOther = other;
		if (other.isMore) {
			startActivityForResult(new Intent(this, OtherMoreActivity.class), 5);
		} else {
			mAdapter.setCurrentSelectId(position);
		}
	}

	private int getCurrentSettingPosition() {
		int position = -1;
		if (mAdapter != null) {
			for (int i = 0; i < mAdapter.getCount(); i++) {
				Other other = (Other) mAdapter.getItem(i);
				if (other.equals(mLastOther)) {
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