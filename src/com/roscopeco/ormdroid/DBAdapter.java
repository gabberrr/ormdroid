package com.roscopeco.ormdroid;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by dmitriy on 14.01.15.
 */
public class DBAdapter {

    final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private static String name;

    private static int version = 1;

    public DBAdapter(Context ctx,String name) {
        this.context = ctx;
        this.name = name;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {}

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.i("ORMDROID", "onUpgrade oldVersion = " + oldVersion + " newVersion = " + newVersion);

            Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            Log.i("ORMDROID", "cursor size = "+c.getCount());
            while(c.moveToNext()){
                String s = c.getString(0);
                Log.i("ORMDROID", "table = "+s);
                if(!s.equals("android_metadata")){
                    Log.i("ORMDROID", "DROP TABLE IF EXISTS "+s);
                    db.execSQL("DROP TABLE IF EXISTS "+s);
                   // Log.i("DATABASETABLES", " table name = " + s);
                }
            }
            c.close();
        }
    }

    public SQLiteDatabase open(String path) throws SQLException {
        db = DBHelper.getWritableDatabase();
        db = db.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY | SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        db.enableWriteAheadLogging();
        return db;
    }

    public static void setVersion(int version){
        DBAdapter.version = version;
    }
}

