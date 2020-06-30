package com.example.handletest.HandlerThread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.handletest.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandlerThreadActivity extends AppCompatActivity  {

    @BindView(R.id.image)
    ImageView image;

    private String url[]={
            "https://img-blog.csdn.net/20160903083245762",
            "https://img-blog.csdn.net/20160903083252184",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083311972",
            "https://img-blog.csdn.net/20160903083319668",
            "https://img-blog.csdn.net/20160903083326871"
    };


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

           // image.setImageBitmap((Bitmap) msg.obj);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        ButterKnife.bind(this);

        HandlerThread handlerThread=new HandlerThread("downloadImage");
        handlerThread.start();

        Handler childHandler =new Handler(handlerThread.getLooper(),new childCallback());

        for (int i=0;i<7;i++){
            childHandler.sendEmptyMessageDelayed(i,1000*i);
        }

    }

    class childCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {

            int what = msg.what;
            final Bitmap bitmap = downloadUrlBitmap(url[what]);

           /* Message msg1=Message.obtain();
            msg1.obj=bitmap;

            handler.sendMessage(msg1);*/

           handler.post(new Runnable() {
               @Override
               public void run() {
                   image.setImageBitmap(bitmap);
               }
           });

            return false;
        }
    }

    private Bitmap downloadUrlBitmap(String urlString){
        HttpURLConnection urlConnection=null;
        BufferedInputStream in= null;
        Bitmap bitmap=null;

        try{
            final URL url=new URL(urlString);

            urlConnection= (HttpURLConnection) url.openConnection();
            in=new BufferedInputStream(urlConnection.getInputStream(),8*1024);
            bitmap=BitmapFactory.decodeStream(in);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }

                if(in!=null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }
        return bitmap;
    }


}
