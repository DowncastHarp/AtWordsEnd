package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class FriendsListActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

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

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(this, FriendContentProvider.CONTENT_URI, null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		
		int Name_Index = data.getColumnIndexOrThrow(FriendContentProvider.KEY_NAME);
		int Wins_Index = data.getColumnIndexOrThrow(FriendContentProvider.KEY_WINS);
		int Losses_Index = data.getColumnIndexOrThrow(FriendContentProvider.KEY_LOSSES);
		
		friends.clear();
		
		while (data.moveToNext()) {
			Friend newitem = new Friend(data.getString(Name_Index),
										data.getInt(Wins_Index),
										data.getInt(Losses_Index));
			friends.add(newitem);
		}
		
		friendsAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}
}
