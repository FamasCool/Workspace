package com.android.androidtool.paramsettings.adpater;

import java.util.ArrayList;

import com.android.androidtool.R;
import com.android.androidtool.paramsettings.model.Cpu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CpuSettingsAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Cpu> mList;
	private int mCurrentSelect;

	public CpuSettingsAdapter(Context context, ArrayList<Cpu> list) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			convertView = mInflater.inflate(R.layout.adapter_cpu_settings, parent,
					false);
			holder.mLogo = (ImageView) convertView
					.findViewById(R.id.cpu_logo);
			holder.mType = (TextView) convertView.findViewById(R.id.cpu_type);
			holder.mCore = (TextView) convertView.findViewById(R.id.cpu_core);
			holder.mMinFreq = (TextView) convertView.findViewById(R.id.cpu_low_freq);
			holder.mMaxFreq = (TextView) convertView.findViewById(R.id.cpu_hight_freq);
			holder.mState = (ImageView) convertView.findViewById(R.id.cpu_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Cpu cpu = mList.get(position);
		if (!cpu.isMore) {
			holder.mType.setText(cpu.type);
			holder.mCore.setText(cpu.core);
			holder.mMinFreq.setText(cpu.minFreq);
			holder.mMaxFreq.setText(cpu.maxFreq);
		} else {
			holder.mType.setText(R.string.more);
		}
		if (mCurrentSelect == position) {
			if (!cpu.isMore) {
				holder.mLogo.setImageResource(R.drawable.ic_cpu_logo_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_cpu_logo_more_select);
			}
			holder.mState.setImageResource(R.drawable.ic_state_select);
		} else {
			if (!cpu.isMore) {
				holder.mLogo.setImageResource(R.drawable.ic_cpu_logo_normal);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_cpu_logo_more_normal);
			}
			holder.mState.setImageResource(R.drawable.ic_state_normal);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView mLogo;
		TextView mType;
		TextView mCore;
		TextView mMinFreq;
		TextView mMaxFreq;
		ImageView mState;
	}
}