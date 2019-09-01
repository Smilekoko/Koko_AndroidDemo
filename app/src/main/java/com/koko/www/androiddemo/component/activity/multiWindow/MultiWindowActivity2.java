package com.koko.www.androiddemo.component.activity.multiWindow;

import android.content.ClipData;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.koko.www.androiddemo.R;

import static android.text.Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE;

public class MultiWindowActivity2 extends AppCompatActivity {
    private static StringBuilder mBuilder = new StringBuilder();
    private TextView mTextView;
    private ScrollView mScrollView;
    private TextView mDrawTextView;
    private ImageView mDrawImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_window2);
        setTitle("Second Activity");
        mTextView = findViewById(R.id.textview);
        mScrollView = findViewById(R.id.scrollview);
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        mDrawTextView = findViewById(R.id.drag_textview);
        mDrawImageView = findViewById(R.id.drag_imageview);
        mDrawTextView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                    case DragEvent.ACTION_DROP:
                        mDrawTextView.setVisibility(View.GONE);
                        mDrawImageView.setVisibility(View.VISIBLE);
                        ClipData clipData = dragEvent.getClipData();
                        mDrawImageView.setImageURI(clipData.getItemAt(1).getUri());
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        print("onCreate", "#ff0000", false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        print("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        print("onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        print("onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        print("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        print("onDestory", "#0000ff", false);
        print("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        print("onPause");
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        print("InMultiWindowMode:" + isInMultiWindowMode, null, true);
    }

    private void print(String msg) {
        mBuilder.append(msg).append("<br />");
        mTextView.setText(Html.fromHtml(mBuilder.toString(), TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
    }

    public void print(String msg, String color, boolean bold) {
        mBuilder.append(TextUtils.isEmpty(color) ? "" : "<font color='" + color + "'>").append(bold ? "<strong>" : "")
                .append(msg).append(bold ? "</strong>" : "").append(TextUtils.isEmpty(color) ? "" : "</font>")
                .append("<br />");
        mTextView.setText(Html.fromHtml(mBuilder.toString(), TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
    }


    public void reset(View view) {
        mDrawImageView.setVisibility(View.GONE);
        mDrawTextView.setVisibility(View.VISIBLE);
        clear(view);
    }

    public void clear(View view) {
        mBuilder.delete(0, mBuilder.length());
        mTextView.setText("");
    }
}
