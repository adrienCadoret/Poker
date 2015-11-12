package texasholdem;
import static texasholdem.HandEnum.*;
import static texasholdem.RankEnum.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Useful class to check the ranking of each player
 * @author Cadoret
 *
 */

public class HandUtil {

	/**
	 * Private construct
	 */
	private HandUtil() {
	}

	/**
	 * Referring to hand enumeration, this methods returns the hand order 
	 * @param player
	 * @return the hand order
	 */
	public static Integer getRankingToInt(Player player) {
		return player.getHandEnum().ordinal();
	}

	/**
	 * Checks the player ranking
	 * @param player
	 * @param tableCards
	 */
	public static void checkRanking(Player player, List<Card> tableCards) {

		//HIGH_CARD
		Card highCard = getHighCard(player, tableCards);
		player.setHighCard(highCard);

		//ROYAL_FLUSH
		List<Card> rankingList = getRoyalFlush(player, tableCards);
		if (rankingList != null) {
			setHandEnumAndList(player, ROYAL_FLUSH, rankingList);
			return;
		}
		//STRAIGHT_FLUSH
		rankingList = getStraightFlush(player, tableCards);
		if (rankingList != null) {
			setHandEnumAndList(player, STRAIGHT_FLUSH,
					rankingList);
			return;
		}
		//FOUR_OF_A_KIND
		rankingList = getFourOfAKind(player, tableCards);
		if (rankingList != null) {
			setHandEnumAndList(player, FOUR_OF_A_KIND,
					rankingList);
			return;
		}
		//FULL_HOUSE
		rankingList = getFullHouse(player, tableCards);
		if (rankingList != null) {
			setHandEnumAndList(player, FULL, rankingList);
			return;
		}
		//FLUSH
		rankingList = getFlush(player, tableCards);
		if (rankingList != null) {
			setHandEnumAndList(player, FLUSH, rankingList);
			return;
		}
		//STRAIGHT
		rankingList = getStraight(player, tableCards);
		if (rankingList != null) {
			setHandEnumAndList(player, STRAIGHT, rankingList);
			return;
		}
		//THREE_OF_A_KIND
		rankingList = getThreeOfAKind(player, tableCards);
		if (rankingList != null) {
			setHandEnumAndList(player, THREE_OF_A_KIND,
					rankingList);
			return;
		}
		//TWO_PAIR
		rankingList = getTwoPair(player, tableCards);
		if (rankingList != null) {
			setHandEnumAndList(player, TWO_PAIR, rankingList);
			return;
		}
		//ONE_PAIR
		rankingList = getOnePair(player, tableCards);
		if (rankingList != null) {
			setHandEnumAndList(player, ONE_PAIR, rankingList);
			return;
		}
		//HIGH_CARD
		player.setHandEnum(HIGH_CARD);
		List<Card> highCardRankingList = new ArrayList<Card>();
		highCardRankingList.add(highCard);
		player.setRankingList(highCardRankingList);
		return;
	}

	/**
	 * Gets the Royal Flush of a player
	 * @param player
	 * @param tableCards
	 * @return the card list representing the royal flush. Returns null if the player hasn't got a royal flush
	 */
	public static List<Card> getRoyalFlush(Player player, List<Card> tableCards) {
		/*if (!isSameSuit(player, tableCards)) {
			return null;
		}*/

		List<RankEnum> rankEnumList = toRankEnumList(player, tableCards);

		if (rankEnumList.contains(CARD_10)
				&& rankEnumList.contains(JACK)
				&& rankEnumList.contains(QUEEN)
				&& rankEnumList.contains(KING)
				&& rankEnumList.contains(ACE)) {

			return getMergedCardList(player, tableCards);
		}

		return null;
	}

	/**
	 * Gets the Straight Flush of a player
	 * @param player
	 * @param tableCards
	 * @return the card list representing the Straight flush. Returns null if the player hasn't got a Straight flush
	 */
	public static List<Card> getStraightFlush(Player player,
			List<Card> tableCards) {
		return getSequence(player, tableCards, 5, true);
	}

	/**
	 * Gets the Four Of A King of a player
	 * @param player
	 * @param tableCards
	 * @return the card list representing the . Returns null if the player hasn't got a four of a kind
	 */
	public static List<Card> getFourOfAKind(Player player,
			List<Card> tableCards) {
		List<Card> mergedList = getMergedCardList(player, tableCards);
		return checkPair(mergedList, 4);
	}

