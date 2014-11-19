package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;

public class FriendsAdapter extends ArrayAdapter<Friend>{

	private final Context context;
    private final ArrayList<Friend> friendsArrayList;
	
	public FriendsAdapter(FriendsListActivity friendsListActivity, int simpleListItem1, ArrayList<Friend> friends) {
		super(friendsListActivity, simpleListItem1, friends);
		context = friendsListActivity;
		friendsArrayList = friends;
	}

	//@Override
	//public View getView(int position, View convertView, ViewGroup parent) {
	
        /* Stuff from Matthew's homework1 to be used as a reference later

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        
        TextView labelNameView = (TextView) rowView.findViewById(R.id.labelName);
        labelNameView.setText(workoutArrayList.get(position).getName());

		SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
        TextView labelDateView = (TextView) rowView.findViewById(R.id.labelDate);
        labelDateView.setText(formatDate.format(workoutArrayList.get(position).getDate()));
		
        return rowView;*/
	//}
}
