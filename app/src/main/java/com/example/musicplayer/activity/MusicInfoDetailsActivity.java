package com.example.musicplayer.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.R;

public class MusicInfoDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivBack,ivShare,ivPlayMethod,ivLast,ivPlay,ivNext,ivList,ivDisc;
    SeekBar sbMusic;
    TextView tvHadlayTime,tvRemainMusicTime,tvSongName,tvSinger,tvDisc;
    static int control_play = 0x01;
    static int control_goOn = 0x02;
    static int control_pause = 0x03;
    static int control_stop = 0x04;
    static int control_next = 0x05;
    static int state_noplay = 0x11;
    static int state_playing = 0x12;
    static int state_pause = 0x13;
    static int state = state_noplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_info_details);
        initViews();
        setBroadCast();

    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        ivShare = findViewById(R.id.iv_share);
        ivDisc = findViewById(R.id.iv_disc);
        ivPlayMethod = findViewById(R.id.iv_play_method);
        ivLast = findViewById(R.id.iv_last);
        ivPlay = findViewById(R.id.iv_play);
        ivNext = findViewById(R.id.iv_next);
        ivList = findViewById(R.id.iv_list);
        sbMusic = findViewById(R.id.sb_music);
        tvHadlayTime = findViewById(R.id.tv_had_play_time);
        tvRemainMusicTime = findViewById(R.id.tv_remain_music_time);
        tvSongName = findViewById(R.id.tv_song_name);
        tvSinger = findViewById(R.id.tv_singer_name);
        tvDisc = findViewById(R.id.tv_disc);
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivPlayMethod.setOnClickListener(this);
        ivLast.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        ivList.setOnClickListener(this);
        sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progess = seekBar.getProgress();
                MediaPlayer mediaPlayer = new MediaPlayer();
              //  mediaPlayer.seekTo(progess);

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                Intent intent = new Intent(MusicInfoDetailsActivity.this, LikeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_share:
                break;
            case R.id.iv_play_method:
                break;
            case R.id.iv_last:
                break;
            case R.id.iv_play:
                sbMusic.setMax(10);
                break;
            case R.id.iv_next:
                break;
            case R.id.iv_list:
                break;
        }

    }
    private class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String id = intent.getStringExtra("id");
            String musicName = intent.getStringExtra("musicName");
            String singerName = intent.getStringExtra("singerName");
            String disc = intent.getStringExtra("disc");
            state = intent.getIntExtra("state", 0x10);
            if (state != 0x10)
            {
                if (singerName!=null&&musicName!=null)
                {
                    tvSinger.setText(singerName);
                    tvSongName.setText(musicName);
                }
                if (state == state_playing) {
                    ivPlay.setImageResource(R.drawable.pause3);
                } else {
                    ivPlay.setImageResource(R.drawable.play3);
                }
            }

        }

    }

    private void setBroadCast() {
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        IntentFilter filter = new IntentFilter(LikeActivity.UPDATE);
        filter.addDataScheme("file");
        registerReceiver(myBroadCastReceiver, filter);
    }
}
