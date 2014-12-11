package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.widget.Toast;

public class ViewGameActivity extends Activity 
implements LoaderManager.LoaderCallbacks<Cursor>{
	
	//UI Elements
	private Button submitButton;
	private Button backButton;
	private TextView opponentInfo;
	private TextView timer;
	private TextView lastWordPlayedView;
	private ListView usedWordsList;
	private EditText playerWord;
	private ArrayAdapter<String> wordArrayAdapter;
	private CountDownTimer count;

	//Variables
	private int timeLimit = 15;
	private Game game;
	private String opponent;
	private boolean myTurn;
	private ArrayList<String> usedWords;
	
	//Database variables
	private Cursor c; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_game);
		
		//Get the game information from the intent
		Intent data = getIntent();
		opponent = data.getStringExtra("Opponent");
		myTurn = data.getBooleanExtra("MyTurn", false);
		usedWords = data.getStringArrayListExtra("UsedWords");
	
		//Create the game object from the info in the intent;
		game = new Game(opponent, myTurn, usedWords);
		
		//Get the UI elements
		submitButton = (Button)findViewById(R.id.submitButton);
		backButton = (Button)findViewById(R.id.backButton);
		opponentInfo = (TextView)findViewById(R.id.opponentName);
		timer = (TextView)findViewById(R.id.countdownTimer);
		lastWordPlayedView = (TextView)findViewById(R.id.lastPlayedWordView);
		usedWordsList = (ListView)findViewById(R.id.usedWordsList);
		playerWord = (EditText)findViewById(R.id.playerWordEditText);
		
		//Populate the UI elements with the game info
		opponentInfo.setText("Game With: " + opponent);
		wordArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usedWords);
		usedWordsList.setAdapter(wordArrayAdapter);
		
		if(!game.getUsedWords().isEmpty()){
			lastWordPlayedView.setText("Last Word Played: " + game.getUsedWords().get(0));
		}
		else{
			lastWordPlayedView.setText("Start With a New Word!");
		}
		
		count = new CountDownTimer(timeLimit * 1000, 10) {
			public void onTick(long millisUntilFinished){
				int seconds = (int) ((millisUntilFinished / 1000));
				
				String millis = String.format("%03d", millisUntilFinished % 1000);
				timer.setText(Integer.toString(seconds) + "." + millis.substring(0, 2));
				
				//If the there is nothing in the edit text field, disable submit button
				if(playerWord.getText().toString().trim().length() < 3){
					submitButton.setEnabled(false);
				}
				else{
					submitButton.setEnabled(true);
				}
			}
			
			public void onFinish() {
				timer.setText("FINISHED");
				submitButton.setEnabled(false);
			}
		};
		
		getLoaderManager().initLoader(0, null, this);	
		
		if(myTurn){
			count.start();
		}
	}
	
	//Toast toast = Toast.makeText(getApplicationContext(), "Good Job", Toast.LENGTH_SHORT);
	//toast.show();
	
	//Loader class
 	@Override
 	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
 		CursorLoader loader = new CursorLoader(this, 
 				DictionaryContentProvider.CONTENT_URI, null, null, null, null);
 		return loader;
 	}

 	@Override
 	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
 		//Don't need to load anything into the UI
 	}

 	@Override
 	public void onLoaderReset(Loader<Cursor> loader) {
 		//
 	}
	
	public void submitWord(View v){

		//Get the submitted word
		String rawWord = playerWord.getText().toString();
		
		//Check if the word is a valid play
		if(validPlay(rawWord)){
			//Perform end of turn functions: saving to game object, saving game info in database
			Toast toast = Toast.makeText(getApplicationContext(), "WOW", Toast.LENGTH_SHORT);
			toast.show();
			
			ContentResolver cr = getContentResolver();
			
			//Add the word to the game object and used words array list
			game.addWord(rawWord);
			
			//------------------Update UI Elements------------------
			//Notify the array adapter that the data changed and to update the used words listivew
			wordArrayAdapter.notifyDataSetChanged();
			lastWordPlayedView.setText("Last Word Played: " + game.getUsedWords().get(0));
			
			count.cancel();
			
			playerWord.setEnabled(false);
			//------------------------------------------------------
			
			String usedWordsString = "";
			
			for(int i = 0; i < game.getUsedWords().size(); i++){
				usedWordsString += game.getUsedWords().get(i) + " ";
			}
			//Save the game to the database
			ContentValues newValues = new ContentValues();
			
			newValues.put(GameContentProvider.KEY_TURN, !myTurn);
			newValues.put(GameContentProvider.KEY_WORDS, usedWordsString);
			
			cr.update(GameContentProvider.CONTENT_URI, newValues, GameContentProvider.KEY_OPPONENT + " = ?", new String[]{opponent});
		}
	}
	
	public void close(View v){
		this.finish();
	}
	
	public boolean validPlay(String rawString){
		//Convert the raw string to lower case for easy matching
		String playedWord = rawString.trim().toLowerCase();
		
		//Make sure the played word doesn't have any special characters
		Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(playedWord);
		boolean b = m.find();
		
		if(!b){
			/*
			//Check if the word exists in the dictionary db
			String[] mProjection = new String[]{DictionaryContentProvider.KEY_WORD};
			String mSelection = DictionaryContentProvider.KEY_WORD + " = ?";
			String[] mSelectionArgs = new String[]{playedWord};
			String mSortOrder = null;
			//As long as the cursor 
			c = getContentResolver().query(DictionaryContentProvider.CONTENT_URI, mProjection, mSelection, mSelectionArgs, mSortOrder);			
			
			//If an error was encounterd in the query
			if(c == null){
				Toast toast = Toast.makeText(getApplicationContext(), "I'm so sorry. An error occured", Toast.LENGTH_SHORT);
				toast.show();
				playerWord.setText("");
				return false;
			}
			*/
			/*else*/ if(true/*c.getCount() != 0*/){
				//Check if the first letter of the word matches the last letter of the previous word
				if(game.letterComparisonCheck(playedWord)){
					//Check if the word has been used
					if(!game.isWordUsed(playedWord)){
						//Word is a valid play
						Toast toast = Toast.makeText(getApplicationContext(), "\"" + rawString + "\" CONGRATULATIONS!", Toast.LENGTH_SHORT);
						toast.show();
						return true;
					}
					else{
						Toast toast = Toast.makeText(getApplicationContext(), "\"" + rawString + "\" has already been played", Toast.LENGTH_SHORT);
						toast.show();
						playerWord.setText("");
						return false;
					}
				}
				else{
					Toast toast = Toast.makeText(getApplicationContext(), "\"" + rawString + "\" doesn't play by the rules", Toast.LENGTH_SHORT);
					toast.show();
					playerWord.setText("");
					return false;
				}
			}
			else{
				Toast toast = Toast.makeText(getApplicationContext(), "\"" + rawString + "\" is not a valid word", Toast.LENGTH_SHORT);
				toast.show();
				playerWord.setText("");
				return false;
			}
		}	
		else{
			Toast toast = Toast.makeText(getApplicationContext(), "\"" + rawString + "\" contains a special character\nPlease don't use special characters", Toast.LENGTH_SHORT);
			toast.show();
			playerWord.setText("");
			return false;
		}
	}
}
