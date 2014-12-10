package edu.uark.csce.mzm.atwordsend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
		
		//read in the dictionary thing oh god please work
		Scanner sc = null;
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();	
		try{
			sc = new Scanner(new File("dict.txt"));
			while (sc.hasNextLine()) {
				String word = sc.nextLine();
				values.put(word, word);
				//cr.insert(DictionaryContentProvider.CONTENT_URI, values);
	      	}
			sc.close();
		}
		catch(FileNotFoundException ex){
			Log.w("DICTIONARYPROVIDER", "Couldn't open file dict.txt");
		}
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
