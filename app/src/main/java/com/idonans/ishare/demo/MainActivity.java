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
import com.idonans.ishare.IShareHelper;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
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

    public static void showWeiboClientWarning() {
        Toast.makeText(AppContext.getContext(), "微博客户端未安装或版本过低", Toast.LENGTH_SHORT).show();
    }

    private static final String TAG = "MainActivity";
    private IShareHelper mIShareHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIShareHelper = new IShareHelper(this, mIShareListener);

        setContentView(R.layout.activity_main);

        View qqLogin = ViewUtil.findViewByID(this, R.id.qq_login);
        qqLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tencent tencent = mIShareHelper.getIShareQQHelper().getTencent(MainActivity.this);
                if (tencent == null) {
                    showQQClientWarning();
                } else {
                    tencent.login(MainActivity.this, "get_simple_userinfo", mIShareHelper.getIShareQQHelper().getListener());
                }
            }
        });

        View qqShare = ViewUtil.findViewByID(this, R.id.qq_share);
        qqShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tencent tencent = mIShareHelper.getIShareQQHelper().getTencent(MainActivity.this);
                if (tencent == null) {
                    showQQClientWarning();
                } else {
                    Bundle params = new Bundle();
                    params.putString(QQShare.SHARE_TO_QQ_TITLE, "title");
                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://www.github.com/idonans/ishare");
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://avatars3.githubusercontent.com/u/4043830?v=3&s=460");
                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "ishare qq");
                    tencent.shareToQQ(MainActivity.this, params, mIShareHelper.getIShareQQHelper().getListener());
                }
            }
        });

        View qzoneShare = ViewUtil.findViewByID(this, R.id.qzone_share);
        qzoneShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tencent tencent = mIShareHelper.getIShareQQHelper().getTencent(MainActivity.this);
                if (tencent == null) {
                    showQQClientWarning();
                } else {
                    Bundle params = new Bundle();
                    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "title");
                    params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "https://www.github.com/idonans/ishare");
                    params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,
                            new ArrayList<>(Arrays.asList("https://avatars3.githubusercontent.com/u/4043830?v=3&s=460")));
                    params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "ishare qzone");
                    tencent.shareToQzone(MainActivity.this, params, mIShareHelper.getIShareQQHelper().getListener());
                }
            }
        });

        View weixinLogin = ViewUtil.findViewByID(this, R.id.weixin_login);
        weixinLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IWXAPI api = mIShareHelper.getIShareWeixinHelper().getApi();
                if (api == null) {
                    showWeixinClientWarning();
                } else {
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = mIShareHelper.getIShareWeixinHelper().getState();
                    api.sendReq(req);
                }
            }
        });

        View weixinShare = ViewUtil.findViewByID(this, R.id.weixin_share);
        weixinShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IWXAPI api = mIShareHelper.getIShareWeixinHelper().getApi();
                if (api == null) {
                    showWeixinClientWarning();
                } else {
                    WXTextObject textObject = new WXTextObject("ishare weixin text");
                    WXMediaMessage mediaMessage = new WXMediaMessage(textObject);
                    mediaMessage.description = "ishare weixin text desc";

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = "" + System.currentTimeMillis();
                    req.message = mediaMessage;
                    req.scene = SendMessageToWX.Req.WXSceneSession;
                    api.sendReq(req);
                }
            }
        });

        View weiboLogin = ViewUtil.findViewByID(this, R.id.weibo_login);
        weiboLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SsoHandler ssoHandler = mIShareHelper.getIShareWeiboHelper().getSsoHandler();
                ssoHandler.authorize(mIShareHelper.getIShareWeiboHelper().getListener());
            }
        });
        View weiboShare = ViewUtil.findViewByID(this, R.id.weibo_share);
        weiboShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IWeiboShareAPI api = mIShareHelper.getIShareWeiboHelper().getIWeiboShareAPI(false);
                if (api == null) {
                    showWeiboClientWarning();
                } else {
                    TextObject textObject = new TextObject();
                    textObject.text = "ishare weibo test code";
                    WeiboMultiMessage weiboMultiMessage = new WeiboMultiMessage();
                    weiboMultiMessage.textObject = textObject;

                    SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
                    request.transaction = "" + System.currentTimeMillis();
                    request.multiMessage = weiboMultiMessage;
                    api.sendRequest(mIShareHelper.getIShareWeiboHelper().getActivity(), request);
                }
            }
        });
    }

    private IShareHelper.IShareListener mIShareListener = new IShareHelper.IShareListener() {

        private static final String TAG = "MainActivity mIShareListener";

        @Override
        public void onQQComplete(Object o) {
            showLog(TAG + " onQQComplete " + o);
        }

        @Override
        public void onQQError(UiError uiError) {
            showLog(TAG + " onQQError " + uiError);
        }

        @Override
        public void onQQCancel() {
            showLog(TAG + " onQQCancel");
        }

        @Override
        public void onWeixinCallback(BaseResp baseResp) {
            showLog(TAG + " onWeixinCallback " + baseResp);
        }

        @Override
        public void onWeiboAuthComplete(Bundle bundle) {
            showLog(TAG + " onWeiboAuthComplete " + bundle);
        }

        @Override
        public void onWeiboAuthException(WeiboException e) {
            showLog(TAG + " onWeiboAuthException " + e);
            e.printStackTrace();
        }

        @Override
        public void onWeiboAuthCancel() {
            showLog(TAG + " onWeiboAuthCancel");
        }

        @Override
        public void onWeiboShareCallback(BaseResponse baseResponse) {
            showLog(TAG + " onWeiboShareCallback " + baseResponse);
        }
    };

    private static void showLog(String log) {
        CommonLog.d(log);
        Toast.makeText(AppContext.getContext(), log, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mIShareHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIShareHelper.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IOUtil.closeQuietly(mIShareHelper);
    }

}
