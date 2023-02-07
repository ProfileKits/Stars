package com.predictor.library.base.recyclerview;


import com.predictor.library.R;

public enum CNAnimationType {
    TRANSLATE_FROM_RIGHT(R.anim.item_translate_byright),
    TRANSLATE_FROM_LEFT(R.anim.item_translate_byleft),
    TRANSLATE_FROM_BOTTOM(R.anim.item_translate_bybottom),
    SCALE(R.anim.item_scale_anim),
    ALPHA(R.anim.item_alpha_anim);

    private int resId;
    CNAnimationType(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
