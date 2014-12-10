package edu.uark.csce.mzm.atwordsend;

public class Friend {

	private String name;
	private int wins; //Number of times the user has beaten this friend
	private int losses; //Number of times the user has lost to this friend
	
	public Friend(String name, int wins, int losses){
		
		this.name = name;
		this.wins = wins;
		this.losses = losses;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setWins(int wins){
		this.wins = wins;
	}
	
	public int getWins(){
		return this.wins;
	}
	
	public void setLosses(int losses){
		this.losses = losses;
	}
	
	public int getLosses(){
		return this.losses;
	}
}
