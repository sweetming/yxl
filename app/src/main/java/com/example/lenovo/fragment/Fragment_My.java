package com.example.lenovo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.lenovo.Listview.LodaMoreListView;
import com.example.lenovo.adapter.Hot_ListAdapater;
import com.example.lenovo.adapter.My_List_Adapter;
import com.example.lenovo.entity.DZentity;
import com.example.lenovo.entity.MYcs_Entity;
import com.example.lenovo.myapp.MypageActivity;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.WebVIew_Test_Activity;
import com.example.lenovo.myapp.WebviewActivity;

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
public class Fragment_My extends Fragment {
    private View view;
    private LodaMoreListView lv;
    private SwipeRefreshLayout swiper;
    private Document document;
    private Myhandler myhandler=null;
    private Mythread mythread=null;
    private List<MYcs_Entity> list;
    private My_List_Adapter adapter;

    private final int PAGE_TOP=1;
    private int pages=1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_myceshi, null);

            initview();

        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }

        }
        return view;

    }

    private void initview() {
        list=new ArrayList<>();
        swiper= ((SwipeRefreshLayout) view.findViewById(R.id.mycs_swiper));
        swiper.setColorSchemeResources(R.color.blue);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                lv.mFootView.setVisibility(View.GONE);
                lv.setAdapter(null);
                list.clear();
                pages=PAGE_TOP;
                mythread=new Mythread();
                mythread.start();
            }

        });

        lv=(LodaMoreListView)view.findViewById(R.id.mycs_lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(getActivity(), WebVIew_Test_Activity.class);
                String ass="http:"+list.get(position).https.trim().replace(" ","");
                Log.e("onItemClick: ",ass);
                intent.putExtra("key",ass);
                getActivity().startActivity(intent);
            }
        });
        lv.setOnLoadMoreListener(new LodaMoreListView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                lv.mFootView.setVisibility(View.VISIBLE);
                lv.setLoadState(true);
                pages++;
                mythread=new Mythread();
                mythread.start();

            }
        });
        myhandler=new Myhandler();
        if(mythread==null){
            mythread=new Mythread();
            mythread.start();
        }
            swiper.setRefreshing(true);
    }
    class Mythread extends Thread{
        @Override
        public void run() {
            try {

                document = Jsoup.connect("http://www.xinli001.com/ceshi/personality"+"?page="+pages).get();

                Elements el= document.select(".list_show dl dd");

                for (Element e:el){
                    MYcs_Entity my=new MYcs_Entity();
                    my.https=e.select(".pbox a").attr("href");
                    my.srcs=e.select("img").attr("src");
                    my.titles=e.select(".linfo a").text();

                    my.tests=e.select("span").text();
                    list.add(my);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message message=new Message();
            message.what=pages;
            myhandler.sendMessage(message);
        }
    }

    class Myhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    swiper.setRefreshing(false);
                    adapter=new My_List_Adapter(getContext(),list);
                    lv.setAdapter(adapter);

                    break;
                default:
                    adapter.setList(list);
                   adapter.notifyDataSetChanged();

                    break;
            }
            super.handleMessage(msg);
        }
    }
}