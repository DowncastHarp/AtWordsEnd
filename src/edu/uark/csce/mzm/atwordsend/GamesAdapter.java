package edu.uark.csce.mzm.atwordsend;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GamesAdapter extends ArrayAdapter<Game>{

	private final Context context;
    private final ArrayList<Game> gamesArrayList;
	
	public GamesAdapter(GamesListActivity gamesListActivity, int simpleListItem1, ArrayList<Game> games) {
		super(gamesListActivity, simpleListItem1, games);
		context = gamesListActivity;
		gamesArrayList = games;
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
