package com.xjc.androiddbstorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 自定义SQLiteHelper
 */
public class MySqliteHelper extends SQLiteOpenHelper {

	public final String TABLE_NAME = "users";
	final String COLUMN_ID = "_id";
	final String COLUMN_USERNAME = "userName";
	final String COLUMN_USERADDRESS = "userAddress";
	private final static int VERSION = 1;
	
	public MySqliteHelper(Context context,String name) {
		super(context, name, null, VERSION);
	}

	public MySqliteHelper(Context context, String name, int version){  
        super(context,name,null,version);
    }
	
	public MySqliteHelper(Context context, String name, CursorFactory factory, int version) {
		super(context,name,factory,version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ COLUMN_USERNAME + " VARCHAR(50) NOT NULL,"
				+ COLUMN_USERADDRESS + " VARCHAR(50) NOT NULL)";
		db.execSQL(sSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("MySqliteHelper.onUpgrade()");
	}

}
