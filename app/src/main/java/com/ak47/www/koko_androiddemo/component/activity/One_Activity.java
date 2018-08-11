package com.ak47.www.koko_androiddemo.component.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ak47.www.koko_androiddemo.R;

/**
 * 四大组件之activity
 */
public class One_Activity extends AppCompatActivity {
    private String TAG = "One_Activity";
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate():activity调用的第一个方法/当内存不足时，用户用navigates back到activity时，在onPause/onStop状态的activity会调用");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ComponentName component = new ComponentName(One_Activity.this, Two_Activity.class);
                intent.setComponent(component);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart():紧接着onCreate后调用/onRestart后调用");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume():紧接着onStart后调用，接着Activity is running/或者这个activity回到foreground在onPause()后调用");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause():onStop()调用前为保存数据的必要调用/当另外一个activity启动时调用");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop():这个activity是不再可见时调用");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart():activity由onstop不可见状态回到foreground时调用");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy():activity摧毁时调用");
    }
}
