package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GamesListActivity extends Activity {

	private ArrayList<Game> games;
	private GamesAdapter gamesAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games_list);

	    games = new ArrayList<Game>();
	    gamesAdapter = new GamesAdapter(this, android.R.layout.simple_list_item_1, games);
		
	    ListView listView = (ListView) findViewById(R.id.gamesListView);
	    listView.setAdapter(gamesAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.games_list, menu);
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
	
	public void close(View v){
		GamesListActivity.this.finish();
	}
}
