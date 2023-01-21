package com.example.puzzle15;

import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.widget.AppCompatRadioButton$InspectionCompanion;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.transform.sax.SAXResult;

public class GameCashe {
    private String KEY_MODE = "key_mode";
    private static GameCashe gameCashe ;

    private SharedPreferences preferences;

    private String KEY_COUNT = "record_count";
    private String KEY_RECORD = "record";

    private GameCashe(Context context){
        preferences = context.getSharedPreferences("game_cache",Context.MODE_PRIVATE);
    }

    public static void init(Context context){
        if (gameCashe == null) {
            gameCashe = new GameCashe(context);
        }
    }

    public static GameCashe getGameCashe() {
        return gameCashe;
    }
    public void saveRecord(RecordData data){

        int order = getRecordCount() + 1;

        String time_key = "time_".concat(String.valueOf(order));
        String step_key = "step_".concat(String.valueOf(order));
        String date_key = "date_".concat(String.valueOf(order));

        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(time_key,data.getTime());
        editor.putLong(step_key,data.getStep());
        editor.putLong(date_key,data.getDate().getTime());

        editor.apply();

        saveRecordCount();
    }
    public ArrayList<RecordData> getLastRecord(){
        ArrayList<RecordData> recordData = new ArrayList<>();

        for (int order = 0; order < getRecordCount(); order++) {

            String time_key = "time_".concat(String.valueOf(order));
            String step_key = "step_".concat(String.valueOf(order));
            String date_key = "date_".concat(String.valueOf(order));

            RecordData data = new RecordData(
                    preferences.getLong(time_key,0),
                    preferences.getLong(step_key,0),
                    new Date(preferences.getLong(date_key,0))
                    );
                    recordData.add(data);
        }
        return recordData;

    }
    private void saveRecordCount(){

        int lastRecordCount = getRecordCount()+1;

        preferences.edit().putInt(KEY_COUNT,lastRecordCount).apply();
    }
    private int getRecordCount(){
        int totalRecordCount = preferences.getInt(KEY_COUNT,0);
        return totalRecordCount;
    }
    public void setLastMode(int mode){
        preferences.edit().putInt(KEY_MODE,mode).apply();
    }

    public int getLastMode() {
        return preferences.getInt(KEY_MODE, UiModeManager.MODE_NIGHT_AUTO);
    }
}
