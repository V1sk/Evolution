package com.cjw.evolution.ui.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.Toast;

import com.cjw.evolution.R;
import com.cjw.evolution.network.ServerConfig;
import com.cjw.evolution.ui.base.BaseWebViewActivity;
import com.cjw.evolution.ui.main.MainActivity;

public class SignInActivity extends BaseWebViewActivity implements SignInContract.View {

    private SignInContract.Presenter mPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        new SignInPresenter(this).subscribe();
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    protected void overrideUrlLoading(WebView view, String url) {
        if (!url.startsWith(ServerConfig.LOGIN_CALLBACK)) {
            view.loadUrl(url);
        }
    }

    @Override
    protected void pageStarted(WebView view, String url, Bitmap favicon) {
        if (url.startsWith(ServerConfig.LOGIN_CALLBACK)) {
            view.stopLoading();
            //https://v1sk.github.io/?code=df8fe8fe052f987b70c10f65619d74f6695d48d0c4de411649a63e2d07a6be5b
            String code = url.substring(url.indexOf("=") + 1);
            mPresenter.signIn(code);
        }
    }

    @Override
    public void onSignInStarted() {
        progressDialog.show();
    }

    @Override
    public void onSignInCompleted() {
        progressDialog.dismiss();
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onSignInError(Throwable e) {
        progressDialog.dismiss();
        Toast.makeText(this, R.string.sign_in_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
