package com.predictor.galaxy.adapter;

import static com.predictor.library.utils.CNScreenUtil.dip2px;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.predictor.galaxy.R;
import com.predictor.galaxy.viewholder.GridHolder;
import com.predictor.library.base.recyclerview.CNBaseAdapter;


public class PullAdapterCN extends CNBaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        return new GridHolder(getResId(viewGroup, R.layout.item_pull));
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        GridHolder gridHolder = (GridHolder) viewHolder;
        ViewGroup.LayoutParams layoutParams = gridHolder.image.getLayoutParams();
        layoutParams.height = dip2px(gridHolder.image.getContext(), (position % 6 + 3) * 50);

        switch (position % 6) {
            case 0:
                gridHolder.image.setImageResource(R.drawable.image);
                break;
            case 1:
                gridHolder.image.setImageResource(R.drawable.image);
                break;
            case 2:
                gridHolder.image.setImageResource(R.drawable.image);
                break;
            case 3:
                gridHolder.image.setImageResource(R.drawable.image);
                break;
            case 4:
                gridHolder.image.setImageResource(R.drawable.image);
                break;
            case 5:
                gridHolder.image.setImageResource(R.drawable.image);
                break;
        }

    }
}
