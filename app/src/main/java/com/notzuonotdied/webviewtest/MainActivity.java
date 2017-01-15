package com.notzuonotdied.webviewtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TestUri = "http://www.baidu.com";

    private WebView webView;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView() {
        this.btn7 = (Button) findViewById(R.id.btn7);
        this.btn6 = (Button) findViewById(R.id.btn6);
        this.btn5 = (Button) findViewById(R.id.btn5);
        this.btn4 = (Button) findViewById(R.id.btn4);
        this.btn3 = (Button) findViewById(R.id.btn3);
        this.btn2 = (Button) findViewById(R.id.btn2);
        this.btn1 = (Button) findViewById(R.id.btn1);

        webView = new WebView(this);
        // 适应手机屏幕
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        // 放大设置
        webView.getSettings().setDisplayZoomControls(false);
    }

    private void initListener() {
        this.btn1.setOnClickListener(this);
        this.btn2.setOnClickListener(this);
        this.btn3.setOnClickListener(this);
        this.btn4.setOnClickListener(this);
        this.btn5.setOnClickListener(this);
        this.btn6.setOnClickListener(this);
        this.btn7.setOnClickListener(this);
    }

    /**
     * 直接调用Android手机内的默认浏览器来打开网页
     */
    private void Usage1() {
        Uri uri = Uri.parse(TestUri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    /**
     * 我们换种方式，使用webView，还是打开了系统默认的浏览器~
     */
    private void Usage2() {
        // 加载网页
        webView.loadUrl(TestUri);
        this.setContentView(webView);
    }

    /**
     * 使用webView的setWebViewClient()
     */
    private void Usage3() {
        // 加载网页
        webView.loadUrl(TestUri);
        webView.setWebViewClient(new WebViewClient());
        this.setContentView(webView);
    }

    /**
     * JS交互
     */
    private void Usage4() {
        WebView webView = new WebView(this);
        // 支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        // 加载网页
        webView.loadUrl("file:///android_asset/index.html");
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new JSBridge(), "android");
        this.setContentView(webView);
    }

    /**
     * 示例项目
     */
    private void Usage5() {
        Intent intent = new Intent(MainActivity.this,HyBridWebViewActivity.class);
        intent.setData(Uri.parse("http://blog.csdn.net/notzuonotdied"));
        startActivity(intent);
    }

    /**
     * 加载本地的HTML
     */
    private void Usage6() {
        WebView webView = new WebView(this);
        // 支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        // 加载网页
        webView.loadUrl("file:///android_asset/index.html");
        webView.addJavascriptInterface(new JSBridge(), "android");
        this.setContentView(webView);
    }

    /**
     * Native调用JS
     */
    private void Usage7() {
        final WebView webView = new WebView(this);
        // 支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        // 加载网页
        webView.loadUrl("file:///android_asset/index.html");
        webView.addJavascriptInterface(new JSBridge(), "android");
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        l.setLayoutParams(layoutParams);
        Button button = new Button(this);
        button.setText("点击调用JS");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:toast()");
            }
        });
        l.addView(button);
        l.addView(webView);

        this.setContentView(l);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Usage1();
                break;
            case R.id.btn2:
                Usage2();
                break;
            case R.id.btn3:
                Usage3();
                break;
            case R.id.btn4:
                Usage4();
                break;
            case R.id.btn5:
                Usage5();
                break;
            case R.id.btn6:
                Usage6 ();
                break;
            case R.id.btn7:
                Usage7 ();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // 判断webView中是否还有返回页面，如果有就返回webview历史缓存的页面，没有就直接退出当前Activity
        if (webView.canGoBack()) {
            // 返回webview历史缓存的页面
            webView.goBack();
        } else {
            this.setContentView(R.layout.activity_main);
            initView();
            initListener();
        }
    }

    public class JSBridge {
        @JavascriptInterface
        public void toastMessage(String message) {
            Toast.makeText(getApplicationContext(), "通过Natvie传递的Toast:" + message, Toast.LENGTH_LONG).show();
        }
    }
}
