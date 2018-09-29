package com.good.maxky_2208.pro_tree_aaa;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Maxky_2208 on 15/5/2560.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFont();
    }

    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("non.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}