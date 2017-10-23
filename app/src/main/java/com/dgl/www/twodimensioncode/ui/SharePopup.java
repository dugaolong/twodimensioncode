package com.dgl.www.twodimensioncode.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.dgl.www.twodimensioncode.R;
import com.dgl.www.twodimensioncode.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;


public class SharePopup extends Activity implements OnClickListener {
    /**
     * 场景
     */
    public static final String SCENE = "scene";
    public static final String CONTENT = "content";
    public static final String IMAGEURL = "imageUrl";
    public static final String IMAGEPATH = "imagePath";
    public static final String URL = "url";
    public static final String TITLE = "title";

    /**
     * 2为微信
     */
    public static final String SCENE_2 = "2";
    /**
     * 3为朋友圈
     */
    public static final String SCENE_3 = "3";

    private ImageView share_weixin;
    private ImageView share_friends;
    private Bundle bundle;
    private String title = "";
    private String content = "";
    private String url = "";
    private String scene = "";// 0为友盟默认提供的分享界面；2为微信；3为朋友圈；4为微博；5为其他
    private IWXAPI api;

    //此处填写微信API提供的AppID
    private String WEIXIN_APP_ID = "wxdd5a382733d796e6";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle();
        initApi();
        normalInit();
    }

    /**
     * 获取intent数据
     *
     * @author hexiaodong
     */
    private void getBundle() {
        bundle = this.getIntent().getExtras();
        if (bundle.containsKey(TITLE))
            title = bundle.getString(TITLE);
        if (bundle.containsKey(CONTENT))
            content = bundle.getString(CONTENT);
        if (bundle.containsKey(URL))
            url = bundle.getString(URL);
        if (bundle.containsKey(SCENE))
            scene = bundle.getString(SCENE);
    }


    /**
     * 初始化三方api
     *
     * @author hexiaodong
     */
    private void initApi() {
        // 微信
        api = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, false);
        api.registerApp(WEIXIN_APP_ID);
    }

    /**
     * 非h5页面调用时显示分享选择界面
     *
     * @author hexiaodong
     */
    private void normalInit() {
        setContentView(R.layout.alert_dialog_share);
        // 占满屏
        LayoutParams lay = getWindow().getAttributes();
        lay.height = LayoutParams.MATCH_PARENT;
        lay.width = LayoutParams.MATCH_PARENT;

        share_weixin = (ImageView) this.findViewById(R.id.share_weixin);
        share_friends = (ImageView) this.findViewById(R.id.share_friends);

        // 添加按钮监听
        share_weixin.setOnClickListener(this);
        share_friends.setOnClickListener(this);
    }

    /**
     * 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    // 分享到微信
    private void shareToWeiXin(final int scene) {
        if (!api.isWXAppInstalled()) {//检查是否已安装微信
            ToastUtils.showToast("请先安装微信客户端");
            finish();
            return;
        }
        if (!TextUtils.isEmpty(url)) { //如果跳转地址不为空
            shareURLToWeiXin(title, content, url, scene);
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.share_weixin) {//分享到微信会话

            shareToWeiXin(SendMessageToWX.Req.WXSceneSession);
        } else if (v.getId() == R.id.share_friends) {//分享到微信朋友圈

            shareToWeiXin(SendMessageToWX.Req.WXSceneTimeline);
        }
    }


    private void shareURLToWeiXin(String title, String content, String url, int scene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if (scene == SendMessageToWX.Req.WXSceneSession) {
            if (TextUtils.isEmpty(title)) {
                msg.title = content;
            } else {
                msg.title = title;
            }
            msg.description = content;
        } else {
            if (!TextUtils.isEmpty(content)) { // 分享到朋友圈
                // msg.description
                // 没效果 要调用 msg.title
                msg.title = content;
            } else {
                msg.title = title;
            }
        }
        Bitmap thumb = null;
        thumb = BitmapFactory.decodeResource(SharePopup.this.getResources(), R.mipmap.ic_launcher);
        msg.thumbData = pngBmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
        finish();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    /**
     * get png format image byte[] array
     *
     * @param bmp
     * @param needRecycle
     * @return
     * @Author hexiaodong
     */
    public static byte[] pngBmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