	/**
	 * Gets the Full House of a player
	 * @param player
	 * @param tableCards
	 * @return the card list representing the Full House. Returns null if the player hasn't got a full house
	 */
	public static List<Card> getFullHouse(Player player, List<Card> tableCards) {
		List<Card> mergedList = getMergedCardList(player, tableCards);
		List<Card> threeList = checkPair(mergedList, 3);
		if (threeList != null) {
			mergedList.removeAll(threeList);
			List<Card> twoList = checkPair(mergedList, 2);
			if (twoList != null) {
				threeList.addAll(twoList);
				return threeList;
			}
		}
		return null;
	}

	/**
	 * Gets the Flush of a player
	 * @param player
	 * @param tableCards
	 * @return the card list representing the flush. Returns null if the player hasn't got a flush
	 */
	public static List<Card> getFlush(Player player, List<Card> tableCards) {
		List<Card> mergedList = getMergedCardList(player, tableCards);
		List<Card> flushList = new ArrayList<Card>();

		for (Card card1 : mergedList) {
			for (Card card2 : mergedList) {
				if (card1.getSuit().equals(card2.getSuit())) {
					if (!flushList.contains(card1)) {
						flushList.add(card1);
					}
					if (!flushList.contains(card2)) {
						flushList.add(card2);
					}
				}
			}
			if (flushList.size() == 5) {
				return flushList;
			}
			flushList.clear();
		}
		return null;
	}

	/**
	 * Gets the Straight of a player
	 * @param player
	 * @param tableCards
	 * @return the card list representing the straight. Returns null if the player hasn't got a straight
	 */
	public static List<Card> getStraight(Player player, List<Card> tableCards) {
		return getSequence(player, tableCards, 5, false);
	}

	/**
	 * Gets the Three of a kind of a player
	 * @param player
	 * @param tableCards
	 * @return the card list representing the Three of a kind. Returns null if the player hasn't got a three of a kind
	 */
	public static List<Card> getThreeOfAKind(Player player,
			List<Card> tableCards) {
		List<Card> mergedList = getMergedCardList(player, tableCards);
		return checkPair(mergedList, 3);
	}

	public static List<Card> getTwoPair(Player player, List<Card> tableCards) {
		List<Card> mergedList = getMergedCardList(player, tableCards);
		List<Card> twoPair1 = checkPair(mergedList, 2);
		if (twoPair1 != null) {
			mergedList.removeAll(twoPair1);
			List<Card> twoPair2 = checkPair(mergedList, 2);
			if (twoPair2 != null) {
				twoPair1.addAll(twoPair2);
				return twoPair1;
			}
		}
		return null;
	}

	/**
	 * Gets the One Pair of a player
	 * @param player
	 * @param tableCards
	 * @return the card list representing the One Pair. Returns null if the player hasn't got a one pair
	 */
	public static List<Card> getOnePair(Player player, List<Card> tableCards) {
		List<Card> mergedList = getMergedCardList(player, tableCards);
		return checkPair(mergedList, 2);
	}

	/**
	 * Gets the High Card of a player with the tableCards in parameter
	 * @param player
	 * @param tableCards
	 * @return the card representing high card. 
	 */
	public static Card getHighCard(Player player, List<Card> tableCards) {
		List<Card> allCards = new ArrayList<Card>();
		allCards.addAll(tableCards);
		allCards.add(player.getCards()[0]);
		allCards.add(player.getCards()[1]);

		Card highCard = allCards.get(0);
		for (Card card : allCards) {
			if (card.getRankToInt() > highCard.getRankToInt()) {
				highCard = card;
			}
		}
		return highCard;
	}
	
	/**
	 * Gets the High Card of a card list
	 * @param tableCards
	 * @return the card representing high card. 
	 */
	public static Card getHighCard(List<Card> cards) {
		Card highCard = cards.get(0);
		for (Card card : cards) {
			if (card.getRankToInt() > highCard.getRankToInt()) {
				highCard = card;
			}
		}
		return highCard;
	}
	
	/**
	 * Gets the Second High Card of a player with the tableCards in parameter
	 * @param player
	 * @param tableCards
	 * @return the card representing second high card. 
	 */
	public static Card getSecondHighCard(Player player, Card card) {
		if (player.getCards()[0].equals(card)) {
			return player.getCards()[1];
		}
		return player.getCards()[0];
	}
	
	/**
	 * Sum of the player cards rank
	 * @param player
	 * @return the sum
	 */
	public static Integer sumRankingList(Player player) {
		Integer sum = 0;
		for (Card card : player.getRankingList()) {
			sum += card.getRankToInt();
		}
		return sum;
	}
	
	/**
	 * Sum of the list cards rank
	 * @param cards
	 * @return
	 */
	public static Integer sumRankingList(List<Card> cards) {
		Integer sum = 0;
		for (Card card : cards) {
			sum += card.getRankToInt();
		}
		return sum;
	}
	
