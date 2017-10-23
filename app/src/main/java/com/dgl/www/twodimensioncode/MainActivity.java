/**
 * Copyright (C) 2014 Guangzhou QTONE Technologies Ltd.
 * <p/>
 * 本代码版权归广州全通教育股份有限公司所有，且受到相关的法律保护。没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 * @date ${date} ${time}
 * @version V1.0
 */
package com.dgl.www.twodimensioncode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dgl.www.twodimensioncode.config.StaticString;
import com.dgl.www.twodimensioncode.ui.GenerateActivity;
import com.dgl.www.twodimensioncode.ui.HistoryActivity;
import com.dgl.www.twodimensioncode.ui.ScanActivity;
import com.dgl.www.twodimensioncode.ui.SettingActivity;
import com.dgl.www.twodimensioncode.utils.LogUtil;
import com.xiaomi.ad.SplashAdListener;
import com.xiaomi.ad.adView.SplashAd;

import java.util.HashMap;

/**
 * 贵州校讯通主页入口
 *
 * @date 2014年9月25日 上午19:30:54
 */
@SuppressLint("NewApi")
public class MainActivity extends ActivityGroup implements OnClickListener ,ActivityCompat.OnRequestPermissionsResultCallback{

    private RadioButton star;// generate
    private RadioButton scan;// 扫描
    private RadioButton history;// history
    private RadioButton setting;// setting
    private FrameLayout container;
    private Context mContext;
    private static final String POSITION_ID = "c7a5c4045ea7fb90d89c1cad396c4538";//
    private ViewGroup mContainer;
    private static final String TAG = "MainActivity";
    public static final int REQUESTCODE_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {//api15 以上打开硬件加速
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        setContentView(R.layout.main_layout);
//        checkPermission();
//        try {
//            default_sound_state = Settings.System.getInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED);
//            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 10*60*1000);
//        } catch (SettingNotFoundException e) {
//            e.printStackTrace();
//        }
        mContext = this;
        initView();
        initListener();
        //请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// ANDROID6.0 请求权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUESTCODE_CAMERA);
            }
        }

        showActivity();
        mContainer = (ViewGroup) findViewById(R.id.splash_ad_container);
        SplashAd splashAd = new SplashAd(this, mContainer, R.drawable.splash_default_picture, new SplashAdListener() {
            @Override
            public void onAdPresent() {
                // 开屏广告展示
                Log.d(TAG, "onAdPresent");
            }

            @Override
            public void onAdClick() {
                //用户点击了开屏广告
                Log.d(TAG, "onAdClick");
            }

            @Override
            public void onAdDismissed() {
                //这个方法被调用时，表示从开屏广告消失。
                Log.d(TAG, "onAdDismissed");
            }

            @Override
            public void onAdFailed(String s) {
                Log.d(TAG, "onAdFailed, message: " + s);
            }
        });
        splashAd.requestAd(POSITION_ID);

    }
//    private void checkPermission() {
//        if (!Settings.System.canWrite(this)) {
//
//            Uri selfPackageUri = Uri.parse("package:"
//                    + this.getPackageName());
//            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
//                    selfPackageUri);
//            startActivity(intent);
//            ToastUtils.showToast("请在该设置页面勾选，才可以使用扫描二维码功能");
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
//        user_imageview = (ImageView) findViewById(R.id.user_imageview);
//        user_imageview.setOnClickListener(this);
        container = (FrameLayout) findViewById(R.id.fragmentRoot);
        star = (RadioButton) findViewById(R.id.star_button);
        scan = (RadioButton) findViewById(R.id.scan_button);
        history = (RadioButton) findViewById(R.id.history_button);
        setting = (RadioButton) findViewById(R.id.setting_button);

    }

    private HashMap<String, String> mhashmap = new HashMap<String, String>();

    private void launchActivity(String id, Class<?> activityClass) {
        container.removeAllViews();
        mhashmap.put(id, id);
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(StaticString.NOLOGINTYPE, id);
        Bundle data = new Bundle();

        Window window = getLocalActivityManager().startActivity(id, intent);
        View mview = window.getDecorView();
        mview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        if (id.equals(StaticString.ScanActivity)) {
            mview.setTag(StaticString.ScanActivity);
            container.addView(mview);
        } else if (id.equals(StaticString.StarActivity)) {
            mview.setTag(StaticString.StarActivity);
            container.addView(mview);
        } else if (id.equals(StaticString.HistoryActivity)) {
            mview.setTag(StaticString.HistoryActivity);
            container.addView(mview);
        } else if (id.equals(StaticString.SettingActivity)) {
            mview.setTag(StaticString.SettingActivity);
            container.addView(mview);
        }
    }

    private void initListener() {
        scan.setOnClickListener(this);
        star.setOnClickListener(this);
        history.setOnClickListener(this);
        setting.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
//            case R.id.user_imageview:
//                break;
            case R.id.scan_button:
//                user_imageview.setVisibility(View.VISIBLE);
                launchActivity(StaticString.ScanActivity, ScanActivity.class);
                break;
            case R.id.star_button:
//                user_imageview.setVisibility(View.GONE);
                launchActivity(StaticString.StarActivity, GenerateActivity.class);
                break;
            case R.id.history_button:
//                user_imageview.setVisibility(View.GONE);
                launchActivity(StaticString.HistoryActivity, HistoryActivity.class);

                break;
            case R.id.setting_button:
//                user_imageview.setVisibility(View.GONE);
                LogUtil.showLog("app", "go in  setting");
                try {
                    launchActivity(StaticString.SettingActivity, SettingActivity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            default:
                break;
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        finish();
        LogUtil.showLog("app", "退出返回桌面了");
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Settings.System.putInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, default_sound_state == -1 ? 0 : default_sound_state);
    }
    /*显示tab按钮的图标
        */
    private void showActivity() {
        Drawable myImage = this.getResources().getDrawable(R.drawable.star);
        star.setCompoundDrawablesWithIntrinsicBounds(null, myImage, null, null);
        myImage = this.getResources().getDrawable(R.drawable.scan);
        scan.setCompoundDrawablesWithIntrinsicBounds(null, myImage, null, null);
        myImage = this.getResources().getDrawable(R.drawable.history);
        history.setCompoundDrawablesWithIntrinsicBounds(null, myImage, null, null);
        myImage = this.getResources().getDrawable(R.drawable.setting);
        setting.setCompoundDrawablesWithIntrinsicBounds(null, myImage, null, null);
        launchActivity(StaticString.ScanActivity, ScanActivity.class);
//        msg.setText(R.string.study_circle);
        scan.setChecked(true);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // 捕获back键，在展示广告期间按back键，不跳过广告
            if (mContainer.getVisibility() == View.VISIBLE) {
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (requestCode == REQUESTCODE_CAMERA) {
                    if(grantResults.length>0){
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            // Permission Granted
//                        Toast.makeText(this,"You Granted the permission",Toast.LENGTH_LONG).show();
                        } else {
                            // Permission Denied
//                        Toast.makeText(this,"You denied the permission",Toast.LENGTH_LONG).show();
                            Toast.makeText(this,"您禁止了相机权限!",Toast.LENGTH_LONG).show();
                        }
                    }

                }
                break;
            default:
        }
    }
}
