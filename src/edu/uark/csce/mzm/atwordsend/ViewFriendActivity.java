package edu.uark.csce.mzm.atwordsend;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
