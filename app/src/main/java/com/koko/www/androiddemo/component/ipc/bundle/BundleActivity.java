package com.koko.www.androiddemo.component.ipc.bundle;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.koko.www.androiddemo.R;

/**
 * 关于怎么使用Bundle来传递数据
 */
public class BundleActivity extends AppCompatActivity {
    private TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        textView = findViewById(R.id.text);
        textView.setText("String:" + bundle.get("str") + "int:" + bundle.get("int"));
    }
}
