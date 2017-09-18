package feather.record.Other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import feather.record.Data.EnterInfo;

/**
 * Created by feather on 2017/9/15.
 */

public class DB_Helper extends SQLiteOpenHelper {

    //   資料表名稱
    final static String TABLE_NAME = "RecrodTable";

    //   欄位名稱
    final static String ID_FIELD = "_id";  //資料表流水號
    final static String ITEM = "ITEM";
    final static String YEAR = "YEAR";
    final static String MONTH = "MONTH";
    final static String DAY = "DAY";
    final static String NAME = "NAME";
    final static String OPTION = "OPTION";
    final static String MAONEY = "MONEY";
    final static String NOTE = "NOTE";

    public DB_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//                  建立資料表
        String sql = "create table " + TABLE_NAME + "(" +
                ID_FIELD + "  integer primary key autoincrement, " + //主鍵
                ITEM + " text not null , " + //不可為空
                YEAR + " text not null , " +
                MONTH + " text not null , " +
                DAY + " text not null , " +
                NAME + " text not null , " +
                OPTION + " text not null , " +
                MAONEY + " text not null , " +
                NOTE + " text) "; //可為空值

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//              （更新？
//                將資料表刪除
        db.execSQL("drop table if exists " + TABLE_NAME);
//                  再建立一次資料表
        onCreate(db);
    }

    public void Add(String item, String year, String month, String day,
                    String name, String option, String money, String note) {
//              增加
        SQLiteDatabase db = this.getWritableDatabase();
//              建值
        ContentValues values = new ContentValues();
        values.put(ITEM, item);
        values.put(YEAR, year);
        values.put(MONTH, month);
        values.put(DAY, day);
        values.put(NAME, name);
        values.put(OPTION, option);
        values.put(MAONEY, money);
        values.put(NOTE, note);
//              輸入資料庫  產生流水號
        long result = db.insert(TABLE_NAME, null, values);
//              每次都要記得關閉
        db.close();
    }

    public ArrayList<EnterInfo> Select(String Year, String Month) {

        ArrayList<EnterInfo> select_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        資料表
//        字寫 sql 語法
        Cursor c = db.rawQuery("select * from " + TABLE_NAME +
                " where " + YEAR + " = ? and " + MONTH + " = ? ", new String[]{Year, Month});
//        Cursor c = db.query(TABLE_NAME,
////                                欄位
//                new String[]{ID_FIELD, ITEM, YEAR, MONTH, DAY, NAME, OPTION, MAONEY, NOTE},
////                                  where、 where條件、groupBy、having與 orderBy
//                YEAR +"=" +Year ,null , null, null, null, null);
//        總數量
        int rows_num = c.getCount();

//        必須先移到第一個才能開始列表
        c.moveToFirst();

//        一行一行建立資料列
        for (int x = 0; x < rows_num; x++) {
            EnterInfo info = new EnterInfo();
//            按照查詢順序
            info.setId(c.getLong(0));
            info.setItem(c.getString(1));
            info.setYear(c.getString(2));
            info.setMonth(c.getString(3));
            info.setDay(c.getString(4));
            info.setName(c.getString(5));
            info.setOption(c.getString(6));
            info.setMoney(c.getString(7));
            info.setNote(c.getString(8));
//            放入列
            select_list.add(info);
//            需要自己換下筆資料
            c.moveToNext();
        }
        return select_list;
    }

    public void Delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, null, null);
        db.close();
    }

}
