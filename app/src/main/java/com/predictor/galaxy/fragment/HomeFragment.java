package com.predictor.galaxy.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.predictor.galaxy.R;
import com.predictor.galaxy.adapter.PullAdapterCN;
import com.predictor.library.base.CNBaseFragment;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends CNBaseFragment {
    private RecyclerView recyclerView;
    private PullAdapterCN adapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    private static final String TAG = "HomeFragment";

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 15; i++) {
            arrayList.add("1");
        }
        adapter = new PullAdapterCN();
        adapter.setGridDivide(recyclerView, (int) getResources().getDimension(R.dimen.dp_10));
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
        addHead();
        addFoot();
    }

    @Override
    protected void initListener() {

    }


    public void addHead() {
        View headLayout = LayoutInflater.from(mContext).inflate(R.layout.layout_head, null);
        TextView head_txt = headLayout.findViewById(R.id.head_txt);
        int red = new Random().nextInt(255);
        int green = new Random().nextInt(255);
        int blue = new Random().nextInt(255);

        head_txt.setBackgroundColor(Color.argb(255, red, green, blue));
        adapter.addHeadView(headLayout);
    }


    public void removeHead() {
        adapter.removeHeadView(0);
    }


    public void addFoot() {
        View headLayout = LayoutInflater.from(mContext).inflate(R.layout.layout_foot, null);
        TextView head_txt = headLayout.findViewById(R.id.head_txt);
        int red = new Random().nextInt(255);
        int green = new Random().nextInt(255);
        int blue = new Random().nextInt(255);

        head_txt.setBackgroundColor(Color.argb(255, red, green, blue));
        adapter.addFootView(headLayout);
    }


    public void removeFoot() {
        adapter.removeFootView(0);
    }
}