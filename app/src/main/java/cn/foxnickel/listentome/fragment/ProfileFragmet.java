package cn.foxnickel.listentome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.foxnickel.listentome.R;

/**
 * Created by Administrator on 2017/3/3.
 */

public class ProfileFragmet extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private View mRootView;
    private Toolbar mToolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.action_settings);
        return mRootView;
    }
}
