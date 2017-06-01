package com.dgl.www.twodimensioncode.ui;

import android.app.Activity;
import android.os.Bundle;

import com.dgl.www.twodimensioncode.R;

/**
 * Created by dugaolong on 17/3/1.
 */

public class HistoryActivity extends Activity {
    private static final String TAG = "HistoryActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        setTitle("历史记录");
    }
}
