package com.xjc.android;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service
{
	private static final String TAG = "TestService";
	private MyBinder mBinder = new MyBinder();
	public class MyBinder extends Binder
	{
		MyService getService()
		{
			return MyService.this;
		}
	}
	public void helloService()
	{
		for (int i = 0; i < 100; i++)
		{
			try
			{
				Thread.sleep(1000);
				Log.i("xjclog", i+"");
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO Auto-generated method stub
		//新加一个线程，来启动服务
		new Thread(new Runnable()
		{
			public void run()
			{
				// TODO Auto-generated method stub
				helloService();						
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		Log.i(TAG, "----start IBinder-----");
		return mBinder;
	}

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("xjclog", "Service destroy");
	}

	@Override
	public boolean onUnbind(Intent intent)
	{
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

}
