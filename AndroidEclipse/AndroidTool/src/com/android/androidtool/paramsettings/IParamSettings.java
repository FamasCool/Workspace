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
	// Cpu type start 199 end 208
	public static final int INDEX_CPU_TYPE = 199;
	// Cpu core start 209 end 218
	public static final int INDEX_CPU_CORE = 209;
	// Cpu max freq start 219 end 228
	public static final int INDEX_CPU_MAX_FREQ = 219;
	// Cpu min freq start 229 end 238
	public static final int INDEX_CPU_MIN_FREQ = 229;

	/*
	 * Memory system properties key
	 */
	public static final String KEY_MAX_RAM = "persist.sys.tt.ram_max";
	public static final String KEY_MIN_RAM = "persist.sys.tt.ram_min";

	/*
	 * Memory NV index
	 */
	// Max RAM start 239 end 248
	public static final int INDEX_MAX_RAM = 239;
	// Min RAM start 249 end 258
	public static final int INDEX_MIN_RAM = 249;

	/*
	 * Storage system properties key
	 */
	public static final String KEY_SYSTEM_STORAGE = "persist.sys.tt.rom_data";
	public static final String KEY_INTERNAL_STORAGE = "persist.sys.tt.rom_sd";

	/*
	 * Storage NV index
	 */
	// System storage start 259 end 268
	public static final int INDEX_SYSTEM_STORAGE = 259;
	// Internal storage start 269 end 278
	public static final int INDEX_INTERNAL_STORAGE = 269;

	/*
	 * Lcd system properties key
	 */
	public static final String KEY_LCD_WIDTH = "persist.sys.tt.lcd_with";
	public static final String KEY_LCD_HEIGHT = "persist.sys.tt.lcd_hight";
	public static final String KEY_LCD_DENSITY = "persist.sys.tt.lcd_dpi";

	/*
	 * Lcd NV index
	 */
	// Lcd width start 99 end 108
	public static final int INDEX_LCD_WIDTH = 99;
	// Lcd height start 109 end 118
	public static final int INDEX_LCD_HEIGHT = 109;
	// Lcd density start 119 end 128
	public static final int INDEX_LCD_DENSITY = 119;

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
	// Front camera pixel start 129 end 138
	public static final int INDEX_FRONT_CAMERA_PIXEL = 129;
	// Front camera width pixel start 139 end 148
	public static final int INDEX_FRONT_CAMERA_WIDTH_PIXEL = 139;
	// Front camera height pixel start 149 end 158;
	public static final int INDEX_FRONT_CAMERA_HEIGHT_PIXEL = 149;
	// Back camera pixel start 159 end 168
	public static final int INDEX_BACK_CAMERA_PIXEL = 159;
	// Back camera width pixel start 169 end 178
	public static final int INDEX_BACK_CAMERA_WIDTH_PIXEL = 169;
	// Back camera height pixel start 179 end 188
	public static final int INDEX_BACK_CAMERA_HEIGHT_PIXEL = 179;
	// Video quality start 189 end 198
	public static final int INDEX_VIDEO_QUALITY = 189;

	/*
	 * Other system properties key
	 */
	public static final String KEY_SUPPORT_ALL_SENSOR = "persist.sys.tt.sensor";
	public static final String KEY_SUPPORT_ROOT = "persist.sys.tt.root";
	public static final String KEY_BENCHMARK = "persist.sys.tt.scores";

	/*
	 * Other NV index
	 */
	// Support all sensor start 279 end 288
	public static final int INDEX_SUPPORT_ALL_SENSOR = 279;
	// Support root start 289 end 298
	public static final int INDEX_SUPPORT_ROOT = 289;
	// Benchmark start 299 end 308
	public static final int INDEX_BENCHMARK = 299;

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
