package com.ak47.www.koko_androiddemo.thread_asyn.asynctask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.ak47.www.koko_androiddemo.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 用AsyncTask创建异步任务来下载远程图片
 * AsyncTask是一种在后台线程中执行操作而不必手动处理线程创建或执行的机制。
 * AsyncTasks被设计为用于短操作（最多几秒）
 */
public class AsyncTask_activity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (isNetworkAvailable()) {
            String url = "https://i.imgur.com/tGbaZCY.jpg";
            new ImageDownloadTask(imageView).execute(url);
        }
    }

    //验证网络可用性
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    //自定义AsyncTask的内部类
    // Params - 传递给execute（）方法的类型。
    // Progress - 任务内用于跟踪进度的类型。
    // Result - 由doInBackground（）返回的类型。
    private class ImageDownloadTask extends AsyncTask<String, Integer, Bitmap> {
        int count = 0;

        private static final String TAG = "AsyncTask_activity";
        ImageView imageView;

        ImageDownloadTask(ImageView imageView) {
            this.imageView = imageView;
        }

        //在后台线程中执行，以执行网络下载等操作。
        @Override
        protected Bitmap doInBackground(String... addresses) {
            Bitmap bitmap = null;
            InputStream in = null;
            try {
                count = 1;
                // 1. Declare a URL Connection
                URL url = new URL(addresses[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                publishProgress(count * 20);

                count = 2;
                // 2. Open InputStream to connection
                conn.connect();
                in = conn.getInputStream();
                publishProgress(count * 20);

                count = 3;
                // 3. Download and decode the bitmap using BitmapFactory
                bitmap = BitmapFactory.decodeStream(in);
                publishProgress(count * 20);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                count = 4;
                if ((in != null)) {
                    try {
                        in.close();
                        publishProgress(count * 20);
                    } catch (IOException e) {
                        Log.e(TAG, "Exception while closing inputstream" + e);
                    }
                }
            }
            return bitmap;
        }

        // Fires after the task is completed, displaying the bitmap into the ImageView
        @Override
        protected void onPostExecute(Bitmap result) {

            count = 5;
            // Set bitmap image for the result
            imageView.setImageBitmap(result);
            publishProgress(count * 20);
        }

        //onPreExecute - 在主线程中执行，以创建初始进度栏视图。
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        //当publishProgress从doInBackground调用时，在主线程中执行。
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }


}
