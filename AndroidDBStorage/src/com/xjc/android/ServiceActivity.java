package com.xjc.android;


import com.xjc.androiddbstorage.R;
import com.xjc.androiddbstorage.R.id;
import com.xjc.androiddbstorage.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ServiceActivity extends Activity implements OnClickListener
{
	private Intent mIntent;
	private Context mContext;
	private Button button_start;
	private Button button_stop;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_layout);
		LinearLayout layout = (LinearLayout)findViewById(R.id.container);
		
		mContext = ServiceActivity.this;
		//动态添加Button控件
		 button_start = new Button(this);
		 button_start.setText("启动Sevice");
		 
		 button_stop = new Button(this);
		 button_stop.setText("停止Service");
		 
		 button_start.setOnClickListener(this);
		 button_stop.setOnClickListener(this);
		 layout.addView(button_start);
		 layout.addView(button_stop);
	}
	
	public void onClick(View v)
	{
		if (v == button_start)
		{
			mIntent = new Intent(ServiceActivity.this,MyService.class);
			mContext.startService(mIntent);
			Toast.makeText(this, "启动服务", Toast.LENGTH_SHORT).show();
		}
		else if(v == button_stop)
		{
			mIntent = new Intent(ServiceActivity.this,MyService.class);
			mContext.stopService(mIntent);
			Toast.makeText(this, "停止服务", Toast.LENGTH_SHORT).show();
		}
	}
}
