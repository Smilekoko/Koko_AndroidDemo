package com.ak47.www.koko_androiddemo.component.fragment.demo1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ak47.www.koko_androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class BodyPartFragment extends Fragment {
    private final static String TAG = "";
    private List<Integer> mImageIds;
    private int mListIndex;
    //保存状态的tag
    public static final String IMAGE_ID_LIST = "iamge_ids";
    public static final String LIST_INDEX = "list_index";

    public void setmImageIds(List<Integer> mImageIds) {
        this.mImageIds = mImageIds;
    }

    public void setmListIndex(int mListIndex) {
        this.mListIndex = mListIndex;
    }

    public BodyPartFragment() {
    }

    /**
     * 当Fragment旋转时会经历销毁和建立，Fragment销毁之前被调用，保存销毁前的状态
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
        outState.putInt(LIST_INDEX, mListIndex);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //检测保存状态的savedInstanceState，是否为null,不为空恢复状态
        if (savedInstanceState != null) {
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        View view = inflater.inflate(R.layout.fragment_body_part, container, false);
        final ImageView imageView = view.findViewById(R.id.body_part_image_view);
        if (mImageIds != null) {
            imageView.setImageResource(mImageIds.get(mListIndex));
            //给imageView添加点击监听,实现点击切换图片
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListIndex < mImageIds.size() - 1) {
                        mListIndex++;
                    } else {
                        mListIndex = 0;
                    }
                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });
        } else {
            Log.e(TAG, "This fragment has null list of image id's");
        }
        return view;
    }
}
