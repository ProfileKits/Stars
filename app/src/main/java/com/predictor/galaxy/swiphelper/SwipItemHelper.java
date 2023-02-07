package com.predictor.galaxy.swiphelper;

import androidx.recyclerview.widget.RecyclerView;


public class SwipItemHelper {
    private WItemTouchHelperPlus extension;
    public SwipItemHelper(SlidTranslate slidTranslate) {
        PlusItemSlideCallback callback = new PlusItemSlideCallback(slidTranslate);
        extension = new WItemTouchHelperPlus(callback);
    }

    public void attachRecyclerView(RecyclerView recyclerView) {
        extension.attachToRecyclerView(recyclerView);
    }
}
