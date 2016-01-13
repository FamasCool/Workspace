package com.android.androidtool.paramsettings.model;

public class Cpu {
	public String type;
	public String core;
	public String minFreq;
	public String maxFreq;
	public boolean isMore;
	public int drawableId;

	public Cpu(int drawableId, String type, String core, String min, String max, boolean isMore) {
		this.drawableId = drawableId;
		this.type = type;
		this.core = core;
		this.minFreq = min;
		this.maxFreq = max;
		this.isMore = isMore;
	}

	public boolean equals(Cpu other) {
		if (other != null) {
			if (type.equals(other.type) && core.equals(other.core) && minFreq.equals(other.minFreq)
					&& maxFreq.equals(other.maxFreq)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Type: " + type + " Core: " + core + " Min: " + minFreq + " Max: " + maxFreq + " isMore: " + isMore;
	}
}
