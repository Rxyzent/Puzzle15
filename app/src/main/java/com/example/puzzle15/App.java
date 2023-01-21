package com.example.puzzle15;

import android.app.Application;
import android.media.MediaPlayer;



public class App extends Application {

    public static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        GameCashe.init(this);
    }
    static MediaPlayer mediaPlayer = null;
    public static void makeTrueSound(){
        if(mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(instance,R.raw.tick_click);
        }else {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = MediaPlayer.create(instance,R.raw.tick_click);
        }
        mediaPlayer.start();
    }
    static MediaPlayer mediaErrorPlayer = null;
    public static void makeErrorSound(){
        if(mediaErrorPlayer==null){
            mediaErrorPlayer = MediaPlayer.create(instance,R.raw.miss_click);
        }else {
            mediaErrorPlayer.stop();
            mediaErrorPlayer.release();
            mediaErrorPlayer = null;
            mediaErrorPlayer = MediaPlayer.create(instance,R.raw.miss_click);
        }
        mediaErrorPlayer.start();
    }
    static MediaPlayer mediaWinPlayer = null;
    public static void makeWinSound(){
        if(mediaWinPlayer==null){
            mediaWinPlayer = MediaPlayer.create(instance,R.raw.win_sound);
        }else {
            mediaWinPlayer.stop();
            mediaWinPlayer.release();
            mediaWinPlayer = null;
            mediaWinPlayer = MediaPlayer.create(instance,R.raw.win_sound);
        }
        mediaWinPlayer.start();
    }
}
