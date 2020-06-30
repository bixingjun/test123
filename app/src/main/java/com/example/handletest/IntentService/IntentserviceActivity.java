package com.example.handletest.IntentService;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.handletest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntentserviceActivity extends AppCompatActivity implements MyIntentService.UpdateUI {

    @BindView(R.id.image)
    ImageView image;

    private String url[] = {
            "https://img-blog.csdn.net/20160903083245762",
            "https://img-blog.csdn.net/20160903083252184",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083311972",
            "https://img-blog.csdn.net/20160903083319668",
            "https://img-blog.csdn.net/20160903083326871"
    };

    private  Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            image.setImageBitmap((Bitmap) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intentservice);

        ButterKnife.bind(this);

        Intent intent = new Intent(this,MyIntentService.class);
        for (int i=0;i<7;i++) {//循环启动任务
            intent.putExtra(MyIntentService.DOWNLOAD_URL,url[i]);
            intent.putExtra(MyIntentService.INDEX_FLAG,i);
            startService(intent);
        }
        MyIntentService.setUpdateUI(this);
    }


    @Override
    public void updateui(Message message) {
        mUIHandler.sendMessageDelayed(message,message.what * 1000);
    }
}
