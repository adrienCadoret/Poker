package texasholdem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import actions.*;

/**
 * This class contains all things we need to progress a Round
 * @author Cadoret Adrien
 *
 */
public class Round {

	 /**
	  * Players who are playing in this round
	  */
	 private LinkedList<Player> currentPlayers;
	 
	 /**
	  * Game attached to the Round
	  */
	 private Game game;
	 
	 /**
	 * The pot
	 */
	private int pot;
		
	/**
	 * The dealer
	 */
	private Player dealer;
	
	/**
	 * The table cards
	 */
	private List<Card> tableCards;

	/**
	 * The Deck  
	 */
	private Deck deck;

	/**
	 * Small Blind
	 */
	private int smallBlind;

	/**
	 * Big blind
	 */
	private int bigBlind;
	
	/**
	 * SmallBlind player
	 */
	private Player smallBlindPlayer;
	
	/**
	 * BigBlind player
	 */
	private Player bigBlindPlayer;
	
	/**
	 * The current bet / the biggest bet
	 */
	private int currentBet;
	
	/**
	 * Current action
	 */
	Action currentAction;
	
	/**
	 * Current player
	 */
	Player currentPlayer;
	
	/**
	 * True if it is the preflop, false else.
	 */
	private boolean isPreflop;
	
	 
	/**
	 * Empty round
	 */
	public Round(){}
	
	/**
	 * Constructor
	 * @param game
	 */
	public Round(Game game){
		this.game = game;
		this.smallBlind = game.getSmallBlind();
		this.bigBlind = game.getBigBlind();
		
		LinkedList<Player> playersToClone = game.getPlayers();
		this.currentPlayers = new LinkedList<Player>();
		
		for(Player player : playersToClone){
			if(player.getCredit()>0){
				System.out.println(player);
				this.currentPlayers.add((Player)player.clone());
				if(player.getId()==game.getDealer().getId())
					this.dealer = this.getPlayers().getLast();
			}
		}
		
		this.smallBlindPlayer = getNext(dealer);
		this.bigBlindPlayer = getNext(smallBlindPlayer);
		this.tableCards = new ArrayList<Card>();
		this.deck = new Deck();
		this.pot = 0;
	}
	
	/**
	 * Runs the Round
	 */
	public void run() {
		
		System.out.println(game.getGameStatistiks());
		
		// pre-flop
		 
		 preflop();
		 for(Player player : currentPlayers){
			 System.out.println(player.getPlayerStatistiks());
		 }
		 // flop
		 callFlop();
		 runBetPhase();
		 // turn 
		 betTurn();	
		 runBetPhase();
		 // river
		 betRiver();
		 runBetPhase();
		 // showdown
		 //showdown();
		 List<Player> winners = RoundUtil.getWinner(currentPlayers, tableCards);
		 for(Player winner : winners){
			 System.out.println("Winner : "+winner.getName()+" with a "+winner.getHandEnum());
		 }
		 
		 this.distributePot(winners);
		 this.synchronizePlayersLists();
		 
		 System.out.println(game.getGameStatistiks());
		
	}
	
	/**
	 * Runs bet phase
	 */
	private  void runBetPhase() {
		System.out.println("===========     BET PHASE     ===========");
		
		
		boolean sameBetForAllPlayers = false;
		boolean allHavePlayed = allHavePlayed();
		
		Scanner actionScanner;
		Scanner amountScanner;
		
		if(isPreflop) currentPlayer = this.getNext(getNext(getNext(dealer)));
		else currentPlayer = this.getNext(dealer);
		
		
		while((!sameBetForAllPlayers || !allHavePlayed) && !this.allAllIn() && this.getPlayers().size() > 1){
			
			actionScanner = new Scanner(System.in);
		
			if(!currentPlayer.isAllIn()){
			
		    System.out.println(currentPlayer.getName()+" it's your turn ! \n"+
		    "You can: 'FOLD', 'CALL', 'RAISE', 'ALLIN', 'CHECK' OR 'BET' ");
		    switch (actionScanner.nextLine()) {
		    
			case "FOLD":
				currentPlayer.setAction(new FoldAction());
				checkAndExecuteAction(currentPlayer);
				break;
			case "CALL":
				currentPlayer.setAction(new CallAction());
				checkAndExecuteAction(currentPlayer);
				break;
			case "RAISE":
				System.out.println("Enter raise amount:");
				amountScanner = new Scanner(System.in);
				currentPlayer.setAction(new RaiseAction(amountScanner.nextInt()));
				checkAndExecuteAction(currentPlayer);
				break;
			case "ALLIN":
				currentPlayer.setAction(new AllInAction());
				checkAndExecuteAction(currentPlayer);
				break;
			case "CHECK":
				currentPlayer.setAction(new CheckAction());
				checkAndExecuteAction(currentPlayer);
				break;
			case "BET":
				System.out.println("Enter bet amount:");
				amountScanner = new Scanner(System.in);
				currentPlayer.setAction(new RaiseAction(amountScanner.nextInt()));
				checkAndExecuteAction(currentPlayer);
				break;

			default:
			    System.out.println("Other chance !");
				break;
			}
		    
			
			}
			else{
				currentPlayer.setAction(new AllInAction());
				currentPlayer = this.getNext(currentPlayer);
			}
			
			sameBetForAllPlayers = hasSameBet();
			allHavePlayed = allHavePlayed();

			
			
		}
		
		resetBetsAndActions();
		System.out.println("==========================   End of BET PHASE =================================");
	}
	

	

