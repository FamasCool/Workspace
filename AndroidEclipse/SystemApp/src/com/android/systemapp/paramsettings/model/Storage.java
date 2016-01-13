package com.android.systemapp.paramsettings.model;

public class Storage {
	public String systemStorage;
	public String internalStorage;
	public int drawableId;
	public boolean isMore;

	public Storage(int drawableId, String systemStorage, String internalStorage, boolean isMore) {
		this.systemStorage = systemStorage;
		this.internalStorage = internalStorage;
		this.drawableId = drawableId;
		this.isMore = isMore;
	}

	public boolean equals(Storage other) {
		if (other != null) {
			if (systemStorage.equals(other.systemStorage) && internalStorage.equals(other.internalStorage)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "system: " + systemStorage + " internal: " + internalStorage + " isMore: " + isMore;
	}
}
