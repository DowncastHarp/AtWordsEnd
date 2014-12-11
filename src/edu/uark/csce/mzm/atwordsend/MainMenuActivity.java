package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        /*/-------------------HARD CODE------------------------
        //Add a game and friend to the database. If one doesn't exist already
        //Check if the word exists in the dictionary db
		String[] mProjection = new String[]{FriendContentProvider.KEY_NAME};
		String mSelection = FriendContentProvider.KEY_NAME + " = ?";
		String[] mSelectionArgs = new String[]{"Pace Halder"};
		String mSortOrder = null;
		
        ContentResolver cr = getContentResolver();
        
		if(getContentResolver().query(FriendContentProvider.CONTENT_URI, mProjection, mSelection, mSelectionArgs, mSortOrder).getCount() == 0){
	        ContentValues friendCV = new ContentValues();
	        
	        friendCV.put(FriendContentProvider.KEY_NAME, "PaceHalder");
	        friendCV.put(FriendContentProvider.KEY_WINS, 0);
	        friendCV.put(FriendContentProvider.KEY_LOSSES, 0);
	        cr.insert(FriendContentProvider.CONTENT_URI, friendCV);
		}
		
		mProjection = new String[]{GameContentProvider.KEY_OPPONENT};
		mSelection = GameContentProvider.KEY_OPPONENT + " = ?";
		mSelectionArgs = new String[]{"Pace Halder"};
		if(getContentResolver().query(GameContentProvider.CONTENT_URI, mProjection, mSelection, mSelectionArgs, mSortOrder).getCount() == 0){
			ContentValues gameCV = new ContentValues();
			
	        gameCV.put(GameContentProvider.KEY_OPPONENT, "PaceHalder");
	        gameCV.put(GameContentProvider.KEY_TURN, true);
	        gameCV.put(GameContentProvider.KEY_WORDS, "Raven;Savior;Emotions;Negative;Appalachian");
	        cr.insert(GameContentProvider.CONTENT_URI, gameCV);
		}
        //--------------------------------------------------*/
	}
	
	@Override
	protected void onStart(){
		super.onStart();
	}
	
	public void startGamesListActivity(View v){
        Intent intent = new Intent(this, GamesListActivity.class);
		/*/Hard coded game object (Only works if this starts ViewGameActivity.class:
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
		}*/

        startActivity(intent);
	}
	
	/*
	public void startMatchFindActivity(View v){
        Intent intent = new Intent(this, MatchFindActivity.class);
        startActivity(intent);		
	}
	*/
	
	public void startFriendsListActivity(View v){
        Intent intent = new Intent(this, FriendsListActivity.class);
        startActivity(intent);		
	}
	
	public void startSettingsActivity(View v){
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);		
	}
}
