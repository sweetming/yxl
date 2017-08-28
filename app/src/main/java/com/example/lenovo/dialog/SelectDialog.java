package com.example.lenovo.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.lenovo.myapp.R;

/**
 * Created by lenovo on 2016/11/8.
 */
public class SelectDialog extends AlertDialog {

    public SelectDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
     public SelectDialog(Context context){
            super(context);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog);
    }
}
