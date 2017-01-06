package com.smarttop.library;

import com.smarttop.library.effects.BaseEffects;
import com.smarttop.library.effects.FadeIn;
import com.smarttop.library.effects.Fall;
import com.smarttop.library.effects.FlipH;
import com.smarttop.library.effects.FlipV;
import com.smarttop.library.effects.NewsPaper;
import com.smarttop.library.effects.RotateBottom;
import com.smarttop.library.effects.RotateLeft;
import com.smarttop.library.effects.Shake;
import com.smarttop.library.effects.SideFall;
import com.smarttop.library.effects.SlideBottom;
import com.smarttop.library.effects.SlideLeft;
import com.smarttop.library.effects.SlideRight;
import com.smarttop.library.effects.SlideTop;
import com.smarttop.library.effects.Slit;

/**
 * Created by smartTop on 2016/12/23.
 */

public enum Effectstype {
    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    Slidebottom(SlideBottom.class),
    Slideright(SlideRight.class),
    fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    Rotatebottom(RotateBottom.class),
    Rotateleft(RotateLeft.class),
    slit(Slit.class),
    shake(Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }
    public BaseEffects getAnimator() {
        BaseEffects bEffects = null;
        try {
            bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e){
            throw new Error("Can not init animatorClazz instance");
        }catch (InstantiationException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (IllegalAccessException e) {
            throw new Error("Can not init animatorClazz instance");
        }
        return bEffects;
    }
}
