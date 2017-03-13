package cn.foxnickel.listentome.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.adapter.ListenPagerAdapter;
import cn.foxnickel.listentome.fragment.subfragment.ExamFragment;
import cn.foxnickel.listentome.fragment.subfragment.ExerciseFragment;

/**
 * Created by Administrator on 2017/3/3.
 */

public class ListenFragmet extends Fragment {
    private View mRootView;
    private BGABanner mBanneer;
    private List<Fragment> mFragmentList;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_listen, container, false);
        initPicBanner();
        initViewPager();
        return mRootView;
    }

    /*初始化图片Banner*/
    private void initPicBanner() {
        new Runnable() {
            @Override
            public void run() {
                mBanneer = (BGABanner) mRootView.findViewById(R.id.pic_banner);
                mBanneer.setData(R.drawable.pic4, R.drawable.pic4, R.drawable.pic6);
            }
        }.run();

    }

    private void initViewPager() {
        new Asy().execute();

    }
    class Asy extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mFragmentList = new ArrayList<>();
            mViewPager = (ViewPager) mRootView.findViewById(R.id.viewPager);
            mTabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);
            mTabLayout.setupWithViewPager(mViewPager);
        }

        @Override
        protected void onPostExecute(Void Void) {
            super.onPostExecute(Void);
            ListenPagerAdapter listenPagerAdapter = new ListenPagerAdapter(getChildFragmentManager(), getContext(), mFragmentList);
            mViewPager.setAdapter(listenPagerAdapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mFragmentList.add(new ExerciseFragment());
            mFragmentList.add(new ExamFragment());
            return null;
        }
    }
}


