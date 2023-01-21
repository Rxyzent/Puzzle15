package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {

    private TextView timeText,stepText;
    private Button newGame,menu;
    private RecordData recordData;
    public static String  KEY ="winner_scores";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        recordData = getIntent().getParcelableExtra(KEY);
        loadView();

        long second = recordData.getTime()%60;
        long minut = recordData.getTime()/60%60;

        String time = String.format("%02d:%02d",minut,second);

        timeText.setText(String.valueOf(time));
        stepText.setText(String.valueOf(recordData.getStep()));

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });
    }

    private void loadView() {
        timeText = findViewById(R.id.win_time);
        stepText = findViewById(R.id.win_steps);
        newGame = findViewById(R.id.win_newGame_btn);
        menu = findViewById(R.id.win_menu_btn);
    }
}