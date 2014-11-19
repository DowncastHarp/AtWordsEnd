package edu.uark.csce.mzm.atwordsend;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ViewFriendActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_friend);
	}
	
	public void delete(View v){
		//add code to delete this, this may require that this have been started with startActivityForResults
		//unsure what exactly, for now it will just finish like close
		this.finish();
	}
	
	public void close(View v){
		this.finish();
	}
}
