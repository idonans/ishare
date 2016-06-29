package com.idonans.ishare.qq;

import android.content.Intent;

import com.idonans.acommon.AppContext;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by pengji on 16-6-28.
 */
public final class IShareQQHelper implements Closeable {

    private final Tencent mTencent;
    private final IUiListenerAdapter mListener;

    public IShareQQHelper(String appId, IUiListener listener) {
        mTencent = Tencent.createInstance(appId, AppContext.getContext());
        mListener = new IUiListenerAdapter();
        mListener.setOutListener(listener);
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_LOGIN:
            case Constants.REQUEST_QQ_SHARE:
            case Constants.REQUEST_QZONE_SHARE: {
                Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public Tencent getTencent() {
        return mTencent;
    }

    public IUiListener getListener() {
        return mListener;
    }

    @Override
    public void close() throws IOException {
        mListener.setOutListener(null);
    }

    private class IUiListenerAdapter implements IUiListener {

        private IUiListener mOutListener;

        public void setOutListener(IUiListener outListener) {
            mOutListener = outListener;
        }

        @Override
        public void onComplete(Object o) {
            if (mOutListener != null) {
                mOutListener.onComplete(o);
            }
        }

        @Override
        public void onError(UiError uiError) {
            if (mOutListener != null) {
                mOutListener.onError(uiError);
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
