package com.qty.scanner;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Administrator on 15-12-8.
 */
public class ScannerUtils {
    private static final boolean DEBUG = true;
    private static final String EXTERNAL_VOLUME = "external";
    private static final String INTERNAL_VOLUME = "internal";
    private static final String TAG = "ScannerUtils";

    private static void scan(Context context, String path)
    {
        Bundle bundle = new Bundle();
        bundle.putString("volume", path);
        Intent service = new Intent("android.media.IMediaScannerService").putExtras(bundle);
        service.setComponent(new ComponentName("com.android.providers.media", "com.android.providers.media.MediaScannerService"));
        service.setPackage("com.android.providers.media");
        context.startService(service);
    }

    public static void scanAllStorage(Context context)
    {
        Toast.makeText(context, "start Scan ...", Toast.LENGTH_SHORT).show();
        scan(context, INTERNAL_VOLUME);
        scan(context, EXTERNAL_VOLUME);
    }

    public static void scanExternalStorage(Context context)
    {
        Toast.makeText(context, "start Scan ...", Toast.LENGTH_SHORT).show();
        scan(context, EXTERNAL_VOLUME);
    }

    private static void scanFile(Context context, String path)
    {
        Bundle bundle = new Bundle();
        bundle.putString("filepath", path);
        Intent service = new Intent("android.media.IMediaScannerService").putExtras(bundle);
        service.setComponent(new ComponentName("com.android.providers.media", "com.android.providers.media.MediaScannerService"));
        service.setPackage("com.android.providers.media");
        context.startService(service);
    }

    public static void scanFolder(Context context, String path)
    {
        if (!TextUtils.isEmpty(path))
        {
            Toast.makeText(context, "start Scan ...", Toast.LENGTH_SHORT).show();
            scanFile(context, path);
            return;
        }
        Toast.makeText(context, "Folder path is Empty", Toast.LENGTH_SHORT).show();
    }

    public static void scanInternalStorage(Context context)
    {
        Toast.makeText(context, "start Scan ...", Toast.LENGTH_SHORT).show();
        scan(context, INTERNAL_VOLUME);
    }
}