package texasholdem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles a game. From get winner to call flop, turn etc..
 * @author Cadoret
 *
 */
public class Game{

	/**
	 * One deck
	 */
	private Deck deck;
	
	/**
	 * Players list
	 */
	private LinkedList<Player> players;
	
	/**
	 * The small blind
	 */
	private int smallBlind;
	

	/**
	 * The big blind
	 */
	private int bigBlind;

	/**
	 * The dealer
	 */
	private Player dealer;
	
	/**
	 * True while there is no final winner
	 */
	private boolean isRunning;
	
	/**
	 * Cash given to each player before run the game.
	 */
	private int startingCash;

	/**
	 * Number of round
	 */

	private int roundNumber;

	/**
	 * Constructor
	 * @param players
	 * @param smallBlind
	 * @param startingCash
	 */
	public Game(int smallBlind, int startingCash) {
		this.deck = new Deck();
		this.smallBlind = smallBlind;
		this.bigBlind = smallBlind*2;
		this.startingCash = startingCash;
		players = new LinkedList<Player>();
		setRoundNumber(0);
		
	}
	
	/**
	 * Distributes the pot to winners
	 * @param game winners
	 */
	public void distributePot(List<Player> winners, int pot){
		int winnersNum = winners.size();
		int sharedGain = pot / winnersNum;  
		for(Player winner : winners){
			winner.win(sharedGain);
		}
	}
	
	/**
	 * Gets players
	 * @return the players
	 */
	public LinkedList<Player> getPlayers(){
		return this.players;
	}
	

	/**
	 * Remove a player
	 * @param player to remove
	 */
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	/**
	 * Add a player before the game is starting
	 * @param player to add
	 */
	public void addPlayer(Player player) {
		players.add(player);
		player.setCredit(startingCash);
	}
	

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public String toString(){
		return  "Players : "+this.players+"\n"+
				"Small Blind : "+this.smallBlind+"\n"+
				"Big Blind : "+this.bigBlind+"\n";
	}

	public Player getDealer() {
		return dealer;
	}
	
	public int getIndexOfDealer() {
		return this.getPlayers().indexOf(this.dealer);
	}

	public void setDealer(Player dealer) {
		this.dealer = dealer;
	}
	
	public String getGameStatistiks(){
		String ret = "";
		
		for(Player player : players){
			ret = ret + player.getName()+": $"+player.getCredit() + "\n";	
		}
		ret = ret + this.getDealer().getName() + " has the button/is the dealer \n";
		
		return ret;
	}
	
	public String getPlayerStatistiks(Player player){
		String ret = "";
		
		ret = ret + player.getName()+": $"+player.getCredit() + "\n";	
			
		ret = ret + this.getDealer().getName() + " has the button/is the dealer \n";
		
		ret = ret + "Your cards: " + player.getCards()[0].toString() + " " + player.getCards()[1].toString() + "\n";
		
		return ret;
	}


	
	public Player getSmallBlindPlayer(){
		
		Iterator<Player> it = players.iterator();
		Player player = it.next();
		
		while(!player.equals(dealer)){
			player = it.next();
		}
		if(it.hasNext())
			return it.next(); 
		else
			return players.getFirst();
			
	}
	
	public Player getBigBlindPlayer(){
		
		Iterator<Player> it = players.iterator();
		Player player = it.next();
		
		while(!player.equals(dealer)){
			player = it.next();
		}
		if(it.hasNext()){
			it.next();
			if(it.hasNext())
				return it.next(); 
			else 
				return players.getFirst();
		}
		else{
			return players.get(1);
		}			
		
	}
	
	
	public Round createRound(){
		return new Round(this);
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	
	public int getSmallBlind() {
		return smallBlind;
	}

	public void setSmallBlind(int smallBlind) {
		this.smallBlind = smallBlind;
	}
	
	public int getBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(int bigBlind) {
		this.bigBlind = bigBlind;
	}
	
}
