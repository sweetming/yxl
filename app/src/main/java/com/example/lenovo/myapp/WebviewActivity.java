package com.example.lenovo.myapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by lenovo on 2017/5/26.
 */
public class WebviewActivity extends Activity  {
    private String https;
    private SwipeRefreshLayout swipe;
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_text);
        Intent intent = getIntent();
        if (intent != null) {
            https = intent.getStringExtra("key");
        }
        Log.e("onCreate: ", https);

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
       // tintManager.setTintColor(R.color.blue);
        swipe=(SwipeRefreshLayout) findViewById(R.id.web_swipe);
        swipe.setColorSchemeResources(R.color.blue);
        swipe.setRefreshing(true);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                mWebView.loadUrl(mWebView.getUrl());
                Log.e( "onRefresh: ",mWebView.getUrl() );

            }
        });
        mWebView = (WebView) findViewById(R.id.web_webview);


        mWebView.requestFocus();//触摸焦点起作用mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);//取消滚动条
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置允许js弹出alert对话框

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(false);// 将图片调整到适合webview大小
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//支持缓存

        //load在线
        mWebView.loadUrl(https);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.contains("10")){
                view.loadUrl(url);
                return true;}else{
                finish();
                return false;
            }


            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    swipe.setRefreshing(false);
                } else {
                    swipe.setRefreshing(true);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // return super.onTouchEvent(event);
        return false;
    }
}
