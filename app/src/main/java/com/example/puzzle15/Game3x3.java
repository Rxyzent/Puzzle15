package com.example.puzzle15;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class Game3x3 extends BaseActivity {

    private Button newGame,pause;
    private TextView moves, time;
    private RelativeLayout buttonGroup;
    private Button[][] buttons = new Button[3][3];

    private int stepCount = 0,timeCount =0;

    private Timer timer;
    private TimerTask timerTask;
    private boolean isTimerRuning=false,startGame=true;


    private int emptyI = (int) (Math.random() * 10 % 3);
    private int emptyJ = (int) (Math.random() * 10 % 3);

    private ArrayList<Integer> numbers = new ArrayList<>();


    @Override
    protected void BaseOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.puzzle3x3);



        loadView();
        loadData();


        if (savedInstanceState == null) {
            Toast.makeText( Game3x3.this,"O'yinni boshlash uchun start tugmasini bosing!", Toast.LENGTH_SHORT).show();
        }

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startGame){

                    loadDataToView();
                    loadAction();
                    loadTimer();
                    pause.setText("pause");
                    startGame=false;
                }else if(isTimerRuning){
                    pause.setText("continue");
                    timer.cancel();
                    isTimerRuning=false;
                    showPauseDialog();
                }else {
                    loadTimer();
                    loadAction();
                    pause.setText("pause");
                }
            }
        });

        if(savedInstanceState!=null && savedInstanceState.containsKey("stepCount") && savedInstanceState.containsKey("time")){



            /*if(!startGame){

                timeCount = savedInstanceState.getInt("time");
                stepCount = savedInstanceState.getInt("stepCount");
                startGame = savedInstanceState.getBoolean("startGame");
                isTimerRuning = savedInstanceState.getBoolean("isTimeRuning");

                emptyI = savedInstanceState.getInt("emptyI");
                emptyJ = savedInstanceState.getInt("emptyJ");

                String [] numbers = savedInstanceState.getStringArray("numbers");

                for (int i = 0; i < numbers.length; i++) {
                    buttons[i/3][i%3].setText(numbers[i]);
                    buttons[i/3][i%3].setBackgroundResource(R.drawable.fill_button_backgraund);

                }
                buttons[emptyI][emptyJ].setText("");
                buttons[emptyI][emptyJ].setBackgroundResource(R.drawable.empty_button_backgraund);

                long second = timeCount%60;
                long minut = timeCount/60%60;

                String timeText = String.format("%02d:%02d",minut,second);

                time.setText(timeText);
                moves.setText(String.valueOf(stepCount));


                pause.performClick();
            }*/
            timeCount = savedInstanceState.getInt("time");
            stepCount = savedInstanceState.getInt("stepCount");
            startGame = savedInstanceState.getBoolean("startGame");
            isTimerRuning = savedInstanceState.getBoolean("isTimeRuning");

            emptyI = savedInstanceState.getInt("emptyI");
            emptyJ = savedInstanceState.getInt("emptyJ");

            String [] numbers = savedInstanceState.getStringArray("numbers");

            for (int i = 0; i < numbers.length; i++) {
                buttons[i/3][i%3].setText(numbers[i]);
                buttons[i/3][i%3].setBackgroundResource(R.drawable.fill_button_backgraund);

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
            buttons[i/3][i%3].setOnClickListener(this::onButtonClick);

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

            App.makeTrueSound();
            Animation animationBounce = AnimationUtils.loadAnimation(this,R.anim.bounce);
            buttons[emptyI][emptyJ].startAnimation(animationBounce);

            emptyI = i;
            emptyJ = j;

            if(emptyI==2&&emptyJ==2){
                if(checkWin()){
                    App.makeWinSound();

                    timer.cancel();
                    long second = timeCount%60;
                    long minut = timeCount/60%60;

                    String timeText = String.format("%02d:%02d",minut,second);
                    time.setText(timeText);
/*
                    SharedPreferences sharedPreferences = getSharedPreferences("gameCache",Context.MODE_PRIVATE);

                    if(sharedPreferences.getInt("time3x3",0)*sharedPreferences.getInt("moves3x3",0)>stepCount*timeCount){
                        saveData("time3x3",timeCount);
                        saveData("moves3x3", stepCount);
                    }*/
                    saveData();
                    RecordData recordData = new RecordData(timeCount,stepCount,new Date());
                    Intent intent = new Intent(Game3x3.this,WinActivity.class);
                    intent.putExtra(WinActivity.KEY,recordData);

                    resultLauncher.launch(intent);

                }
            }

        } else{
            App.makeErrorSound();
            Animation animationShake = AnimationUtils.loadAnimation(this,R.anim.shake);
            button.startAnimation(animationShake);
        }
    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            int code  = result.getResultCode();
            if(code==1){
                loadView();
                loadData();
                startGame = true;
                isTimerRuning = false;
                pause.performClick();
                newGame.performClick();
            }else if(code==2){
                finish();
            }
        }
    });
    private void showPauseDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Pause").setMessage("Are you want to continue?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pause.performClick();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Game3x3.this.finish();
            }
        });

        builder.setCancelable(false);

        builder.create();
        builder.show();
    }

    private boolean checkWin() {
        char min = '0';
        int count = 0;
        for (int i = 0; i <buttonGroup.getChildCount()-1; i++) {
            if(buttons[i/3][i%3].getText().charAt(0)>min){
                count++;
                min=buttons[i/3][i%3].getText().charAt(0);
            }
        }
        if(count==8){
            return  true;
        }else {
            return false;
        }
    }

    private void loadData() {
        numbers.clear();
        for (int i = 1; i < 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
       while (!isResolvable(numbers)){
           Collections.shuffle(numbers);
       }


        emptyI = (int) (Math.random() * 10 % 3);
        emptyJ = (int) (Math.random() * 10 % 3);
    }

    private void loadDataToView() {

        int count =0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

            buttons[i / 3][i % 3] = button;
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
        if(count%2==0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(isTimerRuning==true){
            timer.cancel();
            timer = null;

            timerTask.cancel();
            timerTask = null;
        }

        String [] numbers = new String[9];

        for (int i = 0; i < 9; i++) {
            numbers[i] = buttons[i/3][i%3].getText().toString();
        }



        outState.putInt("time",timeCount);
        outState.putInt("stepCount",stepCount);
        outState.putInt("emptyI",emptyI);
        outState.putInt("emptyJ",emptyJ);
        outState.putStringArray("numbers",numbers);
        outState.putBoolean("startGame",false);
        outState.putBoolean("isTimeRuning",false);

    }

    private void saveData(){

        RecordData recordData = new RecordData(timeCount,stepCount,new Date());

        GameCashe.getGameCashe().saveRecord(recordData);
    }

}
