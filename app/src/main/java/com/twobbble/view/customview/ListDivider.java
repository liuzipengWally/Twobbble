package com.twobbble.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * 主页RecyclerView的分隔线
 */
public class ListDivider extends RecyclerView.ItemDecoration {
    private static final int[] attrs = {android.R.attr.listDivider};//系统自带分割线文件，获取后先保存为数组

    private Drawable mDivider;
    private Context mContext;

    public ListDivider(Context context) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs); //将数组转化为TypedArray

        mDivider = typedArray.getDrawable(0);  //取出这个Drawable文件
        typedArray.recycle();   //回收TypedArray
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int childCount = parent.getChildCount();
        if (childCount != 0) {
            int left = 0;
            int right = (parent.getWidth());

            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
//                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom();
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
