package cn.foxnickel.listentome;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.foxnickel.listentome.adapter.WordAdapter;
import cn.foxnickel.listentome.bean.Word;
import cn.foxnickel.listentome.dao.ListenToMeDataBaseHelper;

/**
 * Created by Night on 2017/3/29.
 * Desc:
 */

public class MyWordActivity extends AppCompatActivity {
    public RecyclerView mRecyclerView;
    public static List<Word> mList;
    private RecyclerView.LayoutManager mLayoutManager;
    public static WordAdapter mWordAdapter;
    private ListenToMeDataBaseHelper mDataBaseHelper;
    private int markIndex;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_word);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_word);
        mList = new ArrayList<>();
        mDataBaseHelper = new ListenToMeDataBaseHelper(this, "ListenToMeDB.db", null, 1);
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select WordName from Word where WordId=" +
                "(select WordId from WordCollection where WorkMark=?)", new String[]{"1"});
        String markWordName = null;
        if (cursor.moveToFirst()) {
            markWordName = cursor.getString(cursor.getColumnIndex("WordName"));
        }
        cursor = db.rawQuery("select * from Word ", null);
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                String wordName = cursor.getString(cursor.getColumnIndex("WordName"));
                String wordPhonetic = cursor.getString(cursor.getColumnIndex("WordPhoneticText"));
                String wordExplain = cursor.getString(cursor.getColumnIndex("WordExplain"));
                String wordAudio = cursor.getString(cursor.getColumnIndex("WordPhoneticAudio"));
                Word w = new Word(wordName, wordPhonetic, wordAudio, wordExplain);
                if (wordName.equals(markWordName)) {
                    w.setIsMark(1);
                    markIndex = index;
                }
                mList.add(w);
                index++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        mWordAdapter = new WordAdapter(mList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mWordAdapter);

        MoveToPosition((LinearLayoutManager) mLayoutManager, markIndex);
    }

    public static void MoveToPosition(LinearLayoutManager manager, int n) {
        manager.scrollToPositionWithOffset(n, 0);
        manager.setStackFromEnd(true);
    }
}
