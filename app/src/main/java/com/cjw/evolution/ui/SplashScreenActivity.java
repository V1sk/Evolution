package com.cjw.evolution.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.cjw.evolution.R;
import com.cjw.evolution.account.UserSession;
import com.cjw.evolution.network.ServerConfig;
import com.cjw.evolution.ui.main.MainActivity;
import com.cjw.evolution.ui.signin.SignInActivity;

public class SplashScreenActivity extends AppCompatActivity {

    final long SIGNED_IN_DELAY = 2500;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserSession.getInstance().isSignedIn()) {
                    openMainActivity();
                } else {
                    openSignInActivity();
                }
            }
        }, SIGNED_IN_DELAY);
    }

    private void openSignInActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
        intent.putExtra(SignInActivity.EXTRA_TITLE, getString(R.string.sign_in));
        intent.putExtra(SignInActivity.EXTRA_URL, ServerConfig.LOGIN_URL);
        startActivity(intent);
        finish();
    }

    private void openMainActivity() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }
}
