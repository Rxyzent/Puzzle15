package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.Animator;
import android.app.UiModeManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.airbnb.lottie.LottieAnimationView;

public class SettingsActivity extends BaseActivity {

    private RadioButton auto,day,night;
    private LottieAnimationView day_night;
    private CountDownTimer downTimer;

    @Override
    protected void BaseOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);


        loadView();
        downTimer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                day_night.pauseAnimation();
            }
        };

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED);
                GameCashe.getGameCashe().setLastMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED);
                day.setChecked(false);
                night.setChecked(false);
            }
        });
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                GameCashe.getGameCashe().setLastMode(AppCompatDelegate.MODE_NIGHT_NO);
                auto.setChecked(false);
                night.setChecked(false);
                day_night.resumeAnimation();
            }
        });
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                GameCashe.getGameCashe().setLastMode(AppCompatDelegate.MODE_NIGHT_YES);
                day_night.playAnimation();
                downTimer.start();
                auto.setChecked(false);
                day.setChecked(false);
            }
        });
        if(modeOrder == AppCompatDelegate.MODE_NIGHT_UNSPECIFIED){
            auto.setChecked(true);

        }else if(modeOrder == UiModeManager.MODE_NIGHT_NO){
            day.setChecked(true);

        }else if(modeOrder == UiModeManager.MODE_NIGHT_YES){
            night.setChecked(true);

        }
    }

    private void loadView() {
        auto = findViewById(R.id.auto_btn);
        day = findViewById(R.id.day_btn);
        night = findViewById(R.id.night_btn);
        day_night = findViewById(R.id.day_night);
    }
}