package com.dgl.www.twodimensioncode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.dgl.www.twodimensioncode.R;

/**
 * Created by dugaolong on 17/3/1.
 */

public class StarActivity extends Activity {

    private static final String TAG = "StarActivity";
    protected DisplayMetrics metric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.star);
    }
}
