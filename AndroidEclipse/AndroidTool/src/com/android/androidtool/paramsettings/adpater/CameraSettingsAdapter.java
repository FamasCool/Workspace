package com.android.androidtool.paramsettings.adpater;

import java.util.ArrayList;

import com.android.androidtool.R;
import com.android.androidtool.paramsettings.model.Camera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CameraSettingsAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Camera> mList;
	private int mCurrentSelect;
	
	public CameraSettingsAdapter(Context context, ArrayList<Camera> list) {
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
			convertView = mInflater.inflate(R.layout.adapter_camera_settings, parent,
					false);
			holder.mLogo = (ImageView) convertView.findViewById(R.id.camera_logo);
			holder.mFrontCamera = (TextView) convertView
					.findViewById(R.id.front_camera_pixel);
			holder.mBackCamera = (TextView) convertView.findViewById(R.id.back_camera_pixel);
			holder.mVideo = (TextView) convertView.findViewById(R.id.video_quality);
			holder.mState = (ImageView) convertView.findViewById(R.id.camera_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Camera camera = mList.get(position);
		if (!camera.isMore) {
			holder.mFrontCamera.setText(camera.frontCameraPixel);
			holder.mBackCamera.setText(camera.backCameraPixel);
			holder.mVideo.setText(camera.videoQuality);
			if (mCurrentSelect == position) {
				holder.mLogo.setImageResource(R.drawable.ic_camera_logo_select);
				holder.mState.setImageResource(R.drawable.ic_state_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_camera_logo_normal);
				holder.mState.setImageResource(R.drawable.ic_state_normal);
			}
		} else {
			holder.mFrontCamera.setText(R.string.more);
			holder.mBackCamera.setText("");
			holder.mVideo.setText("");
			if (mCurrentSelect == position) {
				holder.mLogo.setImageResource(R.drawable.ic_camera_logo_more_select);
				holder.mState.setImageResource(R.drawable.ic_state_select);
			} else {
				holder.mLogo.setImageResource(R.drawable.ic_camera_logo_more_normal);
				holder.mState.setImageResource(R.drawable.ic_state_normal);
			}
		}
		return convertView;
	}

	class ViewHolder {
		ImageView mLogo;
		TextView mFrontCamera;
		TextView mBackCamera;
		TextView mVideo;
		ImageView mState;
	}
}