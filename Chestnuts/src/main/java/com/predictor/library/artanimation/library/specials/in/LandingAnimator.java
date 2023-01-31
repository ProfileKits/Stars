package com.predictor.library.artanimation.library.specials.in;

import android.animation.ObjectAnimator;
import android.view.View;

import com.predictor.library.artanimation.library.BaseViewAnimator;
import com.predictor.library.artanimation.library.base.Glider;
import com.predictor.library.artanimation.library.base.Skill;

public class LandingAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 0, 1f))
        );
    }
}
