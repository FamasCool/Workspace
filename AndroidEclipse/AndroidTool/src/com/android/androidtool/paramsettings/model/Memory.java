package com.android.androidtool.paramsettings.model;

public class Memory {
	public String maxRam;
	public String minRam;
	public int drawableId;
	public boolean isMore;

	public Memory(int drawableId, String minRam, String maxRam, boolean isMore) {
		this.maxRam = maxRam;
		this.minRam = minRam;
		this.drawableId = drawableId;
		this.isMore = isMore;
	}

	public boolean equals(Memory other) {
		if (other != null) {
			if (minRam.equals(other.minRam) && maxRam.equals(other.maxRam)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Min Ram: " + minRam + " Max Ram: " + maxRam + " isMore: " + isMore;
	}
}
