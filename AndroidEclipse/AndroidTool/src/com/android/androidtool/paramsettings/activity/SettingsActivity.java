package com.android.androidtool.paramsettings.activity;

import com.android.androidtool.R;
import com.android.androidtool.paramsettings.IParamSettings;
import com.android.androidtool.paramsettings.ParamSettings;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.MenuItem;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceClickListener {
	
	private static final String PREF_RESET_CUSTOM_PARAMETER = "reset_all_custom_device_parameter";
	
	private Preference mResetCustomParameterPref;
	
	private Dialog mResetDialog;
	private IParamSettings mParamSettings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.model_tilte);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		
		addPreferencesFromResource(R.xml.settings_preference);
		
		mParamSettings = new ParamSettings(this);
		initPreferences();
	}
	
	private void initPreferences() {
		mResetCustomParameterPref = findPreference(PREF_RESET_CUSTOM_PARAMETER);
		
		mResetCustomParameterPref.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference == mResetCustomParameterPref) {
			if (mResetDialog == null) {
				mResetDialog = createResetDialog(R.string.reset_custom_device_parameter_dialog_msg,
						mCustomParameterNegativeListener, mCustomParameterPositiveListener);
			}
			if (!mResetDialog.isShowing()) {
				mResetDialog.show();
			}
		}
		return true;
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
	
	private Dialog createResetDialog(int resMsg, DialogInterface.OnClickListener negativeListener,
			DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.ic_warning);
		builder.setTitle(R.string.warning_dialog_title);
		builder.setMessage(resMsg);
		builder.setNegativeButton(android.R.string.cancel, negativeListener);
		builder.setPositiveButton(android.R.string.ok, positiveListener);
		return builder.create();
	}
	
	private DialogInterface.OnClickListener mCustomParameterNegativeListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
	
	private DialogInterface.OnClickListener mCustomParameterPositiveListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			mParamSettings.resetAllParamToFactory();
			Toast.makeText(getBaseContext(), R.string.success, Toast.LENGTH_SHORT).show();
		}
	};

}
