package com.example.handletest.AsyncTask;
/*
 *  包名: com.example.handletest.AsyncTask
 * Created by ASUS on 2018/6/22.
 *  描述: TODO
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAsyncTask extends AsyncTask <String, Integer, String> {

    private PowerManager.WakeLock mWakeLock;
    private int ValueProgress=100;
    private Context context;

    public MyAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
       /* PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();*/
        //Display progressBar
//        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }
            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();
            // download the file
            input = connection.getInputStream();
            //create output
            output = new FileOutputStream(getSDCardDir());
            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                //
                Thread.sleep(100);
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        if (updateUI!=null){
            updateUI.UpdateProgressBar(values[0]);
        }
    }

    public static File getSDCardDir(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            // 创建一个文件夹对象，赋值为外部存储器的目录
            String dirName = Environment.getExternalStorageDirectory()+"/MyDownload/";
            File f = new File(dirName);
            if(!f.exists()){
                f.mkdir();
            }
            File downloadFile=new File(f,"new.jpg");
            return downloadFile;
        }
        else{

            return null;

        }

    }
    public UpdateUI updateUI;
    public interface UpdateUI{
        void UpdateProgressBar(Integer values);
    }
    public void setUpdateUIInterface(UpdateUI updateUI){
        this.updateUI=updateUI;
    }


}
