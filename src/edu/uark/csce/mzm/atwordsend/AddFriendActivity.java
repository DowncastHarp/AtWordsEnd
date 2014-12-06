package edu.uark.csce.mzm.atwordsend;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddFriendActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);
	}
	
	public void sendRequest(View v){

		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		values.put(AddRequestContentProvider.KEY_NAME, ((EditText) findViewById(R.id.addFriendEditText)).getText().toString());
		cr.insert(AddRequestContentProvider.CONTENT_URI, values);

		getLoaderManager().restartLoader(0, null, this);
	}
	
	public void close(View v){
		this.finish();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Recieved requests stuff
		
		//Sent requests stuff
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}
}
