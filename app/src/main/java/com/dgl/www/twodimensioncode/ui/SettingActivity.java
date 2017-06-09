package com.dgl.www.twodimensioncode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dgl.www.twodimensioncode.R;
import com.dgl.www.twodimensioncode.config.StaticString;

/**
 * Created by dugaolong on 17/3/1.
 */

public class SettingActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "SettingActivity";
    private CheckBox playBeep,vibrate,copy;
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
}
