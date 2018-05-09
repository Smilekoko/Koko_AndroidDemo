package com.ak47.www.koko_androiddemo.structure;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ak47.www.koko_androiddemo.R;

import static com.ak47.www.koko_androiddemo.structure.StateActivity.StateActivity_TAG;


/**
 * A simple {@link Fragment} subclass.
 * 置更改时(屏幕旋转)是保存状态
 */
public class SimpleFragment extends Fragment {
    private int someStateValue;
    private final String SOME_VALUE_KEY = "someValueToSave";


    public SimpleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.e(StateActivity_TAG, "----Fragment/onCreate()----");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Log.e(StateActivity_TAG, "----Fragment/onCreateView()----");
        View view = inflater.inflate(R.layout.fragment_simple, container, false);
        if (savedInstanceState != null) {
            someStateValue = savedInstanceState.getInt(SOME_VALUE_KEY);
            // Do something with value if needed
//            Log.e(StateActivity_TAG, "Fragment/someStateValue=" + someStateValue);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
//        Log.e(StateActivity_TAG, "----Fragment/onSaveInstanceState()----");
        outState.putInt(SOME_VALUE_KEY, 666);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.e(StateActivity_TAG, "----Fragment/onDestroy()----");
    }
}
