package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class SplashActivity extends BaseActivity {

    private CountDownTimer downTimer;

    @Override
    protected void BaseOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);


        downTimer = new CountDownTimer(2_000,1_000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                Intent intent = new Intent(SplashActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        downTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        downTimer.cancel();
    }
}

