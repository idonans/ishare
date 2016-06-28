package com.idonans.ishare.demo;

import android.app.Application;
import android.util.Log;

import com.idonans.acommon.App;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by pengji on 16-6-28.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        App.init(new App.Config.Builder()
                .setContext(this)
                .setBuildConfigAdapter(new BuildConfigAdapterImpl())
                .build());
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
        public int getLogLevel() {
            return Log.DEBUG;
        }

        @Override
        public boolean isDebug() {
            return BuildConfig.DEBUG;
        }
    }

}
