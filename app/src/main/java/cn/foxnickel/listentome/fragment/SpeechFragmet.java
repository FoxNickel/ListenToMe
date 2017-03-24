package cn.foxnickel.listentome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.SpeechTest;

/**
 * Created by Administrator on 2017/3/3.
 */

public class SpeechFragmet extends Fragment implements View.OnClickListener {

    private View mRootView;
    private BGABanner mBanner;
    private ImageView mEasyChat, mEasySchoolLife;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_speech, container, false);
        initPicBanner();
        initImageView();
        setListener();
        return mRootView;
    }

    private void setListener() {
        mEasyChat.setOnClickListener(this);
        mEasySchoolLife.setOnClickListener(this);
    }

    private void initImageView() {
        mEasyChat = (ImageView) mRootView.findViewById(R.id.speech_easy_chat);
        mEasySchoolLife = (ImageView) mRootView.findViewById(R.id.speech_easy_school_life);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speech_easy_chat:
                Intent intent = new Intent(getActivity(), SpeechTest.class);
                startActivity(intent);
                break;
            case R.id.speech_easy_school_life:
                break;
        }
    }
}
