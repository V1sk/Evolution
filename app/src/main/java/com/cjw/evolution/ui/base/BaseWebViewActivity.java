package com.cjw.evolution.ui.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.cjw.evolution.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CJW on 2016/10/1.
 */

public abstract class BaseWebViewActivity extends BaseActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_URL = "url";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.web_view)
    WebView webView;

    private String mTitle;
    private String mUrl;

    protected abstract void overrideUrlLoading(WebView view, String url);
    protected abstract void pageStarted(WebView view, String url, Bitmap favicon);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        ActionBar actionBar = supportActionBar(toolbar);
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
        setActionBarTitle();
        setUpWebView(webView);
        webView.loadUrl(mUrl);
    }

    private void setUpWebView(WebView webView) {
        // Settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebClient());
        webView.setWebChromeClient(new ChromeClient());
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            overrideUrlLoading(view,url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
            progressBar.setProgress(newProgress);

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title)) {
                mTitle = title;
                setActionBarTitle();
            }
        }
    }

    private void setActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mTitle);
        }
    }
}
