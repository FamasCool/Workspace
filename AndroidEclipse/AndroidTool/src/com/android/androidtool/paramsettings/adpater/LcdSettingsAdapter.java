package com.android.androidtool.paramsettings.adpater;

import java.util.ArrayList;

import com.android.androidtool.R;
import com.android.androidtool.paramsettings.model.Lcd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LcdSettingsAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Lcd> mList;
	private int mCurrentSelect;

	public LcdSettingsAdapter(Context context, ArrayList<Lcd> list) {
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			convertView = mInflater.inflate(R.layout.adapter_screen_settings, parent, false);
			holder.mLogo = (ImageView) convertView.findViewById(R.id.screen_logo);
			holder.mHeight = (TextView) convertView.findViewById(R.id.screen_height);
			holder.mWidth = (TextView) convertView.findViewById(R.id.screen_width);
			holder.mDpi = (TextView) convertView.findViewById(R.id.screen_dpi);
			holder.mState = (ImageView) convertView.findViewById(R.id.screen_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Lcd lcd = mList.get(position);
		if (!lcd.isMore) {
			holder.mHeight.setText(lcd.height);
			holder.mWidth.setText(lcd.width);
			holder.mDpi.setText(lcd.density);
			if (mCurrentSelect == position) {
				holder.mLogo.setImageResource(R.drawable.ic_screen_logo_select);
				holder.mState.setImageResource(R.drawable.ic_state_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_screen_logo_normal);
				holder.mState.setImageResource(R.drawable.ic_state_normal);
			}
		} else {
			holder.mHeight.setText(R.string.more);
			holder.mWidth.setText("");
			holder.mDpi.setText("");
			if (mCurrentSelect == position) {
				holder.mLogo.setImageResource(R.drawable.ic_screen_logo_more_select);
				holder.mState.setImageResource(R.drawable.ic_state_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_screen_logo_more_normal);
				holder.mState.setImageResource(R.drawable.ic_state_normal);
			}
		}
		return convertView;
	}

	class ViewHolder {
		ImageView mLogo;
		TextView mHeight;
		TextView mWidth;
		TextView mDpi;
		ImageView mState;
	}
}