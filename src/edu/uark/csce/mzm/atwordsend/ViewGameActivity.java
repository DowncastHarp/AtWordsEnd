package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewGameActivity extends Activity {
	
	//UI Elements
	private Button submitButton;
	private Button backButton;
	private TextView opponentInfo;
	private TextView timer;
	private ListView usedWordsList;
	private EditText playerWord;
	private ArrayAdapter<String> wordArrayAdapter;

	//Variables
	private int timeLimit = 15;
	private Game game;
	private int id;
	private String opponent;
	private boolean myTurn;
	private ArrayList<String> usedWords;
	private boolean wordExists = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_game);
		
		//Get the game information from the intent
		Intent data = getIntent();
		id = data.getIntExtra("ID", -1);
		opponent = data.getStringExtra("Opponent");
		myTurn = data.getBooleanExtra("MyTurn", false);
		usedWords = data.getStringArrayListExtra("UsedWords");
	
		//Create the game object from the info in the intent;
		game = new Game(id, opponent, myTurn, usedWords);
		
		//Get the UI elements
		submitButton = (Button)findViewById(R.id.submitButton);
		backButton = (Button)findViewById(R.id.backButton);
		opponentInfo = (TextView)findViewById(R.id.opponentName);
		timer = (TextView)findViewById(R.id.countdownTimer);
		usedWordsList = (ListView)findViewById(R.id.usedWordsList);
		playerWord = (EditText)findViewById(R.id.playerWordEditText);
		
		//Populate the UI elements with the game info
		opponentInfo.setText("Game With: " + opponent);
		wordArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usedWords);
		usedWordsList.setAdapter(wordArrayAdapter);
		
		CountDownTimer count = new CountDownTimer(timeLimit * 1000, 10) {
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
		
		if(myTurn){
			count.start();
		}
	}
	
	//Toast toast = Toast.makeText(getApplicationContext(), "Good Job", Toast.LENGTH_SHORT);
	//toast.show();
	
	public void submitWord(View v){

		//Get the submitted word
		String rawWord = playerWord.getText().toString();
		String playedWord = rawWord.trim().toLowerCase();
		
		//Check if the word exists
		if(wordExists){
			//Check if the first letter of the word matches the last letter of the previous word
			if(game.letterComparisonCheck(playedWord)){
				//Check if the word has been used
				if(!game.isWordUsed(playedWord)){
					Toast toast = Toast.makeText(getApplicationContext(), "\"" + rawWord + "\" CONGRATULATIONS!", Toast.LENGTH_SHORT);
					toast.show();
				}
				else{
					Toast toast = Toast.makeText(getApplicationContext(), "\"" + rawWord + "\" has already been played", Toast.LENGTH_SHORT);
					toast.show();
					playerWord.setText("");
				}
			}
			else{
				Toast toast = Toast.makeText(getApplicationContext(), "\"" + rawWord + "\" doesn't play by the rules", Toast.LENGTH_SHORT);
				toast.show();
				playerWord.setText("");
			}
		}
		else{
			Toast toast = Toast.makeText(getApplicationContext(), "\"" + rawWord + "\" is not valid", Toast.LENGTH_SHORT);
			toast.show();
			playerWord.setText("");
		}
	}
	
	public void close(View v){
		this.finish();
	}
}
