package com.android.logswitch;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class MainActivity extends PreferenceActivity implements OnPreferenceClickListener {
	
	private static final String MTK6580M_PREFERENCE_KEY = "mtk6580_m_log_key";
	
	private Preference mMtk6580MPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.main_preference);
		initPreferences();
	}

	@SuppressWarnings("deprecation")
	private void initPreferences() {
		mMtk6580MPref = findPreference(MTK6580M_PREFERENCE_KEY);
		mMtk6580MPref.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		Intent intent = new Intent();
		if (preference == mMtk6580MPref) {
			intent.setClass(this, Mtk6580mLogActivity.class);
			startActivity(intent);
		}
		return true;
	}
}
