package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;
import com.android.androidtool.paramsettings.model.Lcd;

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

public class LcdMoreActivity extends Activity implements OnClickListener {

	private EditText mWidth;
	private EditText mHeight;
	private EditText mDpi;
	private Button mOk;
	private Button mCancel;
	private Lcd mLcd;
	private IParamSettings mParamSettings;

	private int mLcdWidthMinLimit;
	private int mLcdWidthMaxLimit;
	private int mLcdHeightMinLimit;
	private int mLcdHeightMaxLimit;
	private int mLcdDensityMinLimit;
	private int mLcdDensityMaxLimit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.lcd_title);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_lcd_more);
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateViews();
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
			setResult(RESULT_CANCELED);
			finish();
			break;
		}

		return true;
	}

	private void initViews() {
		mParamSettings = new ParamSettings(this);
		Resources res = getResources();
		mLcdWidthMinLimit = res.getInteger(R.integer.lcd_width_min_limit);
		mLcdWidthMaxLimit = res.getInteger(R.integer.lcd_width_max_limit);
		mLcdHeightMinLimit = res.getInteger(R.integer.lcd_height_min_limit);
		mLcdHeightMaxLimit = res.getInteger(R.integer.lcd_height_max_limit);
		mLcdDensityMinLimit = res.getInteger(R.integer.lcd_density_min_limit);
		mLcdDensityMaxLimit = res.getInteger(R.integer.lcd_density_max_limit);
		mWidth = (EditText) findViewById(R.id.lcd_width);
		mHeight = (EditText) findViewById(R.id.lcd_height);
		mDpi = (EditText) findViewById(R.id.lcd_dpi);
		mCancel = (Button) findViewById(R.id.cancel);
		mOk = (Button) findViewById(R.id.ok);
		mCancel.setOnClickListener(this);
		mOk.setOnClickListener(this);
	}

	private void updateViews() {
		mLcd = mParamSettings.getLcdParam();
		mWidth.setText(mLcd.width);
		mWidth.setSelection(mLcd.width.length());
		mHeight.setText(mLcd.height);
		mHeight.setSelection(mLcd.height.length());
		mDpi.setText(mLcd.density);
		mDpi.setSelection(mLcd.density.length());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			setResult(RESULT_CANCELED);
			finish();
			break;

		case R.id.ok:
			Lcd lcd = checkLcd();
			if (lcd != null) {
				if (!mLcd.equals(lcd)) {
					mParamSettings.setLcdParam(lcd);
					mParamSettings.writeLcdParamToNV(lcd);
				}
				setResult(RESULT_OK);
				finish();
			}
			break;
		}
	}

	private Lcd checkLcd() {
		Lcd lcd = null;
		String width = checkWidth();
		if (!TextUtils.isEmpty(width)) {
			String height = checkHeight();
			if (!TextUtils.isEmpty(height)) {
				String density = checkDensity();
				if (!TextUtils.isEmpty(density)) {
					lcd = new Lcd(-1, width, height, density, false);
				}
			}
		}
		return lcd;
	}

	private String checkWidth() {
		String width = mWidth.getText().toString().trim();
		if (!TextUtils.isEmpty(width)) {
			try {
				int value = Integer.valueOf(width);
				if (value >= mLcdWidthMinLimit && value <= mLcdWidthMaxLimit) {
					return width;
				} else {
					Toast.makeText(this,
							getString(R.string.lcd_width_value_limit, mLcdWidthMinLimit, mLcdWidthMaxLimit),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkWidth=>error: ", e);
				Toast.makeText(this, R.string.lcd_width_not_a_number, Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.lcd_width_not_allow_empty, Toast.LENGTH_LONG).show();
		}
		mWidth.setText("");
		return null;
	}

	private String checkHeight() {
		String height = mHeight.getText().toString().trim();
		if (!TextUtils.isEmpty(height)) {
			try {
				int value = Integer.valueOf(height);
				if (value >= mLcdHeightMinLimit && value <= mLcdHeightMaxLimit) {
					return height;
				} else {
					Toast.makeText(this,
							getString(R.string.lcd_height_value_limit, mLcdHeightMinLimit, mLcdHeightMaxLimit),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkHeight=>error: ", e);
				Toast.makeText(this, R.string.lcd_height_not_a_number, Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.lcd_height_not_allow_empty, Toast.LENGTH_LONG).show();
		}
		mHeight.setText("");
		return null;
	}

	private String checkDensity() {
		String density = mDpi.getText().toString().trim();
		if (!TextUtils.isEmpty(density)) {
			try {
				int value = Integer.valueOf(density);
				if (value >= mLcdDensityMinLimit && value <= mLcdDensityMaxLimit) {
					return density;
				} else {
					Toast.makeText(this,
							getString(R.string.lcd_density_value_limit, mLcdDensityMinLimit, mLcdDensityMaxLimit),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(this, "checkDensity=>error: ", e);
				Toast.makeText(this, R.string.lcd_density_not_a_number, Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.lcd_density_not_allow_empty, Toast.LENGTH_LONG).show();
		}
		mDpi.setText("");
		return null;
	}
}