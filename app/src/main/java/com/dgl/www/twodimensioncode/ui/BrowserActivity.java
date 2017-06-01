package com.dgl.www.twodimensioncode.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dgl.www.twodimensioncode.R;


/**
 * @author liufuning
 */

public class BrowserActivity extends Activity implements OnClickListener {

    public static final String URL = "url";
    public static final String IMAGEURL = "imageUrl";//图片URL 地址
    public static final String TITLE = "title";
    public static final String DESC = "desc";

    private String mTitle = "";
    private String mDesc = "";
    private String url = "";
    private String imageUrl = "";
    private WebView webView;
    private ProgressBar progressbar;
    private RelativeLayout back;
    private ImageView share;
    private TextView title;
    private TextView backReturn;
    private Bundle bundle;
    private RelativeLayout titleLayout;
    private PopupWindow groupPopupWindow; // 活动窗口
    Intent intent;
    private TextView refreshview;

    private WebSettings webSettings;
    private WebChromeClient.CustomViewCallback myCallback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//
        setContentView(R.layout.browser_activity);
        intent = getIntent();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();

        webView.loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.freeMemory();
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

    }



    @SuppressWarnings("deprecation")
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void initView() {
        bundle = intent.getExtras();
        if (bundle.containsKey(TITLE)) {
            mTitle = bundle.getString(TITLE);
        }
        if (bundle.containsKey(DESC)) {
            mDesc = bundle.getString(DESC);
        }
        if (bundle.containsKey(URL)) {
            url = bundle.getString(URL);
        }
        if (bundle.containsKey(IMAGEURL)) {
            imageUrl = bundle.getString(IMAGEURL);
        }

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        webView = (WebView) findViewById(R.id.webview);


        back = (RelativeLayout) findViewById(R.id.btn_back);
        back.setOnClickListener(this);

        refreshview = (TextView) this.findViewById(R.id.btn_shuaxin_id);
        refreshview.setOnClickListener(this);
        backReturn = (TextView) findViewById(R.id.btn_back_return);
        back.setVisibility(View.VISIBLE);

        title = (TextView) findViewById(R.id.title);
        if (!TextUtils.isEmpty(mTitle)) {
            title.setText(mTitle.length() > 8 ? mTitle.substring(0, 6) + "..."
                    : mTitle);
        }

        share = (ImageView) findViewById(R.id.btn_share);
        share.setOnClickListener(this);

        titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);
        titleLayout.setOnClickListener(this);


        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // webSettings.setPluginsEnabled(true);

        webSettings.setSavePassword(false);
        // 设置可以支持缩放
        webSettings.setSupportZoom(false);
        // 设置出现缩放工具
        // webSettings.setBuiltInZoomControls(true);
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(false);
        // 自适应屏幕
        webSettings.setLoadWithOverviewMode(false);
        // 把图片加载放在最后来加载渲染
        webSettings.setBlockNetworkImage(true);


        // 自适应屏幕

//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

//        webView.addJavascriptInterface(this, "android");
        webView.requestFocus();

        // xinw
        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 提高渲染的优先级
        webSettings.setRenderPriority(RenderPriority.HIGH);
        //远程代码执行漏洞
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
        // Cache开启和设置
        // 一个页面的 图片\js\css 载入过之后
        // 在服务器设置的文件有效期内，每次请求，会去服务器检查文件最后修改时间，如果一致，不会重新下载，而是使用缓存
        webView.setInitialScale(100);
        // 调用原生
        /*
         * webView.addJavascriptInterface(new Object() {
		 *
		 * @JavascriptInterface public void call(String number){ Intent intent =
		 * new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number)); }
		 *
		 *
		 * } ,"callPhone");
		 */

        // 调用getPhotoUrls([{“original”:”http://xxx/original.jpg”,
        // “thumb”:”http://xxx/thumb.jpg”}, {…}, {…}])方法
        removeSearchBoxImpl();// 删除掉Android默认注册的JS接口
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                //Url黑名单过滤
                return super.shouldInterceptRequest(view, url);
            }

            //判断url,在这里可以进入原生页面
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                titleLayout.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                super.onPageFinished(view, url);
                // 加载完网页后，设置图片开始加载为false
                webSettings.setBlockNetworkImage(false);
                if (webView != null) {
                    progressbar.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (webView != null) {
                    progressbar.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    AlertDialog alertDialog;
    String content;

    private class MyWebChromeClient extends WebChromeClient {
        // For Android 3.0+
        // For Android < 3.0
        public void onReceivedTitle(WebView view, String mtitle) {
            //			//h5无标题时，显示标题
            super.onReceivedTitle(view, mtitle);
            if (mtitle == null) {
                titleLayout.setVisibility(View.GONE);
            } else if (!mtitle.equals("")) {
                titleLayout.setVisibility(View.VISIBLE);
                title.setText(mtitle.length() > 6 ? mtitle.substring(0, 6) + "..." : mtitle);
            }

        }


        //全屏设置
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null;
                return;
            }
            ViewGroup parent = (ViewGroup) webView.getParent();
            parent.removeView(webView);
            parent.addView(view);
            myCallback = callback;
        }

        /**
         * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            new AlertDialog.Builder(BrowserActivity.this)
                    .setTitle(view.getTitle())
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    result.confirm();
                                }
                            }).setCancelable(false).create().show();
            return true;
        }

        /**
         * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
            final LayoutInflater factory = LayoutInflater.from(BrowserActivity.this);
            final View v = factory.inflate(R.layout.prompt_dialog, null);
            ((EditText) v.findViewById(R.id.prompt_input_field)).setText(defaultValue);
            if (defaultValue == null) {
                content = "";
            } else {
                content = defaultValue;
            }
            alertDialog = new AlertDialog.Builder(BrowserActivity.this).
                    setView(v).
                    setTitle(view.getTitle()).
                    setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String value = ((EditText) v
                                            .findViewById(R.id.prompt_input_field))
                                            .getText().toString();
                                    result.confirm(value);
                                    if (!content.equals(value)) {
                                        Message msg = new Message();
                                        msg.what = 7;
                                        handler.sendMessage(msg);
                                    }
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            result.cancel();
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher).
                            create();
            alertDialog.show();
            return true;
        }
    }


    @Override
    public void onClick(View arg0) {
        int id = arg0.getId();
       if (id == R.id.btn_back) {
            onBackBtClick();
        } else if (id == R.id.btn_share) {
            Intent intent = new Intent(BrowserActivity.this, SharePopup.class);
            Bundle data = new Bundle();
            data.putString(SharePopup.TITLE, mTitle);
            data.putString(SharePopup.CONTENT, mDesc);
           data.putString(SharePopup.URL, url);
            data.putString(SharePopup.IMAGEURL, imageUrl);
            intent.putExtras(data);
            startActivity(intent);
        }  else if (id == R.id.btn_shuaxin_id) {
            if (url != null)
                webView.loadUrl(url);
        }
    }


    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 7) {
                // 隐藏键盘
                closeKeyBroad();
            }
        }
    };

    private void closeKeyBroad() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (null != imm) {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onBackBtClick()) return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回键通用回调处理，能GoBack则goBack，否则finish
     *
     * @return 如果是系统返回键调用，true表示消化按键事件，false则相反
     */
    private boolean onBackBtClick() {
        if (webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        } else {
            finish();
        }
        return false;
    }


    private boolean removeSearchBoxImpl() {
        if (hasHoneycomb() && !hasJellyBeanMR1()) {
            //远程代码执行漏洞
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibility");
            webView.removeJavascriptInterface("accessibilityTraversal");
            return true;
        }

        return false;
    }

    private boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    private boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }


}