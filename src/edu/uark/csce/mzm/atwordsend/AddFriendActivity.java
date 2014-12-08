package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class AddFriendActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

	private ArrayList<String> recievedRequests;
	private ArrayAdapter<String> recievedRequestsAdapter;
	
	private ArrayList<String> sentRequests;
	private ArrayAdapter<String> sentRequestsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);

		recievedRequests = new ArrayList<String>();
	    recievedRequestsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recievedRequests);

	    ListView receivedRequestsListView = (ListView) findViewById(R.id.receivedRequestsListView);
	    receivedRequestsListView.setAdapter(recievedRequestsAdapter);
	    
	    receivedRequestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
	    	@Override
	    	public void onItemClick(AdapterView<?> parent, final View view, int pos, long id){
	    		final String item = (String) parent.getItemAtPosition(pos);
	    		
	    		PopupWindow popup = new PopupWindow(getApplicationContext());
	    		popup.setHeight(50);
	    		popup.setWidth(100);
	    		popup.setContentView(findViewById(R.layout.popup_window_view));
	    	}
	    });
	    
		sentRequests = new ArrayList<String>();
	    sentRequestsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sentRequests);

	    ListView sentRequestsListView = (ListView) findViewById(R.id.sentRequestsListView);
	    sentRequestsListView.setAdapter(sentRequestsAdapter);
	    
	    getLoaderManager().restartLoader(0, null, this);
	    getLoaderManager().restartLoader(1, null, this);
	}
	
	public void sendRequest(View v){ 

		ContentResolver cr = getContentResolver();
		ContentValues sValues = new ContentValues();
		ContentValues rValues = new ContentValues();
		
		sValues.put(SentRequestContentProvider.KEY_NAME, ((EditText) findViewById(R.id.addFriendEditText)).getText().toString());
		cr.insert(SentRequestContentProvider.CONTENT_URI, sValues);
		
		rValues.put(RecievedRequestContentProvider.KEY_NAME, ((EditText) findViewById(R.id.addFriendEditText)).getText().toString());
		cr.insert(RecievedRequestContentProvider.CONTENT_URI, rValues);
		
		((EditText) findViewById(R.id.addFriendEditText)).setText(null);

		getLoaderManager().restartLoader(0, null, this);
		getLoaderManager().restartLoader(1, null, this);
	}
	
	public void close(View v){
		this.finish();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		if(id == 0){
			CursorLoader loader = new CursorLoader(this, RecievedRequestContentProvider.CONTENT_URI, null, null, null, null);
			return loader;
		}
		else if(id == 1){
			CursorLoader loader = new CursorLoader(this, SentRequestContentProvider.CONTENT_URI, null, null, null, null);
			return loader;
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		
		
		switch (loader.getId()) {
	        case 0:
				// Recieved requests stuff
				int rName_Index = data.getColumnIndexOrThrow(RecievedRequestContentProvider.KEY_NAME);
				recievedRequests.clear();
				
				while (data.moveToNext()) {
					recievedRequests.add(data.getString(rName_Index));
				}
				
				recievedRequestsAdapter.notifyDataSetChanged();
	            break;
	            
	        case 1:
				//Sent requests stuff
				int sName_Index = data.getColumnIndexOrThrow(SentRequestContentProvider.KEY_NAME);
				sentRequests.clear();
				
				while (data.moveToNext()) {
					sentRequests.add(data.getString(sName_Index));
				}
				
				sentRequestsAdapter.notifyDataSetChanged();
	            break;
	            
	        default:
	            break;
		}	
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}
}
