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
		
	    ListView listView = (ListView) findViewById(R.id.friendsListView);
	    listView.setAdapter(friendsAdapter);
	}
	
	public void startAddFriendActivity(View v){
        Intent intent = new Intent(this, AddFriendActivity.class);
        startActivity(intent);
	}
	
	public void close(View v){
		this.finish();
	}
}
