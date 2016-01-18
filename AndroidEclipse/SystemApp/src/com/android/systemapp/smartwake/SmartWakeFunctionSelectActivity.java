package com.android.systemapp.smartwake;

import com.android.systemapp.Log;
import com.android.systemapp.R;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.ContactsContract;
import android.view.MenuItem;

import com.mediatek.common.featureoption.XunhuOption;

public class SmartWakeFunctionSelectActivity extends PreferenceActivity {
	
	private static final boolean ENABLED_SMART_WAKE = XunhuOption.XUNHU_QTY_SMART_WAKE;
	
	private static final String PREF_SMRAT_WAKE_CALL = "smart_wake_call_key";
	private static final String PREF_SMART_WAKE_OPEN_MESSAGE_APP = "smart_wake_open_message_app_key";
	private static final String PREF_SMART_WAKE_OPEN_APP = "smart_wake_open_app_key";
	private static final String PREF_SMART_WAKE_UNLOCK_SCREEN = "smart_wake_unlock_screen_key";
	private static final String PREF_SMART_WAKE_WAKE_SCREEN = "smart_wake_wake_screen_key";
	private static final String PREF_SMART_WAKE_CONTROL_MUSIC = "smart_wake_control_music_key";
	private static final String PREF_SMART_WAKE_PLAY_PREVIOUS_MUSIC = "smart_wake_play_previous_music_key";
	private static final String PREF_SMART_WAKE_PLAY_NEXT_MUSIC = "smart_wake_play_next_music_key";
	
	private Preference mCallPreference;
	private Preference mOpenMessageAppPreference;
	private Preference mOpenAppPreference;
	private Preference mUnlockScreenPreference;
	private Preference mWakeScreenPreference;
	private Preference mControlMusicPreference;
	private Preference mPlayPreviousMusicPreference;
	private Preference mPlayNextMusicPreference;
	
	private Resources mResources;
	
	private String mFunctionName;
	private int mFunctionIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(this, "onCreate=>enabled: " + ENABLED_SMART_WAKE);
		if (!ENABLED_SMART_WAKE) {
			finish();
		}
		initValues();
		initActionBar();
		addPreferencesFromResource(R.xml.sm_function_select_preference);
		initPreferences();
		updatePreferenceVisibility();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()== android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		if (preference == mCallPreference) {
			mFunctionIndex = 1;
			mFunctionName = getResources().getString(
					R.string.smart_wake_call_title);
			startActivityForResult(new Intent(Intent.ACTION_PICK,
					ContactsContract.Contacts.CONTENT_URI), 1);
		} else if (preference == mOpenMessageAppPreference) {
			mFunctionIndex = 2;
			Intent i = new Intent();
			i.putExtra("ControlData",
					getResources().getString(R.string.smart_wake_open_message_app_title));
			i.putExtra("ActionData", "2;");
			setResult(getIntent().getExtras().getInt("type"), i);
			finish();
		} else if (preference == mOpenAppPreference) {
			mFunctionIndex = 3;
			startActivityForResult(new Intent(this, SmartWakeAppSelectActivity.class), 3);
		} else if (preference == mUnlockScreenPreference) {
			mFunctionIndex = 4;
			Intent i = new Intent();
			i.putExtra("ControlData", getResources().getString(
							R.string.smart_wake_unlock_screen_title));
			i.putExtra("ActionData", "4;");
			setResult(getIntent().getExtras().getInt("type"), i);
			finish();
		} else if (preference == mWakeScreenPreference) {
			mFunctionIndex = 5;
			Intent i = new Intent();
			i.putExtra("ControlData", getResources().getString(
							R.string.smart_wake_wake_screen_title));
			i.putExtra("ActionData", "5;");
			setResult(getIntent().getExtras().getInt("type"), i);
			finish();
		} else if (preference == mControlMusicPreference) {
			mFunctionIndex = 6;
			Intent i = new Intent();
			i.putExtra("ControlData",getResources().getString(
							R.string.smart_wake_control_music_title));
			i.putExtra("ActionData", "6;");
			setResult(getIntent().getExtras().getInt("type"), i);
			finish();
		} else if (preference == mPlayPreviousMusicPreference) {
			mFunctionIndex = 7;
			Intent i = new Intent();
			i.putExtra("ControlData", getResources().getString(
							R.string.smart_wake_play_previous_music_title));
			i.putExtra("ActionData", "7;");
			setResult(getIntent().getExtras().getInt("type"), i);
			finish();
		} else if (preference == mPlayNextMusicPreference) {
			mFunctionIndex = 8;
			Intent i = new Intent();
			i.putExtra("ControlData",getResources().getString(
							R.string.smart_wake_play_next_music_title));
			i.putExtra("ActionData", "8;");
			setResult(getIntent().getExtras().getInt("type"), i);
			finish();
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(this, "onActivityResult=>resultCode: " + resultCode + " requestCode: " + requestCode + " data: " + data);
		if (data != null) {
			if (requestCode == 3) {
				setResult(getIntent().getExtras().getInt("type"), data);
				finish();
			} else if (requestCode == 1) {
				String[] actions = getActionsFromIntent(data);
				Log.d(this, "onActivityResult=>data: " + data.toString() + " actions: " + actions);
				if (null == actions) {
					mFunctionName = null;
					return;
				}

				Intent i = new Intent();
				i.putExtra("ControlData", mFunctionName + "  " + actions[0]);
				i.putExtra("ActionData", mFunctionIndex + ";" + actions[0] + ";"
						+ actions[1]);
				setResult(getIntent().getExtras().getInt("type"), i);
				finish();
			}
		}
	}
	
