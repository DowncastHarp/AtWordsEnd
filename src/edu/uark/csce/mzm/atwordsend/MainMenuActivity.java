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
	protected void onStart(){
		super.onStart();
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
