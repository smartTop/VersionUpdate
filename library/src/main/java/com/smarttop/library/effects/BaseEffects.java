package com.smarttop.library.effects;

import android.view.View;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by smartTop on 2016/12/23.
 */

public abstract class BaseEffects {
    private static final int DURATION = 1*700;
    protected long mDuration= DURATION;
    private AnimatorSet mAnimatorSet;
    {
        mAnimatorSet = new AnimatorSet();
    }

    /**
     * 一个抽象方法 动画的执行 具体实现在子类中实现
     * @param view
     */
    protected abstract void setupAnimation(View view);

    /**
     * 开启动画
     * @param view
     */
    public void start(View view){
        reset(view);
        setupAnimation(view);
        mAnimatorSet.start();
    }

    /**
     * 重置动画的最初位置
     * @param view
     */
    public void reset(View view){
        ViewHelper.setPivotX(view, view.getMeasuredWidth() / 2.0f);
        ViewHelper.setPivotY(view, view.getMeasuredHeight() / 2.0f);
    }
    public AnimatorSet getAnimatorSet(){
        return mAnimatorSet;
    }
    public void setDuration(long duration){
        this.mDuration = duration;
    }
}
