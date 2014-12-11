package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GamesListActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

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

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(this, GameContentProvider.CONTENT_URI, null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		
		int Name_Index = data.getColumnIndexOrThrow(GameContentProvider.KEY_OPPONENT);
		int Wins_Index = data.getColumnIndexOrThrow(GameContentProvider.KEY_TURN);
		int Losses_Index = data.getColumnIndexOrThrow(GameContentProvider.KEY_WORDS);
		
		games.clear();
		
		while (data.moveToNext()) {
			
			
			Game newitem = new Game(data.getString(Name_Index),
									data.getInt(Wins_Index),
									data.getInt(Losses_Index));
			games.add(newitem);
		}

		gamesAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	
		
	}
}
