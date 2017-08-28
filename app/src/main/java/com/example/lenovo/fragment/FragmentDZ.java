package com.example.lenovo.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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

import com.example.lenovo.Listview.LodaMoreListView;
import com.example.lenovo.adapter.Hot_ListAdapater;
import com.example.lenovo.entity.DZentity;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.WebviewActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/5/10.
 */
public class FragmentDZ extends Fragment {


    private LodaMoreListView lv;
    private String getString;
    private Document document;
    private  DZentity dz;
    private Mythread mythread=null;
    private View rootView;
    private Myhandler myhandler=null;
    private List<DZentity> listbean;
    private Hot_ListAdapater adapater;
    private SwipeRefreshLayout swipe;
    private final int PAGE_TOP=1;
    private int pages=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.hotfragments, null);
        listbean=new ArrayList<>();
            lv = (LodaMoreListView) rootView.findViewById(R.id.dz_lv);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent =new Intent(getActivity(), WebviewActivity.class);
                    String ass="http:"+listbean.get(position).https.trim().replace(" ","");
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

            swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
            swipe.setColorSchemeResources(R.color.blue);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                lv.mFootView.setVisibility(View.GONE);
                lv.setAdapter(null);
                listbean.clear();
                pages=PAGE_TOP;
                mythread=new Mythread();
                mythread.start();
            }

        });

        swipe.setRefreshing(true);

        if(mythread==null){
            mythread=new Mythread();
            mythread.start();

        }



        return rootView;

    }


    private void initView() {

    }
    class Mythread extends Thread{
        @Override
        public void run() {
            try {

                 document = Jsoup.connect("http://www.xinli001.com/info/tag_"+getString+"?page="+pages).get();
                Log.e("run: ","stttttttttttttttt"+getString);
                Elements el= document.select(".arlist #hot-list ul li");

                for (Element e:el){

                    dz= new DZentity();
                    dz.https=e.select(".img a").attr("href");
                    dz.imgs=e.select(".img img").attr("src");
                    dz.title=e.select(".text h2").select("a").text();
                    dz.creater=e.select(".attr h4").select("a").text();
                    dz.looker=e.select(".hit").select(".ico-view").text();
                    if(dz.https!=""){
                    listbean.add(dz);}

            }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message message=new Message();
            message.what=pages;
            myhandler.sendMessage(message);
        }
    }

    class Myhandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    swipe.setRefreshing(false);
                    adapater = new Hot_ListAdapater(listbean,getContext());
                    lv.setAdapter(adapater);

                    break;
                default:
                    adapater.setList(listbean);
                    adapater.notifyDataSetChanged();

                    break;
            }
            super.handleMessage(msg);
        }
    }
    @SuppressLint("ValidFragment")
    public FragmentDZ(String getString) {
        this.getString=getString;
    }

    public FragmentDZ(){
        super();
    }
}
