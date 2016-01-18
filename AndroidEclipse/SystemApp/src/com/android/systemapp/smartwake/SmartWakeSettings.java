package com.android.systemapp.smartwake;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.android.systemapp.Log;
import com.android.systemapp.R;
import com.android.systemapp.ui.CustomSwitchPreference;
import com.android.systemapp.ui.CustomSwitchPreference.OnSwicthCheckedChangeListener;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Switch;

import com.mediatek.common.featureoption.XunhuOption;

public class SmartWakeSettings extends PreferenceActivity implements OnClickListener, OnSwicthCheckedChangeListener {

	private static final boolean ENABLED_SMART_WAKE = XunhuOption.XUNHU_QTY_SMART_WAKE;
	private static final String SMART_WAKE_NODE_NAME = "/proc/gesturectrl";

	private static final String PREF_SMART_WAKE_GENERAL = "smart_wake_general_category";
	private static final String PREF_SMART_WAKE_DOUBLE_CLICK_WAKE_SCREEN = "smart_wake_double_click_wake_screen_key";
	private static final String PREF_SMART_WAKE_GESTURE = "smart_wake_gesture_category";
	private static final String PREF_SMART_WAKE_C = "smart_wake_c_key";
	private static final String PREF_SMART_WAKE_E = "smart_wake_e_key";
	private static final String PREF_SMART_WAKE_M = "smart_wake_m_key";
	private static final String PREF_SMART_WAKE_O = "smart_wake_o_key";
	private static final String PREF_SMART_WAKE_S = "smart_wake_s_key";
	private static final String PREF_SMART_WAKE_V = "smark_wake_v_key";
	private static final String PREF_SMART_WAKE_W = "smart_wake_w_key";
	private static final String PREF_SMART_WAKE_Z = "smart_wake_z_key";
	private static final String PREF_SMART_WAKE_UP = "smart_wake_up_key";
	private static final String PREF_SMART_WAKE_DOWN = "smart_wake_down_key";
	private static final String PREF_SMART_WAKE_LEFT = "smart_wake_left_key";
	private static final String PREF_SMART_WAKE_RIGHT = "smart_wake_right_key";
	private static final String PREF_SMART_WAKE_TWO_LINE = "smart_wake_two_line_key";
	private static final String PREF_SMART_WAKE_DOUBLE_CLICK = "smart_wake_double_click_key";

	private static final String KEY_SMART_WAKE = "smart_wake_setting";
	private static final String KEY_SMART_WAKE_C = "smart_wake_c_setting";
	private static final String KEY_SMART_WAKE_E = "smart_wake_e_setting";
	private static final String KEY_SMART_WAKE_M = "smart_wake_m_setting";
	private static final String KEY_SMART_WAKE_O = "smart_wake_o_setting";
	private static final String KEY_SMART_WAKE_S = "smart_wake_s_setting";
	private static final String KEY_SMART_WAKE_V = "smart_wake_v_setting";
	private static final String KEY_SMART_WAKE_W = "smart_wake_w_setting";
	private static final String KEY_SMART_WAKE_Z = "smart_wake_z_setting";
	private static final String KEY_SMART_WAKE_UP = "smart_wake_up_setting";
	private static final String KEY_SMART_WAKE_DOWN = "smart_wake_down_setting";
	private static final String KEY_SMART_WAKE_LEFT = "smart_wake_left_setting";
	private static final String KEY_SMART_WAKE_RIGHT = "smart_wake_right_setting";
	private static final String KEY_SMART_WAKE_TWO_LINE = "smart_wake_two_line_setting";
	private static final String KEY_SMART_WAKE_DOUBLE_CLICK = "smart_wake_double_click_setting";

	private static final String KEY_SMART_WAKE_C_ACTION = "smart_wake_c_action";
	private static final String KEY_SMART_WAKE_E_ACTION = "smart_wake_e_action";
	private static final String KEY_SMART_WAKE_M_ACTION = "smart_wake_m_action";
	private static final String KEY_SMART_WAKE_O_ACTION = "smart_wake_o_action";
	private static final String KEY_SMART_WAKE_S_ACTION = "smart_wake_s_action";
	private static final String KEY_SMART_WAKE_V_ACTION = "smart_wake_v_action";
	private static final String KEY_SMART_WAKE_W_ACTION = "smart_wake_w_action";
	private static final String KEY_SMART_WAKE_Z_ACTION = "smart_wake_z_action";
	private static final String KEY_SMART_WAKE_UP_ACTION = "smart_wake_up_action";
	private static final String KEY_SMART_WAKE_DOWN_ACTION = "smart_wake_down_action";
	private static final String KEY_SMART_WAKE_LEFT_ACTION = "smart_wake_left_action";
	private static final String KEY_SMART_WAKE_RIGHT_ACTION = "smart_wake_right_action";
	private static final String KEY_SMART_WAKE_TWO_LINE_ACTION = "smart_wake_two_line_action";
	private static final String KEY_SMART_WAKE_DOUBLE_CLICK_ACTION = "smart_wake_double_click_action";

