package com.predictor.galaxy.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.predictor.galaxy.R;
import com.predictor.galaxy.swiphelper.SlidTranslate;
import com.predictor.galaxy.viewholder.SlideItemHoder;
import com.predictor.galaxy.viewholder.SwipBaseHolder;
import com.predictor.library.base.recyclerview.CNBaseAdapter;
import com.predictor.library.utils.CNToast;


public class SwipSlidItemAdapterCN extends CNBaseAdapter<String> implements SlidTranslate {
    final int TYPE_BOTTOM = 0;
    final int TYPE_WITH = 1;

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).equals("侧滑菜单固定在item底部不动")) {
            return TYPE_BOTTOM;
        } else {
            return TYPE_WITH;
        }
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_BOTTOM:
                return new SlideItemHoder(getResId(viewGroup, R.layout.item_slide_bottom));
            default:
                return new SlideItemHoder(getResId(viewGroup, R.layout.item_slide_with));
        }
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        SlideItemHoder slideItemHoder = (SlideItemHoder) viewHolder;
        String itemBean = dataList.get(position);
        slideItemHoder.txt_text.setText(itemBean + "  position=" + position);

        slideItemHoder.slide_itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CNToast.show(v.getContext(), "item点击  " + position);
            }
        });

        slideItemHoder.zhiding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CNToast.show(v.getContext(), "置顶  " + position);
            }
        });

        slideItemHoder.shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CNToast.show(v.getContext(), "删除  " + position);
            }
        });
    }


    @Override
    public void translationX(SwipBaseHolder swipBaseHolder, float dX) {
        SlideItemHoder slideItemHoder = (SlideItemHoder) swipBaseHolder;
        slideItemHoder.slide_itemView.setTranslationX(dX);
    }
}
