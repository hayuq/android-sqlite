package com.xjc.androiddbstorage;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * SQLite数据库操作类
 */
public class SqliteDBOperation {

	// 声明一个SQLiteDatabase对象
	private SQLiteDatabase mDatabase;
	private Context mContext;

	final String DATABASE_NAME = "androidSqliteDB";
	final String TABLE_NAME = "users";
	final String COLUMN_ID = "_id";
	final String COLUMN_USERNAME = "userName";
	final String COLUMN_USERADDRESS = "userAddress";

	public SqliteDBOperation(Context context) {
		mContext = context;
	}

	/**
	 * 创建表users
	 */
	public void createTable() {

		try {
			String sSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
					+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT," 
					+ COLUMN_USERNAME + " VARCHAR(50) NOT NULL,"
					+ COLUMN_USERADDRESS + " VARCHAR(50) NOT NULL)";
			mDatabase.execSQL(sSql);
			ContentValues cValues = new ContentValues();
			cValues.put(COLUMN_USERNAME, "xjc");
			cValues.put(COLUMN_USERADDRESS, "ChengDu");
			mDatabase.insert(TABLE_NAME, "", cValues);
		}
		catch (Exception e) {
			Log.i("xjcSQL", "SQL语句无效");
		}
	}

	/**
	 * 建立及打开数据库
	 */
	public void openOrCreateDatabase() {

		// 建立数据库连接
		mDatabase = mContext.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE, null);

		// 建立一张数据表users
//		String firstTableName = mDatabase.findEditTable(TABLE_NAME);
//		if (firstTableName == null || firstTableName.equals("")) {

			try {
				String sSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
						+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT," 
						+ COLUMN_USERNAME + " VARCHAR(50) NOT NULL,"
						+ COLUMN_USERADDRESS + " VARCHAR(50) NOT NULL)";
				mDatabase.execSQL(sSql);
				//向数据库中添加一条数据
//				ContentValues cValues = new ContentValues();
//				cValues.put(COLUMN_USERNAME, "xjc");
//				cValues.put(COLUMN_USERADDRESS, "ChengDu");
//				mDatabase.insert(TABLE_NAME, "", cValues);
//				
//				Log.i("xjclog", "向表中添加数据成功");
			}
			catch (Exception e) {
				Log.i("xjclog", "SQL语句无效");
			}
//		}
//		else {
//			Log.i("xjclog", "该表已存在");
			
			//向数据库中添加一条数据
//			ContentValues cValues = new ContentValues();
//			cValues.put(COLUMN_USERNAME, "xjc");
//			cValues.put(COLUMN_USERADDRESS, "ChengDu");
//			mDatabase.insert(TABLE_NAME, "", cValues);
//			
//			Log.i("xjclog", "向表中添加数据成功");
//		}
		
	}

	/**
	 * 查询数据库所有数据
	 * @return Cursor对象
	 */
	public Cursor selectAll() {
		if (mDatabase != null) {
			return mDatabase.query(TABLE_NAME,new String[] { COLUMN_ID, COLUMN_USERNAME, COLUMN_USERADDRESS }, 
					null, null, null, null,null);
		}
		return null;
	}

	/**
	 * 按条件查询用户数据
	 * @param sql
	 * @param selectionArgs
	 * @return User列表
	 */
	public List<User> selectUser(String sql, String[] selectionArgs) {
		List<User> users=new ArrayList<User>();
		if (mDatabase != null) {
			Cursor cursor = mDatabase.rawQuery(sql, selectionArgs);
			while (cursor.moveToNext()) {
				String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
				String useraddress = cursor.getString(cursor.getColumnIndex(COLUMN_USERADDRESS));
				users.add(new User(username,useraddress));
			}
			return users;
		}
		return null;
	}

	/**
	 * 插入用户数据
	 * @param user 用户实例
	 * @return row ID 插入数据的行号
	 */
	public long insertUser(User user) {
		if (mDatabase != null && user != null) {
			// 向表中插入一条记录
			if (!user.getUserName().equals("") && !user.getUserAddress().equals("")) {
				ContentValues values = new ContentValues();
				values.put(COLUMN_USERNAME, user.getUserName());
				values.put(COLUMN_USERADDRESS, user.getUserAddress());
				return mDatabase.insert(TABLE_NAME, "", values);
			}
			else {
				new AlertDialog.Builder(mContext).setMessage("不能为空").setTitle("警告").setPositiveButton("确定", new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create().show();
			}
			// 两种方式均可
//			 String sSql = "INSERT INTO "+TABLE_NAME+'('+COLUMN_USERNAME+","+COLUMN_USERADDRESS+") VALUES ('xjc','Sichuan')";
//			 mDatabase.execSQL(sSql);
		}
		return -1;
	}

	/**
	 * 更新数据库中用户数据
	 * @return 受影响的行数
	 */
	public int updateUser(User user, int id) {
		if (mDatabase != null && user != null) {
			String where = COLUMN_ID + " = ?";
			String[] whereValue = { String.valueOf(id) };
			
			if (!user.getUserName().equals("") && !user.getUserAddress().equals("")) {
				ContentValues cv = new ContentValues();
				cv.put(COLUMN_USERNAME, user.getUserName());
				cv.put(COLUMN_USERADDRESS, user.getUserAddress());
				return mDatabase.update(TABLE_NAME, cv, where, whereValue);
			}
			else {
				new AlertDialog.Builder(mContext).setMessage("不能为空").setTitle("警告").setPositiveButton("确定", new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create().show();
			}
		}
		return -1;
	}

	/**
	 * 根据id删除数据库中数据
	 * @param id  id
	 * @return 受影响的行数
	 */
	public int deleteUser(int id) {
		if (mDatabase != null && id > 0) {
			return mDatabase.delete(TABLE_NAME, COLUMN_ID + " = ?",new String[] { String.valueOf(id) });
		}
		return -1;
	}

	/**
	 * 关闭数据库连接
	 */
	public void closeDatabase() {
		// 关闭数据库连接
		if (mDatabase != null && mDatabase.isOpen()) {
			mDatabase.close();
			mDatabase = null;
		}
	}
}
