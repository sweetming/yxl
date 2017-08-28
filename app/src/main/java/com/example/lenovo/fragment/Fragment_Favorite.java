package com.example.lenovo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.anye.greendao.gen.UserDAODao;
import com.example.lenovo.adapter.Fav_ListAdapter;
import com.example.lenovo.adapter.Hot_ListAdapater;
import com.example.lenovo.adapter.Popular_viewpager_adapter;
import com.example.lenovo.entity.DZentity;
import com.example.lenovo.entity.UserDAO;
import com.example.lenovo.myapp.MyAppliction;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.WebviewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/2.
 */
public class Fragment_Favorite extends Fragment {

    private TabLayout mtablayout;
    private ViewPager myvp;
    private View view1,view2;
    private List<String> listtitle=new ArrayList<>();
    private List<Fragment> listview=new ArrayList<>();
    private View rootView;
    private ListView lv;
    private Fav_ListAdapter adapater;
    private UserDAODao mUserDao= MyAppliction.getinstances().getDaoSession().getUserDAODao();
    private List<UserDAO> listdao;
    private List<DZentity> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_favorite, null);
            initview();



        return rootView;
    }

    private void initview() {
        lv= ((ListView) rootView.findViewById(R.id.fav_lv));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(getActivity(), WebviewActivity.class);
                String ass=list.get(position).https.trim().replace(" ","");
                Log.e("onItemClick: ",ass);
                intent.putExtra("key",ass);
                getActivity().startActivity(intent);
            }
        });
        listdao=new ArrayList<>();
        listdao=mUserDao.loadAll();
        list=new ArrayList<>();
        for(int i=0;i<listdao.size();i++){
            DZentity dz=new DZentity();
            dz.https=listdao.get(i).getDaohttps();
            dz.looker=listdao.get(i).getDaolooker();
            dz.creater=listdao.get(i).getDaocreater();
            dz.imgs=listdao.get(i).getDaoimgs();
            dz.title=listdao.get(i).getDaotitles();
            list.add(dz);
        }
        adapater=new Fav_ListAdapter(list,getContext());
        lv.setAdapter(adapater);


    }
}

