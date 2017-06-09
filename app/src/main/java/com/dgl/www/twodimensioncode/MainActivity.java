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

import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.dgl.www.twodimensioncode.config.XXTActivityString;
import com.dgl.www.twodimensioncode.ui.GenerateActivity;
import com.dgl.www.twodimensioncode.ui.HistoryActivity;
import com.dgl.www.twodimensioncode.ui.ScanActivity;
import com.dgl.www.twodimensioncode.ui.SettingActivity;
import com.dgl.www.twodimensioncode.utils.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贵州校讯通主页入口
 *
 * @date 2014年9月25日 上午19:30:54
 */
@SuppressLint("NewApi")
public class MainActivity extends ActivityGroup implements OnClickListener {

    private RadioButton star;// star
    private RadioButton scan;// 扫描
    private RadioButton history;// history
    private RadioButton setting;// setting
    private FrameLayout container;
    private Context mContext;
    private static int default_sound_state = -1;
    private List<Map<String, String>> list;
//    private ImageView user_imageview;//用户头像



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
        try {
            default_sound_state = Settings.System.getInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED);
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 10*60*1000);
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }
        mContext = this;
        //PushManager.getInstance().initialize(this.getApplicationContext());
        //stopService(new Intent(this, MsgService.class));
        initView();
        initListener();

        showActivity();
    }



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
        intent.putExtra(XXTActivityString.NOLOGINTYPE, id);
        Bundle data = new Bundle();

        Window window = getLocalActivityManager().startActivity(id, intent);
        View mview = window.getDecorView();
        mview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        if (id.equals(XXTActivityString.ScanActivity)) {
            mview.setTag(XXTActivityString.ScanActivity);
            container.addView(mview);
        } else if (id.equals(XXTActivityString.StarActivity)) {
            mview.setTag(XXTActivityString.StarActivity);
            container.addView(mview);
        } else if (id.equals(XXTActivityString.HistoryActivity)) {
            mview.setTag(XXTActivityString.HistoryActivity);
            container.addView(mview);
        } else if (id.equals(XXTActivityString.SettingActivity)) {
            mview.setTag(XXTActivityString.SettingActivity);
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
                launchActivity(XXTActivityString.ScanActivity, ScanActivity.class);
                break;
            case R.id.star_button:
//                user_imageview.setVisibility(View.GONE);
                launchActivity(XXTActivityString.StarActivity, GenerateActivity.class);
                break;
            case R.id.history_button:
//                user_imageview.setVisibility(View.GONE);
                launchActivity(XXTActivityString.HistoryActivity, HistoryActivity.class);

                break;
            case R.id.setting_button:
//                user_imageview.setVisibility(View.GONE);
                LogUtil.showLog("app", "go in  setting");
                try {
                    launchActivity(XXTActivityString.SettingActivity, SettingActivity.class);
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
//        Settings.System.putInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, default_sound_state == -1 ? 0 : default_sound_state);
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
        launchActivity(XXTActivityString.ScanActivity, ScanActivity.class);
//        msg.setText(R.string.study_circle);
        scan.setChecked(true);
    }
}
