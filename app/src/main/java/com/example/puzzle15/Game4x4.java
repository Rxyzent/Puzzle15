package com.example.puzzle15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Game4x4 extends AppCompatActivity {

    private Button newGame,pause;
    private TextView moves, time;
    private RelativeLayout buttonGroup;
    private Button[][] buttons = new Button[4][4];

    private int stepCount = 0,timeCount =0;

    private Timer timer;
    private TimerTask timerTask;
    private boolean isTimerRuning,startGame=true;


    private int emptyI = (int) (Math.random() * 10 % 4);
    private int emptyJ = (int) (Math.random() * 10 % 4);

    private ArrayList<Integer> numbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4x4);



        loadView();
        loadData();


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTimerRuning){
                    pause.setText("continue");
                    timer.cancel();
                    isTimerRuning=false;
                }else if(startGame){
                    loadDataToView();
                    loadAction();
                    loadTimer();
                    pause.setText("pause");
                    startGame=false;
                }else {
                    loadTimer();
                    loadAction();
                    pause.setText("pause");
                }
            }
        });

        if(savedInstanceState!=null && savedInstanceState.containsKey("stepCount") && savedInstanceState.containsKey("time")){

            timeCount = savedInstanceState.getInt("time");
            stepCount = savedInstanceState.getInt("stepCount");
            startGame = savedInstanceState.getBoolean("startGame");
            isTimerRuning = savedInstanceState.getBoolean("isTimeRuning");

            emptyI = savedInstanceState.getInt("emptyI");
            emptyJ = savedInstanceState.getInt("emptyJ");

            String [] numbers = savedInstanceState.getStringArray("numbers");

            for (int i = 0; i < numbers.length; i++) {
                buttons[i/4][i%4].setText(numbers[i]);
                buttons[i/4][i%4].setBackgroundResource(R.drawable.fill_button_backgraund);

            }
            buttons[emptyI][emptyJ].setText("");
            buttons[emptyI][emptyJ].setBackgroundResource(R.drawable.empty_button_backgraund);

            long second = timeCount%60;
            long minut = timeCount/60%60;

            String timeText = String.format("%02d:%02d",minut,second);

            time.setText(timeText);
            moves.setText(String.valueOf(stepCount));


            pause.performClick();
        }


    }

    private void loadTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                timeCount++;

                long second = timeCount%60;
                long minut = timeCount/60%60;

                String timeText = String.format("%02d:%02d",minut,second);

                time.setText(timeText);
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask,1_000,1_000);
        isTimerRuning = true;
    }

    private void loadAction() {
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timer.cancel();
                timeCount = 0;
                stepCount = 0;
                loadData();
                loadDataToView();
                loadTimer();
                isTimerRuning=true;
                pause.setText("pause");
            }
        });
        for (int i = 0; i < buttonGroup.getChildCount(); i++) {
            buttons[i/4][i%4].setOnClickListener(this::onButtonClick);

        }
    }

    private void onButtonClick(View view) {
        Button button = (Button) view;

        int i = button.getTag().toString().charAt(0)-'0';
        int j = button.getTag().toString().charAt(2)-'0';

        int deltai = Math.abs(i - emptyI);
        int deltaj = Math.abs(j - emptyJ);

        if(deltai+deltaj == 1){

            stepCount++;

            moves.setText(String.valueOf(stepCount));

            buttons[emptyI][emptyJ].setText(button.getText());
            buttons[emptyI][emptyJ].setBackground(button.getBackground());

            button.setText("");
            button.setBackgroundResource(R.drawable.empty_button_backgraund);

            emptyI = i;
            emptyJ = j;

            if(emptyI==3&&emptyJ==3){
                if(checkWin()){

                    Toast.makeText(this, "Tabriklaymiz! Siz g'alaba qozondingiz.", Toast.LENGTH_SHORT).show();

                    timer.cancel();
                    long second = timeCount%60;
                    long minut = timeCount/60%60;

                    String timeText = String.format("%02d:%02d",minut,second);

                    time.setText(timeText);

                }
            }

        }
    }

    private boolean checkWin() {

        for (int i = 1; i < buttonGroup.getChildCount()-1; i++) {
            String s1 = buttons[i/4][i%4].getText().toString();
            String s2 = buttons[(i-1)/4][(i-1)%4].getText().toString();
            if(s1.compareTo(s2)>0){
            }else {
               return false;
            }
        }
       return true;
    }

    private void loadData() {
        numbers.clear();
        for (int i = 1; i < 16; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        while (!isResolvable(numbers)){
            Collections.shuffle(numbers);
        }


        emptyI = (int) (Math.random() * 10 % 4);
        emptyJ = (int) (Math.random() * 10 % 4);
    }

    private void loadDataToView() {

        int count =0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(i==emptyI&&j==emptyJ){
                    buttons[i][j].setBackgroundResource(R.drawable.empty_button_backgraund);
                    buttons[i][j].setText("");

                }else {
                    buttons[i][j].setText(String.valueOf(numbers.get(count)));
                    buttons[i][j].setBackgroundResource(R.drawable.fill_button_backgraund);
                    count++;
                }
            }
        }
        moves.setText("0");
        time.setText("00:00");

    }

    private void loadView() {

        time = findViewById(R.id.time);
        moves = findViewById(R.id.move);
        newGame = findViewById(R.id.new_game);
        pause = findViewById(R.id.pauseBtn);


        buttonGroup = findViewById(R.id.buttonGroup);

        for (int i = 0; i < buttonGroup.getChildCount(); i++) {
            Button button = (Button) buttonGroup.getChildAt(i);

            buttons[i / 4][i % 4] = button;
        }
    }
    private boolean isResolvable(ArrayList<Integer> list){
        int count=0;
        for (int i = 0; i < list.size()-1; i++) {
            for (int j = i+1; j < list.size(); j++) {
                if(list.get(i)>list.get(j)){
                    count++;
                }
        }

    }
        return count % 2 == 0;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        timer.cancel();
        timer = null;

        timerTask.cancel();
        timerTask = null;

        String [] numbers = new String[16];

        for (int i = 0; i < 16; i++) {
            numbers[i] = buttons[i/4][i%4].getText().toString();
        }



        outState.putInt("time",timeCount);
        outState.putInt("stepCount",stepCount);
        outState.putInt("emptyI",emptyI);
        outState.putInt("emptyJ",emptyJ);
        outState.putStringArray("numbers",numbers);
        outState.putBoolean("startGame",false);
        outState.putBoolean("isTimeRuning",false);

    }
}