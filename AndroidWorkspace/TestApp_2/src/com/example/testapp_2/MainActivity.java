package com.example.testapp_2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity  {

	public final static String EXTRA_MESSAGE = "com.example.testapp_2.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Called when the user clicks the Send button */
	public void btnMsgSend_Click(View view){
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		
		/*It's generally a good practice to define keys for intent extras 
		 * using your app's package name as a prefix. This ensures they 
		 * are unique, in case your app interacts with other apps		
		 */
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}

	
	
}
