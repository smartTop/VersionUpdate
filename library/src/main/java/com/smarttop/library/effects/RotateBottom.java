package com.smarttop.library.effects;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by smartTop on 2016/12/23.
 */

public class RotateBottom extends BaseEffects{
    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view,"rotationX",90,0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view,"translationY",300,0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view,"alpha",0,1).setDuration(mDuration*3/2)
        );
    }
}
