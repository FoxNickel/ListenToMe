package cn.foxnickel.listentome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.foxnickel.listentome.R;

/**
 * Created by Administrator on 2017/3/3.
 */

public class ListenFragmet extends Fragment {
    private View mRootView;
    private BGABanner mBanneer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_listen, container, false);
        mBanneer = (BGABanner) mRootView.findViewById(R.id.pic_banner);
        mBanneer.setData(R.drawable.pic1, R.drawable.pic2, R.drawable.pic3);
        return mRootView;
    }

}
