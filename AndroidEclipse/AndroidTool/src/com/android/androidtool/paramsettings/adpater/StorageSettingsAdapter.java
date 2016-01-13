package com.android.androidtool.paramsettings.adpater;

import java.util.ArrayList;

import com.android.androidtool.R;
import com.android.androidtool.paramsettings.model.Storage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StorageSettingsAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Storage> mList;
	private int mCurrentSelect;

	public StorageSettingsAdapter(Context context, ArrayList<Storage> list) {
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
			convertView = mInflater.inflate(R.layout.adapter_storage_settings, parent,
					false);
			holder.mLogo = (ImageView) convertView.findViewById(R.id.storage_logo);
			holder.mSystemData = (TextView) convertView
					.findViewById(R.id.storage_system_data);
			holder.mInternalData = (TextView) convertView.findViewById(R.id.storage_internal_data);
			holder.mState = (ImageView) convertView.findViewById(R.id.storage_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Storage storage = mList.get(position);
		if (!storage.isMore) {
			holder.mSystemData.setText(storage.systemStorage);
			holder.mInternalData.setText(storage.internalStorage);
			if (mCurrentSelect == position) {
				holder.mLogo.setImageResource(R.drawable.ic_storage_logo_select);
				holder.mState.setImageResource(R.drawable.ic_state_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_storage_logo_normal);
				holder.mState.setImageResource(R.drawable.ic_state_normal);
			}
		} else {
			holder.mSystemData.setText(R.string.more);
			holder.mInternalData.setText("");
			if (mCurrentSelect == position) {
				holder.mLogo.setImageResource(R.drawable.ic_storage_logo_more_select);
				holder.mState.setImageResource(R.drawable.ic_state_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_storage_logo_more_normal);
				holder.mState.setImageResource(R.drawable.ic_state_normal);
			}
		}
		return convertView;
	}

	class ViewHolder {
		ImageView mLogo;
		TextView mSystemData;
		TextView mInternalData;
		ImageView mState;
	}
}