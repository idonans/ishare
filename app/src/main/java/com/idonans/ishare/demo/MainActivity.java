package com.idonans.ishare.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.idonans.acommon.lang.CommonLog;
import com.idonans.acommon.util.IOUtil;
import com.idonans.acommon.util.ViewUtil;
import com.idonans.ishare.qq.IShareQQHelper;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private IShareQQHelper mIShareQQHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIShareQQHelper = new IShareQQHelper(IShareConfig.QQ_APP_ID, mQQUIListener);

        setContentView(R.layout.activity_main);

        View qqLogin = ViewUtil.findViewByID(this, R.id.qq_login);
        qqLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIShareQQHelper.getTencent().login(MainActivity.this, "get_simple_userinfo", mIShareQQHelper.getListener());
            }
        });

        View qqShare = ViewUtil.findViewByID(this, R.id.qq_share);
        qqShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle params = new Bundle();
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "title");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://www.github.com/idonans/ishare");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://avatars3.githubusercontent.com/u/4043830?v=3&s=460");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "ishare qq");
                mIShareQQHelper.getTencent().shareToQQ(MainActivity.this, params, mIShareQQHelper.getListener());
            }
        });

        View qzoneShare = ViewUtil.findViewByID(this, R.id.qzone_share);
        qzoneShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle params = new Bundle();
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "title");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://www.github.com/idonans/ishare");
                params.putStringArrayList(QQShare.SHARE_TO_QQ_IMAGE_URL,
                        new ArrayList<>(Arrays.asList("https://avatars3.githubusercontent.com/u/4043830?v=3&s=460")));
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "ishare qzone");
                mIShareQQHelper.getTencent().shareToQzone(MainActivity.this, params, mIShareQQHelper.getListener());
            }
        });
    }

    private IUiListener mQQUIListener = new IUiListener() {

        private static final String TAG = "MainActivity mQQUIListener";

        @Override
        public void onComplete(Object o) {
            CommonLog.d(TAG + " onComplete " + o);
        }

        @Override
        public void onError(UiError uiError) {
            CommonLog.d(TAG + " onError " + uiError.errorCode + ", " + uiError.errorDetail + ", " + uiError.errorMessage);
        }

        @Override
        public void onCancel() {
            CommonLog.d(TAG + " onCancel");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mIShareQQHelper.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IOUtil.closeQuietly(mIShareQQHelper);
    }

}
