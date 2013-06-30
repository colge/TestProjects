package com.example.testapp_2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mAmbientTemp;
	private Sensor mPresure;
	private Sensor mHumid;
	private Sensor mLight;
	
	//Needs to be global Var to store all values and displayed at once.
	private String mAmbTempReading = "", 
			   mHumidityReading = "", 
			   mPressureReading = "", 
			   mLightReading="";

	// Create the text view
    TextView textView = null;//new TextView(this);
    String message = null;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				 
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);		
		mPresure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);		
		mLight  = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);		
		mHumid = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
		mAmbientTemp = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
		
		// Get the message from the intent
	    Intent intent = getIntent();
	    message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	    
	    message += "Device Model[" +  android.os.Build.MODEL + "]\n";
	   
	    		
	    textView = new TextView(this);
	    
	    textView.setTextSize(15);	    
	    textView.setText(message);

	    // Set the text view as the activity layout
	    setContentView(textView);
	        
	}
	
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	 protected void onResume() {
	     super.onResume();
	     mSensorManager.registerListener((SensorEventListener) this, mAmbientTemp, SensorManager.SENSOR_DELAY_NORMAL);
	     mSensorManager.registerListener((SensorEventListener) this, mHumid, SensorManager.SENSOR_DELAY_NORMAL);	     
	     mSensorManager.registerListener((SensorEventListener) this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
	     mSensorManager.registerListener((SensorEventListener) this, mPresure, SensorManager.SENSOR_DELAY_NORMAL);
	     
	 }

	 @Override
	 protected void onPause() {
	     super.onPause();
	     super.onStop();
	     mSensorManager.unregisterListener((SensorListener) this);
	 }

	 @Override
	 public void onAccuracyChanged(Sensor sensor, int accuracy) {
	 }
		 
	 @Override
	 public void onSensorChanged(SensorEvent event) {
		 
		 
		 if (event.sensor.getType() != Sensor.TYPE_AMBIENT_TEMPERATURE
				 &&
				 event.sensor.getType() != Sensor.TYPE_RELATIVE_HUMIDITY
				 && 
				 event.sensor.getType() != Sensor.TYPE_LIGHT				
				 &&
				 event.sensor.getType() != Sensor.TYPE_PRESSURE
			)
         {         	
			 return;
         }
	 	 
	
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
        {         	
        	mAmbTempReading = "Ambient Temp:" +String.valueOf(  event.values[0])+ " C\n";
        }
        
        if(	event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY  	){        	
        	mHumidityReading = "Ambient Humidity:" +String.valueOf(  event.values[0])+ "%\n";
        }       
        
        if(	event.sensor.getType() == Sensor.TYPE_PRESSURE  	){        	
        	mPressureReading = "Ambient air pressure.:" +String.valueOf(  event.values[0])+ " mbar\n";        	
        }
        
        if(	event.sensor.getType() == Sensor.TYPE_LIGHT  	){        	
        	mLightReading = "Light Lx:" +String.valueOf(  event.values[0])+ "Lx\n";       	
        }             
        String timeStamp = "Data Aquired: [" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime())+"]\n\n";
        StringBuffer displayMsg = new StringBuffer(message).append(timeStamp).append(mAmbTempReading).append(mHumidityReading).append(mPressureReading).append(mLightReading);
        textView.setText(displayMsg);
        /*
        try{			 
			 Thread.sleep(100);
		 }catch (InterruptedException  ex  ){
			 
			 ex.printStackTrace();
		 }*/					
       
        
	 }

}
