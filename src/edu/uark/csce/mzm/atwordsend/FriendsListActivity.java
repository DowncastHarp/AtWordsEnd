package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class FriendsListActivity extends Activity {

	private ArrayList<Friend> friends;
	private FriendsAdapter friendsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_list);
		
		friends = new ArrayList<Friend>();
	    friendsAdapter = new FriendsAdapter(this, android.R.layout.simple_list_item_1, friends);
		
	    ListView listView = (ListView) findViewById(R.id.gamesListView);
	    listView.setAdapter(friendsAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friends_list, menu);
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
	
	public void startAddFriendActivity(View v){
        Intent intent = new Intent(this, AddFriendActivity.class);
        startActivity(intent);
	}
	
	public void close(View v){
		this.finish();
	}
}
