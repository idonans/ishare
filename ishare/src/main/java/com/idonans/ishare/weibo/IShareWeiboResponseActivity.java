package com.idonans.ishare.weibo;

import android.content.Intent;
import android.os.Bundle;

import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.lang.CommonLog;

/**
 * 接收微博分享的响应结果
 * Created by pengji on 16-7-1.
 */
public class IShareWeiboResponseActivity extends CommonActivity {

    private static final String TAG = "IShareWeiboResponseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonLog.d(TAG + " onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        CommonLog.d(TAG + " onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonLog.d(TAG + " onDestroy");
    }

}
