package com.cjw.evolution.ui.base;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
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

    private static final String TAG = "BaseWebViewActivity";

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

    protected abstract void pageStarted(WebView view, String url);

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

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpWebView(WebView webView) {
        // Settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 启用地理定位
        webSettings.setGeolocationEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);

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

    protected class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            overrideUrlLoading(view, url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            overrideUrlLoading(view, request.getUrl().toString());
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pageStarted(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    }

    protected class ChromeClient extends WebChromeClient {
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

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.i(TAG, "onConsoleMessage: " + consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            showDialog(view, message, result);
            return true;
        }

        private void showDialog(WebView view, String message, JsResult result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage(message);
            builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    result.confirm();
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    result.cancel();
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            showDialog(view, message, result);
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            showDialog(view, message, result);
            return true;
        }

    }

    private void setActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mTitle);
        }
    }
}
