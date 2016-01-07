package com.android.agingtest;

import com.android.agingtest.OnStateChangedCallback;

interface IAgingService {

	boolean isVibrationTestOn();
	boolean isSpeakerTestOn();
	boolean isEarpieceTestOn();
	void setVibrationEnabled(boolean enabled);
	void setSpeakerTestEnabled(boolean enabled);
	void setEarpieceTestEnabled(boolean enabled);
	void addStateChangedCallback(in OnStateChangedCallback callback);
	void removeStateChangedCallback(in OnStateChangedCallback callback);
}
