package com.ak47.www.koko_androiddemo.net.apache;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ak47.www.koko_androiddemo.R;

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
    private EditText message;
    private Button get_button, post_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_net_test);

        message = (EditText) findViewById(R.id.edit_text);
        get_button = (Button) findViewById(R.id.get_button);
        post_button = (Button) findViewById(R.id.post_button);

        get_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get();
            }
        });

    }


    /**
     * 以get方式发送请求和响应处理
     */
    private static void get() {
        new Thread(new Runnable() {


            @Override
            public void run() {

                //第一步
                CloseableHttpClient httpclient = HttpClients.createDefault();
                try {
                    //第二步
                    HttpGet httpget = new HttpGet("http://httpbin.org/");

                    System.out.println("Executing request " + httpget.getRequestLine());

                    //第三步
                    // Create a custom response handler
                    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                        @Override
                        public String handleResponse(final HttpResponse response) throws IOException {
                            int status = response.getStatusLine().getStatusCode();
                            if (status >= 200 && status < 300) {
                                HttpEntity entity = response.getEntity();
                                return entity != null ? EntityUtils.toString(entity) : null;
                            } else {
                                throw new ClientProtocolException("Unexpected response status: " + status);
                            }
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

            }
        }).start();
    }


}
