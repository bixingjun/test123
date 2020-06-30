package com.example.handletest;
/*
 *  包名: com.example.handletest
 * Created by ASUS on 2018/6/24.
 *  描述: TODO
 */

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
