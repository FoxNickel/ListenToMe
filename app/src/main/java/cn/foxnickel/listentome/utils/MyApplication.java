package cn.foxnickel.listentome.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Night on 2017/3/21.
 * Desc:
 */

public class MyApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
