package com.android.androidtool.paramsettings;

import com.android.androidtool.paramsettings.model.Camera;
import com.android.androidtool.paramsettings.model.Cpu;
import com.android.androidtool.paramsettings.model.Lcd;
import com.android.androidtool.paramsettings.model.Memory;
import com.android.androidtool.paramsettings.model.Other;
import com.android.androidtool.paramsettings.model.Storage;

public interface IParamSettings {
	// Param setting NV id
	public static final int PARAM_SETTING_ID = 59;
	// NV item offset
	public static final int NV_ITEM_OFFSET = 10;
	// NV offset
	public static final int NV_OFFSET = 1024;

	/*
	 * Cpu system properties key
	 */
	public static final String KEY_CPU_TYPE = "persist.sys.tt.cpu_model";
	public static final String KEY_CPU_CORE = "persist.sys.tt.cpu_core";
	public static final String KEY_CPU_MAX_FREQ = "persist.sys.tt.cpu_fre_max";
	public static final String KEY_CPU_MIN_FREQ = "persist.sys.tt.cpu_fre_min";

	/*
	 * Cpu NV index
	 */
	// Cpu type start 261 end 270
	public static final int INDEX_CPU_TYPE = NV_OFFSET + 113;
	// Cpu core start 271 end 280
	public static final int INDEX_CPU_CORE = NV_OFFSET + 123;
	// Cpu max freq start 281 end 290
	public static final int INDEX_CPU_MAX_FREQ = NV_OFFSET + 133;
	// Cpu min freq start 291 end 300
	public static final int INDEX_CPU_MIN_FREQ = NV_OFFSET + 143;

	/*
	 * Memory system properties key
	 */
	public static final String KEY_MAX_RAM = "persist.sys.tt.ram_max";
	public static final String KEY_MIN_RAM = "persist.sys.tt.ram_min";

	/*
	 * Memory NV index
	 */
	// Max RAM start 301 end 310
	public static final int INDEX_MAX_RAM = NV_OFFSET + 153;
	// Min RAM start 311 end 320
	public static final int INDEX_MIN_RAM = NV_OFFSET + 163;

	/*
	 * Storage system properties key
	 */
	public static final String KEY_SYSTEM_STORAGE = "persist.sys.tt.rom_data";
	public static final String KEY_INTERNAL_STORAGE = "persist.sys.tt.rom_sd";

	/*
	 * Storage NV index
	 */
	// System storage start 321 end 330
	public static final int INDEX_SYSTEM_STORAGE = NV_OFFSET + 173;
	// Internal storage start 331 end 340
	public static final int INDEX_INTERNAL_STORAGE = NV_OFFSET + 183;

	/*
	 * Lcd system properties key
	 */
	public static final String KEY_LCD_WIDTH = "persist.sys.tt.lcd_with";
	public static final String KEY_LCD_HEIGHT = "persist.sys.tt.lcd_hight";
	public static final String KEY_LCD_DENSITY = "persist.sys.tt.lcd_dpi";

	/*
	 * Lcd NV index
	 */
	// Lcd width start 341 end 350
	public static final int INDEX_LCD_WIDTH = NV_OFFSET + 193;
	// Lcd height start 351 end 360
	public static final int INDEX_LCD_HEIGHT = NV_OFFSET + 203;
	// Lcd density start 361 end 370
	public static final int INDEX_LCD_DENSITY = NV_OFFSET + 213;

	/*
	 * Camera system properties key
	 */
	public static final String KEY_FRONT_CAMERA_PIXEL = "persist.sys.tt.cam_f_pix";
	public static final String KEY_FRONT_CAMERA_WIDTH_PIXEL = "persist.sys.tt.cam_f_pix_w";
	public static final String KEY_FRONT_CAMERA_HEIGHT_PIXEL = "persist.sys.tt.cam_f_pix_h";
	public static final String KEY_BACK_CAMERA_PIXEL = "persist.sys.tt.cam_b_pix";
	public static final String KEY_BACK_CAMERA_WIDTH_PIXEL = "persist.sys.tt.cam_b_pix_w";
	public static final String KEY_BACK_CAMERA_HEIGHT_PIXEL = "persist.sys.tt.cam_b_pix_h";
	public static final String KEY_VIDEO_QUALITY = "persist.sys.tt.cam_xxxp";

	/*
	 * Camera NV index
	 */
	// Front camera pixel start 371 end 380
	public static final int INDEX_FRONT_CAMERA_PIXEL = NV_OFFSET + 223;
	// Front camera width pixel start 381 end 390
	public static final int INDEX_FRONT_CAMERA_WIDTH_PIXEL = NV_OFFSET + 233;
	// Front camera height pixel start 391 end 400;
	public static final int INDEX_FRONT_CAMERA_HEIGHT_PIXEL = NV_OFFSET + 243;
	// Back camera pixel start 401 end 410
	public static final int INDEX_BACK_CAMERA_PIXEL = NV_OFFSET + 253;
	// Back camera width pixel start 411 end 420
	public static final int INDEX_BACK_CAMERA_WIDTH_PIXEL = NV_OFFSET + 263;
	// Back camera height pixel start 421 end 430
	public static final int INDEX_BACK_CAMERA_HEIGHT_PIXEL = NV_OFFSET + 273;
	// Video quality start 431 end 440
	public static final int INDEX_VIDEO_QUALITY = NV_OFFSET + 283;

