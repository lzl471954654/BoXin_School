package com.boxintech.boxin_school.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 刘智林 on 2017/4/18.
 */

public class LessonFragmentPagerAdapter extends FragmentPagerAdapter {
    Context context;
    List<Fragment> fragmentList;

    public LessonFragmentPagerAdapter(FragmentManager fragmentManager,List<Fragment> fragments,Context context1)
    {
        super(fragmentManager);
        fragmentList = fragments;
        context = context1;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] title = {"星期一","星期二","星期三","星期四","星期五"};
        return title[position];
    }
}
