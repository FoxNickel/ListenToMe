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

public class SpeechFragmet extends Fragment {

    private View mRootView;
    private BGABanner mBanner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_speech, container, false);
        initPicBanner();
        return mRootView;
    }

    /*初始化图片Banner*/
    private void initPicBanner() {
        new Runnable() {
            @Override
            public void run() {
                mBanner = (BGABanner) mRootView.findViewById(R.id.pic_banner);
                mBanner.setData(R.drawable.pic1, R.drawable.pic2, R.drawable.pic3);
            }
        }.run();

    }

}
