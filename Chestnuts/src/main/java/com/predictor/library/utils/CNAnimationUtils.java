package com.predictor.library.utils;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import java.util.Random;

/**
 * 使用startWith启动常用动画
 * 例如：CNAnimationUtils.startWith(textView, CNAnimationUtils.IN_ROTATE360,  1000);
 */
public class CNAnimationUtils {
    private static final int SELF = Animation.RELATIVE_TO_SELF;
    private static final int PARENT = Animation.RELATIVE_TO_PARENT;

    public static final int IN_FAED = 1;
    public static final int IN_SLIDE = 2;
    public static final int IN_SCALE = 3;
    public static final int IN_ROTATE = 4;
    public static final int IN_SCALE_ROTATE = 5;
    public static final int IN_SLIDE_FADE = 6;
    public static final int IN_ROTATE360 = 7;
    public static final int OUT_FAED = 11;
    public static final int OUT_SLIDE = 12;
    public static final int OUT_SCALE = 13;
    public static final int OUT_ROTATE = 14;
    public static final int OUT_SCALE_ROTATE = 15;
    public static final int OUT_SLIDE_FADE = 16;
    public static final int OUT_RIGHT_SLIDE = 17;
    private static Random random = new Random();

    public static int randomAnimationInIndex() {
        return random.nextInt(IN_SLIDE_FADE) + 1;
    }

    /**
     * 反转动画的Index，比如传进来的是in，返回对应的out
     *
     * @return
     */
    public static int reversalAnimationIndex(int index) {
        if (index > 10) {
            return index - 10;
        } else {
            return index + 10;
        }
    }

