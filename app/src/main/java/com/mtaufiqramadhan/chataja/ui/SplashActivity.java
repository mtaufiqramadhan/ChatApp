package com.mtaufiqramadhan.chataja.ui;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.mtaufiqramadhan.chataja.R;
import com.mtaufiqramadhan.chataja.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                //TODO
            }

            @Override
            public void onFinish() {
                updateUI(currentUser);
            }
        }.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void setupFindViews() {
        //TODO
    }

    @Override
    public void setupListData() {
        //TODO
    }

    @Override
    public void setupListener() {
        //TODO
    }

}
