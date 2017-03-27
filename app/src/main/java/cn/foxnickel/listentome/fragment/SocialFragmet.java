package cn.foxnickel.listentome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.adapter.SocialAdapter;
import cn.foxnickel.listentome.bean.Dynamic;
import cn.foxnickel.listentome.utils.GetJsonFromServerTask;
import cn.foxnickel.listentome.utils.OkHttpManager;

/**
 * Created by Administrator on 2017/3/3.
 */

public class SocialFragmet extends Fragment {
    private View mRootView;
    private Toolbar mToolbar;
    private SocialAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private OkHttpManager mOkHttpManager = new OkHttpManager();
    private final String TAG = getClass().getSimpleName();
    List<Dynamic> mDynamicList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_social, container, false);

        initView();
        getDataFromServer();

        mAdapter = new SocialAdapter(mDynamicList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SocialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showShortToast("sgfsdgs");
                Log.e("TAG","position "+position);
            }
        });
        return mRootView;
    }

    private void getDataFromServer() {
        try {
            String str = new GetJsonFromServerTask().execute("http://www.foxnickel.cn/web.json").get();
            Gson gson = new Gson();
            String finalStr = formatJsonString(str);
            Dynamic[] dynamics = gson.fromJson(finalStr, Dynamic[].class);
            for (int i = 0; i < dynamics.length; i++) {
                Log.i(TAG, "doInBackground: dynamics: " + dynamics[i].toString());
            }
            mDynamicList = Arrays.asList(dynamics);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.action_search);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_social);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private String formatJsonString(String string) {
        String temp = string.replace("\\", "");
        String finalStr = temp.substring(1, temp.indexOf("]") + 1);
        return finalStr;
    }

}
