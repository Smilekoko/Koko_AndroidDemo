package com.ak47.www.koko_androiddemo.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.ak47.www.koko_androiddemo.R;

/**
 * Created by 1796 on 2018/7/14.
 * 自定义一个有删除符号的的EditText
 */

public class EditTextWithClear extends AppCompatEditText {
    private Drawable mClearButtonImage;

    /**
     * Required for creating an instance of a View from code.
     *
     * @param context
     */
    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    /**
     * Required to inflate the view from an XML layout and apply XML attributes.
     *
     * @param context
     * @param attrs
     */
    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Required to apply a default style to all UI elements without having to specify it in each layout file.
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_clear_opaque_24dp, null);
        // TODO: If the X (clear) button is tapped, clear the text.
        // TODO: If the text changes, show or hide the X (clear) button.
    }

    @Override
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
    }
}
