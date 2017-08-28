package com.example.lenovo.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.adapter.Hot_ListAdapater;
import com.example.lenovo.entity.DZentity;
import com.example.lenovo.entity.GlideImageLoader;
import com.example.lenovo.entity.RMentity;
import com.example.lenovo.entity.RWentity;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.WebviewActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/2.
 */
public class Fragment_Trending extends Fragment implements View.OnClickListener {
    private View view;
    private Banner banner;
    private List<String> images;
    private List<String> titles;
    private List<String> https;
    private List<String> rWtitle,rWhttps;
    private Document docment;
    private Mythread mythread=null;
    private RMentity rm;
    private Myhandler myhandler=null;
    private TextView tv1,tv2,tv3,tv4,tv5;
    private RWentity rw;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_trending, null);

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

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.rw_tv1:
                startAtoW(rWhttps.get(0));
                break;
            case R.id.rw_tv2:
                startAtoW(rWhttps.get(1));
                break;
            case R.id.rw_tv3:
                startAtoW(rWhttps.get(2));
                break;
            case R.id.rw_tv4:
                startAtoW(rWhttps.get(3));
                break;
            case R.id.rw_tv5:
                startAtoW(rWhttps.get(4));
                break;
        }

    }

    class Mythread extends Thread{
        @Override
        public void run() {
            try {

                docment = Jsoup.connect("http://www.xinli001.com/").get();

                Elements el= docment.select(".hslide-wrap .hslide .slides .slide");
                Elements ell=docment.select(".hzxtj .body ul li");
                for (Element e:ell){
                    rw=new RWentity();
                    rw.bt=e.select("a").text();
                    rw.wy=e.select("a").attr("href");
                    rWtitle.add(rw.bt);
                    rWhttps.add(rw.wy);
                }


                for (Element e:el){

                    rm=new RMentity();
                    rm.https=e.select("a").attr("href");
                    rm.imgs=e.select("img").attr("src");
                    rm.title=e.select(".tip").text();Log.e( "run1: ",rm.imgs+"----------"+rm.title );
                    images.add(rm.imgs);
                    titles.add(rm.title);
                    https.add(rm.https);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Message message=new Message();
            message.what=1;
            myhandler.sendMessage(message);
        }
    }
    class Myhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    banner.setImages(images);
                    banner.setBannerTitles(titles);
                    banner.start();
                    tv1.setText(rWtitle.get(0));
                    tv2.setText(rWtitle.get(1));
                    tv3.setText(rWtitle.get(2));
                    tv4.setText(rWtitle.get(3));
                    tv5.setText(rWtitle.get(4));
                    break;
                default:

                    break;
            }
            super.handleMessage(msg);
        }
    }
    private void initview() {
        tv1= ((TextView) view.findViewById(R.id.rw_tv1));

        tv2= ((TextView) view.findViewById(R.id.rw_tv2));
        tv3= ((TextView) view.findViewById(R.id.rw_tv3));
        tv4= ((TextView) view.findViewById(R.id.rw_tv4));
        tv5= ((TextView) view.findViewById(R.id.rw_tv5));
        tv1.setOnClickListener(this);tv2.setOnClickListener(this);tv3.setOnClickListener(this);tv4.setOnClickListener(this);tv5.setOnClickListener(this);
        rWtitle=new ArrayList<>();
        rWhttps=new ArrayList<>();
        https=new ArrayList<>();
        images=new ArrayList<>();
        titles=new ArrayList<>();
        banner=(Banner)view.findViewById(R.id.trending_banner);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);

        banner.setImageLoader(new GlideImageLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
               String s= https.get(position);
                Intent intent=new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("key",s);
                getActivity().startActivity(intent);
            }
        });
        myhandler=new Myhandler();
        if(mythread==null){
            mythread=new Mythread();
            mythread.start();
        }
    }

public void startAtoW(String keys){
    Intent intent=new Intent(getActivity(), WebviewActivity.class);
    intent.putExtra("key",keys);
    getActivity().startActivity(intent);
}
}
