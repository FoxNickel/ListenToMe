package cn.foxnickel.listentome.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.adapter.SocialAdapter;
import cn.foxnickel.listentome.bean.Dynamic;
import cn.foxnickel.listentome.dao.ListenToMeDataBaseHelper;
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
    private SQLiteDatabase mDb;
    private ListenToMeDataBaseHelper mListenToMeDataBaseHelper;

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
        mListenToMeDataBaseHelper = new ListenToMeDataBaseHelper(getContext(), "ListenToMeDB.db", null, 2);
        //getDataFromServer();
        mDynamicList = getDataFromDB();
        for (Dynamic dynamic : mDynamicList) {
            Log.i(TAG, "onCreateView: dynamics: " + dynamic.toString());
        }
        mAdapter = new SocialAdapter(mDynamicList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SocialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("TAG","position "+position);
            }
        });
        return mRootView;
    }

    private List<Dynamic> getDataFromDB() {
        mDb = mListenToMeDataBaseHelper.getWritableDatabase();
        Cursor cursor = mDb.rawQuery("select * from dynamic", null);
        List<Dynamic> dynamics = cursorToList(cursor);
        return dynamics;
    }

    private List<Dynamic> cursorToList(Cursor cursor) {
        List<Dynamic> dynamics = new ArrayList<>();
        while (cursor.moveToNext()) {
            Dynamic dynamic = new Dynamic();
            dynamic.setDSId(cursor.getInt(cursor.getColumnIndex("dsid")));
            dynamic.setUserId(cursor.getInt(cursor.getColumnIndex("userid")));
            dynamic.setDSContent(cursor.getString(cursor.getColumnIndex("dscontent")));
            dynamic.setDSLike(cursor.getInt(cursor.getColumnIndex("dslike")));
            dynamic.setDSDate(cursor.getString(cursor.getColumnIndex("dsdate")));
            dynamic.setUserName(cursor.getString(cursor.getColumnIndex("username")));
            dynamics.add(dynamic);
        }
        cursor.close();
        return dynamics;
    }
    private void getDataFromServer() {
        try {
            String str = new GetJsonFromServerTask().execute("http://www.foxnickel.cn:3000/community/dynamics").get();
            Gson gson = new Gson();
            String finalStr = formatJsonString(str);
            Dynamic[] dynamics = gson.fromJson(finalStr, Dynamic[].class);
            mDb = mListenToMeDataBaseHelper.getWritableDatabase();
            for (int i = 0; i < dynamics.length; i++) {
                Log.i(TAG, "doInBackground: dynamics: " + dynamics[i].toString());
                String insertData = "insert into dynamic values(" + dynamics[i].getDSId() + "," + dynamics[i].getUserId() + ",'" + dynamics[i].getDSContent() + "'," + dynamics[i].getDSLike() + ",'" + dynamics[i].getDSDate() + "','" + dynamics[i].getUserName() + "')";
                Log.i(TAG, "getDataFromServer: insertData: " + insertData);
                mDb.execSQL(insertData);
            }
            //mDynamicList = Arrays.asList(dynamics);
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
