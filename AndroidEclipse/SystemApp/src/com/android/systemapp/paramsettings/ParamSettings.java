package com.android.systemapp.paramsettings;

import com.android.systemapp.NvRAMAgent;
import com.android.systemapp.paramsettings.model.Camera;
import com.android.systemapp.paramsettings.model.Cpu;
import com.android.systemapp.paramsettings.model.Lcd;
import com.android.systemapp.paramsettings.model.Memory;
import com.android.systemapp.paramsettings.model.Other;
import com.android.systemapp.paramsettings.model.Storage;

import android.content.Context;
import android.os.IBinder;
import android.os.ServiceManager;
import android.os.SystemProperties;

import com.android.systemapp.Log;

public class ParamSettings implements IParamSettings {

	private Context mContext;

	public ParamSettings(Context context) {
		mContext = context;
	}

	@Override
	public String getParam(String key, String def) {
		return SystemProperties.get(key, def);
	}

	@Override
	public void setParam(String key, String value) {
		SystemProperties.set(key, value);
	}

	@Override
	public Cpu getCpuParam() {
		String type = getParam(KEY_CPU_TYPE, "");
		String core = getParam(KEY_CPU_CORE, "");
		String min = getParam(KEY_CPU_MIN_FREQ, "");
		String max = getParam(KEY_CPU_MAX_FREQ, "");
		Log.d(this, "getCpuParam=>type: " + type + " core: " + core
					+ " min: " + min + " max: " + max);
		return new Cpu(-1, type, core, min, max, false);
	}

	@Override
	public void setCpuParam(Cpu cpu) {
			Log.d(this, "setCpuParam=>cpu: " + cpu);
		setParam(KEY_CPU_TYPE, cpu.type);
		setParam(KEY_CPU_CORE, cpu.core);
		setParam(KEY_CPU_MIN_FREQ, cpu.minFreq);
		setParam(KEY_CPU_MAX_FREQ, cpu.maxFreq);
		ParamSettingsUtils.execCommand("echo "+ cpu.core + " " + cpu.maxFreq + " " + cpu.minFreq +" > /sys/devices/system/cpu/kernel_max");
		ParamSettingsUtils.writeInfoToPointer("/sys/devices/system/cpu/kernel_max", cpu.core + " " + cpu.maxFreq + " " + cpu.minFreq);
	}

	@Override
	public void setCpuParam(String type, String core, String minFreq,
			String maxFreq) {
			Log.d(this, "setCpuParam=>type: " + type + " core: " + core
					+ " minFreq: " + minFreq + " maxFreq: " + maxFreq);
		setParam(KEY_CPU_TYPE, type);
		setParam(KEY_CPU_CORE, core);
		setParam(KEY_CPU_MIN_FREQ, minFreq);
		setParam(KEY_CPU_MAX_FREQ, maxFreq);
		ParamSettingsUtils.execCommand("echo "+ core + " " + maxFreq + " " + minFreq +" > /sys/devices/system/cpu/kernel_max");
		ParamSettingsUtils.writeInfoToPointer("/sys/devices/system/cpu/kernel_max", core + " " + maxFreq + " " + minFreq);
	}

	@Override
	public Memory getMemoryParam() {
		String minRam = getParam(KEY_MIN_RAM, "");
		String maxRam = getParam(KEY_MAX_RAM, "");
		
			Log.d(this, "getMemoryParam=>minRam: " + minRam + " maxRam: "
					+ maxRam);
		return new Memory(
				-1,
				ParamSettingsUtils.formatStorageSize(ParamSettingsUtils.getStorageValue(minRam) * 1024L * 1024L),
				ParamSettingsUtils.formatStorageSize(ParamSettingsUtils.getStorageValue(maxRam) * 1024L * 1024L),
				false);
	}

