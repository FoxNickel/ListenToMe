package cn.foxnickel.listentome.fragment;

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
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> mFragmentList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*根布局*/
        mRootView = inflater.inflate(R.layout.fragment_listen, container, false);
        /*图片轮播*/
        mBanneer = (BGABanner) mRootView.findViewById(R.id.pic_banner);
        mBanneer.setData(R.drawable.pic6, R.drawable.pic6, R.drawable.pic6);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new ExerciseFragment());
        mFragmentList.add(new ExamFragment());

        ListenPagerAdapter listenPagerAdapter = new ListenPagerAdapter(getFragmentManager(), getContext(), mFragmentList);
        mTabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(listenPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return mRootView;
    }

}
