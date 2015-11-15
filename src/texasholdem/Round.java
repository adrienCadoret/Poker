package texasholdem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import actions.*;

public class Round {

	 /**
	  * Players who are playing in this round
	  */
	 private LinkedList<Player> currentPlayers;
	 
	 
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
	 
	 
	/*public Round(LinkedList<Player> players, Player dealer, int smallBlind){
		this.players = players;
		this.dealer = dealer;
		this.smallBlind = smallBlind;
		this.bigBlind = smallBlind*2;
		this.tableCards = new ArrayList<Card>();
		this.deck = new Deck();
		this.pot = 0;
	}*/
	
	public Round(Game game){
		this.game = game;
		this.currentPlayers = game.getPlayers();
		this.dealer = game.getDealer();
		this.smallBlind = game.getSmallBlind();
		this.bigBlind = game.getBigBlind();
		this.tableCards = new ArrayList<Card>();
		this.deck = new Deck();
		this.pot = 0;
		this.smallBlindPlayer = game.getSmallBlindPlayer();
		this.bigBlindPlayer = game.getBigBlindPlayer();
	}
	
	public void run() {
		// pre-flop
		 this.preflop();
		 System.out.println(game.getGameStatistiks());
		 for(Player player : currentPlayers){
			 System.out.println(player.getPlayerStatistiks());
		 }
		 // flop
		/* this.callFlop();
		 this.runBetPhase();
		 // turn 
		 this.betTurn();	
		 runBetPhase();
		 // river
		 this.betRiver();
		 runBetPhase();
		 // showdown
		 this.showdown();
		 game.distributePot();*/
		
	}
	
	private  void showdown() {
		// TODO Auto-generated method stub
		
	}


	private  void runBetPreflopPhase() {
		System.out.println("===========     BET PREFLOP PHASE     ===========");
		
	    System.out.println(getRoundStatistiks());
		
		boolean sameBetForAllPlayers = false;

		Player currentPlayer;
		Action currentAction;
		
		Scanner actionScanner;
		Scanner amountScanner;
		
		currentPlayer = this.getNext(getNext(getNext(dealer)));
		
		while(!sameBetForAllPlayers && currentPlayers.size()>1){
			
			actionScanner = new Scanner(System.in);
		
		    System.out.println(currentPlayer.getName()+" it's your turn ! \n"+
		    "You can: 'FOLD', 'CALL', 'RAISE', 'ALLIN' ");
		    switch (actionScanner.nextLine()) {
		    
			case "FOLD":
				currentPlayer.setAction(new FoldAction());
				if(this.actionIsPossible(currentPlayer)){
					executeAction(currentPlayer);
					currentPlayer = this.getNext(currentPlayer);
				}
				else
					System.out.println("You can't do this action, replay");
				break;
			case "CALL":
				currentPlayer.setAction(new CallAction());
				if(this.actionIsPossible(currentPlayer)){
					executeAction(currentPlayer);
					currentPlayer = this.getNext(currentPlayer);
				}
				else
					System.out.println("You can't do this action, replay");
				break;
			case "RAISE":
				System.out.println("Enter raise amount:");
				amountScanner = new Scanner(System.in);
				currentPlayer.setAction(new RaiseAction(amountScanner.nextInt()));
				if(this.actionIsPossible(currentPlayer)){
					executeAction(currentPlayer);
					currentPlayer = this.getNext(currentPlayer);
				}
				else
					System.out.println("You can't do this action, replay");
				break;
			case "ALLIN":
				currentPlayer.setAction(new AllInAction());
				if(this.actionIsPossible(currentPlayer)){
					executeAction(currentPlayer);
					currentPlayer = this.getNext(currentPlayer);
				}
				else
					System.out.println("You can't do this action, replay");
				break;

			default:
			    System.out.println("Autre chance !");
				break;
			}
		    
			sameBetForAllPlayers = hasSameBet(currentPlayers);
		}
		
		System.out.println("==========================   End of BET PREFLOP PHASE =================================");
	}
	
