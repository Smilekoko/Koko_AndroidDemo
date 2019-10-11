package com.koko.www.androiddemo.activity.launchMode;

import android.content.ComponentName;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.koko.www.androiddemo.R;
import com.koko.www.androiddemo.activity.OneActivity;

/**
 * 测试Intent.FLAG_ACTIVITY_CLEAR_TOP tag
 */
public class CLEAR_TOPActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear__top);
        Button button = findViewById(R.id.btn_startOne);
        editText = findViewById(R.id.edit_text);
        button.setOnClickListener(view -> {
            Intent intent = new Intent();
            ComponentName component = new ComponentName(CLEAR_TOPActivity.this, OneActivity.class);
            intent.setComponent(component);
            startActivity(intent);
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        String TAG = "Intent.FLAG_ACTIVITY_CLEAR_TOP";
        Log.e(TAG, "实例摧毁了");
    }


    /**
     * Intent.FLAG_ACTIVITY_CLEAR_TOP tag启动时，调用该方法
     *
     * @param intent 新意图
     */
    @Override
    protected void onNewIntent(Intent intent) {
        editText.setText(intent.getAction());
        super.onNewIntent(intent);
    }
}
