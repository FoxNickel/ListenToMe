package cn.foxnickel.listentome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.foxnickel.listentome.adapter.ListenCollectionRecyclerAdapter;
import cn.foxnickel.listentome.bean.ListenExamBean;
import cn.foxnickel.listentome.utils.Constants;

public class ListenCollectionActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListenCollectionRecyclerAdapter mListenCollectionRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_collection);
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        /*toolBar相关设置*/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        List<ListenExamBean> ListenExamBeanList = new ArrayList<>();
        ListenExamBeanList.add(new ListenExamBean(Constants.QUESTION_IMAGE_PATH, "试卷名称", "试卷描述", "成绩 96 分"));
        ListenExamBeanList.add(new ListenExamBean(Constants.QUESTION_IMAGE_PATH, "试卷名称2", "试卷描述2", "成绩 97 分"));
        ListenExamBeanList.add(new ListenExamBean(Constants.QUESTION_IMAGE_PATH, "试卷名称3", "试卷描述3", "成绩 98 分"));
        mRecyclerView = (RecyclerView) findViewById(R.id.listen_collection_recycler);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mListenCollectionRecyclerAdapter = new ListenCollectionRecyclerAdapter(this, ListenExamBeanList);
        mRecyclerView.setAdapter(mListenCollectionRecyclerAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mListenCollectionRecyclerAdapter.setOnItemClickListener(new ListenCollectionRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemCkick(View V, int position) {
                Toast.makeText(ListenCollectionActivity.this, "You Clicked Item " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