	@Override
	public void setMemoryParam(Memory memory) {
		
			Log.d(this, "setMemoryParam=>memory: " + memory);
		setParam(KEY_MIN_RAM, ParamSettingsUtils.getStorageMBString(memory.minRam));
		setParam(KEY_MAX_RAM, ParamSettingsUtils.getStorageMBString(memory.maxRam));
		 Log.d(this, "setMemoryParam(Memory memory)=>min: " + ParamSettingsUtils.getStorageMBString(memory.minRam) + " max: " + ParamSettingsUtils.getStorageMBString(memory.maxRam));
		ParamSettingsUtils.execCommand("echo "+ ParamSettingsUtils.getStorageMBString(memory.maxRam) + " " + ParamSettingsUtils.getStorageMBString(memory.minRam) + " > /proc/meminfo");
		ParamSettingsUtils.writeInfoToPointer("/proc/meminfo", ParamSettingsUtils.getStorageMBString(memory.maxRam) + " " + ParamSettingsUtils.getStorageMBString(memory.minRam));
	}

	@Override
	public void setMemoryParam(String minRam, String maxRam) {
		
			Log.d(this, "setMemoryParam=>maxRam: " + maxRam + " minRam: "
					+ minRam);
		setParam(KEY_MIN_RAM, ParamSettingsUtils.getStorageMBString(minRam));
		setParam(KEY_MAX_RAM, ParamSettingsUtils.getStorageMBString(maxRam));
		ParamSettingsUtils.execCommand("echo "+ ParamSettingsUtils.getStorageMBString(maxRam) +" "+ ParamSettingsUtils.getStorageMBString(minRam) +" > /proc/meminfo");
		ParamSettingsUtils.writeInfoToPointer("/proc/meminfo", ParamSettingsUtils.getStorageMBString(maxRam) + " " + ParamSettingsUtils.getStorageMBString(minRam));
	}

	@Override
	public Storage getStorageParam() {
		String systemStorage = getParam(KEY_SYSTEM_STORAGE, "");
		String internalStorage = getParam(KEY_INTERNAL_STORAGE, "");
		
			Log.d(this, "getStorageParam=>system: " + systemStorage
					+ " internal: " + internalStorage);
		return new Storage(
				-1,
				ParamSettingsUtils.formatStorageSize(ParamSettingsUtils.getStorageValue(systemStorage) * 1024L * 1024L * 1024L),
				ParamSettingsUtils.formatStorageSize(ParamSettingsUtils.getStorageValue(internalStorage) * 1024L * 1024L * 1024L),
				false);
	}

	@Override
	public void setStoargeParam(Storage storage) {
		
			Log.d(this, "setStorageParam=>storage: " + storage);
		setParam(KEY_SYSTEM_STORAGE,
				ParamSettingsUtils.getStorageGBString(storage.systemStorage));
		setParam(KEY_INTERNAL_STORAGE,
				ParamSettingsUtils.getStorageGBString(storage.internalStorage));
	}

	@Override
	public void setStorageParam(String rom, String internal) {
		
			Log.d(this, "setStorageParam=>rom: " + rom + " internal: "
					+ internal);
		setParam(KEY_SYSTEM_STORAGE, ParamSettingsUtils.getStorageGBString(rom));
		setParam(KEY_INTERNAL_STORAGE, ParamSettingsUtils.getStorageGBString(internal));
	}

	@Override
	public Lcd getLcdParam() {
		String width = getParam(KEY_LCD_WIDTH, "");
		String height = getParam(KEY_LCD_HEIGHT, "");
		String density = getParam(KEY_LCD_DENSITY, "");
		
			Log.d(this, "getLcdParam=>width: " + width + " height: " + height);
		return new Lcd(-1, width, height, density, false);
	}

	@Override
	public void setLcdParam(Lcd lcd) {
		
			Log.d(this, "setLcdParam=>lcd: " + lcd);
		setParam(KEY_LCD_WIDTH, lcd.width);
		setParam(KEY_LCD_HEIGHT, lcd.height);
		setParam(KEY_LCD_DENSITY, lcd.density);
	}

	@Override
	public void setLcdParam(String width, String height, String density) {
		
			Log.d(this, "setLcdParam=>width: " + width + " height: " + height
					+ " density: " + density);
		setParam(KEY_LCD_WIDTH, width);
		setParam(KEY_LCD_HEIGHT, height);
		setParam(KEY_LCD_DENSITY, density);
	}

