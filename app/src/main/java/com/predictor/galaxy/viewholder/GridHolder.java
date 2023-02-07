package com.predictor.galaxy.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.predictor.galaxy.R;

public class GridHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public GridHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
    }
}
