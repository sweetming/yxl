package com.example.lenovo.myapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.dialog.SelectDialog;
import com.example.lenovo.fragment.Fragment_Favorite;
import com.example.lenovo.fragment.Fragment_My;
import com.example.lenovo.fragment.Fragment_Popular;
import com.example.lenovo.fragment.Fragment_Trending;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout icon_popular,icon_trending,icon_favorite,icon_my;
    private TextView title_text;
    private TextView po_text,tr_text,fa_text,my_text;
    private int blue,hui;
    private FragmentManager fm;
    private ImageView iv,ivs;
    private Fragment_Popular fp;
    private Fragment_Favorite ff;
    private Fragment_Trending fragt;
    private Fragment_My fmy;
    private ImageView im1,im2,im3,im4;
    private Boolean show=true;

    private SelectDialog seldialog=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void initview() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager.setStatusBarTintEnabled(true);


        im1=(ImageView) findViewById(R.id.img_popubler);

        im2=(ImageView) findViewById(R.id.img_terending);

        im3=(ImageView) findViewById(R.id.img_favorite);
        im4=(ImageView) findViewById(R.id.img_my);
        iv= (ImageView) findViewById(R.id.main_igsel);
        iv.setOnClickListener(this);
        ivs= (ImageView) findViewById(R.id.main_search);
        ivs.setOnClickListener(this);
        blue = getResources().getColor(R.color.blue);
        hui = getResources().getColor(R.color.hui);
        po_text = (TextView) findViewById(R.id.icon_popular_text);
        tr_text = (TextView) findViewById(R.id.icon_trending_text);
        fa_text = (TextView) findViewById(R.id.icon_favorite_text);
        my_text = (TextView) findViewById(R.id.icon_my_text);
        icon_popular =(LinearLayout) findViewById(R.id.icon_popular);
        icon_popular.setOnClickListener(this);
        icon_trending = (LinearLayout) findViewById(R.id.icon_trending);
        icon_trending.setOnClickListener(this);
        icon_favorite = (LinearLayout)findViewById(R.id.icon_favorite);
        icon_favorite.setOnClickListener(this);
        icon_my = (LinearLayout) findViewById(R.id.icon_my);
        icon_my.setOnClickListener(this);
        title_text = (TextView) findViewById(R.id.title_text);

        seldialog = new SelectDialog(this,R.style.seldialog);
        Window win =seldialog.getWindow();
        WindowManager.LayoutParams params = seldialog.getWindow().getAttributes();
        params.gravity= Gravity.TOP;
        params.windowAnimations=1;
        params.x=title_text.getScrollX()+160;
        params.y=120;
        cleancolor();
        title_text.setText("热门");
        po_text.setTextColor(blue);
        im1.setImageResource(R.drawable.im2);
        fm = getSupportFragmentManager();
        fragt=new Fragment_Trending();
        fmy= new Fragment_My();
        ff = new Fragment_Favorite();
        fp = new Fragment_Popular();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_fragment,fragt);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.icon_popular:
            cleancolor();
            title_text.setText("热门");
            po_text.setTextColor(blue);
            im1.setImageResource(R.drawable.im2);

          FragmentTransaction  ft1 = fm.beginTransaction();
            ft1.replace(R.id.main_fragment,fragt);
            ft1.commit();
           break;
        case R.id.icon_favorite:
            cleancolor();
            title_text.setText("收藏");
            fa_text.setTextColor(blue);

            im3.setImageResource(R.drawable.im3);
            FragmentTransaction  ft2 = fm.beginTransaction();
            ft2.replace(R.id.main_fragment,ff);
            ft2.commit();
            break;
        case R.id.icon_trending:
            cleancolor();
            title_text.setText("文章");
            tr_text.setTextColor(blue);

            im2.setImageResource(R.drawable.im1);
             FragmentTransaction ft3 = fm.beginTransaction();
            ft3.replace(R.id.main_fragment,fp);
            ft3.commit();
            break;
        case R.id.icon_my:
            cleancolor();
            title_text.setText("我的");
            my_text.setTextColor(blue);
             im4.setImageResource(R.drawable.im4);
             FragmentTransaction  ft4 = fm.beginTransaction();
            ft4.replace(R.id.main_fragment,fmy);
            ft4.commit();
            break;
        case R.id.main_igsel:


           break;
        default:
            break;


    }
    }


    private void cleancolor(){
        po_text.setTextColor(hui);
        tr_text.setTextColor(hui);
        fa_text.setTextColor(hui);
        my_text.setTextColor(hui);
        im1.setImageResource(R.drawable.ic_trending);
        im2.setImageResource(R.drawable.ic_polular);
        im3.setImageResource(R.drawable.ic_favorite);
        im4.setImageResource(R.drawable.ic_my);
    }
}
