package com.example.handletest.IntentService;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {

    public static final String DOWNLOAD_URL="download_url";
    public static final String INDEX_FLAG="index_flag";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {



        //在子线程中进行网络请求
        Bitmap bitmap=downloadUrlBitmap(intent.getStringExtra(DOWNLOAD_URL));
        Message msg1 = new Message();
        msg1.what = intent.getIntExtra(INDEX_FLAG,0);
        msg1.obj =bitmap;

        if(updateUI!=null) {

            updateUI.updateui(msg1);
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
            bitmap= BitmapFactory.decodeStream(in);


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


    private  static UpdateUI updateUI;

    interface UpdateUI{

        void updateui(Message message);
    }

    public  static void setUpdateUI(UpdateUI updateUIInterface){
        updateUI=updateUIInterface;
    }
}


