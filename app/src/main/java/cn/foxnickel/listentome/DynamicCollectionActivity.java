package cn.foxnickel.listentome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.foxnickel.listentome.adapter.SocialAdapter;
import cn.foxnickel.listentome.bean.Dynamic;
import cn.foxnickel.listentome.utils.GetJsonFromServerTask;

public class DynamicCollectionActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SocialAdapter mSocialAdapter;
    private List<Dynamic> mDynamicList;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_collection);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回上级的箭头
        getSupportActionBar().setDisplayShowTitleEnabled(false);//将actionbar原有的标题去掉
        /*返回上级*/
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initRecycler();
    }

    private void initRecycler() {
        getDataFromServer();
        mRecyclerView = (RecyclerView) findViewById(R.id.dynamic_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mSocialAdapter = new SocialAdapter(mDynamicList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSocialAdapter);
    }

    private void getDataFromServer() {
        try {
            String str = new GetJsonFromServerTask().execute("http://www.foxnickel.cn:3000/community/dynamics").get();
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

    private String formatJsonString(String string) {
        String temp = string.replace("\\", "");
        String finalStr = temp.substring(1, temp.indexOf("]") + 1);
        return finalStr;
    }

}
