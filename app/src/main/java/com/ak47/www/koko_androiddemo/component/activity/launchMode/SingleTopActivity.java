package com.ak47.www.koko_androiddemo.component.activity.launchMode;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ak47.www.koko_androiddemo.R;
import com.ak47.www.koko_androiddemo.component.activity.One_Activity;

/**
 * 测试launchMode的singleTop属性
 */
public class SingleTopActivity extends AppCompatActivity {
    private Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);
        button = (Button) findViewById(R.id.btn_startSelf);
        button2 = (Button) findViewById(R.id.btn_startOthers);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ComponentName component = new ComponentName(SingleTopActivity.this, SingleTopActivity.class);
                intent.setComponent(component);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ComponentName component = new ComponentName(SingleTopActivity.this, One_Activity.class);
                intent.setComponent(component);
                startActivity(intent);
            }
        });
    }
}
