package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.ParamSettingsUtils;
import com.android.androidtool.paramsettings.adpater.LcdSettingsAdapter;
import com.android.androidtool.paramsettings.model.Lcd;

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

public class LcdSettingsActivity extends Activity implements OnItemClickListener {

	private ListView mListView;
	private LcdSettingsAdapter mAdapter;
	private Lcd mLastLcd;
	private Lcd mSelectLcd;
	private IParamSettings mParamSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.lcd_title);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_screen_settings);
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
			Log.d(this, "onPause=>select memory: " + mSelectLcd);
		if (mSelectLcd != null) {
			if (!mSelectLcd.isMore && !mSelectLcd.equals(mLastLcd)) {
				mParamSettings.setLcdParam(mSelectLcd);
				mParamSettings.writeLcdParamToNV(mSelectLcd);
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
		getMenuInflater().inflate(R.menu.menu_lcd_settings, menu);
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
			mParamSettings.resetLcdParamToFactory();
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
		mAdapter = new LcdSettingsAdapter(this, ParamSettingsUtils.getLcdList(this));
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	private void updateViews() {
		mLastLcd = mParamSettings.getLcdParam();
		int position = getCurrentSettingPosition();
			Log.d(this, "updateViews=>position: " + position + " last lcd: " + mLastLcd);
		mAdapter.setCurrentSelectId(position);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Lcd lcd = (Lcd) mAdapter.getItem(position);
		mSelectLcd = lcd;
		if (lcd.isMore) {
			startActivityForResult(new Intent(this, LcdMoreActivity.class), 3);
		} else {
			mAdapter.setCurrentSelectId(position);
		}
	}

	private int getCurrentSettingPosition() {
		int position = -1;
		if (mAdapter != null) {
			for (int i = 0; i < mAdapter.getCount(); i++) {
				Lcd lcd = (Lcd) mAdapter.getItem(i);
				if (lcd.equals(mLastLcd)) {
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