package com.predictor.galaxy.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.predictor.galaxy.R;


public class AnimationHolder extends RecyclerView.ViewHolder {
    public TextView txt;

    public AnimationHolder(@NonNull View itemView) {
        super(itemView);
        txt = itemView.findViewById(R.id.txt);
    }
}
