package com.jagsstudio.androidclock;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

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
			Calendar calObject = Calendar.getInstance();
			TextView clockTextView = (TextView) findViewById(R.id.textView1);
			String strAM_PM = (calObject.get(Calendar.AM_PM)==1 ? "PM" : "AM");
			clockTextView.setText(String.format("%d:%02d:%02d %s", calObject.get(Calendar.HOUR), calObject.get(Calendar.MINUTE), calObject.get(Calendar.SECOND), strAM_PM));
		}
	};
	
}