	private void initValues() {
		mResources = getResources();
	}
	
	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.smart_wake_function_select_label);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void initPreferences() {
		mCallPreference = findPreference(PREF_SMRAT_WAKE_CALL);
		mOpenMessageAppPreference = findPreference(PREF_SMART_WAKE_OPEN_MESSAGE_APP);
		mOpenAppPreference = findPreference(PREF_SMART_WAKE_OPEN_APP);
		mUnlockScreenPreference = findPreference(PREF_SMART_WAKE_UNLOCK_SCREEN);
		mWakeScreenPreference = findPreference(PREF_SMART_WAKE_WAKE_SCREEN);
		mControlMusicPreference = findPreference(PREF_SMART_WAKE_CONTROL_MUSIC);
		mPlayPreviousMusicPreference = findPreference(PREF_SMART_WAKE_PLAY_PREVIOUS_MUSIC);
		mPlayNextMusicPreference = findPreference(PREF_SMART_WAKE_PLAY_NEXT_MUSIC);
	}
	
	private void updatePreferenceVisibility() {
		PreferenceScreen ps = getPreferenceScreen();
		
		if (!mResources.getBoolean(R.bool.smart_wake_call_function_visible)) {
			ps.removePreference(mCallPreference);
		}
		
		if (!mResources.getBoolean(R.bool.smart_wake_open_message_app_function_visible)) {
			ps.removePreference(mOpenMessageAppPreference);
		}
		
		if (!mResources.getBoolean(R.bool.smart_wake_open_app_function_visible)) {
			ps.removePreference(mOpenAppPreference);
		}
		
		if (!mResources.getBoolean(R.bool.smart_wake_unlock_screen_function_visible)) {
			ps.removePreference(mUnlockScreenPreference);
		}
		
		if (!mResources.getBoolean(R.bool.smart_wake_wake_screen_function_visible)) {
			ps.removePreference(mWakeScreenPreference);
		}
		
		if (!mResources.getBoolean(R.bool.smart_wake_control_music_function_visible)) {
			ps.removePreference(mControlMusicPreference);
		}
		
		if (!mResources.getBoolean(R.bool.smart_wake_play_previous_music_function_visible)) {
			ps.removePreference(mPlayPreviousMusicPreference);
		}
		
		if (!mResources.getBoolean(R.bool.smart_wake_play_next_music_function_visible)) {
			ps.removePreference(mPlayNextMusicPreference);
		}
	}
	
	private String[] getActionsFromIntent(Intent intent) {
		String number = null;
		Cursor cursor1 = null;
		Cursor cursor2 = null;
		Uri contactsUri = intent.getData();
		String[] actions = null;
		if (contactsUri == null) {
			actions = null;
			Log.d(this, "getActionsFromIntent=>contactsUri is null.");
		} else {
			try {
				ContentResolver resolver = getContentResolver();
				cursor1 = resolver.query(contactsUri, null, null, null, null);
				cursor1.moveToFirst();
				String name = cursor1.getString(cursor1
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String contactId = cursor1.getString(cursor1
						.getColumnIndex(ContactsContract.Contacts._ID));
				cursor2 = resolver.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, "contact_id = " + contactId, null, null);
				while (cursor2.moveToNext()) {
					number = cursor2.getString(cursor2
							.getColumnIndex("data1"));
				}
				actions = new String[2];
				actions[0] = (name + "/" + number);
				actions[1] = number;
			} catch (Exception e) {
				actions = null;
				e.printStackTrace();
				Log.d(this, "getActionsFromIntent=>error: ", e);
			} finally {
				if (cursor1 != null) {
					cursor1.close();
				}
				if (cursor2 != null) {
					cursor2.close();
				}
			}
		}
		return actions;
	}
}
