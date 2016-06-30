package com.idonans.ishare.weixin.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.util.IOUtil;
import com.idonans.ishare.weixin.IShareWeixinHelper;

/**
 * 与微信通信页
 * Created by pengji on 16-6-30.
 */
public class WXEntryActivity extends CommonActivity {

    private IShareWeixinHelper mShareWeixinHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareWeixinHelper = new IShareWeixinHelper();
        mShareWeixinHelper.getApi().handleIntent(getIntent(), IShareWeixinHelper.getGlobalWXAPIEventHandler());
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mShareWeixinHelper.getApi().handleIntent(intent, IShareWeixinHelper.getGlobalWXAPIEventHandler());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IOUtil.closeQuietly(mShareWeixinHelper);
    }

}
