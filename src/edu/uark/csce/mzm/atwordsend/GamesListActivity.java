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
	
	public void close(View v){
		GamesListActivity.this.finish();
	}
}
