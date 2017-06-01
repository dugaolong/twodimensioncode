package com.dgl.www.twodimensioncode.ui;


import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dgl.www.twodimensioncode.R;

import static com.dgl.www.twodimensioncode.ToastUtils.showToast;

/**
 * Created by dugaolong on 17/6/1.
 */

public class ResultActivity extends Activity implements View.OnClickListener {

    private Context mContext;
    private TextView textViewRe;
    private Button buttonCopy, gotoWeb;
    private LinearLayout linearLayout;
    private String resultStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        mContext = this;
        Intent intent = getIntent();
        resultStr = intent.getStringExtra("uri");


        textViewRe = (TextView) findViewById(R.id.result);
        buttonCopy = (Button) findViewById(R.id.buttonCopy);
        linearLayout = (LinearLayout) findViewById(R.id.resultLl);
        gotoWeb = (Button) findViewById(R.id.gotoWeb);
        buttonCopy = (Button) findViewById(R.id.buttonCopy);
        gotoWeb.setOnClickListener(this);
        buttonCopy.setOnClickListener(this);

        textViewRe.setText(resultStr);

        linearLayout.setVisibility(View.VISIBLE);
        textViewRe.setText(resultStr);
        if (resultStr.startsWith("http://") || resultStr.startsWith("https://")) {
            buttonCopy.setVisibility(View.VISIBLE);
            gotoWeb.setVisibility(View.VISIBLE);
        } else {
            buttonCopy.setVisibility(View.VISIBLE);
            gotoWeb.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotoWeb:
                gotoWebView(resultStr);
                break;
            case R.id.buttonCopy:
                copyStr(resultStr);
                break;
        }

    }

    /**
     * H5页面跳转
     */
    protected void gotoWebView(final String url) {
        Intent intent = new Intent(this, BrowserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BrowserActivity.URL, url);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //复制二维码
    public void copyStr(String resultStr){
        ClipboardManager cmb = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(resultStr); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
        cmb.getText();//获取粘贴信息
        showToast("已复制结果到粘贴板");
    }
}