	/**
	 * Checks if all players are AllIn
	 * @return true if all are AllIn, false else.
	 */
	private boolean allAllIn() {
		for(Player player : currentPlayers)
			if(!player.isAllIn())
				return false;
		return true;
	}

	/**
	 * Checks the bets equivalence 
	 * @return true if all players have same bet, false else.
	 */
	private boolean hasSameBet() {
		int bet = currentPlayers.getFirst().getBet();
		for(Player player : currentPlayers){
			if(player.getBet() != bet)
				return false;
		}
		return true;
	}
	
	/**
	 * Checks if all players have played 
	 * @return true if all players have played, false else.
	 */
	private boolean allHavePlayed(){
		for(Player player : currentPlayers){
			if(player.getAction() == null)
				return false;
		}
		return true;
	}
	
	/**
	 * Reset all player action and their bet
	 */
	private void resetBetsAndActions(){
		for(Player player : currentPlayers){
			if(!(player.getAction() instanceof AllInAction)){
				player.setAction(null);
				player.setBet(0);
			}
		}
		this.currentAction = null;
		this.currentBet = 0;
		this.currentPlayer = null;
	}
	
	/**
	 * Checks and Executes Action
	 * @param player
	 */
	private void checkAndExecuteAction(Player player) {
		if(this.actionIsPossible(player)){
			executeAction(player);
			currentAction = player.getAction();
			System.out.println(player.getName() +" "+ currentAction.getVerb());
			currentPlayer = this.getNext(currentPlayer);
		}
		else{
			player.setAction(null);
			System.out.println("You can't do this action, replay");
		}
		
	}

	/**
	 * Check if action is correct
	 * @return boolean
	 */
	public boolean actionIsPossible(Player player){
		switch (player.getAction().getName()) {
		case "All-in":				
			// if already all in, return false
			if(player.isAllIn()) return false;
			else 
				return true;
		case "Bet":
			if(player.getCredit() >= player.getAction().getAmount())
				return true;
			else {
				System.out.println("You don't have enough money");
				return false;
			}
		case "Call":
			if(player.getCredit() >= this.currentBet - player.getBet())
				return true;
			else {
				System.out.println("You don't have enough money");
				return false;
			}
		case "Check":
			if(currentBet == 0) 
				return true;
			else {
				System.out.println("You have to pay of fold yourself");
				return false;
			}

		case "Fold":
			return true;
		case "Raise":
			if(player.getCredit() >= (this.currentBet - player.getBet()) + player.getAction().getAmount()
				&& player.getAction().getAmount() >= this.bigBlind)
				return true;
			else{
				if(player.getCredit() < (this.currentBet - player.getBet()) + player.getAction().getAmount()){
					System.out.println("You have to pay of fold yourself");
					return false;
				}
				else if(player.getAction().getAmount() < this.bigBlind){
					System.out.println("Your raise must be superior to the Big Blind");
					return false;
				}
			}
		case "Small-blind":
			return true;
		case "Big-blind":
			return true;
			
		default:
			return false;
		}
		
	}
	
