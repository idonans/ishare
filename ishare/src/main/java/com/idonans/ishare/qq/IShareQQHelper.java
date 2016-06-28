package com.idonans.ishare.qq;

import android.content.Intent;

import com.idonans.acommon.AppContext;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

/**
 * Created by pengji on 16-6-28.
 */
public class IShareQQHelper {

    private final Tencent mTencent;
    private final IUiListener mListener;

    public IShareQQHelper(String appId, IUiListener listener) {
        mTencent = Tencent.createInstance(appId, AppContext.getContext());
        mListener = listener;
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

}
