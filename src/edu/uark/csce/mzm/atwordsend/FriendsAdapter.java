package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendsAdapter extends ArrayAdapter<Friend>{

	private final Context context;
    private final ArrayList<Friend> friendsArrayList;
	
	public FriendsAdapter(FriendsListActivity friendsListActivity, int simpleListItem1, ArrayList<Friend> friends) {
		super(friendsListActivity, simpleListItem1, friends);
		context = friendsListActivity;
		friendsArrayList = friends;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.friend_view, parent, false);
        
        TextView labelNameView = (TextView) rowView.findViewById(R.id.friendName);
        labelNameView.setText(friendsArrayList.get(position).getName());
		
        return rowView;
	}
}
