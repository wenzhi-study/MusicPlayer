package com.example.musicplayer.myAdapater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;

/**
 * @ProjectName: MusicPlayer
 * @Package: com.example.musicplayer.MyAdapater
 * @ClassName: MainViewPagerAdapater
 * @Description: java类作用描述
 * @Author: 作者名 王文治
 * @CreateDate: 2019/12/10 9:04
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/10 9:04
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MainViewPagerAdapater extends PagerAdapter {

    private Context context;
    private ArrayList<View> views;

    public MainViewPagerAdapater(Context context, ArrayList<View> views) {
        this.context = context;
        this.views = views;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(position));
    }
}
