package com.ak47.www.koko_androiddemo.component.activity.launchMode;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ak47.www.koko_androiddemo.R;
import com.ak47.www.koko_androiddemo.component.activity.One_Activity;
import com.ak47.www.koko_androiddemo.component.activity.Two_Activity;

/**
 * 测试Intent.FLAG_ACTIVITY_CLEAR_TOP tag
 */
public class CLEAR_TOPActivity extends AppCompatActivity {
    private String TAG = "Intent.FLAG_ACTIVITY_CLEAR_TOP";
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear__top);
        button = (Button) findViewById(R.id.btn_startOne);
        editText = (EditText) findViewById(R.id.edit_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ComponentName component = new ComponentName(CLEAR_TOPActivity.this, One_Activity.class);
                intent.setComponent(component);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "实例摧毁了");
    }


    /**
     * Intent.FLAG_ACTIVITY_CLEAR_TOP tag启动时，调用该方法
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        editText.setText(intent.getAction());
        super.onNewIntent(intent);
    }
}
