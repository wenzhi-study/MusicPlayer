package com.example.musicplayer.bean;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * @ProjectName: MusicPlayer
 * @Package: com.example.musicplayer.bean
 * @ClassName: MyMusicPlayer
 * @Description:
 * @Author: 作者名：王文治
 * @CreateDate: 2019/12/16 9:36
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/16 9:36
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MyMusicPlayer {
    int CurrnetPausePosationInSong = 0;
    int state = 0x11;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    /*
     *
     * 函数名:preAndPlay
     * 参  数:歌曲路径
     * 作  用：准备并播放歌曲
     * */
    public void preAndPlay(String path) {
        try {

            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startMusic(){
        mediaPlayer.start();
    }
    //音乐暂停
    public void pause() {
        mediaPlayer.pause();
    }

    //音乐停止
    public void stopMusic() {
        mediaPlayer.stop();

    }

    public void continueplay() {
        mediaPlayer.start();
    }

    //音乐下一曲
    public void changeMusci(String path) {
        stopMusic();
        preAndPlay(path);
    }

    //音乐下一曲
    public void lastMusci(String path) {
        stopMusic();
        preAndPlay(path);
    }

    //音乐连续播放
    public void lianPlay() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //播放下一首歌
            }
        });
    }

    //关闭音乐播放器
    public void closeMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean checkPlayMusic() {
        return mediaPlayer.isPlaying();
    }

    public void seekTo(int progess) {
        mediaPlayer.seekTo(progess * 3000);
    }


    public int getCurrentProgress() {
        int sumPro = mediaPlayer.getDuration();
        int currrentPro = mediaPlayer.getCurrentPosition();
        return 100 * currrentPro / sumPro;
    }

}
