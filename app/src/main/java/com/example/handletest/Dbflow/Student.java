package com.example.handletest.Dbflow;
/*
 *  包名: com.example.handletest.Dbflow
 * Created by ASUS on 2018/6/24.
 *  描述: TODO
 */

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Student extends BaseModel{

    @PrimaryKey(autoincrement = true)//ID自增
    public int id;

    @Column
    public String name;

    @Column
    public int age;



}
