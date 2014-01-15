package com.jagsstudio.androidclock;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {
	private Context context;
	private Calendar myAlarm;
	private boolean myAlarmEnabled;
	private Timer myTimer;
	private Ringtone myRingtone;
	private boolean hasRingtoneSupport;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//set alarm ringtone
		hasRingtoneSupport = false;
		try {
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
			myRingtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
			hasRingtoneSupport = true;
		} catch (Exception e) {}	
		
		//set a sample alarm 
		myAlarm = Calendar.getInstance();
		myAlarm.set(Calendar.SECOND, 0);
		myAlarmEnabled = false;
		
		//Tick for clock
		myTimer = new Timer();
		myTimer.schedule(new TimerTask(){
			@Override
			public void run(){
				ClockMethod();
			}
		}, 0, 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_setalarm:
	            setAlarmClock();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void setAlarmClock(){
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        TimePickerDialog myAlarmPicker;
        myAlarmPicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            	myAlarm.set(Calendar.HOUR_OF_DAY, selectedHour);
            	myAlarm.set(Calendar.MINUTE, selectedMinute);
                myAlarmEnabled = true;
            }
        }, hour, minute, false);
        myAlarmPicker.setTitle("Set Alarm");
        myAlarmPicker.show();
	}
	
	private void ClockMethod(){
		this.runOnUiThread(Clock_Tick);
	}
	
	private Runnable Clock_Tick = new Runnable() {
		public void run(){
			Calendar calObject = Calendar.getInstance();
			TextView clockTextView = (TextView) findViewById(R.id.textView1);
			String strAM_PM = (calObject.get(Calendar.AM_PM)==1 ? "PM" : "AM");
			clockTextView.setText(String.format("%d:%02d:%02d %s", calObject.get(Calendar.HOUR), calObject.get(Calendar.MINUTE), calObject.get(Calendar.SECOND), strAM_PM));
			if (calObject.get(Calendar.HOUR_OF_DAY)==myAlarm.get(Calendar.HOUR_OF_DAY) && calObject.get(Calendar.MINUTE)==myAlarm.get(Calendar.MINUTE) && myAlarmEnabled) fireAlarm();
		}
		
		public void fireAlarm(){
			if (hasRingtoneSupport) myRingtone.play();
			new AlertDialog.Builder(context)
		    .setTitle("Alarm")
		    .setMessage("This is your alarm!")
            .setCancelable(false)
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
        			if (hasRingtoneSupport) myRingtone.stop();                    
                }
            })
            .setNegativeButton("Snooze", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
            		myAlarm = Calendar.getInstance();
            		myAlarm.roll(Calendar.SECOND, Calendar.getInstance().get(Calendar.SECOND)+30);
            		myAlarmEnabled = true;
        			if (hasRingtoneSupport) myRingtone.stop();
                    dialog.cancel();
                }
            })
		    .show();
			myAlarmEnabled = false;
		}
	};
	
}

