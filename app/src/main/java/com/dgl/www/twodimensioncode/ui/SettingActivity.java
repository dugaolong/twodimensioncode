package com.dgl.www.twodimensioncode.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.dgl.www.twodimensioncode.R;
import com.dgl.www.twodimensioncode.config.StaticString;

/**
 * Created by dugaolong on 17/3/1.
 */

public class SettingActivity extends Activity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener {
    private static final String TAG = "SettingActivity";
    private CheckBox playBeep,vibrate,copy;
    private LinearLayout share_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        setTitle("设置");

        playBeep = (CheckBox) findViewById(R.id.cb_playBeep);
        vibrate = (CheckBox) findViewById(R.id.cb_vibrate);
        copy = (CheckBox) findViewById(R.id.cb_copy);
        playBeep.setOnCheckedChangeListener(this);
        vibrate.setOnCheckedChangeListener(this);
        copy.setOnCheckedChangeListener(this);
        share_layout = (LinearLayout) findViewById(R.id.share_layout);
        share_layout.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()){
            case R.id.cb_playBeep:
                if (isChecked) {
                    StaticString.playBeep=true;
                }
                else {
                    StaticString.playBeep=false;
                }
                break;
            case R.id.cb_vibrate:
                if (isChecked) {
                    StaticString.vibrate=true;
                }
                else {
                    StaticString.vibrate=false;
                }
                break;
            case R.id.cb_copy:
                if (isChecked) {
                    StaticString.copy=true;
                }
                else {
                    StaticString.copy=false;
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.share_layout:
                Intent intent = new Intent(SettingActivity.this,SharePopup.class);
                Bundle data = new Bundle();
                data.putString(SharePopup.SCENE, "2");
                data.putString(SharePopup.TITLE, "扫描二维码");
                data.putString(SharePopup.CONTENT, "扫描二维码应用让你的生活更简单！");
                data.putString(SharePopup.URL, "http://app.mi.com/details?id=www.dugaolong.com.xianshishigongjiao");
                intent.putExtras(data);
                startActivity(intent);
                break;
        }
    }
}