	/**
	 * Execute player action
	 * @param player
	 */
	private void executeAction(Player player){
		int bet;
			switch (player.getAction().getName()) {
			case "All-in":				
				// changes the player state
				bet = player.getCredit();
				player.payCash(bet);
				this.setPot(getPot()+bet);
				player.setBet(player.getBet()+bet);
				player.setNbrOfCallsOnAllIn(this.currentPlayers.size());
				player.setCreditOnAllIn(bet);
				// changes the round state
				actualizeCurrentBet(bet);
				
				break;
			case "Bet":  
				// changes the player state
				bet = player.getAction().getAmount();
				player.payCash(bet);
				this.setPot(getPot()+bet);
				player.setBet(player.getBet()+bet);
				// changes the round state
				actualizeCurrentBet(bet);
				break;
			case "Call":
				// changes the player state
				bet = this.currentBet - player.getBet();
				player.payCash(bet);
				this.setPot(getPot()+bet);
				player.setBet(player.getBet()+bet);
				break;
			case "Check":
				// changes nothing for player and for round
				break;
			case "Fold":
				// just remove player to the currentPlayers list
				this.currentPlayers.remove(player);
				updatePlayerInGame(player);
				break;
			case "Raise":
				// changes the player state
				bet = (this.currentBet - player.getBet()) + player.getAction().getAmount();
				player.payCash(bet);
				this.setPot(getPot()+bet);
				player.setBet(player.getBet()+bet);
				// changes the round state
				actualizeCurrentBet(player.getAction().getAmount()+this.currentBet);
				break;

			case "Small-blind":
				// changes the player state
				player.payCash(smallBlind);
				this.setPot(getPot()+smallBlind);
				player.setBet(smallBlind);
				// changes the round state
				actualizeCurrentBet(smallBlind);
				break;
			case "Big-blind":
				// changes the player state
				player.payCash(bigBlind);
				this.setPot(getPot()+bigBlind);
				player.setBet(bigBlind);
				// changes the round state
				actualizeCurrentBet(bigBlind);
				break;
			default:
				System.out.println("No action is defined");
				break;
			}
	}
	
	
	/**
	 * Gets next player of a player
	 * @param prevPlayer
	 * @return the next player
	 */
	public Player getNext(Player prevPlayer){
		if(prevPlayer.equals(currentPlayers.getLast())) 
			return currentPlayers.getFirst();
		else{
			int prevPlayerIndex = currentPlayers.indexOf(prevPlayer);
			Player ret =  currentPlayers.get(prevPlayerIndex+1);
			return ret;
		}

	}
		
	/**
	 * Actualizes the current bet (after an action)
	 * @param newBet
	 */
	private void actualizeCurrentBet(int newBet){
		if(newBet > this.currentBet){
			this.currentBet = newBet;
		}
	}

	/**
	 * Pays blinds
	 */
	private void payBlinds() {
		
		//currentPlayer = smallBlindPlayer;
		smallBlindPlayer.setAction(new SmallBlindAction());
		currentPlayer = smallBlindPlayer;
		checkAndExecuteAction(currentPlayer);
		
		currentPlayer = bigBlindPlayer;
		currentPlayer.setAction(new BigBlindAction());
		checkAndExecuteAction(currentPlayer);

	}

	/**
	 * Runs preflop
	 */
	private void preflop() {
		this.isPreflop = true;
		System.out.println("===========     PREFLOP     ===========");
	 	payBlinds();
	 	deal();
	 	runBetPhase();
	 	this.isPreflop = false;
	}
	