    //设置并启动动画
    public static void startWith(View view, int index, long delayTimeToStop, long duration, int repeatCount) {
        Animation animation = getAnimation(index);
        animation.setDuration(duration);
        animation.setRepeatCount(repeatCount);
        animation.startNow();
        if (delayTimeToStop > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.clearAnimation();
                }
            }, delayTimeToStop);
        }
        view.setAnimation(animation);
    }

    //设置并启动动画
    public static Animation startWith(View view, int index, long duration) {
        Animation animation = getAnimation(index);
        animation.setDuration(duration);
        animation.setRepeatCount(-1);
        animation.startNow();
        view.setAnimation(animation);
        return animation;
    }

    //停止动画
    public static void stopWith(View view){
        view.clearAnimation();
    }

    public static Animation getAnimation(int index) {
        Animation animation = null;
        switch (index) {
            case IN_FAED:
                animation = new AlphaAnimation(0, 1);
                break;
            case IN_SLIDE:
                animation = new TranslateAnimation(PARENT, 1, PARENT, 0, PARENT, 0, PARENT, 0);
                break;
            case IN_SCALE:
                animation = new ScaleAnimation(0, 1, 0, 1, PARENT, 0.5f, PARENT, 0.5f);
                break;
            case IN_ROTATE:
                animation = new RotateAnimation(-90, 0, SELF, 0, SELF, 1);
                break;
            case IN_ROTATE360:
                animation = getRotate();
                break;
            case IN_SCALE_ROTATE:
                animation = getScaleRotateIn();
                break;
            case IN_SLIDE_FADE:
                animation = getSlideFadeIn();
                break;
            case OUT_FAED:
                animation = new AlphaAnimation(1, 0);
                break;
            case OUT_SLIDE:
                animation = new TranslateAnimation(PARENT, 0, PARENT, -1, PARENT, 0, PARENT, 0);
                break;
            case OUT_SCALE:
                animation = new ScaleAnimation(1, 0, 1, 0, PARENT, 0.5f, PARENT, 0.5f);
                break;
            case OUT_ROTATE:
                animation = new RotateAnimation(0, 90, SELF, 0, SELF, 1);
                break;
            case OUT_SCALE_ROTATE:
                animation = getScaleRotateOut();
                break;
            case OUT_SLIDE_FADE:
                animation = getSlideFadeOut();
                break;
            case OUT_RIGHT_SLIDE:
                animation = new TranslateAnimation(PARENT, 0, PARENT, 1, PARENT, 0, PARENT, 0);
                break;
        }

        if (animation != null) {
            animation.setDuration(1500);
        }
        return animation;
    }

    private static Animation getScaleRotateIn() {
        ScaleAnimation animation1 = new ScaleAnimation(0, 1, 0, 1, SELF, 0.5f, SELF, 0.5f);
        RotateAnimation animation2 = new RotateAnimation(0, 360, SELF, 0.5f, SELF, 0.5f);
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(animation1);
        animation.addAnimation(animation2);
        return animation;
    }

    private static Animation getScaleRotateOut() {
        ScaleAnimation animation1 = new ScaleAnimation(1, 0, 1, 0, SELF, 0.5f, SELF, 0.5f);
        RotateAnimation animation2 = new RotateAnimation(0, 360, SELF, 0.5f, SELF, 0.5f);
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(animation1);
        animation.addAnimation(animation2);
        return animation;
    }


    private static Animation getRotate() {
        RotateAnimation anim = new RotateAnimation(0, 360, SELF, 0.5f, SELF, 0.5f);
        return anim;
    }

    private static Animation getSlideFadeIn() {
        TranslateAnimation animation1 = new TranslateAnimation(PARENT, 1, PARENT, 0, PARENT, 0, PARENT, 0);
        AlphaAnimation animation2 = new AlphaAnimation(0, 1);
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(animation1);
        animation.addAnimation(animation2);
        return animation;
    }

    private static Animation getSlideFadeOut() {
        TranslateAnimation animation1 = new TranslateAnimation(PARENT, 0, PARENT, -1, PARENT, 0, PARENT, 0);
        AlphaAnimation animation2 = new AlphaAnimation(1, 0);
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(animation1);
        animation.addAnimation(animation2);
        return animation;
    }

    public static void setVisible(View view) {
        TranslateAnimation showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        showAnim.setDuration(500);
        view.startAnimation(showAnim);
        view.setVisibility(View.VISIBLE);
    }

    public static void setGone(View view) {
        TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        hideAnim.setDuration(500);
        view.startAnimation(hideAnim);
        view.setVisibility(View.GONE);
    }

    public static void fadeIn(View view, float startAlpha, float endAlpha, long duration) {
        if (view.getVisibility() == View.VISIBLE) return;

        view.setVisibility(View.VISIBLE);
        Animation animation = new AlphaAnimation(startAlpha, endAlpha);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }


    public static void fadeIn(View view) {
        fadeIn(view, 0F, 1F, 1000);

        // We disabled the button in fadeOut(), so enable it here.
        view.setEnabled(true);
    }

    public static void fadeOut(View view) {
        if (view.getVisibility() != View.VISIBLE) return;

        // Since the button is still clickable before fade-out animation
        // ends, we disable the button first to block click.
        view.setEnabled(false);
        Animation animation = new AlphaAnimation(1F, 0F);
        animation.setDuration(400);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
    }


    public static void startShakeByPropertyAnim(View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration) {
        if (view == null) {
            return;
        }
        //TODO 验证参数的有效性

        //先变小后变大
        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );
        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );

        //先往左再往右
        PropertyValuesHolder rotateValuesHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0.1f, -shakeDegrees),
                Keyframe.ofFloat(0.2f, shakeDegrees),
                Keyframe.ofFloat(0.3f, -shakeDegrees),
                Keyframe.ofFloat(0.4f, shakeDegrees),
                Keyframe.ofFloat(0.5f, -shakeDegrees),
                Keyframe.ofFloat(0.6f, shakeDegrees),
                Keyframe.ofFloat(0.7f, -shakeDegrees),
                Keyframe.ofFloat(0.8f, shakeDegrees),
                Keyframe.ofFloat(0.9f, -shakeDegrees),
                Keyframe.ofFloat(0.1f, -shakeDegrees),
                Keyframe.ofFloat(0.2f, shakeDegrees),
                Keyframe.ofFloat(0.3f, -shakeDegrees),
                Keyframe.ofFloat(0.4f, shakeDegrees),
                Keyframe.ofFloat(0.5f, -shakeDegrees),
                Keyframe.ofFloat(0.6f, shakeDegrees),
                Keyframe.ofFloat(0.7f, -shakeDegrees),
                Keyframe.ofFloat(0.8f, shakeDegrees),
                Keyframe.ofFloat(0.9f, -shakeDegrees),
                Keyframe.ofFloat(1.0f, 0f)
        );

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXValuesHolder, scaleYValuesHolder, rotateValuesHolder);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

}
