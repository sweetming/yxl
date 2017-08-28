package com.example.lenovo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anye.greendao.gen.UserDAODao;
import com.bumptech.glide.Glide;
import com.example.lenovo.entity.DZentity;
import com.example.lenovo.entity.UserDAO;
import com.example.lenovo.myapp.MyAppliction;
import com.example.lenovo.myapp.R;

import java.util.List;

/**
 * Created by lenovo on 2017/5/27.
 */
public class Fav_ListAdapter extends BaseAdapter {

    private UserDAODao mUserDao= MyAppliction.getinstances().getDaoSession().getUserDAODao();
    private List<DZentity> list;
    private Context context;
    private LayoutInflater inflater;

    public Fav_ListAdapter(List<DZentity> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }
    public void setList(List<DZentity> list) {
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
        final ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.fav_item,null);
            holder.iv=(ImageView) convertView.findViewById(R.id.fav_item_iv);
            holder.title=(TextView) convertView.findViewById(R.id.fav_item_title);
            holder.zz=(TextView) convertView.findViewById(R.id.fav_item_creater);
            holder.yd= ((TextView) convertView.findViewById(R.id.fav_item_looker));
            holder.sc=(TextView)convertView.findViewById(R.id.fav_item_sc);
            holder.sc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserDAO findUser = mUserDao.queryBuilder().where(UserDAODao.Properties.Daotitles.eq(holder.title.getText())).build().unique();
                    if(findUser==null){
                        UserDAO user=new UserDAO();
                        user.setDaotitles(holder.title.getText()+"");
                        user.setDaocreater(holder.zz.getText()+"");
                        user.setDaolooker(holder.yd.getText()+"");
                        user.setDaohttps(holder.https);
                        Log.e( "onClick: ",holder.https+holder.img);
                        user.setDaoimgs(holder.img);
                        mUserDao.insert(user);
                        holder.sc.setBackgroundResource(R.drawable.ic_star);

                    }else {
                        holder.sc.setBackgroundResource(R.drawable.ic_unstar_navbar);
                        mUserDao.deleteByKey(findUser.getId());


                    }
                }
            });convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        DZentity dZentity=(DZentity) getItem(position);


        holder.title.setText(dZentity.title);
        holder.zz.setText(dZentity.creater);
        holder.yd.setText(dZentity.looker);
        holder.https=dZentity.https;
        holder.img=dZentity.imgs;
        Glide.with(context).load(dZentity.imgs).into(holder.iv);


        return convertView;
    }




    class ViewHolder{
        public ImageView iv;
        public TextView title,zz,yd,sc;
        public String https;
        public String img;
    }
}
