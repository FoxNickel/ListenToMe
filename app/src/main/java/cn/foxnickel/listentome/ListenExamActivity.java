package cn.foxnickel.listentome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.foxnickel.listentome.adapter.ListenExamAdater;
import cn.foxnickel.listentome.bean.HearingIssueBean;
import cn.foxnickel.listentome.bean.ListenExamBean;
import cn.foxnickel.listentome.bean.SocialBean;

/**
 * Created by Night on 2017/3/24.
 * Desc:
 */

public class ListenExamActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListenExamAdater mExamAdater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_exam);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_question);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<HearingIssueBean> list = new ArrayList<>();
        list.add(new HearingIssueBean(0, 0, 0, "sdfsdfsdf", "sdsdfsdf", "sdfsdfsdfsdf", 0, 0, 0));
        list.add(new HearingIssueBean(0, 0, 0, "sdfsdfsdf", "sdsdfsdf", "sdfsdfsdfsdf", 0, 0, 0));
        list.add(new HearingIssueBean(0, 0, 0, "sdfsdfsdf", "sdsdfsdf", "sdfsdfsdfsdf", 0, 0, 0));
        list.add(new HearingIssueBean(0, 0, 0, "sdfsdfsdf", "sdsdfsdf", "sdfsdfsdfsdf", 0, 0, 0));
        mExamAdater = new ListenExamAdater(list, this);
        mRecyclerView.setAdapter(mExamAdater);
    }
}
