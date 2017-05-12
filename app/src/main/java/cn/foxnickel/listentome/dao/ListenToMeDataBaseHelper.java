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
    public static final String CREATE_HEARINGEXAM_TABLE = "create table HearingExam(" +
            "UserId integer," +
            "ExamId integer," +
            "ExamScore integer," +
            "ExamBeginTime text," +
            "ExamDuration text," +
            "primary key(UserId,ExamId))";

    public static final String CREATE_WORD_COLLECTION = "create table WordCollection(" +
            "UserId integer," +
            "WordId integer," +
            "WorkMark integer," +
            "primary key(UserId,WordId))";
    public static final String CREATE_WORD = "create table Word(" +
            "WordId integer primary key autoincrement," +
            "WordName text," +
            "WordPhoneticText text," +
            "WordExplain text," +
            "WordPhoneticAudio text" +
            ")";

    private final String CREATE_TABLE_DYNAMIC = "create table dynamic (" +
            "dsid integer, " +
            "userid integer, " +
            "dscontent text, " +
            "dslike integer, " +
            "dsdate text, " +
            "username text)";

    public ListenToMeDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);//创建User表
        sqLiteDatabase.execSQL(CREATE_HEARINGEXAM_TABLE);//创建听力考试表
        sqLiteDatabase.execSQL(CREATE_WORD_COLLECTION);//创建单词收藏表
        sqLiteDatabase.execSQL(CREATE_WORD);//创建单词表

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CREATE_TABLE_DYNAMIC);
    }
}
