package com.idonans.ishare.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.idonans.acommon.lang.CommonLog;
import com.idonans.acommon.util.ViewUtil;
import com.idonans.ishare.qq.IShareQQHelper;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

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
    }

    private IUiListener mQQUIListener = new IUiListener() {

        private static final String TAG = "MainActivity mQQUIListener";

        @Override
        public void onComplete(Object o) {
            CommonLog.d(TAG + " onComplete " + o);
        }

        @Override
        public void onError(UiError uiError) {
            CommonLog.d(TAG + " onError " + uiError);
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

}
