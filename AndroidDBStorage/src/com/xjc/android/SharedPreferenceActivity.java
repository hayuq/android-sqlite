package com.xjc.android;


import com.xjc.androiddbstorage.R;
import com.xjc.androiddbstorage.SqliteDBOperation;
import com.xjc.androiddbstorage.User;
import com.xjc.androiddbstorage.R.id;
import com.xjc.androiddbstorage.R.layout;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;

public class SharedPreferenceActivity extends ActionBarActivity {

	private LayoutParams layoutParams;
	private SharedPreferences mSharedPreferences;
	private CheckBox checkBox1;
	private EditText editText;
	private LinearLayout linearLayout;
	private ListView listView;
	private SqliteDBOperation mDbOperation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.id.container);

		linearLayout = (LinearLayout) findViewById(R.layout.fragment_main);
		listView = (ListView) findViewById(R.id.list_item);
		layoutParams = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

		linearLayout.setBackgroundColor(android.graphics.Color.GREEN);
		listView.setBackgroundColor(android.graphics.Color.BLACK);
		linearLayout.addView(listView, layoutParams);
		setContentView(linearLayout);

		linearLayout.addView(listView);

		mDbOperation.insertUser(new User("xjc", "ChengDu"));
		mDbOperation.closeDatabase();

		// setContentView(R.layout.fragment_main); // 得到preference对象
		mSharedPreferences = getSharedPreferences("xjc", MODE_PRIVATE);

		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		editText = (EditText) findViewById(R.id.editText1);

		// 判断是否得到了preference对象
		if (mSharedPreferences != null) {
			checkBox1.setChecked(mSharedPreferences.getBoolean("wifitoggle", false));
			editText.setText(mSharedPreferences.getString("yourname", ""));
		}
		else {
			Log.i("xjclog", "no xml");
		}
		Button button1 = (Button) findViewById(R.id.btn_Save);
		// button1.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// SharedPreferences.Editor editor = mSharedPreferences.edit();
		//
		// editor.putBoolean("wifitoggle", checkBox1.isChecked());
		// editor.putString("yourname", editText.getText().toString());
		// editor.commit(); Toast.makeText(SharedPreferenceActivity.this,
		// "修改保存",
		// 3000).show();
		// }
		//
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

	}
}
