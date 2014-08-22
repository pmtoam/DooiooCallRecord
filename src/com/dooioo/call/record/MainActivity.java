package com.dooioo.call.record;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dooioo.example2.R;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService(new Intent(this, CoreService.class));
	}

}
