package com.dgl.www.twodimensioncode.wxapi;

import android.app.Activity;
import android.os.Bundle;

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
        api = WXAPIFactory.createWXAPI(this, "wxa9139d5f63af3e49", false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }


    @Override
    public void onResp(BaseResp baseResp) {
        String result = null;

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "拒绝";
                break;
            default:
                result = "未知原因";
                break;
        }
        //只有成功的时候才获得积分
//        if (result.equals("成功")) {
//
//        }
        this.finish();

    }


}

