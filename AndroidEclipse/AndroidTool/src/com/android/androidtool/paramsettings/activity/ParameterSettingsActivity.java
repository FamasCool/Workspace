package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.ParamSettingsUtils;
import com.android.androidtool.paramsettings.adpater.ParameterSettingsAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ParameterSettingsActivity extends Activity implements OnItemClickListener {

	private static final int CPU = 0;
	private static final int MEMORY = 1;
	private static final int STORAGE = 2;
	private static final int LCD = 3;
	private static final int CAMERA = 4;
	private static final int OTHER = 5;
	private static final int MODEL = 6;	
	private static final int SETTINGS = 7;

	private IParamSettings mParamSettings;
	private GridView mGridView;
	private ParameterSettingsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_parameter_settinsg);

		initViews();
	}

	public void initViews() {
		mParamSettings = new ParamSettings(this);
		mGridView = (GridView) findViewById(R.id.main_grid_view);
		mAdapter = new ParameterSettingsAdapter(this, ParamSettingsUtils.getTitleList(this), ParamSettingsUtils.getSettingsBackgroundList(this));
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		case CPU:
			startActivity(new Intent(this, CpuSettingsActivity.class));
			break;

		case MEMORY:
			startActivity(new Intent(this, MemorySettingsActivity.class));
			break;

		case STORAGE:
			startActivity(new Intent(this, StorageSettingsActivity.class));
			break;

		case LCD:
			startActivity(new Intent(this, LcdSettingsActivity.class));
			break;

		case CAMERA:
			startActivity(new Intent(this, CameraSettingActivity.class));
			break;

		case OTHER:
			startActivity(new Intent(this, OtherSettingsActivity.class));
			break;
			
		case MODEL:
			startActivity(new Intent(this, ModelActivity.class));
			break;
			
		case SETTINGS:
			startActivity(new Intent(this, SettingsActivity.class));
			break;
		}
	}

}