package com.koko.www.androiddemo.activity.multiWindow;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.koko.www.androiddemo.R;

import static android.text.Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE;

/**
 * 这是一个关于多窗口的demo
 */
public class MultiWindowActivity1 extends AppCompatActivity {
    private TextView mTextView;
    private ScrollView mScrollView;
    private static StringBuilder mBuilder = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_window);
        setTitle("Frist Activity");
        mTextView = findViewById(R.id.textview);
        mScrollView = findViewById(R.id.scrollview);
        mScrollView.post(() -> mScrollView.fullScroll(View.FOCUS_DOWN));
        ImageView mImageView = findViewById(R.id.imageview);
        mImageView.setOnLongClickListener(view -> {
            Uri uri = getUri(MultiWindowActivity1.this, R.mipmap.ic_launcher);
            ClipData dragData = ClipData.newPlainText("", (CharSequence) view.getTag());
            ClipData.Item imageItem = new ClipData.Item(uri);
            dragData.addItem(imageItem);
            View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
            view.startDragAndDrop(dragData, shadow, view, View.DRAG_FLAG_GLOBAL);
            return true;
        });

        //<font color='#ff0000'>onCreate</font><br />
        print("onCreate", "#ff0000", false);
    }
    /**
     * Convert uri by drawable id.
     * Uri uri = Uri.parse("android.resource://com.wx.multiwindow/mipmap/ic_launcher");
     *
     */
    public Uri getUri(Context context, int id) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + context.getResources().getResourcePackageName(id) + "/"
                + context.getResources().getResourceTypeName(id) + "/"
                + context.getResources().getResourceEntryName(id));
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
    protected void onPause() {
        super.onPause();
        print("onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        print("onDestory", "#0000ff", false);
        print("");
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        print("InMultiWindowMode:" + isInMultiWindowMode, null, true);
    }

    private void print(String msg) {
        mBuilder.append(msg).append("<br />");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextView.setText(Html.fromHtml(mBuilder.toString(), TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
        }
    }

    /**
     * 打印富文本信息
     */
    public void print(String msg, String color, boolean bold) {
        mBuilder.append(TextUtils.isEmpty(color) ? "" : "<font color='" + color + "'>")
                .append(bold ? "<strong>" : "")
                .append(msg).append(bold ? "</strong>" : "")
                .append(TextUtils.isEmpty(color) ? "" : "</font>")
                .append("<br />");
        //TO_HTML_PARAGRAPH_LINES_CONSECUTIVE:在<p>元素内包裹由'\ n'分隔的连续文本行。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextView.setText(Html.fromHtml(mBuilder.toString(), TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
        }
    }

    /**
     * 清理日志
     */
    public void clear(View v) {
        mBuilder.delete(0, mBuilder.length());
        mTextView.setText("");
    }

    /**
     * 跳转Activity2
     */
    public void click(View view) {
        Intent intent = new Intent(this, MultiWindowActivity2.class);
        //此标志仅用于分屏多窗口模式。新活动将显示在启动它的旁边。这只能与FLAG_ACTIVITY_NEW_TASK。一起使用 。
        // 此外，FLAG_ACTIVITY_MULTIPLE_TASK如果要创建现有活动的新实例，则需要进行设置。
        intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
