package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewFriendActivity extends Activity {

	Friend friend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_friend);
		
		Intent data = getIntent();
		String[] projections = {FriendContentProvider.KEY_NAME , FriendContentProvider.KEY_WINS, FriendContentProvider.KEY_LOSSES};
		String[] stringArgs = {data.getStringExtra("Name")};
		
		Cursor cursor = getContentResolver().query(FriendContentProvider.CONTENT_URI, projections, FriendContentProvider.KEY_NAME + " = ?", stringArgs, null);
		cursor.moveToFirst();
		
		friend = new Friend(cursor.getString(0), 
						    cursor.getInt(1), 
						    cursor.getInt(2));
		
		((TextView) findViewById(R.id.friendName)).setText(friend.getName());
		((TextView) findViewById(R.id.scoreValue)).setText(friend.getWins() + " / " + friend.getLosses());
		
	}
	
	public void startGame(View v){

		//Give a notification if we already have a game with this person and then do nothing
		
		String[] projections = {"Count(*)"};
		String[] stringArgs = {friend.getName()};
		
		Cursor cursor = getContentResolver().query(GameContentProvider.CONTENT_URI, projections, GameContentProvider.KEY_OPPONENT + " = ?", stringArgs, null);
		cursor.moveToFirst();
		
		if(cursor.getInt(0) > 0){
			Toast.makeText(getApplicationContext(), "A game with this person already exists.", Toast.LENGTH_LONG).show();
			return;
		}
		
        Intent intent = new Intent(this, ViewGameActivity.class);

        intent.putExtra("Opponent", friend.getName());
        intent.putExtra("MyTurn", true);
        intent.putExtra("UsedWords", new ArrayList<String>());

        startActivity(intent);
	}
	
	public void delete(View v){

		ContentResolver cr = getContentResolver();
		cr.delete(FriendContentProvider.CONTENT_URI.buildUpon().appendPath("'" + friend.getName() + "'").build(), null, null);
		this.finish();
	}
	
	public void close(View v){
		this.finish();
	}
}
