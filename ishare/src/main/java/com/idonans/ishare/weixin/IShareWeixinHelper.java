package com.idonans.ishare.weixin;

import com.idonans.acommon.AppContext;
import com.idonans.acommon.lang.CommonLog;
import com.idonans.ishare.IShareConfig;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * 微信登陆分享
 * Created by pengji on 16-6-30.
 */
public final class IShareWeixinHelper implements Closeable {

    private IWXAPI mApi;

    public IShareWeixinHelper() {
        mApi = WXAPIFactory.createWXAPI(AppContext.getContext(), IShareConfig.getWeixinAppKey(), false);
    }

    private static final IWXAPIEventHandler sGlobalWXAPIEventHandler = new GlobalWXAPIEventHandler();

    public IWXAPI getApi() {
        return mApi;
    }

    public static IWXAPIEventHandler getGlobalWXAPIEventHandler() {
        return sGlobalWXAPIEventHandler;
    }

    @Override
    public void close() throws IOException {
        // TODO
    }

    private static final class GlobalWXAPIEventHandler implements IWXAPIEventHandler {

        private static final String TAG = "GlobalWXAPIEventHandler";

        @Override
        public void onReq(BaseReq baseReq) {
            CommonLog.d(TAG + " onReq " + baseReq);
        }

        @Override
        public void onResp(BaseResp baseResp) {
            CommonLog.d(TAG + " onResp " + baseResp);
        }
    }

}
