package com.android.androidtool.paramsettings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.android.androidtool.Log;
import com.android.androidtool.R;
import com.android.androidtool.ShellExe;
import com.android.androidtool.paramsettings.model.Camera;
import com.android.androidtool.paramsettings.model.Cpu;
import com.android.androidtool.paramsettings.model.Lcd;
import com.android.androidtool.paramsettings.model.Memory;
import com.android.androidtool.paramsettings.model.Other;
import com.android.androidtool.paramsettings.model.Storage;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;

public class ParamSettingsUtils {
	private static final String TAG = "ParamSettingsUtils";
	public static final long KB = 1024;
	public static final long MB = KB * 1024;
	public static final long GB = MB * 1024;
	public static final long TB = GB * 1024;

	public static final long KHZ = 1000;
	public static final long MHZ = KHZ * 1000;
	public static final long GHZ = MHZ * 1000;

	public static ArrayList<String> getTitleList(Context context) {
		Resources res = context.getResources();
		ArrayList<String> titleList = new ArrayList<String>();
		String[] titles = res.getStringArray(R.array.param_settings_titles);
		for (String str : titles) {
			titleList.add(str);
		}
		return titleList;
	}

	public static ArrayList<Integer> getSettingsBackgroundList(Context context) {
		Resources res = context.getResources();
		ArrayList<Integer> backgroundList = new ArrayList<Integer>();
		TypedArray icons = res.obtainTypedArray(R.array.param_settings_icons);
		for (int i = 0; i < icons.length(); i++) {
			backgroundList.add(icons.getResourceId(i, 0));
		}
		icons.recycle();
		return backgroundList;
	}

	public static ArrayList<Cpu> getCpuList(Context context) {
		Resources res = context.getResources();
		ArrayList<Cpu> list = new ArrayList<Cpu>();
		String[] arrays = res.getStringArray(R.array.cpu_settings);
		String[] strs;
		for (String str : arrays) {
			strs = str.split(";");
			list.add(new Cpu(R.drawable.settings_adapter_item_background, strs[0], strs[1], strs[2], strs[3], false));
		}
		list.add(new Cpu(R.drawable.settings_adapter_item_background, "", "", "", "", true));
		return list;
	}

	public static ArrayList<Memory> getMemoryList(Context context) {
		Resources res = context.getResources();
		ArrayList<Memory> list = new ArrayList<Memory>();
		String[] arrays = res.getStringArray(R.array.memory_settings);
		String[] strs;
		for (String str : arrays) {
			strs = str.split(";");
			list.add(new Memory(R.drawable.settings_adapter_item_background, strs[0], strs[1], false));
		}
		list.add(new Memory(R.drawable.settings_adapter_item_background, "", "", true));
		return list;
	}

	public static ArrayList<Storage> getStorageList(Context context) {
		Resources res = context.getResources();
		ArrayList<Storage> list = new ArrayList<Storage>();
		String[] arrays = res.getStringArray(R.array.storage_settings);
		String[] strs;
		for (String str : arrays) {
			strs = str.split(";");
			list.add(new Storage(R.drawable.settings_adapter_item_background, strs[0], strs[1], false));
		}
		list.add(new Storage(R.drawable.settings_adapter_item_background, "", "", true));
		return list;
	}

	public static ArrayList<Lcd> getLcdList(Context context) {
		Resources res = context.getResources();
		ArrayList<Lcd> list = new ArrayList<Lcd>();
		String[] arrays = res.getStringArray(R.array.lcd_settings);
		String[] strs;
		for (String str : arrays) {
			strs = str.split(";");
			list.add(new Lcd(R.drawable.settings_adapter_item_background, strs[0], strs[1], strs[2], false));
		}
		list.add(new Lcd(R.drawable.settings_adapter_item_background, "", "", "", true));
		return list;
	}

	public static ArrayList<Camera> getCameraList(Context context) {
		Resources res = context.getResources();
		ArrayList<Camera> list = new ArrayList<Camera>();
		String[] arrays = res.getStringArray(R.array.camera_settings);
		String[] strs;
		for (String str : arrays) {
			strs = str.split(";");
			list.add(new Camera(R.drawable.settings_adapter_item_background, strs[0], strs[1], strs[2], false));
		}
		list.add(new Camera(R.drawable.settings_adapter_item_background, "", "", "", true));
		return list;
	}

	public static ArrayList<Other> getOtherList(Context context) {
		Resources res = context.getResources();
		ArrayList<Other> list = new ArrayList<Other>();
		String[] arrays = res.getStringArray(R.array.other_settings);
		String[] strs;
		for (String str : arrays) {
			strs = str.split(";");
			list.add(new Other(R.drawable.settings_adapter_item_background, strs[0], strs[1], strs[2], false));
		}
		list.add(new Other(R.drawable.settings_adapter_item_background, "", "", "", true));
		return list;
	}

	public static byte[] getByteArray(String value) {
		Log.d(TAG, "getByteArray=>value: " + value);
		if (TextUtils.isEmpty(value)) {
			return null;
		}

		int length = value.length();
		if (length < 10) {
			for (int i = 0; i < (10 - length); i++) {
				value += " ";
			}
		} else if (length > 10) {
			value = value.substring(0, 10);
		}

		return value.getBytes();
	}

