/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ak47.www.koko_androiddemo.component.fragment.demo1;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ak47.www.koko_androiddemo.R;

/**
 * Fragment的知识点：
 * 1.Fragment都需要FrameLayout来做容器，方便FragmentManager动态的添加，替换，删除Fragment
 * 唯一一种不需要FrameLayout来做容器，就该Fragment是静止的不涉及上述的改动
 * <p>
 * 2.Fragment的可重用性和模块性很重要，所以要解耦
 */

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class AndroidMeActivity extends AppCompatActivity {
    private static final String TAG = "AndroidMeActivity";

    // TODO (1) Create a layout file that displays one body part image named fragment_body_part.xml
    // This layout should contain a single ImageView

    // TODO (2) Create a new class called BodyPartFragment to display an image of an Android-Me body part
    // In this class, you'll need to implement an empty constructor and the onCreateView method
    // TODO (3) Show the first image in the list of head images
    // Soon, you'll update this image display code to show any image you want


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        //屏幕旋转Activity也会销毁，再重建
        //只有Fragment保存了状态Activity的savedInstanceState就不为空，原因不清楚
        if (savedInstanceState == null) {
            Log.e(TAG, "Android没有保存状态");
            // TODO (5) Create a new BodyPartFragment instance and display it using the FragmentManager
            BodyPartFragment headFragment = new BodyPartFragment();
            headFragment.setmImageIds(AndroidImageAssets.getHeads());
            headFragment.setmListIndex(0);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.head_container, headFragment)
                    .commit();

            // New body fragment
            BodyPartFragment bodyFragment = new BodyPartFragment();
            bodyFragment.setmImageIds(AndroidImageAssets.getBodies());
            bodyFragment.setmListIndex(0);
            fragmentManager.beginTransaction()
                    .add(R.id.body_container, bodyFragment)
                    .commit();

            // New leg fragment
            BodyPartFragment legFragment = new BodyPartFragment();
            legFragment.setmImageIds(AndroidImageAssets.getLegs());
            legFragment.setmListIndex(0);
            fragmentManager.beginTransaction()
                    .add(R.id.leg_container, legFragment)
                    .commit();
        }

    }
}