	private Switch mSmartWakeSwitch;
	private CustomSwitchPreference mSmartWakeDoubleClickWakeScreenPreference;
	private CustomSwitchPreference mSmartWakeCPreference;
	private CustomSwitchPreference mSmartWakeEPreference;
	private CustomSwitchPreference mSmartWakeMPreference;
	private CustomSwitchPreference mSmartWakeOPreference;
	private CustomSwitchPreference mSmartWakeSPreference;
	private CustomSwitchPreference mSmartWakeVPreference;
	private CustomSwitchPreference mSmartWakeWPreference;
	private CustomSwitchPreference mSmartWakeZPreference;
	private CustomSwitchPreference mSmartWakeUpPreference;
	private CustomSwitchPreference mSmartWakeDownPreference;
	private CustomSwitchPreference mSmartWakeLeftPreference;
	private CustomSwitchPreference mSmartWakeRightPreference;
	private CustomSwitchPreference mSmartWakeTwoLinePreference;
	private CustomSwitchPreference mSmartWakeDoubleClickPreference;

	private Resources mResources;
	private ContentResolver mContentResolver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(this, "onCreate=>enabled: " + ENABLED_SMART_WAKE);
		if (!ENABLED_SMART_WAKE) {
			finish();
		}
		initValues();
		initActionBar();
		addPreferencesFromResource(R.xml.sm_settings_preference);
		initPreferences();
		updatePreferenceVisibility();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateSwitchState();
		updatePreferencesState();
		updatePreferencesSummary();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(this, "onActivityResult=>requestCode: " + requestCode + " resultCode: " + resultCode + " data: " + data);
		if (null == data) {
			super.onActivityResult(requestCode, resultCode, data);
			return;
		}

		String controlStr = data.getExtras().getString("ControlData");
		String actionStr = data.getExtras().getString("ActionData");
		Log.d(this, "onActivityResult=>controlStr: " + controlStr + " actionStr: " + actionStr);

