package com.example.musicplayer.myAdapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.bean.LocalMusicBean;

import java.util.ArrayList;

/**
 * @ProjectName: MusicPlayer
 * @Package: com.example.musicplayer.myAdapater
 * @ClassName: LocalMusicAdapater
 * @Description:
 * @Author: 作者名：小米PRO
 * @CreateDate: 2019/12/21 18:26
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/21 18:26
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class LocalMusicAdapater extends BaseAdapter {

    private ArrayList<LocalMusicBean> musicBeans = new ArrayList<LocalMusicBean>();
    private Context context;
    private LayoutInflater myLi;

    public LocalMusicAdapater(ArrayList<LocalMusicBean> musicBeans, Context context) {
        this.musicBeans = musicBeans;
        this.context = context;
        this.myLi = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return musicBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return musicBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocalMusicBean item = musicBeans.get(position);
        LocalMusicAdapater.ViewHolder holder;
        if (convertView == null) {
            convertView = myLi.inflate(R.layout.music_item, null);
            holder = new LocalMusicAdapater.ViewHolder();
            holder.tvMusicName =convertView.findViewById(R.id.tv_music_name);
            holder.tvMusicSpecialName = convertView.findViewById(R.id.tv_music_special_name);
            holder.tvMusicTime = convertView.findViewById(R.id.tv_music_time);
            holder.tvsingerName = convertView.findViewById(R.id.tv_singer_name);
            holder.tvWhichSong = convertView.findViewById(R.id.tv_which_song);
            holder.ivMusicDisc = convertView.findViewById(R.id.iv_disc);
            convertView.setTag(holder);
        } else {
            holder = (LocalMusicAdapater.ViewHolder) convertView.getTag();
        }
        holder.tvsingerName.setText(item.getStrSingerName());
        holder.tvMusicName.setText(item.getStrMusicName());
        holder.tvMusicSpecialName.setText(item.getStrSpecialName());
        holder.tvWhichSong.setText(item.getStrId());
        holder.tvMusicTime.setText(item.getStrMusicTime());

        return convertView;
    }


    public static class ViewHolder {
        TextView tvWhichSong,tvMusicName,tvsingerName,tvMusicSpecialName,tvMusicTime;
        ImageView ivMusicDisc;
    }
}
