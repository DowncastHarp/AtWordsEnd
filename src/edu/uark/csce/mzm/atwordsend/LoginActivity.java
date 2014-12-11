package edu.uark.csce.mzm.atwordsend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	public static final String KEY_ID = "id";
	public static final String KEY_WORD = "word";
	//create the DB
	public SQLiteDatabase dictionaryDB;
	public String TABLE_NAME = "Dictionary";
	public String DB_NAME = "Dictionary.db";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
				
		//make the db table
		dictionaryDB =  this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
		dictionaryDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + 
				//KEY_ID + " integer primary key autoincrement, "+
				KEY_WORD + " string primary key not null);");
		
		//is there anything in the table?
		//made a change
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
	
	public void makeDictionary(){
		//make the query string
        String sql = "INSERT INTO "+ TABLE_NAME +" VALUES (?);";
        SQLiteStatement statement = dictionaryDB.compileStatement(sql);
      
		Context context = getApplicationContext();
		AssetManager am = context.getAssets();
		String word;
		int count = 0;

		try{
			//open the dict.txt file
			InputStream inputStream = am.open("dict.txt");
		    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			//read and insert into db
			word = bufferedReader.readLine();
	        dictionaryDB.beginTransaction();
			while (word != null) {
				statement.clearBindings();
				statement.bindString(1, word);
				statement.execute();
				word = bufferedReader.readLine();
				count++;
				Log.d("Inserted", Integer.toString(count));
	      	}
			
			//close our stuff
			bufferedReader.close();
	        dictionaryDB.setTransactionSuccessful();	
	        dictionaryDB.endTransaction();
			Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
		}
		catch(IOException ex){
			Log.w("DICTIONARYPROVIDER", "Couldn't open file dict.txt");
			Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
		}
	}
}