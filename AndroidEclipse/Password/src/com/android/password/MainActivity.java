package com.android.password;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	private static final int NV_INDEX = 45;
	private static final int INSTALL_APPLICATION_STATE_INDEX = 100;
	private static final int INSTALL_APPLICATION_PASSWORD_INDEX = 101;
	private static final int PASS_WORD_LENGTH = 16;
	
	private TextView mPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPassword = (TextView) findViewById(R.id.password);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mPassword.setText(getPassword());
	}
	
	private String getPassword() {
		String password = "";
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(NV_INDEX);
			 Log.d(TAG, "getPassword=>length: " + (buff != null ? buff.length : " null"));
			if (buff != null && buff.length >= (INSTALL_APPLICATION_PASSWORD_INDEX + PASS_WORD_LENGTH)) {
				byte[] psw = java.util.Arrays.copyOfRange(buff, INSTALL_APPLICATION_PASSWORD_INDEX, INSTALL_APPLICATION_PASSWORD_INDEX + PASS_WORD_LENGTH);
				if (!checkByteArrays(psw)) {
					password = new String(psw);
				}
				Log.d(TAG, "getPassword=>length: " + psw.length + " valuse: " + password);
			}
		} catch (Exception e) {
			Log.e(TAG, "getPassword=>error: ", e);
		}
		return password;
	}
	
	private boolean checkByteArrays(byte[] array) {
		boolean result = true;
		for (int i = 0; i < array.length; i++) {
			Log.d(TAG, "checkByteArrays=>i: " + ((char)array[i]));
			if (array[i] != (byte)'\0') {
				result = false;
			}
		}
		Log.d(TAG, "checkByteArrays=>result: " + result);
		return result;
	}


}
