package cn.foxnickel.listentome.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.foxnickel.listentome.utils.Contract;

/**
 * Created by Night on 2017/3/18.
 * Desc:
 */

public class ListenToMeDataBaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER_TABLE = "create table " + Contract.USER_TABLE_NAME + " (" +
            Contract.User_COLUMN_ID + " integer primary key," +
            Contract.USER_COLUMN_NAME + " text, " +
            Contract.User_COLUMN_PHONE + " text, " +
            Contract.User_COLUMN_EMAIL + " text, " +
            Contract.User_COLUMN_PWD + " text not null, " +
            Contract.User_COLUMN_EXP + " integer not null)";

    public ListenToMeDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);//创建User表
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
