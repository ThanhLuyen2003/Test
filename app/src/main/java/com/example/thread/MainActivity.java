package com.example.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    public static final String url = "https://khoinguonsangtao.vn/wp-content/uploads/2022/07/avatar-cute-2.jpg";
    public static final String url2 = "https://kynguyenlamdep.com/wp-content/uploads/2022/06/avatar-cute-meo-con-than-chet-700x695.jpg";
    public static final String url3 = "https://i.9mobi.vn/cf/Images/tt/2021/8/20/anh-avatar-dep-35.jpg";

    List<String> listString = new ArrayList<>();


    private Bitmap mbitmap;
    private Bitmap mbitmap2;
    private Bitmap mbitmap3;

    List<Bitmap> bitmaps = new ArrayList<>();

    ImageView imgAvata, imgAvata2, imgAvata3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imgAvata = findViewById(R.id.image);
        imgAvata2 = findViewById(R.id.image2);
        imgAvata3 = findViewById(R.id.image3);

        Button but = findViewById(R.id.button);

       but.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               downloadImage();
           }
       });
    }

//    private void downloadImage(){
//        Thread thread = new Thread(new Runnable(){
//            public void run(){
//                Bitmap bitmap = loadImageFromUrl(url);
//                imgAva.post(new Runnable){
//                    public void run(){
//                        imgAva.setImageBitmap(bitmap);
//                    }
//                }
//           mbitmap = loadImage(url);
//           Message message = messHandler.obtainMessage();
//           messHandler.sendMessage(message);
//           }
//        });
//        thread.start();
//    }

    private void downloadImage() {
        try {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<Bitmap>> listFuture = new ArrayList<Future<Bitmap>>();

            listString.add(url);
            listString.add(url2);
            listString.add( url3);

//        for (String string : listString){
//            Callable<Bitmap> callable = new Callable<Bitmap>() {
//                @Override
//                public Bitmap call() throws Exception {
//
//                    mbitmap = loadImageFromUrl(string);
//                    return mbitmap;
//                }
//            };
//            Future<Bitmap> future = executorService.submit(callable);
//            listFuture.add(future);
//        }


        Callable<Bitmap> callable = new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {

                mbitmap = loadImageFromUrl(url);
                return mbitmap;
            }
        };
            Callable<Bitmap> callable2 = new Callable<Bitmap>() {
                @Override
                public Bitmap call() throws Exception {

                    mbitmap2 = loadImageFromUrl(url2);

                    return mbitmap2;
                }
            };

            Callable<Bitmap> callable3 = new Callable<Bitmap>() {
                @Override
                public Bitmap call() throws Exception {

                    mbitmap3 = loadImageFromUrl(url3);

                    return mbitmap3;
                }
            };

        Future<Bitmap> future = executorService.submit(callable);
        Future<Bitmap> future2 = executorService.submit(callable2);
        Future<Bitmap> future3 = executorService.submit(callable3);



            if(future.get() != null){
                imgAvata.setImageBitmap(future.get());
            }
//            if(future.get() != null){
//                imgAvata2.setImageBitmap(future2.get());
//
//            }  if(future.get() != null){
//                imgAvata3.setImageBitmap(future3.get());
//            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

//    private Handler messageHandler = new Handler(){
//        public void HandlerMessage(Message msg){
//            super.handleMessage(msg);
//            imgAvata.setImageBitmap(mbitmap);
//        }
//    };


    private Bitmap loadImageFromUrl(String url){
        Bitmap  bitmap=null;
        URL mUrl=null;
        try {
            mUrl = new URL(url);
            bitmap = BitmapFactory.decodeStream(mUrl.openConnection().getInputStream());

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return bitmap;
    }
}