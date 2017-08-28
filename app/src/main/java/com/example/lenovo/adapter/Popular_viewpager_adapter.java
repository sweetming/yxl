package com.example.lenovo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by lenovo on 2016/11/4.
 */
public class Popular_viewpager_adapter extends FragmentPagerAdapter{

    private Fragment[] listview;
    private String[] listtitle;

    public Popular_viewpager_adapter(FragmentManager fm,Fragment[] ListView,String[] listtitle) {
        super(fm);
        this.listtitle=listtitle;
        this.listview=ListView;
    }


    @Override
    public Fragment getItem(int position) {
        return listview[position];
    }

    @Override
    public int getCount() {
        return listview.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listtitle[position % listtitle.length];
    }
}
