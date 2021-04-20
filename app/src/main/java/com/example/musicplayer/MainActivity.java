package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    TextView musicCurrent,musicDuration;

    MediaPlayer player;
    ImageButton startbtn;
    int playing_file;
   // ProgressBar musicProgressBar;
    SeekBar musicSeekBar;
    Timer timer;
    TimerTask timerTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicCurrent = (TextView)findViewById(R.id.textView3);
        musicDuration =  (TextView)findViewById(R.id.textView2);
        playing_file=R.raw.music1;
        player = new MediaPlayer();

        startbtn=(ImageButton)findViewById(R.id.imageButton);
        startbtn.setOnClickListener(new pause_and_continue());

        musicSeekBar = (SeekBar)findViewById(R.id.seekBar);

        player=MediaPlayer.create(MainActivity.this,playing_file);
        musicDuration.setText(calcTime(player.getDuration()));
        musicSeekBar.setMax(player.getDuration());

        musicSeekBar.setOnSeekBarChangeListener(new seekBarListener());

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (player.isPlaying()) {
                            int cur=player.getCurrentPosition();
                            musicCurrent.setText(calcTime(cur));
                            musicSeekBar.setProgress(cur);
                        }else{
                            startbtn.setImageResource(android.R.drawable.ic_media_play);
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }


    public String calcTime(int time){
        time/=1000;
        int minute=time/60;
        int second=time%60;
        return ""+(minute>=10?minute:"0"+minute)+":"+(second>=10?second:"0"+second);
    }

    class pause_and_continue implements View.OnClickListener {
        @Override
        public void onClick(View v){
            if(player.isPlaying()){
                startbtn.setImageResource(android.R.drawable.ic_media_play);
                player.pause();
            }else{
                startbtn.setImageResource(android.R.drawable.ic_media_pause);
                player.start();
            }

        }


    }
    class seekBarListener implements  SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            player.seekTo(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}