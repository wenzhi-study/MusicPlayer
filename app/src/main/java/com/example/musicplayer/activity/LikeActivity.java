package com.example.musicplayer.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.R;
import com.example.musicplayer.bean.LocalMusicBean;
import com.example.musicplayer.bean.MyMusicPlayer;
import com.example.musicplayer.myAdapater.LocalMusicAdapater;
import com.example.musicplayer.service.MusicService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class LikeActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivPlayOrPause, ivStop, ivDisc, ivFastBackward, ivSpeed;
    ListView lvMusic;
    TextView tvSongName, tvSinger, tvPlayMethod;
    SeekBar skbMusic;
    MyMusicPlayer myMusicPlayer = new MyMusicPlayer();
    ArrayList<LocalMusicBean> musicBeans = new ArrayList<LocalMusicBean>();
    public static final String CONTROL = "com.example.musicplayer.CONTROL";
    public static final String UPDATE = "com.example.musicplayer.UPDATE";
    private ServiceConnection mConnection;
    private IBinder mBinder;
    static int currnetPlayPosation = -1;  //播放位置
    int control = 0x00;
    int control_play = 0x01;
    int control_change = 0x02;
    int control_pause = 0x03;
    int control_stop = 0x04;
    int control_goOn = 0x05;
    static int state_noplay = 0x11;
    static int state_playing = 0x12;
    static int state_pause = 0x13;
    static int state = state_noplay;
    private boolean randomMethod = true;
    Intent intentOnClick = new Intent(CONTROL);

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        service_connection();
        initView();
        initLvMusic();
        setBroadCast();
    }

    public void service_connection() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mConnection = null;
            }
        };
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /*
     * 函数名：initViews
     * 参数：无
     * 作用： 实例化对象，设置适配器，画笔
     * 返回值：无
     * */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void initView() {
        ivDisc = findViewById(R.id.iv_disc);
        ivFastBackward = findViewById(R.id.iv_fastBackward);
        ivPlayOrPause = findViewById(R.id.iv_playOrPause);
        ivSpeed = findViewById(R.id.iv_speed);
        ivStop = findViewById(R.id.iv_stop);
        tvSinger = findViewById(R.id.tv_singer);
        tvSongName = findViewById(R.id.tv_song_name);
        tvPlayMethod = findViewById(R.id.tv_play_method);
        skbMusic = findViewById(R.id.skb_music);
        ivStop.setOnClickListener(this);
        ivSpeed.setOnClickListener(this);
        ivPlayOrPause.setOnClickListener(this);
        ivFastBackward.setOnClickListener(this);
        ivDisc.setOnClickListener(this);
        tvPlayMethod.setOnClickListener(this);
        skbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent = new Intent(CONTROL);
                intent.putExtra("pro", seekBar.getProgress());
                sendBroadcast(intent);

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void initLvMusic() {
        lvMusic = findViewById(R.id.lv_music);
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null, null);
        int id = 0;
        while (cursor.moveToNext() && ifMusicBigThan1(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)))) {
            String song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));//DATE
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String time = sdf.format(new Date(duration));
            id++;
            String sid = String.valueOf(id);
            LocalMusicBean bean = new LocalMusicBean(sid, song, singer, album, time, path);
            musicBeans.add(bean);
        }
        cursor.close();
        LocalMusicAdapater localMusicAdapater = new LocalMusicAdapater(musicBeans, this);
        lvMusic.setAdapter(localMusicAdapater);
        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intentMusicBean = new Intent(CONTROL);
                intentMusicBean.putExtra("path", musicBeans.get(position).getStrMusicPath());
                intentMusicBean.putExtra("id", musicBeans.get(position).getStrId());
                intentMusicBean.putExtra("musicName", musicBeans.get(position).getStrMusicName());
                intentMusicBean.putExtra("singerName", musicBeans.get(position).getStrSingerName());
                intentMusicBean.putExtra("disc", musicBeans.get(position).getStrSpecialName());
                intentMusicBean.putExtra("control", control_play);
                sendBroadcast(intentMusicBean);
                currnetPlayPosation = position;
            }
        });
    }

    /*
     * 函数名：ifMusicBigThan1
     * 参  数:getduration 的long 形的 时间值
     * 返  回：返回时间是否时间大于1 Boolean 形
     * 作  用：判断歌曲时长是否大于1分钟
     * */
    private boolean ifMusicBigThan1(long time) {
        return time / 60000 >= 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_speed:
                nextMusic();
                break;
            case R.id.iv_fastBackward:
                lastMusic();
                break;
            case R.id.iv_stop:
                stopMusic();
                break;
            case R.id.iv_playOrPause:
                playOrPauseMusic();
                break;
            case R.id.iv_disc:
                Intent intent = new Intent(LikeActivity.this, MusicInfoDetailsActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_play_method:
                changPlayMethod();
                break;

        }

    }

    public void changPlayMethod() {
        if (tvPlayMethod.getText() == "顺序") {
            randomMethod = true;
            tvPlayMethod.setText("顺序");
        } else {
            randomMethod = false;
            tvPlayMethod.setText("随机");
        }

    }

    public void nextMusic() {

        Random random = new Random();
        int i = random.nextInt(musicBeans.size());
        if (currnetPlayPosation >= 0) {
            if (currnetPlayPosation == musicBeans.size() - 1) {
                Toast.makeText(this, "已经是最后一曲了", Toast.LENGTH_SHORT).show();
            } else {
                if (randomMethod) {
                    currnetPlayPosation = currnetPlayPosation + 1;
                } else {
                    currnetPlayPosation = i;
                }
                intentOnClick.putExtra("path", musicBeans.get(currnetPlayPosation).getStrMusicPath());
                intentOnClick.putExtra("id", musicBeans.get(currnetPlayPosation).getStrId());
                intentOnClick.putExtra("musicName", musicBeans.get(currnetPlayPosation).getStrMusicName());
                intentOnClick.putExtra("singerName", musicBeans.get(currnetPlayPosation).getStrSingerName());
                intentOnClick.putExtra("disc", musicBeans.get(currnetPlayPosation).getStrSpecialName());
                //intentOnClick.putExtra("state", state_playing);
                intentOnClick.putExtra("control", control_change);
                sendBroadcast(intentOnClick);
            }
        } else {
            Toast.makeText(this, "请先选择音乐", Toast.LENGTH_SHORT).show();
        }
    }

    public void playOrPauseMusic() {
        if (currnetPlayPosation >= 0) {
            intentOnClick.putExtra("path", musicBeans.get(currnetPlayPosation).getStrMusicPath());
//            intentOnClick.putExtra("id", musicBeans.get(currnetPlayPosation).getStrId());
//            intentOnClick.putExtra("musicName", musicBeans.get(currnetPlayPosation).getStrMusicName());
//            intentOnClick.putExtra("singerName", musicBeans.get(currnetPlayPosation).getStrSingerName());
//            intentOnClick.putExtra("disc", musicBeans.get(currnetPlayPosation).getStrSpecialName());
            if (state == state_pause) {
                Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
                intentOnClick.putExtra("control", control_goOn);
            }else if (state== state_noplay){
                intentOnClick.putExtra("control", control_play);
            }
            else {

                Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show();
                intentOnClick.putExtra("control", control_pause);
            }
            sendBroadcast(intentOnClick);
        } else {
            Toast.makeText(this, "请先选择音乐", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopMusic() {
        if (currnetPlayPosation >= 0) {
            intentOnClick.putExtra("path", musicBeans.get(currnetPlayPosation).getStrMusicPath());
            intentOnClick.putExtra("id", musicBeans.get(currnetPlayPosation).getStrId());
            intentOnClick.putExtra("musicName", musicBeans.get(currnetPlayPosation).getStrMusicName());
            intentOnClick.putExtra("singerName", musicBeans.get(currnetPlayPosation).getStrSingerName());
            intentOnClick.putExtra("disc", musicBeans.get(currnetPlayPosation).getStrSpecialName());
            intentOnClick.putExtra("control", control_stop);
            sendBroadcast(intentOnClick);
        } else {
            Toast.makeText(this, "请先选择音乐", Toast.LENGTH_SHORT).show();
        }
    }

    public void lastMusic() {
        if (currnetPlayPosation >= 0) {
            if (currnetPlayPosation == 0) {
                Toast.makeText(this, "已经是第一曲了", Toast.LENGTH_SHORT).show();
            } else {
                currnetPlayPosation = currnetPlayPosation - 1;
                intentOnClick.putExtra("path", musicBeans.get(currnetPlayPosation).getStrMusicPath());
                intentOnClick.putExtra("id", musicBeans.get(currnetPlayPosation).getStrId());
                intentOnClick.putExtra("musicName", musicBeans.get(currnetPlayPosation).getStrMusicName());
                intentOnClick.putExtra("singerName", musicBeans.get(currnetPlayPosation).getStrSingerName());
                intentOnClick.putExtra("disc", musicBeans.get(currnetPlayPosation).getStrSpecialName());
                intentOnClick.putExtra("control", control_change);
                sendBroadcast(intentOnClick);
            }

        } else {
            Toast.makeText(this, "请先选择音乐", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int pro = intent.getIntExtra("progress", 0);
            control = intent.getIntExtra("control", 0x00);

            if (control == control_play) {
                ivPlayOrPause.setImageResource(R.drawable.play3);
                state=state_pause;
            } else if (control == control_pause) {
                ivPlayOrPause.setImageResource(R.drawable.pause3);
                state = state_playing;
            }
            if (intent.getIntExtra("state",0x10)!=0x10)
            {
                state = intent.getIntExtra("state",0x10);
            }
            tvSinger.setText(musicBeans.get(currnetPlayPosation).getStrSingerName());
            tvSongName.setText(musicBeans.get(currnetPlayPosation).getStrMusicName());
            if (pro != 0) {
                skbMusic.setProgress(pro);
            }

        }
    }

    private void setBroadCast() {
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        IntentFilter filter = new IntentFilter(UPDATE);
        registerReceiver(myBroadCastReceiver, filter);
    }


}
