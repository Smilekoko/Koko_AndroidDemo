package com.ak47.www.koko_androiddemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.ak47.www.koko_androiddemo.R;

/**
 * 圆形头像
 */
public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;
    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;
    private boolean mBorderOverlay = false;
    //均匀缩放图像（保持图像的纵横比）
    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;
    private boolean mReady;//是否试图准备好可以绘制
    private boolean mSetupPending;//等待设置

    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;//Paint对象设置Shader，控制绘制的内容
    private int mBitmapWidth;
    private int mBitmapHeight;
    private float mDrawableRadius;
    private float mBorderRadius;
    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();
    private final Matrix mShaderMatrix = new Matrix();
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;
    private final Paint mBitmapPaint = new Paint();
    private final Paint mBorderPaint = new Paint();
    private ColorFilter mColorFilter;

    //如果这个控件会在 java 代码中 new 的话，必须实现该构造函数
    public CircleImageView(Context context) {
        super(context);
        init();
    }

    //如果控件直接在 xml 中使用，必须实现该构造函数
    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //如果要自定义控件参数集就必须要实现该构造函数
    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取定义的CircleImageView的可设置参数集,且初始化
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_border_width, DEFAULT_BORDER_WIDTH);
        mBorderColor = a.getColor(R.styleable.CircleImageView_border_color, DEFAULT_BORDER_COLOR);
        mBorderOverlay = a.getBoolean(R.styleable.CircleImageView_border_overlay, DEFAULT_BORDER_OVERLAY);
        //完成后请务必打电话recycle()。
        a.recycle();
        init();
    }

    ////ImageView的方法:
    //返回当前ScaleType，用于将图像的边界缩放到ImageView的边界。
    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(scaleType);
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    //ImageView的方法:
    //如果希望ImageView调整其边界以保持其drawable的纵横比，请将此属性设置为true。
    //这里不支持调整边界
    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    //View的方法:
    //This is called during layout when the size of this view has changed.
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    //将位图设置为此ImageView的内容。
    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();

    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        //getDrawable():返回视图的drawable，如果没有指定drawable，则返回null。
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == mColorFilter) {
            return;
        }
        mColorFilter = cf;
        mBitmapPaint.setColorFilter(mColorFilter);
        //使整个视图无效。
        invalidate();
    }


    public int getmBorderColor() {
        return mBorderColor;
    }

    public void setmBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
    }

    public int getmBorderWidth() {
        return mBorderWidth;
    }

    public void setmBorderWidth(int mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
    }

    public boolean ismBorderOverlay() {
        return mBorderOverlay;
    }

    public void setmBorderOverlay(boolean mBorderOverlay) {
        this.mBorderOverlay = mBorderOverlay;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius, mBitmapPaint);
        if (mBorderWidth != 0) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBorderRadius, mBorderPaint);
        }
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        mReady = true;
        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }
        if (mBitmap == null) {
            return;
        }
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();
        mBorderRect.set(0, 0, getWidth(), getHeight());
        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2,
                (mBorderRect.width() - mBorderWidth) / 2);
        mDrawableRect.set(mBorderRect);
        if (!mBorderOverlay) {
            mDrawableRect.inset(mBorderWidth, mBorderWidth);
        }
        mDrawableRadius = Math.min(mDrawableRect.height() / 2, mDrawableRect.width() / 2);
        updateShaderMatrix();
        invalidate();
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;
        mShaderMatrix.set(null);
        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        }
        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
