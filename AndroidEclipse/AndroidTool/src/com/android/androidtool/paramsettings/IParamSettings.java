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
	
	// product model length
	public static final int PRODUCT_MODEL_LENGTH = 256;
	// product model start 4 end 260
	public static final int INDEX_PRODUCT_NAME = NV_OFFSET + 4;

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
	public static final int INDEX_CPU_TYPE = NV_OFFSET + 261;
	// Cpu core start 271 end 280
	public static final int INDEX_CPU_CORE = NV_OFFSET + 271;
	// Cpu max freq start 281 end 290
	public static final int INDEX_CPU_MAX_FREQ = NV_OFFSET + 281;
	// Cpu min freq start 291 end 300
	public static final int INDEX_CPU_MIN_FREQ = NV_OFFSET + 291;

	/*
	 * Memory system properties key
	 */
	public static final String KEY_MAX_RAM = "persist.sys.tt.ram_max";
	public static final String KEY_MIN_RAM = "persist.sys.tt.ram_min";

	/*
	 * Memory NV index
	 */
	// Max RAM start 301 end 310
	public static final int INDEX_MAX_RAM = NV_OFFSET + 301;
	// Min RAM start 311 end 320
	public static final int INDEX_MIN_RAM = NV_OFFSET + 311;

	/*
	 * Storage system properties key
	 */
	public static final String KEY_SYSTEM_STORAGE = "persist.sys.tt.rom_data";
	public static final String KEY_INTERNAL_STORAGE = "persist.sys.tt.rom_sd";

	/*
	 * Storage NV index
	 */
	// System storage start 321 end 330
	public static final int INDEX_SYSTEM_STORAGE = NV_OFFSET + 321;
	// Internal storage start 331 end 340
	public static final int INDEX_INTERNAL_STORAGE = NV_OFFSET + 331;

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
	public static final int INDEX_LCD_WIDTH = NV_OFFSET + 341;
	// Lcd height start 351 end 360
	public static final int INDEX_LCD_HEIGHT = NV_OFFSET + 351;
	// Lcd density start 361 end 370
	public static final int INDEX_LCD_DENSITY = NV_OFFSET + 361;

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
	public static final int INDEX_FRONT_CAMERA_PIXEL = NV_OFFSET + 371;
	// Front camera width pixel start 381 end 390
	public static final int INDEX_FRONT_CAMERA_WIDTH_PIXEL = NV_OFFSET + 381;
	// Front camera height pixel start 391 end 400;
	public static final int INDEX_FRONT_CAMERA_HEIGHT_PIXEL = NV_OFFSET + 391;
	// Back camera pixel start 401 end 410
	public static final int INDEX_BACK_CAMERA_PIXEL = NV_OFFSET + 401;
	// Back camera width pixel start 411 end 420
	public static final int INDEX_BACK_CAMERA_WIDTH_PIXEL = NV_OFFSET + 411;
	// Back camera height pixel start 421 end 430
	public static final int INDEX_BACK_CAMERA_HEIGHT_PIXEL = NV_OFFSET + 421;
	// Video quality start 431 end 440
	public static final int INDEX_VIDEO_QUALITY = NV_OFFSET + 431;

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
	public static final int INDEX_SUPPORT_ALL_SENSOR = NV_OFFSET + 441;
	// Support root start 451 end 460
	public static final int INDEX_SUPPORT_ROOT = NV_OFFSET + 451;
	// Benchmark start 461 end 470
	public static final int INDEX_BENCHMARK = NV_OFFSET + 461;

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

	public String getParam(String key, String def);

	public void setParam(String key, String value);

	public Cpu getCpuParam();

	public void setCpuParam(Cpu cpu);

	public void setCpuParam(String type, String core, String minFreq, String maxFreq);

	public Memory getMemoryParam();

	public void setMemoryParam(Memory memory);

	public void setMemoryParam(String minRam, String maxRam);

	public Storage getStorageParam();

	public void setStoargeParam(Storage storage);

	public void setStorageParam(String rom, String internal);

	public Lcd getLcdParam();

	public void setLcdParam(Lcd lcd);

	public void setLcdParam(String width, String height, String density);

	public Camera getCameraParam();

	public void setCameraParam(Camera camera);

	public void setCameraParam(String front, String frontWidth, String frontHeight, String back, String backWidth,
			String backHeight, String video);

	public Other getOtherParam();

	public void setOtherParam(Other other);

	public void setOtherParam(String sensor, String root, String benchmark);

	public boolean writeCpuParamToNV(Cpu cpu);

	public boolean writeCpuParamToNV(String type, String core, String minFreq, String maxFreq);

	public boolean writeMemoryParamToNV(Memory memory);

	public boolean writeMemoryParamToNV(String minRam, String maxRam);

	public boolean writeStorageParamToNV(Storage storage);

	public boolean writeStorageParamToNV(String rom, String internal);

	public boolean writeLcdParamToNV(Lcd lcd);

	public boolean writeLcdParamToNV(String width, String height, String density);

	public boolean writeCameraParamToNV(Camera camera);

	public boolean writeCameraParamToNV(String front, String frontWidth, String frontHeight, String back,
			String backWidth, String backHeight, String video);

	public boolean writeOtherParamToNV(Other other);

	public boolean writeOtherParamToNV(String sensor, String root, String benchmark);
	
	public boolean writeProductModelToNV(String model);

	public Cpu getCpuParamFromNV();

	public Memory getMemoryParamFromNV();

	public Storage getStorageParamFromNV();

	public Lcd getLcdParamFromNV();

	public Camera getCameraParamFromNV();

	public Other getOtherParamFromNV();

	public boolean resetAllParamToFactory();

	public boolean resetCpuParamToFactory();

	public boolean resetMemoryParamToFactory();

	public boolean resetStorageParamToFactory();

	public boolean resetLcdParamToFactory();

	public boolean resetCameraParamToFactory();

	public boolean resetOtherParamToFactory();

	public void printNvValue();
}
