package com.twobbble.view.customview;

import android.app.Fragment;

/**
 * Created by liuzipeng on 2016/9/28.
 * 该抽象类用于实现fragment的懒加载,因为fragment被Viewpager管理的时候,Viewpager会同时去走当前的fragment和前一个fragment以及后一个fragment的生命周期,这样就会导致fragment中
 * 不该此时被执行的代码会被执行,而通过重写setUserVisibleHint方法,让其在fragment被设置为可见的时候去加载那部分代码就能解决这个问题。
 */

public abstract class LazyFragment extends Fragment {
    protected boolean isVisible;

    /**
     * 在这里实现Fragment数据的缓加载.
     * fragment 被设为可见的时候,会调用这个方法
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
        inVisible();
    }

    protected abstract void inVisible();
}
