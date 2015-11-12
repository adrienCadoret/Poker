package texasholdem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	private List<Player> players;

	/**
	 * The table cards
	 */
	private List<Card> tableCards;
	
	/**
	 * The small blind
	 */
	private static int SMALL_BLIND;
	
	/**
	 * The big blind
	 */
	private static int BIG_BLIND;
	
	/**
	 * The pot
	 */
	private int pot = 0;
	
	/**
	 * The dealer
	 */
	private Player dealer;

	/**
	 * Constructor
	 * @param players
	 * @param smallBlind
	 * @param bigBlind
	 * @param startingCash
	 */
	public Game(List<Player> players, int smallBlind, int bigBlind, int startingCash) {
		this.deck = new Deck();
		SMALL_BLIND = smallBlind;
		BIG_BLIND = bigBlind;
		this.pot = 0;
		tableCards = new ArrayList<Card>();
		this.players = players;
		// initializes the cash of each player
		for(Player p : players){
			p.setCredit(startingCash);
		}
	}
	
	/**
	 * Distributes the pot to winners
	 * @param game winners
	 */
	public void distributePot(List<Player> winners){
		int winnersNum = winners.size();
		int sharedGain = this.getPot() / winnersNum;  
		for(Player winner : winners){
			winner.win(sharedGain);
		}
		clearPot();
	}
	
	/**
	 * Gets players
	 * @return the players
	 */
	public List<Player> getPlayers(){
		return this.players;
	}
	
	/**
	 * Clears the pot
	 */
	public void clearPot(){
		this.pot = 0;
	}
	
	/**
	 * Gets the pot (integer)
	 * @return the pot
	 */
	public int getPot(){
		return this.pot;
	}
	
	/**
	 * Set the pot 
	 * @param money
	 */
	private void setPot(int money){
		this.pot = this.pot + money;
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
	}

	/**
	 * deal = give two cards to each player
	 */
	public void deal() {
		for (Player player : players) {
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
	 * Bets ruver
	 */
	public void betRiver() {
		deck.pop();
		tableCards.add(deck.pop());
	}

	/**
	 * Gets a winner or a winners list
	 * @param players
	 * @return
	 */
	public List<Player> getWinner(List<Player> players) {
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
	 * Gets table cards
	 * @return
	 */
	public List<Card> getTableCards() {
		return tableCards;
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
