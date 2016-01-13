package com.android.androidtool.paramsettings.adpater;

import java.util.ArrayList;

import com.android.androidtool.R;
import com.android.androidtool.paramsettings.model.Memory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MemorySettingsAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Memory> mList;
	private int mCurrentSelect;
	
	public MemorySettingsAdapter(Context context, ArrayList<Memory> list) {
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
			convertView = mInflater.inflate(R.layout.adapter_memory_setttings, parent,
					false);
			holder.mLogo = (ImageView) convertView.findViewById(R.id.memory_logo);
			holder.mMaxRam = (TextView) convertView
					.findViewById(R.id.memory_max_ram);
			holder.mMinRam = (TextView) convertView.findViewById(R.id.memory_min_ram);
			holder.mState = (ImageView) convertView.findViewById(R.id.memory_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Memory memory = mList.get(position);
		if (!memory.isMore) {
			holder.mMaxRam.setText(memory.maxRam);
			holder.mMinRam.setText(memory.minRam);
			if (mCurrentSelect == position) {
				holder.mLogo.setImageResource(R.drawable.ic_memory_logo_select);
				holder.mState.setImageResource(R.drawable.ic_state_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_memory_logo_normal);
				holder.mState.setImageResource(R.drawable.ic_state_normal);
			}
		} else {
			holder.mMaxRam.setText(R.string.more);
			holder.mMinRam.setText("");
			if (mCurrentSelect == position) {
				holder.mLogo.setImageResource(R.drawable.ic_memory_logo_more_select);
				holder.mState.setImageResource(R.drawable.ic_state_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_memory_logo_more_normal);
				holder.mState.setImageResource(R.drawable.ic_state_normal);
			}
		}
		return convertView;
	}

	class ViewHolder {
		ImageView mLogo;
		TextView mMaxRam;
		TextView mMinRam;
		ImageView mState;
	}
}