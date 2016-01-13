package com.android.systemapp.paramsettings.model;

public class Lcd {
	public String width;
	public String height;
	public String density;
	public int drawableId;
	public boolean isMore;

	public Lcd(int drawableId, String width, String height, String dpi, boolean isMore) {
		this.width = width;
		this.height = height;
		this.density = dpi;
		this.drawableId = drawableId;
		this.isMore = isMore;
	}

	public boolean equals(Lcd other) {
		if (other != null) {
			if (width.equals(other.width) && height.equals(other.height) && density.equals(other.density)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "Width: " + width + " Height: " + height + " Dpi: " + density + " isMore: " + isMore;
	}
}
