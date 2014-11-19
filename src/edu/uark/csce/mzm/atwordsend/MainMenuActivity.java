package edu.uark.csce.mzm.atwordsend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void startGamesListActivity(View v){
        Intent intent = new Intent(this, GamesListActivity.class);
        startActivity(intent);
	}
	
	public void startMatchFindActivity(View v){
        Intent intent = new Intent(this, MatchFindActivity.class);
        startActivity(intent);		
	}
	
	public void startFriendsListActivity(View v){
        Intent intent = new Intent(this, FriendsListActivity.class);
        startActivity(intent);		
	}
	
	public void startSettingsActivity(View v){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);		
	}
}
