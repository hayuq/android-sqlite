package com.xjc.androiddbstorage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class UserListActivity extends ListActivity {

	private final int USERDIALOG_EDIT = 1;
	private final int USERDIALOG_DEL = 2;
	private final int OP_ADD = 3;
	private final int OP_EDIT = 4;
	private final int OP_DEL = 5;
	private final String OP_ITEMS = "1";
	private final String OP_VALUE = "2";
	private SqliteDBOperation mDbOperation;
	private Cursor mCursor;
	
	private MySqliteHelper sqliteHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mDbOperation = new SqliteDBOperation(this);
		// 建立数据库连接
		mDbOperation.openOrCreateDatabase();
		displayUser();
		registerForContextMenu(getListView());// 给当前ListView注册上下文菜单ContextMenu
		
		/*使用SQLiteOpenHelper类*/
		// 创建了一个MySqliteHelper对象，只执行这句话是不会创建或打开数据库连接的  
		sqliteHelper = new MySqliteHelper(this, mDbOperation.DATABASE_NAME);
		sqliteHelper.getWritableDatabase();
	}

	/**
	 * 显示数据库数据到ListView界面
	 */
	private void displayUser() {
		// 建立数据源,查找出用户
		mCursor = mDbOperation.selectAll();
		if (mCursor != null && mCursor.getCount() >= 0) {
			// 绑定数据源与adapter
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
					android.R.layout.simple_list_item_2, mCursor, new String[] {
							mDbOperation.COLUMN_USERNAME, mDbOperation.COLUMN_USERADDRESS },
					new int[] { android.R.id.text1, android.R.id.text2 });

			// 绑定adapter到ListView界面
			this.setListAdapter(adapter);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_userdialog, null);

		builder.setIcon(R.drawable.ic_launcher).setView(dialogView);
		if (id == USERDIALOG_EDIT) { // 添加修改对话框
			if (args.getInt(OP_ITEMS) == OP_ADD) { // 添加

				builder.setTitle("添加联系人")
						.setPositiveButton("保存", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {

								AlertDialog alertDialog = (AlertDialog) dialog;
								EditText editName = (EditText) alertDialog.findViewById(R.id.editText_UserName);
								EditText editAddress = (EditText) alertDialog.findViewById(R.id.editText_UserAddress);

								User user = new User();
								user.setUserName(editName.getText().toString());
								user.setUserAddress(editAddress.getText().toString());
								if (mDbOperation.insertUser(user) != -1) {
									dialog.dismiss(); // 关闭当前dialog
									Toast.makeText(UserListActivity.this, "添加成功",Toast.LENGTH_SHORT).show();
									displayUser();
								}
								editName.setText(null);
								editAddress.setText(null);
							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss(); // 关闭当前dialog
							}
						});
				return builder.create();
			}
			else if (args.getInt(OP_ITEMS) == OP_EDIT) { // 修改
				mCursor.moveToPosition(args.getInt(OP_VALUE));
				final int _id = mCursor.getInt(0);

				//获取当前值到EditText
				EditText editName = (EditText) dialogView.findViewById(R.id.editText_UserName);
				EditText editAddress = (EditText) dialogView.findViewById(R.id.editText_UserAddress);
				String username = mCursor.getString(mCursor.getColumnIndex(mDbOperation.COLUMN_USERNAME));
				String useraddress = mCursor.getString(mCursor.getColumnIndex(mDbOperation.COLUMN_USERADDRESS));
				editName.setText(username);
				editAddress.setText(useraddress);
				
				builder.setTitle("修改联系人")
						.setPositiveButton("保存", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {

								AlertDialog alertDialog = (AlertDialog) dialog;
								EditText editName = (EditText) alertDialog.findViewById(R.id.editText_UserName);
								EditText editAddress = (EditText) alertDialog.findViewById(R.id.editText_UserAddress);

								User user = new User();
								user.setUserName(editName.getText().toString());
								user.setUserAddress(editAddress.getText().toString());
								if (mDbOperation.updateUser(user, _id) > 0) {
									alertDialog.dismiss(); // 关闭当前dialog
									Toast.makeText(UserListActivity.this, "修改成功",Toast.LENGTH_SHORT).show();
									displayUser();
								}
							}

						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss(); // 关闭当前dialog
							}
						});
				return builder.create();
			}
		}
		else if (id == USERDIALOG_DEL) { // 删除对话框
			mCursor.moveToPosition(args.getInt(OP_VALUE));
			final int _id = mCursor.getInt(0);

			AlertDialog dialog = new AlertDialog.Builder(this).setTitle("删除联系人")
					.setIcon(R.drawable.ic_launcher).setMessage("确定删除该联系人吗？")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

							if (mDbOperation.deleteUser(_id) > 0) {
								dialog.dismiss(); // 关闭当前dialog
								Toast.makeText(UserListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
								displayUser();
							}
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss(); // 关闭当前dialog
						}
					}).create();
			return dialog;
		}
		return super.onCreateDialog(id, args);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(1, 1, 1, "添加联系人");
		menu.add(2, 2, 2, "查询联系人");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == 1) { // 添加用户的对话框
			Bundle bundle = new Bundle();
			bundle.putInt(OP_ITEMS, OP_ADD);
			onCreateDialog(USERDIALOG_EDIT, bundle).show();
			// showDialog(USERDIALOG_EDIT);
		}
		else if (id == 2) { // 查询
			displayUser();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

		menu.add(1, 11, 1, "修改");
		menu.add(2, 12, 2, "删除");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		Bundle bundle = new Bundle();
		if (itemId == 11) { // 修改
			bundle.putInt(OP_ITEMS, OP_EDIT);
			bundle.putInt(OP_VALUE, menuInfo.position);
			onCreateDialog(USERDIALOG_EDIT, bundle).show();
		}
		else if (itemId == 12) { // 删除
			bundle.putInt(OP_ITEMS, OP_DEL);
			bundle.putInt(OP_VALUE, menuInfo.position);
			onCreateDialog(USERDIALOG_DEL, bundle).show();
		}
		return super.onContextItemSelected(item);
	}

}
