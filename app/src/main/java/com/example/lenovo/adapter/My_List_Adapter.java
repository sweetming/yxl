package com.example.lenovo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.entity.MYcs_Entity;
import com.example.lenovo.myapp.R;

import java.util.List;

/**
 * Created by lenovo on 2017/5/28.
 */
public class My_List_Adapter extends BaseAdapter {

    private Context context;
    private List<MYcs_Entity> list;
    private LayoutInflater inflater;


    public My_List_Adapter(Context context, List<MYcs_Entity> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    public void setList(List<MYcs_Entity> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.myceshi_item,null);
            holder.iv=(ImageView) convertView.findViewById(R.id.my_item_iv);
            holder.title=(TextView)convertView.findViewById(R.id.my_item_title);

            holder.tests=(TextView)convertView.findViewById(R.id.my_item_test);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        MYcs_Entity entity=(MYcs_Entity) getItem(position);
        holder.title.setText(entity.titles);

        holder.tests.setText(entity.tests);
        Glide.with(context).load(entity.srcs).into(holder.iv);

        return convertView;
    }

    class ViewHolder{
        private ImageView iv;
        private TextView title,tests;
    }
}
