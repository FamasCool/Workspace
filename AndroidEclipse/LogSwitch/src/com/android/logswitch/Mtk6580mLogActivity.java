package com.android.logswitch;

import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.SwitchPreference;

public class Mtk6580mLogActivity extends PreferenceActivity implements OnPreferenceChangeListener {

	private static final String PREF_CUSTOM_PARAMETER_SETTINGS_CATEGORY = "custom_parameter_settings_category_key";
	private static final String PREF_CUSTOM_PARAMETER_SETTINGS = "custom_parameter_serttings_key";
	private static final String PREF_ANTUTU_PARAMETER_SETTINGS = "antutu_settings_key";
	private static final String PREF_LUDASHI_PARAMETER_SETTINGS = "ludashi_settings_key";
	private static final String PREF_YOUHUADASHI_PARAMETER_SETTINGS = "youhudashi_settings_key";

	private static final String KEY_CUSTOM_PARAMETER_SETTINGS_LOG = "persist.sys.log.cdp";
	private static final String KEY_ANTUTU_PARAMETER_SETTINGS_LOG = "persist.sys.log.cdp_antutu";
	private static final String KEY_LUDASHI_PARAMETER_SETTINGS_LOG = "persist.sys.log.cdp_lds";
	private static final String KEY_YOUHUADASHI_PARAMETER_SETTINGS_LOG = "persist.sys.log.cdp_yhds";

	private PreferenceCategory mCustomParamterSettingsCategory;
	private SwitchPreference mCustomParameterSettingsPref;
	private SwitchPreference mAntutuParameterSettingsPref;
	private SwitchPreference mLuDaShiParameterSettingsPref;
	private SwitchPreference mYouHuaDaShiParameterSettingsPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.mtk6580_m_preference);

		initPreferences();
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference instanceof SwitchPreference) {
			boolean isChecked = (Boolean) newValue;
			Log.d(this, "onPreferenceChange=>isChecked: " + isChecked);
			if (preference == mCustomParameterSettingsPref) {
				SystemProperties.set(KEY_CUSTOM_PARAMETER_SETTINGS_LOG, String.valueOf(isChecked));
				mCustomParameterSettingsPref
						.setChecked(SystemProperties.getBoolean(KEY_CUSTOM_PARAMETER_SETTINGS_LOG, false));
			} else if (preference == mAntutuParameterSettingsPref) {
				SystemProperties.set(KEY_ANTUTU_PARAMETER_SETTINGS_LOG, String.valueOf(isChecked));
				mAntutuParameterSettingsPref
						.setChecked(SystemProperties.getBoolean(KEY_ANTUTU_PARAMETER_SETTINGS_LOG, false));
			} else if (preference == mLuDaShiParameterSettingsPref) {
				SystemProperties.set(KEY_LUDASHI_PARAMETER_SETTINGS_LOG, String.valueOf(isChecked));
				mLuDaShiParameterSettingsPref
						.setChecked(SystemProperties.getBoolean(KEY_LUDASHI_PARAMETER_SETTINGS_LOG, false));
			} else if (preference == mYouHuaDaShiParameterSettingsPref) {
				SystemProperties.set(KEY_YOUHUADASHI_PARAMETER_SETTINGS_LOG, String.valueOf(isChecked));
				mYouHuaDaShiParameterSettingsPref
						.setChecked(SystemProperties.getBoolean(KEY_YOUHUADASHI_PARAMETER_SETTINGS_LOG, false));
			}
		}
		return false;
	}

	private void initPreferences() {
		mCustomParamterSettingsCategory = (PreferenceCategory) findPreference(PREF_CUSTOM_PARAMETER_SETTINGS_CATEGORY);
		mCustomParameterSettingsPref = (SwitchPreference) findPreference(PREF_CUSTOM_PARAMETER_SETTINGS);
		mAntutuParameterSettingsPref = (SwitchPreference) findPreference(PREF_ANTUTU_PARAMETER_SETTINGS);
		mLuDaShiParameterSettingsPref = (SwitchPreference) findPreference(PREF_LUDASHI_PARAMETER_SETTINGS);
		mYouHuaDaShiParameterSettingsPref = (SwitchPreference) findPreference(PREF_YOUHUADASHI_PARAMETER_SETTINGS);

		mCustomParameterSettingsPref.setOnPreferenceChangeListener(this);
		mAntutuParameterSettingsPref.setOnPreferenceChangeListener(this);
		mLuDaShiParameterSettingsPref.setOnPreferenceChangeListener(this);
		mYouHuaDaShiParameterSettingsPref.setOnPreferenceChangeListener(this);
	}
}
