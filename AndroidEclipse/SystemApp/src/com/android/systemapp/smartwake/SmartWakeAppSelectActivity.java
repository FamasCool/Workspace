package com.android.systemapp.smartwake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.android.systemapp.Log;
import com.android.systemapp.R;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.icu.text.Collator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mediatek.common.featureoption.XunhuOption;

public class SmartWakeAppSelectActivity extends ListActivity implements OnItemClickListener {
	
	private static final boolean ENABLED_SMART_WAKE = XunhuOption.XUNHU_QTY_SMART_WAKE;
	
	private PackageManager mPackageManager;
	private Resources mResources;
	private AppListAdapter mAdapter;
	
	private ArrayList<ActivityInfo> mList;
	private String[] mFilterPackageNames;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(this, "onCreate=>enabled: " + ENABLED_SMART_WAKE);
		if (!ENABLED_SMART_WAKE) {
			finish();
		}
		initValues();
		initActionBar();
		initViews();
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ActivityInfo ai = (ActivityInfo) mAdapter.getItem(position);
		CharSequence label = ai.applicationInfo.loadLabel(mPackageManager);
		if (TextUtils.isEmpty(label)) {
			label = mResources.getString(R.string.smart_wake_unknow_app_name);
		}
		Intent intent = new Intent();
		intent.putExtra("ControlData", mResources.getString(R.string.smart_wake_open_app_summary, label));
		intent.putExtra("ActionData", "3;" + label + ";"
				+ ai.packageName + ";" + ai.name);
		setResult(3, intent);
		finish();
	}
	
	private void initValues() {
		mResources = getResources();
		mPackageManager = getPackageManager();
		mFilterPackageNames = mResources.getStringArray(R.array.filter_package_name);
		mList = getActivityInfoList();
		mAdapter = new AppListAdapter(this, mList);
	}
	
	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.smart_wake_function_select_label);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	private void initViews() {
		getListView().setAdapter(mAdapter);
		getListView().setOnItemClickListener(this);
	}
	
	private ArrayList<ActivityInfo> getActivityInfoList() {
		ArrayList<ActivityInfo> list = new ArrayList<ActivityInfo>();
		try {
			Intent intent = new Intent("android.intent.action.MAIN");
			intent.addCategory("android.intent.category.LAUNCHER");
			Iterator iterator = mPackageManager.queryIntentActivities(intent, 0).iterator();
			while (iterator.hasNext()) {
				ActivityInfo info = ((ResolveInfo) iterator.next()).activityInfo;
				if (!filterApp(info)) {
					list.add(info);
				}
			}
		} catch (Exception e) {
			Log.e(this, "getActivityInfoList=>error: ", e);
		}

		Collections.sort(list, new Comparator<ActivityInfo>() {

			@Override
			public int compare(ActivityInfo lhs, ActivityInfo rhs) {
				Collator collator = Collator.getInstance();
				return collator.getCollationKey(lhs.loadLabel(mPackageManager).toString())
						.compareTo(collator.getCollationKey(rhs.loadLabel(mPackageManager)
						.toString()));
			}
			
		});
		Log.d(this, "getActivityInfoList=>size: " + list.size());
		return list;
	}
	
	private boolean filterApp(ActivityInfo ai) {
		Log.d(this, "filterApp=>packageName: " + (ai == null ? "null" : ai.packageName));
		if (ai != null && ai.packageName != null) {
			for (String packageName : mFilterPackageNames) {
				if (ai.packageName.equals(packageName)) {
					return true;
				}
			}
			return false;
		} else {
			return true;
		}
	}
	
	private class AppListAdapter extends BaseAdapter {
		
		private ArrayList<ActivityInfo> mList;
		private Context mContext;
		private LayoutInflater mInflater;
		
		public AppListAdapter(Context context, ArrayList<ActivityInfo> list) {
			mContext = context;
			mList = list;
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return mList != null ? mList.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.sm_app_select_listview, parent, false);
				holder = new ViewHolder();
				holder.mAppIcon = (ImageView) convertView.findViewById(R.id.app_icon);
				holder.mAppName = (TextView) convertView.findViewById(R.id.app_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			ActivityInfo ai = mList.get(position);
			Drawable icon = ai.applicationInfo.loadIcon(mPackageManager);
			CharSequence name = ai.applicationInfo.loadLabel(mPackageManager);
			if (icon != null) {
				holder.mAppIcon.setImageDrawable(icon);
			} else {
				holder.mAppIcon.setImageResource(R.drawable.ic_launcher);
			}
			
			if (!TextUtils.isEmpty(name)) {
				holder.mAppName.setText(name);
			} else {
				holder.mAppName.setText(R.string.smart_wake_unknow_app_name);
			}
			
			return convertView;
		}
		
		class ViewHolder {
			ImageView mAppIcon;
			TextView mAppName;
		}
		
	}

}
