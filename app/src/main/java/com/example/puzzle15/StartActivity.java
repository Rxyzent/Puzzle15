package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StartActivity extends BaseActivity {
    private Button puzzle3x3,puzzle4x4,maxScore,settings,exit;

    @Override
    protected void BaseOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_start);

        loadView();

        puzzle3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,Game3x3.class);
                startActivity(intent);
            }
        });
        puzzle4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,Game4x4.class);
                startActivity(intent);
            }
        });
        maxScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,ScoreActivity.class);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadView() {

        puzzle3x3 = findViewById(R.id.btn_3x3);
        puzzle4x4 = findViewById(R.id.btn_4x4);
        maxScore = findViewById(R.id.btn_score);
        settings = findViewById(R.id.btn_settings);
        exit = findViewById(R.id.btn_exit);
    }
}