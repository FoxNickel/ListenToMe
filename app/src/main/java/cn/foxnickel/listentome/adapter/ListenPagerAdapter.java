package cn.foxnickel.listentome.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/5.
 */


public class ListenPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 2;
    private Context mContext;
    private List<Fragment> mFragmentList;
    private String[] mPagerTitles = {"练习", "考试"};

    public ListenPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList) {
        super(fm);
        mContext = context;
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPagerTitles[position];
    }
}
