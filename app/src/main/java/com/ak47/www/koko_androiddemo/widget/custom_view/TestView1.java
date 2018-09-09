package com.ak47.www.koko_androiddemo.widget.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ak47.www.koko_androiddemo.R;

/**
 * 1，首先是对onMeasure(int widthMeasureSpec, int heightMeasureSpec)进行解析
 * 实现宽高同值
 * <p>
 * 2，接着onDraw()函数绘制，画一个圆
 * <p>
 * 3.自定义布局属性,在attrs.xml中定义
 */
public class TestView1 extends View {
    private int defalutSize;

    //如果这个控件会在 java 代码中 new 的话，必须实现该构造函数
    public TestView1(Context context) {
        super(context);
    }

    //如果控件直接在 xml 中使用，必须实现该构造函数
    public TestView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TestView1);
        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        defalutSize = a.getDimensionPixelSize(R.styleable.TestView1_default_size, 100);
        //最后记得将TypedArray对象回收
        a.recycle();
    }


    /**
     * 测量宽高尺寸
     * layout_width和layout_height参数可以不用写具体的尺寸，而是wrap_content或者是match_parent时。
     * int型数据占用32个bit
     * int数据的前面2个bit用于区分不同的布局模式，后面30个bit存放的是尺寸的数据。
     * <p>
     * 模式解析
     * match_parent--->EXACTLY：match_parent就是要利用父View给我们提供的所有剩余空间，而父View剩余空间是确定的，也就是这个测量模式的整数里面存放的尺寸。
     * 固定尺寸（如100dp）--->EXACTLY：用户自己指定了尺寸大小，我们就不用再去干涉了，当然是以指定的大小为主啦。
     * wrap_content--->AT_MOST：就是我们想要将大小设置为包裹我们的view内容，那么尺寸大小就是父View给我们作为参考的尺寸，只要不超过这个尺寸就可以啦，具体尺寸就根据我们的需求去设定。
     *
     * @param widthMeasureSpec  宽的测量模式和尺寸信息的int混合值
     * @param heightMeasureSpec 高的测量模式和尺寸信息的int混合值
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量模式              表示意思
        //UNSPECIFIED           父容器没有对当前View有任何限制，当前View可以任意取尺寸
        //EXACTLY               当前的尺寸就是当前View应该取的尺寸
        //AT_MOST               当前尺寸是当前View能取的最大尺寸
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        //这里的的尺寸大小并不是最终我们的View的尺寸大小，而是父View提供的参考大小。
//        int widthSiz = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSiz = MeasureSpec.getSize(heightMeasureSpec);

        int width = getMySize(defalutSize, widthMeasureSpec);
        int height = getMySize(defalutSize, heightMeasureSpec);
        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        //最后要调用该函数测量尺寸才生效
        setMeasuredDimension(width, height);
    }

    /**
     * @param defaultSize 默认尺寸
     * @param measureSpec 宽/高 包含测量模式和测量尺寸的复合值
     * @return
     */
    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //测量值的一半为半径
        int r = getMeasuredHeight() / 2;
        //当前View的起始位置+半径为圆心
        int centerX = getLeft() + r;
        int centerY = getTop() + r;

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        canvas.drawCircle(centerX, centerY, r, paint);
    }
}
