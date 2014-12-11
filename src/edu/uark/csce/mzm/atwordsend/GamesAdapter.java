package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

import android.annotation.SuppressLint;
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

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.game_view, parent, false);
        
        TextView labelOpponentText = (TextView) rowView.findViewById(R.id.opponentText);
        labelOpponentText.setText(gamesArrayList.get(position).getOpponent());

        TextView labelTurnText = (TextView) rowView.findViewById(R.id.turnText);
        if(gamesArrayList.get(position).getMyTurn())
        	labelTurnText.setText("Your turn, make a move!");
        else
        	labelTurnText.setText("Their turn, wait a bit.");
		
        return rowView;
	}
}
