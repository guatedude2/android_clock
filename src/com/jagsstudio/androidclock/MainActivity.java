package com.jagsstudio.androidclock;

import java.io.Console;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private Timer myTimer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myTimer = new Timer();
		myTimer.schedule(new TimerTask(){
			@Override
			public void run(){
				ClockMethod();
			}
		}, 0, 1000);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	
	private void ClockMethod(){
		this.runOnUiThread(Clock_Tick);
	}
	
	private Runnable Clock_Tick = new Runnable() {
		public void run(){
			Log.v("Clock Tick", new Date().toString());
		}
	};
	
}

