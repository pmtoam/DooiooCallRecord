package com.dooioo.call.record;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.dooioo.tools.Logger;

public class CoreService extends Service
{

	private final String TAG = "CoreService";

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		Logger.e(TAG, "onCreate()");
		
		//
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.e(TAG, "onStartCommand(DOOIOO_CALL_RECORD)");
		return START_STICKY;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Logger.e(TAG, "onDestroy()");
		startService(new Intent(this, CoreService.class));
	}

}
