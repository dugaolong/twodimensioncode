package com.dgl.www.twodimensioncode.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dgl.www.twodimensioncode.R;
import com.netease.scan.IScanModuleCallBack;
import com.netease.scan.QrScan;
import com.netease.scan.ui.CaptureActivity;

/**
 * Created by dugaolong on 17/3/1.
 */

public class ScanActivity extends Activity implements View.OnClickListener {

    private Context mContext;
    private CaptureActivity mCaptureContext;
    private Toolbar mToolbar;
    private TextView textView;
    private LinearLayout linearLayout;
    private String resultlStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scan);
        mContext = this;

        textView = (TextView) findViewById(R.id.result);
        linearLayout = (LinearLayout) findViewById(R.id.resultLl);
        findViewById(R.id.scanBtn).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scanBtn:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    launch();
                }
                break;

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launch();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }

    public void launch() {
        QrScan.getInstance().launchScan(mContext, new IScanModuleCallBack() {
            @Override
            public void OnReceiveDecodeResult(final Context context, String result) {
                resultlStr = result;
                mCaptureContext = (CaptureActivity) context;
                Log.i("ScanActivity:", "result:" + result);
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("uri",result);
                    Intent intent = new Intent(ScanActivity.this,ResultActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


}
