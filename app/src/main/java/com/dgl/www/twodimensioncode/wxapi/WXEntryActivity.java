package com.dgl.www.twodimensioncode.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.dgl.www.twodimensioncode.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信分享回调
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wxdd5a382733d796e6", false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }


    //分享后的回调
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                ToastUtils.showToast("分享成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtils.showToast("取消分享");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtils.showToast("分享被拒绝");
                break;
            default:
                ToastUtils.showToast("未知原因");
                break;
        }
        this.finish();
    }


}

