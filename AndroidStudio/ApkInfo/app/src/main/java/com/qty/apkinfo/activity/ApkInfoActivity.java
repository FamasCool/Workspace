package com.qty.apkinfo.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qty.apkinfo.Log;
import com.qty.apkinfo.R;

public class ApkInfoActivity extends Activity implements View.OnClickListener {

    private Button mAllApksBtn;
    private Button mAllActivitiesBtn;
    private Button mAllServicesBtn;
    private Button mAllBroadcastsBtn;
    private Button mAllProvidersBtn;
    private Button mQueryApkBtn;
    private Button mQueryActivityBtn;
    private Button mQueryServiceBtn;
    private Button mQueryBroadcastBtn;
    private Button mQueryProviderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_info);

        initViews();
    }

    @Override
    public void onClick(View v) {
        Log.d(this, "onClick=>id: " + v.getId());
        switch (v.getId()) {
            case R.id.all_apks:

                break;

            case R.id.all_activities:

                break;

            case R.id.all_services:

                break;

            case R.id.all_broadcasts:

                break;

            case R.id.all_providers:

                break;

            case R.id.query_apk:

                break;

            case R.id.query_activity:

                break;

            case R.id.query_service:

                break;

            case R.id.query_broadcast:

                break;

            case R.id.query_provider:

                break;
        }
    }

    private void initViews() {
        mAllApksBtn = (Button) findViewById(R.id.all_apks);
        mAllActivitiesBtn = (Button) findViewById(R.id.all_activities);
        mAllServicesBtn = (Button) findViewById(R.id.all_services);
        mAllBroadcastsBtn = (Button) findViewById(R.id.all_broadcasts);
        mAllProvidersBtn = (Button) findViewById(R.id.all_providers);
        mQueryApkBtn = (Button) findViewById(R.id.query_apk);
        mQueryActivityBtn = (Button) findViewById(R.id.query_activity);
        mQueryServiceBtn = (Button) findViewById(R.id.query_service);
        mQueryBroadcastBtn = (Button) findViewById(R.id.query_broadcast);
        mQueryProviderBtn = (Button) findViewById(R.id.query_provider);

        mAllApksBtn.setOnClickListener(this);
        mAllActivitiesBtn.setOnClickListener(this);
        mAllServicesBtn.setOnClickListener(this);
        mAllBroadcastsBtn.setOnClickListener(this);
        mAllProvidersBtn.setOnClickListener(this);
        mQueryApkBtn.setOnClickListener(this);
        mQueryActivityBtn.setOnClickListener(this);
        mQueryServiceBtn.setOnClickListener(this);
        mQueryBroadcastBtn.setOnClickListener(this);
        mQueryProviderBtn.setOnClickListener(this);
    }

}
