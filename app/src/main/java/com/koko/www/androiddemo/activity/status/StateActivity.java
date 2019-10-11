package com.koko.www.androiddemo.activity.status;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.koko.www.androiddemo.R;

public class StateActivity extends AppCompatActivity {
    static final String SOME_VALUE = "int_value";
    static final String SOME_OTHER_VALUE = "string_value";
    public static String StateActivity_TAG = "StateActivity";
    private int someIntValue;
    private String someStringValue;
    private Fragment simpleFragment;
    private final String SIMPLE_FRAGMENT_TAG = "myfragmenttag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(StateActivity_TAG, "-----Activity/onCreate()------");
        setContentView(R.layout.activity_state);

        //确保在配置更改时不会不必要地重新创建片段。
        if (savedInstanceState != null) {
            simpleFragment = getSupportFragmentManager().findFragmentByTag(SIMPLE_FRAGMENT_TAG);
        } else if (simpleFragment == null) {
            // only create fragment if they haven't been instantiated already
            simpleFragment = new SimpleFragment();
            Log.e(StateActivity_TAG,"SimpleFragment被创建");
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(StateActivity_TAG, "--------Activity/onResume()------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(StateActivity_TAG, "--------Activity/onDestroy()------");
    }

    //系统将在活动销毁之前调用该方法
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.e(StateActivity_TAG, "----Activity/onSaveInstanceState()-----");
        // Save custom values into the bundle
        savedInstanceState.putInt(SOME_VALUE, 1);
        savedInstanceState.putString(SOME_OTHER_VALUE, "koko");
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e(StateActivity_TAG, "----Activity/onRestoreInstanceState()-----");
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        someIntValue = savedInstanceState.getInt(SOME_VALUE);
        someStringValue = savedInstanceState.getString(SOME_OTHER_VALUE);
        Log.e(StateActivity_TAG, "Activity/someIntValue=" + someIntValue + "//" + "someStringValue=" + someStringValue);
    }
}
