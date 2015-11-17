package texasholdem;

import java.util.LinkedList;
import java.util.Random;

/**
 * Handles the creation of the game. 
 * @author Cadoret
 *
 */
public class Game{

	
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
		this.smallBlind = smallBlind;
		this.bigBlind = smallBlind*2;
		this.startingCash = startingCash;
		players = new LinkedList<Player>();
		setRoundNumber(0);
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
		int id = 0;
		if(!this.players.isEmpty()){
			id = players.getLast().getId()+1;
		}
		player.setId(id);
		player.setCredit(startingCash);
		players.add(player);
		
	}
	

	/**
	 * Gets dealer
	 * @return player who is dealer
	 */
	public Player getDealer() {
		return dealer;
	}
	
	/**
	 * Sets dealer 
	 * @param dealer
	 */
	public void setDealer(Player dealer) {
		this.dealer = dealer;
	}
	
	/**
	 * Gets index of dealer
	 * @return an index
	 */
	public int getIndexOfDealer() {
		return this.getPlayers().indexOf(this.dealer);
	}


	/**
	 * Gets next player
	 * @param prevPlayer
	 * @return next Player
	 */
	public Player getNext(Player prevPlayer){
		
		if(prevPlayer.equals(players.getLast())) 
			return players.getFirst();
		else{
			int prevPlayerIndex = players.indexOf(prevPlayer);
			return players.get(prevPlayerIndex+1);
		}
		
	}
	
	/**
	 * Creates a round
	 * @return a Round
	 */
	public Round createRound(){
		this.changeDealer();
		return new Round(this);
	}

	/**
	 * Changes the dealer
	 */
	private void changeDealer() {
		if(this.getRoundNumber()!=0){
			boolean newDealerIsFound = false;
			
			while(!newDealerIsFound){
				if(this.getNext(dealer).getCredit() > 0){
					this.setDealer(this.getNext(dealer));
					newDealerIsFound = true;
				}
				else
					this.setDealer(this.getNext(dealer));
			}
		}
		else{
			randomlySetDealer();

		}
		
	}
	
	/**
	 * Randomly set the dealer (used when it's the first round)
	 */
	private  void randomlySetDealer(){
		Random randomizer = new Random();
		Player dealer = this.getPlayers().get(randomizer.nextInt(this.getPlayers().size()));
		this.setDealer(dealer);
	}

	/**
	 * Gets number of rounds in game
	 * @return
	 */
	public int getRoundNumber() {
		return roundNumber;
	}

	/**
	 * Sets number of rounds in game
	 * @param roundNumber
	 */
	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	
	/**
	 * Gets small Blind
	 * @return small blind
	 */
	public int getSmallBlind() {
		return smallBlind;
	}

	/**
	 * Sets the small blind
	 * @param smallBlind
	 */
	public void setSmallBlind(int smallBlind) {
		this.smallBlind = smallBlind;
	}
	
	/**
	 * Gets big Blind
	 * @return big blind
	 */
	public int getBigBlind() {
		return bigBlind;
	}

	/**
	 * Sets the big blind
	 * @param bigBlind
	 */
	public void setBigBlind(int bigBlind) {
		this.bigBlind = bigBlind;
	}
	
	/**
	 * Gets some stats about the game
	 * @return String representing the game
	 */
	public String getGameStatistiks(){
		
		System.out.println("===========     GAME STATS     ===========");
		String ret = "";
		
		for(Player player : players){
			ret = ret + player.getName()+": $"+player.getCredit() + "\n";	
		}
		ret = ret + this.getDealer().getName() + " has the button/is the dealer \n";
		
		return ret;
	}
	
	/**
	 * Gets starting cash 
	 * @return the starting cash
	 */
	public int getStartingCash() {
		return startingCash;
	}
	
	/**
	 * Allow to know there is a game winner
	 * @return true if there is, false else. 
	 */
	public boolean hasWinner(){
		int numberPlayers=0;
		for(Player player : players){
			if(player.getCredit()>0)
				numberPlayers++;
		}
		return (numberPlayers == 1) ? true : false;
	}
	
	/**
	 * This method is always if the method hasWinner returns true. 
	 * @return winner Player
	 */
	public Player getWinner(){
		for(Player player : players){
			if(player.getCredit() > 0)
				return player;
		}
		return null;
	}
	
	/**
	 * Gets the index related to an player Id
	 * @param id
	 * @return the index
	 */
	public int getIndexOfPlayerId(int id){
		for(Player player : players){
			if(player.getId() == id)
				return players.indexOf(player);
		}
		return -1;
	}
	
	public String toString(){
		return  "Players : "+this.players+"\n"+
				"Small Blind : "+this.smallBlind+"\n"+
				"Big Blind : "+this.bigBlind+"\n";
	}
	

	
}
