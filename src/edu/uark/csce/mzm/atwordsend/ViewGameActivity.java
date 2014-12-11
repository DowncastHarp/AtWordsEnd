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

public class ViewGameActivity extends Activity {
	
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
		
		//Set the countdown timer to 15 seconds
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
				//The player lost
				timer.setText("YOU LOST");
				submitButton.setEnabled(false);
				
				//Delete the game from the database
				ContentResolver cr = getContentResolver();
				cr.delete(GameContentProvider.CONTENT_URI.buildUpon().appendPath(opponent).build(), null, null);
				
				//Add a loss to the Player's loss count
				ContentValues newValues = new ContentValues();
				newValues.put(FriendContentProvider.KEY_LOSSES, FriendContentProvider.KEY_LOSSES + " + 1");
				
				String mSelection = FriendContentProvider.KEY_NAME + " = ?";
				String[] mSelectionArgs = new String[]{FriendContentProvider.KEY_NAME + " + 1"};
				getContentResolver().update(DictionaryContentProvider.CONTENT_URI, newValues, mSelection, mSelectionArgs);
			}
		};	
		
		if(myTurn){
			count.start();
		}
	}
	
	public void submitWord(View v){
		//Get the submitted word
		String rawWord = playerWord.getText().toString();
		
		//Check if the word is a valid play
		if(validPlay(rawWord)){
			//Perform end of turn functions: saving to game object, saving game info in database

			ContentResolver cr = getContentResolver();
			
			//Add the word to the game object and used words array list
			game.addWord(rawWord);
			
			//------------------Update UI Elements------------------
			//Notify the array adapter that the data changed and to update the used words listivew
			wordArrayAdapter.notifyDataSetChanged();
			lastWordPlayedView.setText("Last Word Played: " + game.getUsedWords().get(0));
			
			//Cancel the countdown timer
			count.cancel();
			
			//Disable the submitWord button
			playerWord.setEnabled(false);
			//------------------------------------------------------
			
			//Conver the usedWords array list into a string to store in the database
			String usedWordsString = "";
			
			for(int i = 0; i < game.getUsedWords().size(); i++){
				usedWordsString += game.getUsedWords().get(i) + ";";
			}
			
			//First check if the game exists in the game db
			String[] mProjection = new String[]{GameContentProvider.KEY_OPPONENT};
			String mSelection = GameContentProvider.KEY_OPPONENT + " = ?";
			String[] mSelectionArgs = new String[]{opponent};
			String mSortOrder = null; 
			c = getContentResolver().query(GameContentProvider.CONTENT_URI, mProjection, mSelection, mSelectionArgs, mSortOrder);
			//If an error was encountered in the query
			if(c == null){
				Toast toast = Toast.makeText(getApplicationContext(), "I'm so sorry. An error occured", Toast.LENGTH_SHORT);
				toast.show();
			}
			//Save the game to the database
			//This is a new game.  Needs to be inserted
			else if(c.getCount() == 0){
				//Insert a new game
				ContentValues newValues = new ContentValues();
				
				newValues.put(GameContentProvider.KEY_OPPONENT, opponent);
				newValues.put(GameContentProvider.KEY_TURN, false);
				newValues.put(GameContentProvider.KEY_WORDS, "");
				
				getContentResolver().insert(GameContentProvider.CONTENT_URI, newValues);
			}
			//Update the existing game
			else{
				ContentValues newValues = new ContentValues();
				
				newValues.put(GameContentProvider.KEY_TURN, !myTurn);
				newValues.put(GameContentProvider.KEY_WORDS, usedWordsString);
				
				cr.update(GameContentProvider.CONTENT_URI, newValues, GameContentProvider.KEY_OPPONENT + " = ?", new String[]{opponent});
			}
		}
	}
	
	public boolean validPlay(String rawString){
		//Convert the raw string to lower case for easy matching
		String playedWord = rawString.trim().toLowerCase();
		
		//Make sure the played word doesn't have any special characters
		Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(playedWord);
		boolean b = m.find();
		
		if(!b){
			
			//Check if the word exists in the dictionary db
			String[] mProjection = new String[]{DictionaryContentProvider.KEY_WORD};
			String mSelection = DictionaryContentProvider.KEY_WORD + " = ?";
			String[] mSelectionArgs = new String[]{playedWord};
			String mSortOrder = null;
			
			c = getContentResolver().query(DictionaryContentProvider.CONTENT_URI, mProjection, mSelection, mSelectionArgs, mSortOrder);
			
			//If an error was encountered in the query
			if(c == null){
				Toast toast = Toast.makeText(getApplicationContext(), "I'm so sorry. An error occured", Toast.LENGTH_SHORT);
				toast.show();
				playerWord.setText("");
				return false;
			}
			
			else if(c.getCount() != 0){
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

	public void close(View v){
		this.finish();
	}
}