	@Override
	public Camera getCameraParam() {
		String front = getParam(KEY_FRONT_CAMERA_PIXEL, "");
		String frontWidth = getParam(KEY_FRONT_CAMERA_WIDTH_PIXEL, "");
		String frontHeight = getParam(KEY_FRONT_CAMERA_HEIGHT_PIXEL, "");
		String back = getParam(KEY_BACK_CAMERA_PIXEL, "");
		String backWidth = getParam(KEY_BACK_CAMERA_WIDTH_PIXEL, "");
		String backHeight = getParam(KEY_BACK_CAMERA_HEIGHT_PIXEL, "");
		String video = getParam(KEY_VIDEO_QUALITY, "");
		
			Log.d(this, "getCameraParam=>front: " + front + " frontWidth: "
					+ frontWidth + " frontHeight: " + frontHeight + " back: "
					+ back + " backWidth: " + backWidth + " backHeight: "
					+ backHeight + " video: " + video);
		return new Camera(-1, front, frontWidth, frontHeight, back, backWidth,
				backHeight, video, false);
	}

	@Override
	public void setCameraParam(Camera camera) {
		
			Log.d(this, "setCameraParam=>camera: " + camera);
		setParam(KEY_FRONT_CAMERA_PIXEL, camera.frontCameraPixel);
		setParam(KEY_FRONT_CAMERA_WIDTH_PIXEL, camera.frontCameraWidthPixel);
		setParam(KEY_FRONT_CAMERA_HEIGHT_PIXEL, camera.frontCameraHeightPixel);
		setParam(KEY_BACK_CAMERA_PIXEL, camera.backCameraPixel);
		setParam(KEY_BACK_CAMERA_WIDTH_PIXEL, camera.backCameraWidthPixel);
		setParam(KEY_BACK_CAMERA_HEIGHT_PIXEL, camera.backCameraHeightPixel);
		setParam(KEY_VIDEO_QUALITY, camera.videoQuality);
	}

	@Override
	public void setCameraParam(String front, String frontWidth,
			String frontHeight, String back, String backWidth,
			String backHeight, String video) {
		
			Log.d(this, "setCameraParam=>front: " + front + " frontWidth: "
					+ frontWidth + " frontHeight: " + frontHeight + " back: "
					+ back + " backWidth: " + backWidth + " backHeight: "
					+ backHeight + " video: " + video);
		setParam(KEY_FRONT_CAMERA_PIXEL, front);
		setParam(KEY_FRONT_CAMERA_WIDTH_PIXEL, frontWidth);
		setParam(KEY_FRONT_CAMERA_HEIGHT_PIXEL, frontHeight);
		setParam(KEY_BACK_CAMERA_PIXEL, back);
		setParam(KEY_BACK_CAMERA_WIDTH_PIXEL, backWidth);
		setParam(KEY_BACK_CAMERA_HEIGHT_PIXEL, backHeight);
		setParam(KEY_VIDEO_QUALITY, video);
	}

	@Override
	public Other getOtherParam() {
		String supportSensor = getParam(KEY_SUPPORT_ALL_SENSOR, "false");
		String supportRoot = getParam(KEY_SUPPORT_ROOT, "false");
		String benchmark = getParam(KEY_BENCHMARK, "");
		
			Log.d(this, "getOtherParam=>sensor: " + supportSensor + " root: "
					+ supportRoot + " benchmark: " + benchmark);
		return new Other(-1, supportSensor, supportRoot, benchmark, false);
	}

	@Override
	public void setOtherParam(Other other) {
		
			Log.d(this, "setOther=>other: " + other);
		setParam(KEY_SUPPORT_ALL_SENSOR, other.supportAllSensor);
		setParam(KEY_SUPPORT_ROOT, other.supportRoot);
		setParam(KEY_BENCHMARK, other.benchmark);
	}

	@Override
	public void setOtherParam(String sensor, String root, String benchmark) {
		
			Log.d(this, "setOtherParam=>sensor: " + sensor + " root: " + root
					+ " benchmark: " + benchmark);
		setParam(KEY_SUPPORT_ALL_SENSOR, sensor);
		setParam(KEY_SUPPORT_ROOT, root);
		setParam(KEY_BENCHMARK, benchmark);
	}