		if (resultCode == 1) {
			mSmartWakeCPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_C_ACTION, actionStr);
		} else if (resultCode == 2) {
			mSmartWakeEPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_E_ACTION, actionStr);
		} else if (resultCode == 3) {
			mSmartWakeMPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_M_ACTION, actionStr);
		} else if (resultCode == 4) {
			mSmartWakeOPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_O_ACTION, actionStr);
		} else if (resultCode == 5) {
			mSmartWakeSPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_S_ACTION, actionStr);
		} else if (resultCode == 6) {
			mSmartWakeVPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_V_ACTION, actionStr);
		} else if (resultCode == 7) {
			mSmartWakeWPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_W_ACTION, actionStr);
		} else if (resultCode == 8) {
			mSmartWakeZPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_Z_ACTION, actionStr);
		} else if (resultCode == 9) {
			mSmartWakeUpPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_UP_ACTION, actionStr);
		} else if (resultCode == 10) {
			mSmartWakeDownPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_DOWN_ACTION, actionStr);
		} else if (resultCode == 11) {
			mSmartWakeLeftPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_LEFT_ACTION, actionStr);
		} else if (resultCode == 12) {
			mSmartWakeRightPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_RIGHT_ACTION, actionStr);
		} else if (resultCode == 13) {
			mSmartWakeTwoLinePreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_TWO_LINE_ACTION, actionStr);
		} else if (resultCode == 14) {
			mSmartWakeDoubleClickPreference.setSummary(controlStr);
			setSmartWakeActionToSettings(KEY_SMART_WAKE_DOUBLE_CLICK_ACTION, actionStr);
		}
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		Log.d(this, "onPreferenceTreeClick()...");
		Intent intent = new Intent(this, SmartWakeFunctionSelectActivity.class);
		if (preference == mSmartWakeCPreference) {
			intent.putExtra("type", 1);
		} else if (preference == mSmartWakeEPreference) {
			intent.putExtra("type", 2);
		} else if (preference == mSmartWakeMPreference) {
			intent.putExtra("type", 3);
		} else if (preference == mSmartWakeOPreference) {
			intent.putExtra("type", 4);
		} else if (preference == mSmartWakeSPreference) {
			intent.putExtra("type", 5);
		} else if (preference == mSmartWakeVPreference) {
			intent.putExtra("type", 6);
		} else if (preference == mSmartWakeWPreference) {
			intent.putExtra("type", 7);
		} else if (preference == mSmartWakeZPreference) {
			intent.putExtra("type", 8);
		} else if (preference == mSmartWakeUpPreference) {
			intent.putExtra("type", 9);
		} else if (preference == mSmartWakeDownPreference) {
			intent.putExtra("type", 10);
		} else if (preference == mSmartWakeLeftPreference) {
			intent.putExtra("type", 11);
		} else if (preference == mSmartWakeRightPreference) {
			intent.putExtra("type", 12);
		} else if (preference == mSmartWakeTwoLinePreference) {
			intent.putExtra("type", 13);
		} else if (preference == mSmartWakeDoubleClickPreference) {
			intent.putExtra("type", 14);
		}

		if (preference == mSmartWakeDoubleClickWakeScreenPreference) {
			boolean isChecked = mSmartWakeDoubleClickWakeScreenPreference.isChecked();
			setSmartWakeSettingsState(KEY_SMART_WAKE_DOUBLE_CLICK, !isChecked);
			mSmartWakeDoubleClickWakeScreenPreference.setChecked(!isChecked);
		} else {
			startActivityForResult(intent, 0);
		}
		return true;
	}

	@Override
	public void onSwitchChange(Preference preference, boolean newValue) {
		Log.d(this, "onSwitchChange=>preference: " + preference + " newValue: " + newValue);
		if (preference == mSmartWakeCPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_C, newValue);
		} else if (preference == mSmartWakeEPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_E, newValue);
		} else if (preference == mSmartWakeMPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_M, newValue);
		} else if (preference == mSmartWakeOPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_O, newValue);
		} else if (preference == mSmartWakeSPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_S, newValue);
		} else if (preference == mSmartWakeVPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_V, newValue);
		} else if (preference == mSmartWakeWPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_W, newValue);
		} else if (preference == mSmartWakeZPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_Z, newValue);
		} else if (preference == mSmartWakeUpPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_UP, newValue);
		} else if (preference == mSmartWakeDownPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_DOWN, newValue);
		} else if (preference == mSmartWakeLeftPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_LEFT, newValue);
		} else if (preference == mSmartWakeRightPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_RIGHT, newValue);
		} else if (preference == mSmartWakeTwoLinePreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_TWO_LINE, newValue);
		} else if (preference == mSmartWakeDoubleClickPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_DOUBLE_CLICK, newValue);
		} else if (preference == mSmartWakeDoubleClickWakeScreenPreference) {
			setSmartWakeSettingsState(KEY_SMART_WAKE_DOUBLE_CLICK, newValue);
		}
	}

	@Override
	public void onClick(View v) {
		Log.d(this, "onClick()...");
		switch (v.getId()) {
		case R.id.imageswitch:
			boolean isChecked = mSmartWakeSwitch.isChecked();
			setSmartWakeSettingsState(KEY_SMART_WAKE, isChecked);
			updateSwitchState();
			break;
		}
	}

	private void initValues() {
		mResources = getResources();
		mContentResolver = getContentResolver();
	}

	private void initActionBar() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mSmartWakeSwitch = (Switch) inflater.inflate(R.layout.imageswitch_layout, null);
		mSmartWakeSwitch.setOnClickListener(this);
		final int padding = getResources().getDimensionPixelSize(R.dimen.action_bar_switch_padding);
		mSmartWakeSwitch.setPaddingRelative(0, 0, padding, 0);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(mSmartWakeSwitch, new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.END));
	}

	private void initPreferences() {
		mSmartWakeDoubleClickWakeScreenPreference = (CustomSwitchPreference) findPreference(
				PREF_SMART_WAKE_DOUBLE_CLICK_WAKE_SCREEN);
		mSmartWakeCPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_C);
		mSmartWakeEPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_E);
		mSmartWakeMPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_M);
		mSmartWakeOPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_O);
		mSmartWakeSPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_S);
		mSmartWakeVPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_V);
		mSmartWakeWPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_W);
		mSmartWakeZPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_Z);
		mSmartWakeUpPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_UP);
		mSmartWakeDownPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_DOWN);
		mSmartWakeLeftPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_LEFT);
		mSmartWakeRightPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_RIGHT);
		mSmartWakeTwoLinePreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_TWO_LINE);
		mSmartWakeDoubleClickPreference = (CustomSwitchPreference) findPreference(PREF_SMART_WAKE_DOUBLE_CLICK);

		mSmartWakeDoubleClickWakeScreenPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeCPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeEPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeMPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeOPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeSPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeVPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeWPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeZPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeUpPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeDownPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeLeftPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeRightPreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeTwoLinePreference.setOnSwitchCheckedChangeListener(this);
		mSmartWakeDoubleClickPreference.setOnSwitchCheckedChangeListener(this);
	}

	private void updatePreferenceVisibility() {
		PreferenceScreen ps = getPreferenceScreen();
		PreferenceCategory general = (PreferenceCategory) findPreference(PREF_SMART_WAKE_GENERAL);
		PreferenceCategory gesture = (PreferenceCategory) findPreference(PREF_SMART_WAKE_GESTURE);
		int removeGestureCount = 0;

		if (!mResources.getBoolean(R.bool.smart_wake_double_click_wake_screen_visible)) {
			general.removePreference(mSmartWakeDoubleClickWakeScreenPreference);
			ps.removePreference(gesture);
		}

		if (!mResources.getBoolean(R.bool.smart_wake_c_visible)) {
			gesture.removePreference(mSmartWakeCPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_e_visible)) {
			gesture.removePreference(mSmartWakeEPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_m_visible)) {
			gesture.removePreference(mSmartWakeMPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_o_visible)) {
			gesture.removePreference(mSmartWakeOPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_s_visible)) {
			gesture.removePreference(mSmartWakeSPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_v_visible)) {
			gesture.removePreference(mSmartWakeVPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_w_visible)) {
			gesture.removePreference(mSmartWakeWPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_z_visible)) {
			gesture.removePreference(mSmartWakeZPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_up_visible)) {
			gesture.removePreference(mSmartWakeUpPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_down_visible)) {
			gesture.removePreference(mSmartWakeDownPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_left_visible)) {
			gesture.removePreference(mSmartWakeLeftPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_right_visible)) {
			gesture.removePreference(mSmartWakeRightPreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_two_line_visible)) {
			gesture.removePreference(mSmartWakeTwoLinePreference);
			removeGestureCount++;
		}

		if (!mResources.getBoolean(R.bool.smart_wake_double_click_visible)) {
			gesture.removePreference(mSmartWakeDoubleClickPreference);
			removeGestureCount++;
		}

		if (removeGestureCount == 14) {
			ps.removePreference(gesture);
		}
	}

	private void updateSwitchState() {
		String result = getNodeInfo();
		boolean enabled = getSmartWakeSettingsState(KEY_SMART_WAKE);
		Log.d(this, "updateSwitchState=>result: " + result + " enabled: " + enabled);
		if (enabled) {
			if (!result.equals("1")) {
				boolean value = setNodeInfo(enabled);
				if (!value) {
					setSmartWakeSettingsState(KEY_SMART_WAKE, false);
				}
			}
		} else {
			if (result.equals("1")) {
				boolean value = setNodeInfo(enabled);
				if (!value) {
					setSmartWakeSettingsState(KEY_SMART_WAKE, true);
				}
			}
		}
		enabled = getSmartWakeSettingsState(KEY_SMART_WAKE);
		mSmartWakeSwitch.setChecked(enabled);
		updatePreferenceEnabled(enabled);
	}

	private void updatePreferencesState() {
		if (mSmartWakeDoubleClickWakeScreenPreference != null) {
			mSmartWakeDoubleClickWakeScreenPreference
					.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_DOUBLE_CLICK));
		}

		if (mSmartWakeCPreference != null) {
			mSmartWakeCPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_C));
		}

		if (mSmartWakeEPreference != null) {
			mSmartWakeEPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_E));
		}

		if (mSmartWakeMPreference != null) {
			mSmartWakeMPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_M));
		}

		if (mSmartWakeOPreference != null) {
			mSmartWakeOPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_O));
		}

		if (mSmartWakeSPreference != null) {
			mSmartWakeSPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_S));
		}

		if (mSmartWakeVPreference != null) {
			mSmartWakeVPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_V));
		}

		if (mSmartWakeWPreference != null) {
			mSmartWakeWPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_W));
		}

		if (mSmartWakeZPreference != null) {
			mSmartWakeZPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_Z));
		}

		if (mSmartWakeUpPreference != null) {
			mSmartWakeUpPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_UP));
		}

		if (mSmartWakeDownPreference != null) {
			mSmartWakeDownPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_DOWN));
		}

		if (mSmartWakeLeftPreference != null) {
			mSmartWakeLeftPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_LEFT));
		}

		if (mSmartWakeRightPreference != null) {
			mSmartWakeRightPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_RIGHT));
		}

		if (mSmartWakeTwoLinePreference != null) {
			mSmartWakeTwoLinePreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_TWO_LINE));
		}

		if (mSmartWakeDoubleClickPreference != null) {
			mSmartWakeDoubleClickPreference.setChecked(getSmartWakeSettingsState(KEY_SMART_WAKE_DOUBLE_CLICK));
		}
	}

	private void updatePreferencesSummary() {
		if (mSmartWakeCPreference != null) {
			mSmartWakeCPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_C_ACTION));
		}

		if (mSmartWakeEPreference != null) {
			mSmartWakeEPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_E_ACTION));
		}

		if (mSmartWakeMPreference != null) {
			mSmartWakeMPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_M_ACTION));
		}

		if (mSmartWakeOPreference != null) {
			mSmartWakeOPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_O_ACTION));
		}

		if (mSmartWakeSPreference != null) {
			mSmartWakeSPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_S_ACTION));
		}

		if (mSmartWakeVPreference != null) {
			mSmartWakeVPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_V_ACTION));
		}

		if (mSmartWakeWPreference != null) {
			mSmartWakeWPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_W_ACTION));
		}

		if (mSmartWakeZPreference != null) {
			mSmartWakeZPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_Z_ACTION));
		}

		if (mSmartWakeUpPreference != null) {
			mSmartWakeUpPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_UP_ACTION));
		}

		if (mSmartWakeDownPreference != null) {
			mSmartWakeDownPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_DOWN_ACTION));
		}

		if (mSmartWakeLeftPreference != null) {
			mSmartWakeLeftPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_LEFT_ACTION));
		}

		if (mSmartWakeRightPreference != null) {
			mSmartWakeRightPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_RIGHT_ACTION));
		}

		if (mSmartWakeTwoLinePreference != null) {
			mSmartWakeTwoLinePreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_TWO_LINE_ACTION));
		}

		if (mSmartWakeDoubleClickPreference != null) {
			mSmartWakeDoubleClickPreference.setSummary(getPreferencesSummary(KEY_SMART_WAKE_DOUBLE_CLICK_ACTION));
		}
	}

	private void updatePreferenceEnabled(boolean enabled) {
		Log.d(this, "updatePreferenceEnabled=>enabled: " + enabled);
		if (mSmartWakeDoubleClickWakeScreenPreference != null) {
			mSmartWakeDoubleClickWakeScreenPreference.setEnabled(enabled);
		}

		if (mSmartWakeCPreference != null) {
			mSmartWakeCPreference.setEnabled(enabled);
		}

		if (mSmartWakeEPreference != null) {
			mSmartWakeEPreference.setEnabled(enabled);
		}

		if (mSmartWakeMPreference != null) {
			mSmartWakeMPreference.setEnabled(enabled);
		}

		if (mSmartWakeMPreference != null) {
			mSmartWakeMPreference.setEnabled(enabled);
		}

		if (mSmartWakeOPreference != null) {
			mSmartWakeOPreference.setEnabled(enabled);
		}

		if (mSmartWakeSPreference != null) {
			mSmartWakeSPreference.setEnabled(enabled);
		}

		if (mSmartWakeVPreference != null) {
			mSmartWakeVPreference.setEnabled(enabled);
		}

		if (mSmartWakeWPreference != null) {
			mSmartWakeWPreference.setEnabled(enabled);
		}

		if (mSmartWakeZPreference != null) {
			mSmartWakeZPreference.setEnabled(enabled);
		}

		if (mSmartWakeUpPreference != null) {
			mSmartWakeUpPreference.setEnabled(enabled);
		}

		if (mSmartWakeDownPreference != null) {
			mSmartWakeDownPreference.setEnabled(enabled);
		}

		if (mSmartWakeLeftPreference != null) {
			mSmartWakeLeftPreference.setEnabled(enabled);
		}

		if (mSmartWakeRightPreference != null) {
			mSmartWakeRightPreference.setEnabled(enabled);
		}

		if (mSmartWakeTwoLinePreference != null) {
			mSmartWakeTwoLinePreference.setEnabled(enabled);
		}

		if (mSmartWakeDoubleClickPreference != null) {
			mSmartWakeDoubleClickPreference.setEnabled(enabled);
		}

		if (!enabled) {
			if (!mResources.getBoolean(R.bool.smart_wake_enabled_store_preferences_state)) {
				setSmartWakeSettingsState(KEY_SMART_WAKE_C, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_E, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_M, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_O, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_S, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_V, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_W, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_Z, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_UP, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_DOWN, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_LEFT, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_RIGHT, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_TWO_LINE, enabled);
				setSmartWakeSettingsState(KEY_SMART_WAKE_DOUBLE_CLICK, enabled);
				updatePreferencesState();
			}
		}
	}

	private String getNodeInfo() {
		char[] result = new char[] { 0 };
		File awakeTimeFile = new File(SMART_WAKE_NODE_NAME);
		FileReader fr = null;
		try {
			fr = new FileReader(awakeTimeFile);
			fr.read(result);
		} catch (IOException e) {
			Log.d(this, "getNodeInfo=>error: ", e);
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e) {
			}
		}
		Log.d(this, "getNodeInfo=>result: " + String.valueOf(result));
		return String.valueOf(result);
	}

	private boolean setNodeInfo(boolean enabled) {
		boolean flag = false;
		File awakeTimeFile = new File(SMART_WAKE_NODE_NAME);
		FileWriter fr = null;
		try {
			fr = new FileWriter(awakeTimeFile);
			if (enabled) {
				fr.write("1");
			} else {
				fr.write("0");
			}
			fr.close();
			flag = true;
		} catch (IOException e) {
			Log.d(this, "setNodeInfo=>error: ", e.getMessage());
			flag = false;
		}
		Log.d(this, "setNodeInfo=>enabled" + enabled + " state: " + getNodeInfo());
		return flag;
	}

	private boolean getSmartWakeSettingsState(String key) {
		boolean result = false;
		int value = Settings.System.getInt(mContentResolver, key, 0);
		if (value == 1) {
			result = true;
		}
		Log.d(this, "getSmartWakeSettingsState=>key: " + key + " value: " + value + " result: " + result);
		return result;
	}

	private void setSmartWakeSettingsState(String key, boolean enabled) {
		if (enabled) {
			Settings.System.putInt(mContentResolver, key, 1);
		} else {
			Settings.System.putInt(mContentResolver, key, 0);
		}
		Log.d(this, "setSmartWakeSettingsState=>key: " + key + " enabled: " + enabled + " result: "
				+ Settings.System.getInt(mContentResolver, key, 0));
	}

	public String getPreferencesSummary(String key) {
		String result = Settings.System.getString(mContentResolver, key);
		if (TextUtils.isEmpty(result)) {
			Log.e(this, "getPreferencesSummary=>result is null or empty string.");
			return "";
		}

		String[] functions = result.split(";");
		if (functions[0].equals("1")) {
			result = getResources().getString(R.string.smart_wake_call_summary, functions[1]);
		} else if (functions[0].equals("2")) {
			result = getResources().getString(R.string.smart_wake_open_message_summary);
		} else if (functions[0].equals("3")) {
			result = getResources().getString(R.string.smart_wake_open_app_summary, functions[1]);
		} else if (functions[0].equals("4")) {
			result = getResources().getString(R.string.smart_wake_control_music_summary);
		} else if (functions[0].equals("5")) {
			result = getResources().getString(R.string.smart_wake_unluck_screen_summary);
		} else if (functions[0].equals("6")) {
			result = getResources().getString(R.string.smart_wake_wake_screen_summary);
		} else if (functions[0].equals("7")) {
			result = getResources().getString(R.string.smart_wake_play_pre_music_summary);
		} else if (functions[0].equals("8")) {
			result = getResources().getString(R.string.smart_wake_play_next_music_summary);
		}
		Log.d(this, "getPreferencesSummary=>key: " + key + " result: " + result + " action: " + functions[0]);
		return result;
	}

	public void setSmartWakeActionToSettings(String key, String action) {
		Settings.System.putString(mContentResolver, key, action);
	}
}