	/**
	 * Distributes the pot to winners
	 * @param round winners
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void distributePot(List<Player> winners){
		
		List<Player> toRemove = new ArrayList<Player>();
		
		// Sort the winner list relating to the attribute isAllIn of each winner.
		// All winners who are AllIn will be at the beginning of the list
		Collections.sort(winners, new Comparator() {
	
		@Override
		public int compare(Object o1, Object o2) {
			Player p1 = (Player) o1;
			Player p2 = (Player) o2;
			
			if(p1.isAllIn() && p2.isAllIn()) return 0;
			else if(p1.isAllIn() && !p2.isAllIn()) return 1;
			else if(!p1.isAllIn() && p2.isAllIn()) return -1;
			return 0;
		}
	
		});
		
		// If there is more than 1 winner 
		if(winners.size() >= 2){
			for(Player winner : winners){
				if(winner.isAllIn()){
					int gain = winner.getCreditOnAllIn()*winner.getNbrOfCallsOnAllIn();
					System.out.println("GAIN IS "+gain);
					if(this.pot >= gain){
						winner.win(gain);
						this.setPot(getPot()-gain);
						toRemove.add(winner);
					}
					else{
						winner.win(pot);
						return;
					}
				}
			}
		}
		
		// When all AllIn winner cases are done, these are remove
		winners.removeAll(toRemove);
		if(this.pot>0){
			// Recalculating of the winner list + shared pot if more than 1 winner
			winners = RoundUtil.getWinner(winners, this.getTableCards());
			int winnersNum = winners.size();
			int sharedGain = pot / winnersNum;
			for(Player winner : winners){
				winner.win(sharedGain);
			}
		}

	}
	
	/**
	 * deal = give two cards to each player
	 */
	public void deal() {
		for (Player player : currentPlayers) {
			player.getCards()[0] = deck.pop();
			player.getCards()[1] = deck.pop();
			System.out.println(player.getPlayerStatistiks());
		}
	}
	
	
	/**
	 * Call flop with one pop before
	 */
	public void callFlop() {
		System.out.println("==========================   FLOP  =================================");
		
		System.out.println(getRoundStatistiks());

		deck.pop();
		tableCards.add(deck.pop());
		tableCards.add(deck.pop());
		tableCards.add(deck.pop());
		System.out.println("The table cards : "+this.displayTableCards());
	}

	/**
	 * Bets Turn 
	 */
	public void betTurn() {
		System.out.println("==========================   TURN  =================================");
		System.out.println(getRoundStatistiks());

		deck.pop();
		tableCards.add(deck.pop());
		System.out.println("The table cards : "+this.displayTableCards());
	}

	/**
	 * Bets river
	 */
	public void betRiver() {
		System.out.println("==========================   RIVER  =================================");
		System.out.println(getRoundStatistiks());

		deck.pop();
		tableCards.add(deck.pop());
		System.out.println("The table cards : "+this.displayTableCards());
	}

	/**
	 * Gets current players
	 * @return the current players
	 */
	public LinkedList<Player> getPlayers() {
		return currentPlayers;
	}

	/**
	 * Sets the players
	 * @param players
	 */
	public void setPlayers(LinkedList<Player> players) {
		this.currentPlayers = players;
	}
	
	/**
	 * Gets deck
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * Gets the pot
	 * @return the pot
	 */
	public int getPot() {
		return pot;
	}

	/**
	 * Sets the pot
	 * @param pot
	 */
	public void setPot(int pot) {
		this.pot = pot;
	}
	
	/**
	 * Gets table cards
	 * @return table cards
	 */
	public List<Card> getTableCards() {
		return tableCards;
	}

	/**
	 * Sets the table cards
	 * @param tableCards
	 */
	public void setTableCards(List<Card> tableCards) {
		this.tableCards = tableCards;
	}




	
	/**
	 * Gets Round Stats
	 * @return the String representing it
	 */
	public String getRoundStatistiks(){
		
		System.out.println("===========    ROUND STATS     ===========");
		String ret = "";
		
		for(Player player : currentPlayers){
			ret = ret + player.getPlayerStatistiks() + "\n";	
		}
		ret = ret + this.dealer.getName() + " has the button/is the dealer \n";
		ret = ret + "Current bet : "+this.currentBet + "\n";
		ret = ret + "Pot : "+this.pot;
		
		return ret;
	}
	
	/**
	 * Gets the Table Cards
	 * @return a String representing it
	 */
	public String displayTableCards(){
		String ret = "";
		for(Card card : tableCards){
			ret = ret + card.toString() + "   ";	
		}
		return ret;
	}
	
	/**
	 * Synchronize the Game players list and the Round current players list (at the end of the round to set the credit correctly)
	 */
	private void synchronizePlayersLists(){
		for(Player player : currentPlayers){
			this.updatePlayerInGame(player);
			
		}
	}
	
	/**
	 * Synchronize a Game Player credit with the same player in Round (at the end of the round to set the credit correctly)
	 * @param playerToUpdate
	 */
	private void updatePlayerInGame(Player playerToUpdate){
		int credit = playerToUpdate.getCredit();
		int id = playerToUpdate.getId();
		int index = game.getIndexOfPlayerId(id);
		Player updatedPlayer = game.getPlayers().get(index);
		updatedPlayer.setCredit(credit);
	}
	
	

	
	 
	 
	
}