	@Override
	public boolean writeCpuParamToNV(Cpu cpu) {
		return writeCpuParamToNV(cpu.type, cpu.core, cpu.minFreq, cpu.maxFreq);
	}

	@Override
	public boolean writeCpuParamToNV(String type, String core, String minFreq,
			String maxFreq) {
		
			Log.d(this, "writeCpuParamToNV=>type: " + type + " core: " + core
					+ " minFreq: " + minFreq + " maxFreq: " + maxFreq);
		boolean result = false;
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			if (buff != null
					&& buff.length > (INDEX_CPU_MIN_FREQ + NV_ITEM_OFFSET)) {
				byte[] bytes = ParamSettingsUtils.getByteArray(type);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_CPU_TYPE,
						INDEX_CPU_TYPE + NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(core);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_CPU_CORE,
						INDEX_CPU_CORE + NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(maxFreq);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_CPU_MAX_FREQ,
						INDEX_CPU_MAX_FREQ + NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(minFreq);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_CPU_MIN_FREQ,
						INDEX_CPU_MIN_FREQ + NV_ITEM_OFFSET + 1, bytes);

				int flag = agent.writeFile(PARAM_SETTING_ID, buff);
				if (flag > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			Log.e(this, "writeCpuParamToNV=>error: ", e);
		}
		 {
			printNvValue();
		}
		return result;
	}

	@Override
	public boolean writeMemoryParamToNV(Memory memory) {
		return writeMemoryParamToNV(memory.minRam, memory.maxRam);
	}

	@Override
	public boolean writeMemoryParamToNV(String minRam, String maxRam) {
		
			Log.d(this, "writeMemoryParamToNV=>minRam: " + minRam + " maxRam: "
					+ maxRam);
		boolean result = false;
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			if (buff != null && buff.length > (INDEX_MIN_RAM + NV_ITEM_OFFSET)) {
				byte[] bytes = ParamSettingsUtils.getByteArray(ParamSettingsUtils
						.getStorageMBString(minRam));
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_MIN_RAM, INDEX_MIN_RAM
						+ NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(ParamSettingsUtils.getStorageMBString(maxRam));
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_MAX_RAM, INDEX_MAX_RAM
						+ NV_ITEM_OFFSET + 1, bytes);

				int flag = agent.writeFile(PARAM_SETTING_ID, buff);
				if (flag > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			Log.e(this, "writeCpuParamToNV=>error: ", e);
		}
		 {
			printNvValue();
		}
		return result;
	}

	@Override
	public boolean writeStorageParamToNV(Storage storage) {
		return writeStorageParamToNV(storage.systemStorage,
				storage.internalStorage);
	}

	@Override
	public boolean writeStorageParamToNV(String rom, String internal) {
		
			Log.d(this, "writeStorageParamToNV=>rom: " + rom + " internal: "
					+ internal);
		boolean result = false;
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			if (buff != null
					&& buff.length > (INDEX_INTERNAL_STORAGE + NV_ITEM_OFFSET)) {
				byte[] bytes = ParamSettingsUtils.getByteArray(ParamSettingsUtils.getStorageGBString(rom));
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_SYSTEM_STORAGE,
						INDEX_SYSTEM_STORAGE + NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(ParamSettingsUtils.getStorageGBString(internal));
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_INTERNAL_STORAGE,
						INDEX_INTERNAL_STORAGE + NV_ITEM_OFFSET + 1, bytes);

				int flag = agent.writeFile(PARAM_SETTING_ID, buff);
				if (flag > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			Log.e(this, "writeCpuParamToNV=>error: ", e);
		}
		 {
			printNvValue();
		}
		return result;
	}

	@Override
	public boolean writeLcdParamToNV(Lcd lcd) {
		return writeLcdParamToNV(lcd.width, lcd.height, lcd.density);
	}