	public static byte[] insertByteArray(byte[] target, int start, int end, byte[] value) {
		if (target != null && value != null) {
			if (target.length > end && (value.length == (end - start - 1))) {
				for (int i = 0; i < value.length; i++) {
					target[start + i] = value[i];
				}
			}
		}
		Log.d(TAG, "insertByteArray=>start: " + start + " end: " + end + " target: " + target + " value: " + value);
		return target;
	}

	public static String getString(byte[] value, int start, int end) {
		byte[] bytes = Arrays.copyOfRange(value, start, end);
		String result = new String(bytes);
		Log.d(TAG, "getString=>length: " + result.length());
		return result.trim();
	}

	public static int getStorageValue(String value) {
		int size = -1;
		boolean isGB = false;
		if (!TextUtils.isEmpty(value)) {
			int index = value.indexOf("MB");
			if (index < 0) {
				index = value.indexOf("GB");
				if (index >= 0) {
					isGB = true;
				}
			}
			Log.d(TAG, "getStorageValue=>index: " + index);
			if (index >= 0) {
				value = value.substring(0, index);
			}
			try {
				value = value.trim();
				size = Integer.valueOf(value);
				if (isGB) {
					size *= 1024;
				}
			} catch (Exception e) {
				Log.e(TAG, "getStorageValue=>error: ", e);
				size = -1;
			}
		}
		Log.d(TAG, "getStorageValue=>size: " + size + " value: " + value);
		return size;
	}

	public static String formatStorageSize(long value) {
		String size = " ";
		long KB = 1024L;
		long MB = KB * 1024L;
		long GB = MB * 1024L;
		if (value >= 0) {
			if (value < KB) {
				size = value + " B";
			} else if (value >= KB && value < MB) {
				size = value / KB + " KB";
			} else if (value >= MB && value < GB) {
				size = value / MB + " MB";
			} else if (value >= GB) {
				size = value / GB + " GB";
			}
		}
		Log.d(TAG, "formatStorageSize=>size: " + size + " value: " + value);
		return size;
	}

	public static String getStorageMBString(String value) {
		String size = " ";
		boolean isGB = false;
		if (!TextUtils.isEmpty(value)) {
			int index = value.indexOf("MB");
			if (index < 0) {
				index = value.indexOf("GB");
				isGB = true;
			}
			Log.d(TAG, "getStorageValue=>index: " + index);
			if (index >= 0) {
				value = value.substring(0, index).trim();
				try {
					int i = Integer.valueOf(value);
					if (isGB) {
						i *= 1024;
					}
					size = "" + i;
				} catch (Exception e) {
					Log.e(TAG, "getStorageValue=>error: ", e);
					size = null;
				}
			}
		}
		Log.d(TAG, "getStorageMBString=>size: " + size + " value: " + value);
		return size;
	}

	public static String getStorageGBString(String gb) {
		if (!TextUtils.isEmpty(gb)) {
			int index = gb.indexOf("GB");
			if (index > 0) {
				gb = gb.substring(0, index).trim();
			}
		} else {
			gb = " ";
		}
		return gb;
	}

	public static String calculateWidth(String pix) {
		String width = "";
		if (!TextUtils.isEmpty(pix)) {
			try {
				int p = Integer.valueOf(pix) * 10000;
				int w = (int) Math.sqrt(p / (48.0 * 64.0)) * 48;
				width = "" + w;
			} catch (Exception e) {
				Log.d(TAG, "calculateWidth=>error: " + e.toString());
			}
		}
		Log.d(TAG, "calculateWidth=>width: " + width);
		return width;
	}

	public static String calculateHeight(String pix) {
		String height = "";
		if (!TextUtils.isEmpty(pix)) {
			try {
				int p = Integer.valueOf(pix) * 10000;
				int h = (int) Math.sqrt(p / (48.0 * 64.0)) * 64;
				height = "" + h;
			} catch (Exception e) {
				Log.d(TAG, "calculateHeight=>error: " + e.toString());
			}
		}
		Log.d(TAG, "calculateHeight=height: " + height);
		return height;
	}

	public static boolean execCommand(String cmd) {
		boolean result = false;
		Log.d(TAG, "execCommand=>command: " + cmd);
		try {
			int value = ShellExe.execCommand(cmd);
			if (value == 0) {
				String outStr = ShellExe.getOutput();
				Log.d(TAG, "execCommand=>execResult: " + outStr);
				result = true;
			} else if (value == -1) {
				Log.e(TAG, "execCommand=>error: exec command fail.");
			} else if (value == -2) {
				Log.e(TAG, "execCommand=>error: exec command exception fail.");
			}
		} catch (Exception e) {
			Log.e(TAG, "execCommand=>error: ", e);
		}
		return result;
	}

	public static void writeInfoToPointer(String path, String msg) {
		File awakeTimeFile = new File(path);
		FileWriter fr = null;
		try {
			fr = new FileWriter(awakeTimeFile);
			fr.write(msg);
			fr.close();
		} catch (IOException e) {
			Log.d(TAG, "writeInfoToPointer=>error: ", e);
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (Exception e) {
				}
			}
		}
		Log.d(TAG, "writeInfoToPointer=>path" + path + " msg: " + msg);
	}
}
