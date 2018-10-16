package com.ak47.www.koko_androiddemo.component.ipc.bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ak47.www.koko_androiddemo.R;

public class BundleActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        textView = (TextView) findViewById(R.id.text);
        textView.setText("String:" + bundle.get("str") + "int:" + bundle.get("int"));
    }
}
