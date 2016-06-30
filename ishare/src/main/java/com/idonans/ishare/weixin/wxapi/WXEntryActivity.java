package com.idonans.ishare.weixin.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.util.IOUtil;
import com.idonans.ishare.weixin.IShareWeixinHelper;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * 与微信通信页
 * Created by pengji on 16-6-30.
 */
public class WXEntryActivity extends CommonActivity {

    private IShareWeixinHelper mIShareWeixinHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIShareWeixinHelper = new IShareWeixinHelper(null);
        handleIntent(getIntent());
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        finish();
    }

    private void handleIntent(Intent intent) {
        IWXAPI api = mIShareWeixinHelper.getApi();
        if (api != null) {
            api.handleIntent(intent, IShareWeixinHelper.getGlobalWXAPIEventHandler());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IOUtil.closeQuietly(mIShareWeixinHelper);
    }

}
