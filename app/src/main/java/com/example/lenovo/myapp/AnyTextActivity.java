package com.example.lenovo.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;


/**
 * Created by lenovo on 2016/11/4.
 */
public class AnyTextActivity extends Activity {

    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ptf_listview);
        rv= (RecyclerView) findViewById(R.id.text_get_any);

    }
}
