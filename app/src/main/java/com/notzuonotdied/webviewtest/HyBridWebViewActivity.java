package com.notzuonotdied.webviewtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;

import static android.R.attr.host;

public class HyBridWebViewActivity extends AppCompatActivity {

    private HyBridWebView hybridwebview;
    private ProgressBar hybridprogressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_brid_web_view);
        this.hybridprogressbar = (ProgressBar) findViewById(R.id.hybrid_progressbar);
        this.hybridwebview = (HyBridWebView) findViewById(R.id.hybrid_webview);
        initConfig(hybridwebview);
    }

    /**
     * 从intent传过来的参数获取到URL
     */
    protected String getUrl() {
        Uri data = getIntent().getData();
        String url;
        if (null == data) {
            url = getIntent().getStringExtra("URL");
            Log.i("Notzuonotdied","第二场");
        } else {
            url = data.toString();
            Log.i("Notzuonotdied", "进入->" + url);
        }
        return url;
    }

    /**
     * 需要设置webview的属性则重写此方法
     *
     * @param webView
     */
    protected void initConfig(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new HybridJsInterface(), "android");
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            @Override// 拦截URL，方法内为拦截URL期间的自定义执行过程
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //
                if (!TextUtils.isEmpty(url)) {
                    Log.i("Notzuonotdied", "shouldOverrideUrlLoading");
                    // 在这里可以通过解析url，如url中携带参数的话，可以通过截取来进行处理，响应自定义的Action。
                    Intent intent = new Intent(HyBridWebViewActivity.this, HyBridWebViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("URL", url);
                    startActivity(intent);
                    overridePendingTransition(R.anim.hybrid_right_in, R.anim.hybrid_left_out);
                    return true;
                }
                return false;
            }

            @Override// 当WebView中页面刚开始加载的时候
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                hybridprogressbar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override// 当WebView中页面刚结束加载的时候
            public void onPageFinished(WebView view, String url) {
                hybridprogressbar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        String url = getUrl();
        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        // 判断webView中是否还有返回页面，如果有就返回webview历史缓存的页面，没有就直接退出当前Activity
        if (this.hybridwebview.canGoBack()) {
            // 返回webview历史缓存的页面
            this.hybridwebview.goBack();
        } else {
            // 退出当前Activity
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        // Activity退出时动画
        overridePendingTransition(R.anim.hybrid_bottom_out, R.anim.hybrid_bottom_in);
        super.finish();
    }

    /**
     * JavaObject注入WebView
     * */
    public class HybridJsInterface {
        @JavascriptInterface
        public void goToNewPage(String message) {
            Toast.makeText(HyBridWebViewActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
