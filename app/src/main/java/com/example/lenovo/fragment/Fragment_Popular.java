package com.example.lenovo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.lenovo.adapter.Popular_viewpager_adapter;
import com.example.lenovo.myapp.R;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/2.
 */
public class Fragment_Popular extends Fragment {

    private TabLayout mytable;
    private ViewPager myvp;
    private String [] mytitlelist={"婚恋","健康","职场","科普","成长"};
    private Fragment[] myviewlist={new FragmentDZ("876"),new FragmentDZ("823"),new FragmentDZ("844"),new FragmentDZ("792"),new FragmentDZ("862")};

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_popular, null);
            mytable = (TabLayout) view.findViewById(R.id.popular_tab);
            myvp=(ViewPager) view.findViewById(R.id.popular_vp);
            initview();

        }
        else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }

        }
        return  view;
    }

    private void initview() {

       //     myviewlist.add(new FragmentDZ("876"));
       //     myviewlist.add(new FragmentDZ("823"));
        //    myviewlist.add(new FragmentDZ("844"));
       //     myviewlist.add(new FragmentDZ("792"));
        //    myviewlist.add(new FragmentDZ("862"));


        mytable.setTabGravity(TabLayout.GRAVITY_FILL);
        mytable.setTabMode(TabLayout.MODE_SCROLLABLE);

        Popular_viewpager_adapter adapter = new Popular_viewpager_adapter(getFragmentManager(),myviewlist, mytitlelist);
        myvp.setAdapter(adapter);
        myvp.setOffscreenPageLimit(5);
        mytable.setTabsFromPagerAdapter(adapter);

        mytable.setupWithViewPager(myvp);
    }


}
