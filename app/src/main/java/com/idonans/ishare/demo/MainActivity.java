package com.idonans.ishare.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.idonans.acommon.AppContext;
import com.idonans.acommon.lang.CommonLog;
import com.idonans.acommon.util.IOUtil;
import com.idonans.acommon.util.ViewUtil;
import com.idonans.ishare.qq.IShareQQHelper;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static void showQQClientWarning() {
        Toast.makeText(AppContext.getContext(), "QQ客户端未安装或版本过低", Toast.LENGTH_SHORT).show();
    }

    public static void showWeixinClientWarning() {
        Toast.makeText(AppContext.getContext(), "微信客户端未安装或版本过低", Toast.LENGTH_SHORT).show();
    }

    private static final String TAG = "MainActivity";
    private IShareQQHelper mIShareQQHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIShareQQHelper = new IShareQQHelper(mQQUIListener);

        setContentView(R.layout.activity_main);

        View qqLogin = ViewUtil.findViewByID(this, R.id.qq_login);
        qqLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tencent tencent = mIShareQQHelper.getTencent(MainActivity.this);
                if (tencent == null) {
                    showQQClientWarning();
                } else {
                    tencent.login(MainActivity.this, "get_simple_userinfo", mIShareQQHelper.getListener());
                }
            }
        });

        View qqShare = ViewUtil.findViewByID(this, R.id.qq_share);
        qqShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tencent tencent = mIShareQQHelper.getTencent(MainActivity.this);
                if (tencent == null) {
                    showQQClientWarning();
                } else {
                    Bundle params = new Bundle();
                    params.putString(QQShare.SHARE_TO_QQ_TITLE, "title");
                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://www.github.com/idonans/ishare");
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://avatars3.githubusercontent.com/u/4043830?v=3&s=460");
                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "ishare qq");
                    tencent.shareToQQ(MainActivity.this, params, mIShareQQHelper.getListener());
                }
            }
        });

        View qzoneShare = ViewUtil.findViewByID(this, R.id.qzone_share);
        qzoneShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tencent tencent = mIShareQQHelper.getTencent(MainActivity.this);
                if (tencent == null) {
                    showQQClientWarning();
                } else {
                    Bundle params = new Bundle();
                    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "title");
                    params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "https://www.github.com/idonans/ishare");
                    params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,
                            new ArrayList<>(Arrays.asList("https://avatars3.githubusercontent.com/u/4043830?v=3&s=460")));
                    params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "ishare qzone");
                    tencent.shareToQzone(MainActivity.this, params, mIShareQQHelper.getListener());
                }
            }
        });

        View weiboLogin = ViewUtil.findViewByID(this, R.id.weibo_login);
        View weiboShare = ViewUtil.findViewByID(this, R.id.weibo_share);
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
