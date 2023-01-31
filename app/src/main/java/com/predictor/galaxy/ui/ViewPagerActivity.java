package com.predictor.galaxy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RadioButton;

import com.predictor.galaxy.R;
import com.predictor.galaxy.fragment.HomeFragment;
import com.predictor.galaxy.fragment.MeFragment;
import com.predictor.library.adapter.PagerViewAdapter;
import com.predictor.library.base.CNBaseActivity;
import com.predictor.library.base.CNBaseFragment;
import com.predictor.library.listener.OnDoIntListener;
import com.predictor.library.utils.CNToast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends CNBaseActivity {
    private List<CNBaseFragment> fragments = new ArrayList<>();
    private PagerViewAdapter pagerViewAdapter;
    private ViewPager viewpager;
    private RadioButton rb_home, rb_me;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initView() {
        viewpager = findViewById(R.id.viewpager);
        rb_home = findViewById(R.id.rb_home);
        rb_me = findViewById(R.id.rb_me);
    }

    @Override
    protected void initData() {
        fragments.add(HomeFragment.newInstance());
        fragments.add(MeFragment.newInstance());
        pagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager(), fragments);
        pagerViewAdapter.setPageChangeListener(viewpager, new OnDoIntListener() {
            @Override
            public void doSomething(int intValue) {
                CNToast.show(ViewPagerActivity.this, intValue + "");
            }
        });

        //try {
//            Field field = ViewPager.class.getDeclaredField("mScroller");
//            field.setAccessible(true);
//            FixedSpeedScroller scroller = new FixedSpeedScroller(mContext, new AccelerateInterpolator());
//            field.set(viewpager, scroller);
//            scroller.setmDuration(300);
//        } catch (Exception e) {
//        }

        viewpager.setAdapter(pagerViewAdapter);

//        viewpager.setCurrentItem(0, false);


    }

    @Override
    protected void initListener() {
        rb_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(0, false);
            }
        });

        rb_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(1, false);
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }
}