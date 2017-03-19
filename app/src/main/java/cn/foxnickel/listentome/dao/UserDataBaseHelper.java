package cn.foxnickel.listentome.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.foxnickel.listentome.utils.Constraint;

/**
 * Created by Night on 2017/3/18.
 * Desc:
 */

public class UserDataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table " + Constraint.USER_TABLE_NAME + " (" +
            Constraint.User_COLUMN_ID + " integer primary key," +
            Constraint.User_COLUMN_Name + " text, " +
            Constraint.User_COLUMN_PHONE + " text, " +
            Constraint.User_COLUMN_EMAIL + " text, " +
            Constraint.User_COLUMN_PWD + " text, " +
            Constraint.User_COLUMN_EXP + " integer)";

    public UserDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
