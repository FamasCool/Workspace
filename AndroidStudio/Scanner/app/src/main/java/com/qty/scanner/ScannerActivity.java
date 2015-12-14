package com.qty.scanner;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ScannerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final boolean DEBUG = true;
    private static final String TAG = "ScannerActivity";
    private Button mCancel;
    private Dialog mFolderInputDialog;
    private EditText mFolderName;
    private Button mScan;
    private Button mScanAll;
    private Button mScanExternal;
    private Button mScanFolder;
    private Button mScanInternal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mScanAll = (Button) findViewById(R.id.scan_all_storage);
        mScanInternal = (Button) findViewById(R.id.scan_internal_storage);
        mScanExternal = (Button) findViewById(R.id.scan_external_storage);
        mScanFolder = (Button) findViewById(R.id.scan_folder);
        mScanAll.setOnClickListener(this);
        mScanInternal.setOnClickListener(this);
        mScanExternal.setOnClickListener(this);
        mScanFolder.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_all_storage:
                ScannerUtils.scanAllStorage(this);
                break;

            case R.id.scan_internal_storage:
                ScannerUtils.scanInternalStorage(this);
                break;

            case R.id.scan_external_storage:
                ScannerUtils.scanExternalStorage(this);
                break;

            case R.id.scan_folder:
                showInputPathFolder();
                break;

            case R.id.cancel:
                mFolderInputDialog.cancel();
                break;

            case R.id.scan:
                ScannerUtils.scanFolder(this, mFolderName.getText().toString());
                mFolderInputDialog.cancel();
                break;
        }
    }

    private void showInputPathFolder() {
        if (mFolderInputDialog == null) {
            createInputDialog();
        }
        mFolderInputDialog.show();
    }

    private void createInputDialog() {
        View dialogView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.scan_folder_dialog_layout, null);
        mFolderName = (EditText) dialogView.findViewById(R.id.folder_name);
        mCancel = (Button) dialogView.findViewById(R.id.cancel);
        mScan = (Button) dialogView.findViewById(R.id.scan);
        mCancel.setOnClickListener(this);
        mScan.setOnClickListener(this);
        mFolderInputDialog = new Dialog(this, R.style.folderInputDialogStyle);
        mFolderInputDialog.setContentView(dialogView);
    }
}
