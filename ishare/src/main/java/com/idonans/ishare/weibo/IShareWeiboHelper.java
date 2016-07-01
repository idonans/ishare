package com.idonans.ishare.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.idonans.ishare.IShareConfig;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 微博登陆分享
 * Created by pengji on 16-6-29.
 */
public final class IShareWeiboHelper implements Closeable {

    private final WeiboAuthListenerAdapter mListener;
    private final AuthInfo mAuthInfo;
    private final SsoHandler mSsoHandler;

    public IShareWeiboHelper(Activity activity, WeiboAuthListener listener) {
        mListener = new WeiboAuthListenerAdapter();
        mListener.setOutListener(listener);

        mAuthInfo = new AuthInfo(activity, IShareConfig.getWeiboAppKey(), IShareConfig.getWeiboRedirectUrl(), null);
        mSsoHandler = new SsoHandler(activity, mAuthInfo);

        // fix sdk bug: sso 客户端授权时，如果当前 app 被回收，需要恢复参数. 注意需要避免这些参数被混淆
        try {
            {
                Field field = SsoHandler.class.getDeclaredField("mSSOAuthRequestCode");
                field.setAccessible(true);
                field.setInt(mSsoHandler, 32973);
            }
            {
                Field field = SsoHandler.class.getDeclaredField("mAuthListener");
                field.setAccessible(true);
                field.set(mSsoHandler, getListener());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SsoHandler getSsoHandler() {
        return mSsoHandler;
    }

    @NonNull
    public WeiboAuthListenerAdapter getListener() {
        return mListener;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
    }

    @Override
    public void close() throws IOException {
        mListener.setOutListener(null);
    }

    private static class WeiboAuthListenerAdapter implements WeiboAuthListener {

        private WeiboAuthListener mOutListener;

        public void setOutListener(WeiboAuthListener outListener) {
            mOutListener = outListener;
        }

        @Override
        public void onComplete(Bundle bundle) {
            if (mOutListener != null) {
                mOutListener.onComplete(bundle);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            if (mOutListener != null) {
                mOutListener.onWeiboException(e);
            }
        }

        @Override
        public void onCancel() {
            if (mOutListener != null) {
                mOutListener.onCancel();
            }
        }

    }

}