	/*
	 * Other system properties key
	 */
	public static final String KEY_SUPPORT_ALL_SENSOR = "persist.sys.tt.sensor";
	public static final String KEY_SUPPORT_ROOT = "persist.sys.tt.root";
	public static final String KEY_BENCHMARK = "persist.sys.tt.scores";

	/*
	 * Other NV index
	 */
	// Support all sensor start 441 end 450
	public static final int INDEX_SUPPORT_ALL_SENSOR = NV_OFFSET + 293;
	// Support root start 451 end 460
	public static final int INDEX_SUPPORT_ROOT = NV_OFFSET + 303;
	// Benchmark start 461 end 470
	public static final int INDEX_BENCHMARK = NV_OFFSET + 313;

	/*
	 * System properties back up key
	 */
	public static final String KEY_CPU_TYPE_BACKUP = "ro.sys.tt.cpu_model_b";
	public static final String KEY_CPU_CORE_BACKUP = "ro.sys.tt.cpu_core_b";
	public static final String KEY_CPU_MAX_FREQ_BACKUP = "ro.sys.tt.cpu_fre_max_b";
	public static final String KEY_CPU_MIN_FREQ_BACKUP = "ro.sys.tt.cpu_fre_min_b";
	public static final String KEY_MAX_RAM_BACKUP = "ro.sys.tt.ram_max_b";
	public static final String KEY_MIN_RAM_BACKUP = "ro.sys.tt.ram_min_b";
	public static final String KEY_SYSTEM_STORAGE_BACKUP = "ro.sys.tt.rom_data_b";
	public static final String KEY_INTERNAL_STORAGE_BACKUP = "ro.sys.tt.rom_sd_b";
	public static final String KEY_LCD_WIDTH_BACKUP = "ro.sys.tt.lcd_with_b";
	public static final String KEY_LCD_HEIGHT_BACKUP = "ro.sys.tt.lcd_hight_b";
	public static final String KEY_LCD_DENSITY_BACKUP = "ro.sys.tt.lcd_dpi_b";
	public static final String KEY_FRONT_CAMERA_PIXEL_BACKUP = "ro.sys.tt.cam_f_pix_b";
	public static final String KEY_FRONT_CAMERA_WIDTH_PIXEL_BACKUP = "ro.sys.tt.cam_f_pix_w_b";
	public static final String KEY_FRONT_CAMERA_HEIGHT_PIXEL_BACKUP = "ro.sys.tt.cam_f_pix_h_b";
	public static final String KEY_BACK_CAMERA_PIXEL_BACKUP = "ro.sys.tt.cam_b_pix_b";
	public static final String KEY_BACK_CAMERA_WIDTH_PIXEL_BACKUP = "ro.sys.tt.cam_b_pix_w_b";
	public static final String KEY_BACK_CAMERA_HEIGHT_PIXEL_BACKUP = "ro.sys.tt.cam_b_pix_h_b";
	public static final String KEY_VIDEO_QUALITY_BACKUP = "ro.sys.tt.cam_xxxp_b";
	public static final String KEY_SUPPORT_ALL_SENSOR_BACKUP = "ro.sys.tt.sensor_b";
	public static final String KEY_SUPPORT_ROOT_BACKUP = "ro.sys.tt.root_b";
	public static final String KEY_BENCHMARK_BACKUP = "ro.sys.tt.scores_b";

	String getParam(String key, String def);
	void setParam(String key, String value);
	Cpu getCpuParam();
	void setCpuParam(Cpu cpu);
	void setCpuParam(String type, String core, String minFreq, String maxFreq);
	Memory getMemoryParam();
	void setMemoryParam(Memory memory);
	void setMemoryParam(String minRam, String maxRam);
	Storage getStorageParam();
	void setStoargeParam(Storage storage);
	void setStorageParam(String rom, String internal);
	Lcd getLcdParam();
	void setLcdParam(Lcd lcd);
	void setLcdParam(String width, String height, String density);
	Camera getCameraParam();
	void setCameraParam(Camera camera);
	void setCameraParam(String front, String frontWidth, String frontHeight, String back, String backWidth,
			String backHeight, String video);
	Other getOtherParam();
	void setOtherParam(Other other);
	void setOtherParam(String sensor, String root, String benchmark);
	boolean writeCpuParamToNV(Cpu cpu);
	boolean writeCpuParamToNV(String type, String core, String minFreq, String maxFreq);
	boolean writeMemoryParamToNV(Memory memory);
	boolean writeMemoryParamToNV(String minRam, String maxRam);
	boolean writeStorageParamToNV(Storage storage);
	boolean writeStorageParamToNV(String rom, String internal);
	boolean writeLcdParamToNV(Lcd lcd);
	boolean writeLcdParamToNV(String width, String height, String density);
	boolean writeCameraParamToNV(Camera camera);
	boolean writeCameraParamToNV(String front, String frontWidth, String frontHeight, String back,
			String backWidth, String backHeight, String video);
	boolean writeOtherParamToNV(Other other);
	boolean writeOtherParamToNV(String sensor, String root, String benchmark);
	Cpu getCpuParamFromNV();
	Memory getMemoryParamFromNV();
	Storage getStorageParamFromNV();
	Lcd getLcdParamFromNV();
	Camera getCameraParamFromNV();
	Other getOtherParamFromNV();
	boolean resetAllParamToFactory();
	boolean resetCpuParamToFactory();
	boolean resetMemoryParamToFactory();
	boolean resetStorageParamToFactory();
	boolean resetLcdParamToFactory();
	boolean resetCameraParamToFactory();
	boolean resetOtherParamToFactory();
	void printNvValue();
}
