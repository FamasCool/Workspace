package com.android.systemapp.paramsettings.model;

import com.android.systemapp.paramsettings.ParamSettingsUtils;

public class Camera {
	public String frontCameraPixel;
	public String backCameraPixel;
	public String frontCameraWidthPixel;
	public String frontCameraHeightPixel;
	public String backCameraWidthPixel;
	public String backCameraHeightPixel;
	public String videoQuality;
	public int drawableId;
	public boolean isMore;

	public Camera(int drawableId, String frontPix, String backPix, String fps,
			boolean isMore) {
		this.frontCameraPixel = frontPix;
		this.frontCameraWidthPixel = ParamSettingsUtils.calculateWidth(frontCameraPixel);
		this.frontCameraHeightPixel = ParamSettingsUtils.calculateHeight(frontCameraPixel);
		this.backCameraPixel = backPix;
		this.backCameraWidthPixel = ParamSettingsUtils.calculateWidth(backCameraPixel);
		this.backCameraHeightPixel = ParamSettingsUtils.calculateHeight(backCameraPixel);
		this.videoQuality = fps;
		this.drawableId = drawableId;
		this.isMore = isMore;
	}

	public Camera(int drawableId, String frontPix, String frontWidthPix,
			String frontHeightPix, String backPix, String backWidthPix,
			String backHeightPix, String fps, boolean isMore) {
		this.frontCameraPixel = frontPix;
		this.frontCameraWidthPixel = frontWidthPix;
		this.frontCameraHeightPixel = frontHeightPix;
		this.backCameraPixel = backPix;
		this.backCameraWidthPixel = backWidthPix;
		this.backCameraHeightPixel = backHeightPix;
		this.videoQuality = fps;
		this.drawableId = drawableId;
		this.isMore = isMore;
	}

	public boolean equals(Camera other) {
		if (other != null) {
			if (frontCameraPixel.equals(other.frontCameraPixel)
					&& backCameraPixel.equals(other.backCameraPixel)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "FTP: " + frontCameraPixel + " FCH: " + frontCameraHeightPixel
				+ " FCW: " + frontCameraWidthPixel + " BTP: "
				+ backCameraPixel + " BCH: " + backCameraHeightPixel
				+ " BCW: " + backCameraWidthPixel + " isMore: " + isMore;
	}
}

