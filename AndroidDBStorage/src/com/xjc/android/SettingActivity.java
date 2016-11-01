package com.xjc.android;


import com.xjc.androiddbstorage.R;
import com.xjc.androiddbstorage.SqliteDBOperation;
import com.xjc.androiddbstorage.R.id;
import com.xjc.androiddbstorage.R.xml;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.ListView;
import android.widget.Toast;


public class SettingActivity extends PreferenceActivity
{
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		//得到一个Preference对象
		Preference pref = findPreference("silentmode");
		
		ListView listView = (ListView) findViewById(R.id.list_item);
		
		//设置事件监听
		pref.setOnPreferenceClickListener(new OnPreferenceClickListener()
		{
			
			public boolean onPreferenceClick(Preference preference)
			{
				
				//db open
				SqliteDBOperation dbOperation = new SqliteDBOperation(SettingActivity.this);
				dbOperation.openOrCreateDatabase();
				
				Toast.makeText(SettingActivity.this, preference.getTitle().toString(), Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}
}
