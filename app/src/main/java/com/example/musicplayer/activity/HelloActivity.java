package com.example.musicplayer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.R;
import com.example.musicplayer.view.CustomVideoView;

public class HelloActivity extends AppCompatActivity implements View.OnClickListener {
    private CustomVideoView vdoViFirst;
    private Button btnSkip;
    private TextView tvTime;
    private Boolean skipFlag = false;
    private SharedPreferences sp;
    public static int loginFlag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        initView();
        if (!takeSharedPreferences(this)) {
            // 有过功能引导，就跳出
            myThread.start();
        } else {
            if (!takeSharedPreferencesLogin(this)) {
                Intent intent = new Intent(HelloActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


        }
    }
    /*
     * 函数名：initView
     * 参数：无
     * 作用： 实例化对象,设置监听
     * 返回值：无
     * */
    private void initView() {
        btnSkip = findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(this);
        vdoViFirst = findViewById(R.id.vdoVi_first);
        vdoViFirst.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sport));   //地址
        //   videoview.setMediaController(new MediaController(this));播放条
        //播放
        vdoViFirst.start();
        //循环播放
        vdoViFirst.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                vdoViFirst.start();
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        return false;
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_skip) {
            skipFlag = true;
            Intent intent = new Intent(HelloActivity.this, LoginActivity.class);
            saveSharedPreferences(HelloActivity.this, "1");
            startActivity(intent);
            finish();
        }
    }
    //返回重启加载
    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }
    @Override
    protected void onStop() {
        super.onStop();
        vdoViFirst.stopPlayback();
    }
    Thread myThread = new Thread(new Runnable() {
        private int time = 3;
        private Message msgTime;

        @Override
        public void run() {
            while (time >= 0) {
                if (!skipFlag) {//如果点击了跳转就不让线程继续
                    try {      //须考虑异常
                        msgTime = handler.obtainMessage();//从消息池中拿出一个msg  obtianmessage能够循环利用，而new一个需要再次申请
                        msgTime.arg1 = time;
                        handler.sendMessage(msgTime);
                        Thread.sleep(1000);
                        time--;
                        //sleep  1秒并且发送time的值
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent(HelloActivity.this, LoginActivity.class);
            startActivity(intent);
            saveSharedPreferences(HelloActivity.this, "1");
            finish();
        }
    });
    //主线程接受并设置time值
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            tvTime = findViewById(R.id.tv_time);
            tvTime.setText(String.valueOf(msg.arg1));
            return false;
        }
    });

    /*
     * 函数名：saveSharedPreferences
     * 参数：Context context, String save
     * 作用： 保存是否有过引导界面的数据
     * 返回值：无
     * */
    public void saveSharedPreferences(Context context, String save) {
        sp = context.getSharedPreferences("prompt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("stateHello", save);
        editor.commit();// 保存新数据
    }

    /*
     * 函数名：takeSharedPreferences
     * 参数：Context context
     * 作用： 获取是否有过引导界面的数据
     * 返回值：无
     * */
    public boolean takeSharedPreferences(Context context) {
        String str, string = "";
        sp = context.getSharedPreferences("prompt", MODE_PRIVATE);
        str = sp.getString("stateHello", string);
        return !str.equals("");
    }
    public boolean takeSharedPreferencesLogin(Context context) {
        String str, string = "";
        sp = context.getSharedPreferences("prompt", MODE_PRIVATE);
        str = sp.getString("Login", string);
        return !str.equals("");
    }


}
