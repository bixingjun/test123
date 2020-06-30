package com.example.handletest.AsyncTask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.handletest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.handletest.AsyncTask.MyAsyncTask.getSDCardDir;

public class asynctaskActivity extends AppCompatActivity implements MyAsyncTask.UpdateUI {


    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        ButterKnife.bind(this);

        MyAsyncTask myAsyncTask = new MyAsyncTask(this);

        myAsyncTask.setUpdateUIInterface(this);

        myAsyncTask.execute("http://img2.3lian.com/2014/f6/173/d/51.jpg");

        Bitmap bitmap = BitmapFactory.decodeFile(getSDCardDir().toString());

        image.setImageBitmap(bitmap); //565

    }

    @Override
    public void UpdateProgressBar(Integer values) {
        Log.i("sss",values+"");


        progressBar.setProgress(values);
    }
}
