package cn.foxnickel.listentome;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

import cn.foxnickel.listentome.dao.ListenToMeDataBaseHelper;
import cn.foxnickel.listentome.utils.Contract;

/**
 * Created by Administrator on 2017/3/26.
 */

public class TestDB extends AndroidTestCase {

    public void testCreateTable() {
        SQLiteDatabase db = new ListenToMeDataBaseHelper(mContext, "ListenToMeDB.db", null, 1).getWritableDatabase();
        assertEquals("数据库未创建成功", true, db.isOpen());

        HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add(Contract.USER_TABLE_NAME);

        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("数据库表为空",
                cursor.moveToFirst());

        do {
            tableNameHashSet.remove(cursor.getString(0));
        } while (cursor.moveToNext());

        assertTrue("表未创建成功", tableNameHashSet.isEmpty());
    }
}
