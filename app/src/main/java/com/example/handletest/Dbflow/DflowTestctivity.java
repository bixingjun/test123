package com.example.handletest.Dbflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.handletest.R;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DflowTestctivity extends AppCompatActivity {

    @BindView(R.id.bt_add)
    Button button1;

    //AsyncTask使用
    @BindView(R.id.bt_delete)
    Button button2;

    //IntentService使用
    @BindView(R.id.bt_update)
    Button button3;

    //使用
    @BindView(R.id.bt_query)
    Button button4;

    @BindView(R.id.bt_addlist)
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dflow_testctivity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_add)
    public void add(){


        /*List<Student> studentList=new ArrayList<>();

        for(int i=1;i<11;i++){
            Student student = new Student();
            student.name = "bxj";
            student.age = i;
            studentList.add(student);
        }

        modelAdapter.saveAll(studentList);*/
        Student student = new Student();
        student.name = "bxj";
        student.age = 18;

        student.save();

        //student.delete();
        //student.insert();

        new Select()
                .from(Student.class)
                .where(Student_Table.name.is("bxj"))
                .querySingle();

        List<Student> studentList = new Select()
                .from(Student.class)
                .where(Student_Table.age.is(18))
                .queryList();




    }


}
