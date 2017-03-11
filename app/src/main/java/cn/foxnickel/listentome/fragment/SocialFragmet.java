package cn.foxnickel.listentome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.adapter.SocialAdapter;
import cn.foxnickel.listentome.bean.SocialBean;
import cn.foxnickel.listentome.utils.Constants;

/**
 * Created by Administrator on 2017/3/3.
 */

public class SocialFragmet extends Fragment {
    private View mRootView;
    private Toolbar mToolbar;
    private SocialAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_social, container, false);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.action_search);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_social);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<SocialBean> list = new ArrayList<>();
        list.add(new SocialBean(Constants.PATH, "Night", "View wrapper = inflater.inflate(R.layout.fragment_paint, container, false);\n" +
                "46 mRecyclerView = (RecyclerView)wrapper.findViewById(R.id.paint_recycle_view);" +
                "47 mLayoutManager = new LinearLayoutManager(getActivity());" +
                "48  mRecyclerView.setLayoutManager(mLayoutManager); //绑上列表管理器", "88", "88", "88"));
        list.add(new SocialBean(Constants.PATH, "Night", "sdfsdfsdfsdfsdfsdf", "88", "88", "88"));
        list.add(new SocialBean(Constants.PATH, "Night", "View wrapper = inflater.inflate(R.layout.fragment_paint, container, false);\n" +
                "46 mRecyclerView = (RecyclerView)wrapper.findViewById(R.id.paint_recycle_view);" +
                "47 mLayoutManager = new LinearLayoutManager(getActivity());" +
                "48  mRecyclerView.setLayoutManager(mLayoutManager); //绑上列表管理器", "88", "88", "88"));
        list.add(new SocialBean(Constants.PATH, "Night", "View wrapper = inflater.inflate(R.layout.fragment_paint, container, false);\n" +
                "46 mRecyclerView = (RecyclerView)wrapper.findViewById(R.id.paint_recycle_view);" +
                "47 mLayoutManager = new LinearLayoutManager(getActivity());" +
                "48  mRecyclerView.setLayoutManager(mLayoutManager); //绑上列表管理器", "88", "88", "88"));
        mAdapter = new SocialAdapter(list, getActivity());
        mAdapter.setOnItemClickListener(new SocialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return mRootView;
    }

}
