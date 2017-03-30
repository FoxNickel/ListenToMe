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
    List<ListenExamBean> mListenExamBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_collection);
        initToolbar();
        mListenExamBeen = new ArrayList<>();
        mListenExamBeen.add(new ListenExamBean(Constants.QUESTION_IMAGE_PATH_CET4_2013_6_2, "大学英语四级", "2013年六月英语四级听力真题", "成绩 86 分"));
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
        mRecyclerView = (RecyclerView) findViewById(R.id.listen_collection_recycler);
        mListenCollectionRecyclerAdapter = new ListenCollectionRecyclerAdapter(this, mListenExamBeen);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mListenCollectionRecyclerAdapter);
        mListenCollectionRecyclerAdapter.setOnItemClickListener(new ListenCollectionRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemCkick(View V, int position) {
                Toast.makeText(ListenCollectionActivity.this, "You Clicked Item " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
