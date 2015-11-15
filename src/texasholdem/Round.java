package texasholdem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
	}
	
	public void run() {
		// pre-flop
		 this.preflop();
		 System.out.println(game.getGameStatistiks());
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
		// TODO Auto-generated method stub
		
	}

	private  void distributeCards() {
		// TODO Auto-generated method stub
		
	}

	private void payBlinds() {
		
		game.getSmallBlindPlayer().payCash(game.getSmallBlind());
		game.getBigBlindPlayer().payCash(game.getBigBlind());

	}


	private void preflop() {
		changeDealer();
	 	payBlinds();
	 	/*distributeCards();
	 	runBetPreflopPhase();*/
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
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * deal = give two cards to each player
	 */
	public void deal() {
		for (Player player : currentPlayers) {
			player.getCards()[0] = deck.pop();
			player.getCards()[1] = deck.pop();
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

	
	 
	 
	
}
