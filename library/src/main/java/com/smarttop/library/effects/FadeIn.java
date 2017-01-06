package com.smarttop.library.effects;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by smartTop on 2016/12/23.
 */

public class FadeIn extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(ObjectAnimator.ofFloat(view,"alpha",0,1).setDuration(mDuration));
    }
}