	@Override
	public boolean writeLcdParamToNV(String width, String height, String density) {
		
			Log.d(this, "writeLcdParamToNV=>width: " + width + " height: "
					+ height + " density: " + density);
		boolean result = false;
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			if (buff != null
					&& buff.length > (INDEX_LCD_DENSITY + NV_ITEM_OFFSET)) {
				byte[] bytes = ParamSettingsUtils.getByteArray(width);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_LCD_WIDTH,
						INDEX_LCD_WIDTH + NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(height);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_LCD_HEIGHT,
						INDEX_LCD_HEIGHT + NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(density);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_LCD_DENSITY,
						INDEX_LCD_DENSITY + NV_ITEM_OFFSET + 1, bytes);

				int flag = agent.writeFile(PARAM_SETTING_ID, buff);
				if (flag > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			Log.e(this, "writeCpuParamToNV=>error: ", e);
		}
		 {
			printNvValue();
		}
		return result;
	}

	@Override
	public boolean writeCameraParamToNV(Camera camera) {
		return writeCameraParamToNV(camera.frontCameraPixel,
				camera.frontCameraWidthPixel, camera.frontCameraHeightPixel,
				camera.backCameraPixel, camera.backCameraWidthPixel,
				camera.backCameraHeightPixel, camera.videoQuality);
	}

	@Override
	public boolean writeCameraParamToNV(String front, String frontWidth,
			String frontHeight, String back, String backWidth,
			String backHeight, String video) {
		
			Log.d(this, "writeCameraParamToNV=>front: " + front
					+ " frontWidth: " + frontWidth + " frontHeight: "
					+ frontHeight + " back: " + back + " backWidth: "
					+ backWidth + " backHeight: " + backHeight + " video: "
					+ video);
		boolean result = false;
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			if (buff != null
					&& buff.length > (INDEX_VIDEO_QUALITY + NV_ITEM_OFFSET)) {
				byte[] bytes = ParamSettingsUtils.getByteArray(front);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_FRONT_CAMERA_PIXEL,
						INDEX_FRONT_CAMERA_PIXEL + NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(frontWidth);
				buff = ParamSettingsUtils.insertByteArray(buff,
						INDEX_FRONT_CAMERA_WIDTH_PIXEL,
						INDEX_FRONT_CAMERA_WIDTH_PIXEL + NV_ITEM_OFFSET + 1,
						bytes);

				bytes = ParamSettingsUtils.getByteArray(frontHeight);
				buff = ParamSettingsUtils.insertByteArray(buff,
						INDEX_FRONT_CAMERA_HEIGHT_PIXEL,
						INDEX_FRONT_CAMERA_HEIGHT_PIXEL + NV_ITEM_OFFSET + 1,
						bytes);

				bytes = ParamSettingsUtils.getByteArray(back);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_BACK_CAMERA_PIXEL,
						INDEX_BACK_CAMERA_PIXEL + NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(backWidth);
				buff = ParamSettingsUtils.insertByteArray(buff,
						INDEX_BACK_CAMERA_WIDTH_PIXEL,
						INDEX_BACK_CAMERA_WIDTH_PIXEL + NV_ITEM_OFFSET + 1,
						bytes);

				bytes = ParamSettingsUtils.getByteArray(backHeight);
				buff = ParamSettingsUtils.insertByteArray(buff,
						INDEX_BACK_CAMERA_HEIGHT_PIXEL,
						INDEX_BACK_CAMERA_HEIGHT_PIXEL + NV_ITEM_OFFSET + 1,
						bytes);

				bytes = ParamSettingsUtils.getByteArray(video);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_VIDEO_QUALITY,
						INDEX_VIDEO_QUALITY + NV_ITEM_OFFSET + 1, bytes);

				int flag = agent.writeFile(PARAM_SETTING_ID, buff);
				if (flag > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			Log.e(this, "writeCpuParamToNV=>error: ", e);
		}
		 {
			printNvValue();
		}
		return result;
	}

	@Override
	public boolean writeOtherParamToNV(Other other) {
		return writeOtherParamToNV(other.supportAllSensor, other.supportRoot,
				other.benchmark);
	}

