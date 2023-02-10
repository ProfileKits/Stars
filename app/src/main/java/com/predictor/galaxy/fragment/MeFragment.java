package com.predictor.galaxy.fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.predictor.galaxy.R;
import com.predictor.galaxy.adapter.NotifyAdapterCN;
import com.predictor.galaxy.adapter.SwipSlidItemAdapterCN;
import com.predictor.galaxy.bean.Person;
import com.predictor.galaxy.swiphelper.DragSwipHelper;
import com.predictor.galaxy.swiphelper.SwipItemHelper;
import com.predictor.library.base.CNBaseFragment;
import com.predictor.library.base.recyclerview.CNBaseAdapter;
import com.predictor.library.utils.CNToast;
import com.predictor.library.utils.CNValidatorUtil;

import java.util.ArrayList;

public class MeFragment extends CNBaseFragment {
    private RecyclerView recyclerView;
    private SwipSlidItemAdapterCN adapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private SwipItemHelper swipItemHelper;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    private static final String TAG = "MeFragment";

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
//        CNToast.show(mContext,"是否为手机号："+ CNValidatorUtil.isPhone("17022222222",true)+"");
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) {
                arrayList.add("侧滑菜单固定在item底部不动");
            } else {
                arrayList.add("跟随item,从右滑出");
            }
        }
        adapter = new SwipSlidItemAdapterCN();
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
        swipItemHelper = new SwipItemHelper(adapter);
        swipItemHelper.attachRecyclerView(recyclerView);
    }

    @Override
    protected void initListener() {

    }

}