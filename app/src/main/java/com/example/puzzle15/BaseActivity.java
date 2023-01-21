package com.example.puzzle15;

import android.app.UiModeManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.puzzle15.GameCashe;
import com.example.puzzle15.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected int modeOrder = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        modeOrder = GameCashe.getGameCashe().getLastMode();


        AppCompatDelegate.setDefaultNightMode(modeOrder);

        BaseOnCreate(savedInstanceState);

    }
    protected abstract void BaseOnCreate(Bundle savedInstanceState);
}