	/**
	 * Gets the sequence card list of a player 
	 * @param player
	 * @param tableCards
	 * @param sequenceSize
	 * @param compareSuit
	 * @return
	 */
	private static List<Card> getSequence(Player player,
			List<Card> tableCards, Integer sequenceSize, Boolean compareSuit) {
		List<Card> orderedList = getOrderedCardList(player, tableCards);
		List<Card> sequenceList = new ArrayList<Card>();

		Card cardPrevious = null;
		for (Card card : orderedList) {
			if (cardPrevious != null) {
				if ((card.getRankToInt() - cardPrevious.getRankToInt()) == 1) {
					if (!compareSuit
							|| cardPrevious.getSuit().equals(card.getSuit())) {
						if (sequenceList.size() == 0) {
							sequenceList.add(cardPrevious);
						}
						sequenceList.add(card);
					}
				} else {
					if (sequenceList.size() == sequenceSize) {
						return sequenceList;
					}
					sequenceList.clear();
				}
			}
			cardPrevious = card;
		}

		return (sequenceList.size() == sequenceSize) ? sequenceList : null;
	}

	/**
	 * Merges the player cards and these which are on the table 
	 * @param player
	 * @param tableCards
	 * @return
	 */
	public static List<Card> getMergedCardList(Player player,
			List<Card> tableCards) {
		List<Card> merged = new ArrayList<Card>();
		merged.addAll(tableCards);
		merged.add(player.getCards()[0]);
		merged.add(player.getCards()[1]);
		return merged;
	}

	/**
	 * Returns the sorted list 
	 * @param player
	 * @param tableCards
	 * @return
	 */
	private static List<Card> getOrderedCardList(Player player,
			List<Card> tableCards) {
		List<Card> ordered = getMergedCardList(player, tableCards);
		Collections.sort(ordered, new Comparator<Card>() {

			public int compare(Card c1, Card c2) {
				return c1.getRankToInt() < c2.getRankToInt() ? -1 : 1;
			}

		});
		return ordered;
	}

	/**
	 * Returns a card list which have all the same rank
	 * @param mergedList
	 * @param pairSize the size (2 for pair, 3 for triple etc.)
	 * @return the list if there is a pair
	 */
	private static List<Card> checkPair(List<Card> mergedList, Integer pairSize) {
		List<Card> checkedPair = new ArrayList<Card>();
		for (Card card1 : mergedList) {
			checkedPair.add(card1);
			for (Card card2 : mergedList) {
				if (!card1.equals(card2)
						&& card1.getRank().equals(card2.getRank())) {
					checkedPair.add(card2);
				}
			}
			if (checkedPair.size() == pairSize) {
				return checkedPair;
			}
			checkedPair.clear();
		}
		return null;
	}

	/**
	 * Checks if the player cards and table cards are the same suit
	 * @param player
	 * @param tableCards
	 * @return
	 */
	private static Boolean isSameSuit(Player player, List<Card> tableCards) {
		SuitEnum suit = player.getCards()[0].getSuit();

		if (!suit.equals(player.getCards()[1].getSuit())) {
			return false;
		}

		for (Card card : tableCards) {
			if (!card.getSuit().equals(suit)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Lists the cards rank of a player 
	 * @param player
	 * @param tableCards
	 * @return
	 */
	private static List<RankEnum> toRankEnumList(Player player,
			List<Card> tableCards) {
		List<RankEnum> rankEnumList = new ArrayList<RankEnum>();

		for (Card card : tableCards) {
			rankEnumList.add(card.getRank());
		}

		rankEnumList.add(player.getCards()[0].getRank());
		rankEnumList.add(player.getCards()[1].getRank());

		return rankEnumList;
	}

	/**
	 * Sets the player HandEnum and the ranking list
	 * @param player
	 * @param handEnum
	 * @param rankingList
	 */
	private static void setHandEnumAndList(Player player,
			HandEnum handEnum, List<Card> rankingList) {
		player.setHandEnum(handEnum);
		player.setRankingList(rankingList);
	}
	
	/**
	 * Gets acolyte when two players have the same Two-Pair. It is the high card of the rest
	 * @param player
	 * @param tableCards
	 * @return
	 */
	public static Card getTwoPairAcolyte(Player player, List<Card> tableCards) {
		List<Card> mergedList = getMergedCardList(player, tableCards);
		mergedList.removeAll(checkPair(mergedList, 2));
		mergedList.removeAll(checkPair(mergedList, 2));
		
		Card highCard = mergedList.get(0);
		for (Card card : mergedList) {
			if (card.getRankToInt() > highCard.getRankToInt()) {
				highCard = card;
			}
		}
		return highCard;
	}
	
}