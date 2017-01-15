package com.notzuonotdied.webviewtest;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Notzuonotdied on 2017/1/14.
 */

public class HyBridWebView extends WebView {
    private Context context;

    public HyBridWebView(Context context) {
        super(context);
        this.context = context;
    }

    public HyBridWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HyBridWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HyBridWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void goBack() {
//        if (context instanceof Activity) {
//            ((Activity)context).overridePendingTransition(R.anim.hybrid_left_out, R.anim.hybrid_right_in);
//        }
        super.goBack();
    }
}
