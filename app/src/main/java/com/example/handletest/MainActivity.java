package com.example.handletest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.handletest.AsyncTask.asynctaskActivity;
import com.example.handletest.Dbflow.DflowTestctivity;
import com.example.handletest.HandlerThread.HandlerThreadActivity;
import com.example.handletest.HandlerThread.childhandle;
import com.example.handletest.IntentService.IntentserviceActivity;
import com.example.handletest.utils.photoUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    //HandlerThread使用
    @BindView(R.id.bt_1)
    Button button1;

    //AsyncTask使用
    @BindView(R.id.bt_2)
    Button button2;

    //IntentService使用
    @BindView(R.id.bt_3)
    Button button3;

    //使用
    @BindView(R.id.bt_4)
    Button button4;

    @BindView(R.id.bt_5)
    Button button5;

    @BindView(R.id.bt_6)
    Button button6;

    @BindView(R.id.bt_7)
    Button button7;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.bt_8)
    Button button8;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_CONTACTS,
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    private static final int TAKEPHOTO =3;
    private static final int PHOTORESULT =4;

    private Uri outputUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



    }

    @OnClick(R.id.bt_1)
    public void submit1(View view) {
        Toast.makeText(this, "bt_1", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(MainActivity.this,HandlerThreadActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.bt_2)
    public void submit2(View view) {
        Intent intent=new Intent(MainActivity.this,asynctaskActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_3)
    public void submit3(View view) {
        Intent intent=new Intent(MainActivity.this,IntentserviceActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_4)
    public void submit4(View view) {
        Intent intent=new Intent(MainActivity.this,childhandle.class);
        startActivity(intent);

    }
    @OnClick(R.id.bt_5)
    public void submit5(View view) {
        verifyStoragePermissions(this);
        //create();
        Log.i("xxx",getCacheDir().getAbsolutePath());
        Log.i("xxx",getExternalCacheDir().getAbsolutePath());

        Log.i("xxx", Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    @OnClick(R.id.bt_6)
    public void submit6(View view) {
        verifyStoragePermissions(this);
        duoquanxian();
        //11
    }


    @OnClick(R.id.bt_7)
    public void submit7(View view) {
       // verifyStoragePermissions(this);
        carmeraquanxian();
        image.setVisibility(View.VISIBLE);
     //   startCarmera();
    }



    @OnClick(R.id.bt_8)
    public void submit8(View view) {

        Intent intent=new Intent(MainActivity.this,DflowTestctivity.class);
        startActivity(intent);
    }











    public void carmeraquanxian(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 没有权限。
                // 申请授权。
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
        }else {
            startCarmera();
        }
    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TAKEPHOTO && resultCode == RESULT_OK) {
            //设置文件保存路径这里放在跟目录下
            File picture = new File(getExternalCacheDir() + "/img.jpg");
            File pictures = new File(getExternalCacheDir() + "/imgs.jpg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                Uri uri = FileProvider.getUriForFile(this, "com.example.handletest.fileprovider", picture);
                 outputUri = Uri.fromFile(pictures);
                //裁剪照片
                startPhotoZoom(uri,outputUri);

            } else {

                Uri uri = Uri.fromFile(picture);

                Bitmap bitmapFromUri = photoUtils.getBitmapFromUri(uri, this);

                image.setImageBitmap(bitmapFromUri);

                //裁剪照片
                // startPhotoZoom(Uri.fromFile(picture));
            }
        }

        // 裁剪照片的处理结果，看完后面的代码再看这里
       /* if (requestCode == PHOTORESULT) {

            Bundle extras = data.getExtras();

            File file = new File(getExternalCacheDir() + "/head.jpg");

            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                try {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    photo.compress(Bitmap.CompressFormat.JPEG, 70, bos);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

               // imgView.setImageBitmap(photo);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }*/
        if (requestCode == PHOTORESULT) {
            try {
                Bitmap bitmap = photoUtils.getBitmapFromUri(outputUri, this);


                //TODO 将获取的bitmap展示在你的控件上
                image.setImageBitmap(bitmap);
            } catch (Exception e) {

            }
        }
    }


    private void startCarmera(){

        File file = new File(getExternalCacheDir(), "img.jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    FileProvider.getUriForFile(this,"com.example.handletest.fileprovider", file));
        }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        startActivityForResult(intent, TAKEPHOTO);
    }

    public void startPhotoZoom(Uri uri, Uri outputUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");

        //这段代码判断，在安卓7.0以前版本是不需要的。特此注意。不然这里也会抛出异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("circleCrop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("aspectX", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

//        intent.putExtra("scale",true);//自由截取

     //   intent.putExtra("return-data", true);

        //uritempFile为Uri类变量，实例化uritempFile

        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, PHOTORESULT);
    }






    private void duoquanxian() {

    }




    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            for(int i=0;i<PERMISSIONS_STORAGE.length;i++) {
                int permission = ActivityCompat.checkSelfPermission(activity,
                        PERMISSIONS_STORAGE[i]);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // 没有写的权限，去申请写的权限，会弹出对话框
                    ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //用户同意授权，执行读取文件的代码
                //create();
            }else{
                //若用户不同意授权，直接暴力退出应用。
                // 当然，这里也可以有比较温柔的操作。
                finish();
            }
        }

        if (requestCode==2){

            if(permissions[0].equals(Manifest.permission.CAMERA)&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //用户同意授权，执行读取文件的代码
           //     create();
              //  Toast.makeText(this,"你授予读取拍照权限",Toast.LENGTH_SHORT).show();
                startCarmera();
            }else{
                //若用户不同意授权，直接暴力退出应用。
                // 当然，这里也可以有比较温柔的操作。
                Toast.makeText(this,"你拒绝读取拍照权限",Toast.LENGTH_SHORT).show();
            }


        }
    }


    public void create(){

        File file = new File("/mnt/sdcard/work/mywork");
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            //通过file的mkdirs()方法创建<span style="color:#FF0000;">目录中包含却不存在</span>的文件夹
            file.mkdirs();
        }


        //新建一个File类型的成员变量，传入文件名路径。
        File mFile = new File(getCacheDir().getAbsolutePath()+"/zhiyuansn.txt");
        //判断文件是否存在，存在就删除
        if (mFile.exists()){
            mFile.delete();
        }
        try {
            //创建文件
            mFile.createNewFile();
            //给一个吐司提示，显示创建成功
            Toast.makeText(getApplicationContext(), "文件创建成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //新建一个File类型的成员变量，传入文件名路径。
        File mFiles = new File(getExternalCacheDir().getAbsolutePath()+"/zhiyuann.txt");
        //判断文件是否存在，存在就删除
        if (mFiles.exists()){
            mFiles.delete();
        }
        try {
            //创建文件
            mFiles.createNewFile();
            //给一个吐司提示，显示创建成功
            Toast.makeText(getApplicationContext(), "文件创建成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
          /*  如果为挂载状态，那么就通过Environment的getExternalStorageDirectory()方法来获取
            外置存储卡的目录，然后加上我们自己要创建的文件名（记住文件名前要加一个"/"），这样
            就生成了我们要创建的文件路径。*/

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() +  File.separator+"zhiyuan.txt";
            ///storage/emulated/0/zhiyuan.txt
            ///mnt/sdcard/zhiyuann.txt

            //新建一个File对象，把我们要建的文件路径传进去。
            File file1 = new File(path);
            //方便查看，在控制台打印一下我们的存储卡目录。
            Log.d("ssss", "onClick: "+path);
            //判断文件是否存在，如果存在就删除。
            if (file1.exists()) {
                file1.delete();
            }
            try {
                //通过文件的对象file的createNewFile()方法来创建文件
                file1.createNewFile();
                //新建一个FileOutputStream()，把文件的路径传进去
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                //给定一个字符串，将其转换成字节数组
                byte[] bytes = "你好啊，今天天气不错ssssssssssss！".getBytes();
                //通过输出流对象写入字节数组
                fileOutputStream.write(bytes);
                //关流
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
