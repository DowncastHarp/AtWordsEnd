package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;
import java.util.Arrays;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GamesListActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int GAME_ACTIVITY = 1;
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

	    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
	    	@Override
	    	public void onItemClick(AdapterView<?> parent, final View view, int pos, long id){
	    		final Game item = (Game) parent.getItemAtPosition(pos);

	    		Intent intent = new Intent(GamesListActivity.this, ViewGameActivity.class);
	    		intent.putExtra("Opponent", item.getOpponent());
	    		intent.putExtra("myTurn", item.getMyTurn());
	    		intent.putExtra("Words", item.getUsedWords());
	    		startActivityForResult(intent, GAME_ACTIVITY);
	    	}
	    });
	    
		getLoaderManager().restartLoader(0, null, this);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getLoaderManager().restartLoader(0, null, this);
		switch(resultCode){
			case GAME_ACTIVITY:
				getLoaderManager().restartLoader(0, null, this);
				break;
			
			default:
				break;
		}
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
		
		int Opponent_Index = data.getColumnIndexOrThrow(GameContentProvider.KEY_OPPONENT);
		int Turn_Index = data.getColumnIndexOrThrow(GameContentProvider.KEY_TURN);
		int Words_Index = data.getColumnIndexOrThrow(GameContentProvider.KEY_WORDS);
		
		games.clear();
		
		while (data.moveToNext()) {
			
			ArrayList<String> list = new ArrayList<String>(Arrays.asList(data.getString(Words_Index).split(";")));
			
			Game newitem = new Game(data.getString(Opponent_Index),
								   (data.getInt(Turn_Index) == 1 ? true : false),
									list);
			games.add(newitem);
		}

		gamesAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	
		
	}
}
