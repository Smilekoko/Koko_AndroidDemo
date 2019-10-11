package com.koko.www.androiddemo.service;

import android.content.ComponentName;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.koko.www.androiddemo.R;

/**
 * 四大组件之service
 */
public class Service_Activity extends AppCompatActivity {
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(view -> {
            Intent intent = new Intent();
            ComponentName component = new ComponentName(Service_Activity.this, One_Service.class);
            intent.setComponent(component);
            startService(intent);
        });
    }
}
