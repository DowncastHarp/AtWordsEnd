package edu.uark.csce.mzm.atwordsend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//If we have login information stored in preferences then we need to automatically log in from create
	}

	@Override
	public void onBackPressed(){
		
	}
	
	public void login(View v){

		//Verification done here if we didn't auto login
        if (!((EditText) findViewById(R.id.usernameEditText)).getText().toString().equals("") && 
        	!((EditText) findViewById(R.id.passwordEditText)).getText().toString().equals("")){
        	
        	this.finish();
        }
        else
        	Toast.makeText(getApplicationContext(), "Something went wrong in login, try not leaving a field blank.", Toast.LENGTH_LONG).show();
	}
	
	public void createAccount(View v){
		Toast.makeText(getApplicationContext(), "Nothin' yet, try again later.", Toast.LENGTH_LONG).show();
	}
}
