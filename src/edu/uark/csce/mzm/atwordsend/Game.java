package edu.uark.csce.mzm.atwordsend;

import java.util.ArrayList;

public class Game {
	private String opponent;
	private boolean myTurn;
	private ArrayList<String> usedWords;
	
	public Game(){
		this.opponent = "";
		this.myTurn = false;
		this.usedWords = new ArrayList<String>();
	}
	
	public Game(String opponent, boolean myTurn, ArrayList<String> usedWords){
		this.opponent = opponent;
		this.myTurn = myTurn;
		this.usedWords = usedWords;
	}

	public String getOpponent(){
		return opponent;
	}
	
	public void setOpponent(String opponent){
		this.opponent = opponent;
	}
	
	public boolean getMyTurn(){
		return myTurn;
	}
	
	public void setMyTurn(boolean myTurn){
		this.myTurn = myTurn;
	}
	
	public ArrayList<String> getUsedWords(){
		return usedWords;
	}
	
	public void setUsedWords(ArrayList<String> usedWords){
		this.usedWords = usedWords;
	}
	
	public boolean isWordUsed(String word){
		for(int i = 0; i < usedWords.size(); i++){
			if(usedWords.get(i).toLowerCase().equals(word.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	public void addWord(String word){
		this.usedWords.add(0, word);
	}
	
	public boolean letterComparisonCheck(String word){
		String lastWordPlayed = usedWords.get(0);
		char lastLetter = Character.toLowerCase(lastWordPlayed.charAt(lastWordPlayed.length() - 1));
		char firstLetter = Character.toLowerCase(word.charAt(0));
		if(firstLetter == lastLetter){
			return true;
		}
		return false;
	}
}