	@Override
	public boolean writeOtherParamToNV(String sensor, String root,
			String benchmark) {
		
			Log.d(this, "writeOtherParamToNV=>sensor: " + sensor + " root: "
					+ root + " benchmark: " + benchmark);
		boolean result = false;
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			if (buff != null
					&& buff.length > (INDEX_BENCHMARK + NV_ITEM_OFFSET)) {
				byte[] bytes = ParamSettingsUtils.getByteArray(sensor);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_SUPPORT_ALL_SENSOR,
						INDEX_SUPPORT_ALL_SENSOR + NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(root);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_SUPPORT_ROOT,
						INDEX_SUPPORT_ROOT + NV_ITEM_OFFSET + 1, bytes);

				bytes = ParamSettingsUtils.getByteArray(benchmark);
				buff = ParamSettingsUtils.insertByteArray(buff, INDEX_BENCHMARK,
						INDEX_BENCHMARK + NV_ITEM_OFFSET + 1, bytes);

				int flag = agent.writeFile(PARAM_SETTING_ID, buff);
				if (flag > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			Log.e(this, "writeCpuParamToNV=>error: ", e);
		}
		 {
			printNvValue();
		}
		return result;
	}
	
	@Override
	public Cpu getCpuParamFromNV() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			 Log.d(this, "getCpuParamFromNV=>length: " + (buff != null ? buff.length : " null"));
			if (buff != null && buff.length > (INDEX_BENCHMARK + NV_ITEM_OFFSET)) {
				String type = ParamSettingsUtils.getString(buff, INDEX_CPU_TYPE, INDEX_CPU_TYPE + NV_ITEM_OFFSET);
				String core = ParamSettingsUtils.getString(buff, INDEX_CPU_CORE, INDEX_CPU_CORE + NV_ITEM_OFFSET);
				String minFreq = ParamSettingsUtils.getString(buff, INDEX_CPU_MIN_FREQ, INDEX_CPU_MIN_FREQ + NV_ITEM_OFFSET);
				String maxFreq = ParamSettingsUtils.getString(buff, INDEX_CPU_MAX_FREQ, INDEX_CPU_MAX_FREQ + NV_ITEM_OFFSET);
				return new Cpu(-1, type, core, minFreq, maxFreq, false);
			}
		} catch (Exception e) {
			Log.e(this, "getCpuParamFromNV=>error: ", e);
		}
		return null;
	}
	
	@Override
	public Memory getMemoryParamFromNV() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			 Log.d(this, "getMemoryParamFromNV=>length: " + (buff != null ? buff.length : " null"));
			if (buff != null && buff.length > (INDEX_BENCHMARK + NV_ITEM_OFFSET)) {
				String minRam = ParamSettingsUtils.formatStorageSize(ParamSettingsUtils.getStorageValue(ParamSettingsUtils.getString(buff,
									INDEX_MIN_RAM, INDEX_MIN_RAM + NV_ITEM_OFFSET)) * 1024L * 1024L);
				String maxRam = ParamSettingsUtils.formatStorageSize(ParamSettingsUtils.getStorageValue(ParamSettingsUtils.getString(buff,
									INDEX_MAX_RAM, INDEX_MAX_RAM + NV_ITEM_OFFSET)) * 1024L * 1024L);
				return new Memory(-1, minRam, maxRam, false);
			}
		} catch (Exception e) {
			Log.e(this, "getMemoryParamFromNV=>error: ", e);
		}
		return null;
	}
	
	@Override
	public Storage getStorageParamFromNV() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			 Log.d(this, "getStorageParamFromNV=>length: " + (buff != null ? buff.length : " null"));
			if (buff != null && buff.length > (INDEX_BENCHMARK + NV_ITEM_OFFSET)) {
				String systemStorage = ParamSettingsUtils.getString(buff, INDEX_SYSTEM_STORAGE, INDEX_SYSTEM_STORAGE + NV_ITEM_OFFSET);
				String internalStorage = ParamSettingsUtils.getString(buff, INDEX_INTERNAL_STORAGE, INDEX_INTERNAL_STORAGE + NV_ITEM_OFFSET);
				return new Storage(-1, systemStorage, internalStorage, false);
			}
		} catch (Exception e) {
			Log.e(this, "getStorageParamFromNV=>error: ", e);
		}
		return null;
	}
	
	@Override
	public Lcd getLcdParamFromNV() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			 Log.d(this, "getLcdParamFromNV=>length: " + (buff != null ? buff.length : " null"));
			if (buff != null && buff.length > (INDEX_BENCHMARK + NV_ITEM_OFFSET)) {
				String width = ParamSettingsUtils.getString(buff, INDEX_LCD_WIDTH, INDEX_LCD_WIDTH + NV_ITEM_OFFSET);
				String height = ParamSettingsUtils.getString(buff, INDEX_LCD_HEIGHT, INDEX_LCD_HEIGHT + NV_ITEM_OFFSET);
				String density = ParamSettingsUtils.getString(buff, INDEX_LCD_DENSITY, INDEX_LCD_DENSITY + NV_ITEM_OFFSET);
				return new Lcd(-1, width, height, density, false);
			}
		} catch (Exception e) {
			Log.e(this, "getLcdParamFromNV=>error: ", e);
		}
		return null;
	}
	
	@Override
	public Camera getCameraParamFromNV() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			Log.d(this, "getCameraParamFromNV=>length: " + (buff != null ? buff.length : " null"));
			if (buff != null && buff.length > (INDEX_BENCHMARK + NV_ITEM_OFFSET)) {
				String front = ParamSettingsUtils.getString(buff, INDEX_FRONT_CAMERA_PIXEL,
						INDEX_FRONT_CAMERA_PIXEL + NV_ITEM_OFFSET);
				String frontWidth = ParamSettingsUtils.getString(buff, INDEX_FRONT_CAMERA_PIXEL,
						INDEX_FRONT_CAMERA_PIXEL + NV_ITEM_OFFSET);
				String frontHeight = ParamSettingsUtils.getString(buff, INDEX_FRONT_CAMERA_HEIGHT_PIXEL,
						INDEX_FRONT_CAMERA_HEIGHT_PIXEL + NV_ITEM_OFFSET);
				String back = ParamSettingsUtils.getString(buff, INDEX_BACK_CAMERA_PIXEL, INDEX_BACK_CAMERA_PIXEL + NV_ITEM_OFFSET);
				String backWidth = ParamSettingsUtils.getString(buff, INDEX_BACK_CAMERA_WIDTH_PIXEL,
						INDEX_BACK_CAMERA_WIDTH_PIXEL + NV_ITEM_OFFSET);
				String backHeight = ParamSettingsUtils.getString(buff, INDEX_BACK_CAMERA_HEIGHT_PIXEL,
						INDEX_BACK_CAMERA_HEIGHT_PIXEL + NV_ITEM_OFFSET);
				String video = ParamSettingsUtils.getString(buff, INDEX_VIDEO_QUALITY, INDEX_VIDEO_QUALITY + NV_ITEM_OFFSET);
				return new Camera(-1, front, frontWidth, frontHeight, back, backWidth, backHeight, video, false);
			}
		} catch (Exception e) {
			Log.e(this, "getCameraParamFromNV=>error: ", e);
		}
		return null;
	}
	
	@Override
	public Other getOtherParamFromNV() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
			buff = agent.readFile(PARAM_SETTING_ID);
			Log.d(this, "getOtherParamFromNV=>length: " + (buff != null ? buff.length : " null"));
			if (buff != null && buff.length > (INDEX_BENCHMARK + NV_ITEM_OFFSET)) {
				String sensor = ParamSettingsUtils.getString(buff, INDEX_SUPPORT_ALL_SENSOR, INDEX_SUPPORT_ALL_SENSOR + NV_ITEM_OFFSET);
				String root = ParamSettingsUtils.getString(buff, INDEX_SUPPORT_ROOT, INDEX_SUPPORT_ROOT + NV_ITEM_OFFSET);
				String banchmark = ParamSettingsUtils.getString(buff, INDEX_BENCHMARK, INDEX_BENCHMARK + NV_ITEM_OFFSET);
				return new Other(-1, sensor, root, banchmark, false);
			}
		} catch (Exception e) {
			Log.e(this, "getOtherParamFromNV=>error: ", e);
		}
		return null;
	}
	
	@Override
	public boolean resetAllParamToFactory() {
		resetCpuParamToFactory();
		resetMemoryParamToFactory();
		resetStorageParamToFactory();
		resetLcdParamToFactory();
		resetCameraParamToFactory();
		resetOtherParamToFactory();
		return true;
	}

	@Override
	public boolean resetCpuParamToFactory() {
		String type = getParam(KEY_CPU_TYPE_BACKUP, "");
		String core = getParam(KEY_CPU_CORE_BACKUP, "");
		String minFreq = getParam(KEY_CPU_MIN_FREQ_BACKUP, "");
		String maxFreq = getParam(KEY_CPU_MAX_FREQ_BACKUP, "");
		setCpuParam(type, core, minFreq, maxFreq);
		writeCpuParamToNV(type, core, minFreq, maxFreq);
		return true;
	}

	@Override
	public boolean resetMemoryParamToFactory() {
		String minRam = getParam(KEY_MIN_RAM_BACKUP, "");
		String maxRam = getParam(KEY_MAX_RAM_BACKUP, "");
		setMemoryParam(minRam, maxRam);
		writeMemoryParamToNV(minRam, maxRam);
		return true;
	}

	@Override
	public boolean resetStorageParamToFactory() {
		String system = getParam(KEY_SYSTEM_STORAGE_BACKUP, "");
		String internal = getParam(KEY_INTERNAL_STORAGE_BACKUP, "");
		setStorageParam(system, internal);
		writeStorageParamToNV(system, internal);
		return true;
	}

	@Override
	public boolean resetLcdParamToFactory() {
		String width = getParam(KEY_LCD_WIDTH_BACKUP, "");
		String height = getParam(KEY_LCD_HEIGHT_BACKUP, "");
		String density = getParam(KEY_LCD_DENSITY_BACKUP, "");
		setLcdParam(width, height, density);
		writeLcdParamToNV(width, height, density);
		return true;
	}

	@Override
	public boolean resetCameraParamToFactory() {
		String front = getParam(KEY_FRONT_CAMERA_PIXEL_BACKUP, "");
		String frontWidth = getParam(KEY_FRONT_CAMERA_WIDTH_PIXEL_BACKUP, "");
		String frontHeight = getParam(KEY_FRONT_CAMERA_HEIGHT_PIXEL_BACKUP, "");
		String back = getParam(KEY_BACK_CAMERA_PIXEL_BACKUP, "");
		String backWidth = getParam(KEY_BACK_CAMERA_WIDTH_PIXEL_BACKUP, "");
		String backHeight = getParam(KEY_BACK_CAMERA_HEIGHT_PIXEL_BACKUP, "");
		String video = getParam(KEY_VIDEO_QUALITY_BACKUP, "");
		setCameraParam(front, frontWidth, frontHeight, back, backWidth, backHeight, video);
		writeCameraParamToNV(front, frontWidth, frontHeight, back, backWidth, backHeight, video);
		return true;
	}

	@Override
	public boolean resetOtherParamToFactory() {
		String sensor = getParam(KEY_SUPPORT_ALL_SENSOR_BACKUP, "false");
		String root = getParam(KEY_SUPPORT_ROOT_BACKUP, "false");
		String benchmark = getParam(KEY_BENCHMARK_BACKUP, "");
		setOtherParam(sensor, root, benchmark);
		writeOtherParamToNV(sensor, root, benchmark);
		return true;
	}

	@Override
	public void printNvValue() {
		Log.d(this, "printNvValue=>Cpu: " + getCpuParamFromNV());
		Log.d(this, "printNvValue=>Memory: " + getMemoryParamFromNV());
		Log.d(this, "printNvValue=>Storage: " + getStorageParamFromNV());
		Log.d(this, "printNvValue=>Lcd: " + getLcdParamFromNV());
		Log.d(this, "printNvValue=>Camera: " + getCameraParamFromNV());
		Log.d(this, "printNvValue=>Other: " + getOtherParamFromNV());
	}

}
