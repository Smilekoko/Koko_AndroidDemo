package com.koko.www.androiddemo.activity.launchMode;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.koko.www.androiddemo.R;

/**
 * 测试taskAffinity属性
 */
public class TaskAffinityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_affinity);
    }
}
