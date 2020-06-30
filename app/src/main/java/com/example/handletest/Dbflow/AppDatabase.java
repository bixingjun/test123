package com.example.handletest.Dbflow;
/*
 *  包名: com.example.handletest.Dbflow
 * Created by ASUS on 2018/6/24.
 *  描述: TODO
 */

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public final class AppDatabase {
    //数据库名称
     public static final String NAME = "AppDatabase";
    // 数据库版本号
     public static final int VERSION = 1;
}


