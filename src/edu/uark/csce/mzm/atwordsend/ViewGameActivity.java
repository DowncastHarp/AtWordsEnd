package edu.uark.csce.mzm.atwordsend;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ViewGameActivity extends Activity {
	
	//UI Elements
	Button submitButton;
	Button backButton;
	TextView opponentInfo;
	TextView timer;
	ListView usedWords;
	EditText playerWord;

	//Variables
	int timeLimit = 15;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_game);
		
		timer = (TextView)findViewById(R.id.countdownTimer);
		
		CountDownTimer count = new CountDownTimer(timeLimit * 1000, 10) {
			public void onTick(long millisUntilFinished){
				int seconds = (int) ((millisUntilFinished / 1000));
				
				String millis = String.format("%03d", millisUntilFinished % 1000);
				timer.setText(Integer.toString(seconds) + "." + millis.substring(0, 2));
			}
			
			public void onFinish() {
				timer.setText("FINISHED");
			}
		};
		
		count.start();
	}
	
	public void close(View v){
		this.finish();
	}
}
