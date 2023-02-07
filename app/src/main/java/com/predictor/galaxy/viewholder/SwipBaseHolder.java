package com.predictor.galaxy.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.predictor.galaxy.swiphelper.Extension;


public abstract class SwipBaseHolder extends RecyclerView.ViewHolder implements Extension {

    public abstract int getSlidItemWith();

    public SwipBaseHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public float getActionWidth() {
        return getSlidItemWith();
    }
}
