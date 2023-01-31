package com.predictor.library.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.predictor.library.base.CNBaseFragment;
import com.predictor.library.listener.OnDoIntListener;
import com.predictor.library.utils.CNToast;

import java.util.List;

public class PagerViewAdapter extends FragmentPagerAdapter {
    public FragmentManager fragmentManager;
    public List<CNBaseFragment> list;

    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerViewAdapter(FragmentManager fm, List<CNBaseFragment> list) {
        super(fm);
        this.fragmentManager = fm;
        this.list = list;
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void setPageChangeListener(ViewPager viewPager, OnDoIntListener listener){
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                listener.doSomething(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        Fragment fragment = list.get(position);
        fragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }
}
