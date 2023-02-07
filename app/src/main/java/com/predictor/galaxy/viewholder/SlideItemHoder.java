package com.predictor.galaxy.viewholder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.predictor.galaxy.R;


public class SlideItemHoder extends SwipBaseHolder {
    public RelativeLayout slide_itemView;
    public TextView txt_text;
    public TextView zhiding, shanchu;

    public SlideItemHoder(@NonNull View itemView) {
        super(itemView);
        slide_itemView = itemView.findViewById(R.id.slide_itemView);
        txt_text = itemView.findViewById(R.id.txt_text);
        zhiding = itemView.findViewById(R.id.zhiding);
        shanchu = itemView.findViewById(R.id.shanchu);
    }

    @Override
    public int getSlidItemWith() {
        return (int) itemView.getContext().getResources().getDimension(R.dimen.dp_160);
    }
}
