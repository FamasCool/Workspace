package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModelActivity extends Activity implements OnClickListener {
	
	private static final String PROPERTIES_KEY = "persist.sys.custom_dev_model";
	
	private EditText mProductModel;
	private TextView mTipTextView;
	private Button mOkBtn;
	private IParamSettings mParamSettings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.model_tilte);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		
		setContentView(R.layout.activity_model);
		
		mParamSettings = new ParamSettings(this);
		initViews();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_CANCELED);
			finish();
			break;
		}
		return true;
	}

	private void initViews() {
		mProductModel = (EditText) findViewById(R.id.product_model);
		mTipTextView = (TextView) findViewById(R.id.tip);
		mOkBtn = (Button) findViewById(R.id.ok);
		
		String model = SystemProperties.get(PROPERTIES_KEY);
		
		if (!TextUtils.isEmpty(model)) {
			mProductModel.setHint(model);
		}
		mProductModel.setFocusable(true);
		mOkBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok:
			String model = mProductModel.getText().toString();
			if (!TextUtils.isEmpty(model)) {
				SystemProperties.set(PROPERTIES_KEY, model);
				boolean result = mParamSettings.writeProductModelToNV(model);
				if (result) {
					Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(this, R.string.fail, Toast.LENGTH_SHORT).show();
				}
			} else {
				mTipTextView.setVisibility(View.VISIBLE);
				mTipTextView.setText(R.string.not_allow_empty_model);
			}
			break;
		}
	}

}
