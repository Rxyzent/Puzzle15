package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScoreActivity extends BaseActivity {

    private Button mainMenu;
    private LinearLayout recordLayout;
    private View recordItem;


    @Override
    protected void BaseOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_score);

        loadView();


        ArrayList<Integer> listImage = new ArrayList();

        listImage.add(R.drawable.image1);
        listImage.add(R.drawable.image2);
        listImage.add(R.drawable.image3);
        listImage.add(R.drawable.image4);
        listImage.add(R.drawable.image5);
        listImage.add(R.drawable.image6);

        ArrayList<Integer> listColor = new ArrayList();

        listColor.add(R.color.color1);
        listColor.add(R.color.color2);
        listColor.add(R.color.color3);
        listColor.add(R.color.color4);
        listColor.add(R.color.color5);
        listColor.add(R.color.color6);



        ArrayList<RecordData> list = GameCashe.getGameCashe().getLastRecord();

        for (int i = 0; i < list.size(); i++) {
            recordItem = LayoutInflater.from(this).inflate(R.layout.item_record,null,false  );
            RecordData recordData = list.get(i);


            TextView recordTime = recordItem.findViewById(R.id.time_record);
            TextView recordStep = recordItem.findViewById(R.id.step_record);
            TextView recordDate = recordItem.findViewById(R.id.date_record);
            CircleImageView imageView = recordItem.findViewById(R.id.image_view);
            LinearLayout layout = recordItem.findViewById(R.id.back_color);

            imageView.setImageResource(listImage.get(i%6));
            layout.setBackgroundResource(listColor.get(i%6));

            long time = recordData.getTime();
            long second = time%60;
            long minut = time/60%60;
            String timeText = String.format("%02d:%02d",minut,second);

            recordTime.setText(timeText);
            recordStep.setText(String.valueOf(recordData.getStep()));

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
            String dateText = dateFormat.format(recordData.getDate());

            recordDate.setText(dateText);
            recordLayout.addView(recordItem);

        }

        /*SharedPreferences sharedPreferences = getSharedPreferences("gameCache", Context.MODE_PRIVATE);

        long second = sharedPreferences.getInt("time3x3",0)%60;
        long minut = sharedPreferences.getInt("time3x3",0)/60%60;
        String timeText = String.format("%02d:%02d",minut,second);

        time3x3.setText(timeText);
        moves3x3.setText(String.valueOf(sharedPreferences.getInt("moves3x3",0)));*/

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadView() {
        mainMenu = findViewById(R.id.btn_main_menu);
        recordLayout = findViewById(R.id.record_layout);
        /*time3x3 = findViewById(R.id.score_time_3x3);
        time4x4 = findViewById(R.id.score_time_4x4);
        moves3x3 = findViewById(R.id.score_moves_3x3);
        moves4x4 = findViewById(R.id.score_moves_4x4);*/
    }
}