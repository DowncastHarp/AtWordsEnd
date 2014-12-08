package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

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
        Intent intent = new Intent(this, ViewGameActivity.class);
		//Hard coded game object:
		if(true){
			ArrayList<String> wurds = new ArrayList<String>();
			wurds.add(0, "Appalachian");
			wurds.add(0, "Negative");
			wurds.add(0, "Emotions");
			wurds.add(0, "Savior");
			wurds.add(0, "Raven");
			
	        intent.putExtra("ID", 1);
	        intent.putExtra("Opponent", "Pace Halder");
	        intent.putExtra("MyTurn", true);
	        intent.putExtra("UsedWords", wurds);
		}

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
