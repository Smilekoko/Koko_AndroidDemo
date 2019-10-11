package com.koko.www.androiddemo.net.apache;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.koko.www.androiddemo.R;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by 17962 on 2018/4/5.
 * <p/>
 * 关于apache网络接口HttpClient的demo
 */

public class HttpClient_activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_net_test);

        EditText message = findViewById(R.id.edit_text);
        Button get_button = findViewById(R.id.get_button);
        Button post_button = findViewById(R.id.post_button);

        get_button.setOnClickListener(v -> get());


    }


    /**
     * 以get方式发送请求和响应处理
     */
    private static void get() {
        new Thread(() -> {

            //第一步
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                //第二步
                HttpGet httpget = new HttpGet("http://httpbin.org/");

                System.out.println("Executing request " + httpget.getRequestLine());

                //第三步
                // Create a custom response handler
                ResponseHandler<String> responseHandler = response -> {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                };


                String responseBody = httpclient.execute(httpget, responseHandler);
                System.out.println("responseBody:-----" + responseBody);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }


}