	private boolean hasSameBet(LinkedList<Player> playersList) {
		int bet = playersList.getFirst().getBet();
		for(Player player : playersList){
			if(player.getBet() != bet)
				return false;
		}
		return true;
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
			else 
				return false;
		case "Call":
			if(player.getCredit() >= this.currentBet - player.getBet())
				return true;
			else 
				return false;
		case "Check":
			if(currentBet == 0) 
				return true;
			else 
				return false;
		case "Fold":
			return true;
		case "Raise":
			if(player.getCredit() >= (this.currentBet - player.getBet()) + player.getAction().getAmount())
				return true;
			else 
				return false;
			
		default:
			return false;
		}
		
	}
	
	public Player getNext(Player prevPlayer){
		
		if(prevPlayer.equals(currentPlayers.getLast())) 
			return currentPlayers.getFirst();
		else{
			int prevPlayerIndex = currentPlayers.indexOf(prevPlayer);
			return currentPlayers.get(prevPlayerIndex+1);
		}
		
	}
		
	private void executeAction(Player player){
		int bet;
			switch (player.getAction().getName()) {
			case "All-in":				
				// changes the player state
				bet = player.getCredit();
				player.payCash(player.getCredit());
				player.setBet(player.getBet()+bet);
				// changes the round state
				actualizeCurrentBet(bet);
				
				break;
			case "Bet":
				// changes the player state
				bet = player.getAction().getAmount();
				player.payCash(bet);
				player.setBet(player.getBet()+bet);
				// changes the round state
				actualizeCurrentBet(bet);
				break;
			case "Call":
				// changes the player state
				bet = this.currentBet - player.getBet();
				player.payCash(bet);
				player.setBet(player.getBet()+bet);
				// changes the round state
				actualizeCurrentBet(bet);
				break;
			case "Check":
				// changes nothing for player and for round
				break;
			case "Fold":
				// just remove player to the currentPlayers list
				this.currentPlayers.remove(player);
				break;
			case "Raise":
				// changes the player state
				bet = (this.currentBet - player.getBet()) + player.getAction().getAmount();
				player.payCash(bet);
				player.setBet(player.getBet()+bet);
				// changes the round state
				actualizeCurrentBet(bet);
				break;

			default:
				System.out.println("No action is defined");
				break;
			}
	}
	
	private void actualizeCurrentBet(int newBet){
		if(newBet > this.currentBet){
			this.currentBet = newBet;
		}
	}
	
	/*private Action stringToAction(String stringAction, int amount){
		switch (stringAction) {
		case "ALLIN":
			return new AllInAction();
		case "BET":
			return new BetAction(amount);
		case "CALL":
			return new CallAction();
		case "CHECK":
			return new CheckAction();
		case "FOLD":
			return new FoldAction();
		case "RAISE":
			return new RaiseAction(amount);

		default:
			return null;
		}
	}*/


	private void payBlinds() {
		
		smallBlindPlayer.payCash(game.getSmallBlind());
		smallBlindPlayer.setBet(smallBlind);
		bigBlindPlayer.payCash(game.getBigBlind());
		bigBlindPlayer.setBet(bigBlind);
		this.currentBet = this.bigBlind;
		
		System.out.println("SB: "+this.smallBlindPlayer);
		System.out.println("BB: "+this.bigBlindPlayer);

	}


	private void preflop() {
		System.out.println("===========     PREFLOP     ===========");
		changeDealer();
	 	payBlinds();
	 	deal();
	 	runBetPreflopPhase();
	 	callFlop();
	}
	
	private  void changeDealer() {
		LinkedList<Player> players = game.getPlayers(); 
		if(game.getRoundNumber()!=0){
			int dealerIndex = game.getIndexOfDealer();
			if(dealerIndex == players.indexOf(players.getLast())){
				game.setDealer(players.getFirst());
			}
			else{
				game.setDealer(players.get(dealerIndex+1));
			}
		}
		
	}

	private void runBetPhase() {
		
		
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
		deck.pop();
		tableCards.add(deck.pop());
		tableCards.add(deck.pop());
		tableCards.add(deck.pop());
		System.out.println(this.displayTableCards());
	}

	/**
	 * Bets Turn 
	 */
	public void betTurn() {
		deck.pop();
		tableCards.add(deck.pop());
	}

	/**
	 * Bets river
	 */
	public void betRiver() {
		deck.pop();
		tableCards.add(deck.pop());
	}

	public LinkedList<Player> getPlayers() {
		return currentPlayers;
	}

	public void setPlayers(LinkedList<Player> players) {
		this.currentPlayers = players;
	}
	

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}
	
	public List<Card> getTableCards() {
		return tableCards;
	}

	public void setTableCards(List<Card> tableCards) {
		this.tableCards = tableCards;
	}

	
	/**
	 * Gets a winner or a winners list
	 * @param players
	 * @return
	 */
	public List<Player> getWinner(LinkedList<Player> players) {
		checkPlayersRanking(players);
		List<Player> winnerList = new ArrayList<Player>();
		Player winner = players.get(0);
		int winnerRank = HandUtil.getRankingToInt(winner);
		winnerList.add(winner);
		boolean checked;
		for (int i = 1; i < players.size(); i++) {
			checked = false;
			Player player = players.get(i);
			int playerRank = HandUtil.getRankingToInt(player);
			//Draw game
			if (winnerRank == playerRank) {
				if(winnerRank == HandEnum.ROYAL_FLUSH.ordinal() && !checked){
					winnerList.add(player);
					checked = true;
				}
				if(winnerRank == HandEnum.STRAIGHT_FLUSH.ordinal() && !checked ){

					int winnerStraightFlushRank = HandUtil.getStraightFlush(winner, tableCards).get(0).getRankToInt();
					int playerStraightFlushRank = HandUtil.getStraightFlush(player, tableCards).get(0).getRankToInt();
					if(playerStraightFlushRank == winnerStraightFlushRank){
						winnerList.add(player);
					}
					else if(playerStraightFlushRank > winnerStraightFlushRank ){
						winner = player;
						winnerList.clear();
						winnerList.add(winner);
					}	
					checked = true;
				}
				if(winnerRank == HandEnum.FOUR_OF_A_KIND.ordinal() && !checked ){
					List<Card> winnerFourList = HandUtil.getMergedCardList(winner, tableCards);
					List<Card> playerFourList = HandUtil.getMergedCardList(player, tableCards);

					int winnerFourRank = HandUtil.getFourOfAKind(winner, tableCards).get(0).getRankToInt();
					int playerFourRank = HandUtil.getFourOfAKind(player, tableCards).get(0).getRankToInt();
					if(playerFourRank == winnerFourRank){
						winnerFourList.removeAll(HandUtil.getFourOfAKind(winner, tableCards));
						int winnerHighCardRank = HandUtil.getHighCard(winnerFourList).getRankToInt();
						playerFourList.removeAll(HandUtil.getFourOfAKind(player, tableCards));
						int playerHighCardRank = HandUtil.getHighCard(playerFourList).getRankToInt();
						if(playerHighCardRank == winnerHighCardRank){
							winnerList.add(player);
						}
						else if(playerHighCardRank > winnerHighCardRank){
							winner = player;
							winnerList.clear();
							winnerList.add(winner);
						}
					}
					else if(playerFourRank > winnerFourRank ){
						winner = player;
						winnerList.clear();
						winnerList.add(winner);
					}	
					checked = true;
				}
				if(winnerRank == HandEnum.FULL.ordinal() && !checked ){
					int winnerFullRank = HandUtil.getThreeOfAKind(winner, tableCards).get(0).getRankToInt();
					int playerFullRank = HandUtil.getThreeOfAKind(player, tableCards).get(0).getRankToInt();
					if(playerFullRank > winnerFullRank ){
						winner = player;
						winnerList.clear();
						winnerList.add(winner);
					}
					checked = true;
				}
				if(winnerRank == HandEnum.FLUSH.ordinal() && !checked){
					Player highHandPlayer = checkHighCardWinner(winner, player);
					if (highHandPlayer != null && !winner.equals(highHandPlayer)) {
						winner = player;
						winnerList.clear();
						winnerList.add(winner);
					} else if (highHandPlayer == null) {
						//Draw in checkHighSequence and checkHighCardWinner
						winnerList.add(player);
					}
					checked = true;
				}
				if(winnerRank == HandEnum.STRAIGHT.ordinal() && !checked){
					Player highHandPlayer = checkHighCardWinner(winner, player);
					if (highHandPlayer != null && !winner.equals(highHandPlayer)) {
						winner = player;
						winnerList.clear();
						winnerList.add(winner);
					} else if (highHandPlayer == null) {
						//Draw in checkHighSequence and checkHighCardWinner
						winnerList.add(player);
					}
					checked = true;
				}
				if(winnerRank == HandEnum.THREE_OF_A_KIND.ordinal() && !checked ){
					List<Card> winnerThreeList = HandUtil.getMergedCardList(winner, tableCards);
					List<Card> playerThreeList = HandUtil.getMergedCardList(player, tableCards);

					int winnerThreeRank = HandUtil.getThreeOfAKind(winner, tableCards).get(0).getRankToInt();
					int playerThreeRank = HandUtil.getThreeOfAKind(player, tableCards).get(0).getRankToInt();
					if(playerThreeRank == winnerThreeRank){
						winnerThreeList.removeAll(HandUtil.getThreeOfAKind(winner, tableCards));
						int winnerHighCardRank = HandUtil.getHighCard(winnerThreeList).getRankToInt();
						playerThreeList.removeAll(HandUtil.getThreeOfAKind(player, tableCards));
						int playerHighCardRank = HandUtil.getHighCard(playerThreeList).getRankToInt();
						if(playerHighCardRank == winnerHighCardRank){
							winnerList.add(player);
						}
						else if(playerHighCardRank > winnerHighCardRank){
							winner = player;
							winnerList.clear();
							winnerList.add(winner);
						}
					}
					else if(playerThreeRank > winnerThreeRank ){
						winner = player;
						winnerList.clear();
						winnerList.add(winner);
					}	
					checked = true;
				}
				if(winnerRank == HandEnum.TWO_PAIR.ordinal() && !checked){
					List<Card> winnerTwoPair = HandUtil.getTwoPair(winner, tableCards);
					List<Card> playerTwoPair = HandUtil.getTwoPair(player, tableCards);
					int winnerTwoRank = HandUtil.getHighCard(winnerTwoPair).getRankToInt();
					int playerTwoRank = HandUtil.getHighCard(playerTwoPair).getRankToInt();
					if(playerTwoRank > winnerTwoRank){
						winner = player;
						winnerList.clear();
						winnerList.add(winner);
					}
					else if (playerTwoRank == winnerTwoRank){
						int sumWinnerTwoPair = HandUtil.sumRankingList(winnerTwoPair);
						int sumPlayerTwoPair = HandUtil.sumRankingList(playerTwoPair);
						
						if(sumWinnerTwoPair == sumPlayerTwoPair){
							int winnerAcolyteRank = HandUtil.getTwoPairAcolyte(winner, tableCards).getRankToInt();
							int playerAcolyteRank = HandUtil.getTwoPairAcolyte(player, tableCards).getRankToInt();
							if( playerAcolyteRank > winnerAcolyteRank){
								winner = player;
								winnerList.clear();
								winnerList.add(winner);
							}
							else{
								winnerList.add(player);
							}
						}
						else if(sumPlayerTwoPair > sumWinnerTwoPair){
							winner = player;
							winnerList.clear();
							winnerList.add(winner);
						}
					}
					checked = true;
				}
				if(winnerRank == HandEnum.ONE_PAIR.ordinal() && !checked){
					int winnerOnePairRank = HandUtil.getOnePair(winner, tableCards).get(0).getRankToInt();
					int playerOnePairRank = HandUtil.getOnePair(player, tableCards).get(0).getRankToInt();

					if(playerOnePairRank > winnerOnePairRank){
						winner = player;
						winnerList.clear();
						winnerList.add(winner);
					}
					else if (winnerOnePairRank == playerOnePairRank){
						Player highHandPlayer = checkHighCardWinner(winner, player);
						if (highHandPlayer != null && !winner.equals(highHandPlayer)) {
							winner = player;
							winnerList.clear();
							winnerList.add(winner);
						} else if (highHandPlayer == null) {
							//Draw in checkHighSequence and checkHighCardWinner
							winnerList.add(player);
						}
					}
					checked = true;
				}
				if(winnerRank == HandEnum.HIGH_CARD.ordinal() && !checked){
					Player highHandPlayer = checkHighCardWinner(winner, player);
					if (highHandPlayer != null && !winner.equals(highHandPlayer)) {
						winner = player;
						winnerList.clear();
						winnerList.add(winner);
					} else if (highHandPlayer == null) {
						//Draw in checkHighSequence and checkHighCardWinner
						winnerList.add(player);
					}
					checked = true;
				}
			
			} 
			else if (winnerRank < playerRank) {
				winner = player;
				winnerList.clear();
				winnerList.add(winner);
				checked = true;
			}
			winnerRank = HandUtil.getRankingToInt(winner);
		}

		return winnerList;
	}
	
	/**
	 * Compares the high card of two players
	 * @param player1
	 * @param player2
	 * @return the winner
	 */
	@SuppressWarnings("unchecked")
	private Player checkHighCardWinner(Player player1, Player player2) {
		Player winner = compareHighCard(player1, player1.getHighCard(),
				player2, player2.getHighCard());
		if (winner == null) {
			Card player1Card = HandUtil.getHighCard(player1,
					Collections.EMPTY_LIST);
			Card player2Card = HandUtil.getHighCard(player2,
					Collections.EMPTY_LIST);
			winner = compareHighCard(player1, player1Card, player2, player2Card);
			if (winner != null) {
				player1.setHighCard(player1Card);
				player2.setHighCard(player2Card);
			} else if (winner == null) {
				player1Card = HandUtil.getSecondHighCard(player1, player1Card);
				player2Card = HandUtil.getSecondHighCard(player2, player2Card);
				winner = compareHighCard(player1, player1Card, player2,
						player2Card);
				if (winner != null) {
					player1.setHighCard(player1Card);
					player2.setHighCard(player2Card);
				}
			}
		}
		return winner;
	}

	/**
	 * Compares High Card of two players
	 * @param player1
	 * @param player1HighCard
	 * @param player2
	 * @param player2HighCard
	 * @return
	 */
	private Player compareHighCard(Player player1, Card player1HighCard,
			Player player2, Card player2HighCard) {
		if (player1HighCard.getRankToInt() > player2HighCard.getRankToInt()) {
			return player1;
		} else if (player1HighCard.getRankToInt() < player2HighCard
				.getRankToInt()) {
			return player2;
		}
		return null;
	}
	
	/**
	 * Checks the players ranking
	 * @param players
	 */
	private void checkPlayersRanking(List<Player> players) {
		for (Player player : players) {
			HandUtil.checkRanking(player, tableCards);
		}
	}
	
	public String getRoundStatistiks(){
		
		System.out.println("===========    ROUND STATS     ===========");
		String ret = "";
		
		for(Player player : currentPlayers){
			ret = ret + player.getPlayerStatistiks() + "\n";	
		}
		ret = ret + this.dealer.getName() + " has the button/is the dealer \n";
		ret = ret + "Current bet : "+this.currentBet;
		
		return ret;
	}
	
	public String displayTableCards(){
		String ret = "";
		for(Card card : tableCards){
			ret = ret + card.toString() + "   ";	
		}
		return ret;
	}
	

	
	 
	 
	
}
