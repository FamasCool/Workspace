package com.android.systemapp.ui;

import com.android.systemapp.Log;

import android.content.Context;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Checkable;
import android.widget.Switch;

public class CustomSwitchPreference extends SwitchPreference implements OnClickListener {
	
	private Switch mSwitch;
	private OnSwicthCheckedChangeListener mSwitchCheckedChangeListener;

	public CustomSwitchPreference(Context context) {
		this(context, null);
	}

	public CustomSwitchPreference(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.switchPreferenceStyle);
	}

	public CustomSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public CustomSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	
	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		
		View checkableView = view.findViewById(com.android.internal.R.id.switchWidget);
        if (checkableView != null && checkableView instanceof Checkable) {
            if (checkableView instanceof Switch) {
            	mSwitch = (Switch) checkableView;
            	mSwitch.setOnClickListener(this);
            }
        }
	}
	
	public void setOnSwitchCheckedChangeListener(OnSwicthCheckedChangeListener l) {
		mSwitchCheckedChangeListener = l;
	}
	
	public interface OnSwicthCheckedChangeListener {
		void onSwitchChange(Preference preference, boolean newValue);
	}
	
	@Override
	protected void onClick() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case com.android.internal.R.id.switchWidget:
			boolean isChecked = mSwitch.isChecked();
			if (mSwitchCheckedChangeListener != null) {
				mSwitchCheckedChangeListener.onSwitchChange(this, isChecked);
			}
			break;
		}
	}

}
