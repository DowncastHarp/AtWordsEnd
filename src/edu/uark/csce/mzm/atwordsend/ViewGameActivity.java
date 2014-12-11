package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;
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
	private boolean isRunning = false;
	
	//Database variables
	private Cursor c; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_game);
	
		//Get the game information from the intent
		Intent data = getIntent();
		opponent = data.getStringExtra("opponent");
		myTurn = data.getBooleanExtra("myTurn", false);
		usedWords = data.getStringArrayListExtra("usedWords");
		//Remove a blank word if it's the last word played
		if(!usedWords.isEmpty() && usedWords.get(0).equals("")){
			usedWords.remove(0);
		}
	
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
		
		//Disable the submitButton onCreate
		submitButton.setEnabled(false);
		//Set the textWatcher to watch the playerWord for changes
		playerWord.addTextChangedListener(watch);
		
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
				isRunning = true;
				
				String millis = String.format("%03d", millisUntilFinished % 1000);
				timer.setText(Integer.toString(seconds) + "." + millis.substring(0, 2));
			}
			
			public void onFinish() {
				//The player lost
				isRunning = false;
				timer.setText("YOU LOST");
				playerWord.setEnabled(false);
				submitButton.setEnabled(false);
				
				//Delete the game from the game database
				ContentResolver cr = getContentResolver();
				cr.delete(GameContentProvider.CONTENT_URI.buildUpon().appendPath(opponent).build(), null, null);
				
				//Add a loss to the Player's loss count
				//Get the number of losses against opponent
				String[] mmProjection = new String[]{FriendContentProvider.KEY_LOSSES};
				String mmSelection = FriendContentProvider.KEY_NAME + " = ?";
				String[] mmSelectionArgs = new String[]{opponent};
				String mmSortOrder = null; 
				//Query the Friends table to get the losses against opponent
				Cursor c = getContentResolver().query(FriendContentProvider.CONTENT_URI, mmProjection, mmSelection, mmSelectionArgs, mmSortOrder);
				c.moveToFirst();
				int numLosses = c.getInt(0);
				
				ContentValues newValues = new ContentValues();
				newValues.put(FriendContentProvider.KEY_LOSSES, numLosses + 1);
				
				String mSelection = FriendContentProvider.KEY_NAME + " = ?";
				String[] mSelectionArgs = new String[]{opponent};
				getContentResolver().update(FriendContentProvider.CONTENT_URI, newValues, mSelection, mSelectionArgs);
			}
		};	
		
		if(myTurn && !game.getUsedWords().isEmpty()){
			count.start();
		}
		else if(!myTurn){
			//Disable the editText
			playerWord.setEnabled(false);
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
			isRunning = false;
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
				newValues.put(GameContentProvider.KEY_TURN, true);
				newValues.put(GameContentProvider.KEY_WORDS, usedWordsString);
				
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
				// or if this is the first word in the game
				if(game.getUsedWords().isEmpty() || game.letterComparisonCheck(playedWord)){
					//Check if the word has been used
					if(!game.isWordUsed(playedWord)){
						//Word is a valid play
						Toast toast = Toast.makeText(getApplicationContext(), "\"" + rawString + "\" successfully played", Toast.LENGTH_SHORT);
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
		//If the timer is still going when the back button is pushed, forfeit the match
		if(isRunning){
			count.cancel();

			//Perform the same stuff that happens when the timer runs out
			//Delete the game from the game database
			ContentResolver cr = getContentResolver();
			cr.delete(GameContentProvider.CONTENT_URI.buildUpon().appendPath(opponent).build(), null, null);
			
			//Add a loss to the Player's loss count
			//Get the number of losses against opponent
			String[] mmProjection = new String[]{FriendContentProvider.KEY_LOSSES};
			String mmSelection = FriendContentProvider.KEY_NAME + " = ?";
			String[] mmSelectionArgs = new String[]{opponent};
			String mmSortOrder = null; 
			//Query the Friends table to get the losses against opponent
			Cursor c = getContentResolver().query(FriendContentProvider.CONTENT_URI, mmProjection, mmSelection, mmSelectionArgs, mmSortOrder);
			c.moveToFirst();
			int numLosses = c.getInt(0);
			
			ContentValues newValues = new ContentValues();
			newValues.put(FriendContentProvider.KEY_LOSSES, numLosses + 1);
			
			String mSelection = FriendContentProvider.KEY_NAME + " = ?";
			String[] mSelectionArgs = new String[]{opponent};
			getContentResolver().update(FriendContentProvider.CONTENT_URI, newValues, mSelection, mSelectionArgs);
		}
		
		Intent intent = new Intent("MyCustomIntent");
        setResult(Activity.RESULT_OK, intent);
		this.finish();
	}
	
	//TextWatcher Method
	TextWatcher watch = new TextWatcher(){
		  @Override
		  public void afterTextChanged(Editable arg0) {
			  // TODO Auto-generated method stub
			  //If the there is nothing in the edit text field, disable submit button
			  if(playerWord.getText().toString().trim().length() < 3){
				  submitButton.setEnabled(false);
			  }
			  else{
				  submitButton.setEnabled(true);
			  }
		  }
		  @Override
		  public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			  // TODO Auto-generated method stub
		  }
		  @Override
		  public void onTextChanged(CharSequence s, int a, int b, int c) {
			
		  }};
		  
	//Disable usage of the phone's back button
	@Override
	public void onBackPressed(){
		
	}
}
