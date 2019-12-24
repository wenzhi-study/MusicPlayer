package com.example.musicplayer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.musicplayer.R;
import com.example.musicplayer.myAdapater.MainViewPagerAdapater;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTabMusic, tvTabUser, tvTabZone;
    private ImageView ivTabMusic, ivTabUser, ivTabZone,ivPlay;
    private LinearLayout llTabMusic, llTabUSer, llTabZone,llLike,llFecent,llDown,llBuy,llPlayer;
    private ViewPager vpMain;

    private SharedPreferences sp;
    //Intent intentstartService = new Intent(this, MediaService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initMine();
    }
    public void saveSharedPreferences(Context context, String save) {
        sp = context.getSharedPreferences("prompt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("stateHello", save);
        editor.commit();// 保存新数据
    }
    private void initViews() {
        tvTabMusic = findViewById(R.id.tv_tab_music);
        tvTabUser = findViewById(R.id.tv_tab_user);
        tvTabZone = findViewById(R.id.tv_tab_zone);
        ivTabMusic = findViewById(R.id.iv_tab_music);
        ivTabUser = findViewById(R.id.iv_tab_user);
        ivTabZone = findViewById(R.id.iv_tab_zone);
        ivPlay = findViewById(R.id.iv_play);
        llTabMusic = findViewById(R.id.ll_tab_music);
        llTabUSer = findViewById(R.id.ll_tab_user);
        llTabZone = findViewById(R.id.ll_tab_zone);
        llPlayer = findViewById(R.id.ll_palyer);
        vpMain = findViewById(R.id.vp_main);
        llTabMusic.setOnClickListener(this);
        llTabUSer.setOnClickListener(this);
        llTabZone.setOnClickListener(this);
        llPlayer.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        //新建画笔并将3个view画出来
        LayoutInflater myLi = LayoutInflater.from(this);
        View vMine = myLi.inflate(R.layout.mine_main,null);
        llLike   = vMine.findViewById(R.id.ll_like);
        llFecent = vMine.findViewById(R.id.ll_fecent);
        llDown   = vMine.findViewById(R.id.ll_down);
        llBuy    = vMine.findViewById(R.id.ll_buy);
        //将试图存入ArrayList
        final ArrayList<View> views = new ArrayList<View>();
        views.add(vMine);

        //新建并将适配器与ViewPager绑定
        MainViewPagerAdapater mainViewPagerAdapater = new MainViewPagerAdapater(this,views);
        vpMain.setAdapter(mainViewPagerAdapater);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            setView(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        saveSharedPreferences(MainActivity.this,"0");
    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(LikeActivity.CONTROL);
        switch (v.getId()) {
            case R.id.ll_tab_music:
                vpMain.setCurrentItem(0, false); //设置当前页面为队列中的第0个，跳转速度快
                setView(0);//设置tab的显示
                break;
            case R.id.ll_tab_user:
                vpMain.setCurrentItem(1);
                setView(1);
                break;
            case R.id.ll_tab_zone:
                vpMain.setCurrentItem(2);
                setView(2);
                break;
            case R.id.iv_play:
                Toast.makeText(getApplicationContext(), "播放", Toast.LENGTH_SHORT).show();
//                if (statu == 0x11) {
//                    intent1.putExtra("path", songs.get(current).getFilePathStr());
//                    intent1.putExtra("control", 1);
//                    statu = 0x12;
//                    ivPlay.setImageDrawable(getResources().getDrawable(
//                            R.drawable.pause2));
//                }
//                else if (statu == 0x12)
//                {
//                    intent1.putExtra("path", songs.get(current).getFilePathStr());
//                    intent1.putExtra("control", 2);
//                    statu =0x11;
//                    ivPlay.setImageDrawable(getResources().getDrawable(
//                            R.drawable.play2));
//                }

                break;
            case R.id.ll_palyer:
//                Intent intent = new Intent(this,RegisterActivity.class);
//                startActivity(intent);
                break;
            case R.id.ll_like:
                Toast.makeText(getApplicationContext(), "like", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_fecent:

                Toast.makeText(getApplicationContext(), "fecent", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_down:
//
//                Toast.makeText(getApplicationContext(), "down", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LikeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_buy:
                Toast.makeText(getApplicationContext(), "buy", Toast.LENGTH_SHORT).show();
                break;
        }
        //sendBroadcast(intent1);

    }


    public void setView(int position) {
        switch (position) {
            case 0:
                ivTabMusic.setImageDrawable(getResources().getDrawable(
                        R.drawable.music_press));
                tvTabMusic.setTextColor(MainActivity.this.getResources().getColor(R.color.colorBlueDark));
                ivTabUser.setImageDrawable(getResources().getDrawable(
                        R.drawable.tab_user_normal));
                tvTabUser.setTextColor(MainActivity.this.getResources().getColor(R.color.colorBlack));
                ivTabZone.setImageDrawable(getResources().getDrawable(
                        R.drawable.tab_zone_normal));
                tvTabZone.setTextColor(MainActivity.this.getResources().getColor(R.color.colorBlack));
                break;
            case 1:
                ivTabMusic.setImageDrawable(getResources().getDrawable(
                        R.drawable.music_normal));
                tvTabMusic.setTextColor(MainActivity.this.getResources().getColor(R.color.colorBlack));
                ivTabUser.setImageDrawable(getResources().getDrawable(
                        R.drawable.tab_user_pressed));
                tvTabUser.setTextColor(MainActivity.this.getResources().getColor(R.color.colorBlueDark));
                ivTabZone.setImageDrawable(getResources().getDrawable(
                        R.drawable.tab_zone_normal));
                tvTabZone.setTextColor(MainActivity.this.getResources().getColor(R.color.colorBlack));
                break;
            case 2:
                ivTabMusic.setImageDrawable(getResources().getDrawable(
                        R.drawable.music_normal));
                tvTabMusic.setTextColor(MainActivity.this.getResources().getColor(R.color.colorBlack));
                ivTabUser.setImageDrawable(getResources().getDrawable(
                        R.drawable.tab_user_normal));
                tvTabUser.setTextColor(MainActivity.this.getResources().getColor(R.color.colorBlack));
                ivTabZone.setImageDrawable(getResources().getDrawable(
                        R.drawable.tab_zone_pressed));
                tvTabZone.setTextColor(MainActivity.this.getResources().getColor(R.color.colorBlueDark));
                break;
        }

    }
    public void initMine(){
        llLike.setOnClickListener(this);
        llFecent.setOnClickListener(this);
        llDown.setOnClickListener(this);
        llBuy.setOnClickListener(this);
    }

  /*  private class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            statu = intent.getIntExtra("update", 0);
        }
    }

    private void setBroadCast() {
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        IntentFilter filter = new IntentFilter(UPDATE);
        registerReceiver(myBroadCastReceiver, filter);

    }
*/
}
