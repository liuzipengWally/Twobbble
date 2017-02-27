package com.twobbble.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.twobbble.R;
import com.twobbble.tools.Utils;

/**
 * 显示圆形文字的自定义View
 */
public class CircleIcon extends View {
    private String mText;
    private int mColor;
    private int mTextSize;
    private int mIconResId;

    public CircleIcon(Context context) {
        this(context, null);
    }

    public CircleIcon(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getAttributes(context, attrs);
    }


    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIcon);

        mText = typedArray.getString(R.styleable.CircleIcon_circleText);
        mTextSize = (int) typedArray.getDimension(R.styleable.CircleIcon_circleTextSize, Utils.INSTANCE.sp2px(20, getResources().getDisplayMetrics()));
        mColor = typedArray.getColor(R.styleable.CircleIcon_circleColor, getResources().getColor(R.color.colorAccent));
        mIconResId = typedArray.getResourceId(R.styleable.CircleIcon_circleIcon, 0);

        typedArray.recycle();
    }

    public void setText(String text) {
        this.mText = text;
        invalidate();
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
        invalidate();
    }

    public int getColor() {
        return mColor;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        invalidate();
    }

    public void setIconResId(int resId) {
        this.mIconResId = resId;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(mColor);

        float radius = getMeasuredWidth() / 2;
        canvas.drawCircle(radius, radius, radius, circlePaint);

        if (mText != null) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setTextSize(mTextSize);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);

            canvas.drawText(mText, radius, radius + (mTextSize / 3), paint);
        } else {
            if (mIconResId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mIconResId);
                canvas.drawBitmap(bitmap, getMeasuredWidth() / 2 - bitmap.getWidth() / 2, getMeasuredHeight() / 2 - bitmap.getHeight() / 2, null);
            }
        }
    }
}
