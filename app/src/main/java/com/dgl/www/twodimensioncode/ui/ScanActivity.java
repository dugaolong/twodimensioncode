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
import android.widget.Toast;

import com.dgl.www.twodimensioncode.R;
import com.netease.scan.IScanModuleCallBack;
import com.netease.scan.QrScan;
import com.netease.scan.ui.CaptureActivity;

/**
 * Created by dugaolong on 17/3/1.
 */

public class ScanActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_CODE_SCAN = 0x0000;
    private Context mContext;
    private CaptureActivity mCaptureContext;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scan);
        mContext = this;

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
                mCaptureContext = (CaptureActivity) context;
                Log.i("ScanActivity:", "result:" + result);
                try {
                    gotoWebView(result);
                }catch (Exception e){
                    e.printStackTrace();
                }

//                AlertDialog dialog = new AlertDialog.Builder(mCaptureContext)
//                        .setMessage(result)
//                        .setCancelable(false)
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                QrScan.getInstance().restartScan(mCaptureContext);
//                            }
//                        })
//                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                QrScan.getInstance().finishScan(mCaptureContext);
//                            }
//                        })
//                        .create();
//                dialog.show();

            }
        });
    }

    /*
   H5页面跳转
    */
    protected void gotoWebView(final String url) {
        Intent intent = new Intent(this, BrowserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BrowserActivity.URL, url);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
