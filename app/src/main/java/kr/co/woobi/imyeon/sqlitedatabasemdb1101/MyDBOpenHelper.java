package kr.co.woobi.imyeon.sqlitedatabasemdb1101;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE awe_country (_id INTEGER PRIMARY KEY AUTOINCREMENT, country TEXT, city TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       //Droop 구문 쓰기
        db.execSQL("DROP TABLE awe_country ;");
        db.execSQL("CREATE TABLE awe_country(pkid TEXT PRIMARY KEY, country TEXT, city TEXT);");
        db.execSQL("CREATE TABLE awe_country_visitedcount(fkid TEXT);");

    }
}
