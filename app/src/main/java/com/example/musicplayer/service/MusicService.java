package com.example.musicplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.musicplayer.R;
import com.example.musicplayer.activity.LikeActivity;
import com.example.musicplayer.bean.MyMusicPlayer;


public class MusicService extends Service {
    public MyMusicPlayer myMusicPlayer = new MyMusicPlayer();
    int state;
    int control = 0x00;
    int control_play = 0x01;
    int control_change = 0x02;
    int control_pause = 0x03;
    int control_stop = 0x04;
    int control_goOn = 0x05;
    int state_noplay = 0x11;
    int state_playing = 0x12;
    int state_pause = 0x13;
    private IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        public void NotifiStart() {
            Intent intent = new Intent(MusicService.this, LikeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MusicService.this, 0, intent, 0);
            Notification notification = new NotificationCompat.Builder(MusicService.this).setContentTitle("听歌").setSmallIcon(R.drawable.cp).setContentIntent(pendingIntent).build();
            startForeground(1, notification);
        }

        public void NotifiStop() {
            stopForeground(true);
        }
    }

    Thread myThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(LikeActivity.UPDATE);
            while (true) {
                try {
                    Thread.sleep(100);
                    if (myMusicPlayer.checkPlayMusic()) {
                        int pro = myMusicPlayer.getCurrentProgress();
                        intent.putExtra("progress", pro);
                        //intent.putExtra("control", state_pause);
                        sendBroadcast(intent);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentfilter = new IntentFilter(LikeActivity.CONTROL);
        SerBroadcastReceiver mySerReceivrt = new SerBroadcastReceiver();
        registerReceiver(mySerReceivrt, intentfilter);
        myThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

//    public class MyBinder extends Binder {
//        public void NotifiStart() {
//            Intent intent = new Intent(MusicService.this, LikeActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(MusicService.this, 0, intent, 0);
//            Notification notification = new NotificationCompat.Builder(MusicService.this).setContentTitle("听歌").setSmallIcon(R.drawable.cover).setContentIntent(pendingIntent).build();
//            startForeground(1, notification);
//        }
//
//        public void NotifiStop() {
//            stopForeground(true);
//        }
//    }

    public class SerBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String path = intent.getStringExtra("path");
            control = intent.getIntExtra("control",0x00);
            int pro = intent.getIntExtra("pro", -1);
            Intent UIintent = new Intent(LikeActivity.UPDATE);
            if (control == control_play)
            {
                myMusicPlayer.preAndPlay(path); //继续播放
                UIintent.putExtra("control",control_pause);
            }
            else if (control== control_pause)
            {
                myMusicPlayer.pause();
                UIintent.putExtra("control",control_play);
            }
            else if (control == control_change)
            {
                myMusicPlayer.changeMusci(path);
                UIintent.putExtra("control",control_pause);
            }
            else if (control ==control_stop)
            {
                myMusicPlayer.stopMusic();
                UIintent.putExtra("control",control_play);
                UIintent.putExtra("state",state_noplay);
            }
            else if (control ==control_goOn)
            {
                myMusicPlayer.continueplay();
                UIintent.putExtra("control",control_pause);
            }
            if (pro != -1) {
                myMusicPlayer.seekTo(pro);
            }
            sendBroadcast(UIintent);
        }
    }
}
