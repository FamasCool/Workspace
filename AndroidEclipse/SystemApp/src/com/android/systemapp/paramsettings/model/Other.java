package com.android.systemapp.paramsettings.model;

public class Other {
	public String supportAllSensor;
	public String supportRoot;
	public String benchmark;
	public int drawableId;
	public boolean isMore;

	public Other(int drawableId, String sensor, String root, String benchmark, boolean isMore) {
		this.supportAllSensor = sensor;
		this.supportRoot = root;
		this.benchmark = benchmark;
		this.drawableId = drawableId;
		this.isMore = isMore;
	}

	public boolean equals(Other other) {
		if (other != null) {
			if (benchmark.equals(other.benchmark) && supportAllSensor.equals(other.supportAllSensor)
					&& supportRoot.equals(other.supportRoot)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "Sensor: " + supportAllSensor + " Root: " + supportRoot + " Score: " + benchmark + " isMore: " + isMore;
	}

}
