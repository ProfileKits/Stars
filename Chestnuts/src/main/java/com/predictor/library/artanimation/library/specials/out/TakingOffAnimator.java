package com.predictor.library.artanimation.library.specials.out;

import android.animation.ObjectAnimator;
import android.view.View;

import com.predictor.library.artanimation.library.BaseViewAnimator;
import com.predictor.library.artanimation.library.base.Glider;
import com.predictor.library.artanimation.library.base.Skill;

public class TakingOffAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1f, 1.5f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1f, 1.5f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 1, 0))
        );
    }
}
