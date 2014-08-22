package com.dooioo.call.record;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dooioo.tools.AndroidUtil;
import com.dooioo.tools.Logger;

public class CoreService extends Service
{

	private final String TAG = "CoreService";
	// Mark is call in or out.
	public String flag = "out";

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

		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		telephonyManager.listen(new MyPhoneStateListener(this), PhoneStateListener.LISTEN_CALL_STATE);
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

	class MyPhoneStateListener extends PhoneStateListener 
	{

		private MediaRecorder recorder = null;
		private CoreService coreService = null;

		public MyPhoneStateListener(CoreService callListener) 
		{
			this.coreService = callListener;
		}

		@Override
		public void onCallStateChanged(int state, String incomingNumber) 
		{
			super.onCallStateChanged(state, incomingNumber);

			switch (state) 
			{
			case TelephonyManager.CALL_STATE_IDLE:
				
				if (recorder != null) 
				{
					recorder.stop();
					recorder.reset();
					recorder.release();
					recorder = null;
				}
				break;
				
			case TelephonyManager.CALL_STATE_RINGING:
				
				File path = new File(Environment.getExternalStorageDirectory() + "/CallRecords/");
				if (!path.exists()) 
					path.mkdir();
				
				if (recorder == null) 
				{
					try
					{
						recorder = new MediaRecorder();
						recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
						recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
						recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
						recorder.setOutputFile(Environment.getExternalStorageDirectory()
								+ "/CallRecords/"
								+ AndroidUtil.getCurrebtDate(System.currentTimeMillis()) + ".mp3");
						recorder.prepare();
						coreService.flag = "in";
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;

			case TelephonyManager.CALL_STATE_OFFHOOK:
				
				File path1 = new File(Environment.getExternalStorageDirectory() + "/CallRecords/");
				if (!path1.exists()) 
					path1.mkdir();
				
				if (!coreService.flag.equals("in")) 
				{
					try 
					{
						recorder = new MediaRecorder();
						recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
						recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
						recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
						recorder.setOutputFile(Environment.getExternalStorageDirectory()
								+ "/CallRecords/"
								+ AndroidUtil.getCurrebtDate(System.currentTimeMillis()) + ".mp3");
						recorder.prepare();
						recorder.start();
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				else 
				{
					recorder.start();
				}
				break;
			}

		}
	}

}