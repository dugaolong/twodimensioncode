package com.dgl.www.twodimensioncode;

import android.widget.Toast;

/**
 * Created by dugaolong on 17/6/1.
 */

public class ToastUtils {
    private static Toast mToast;

    public static void showToast(String text) {
        if(mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
