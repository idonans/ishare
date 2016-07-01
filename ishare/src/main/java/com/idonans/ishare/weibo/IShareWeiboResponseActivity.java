package com.idonans.ishare.weibo;

import android.content.Intent;
import android.os.Bundle;

import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.util.IOUtil;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;

/**
 * 接收微博分享的响应结果
 * Created by pengji on 16-7-1.
 */
public class IShareWeiboResponseActivity extends CommonActivity {

    private static final String TAG = "IShareWeiboResponseActivity";
    private IShareWeiboHelper mIShareWeiboHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIShareWeiboHelper = new IShareWeiboHelper(this, null, null);
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
        IWeiboShareAPI api = mIShareWeiboHelper.getIWeiboShareAPI();
        if (api != null) {
            api.handleWeiboResponse(intent, IShareWeiboHelper.getGlobalWeiboHandlerResponseAdapter());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IOUtil.closeQuietly(mIShareWeiboHelper);
    }

}
