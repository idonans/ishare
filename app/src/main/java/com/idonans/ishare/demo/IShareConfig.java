package com.idonans.ishare.demo;

import android.widget.Toast;

import com.idonans.acommon.AppContext;

/**
 * Created by pengji on 16-6-28.
 */
public class IShareConfig {

    public static final String QQ_APP_ID = "1105503788";
    public static final String QQ_APP_KEY = "uQA1fS50WimSyeEU";

    public static final String WEIXIN_APP_KEY = "wxe875f8f13fb573c0";
    public static final String WEIXIN_APP_SECRET = "458add78313160603083178e94007114";

    public static final String WEIBO_APP_KEY = "1579966664";
    public static final String WEIBO_APP_SECRET = "f909c45b4f65f64af40a608946d9669f";

    public static void showQQClientWarning() {
        Toast.makeText(AppContext.getContext(), "QQ客户端未安装或版本过低", Toast.LENGTH_SHORT).show();
    }

    public static void showWeixinClientWarning() {
        Toast.makeText(AppContext.getContext(), "微信客户端未安装或版本过低", Toast.LENGTH_SHORT).show();
    }

}
