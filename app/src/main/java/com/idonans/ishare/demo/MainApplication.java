package com.idonans.ishare.demo;

import android.app.Application;
import android.util.Log;

import com.idonans.acommon.App;
import com.idonans.ishare.IShareConfig;
import com.sina.weibo.sdk.utils.LogUtil;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by pengji on 16-6-28.
 */
public class MainApplication extends Application {

    public static final String QQ_APP_ID = "1105503788";
    public static final String QQ_APP_KEY = "uQA1fS50WimSyeEU";

    public static final String WEIXIN_APP_KEY = "wxe875f8f13fb573c0";
    public static final String WEIXIN_APP_SECRET = "458add78313160603083178e94007114";

    public static final String WEIBO_APP_KEY = "1579966664";
    public static final String WEIBO_APP_SECRET = "f909c45b4f65f64af40a608946d9669f";

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        new App.Config.Builder()
                .setContext(this)
                .setBuildConfigAdapter(new BuildConfigAdapterImpl())
                .build()
                .init();

        if (App.getBuildConfigAdapter().isDebug()) {
            LogUtil.enableLog();
        }
        new IShareConfig.Builder()
                .setQQ(QQ_APP_ID, QQ_APP_KEY)
                .setWeixin(WEIXIN_APP_KEY, WEIXIN_APP_SECRET)
                .setWeibo(WEIBO_APP_KEY, WEIBO_APP_SECRET)
                .init();
    }

    public static class BuildConfigAdapterImpl implements App.BuildConfigAdapter {

        @Override
        public int getVersionCode() {
            return BuildConfig.VERSION_CODE;
        }

        @Override
        public String getVersionName() {
            return BuildConfig.VERSION_NAME;
        }

        @Override
        public String getLogTag() {
            return BuildConfig.APPLICATION_ID;
        }

        @Override
        public String getPublicSubDirName() {
            return "ishare";
        }

        @Override
        public String getChannel() {
            return "default_channel";
        }

        @Override
        public int getLogLevel() {
            return Log.DEBUG;
        }

        @Override
        public boolean isDebug() {
            return BuildConfig.DEBUG;
        }
    }

}
