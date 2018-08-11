package com.ak47.www.koko_androiddemo.component.service;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ak47.www.koko_androiddemo.R;
import com.ak47.www.koko_androiddemo.component.activity.One_Activity;
import com.ak47.www.koko_androiddemo.component.activity.Two_Activity;

/**
 * 四大组件之service
 */
public class Service_Activity extends AppCompatActivity {
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ComponentName component = new ComponentName(Service_Activity.this, One_Service.class);
                intent.setComponent(component);
                startService(intent);
            }
        });
    }
}
