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
		
		//is there anything in the table?
		String[] mProjection = new String[] {DictionaryContentProvider.KEY_WORD};
		String mSelection = DictionaryContentProvider.KEY_WORD + " IS NOT NULL";
		String[] mSelectionArgs = new String[] {""};
		String mSortOrder = null;
		Cursor c;
		c = dictionaryDB.rawQuery("SELECT COUNT(*) FROM Dictionary", null);
		c.moveToFirst();
		Log.d("Done", Integer.toString(c.getInt (0)));
		
		if (c != null) {
		    c.moveToFirst();                       // Always one row returned.
		    if (c.getInt (0) == 0) {            // Zero count means empty table.
				makeDictionary();
		    }
		}
		c = dictionaryDB.rawQuery("SELECT COUNT(*) FROM Dictionary", null);
		c.moveToFirst();
		Log.d("Done", Integer.toString(c.getInt (0)));
		
		
		//	Toast.makeText(getApplicationContext(), "already made", Toast.LENGTH_LONG).show();
		//testing to see if it added things to db
	/*	mProjection = {"KEY_WORD"};
		mSelection = "KEY_WORD = ?";
		mSelectionArgs = {"equivalence"};
		mSortOrder = null;
		c = getContentResolver().query(DictionaryContentProvider.CONTENT_URI, mProjection, mSelection, mSelectionArgs, mSortOrder);
		Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
		*/
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
