package com.android.androidtool.paramsettings.adpater;

import java.util.ArrayList;

import com.android.androidtool.R;
import com.android.androidtool.paramsettings.model.Other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OtherSettingsAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Other> mList;
	private int mCurrentSelect;

	public OtherSettingsAdapter(Context context, ArrayList<Other> list) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mList = list;
		mCurrentSelect = -1;
	}

	public int getCurrentSelect() {
		return mCurrentSelect;
	}

	public void setCurrentSelectId(int position) {
		mCurrentSelect = position;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
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
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_other_settings, parent,
					false);
			holder.mLogo = (ImageView) convertView.findViewById(R.id.other_logo);
			holder.mScore = (TextView) convertView
					.findViewById(R.id.other_score);
			holder.mRoot = (TextView) convertView.findViewById(R.id.other_root);
			holder.mSensor = (TextView) convertView.findViewById(R.id.other_sensor);
			holder.mState = (ImageView) convertView.findViewById(R.id.other_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Other other = mList.get(position);
		if (!other.isMore) {
			holder.mScore.setText(other.benchmark);
			holder.mRoot.setText((other.supportRoot.equals("true")) ? "是" : "否");
			holder.mSensor.setText((other.supportAllSensor.equals("true")) ? "是" : "否");
			if (mCurrentSelect == position) {
				holder.mLogo.setImageResource(R.drawable.ic_other_logo_select);
				holder.mState.setImageResource(R.drawable.ic_state_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_other_logo_normal);
				holder.mState.setImageResource(R.drawable.ic_state_normal);
			}
		} else {
			holder.mScore.setText(R.string.more);
			holder.mRoot.setText("");
			holder.mSensor.setText("");
			if (mCurrentSelect == position) {
				holder.mLogo.setImageResource(R.drawable.ic_other_logo_select);
				holder.mState.setImageResource(R.drawable.ic_state_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_other_logo_normal);
				holder.mState.setImageResource(R.drawable.ic_state_normal);
			}
		}
		return convertView;
	}

	class ViewHolder {
		ImageView mLogo;
		TextView mScore;
		TextView mRoot;
		TextView mSensor;
		ImageView mState;
	}
}