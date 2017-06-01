package com.dgl.www.twodimensioncode.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dgl.www.twodimensioncode.LogUtil;
import com.dgl.www.twodimensioncode.R;
import com.dgl.www.twodimensioncode.bean.QrCode;
import com.dgl.www.twodimensioncode.config.BitmapUtil;
import com.google.zxing.WriterException;

import static com.dgl.www.twodimensioncode.ToastUtils.showToast;

/**
 * Created by dugaolong on 17/3/1.
 */

public class StarActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "StarActivity";
    private Button createQr;
    private EditText editTextIn;
    private ImageView iv_qr_image;
    protected int mScreenWidth;
    private String editTextStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.star);
        editTextIn = (EditText) findViewById(R.id.editTextIn);
        createQr = (Button) findViewById(R.id.createQr);
        iv_qr_image = (ImageView) findViewById(R.id.iv_qr_image);
        editTextIn.setOnClickListener(this);
        createQr.setOnClickListener(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }

    //生成二维码
    public void Create2QR(String editTextUri) {
        if (TextUtils.isEmpty(editTextUri)) {
            showToast("填写内容为空");
        } else {
            Bitmap bitmap;
            try {
                bitmap = BitmapUtil.createQRCode(editTextUri, mScreenWidth);
                if (bitmap != null) {
                    iv_qr_image.setImageBitmap(bitmap);
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createQr:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                editTextStr = editTextIn.getText().toString();
                Create2QR(editTextStr);
                //插入数据库
                QrCode qrCode = new QrCode();
                qrCode.setTime(System.currentTimeMillis());
                qrCode.setContent(editTextStr);
                qrCode.setType(editTextStr.contains("http") ? 1 : 2);

                if (qrCode.save()) {
                    LogUtil.showLog(TAG, "插入数据库 成功");
                } else {
                    LogUtil.showLog(TAG, "插入数据库 失败");
                }
                break;
        }
    }
}
