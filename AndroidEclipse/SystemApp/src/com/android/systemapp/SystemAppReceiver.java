package com.android.systemapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class SystemAppReceiver extends BroadcastReceiver {

	private static final String ACTION_EXTRA = "action";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = (intent != null ? intent.getAction() : null);
		Log.d(this, "onReceive=>action: " + action);
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			startService(context, action);
		}
	}

	private void startService(Context context, String action) {
		Log.d(this, "startService=>action: " + action);
		Intent service = new Intent(context, SystemAppService.class);
		service.putExtra(ACTION_EXTRA, action);
		context.startService(service);
	}
}
