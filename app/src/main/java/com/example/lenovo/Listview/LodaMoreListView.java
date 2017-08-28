package com.example.lenovo.Listview;

/**
 * Created by lenovo on 2017/5/25.
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.lenovo.myapp.R;

/**
 * Created by lenovo on 2017/5/15.
 */
public class LodaMoreListView extends ListView implements AbsListView.OnScrollListener{

    public View mFootView;
    private OnLoadMoreListener mListener;
    private LayoutInflater inflater;
    private boolean mIsLoading=false;
    private int mFooterHeight;



    public LodaMoreListView(Context context) {
        super(context,null);

    }

    private void init() {
        mFootView = inflater.inflate(R.layout.foot_view, null);
        this.addFooterView(mFootView);

    }

    public LodaMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        Log.e( "LodaMoreListView: ", "start2");
        inflater=  LayoutInflater.from(context);
        init();
        setOnScrollListener(this);

    }

    public LodaMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if(this.getLastVisiblePosition() == this.getAdapter().getCount() - 1
                && !mIsLoading && (i == SCROLL_STATE_FLING || i == SCROLL_STATE_IDLE)){
            setLoadState(true);
            if(this.mListener != null){
                this.mListener.loadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    public void setLoadState(boolean b) {

        this.mIsLoading = b;
        if(mIsLoading=false){
            removeFooterView(mFootView);
        }
        //if(mIsLoading){
        //  mFootView.setPadding(0,0,0,0);
        //this.setSelection(this.getAdapter().getCount() + 1);
        // }else {
        //   mFootView.setPadding(0,-mFooterHeight,0,0);
        //}
    }


    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.mListener=listener;
    }
    public interface OnLoadMoreListener{
        void loadMore();
    }
}