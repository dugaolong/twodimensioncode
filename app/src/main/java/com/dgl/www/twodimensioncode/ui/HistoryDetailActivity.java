package com.dgl.www.twodimensioncode.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgl.www.twodimensioncode.R;
import com.dgl.www.twodimensioncode.bean.QrCode;

/**
 * Created by dugaolong on 17/3/1.
 */

public class HistoryDetailActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private TextView content,tv_title,btn_back_return;
    private ImageView imageView,btn_back;
    private QrCode qrCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_detail);
        mContext = this;
        Intent intent = getIntent();
        qrCode = (QrCode) intent.getSerializableExtra("data");

        content = (TextView) findViewById(R.id.content);
        tv_title = (TextView) findViewById(R.id.tv_title);
        btn_back_return = (TextView) findViewById(R.id.btn_back_return);
        btn_back_return.setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.image);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        tv_title.setText(qrCode.getType()==1?"链接":"文本");
        content.setText(qrCode.getContent());
        if (null != qrCode.getBlob()) {
            Bitmap imagebitmap = BitmapFactory.decodeByteArray(qrCode.getBlob(), 0, qrCode.getBlob().length);
            //将位图显示为图片
            imageView.setImageBitmap(imagebitmap);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_back_return:
                finish();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
