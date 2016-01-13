package com.android.androidtool.paramsettings.adpater;

import java.util.ArrayList;

import com.android.androidtool.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ParameterSettingsAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<String> mTitleList;
	private ArrayList<Integer> mBackgroundResList;
	
	public ParameterSettingsAdapter(Context context,
			ArrayList<String> titleList,
			ArrayList<Integer> backgroundList) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mTitleList = titleList;
		mBackgroundResList = backgroundList;
	}

	@Override
	public int getCount() {
		return Math.min(mBackgroundResList.size(), mTitleList.size());
	}

	@Override
	public Object getItem(int position) {
		return mTitleList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_parameter_settings, parent, false);
			holder.mTitle = (TextView) convertView.findViewById(R.id.parameter_settings_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mTitle.setText(mTitleList.get(position));
		holder.mTitle.setCompoundDrawablesWithIntrinsicBounds(0, mBackgroundResList.get(position), 0, 0);
		return convertView;
	}

	class ViewHolder {
		TextView mTitle;
	}
}