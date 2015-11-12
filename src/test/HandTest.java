package test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import texasholdem.Card;
import texasholdem.HandEnum;
import texasholdem.HandUtil;
import texasholdem.Player;
import texasholdem.RankEnum;
import texasholdem.SuitEnum;

import org.junit.Test;


public class HandTest extends TestCase {

	/*
	 * 	01) ROYAL_FLUSH,
	 *	02) STRAIGHT_FLUSH,
	 *	03) FOUR_OF_A_KIND,
	 *	04) FULL,
	 *	05) FLUSH,
	 *	06) STRAIGHT,
	 *	07) THREE_OF_A_KIND,
	 *	08) TWO_PAIR,
	 *	09) ONE_PAIR,
	 *	10) HIGH_CARD
	 */
	@Test
	public void testCheckRoyalFlush() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setRoyalFlush(player, tablecards);
		HandUtil.checkRanking(player, tablecards);
		assertEquals(HandEnum.ROYAL_FLUSH, player.getHandEnum());
		assertEquals(HandUtil.getRoyalFlush(player, tablecards), player
				.getRankingList());
		assertEquals(new Integer(HandEnum.ROYAL_FLUSH.ordinal()), HandUtil.getRankingToInt(player));
	}

	@Test
	public void testCheckStraightFlush() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setStraightFlush(player, tablecards);
		HandUtil.checkRanking(player, tablecards);
		assertEquals(HandEnum.STRAIGHT_FLUSH, player.getHandEnum());
		assertEquals(HandUtil.getStraightFlush(player, tablecards), player
				.getRankingList());
		assertEquals(new Integer(HandEnum.STRAIGHT_FLUSH.ordinal()), HandUtil.getRankingToInt(player));
	}

	@Test
	public void testCheckFourOfAKind() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setFourOfAKind(player, tablecards);
		HandUtil.checkRanking(player, tablecards);
		assertEquals(HandEnum.FOUR_OF_A_KIND, player.getHandEnum());
		assertEquals(HandUtil.getFourOfAKind(player, tablecards), player
				.getRankingList());
		assertEquals(new Integer(HandEnum.FOUR_OF_A_KIND.ordinal()), HandUtil.getRankingToInt(player));
	}

	@Test
	public void testCheckFullHouse() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setFullHouse(player, tablecards);
		HandUtil.checkRanking(player, tablecards);
		assertEquals(HandEnum.FULL, player.getHandEnum());
		assertEquals(HandUtil.getFullHouse(player, tablecards), player
				.getRankingList());
		assertEquals(new Integer(HandEnum.FULL.ordinal()), HandUtil.getRankingToInt(player));
	}

	@Test
	public void testCheckFlush() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setFlush(player, tablecards);
		HandUtil.checkRanking(player, tablecards);
		assertEquals(HandEnum.FLUSH, player.getHandEnum());
		assertEquals(HandUtil.getFlush(player, tablecards), player
				.getRankingList());
		assertEquals(new Integer(HandEnum.FLUSH.ordinal()), HandUtil.getRankingToInt(player));
	}

	@Test
	public void testCheckStraight() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setStraight(player, tablecards);
		HandUtil.checkRanking(player, tablecards);
		assertEquals(HandEnum.STRAIGHT, player.getHandEnum());
		assertEquals(HandUtil.getStraight(player, tablecards), player
				.getRankingList());
		assertEquals(new Integer(HandEnum.STRAIGHT.ordinal()), HandUtil.getRankingToInt(player));
	}

	@Test
	public void testCheckThreeOfAKind() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setThreeOfAKind(player, tablecards);
		HandUtil.checkRanking(player, tablecards);
		assertEquals(HandEnum.THREE_OF_A_KIND, player.getHandEnum());
		assertEquals(HandUtil.getThreeOfAKind(player, tablecards), player
				.getRankingList());
		assertEquals(new Integer(HandEnum.THREE_OF_A_KIND.ordinal()), HandUtil.getRankingToInt(player));
	}

	@Test
	public void testCheckTwoPair() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setTwoPair(player, tablecards);
		Card acolyte = new Card(SuitEnum.HEARTS, RankEnum.CARD_6);
		HandUtil.checkRanking(player, tablecards);
		assertEquals(HandUtil.getTwoPairAcolyte(player, tablecards),acolyte);
		assertEquals(HandEnum.TWO_PAIR, player.getHandEnum());
		assertEquals(HandUtil.getTwoPair(player, tablecards), player
				.getRankingList());
		assertEquals(new Integer(HandEnum.TWO_PAIR.ordinal()), HandUtil.getRankingToInt(player));
	}

	@Test
	public void testCheckOnePair() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setOnePair(player, tablecards);
		HandUtil.checkRanking(player, tablecards);
		assertEquals(HandEnum.ONE_PAIR, player.getHandEnum());
		assertEquals(HandUtil.getOnePair(player, tablecards), player
				.getRankingList());
		assertEquals(new Integer(HandEnum.ONE_PAIR.ordinal()), HandUtil.getRankingToInt(player));
	}

	@Test
	public void testCheckHighCard() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setHighCard(player, tablecards);
		HandUtil.checkRanking(player, tablecards);
		assertEquals(HandEnum.HIGH_CARD, player.getHandEnum());
		assertEquals(HandUtil.getHighCard(player, tablecards), player
				.getRankingList().get(0));
		assertEquals(new Integer(HandEnum.HIGH_CARD.ordinal()), HandUtil.getRankingToInt(player));
	}

	@Test
	public void testIsRoyalFlush() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setRoyalFlush(player, tablecards);

		List<Card> royalFlushList = new ArrayList<Card>();
		royalFlushList.addAll(tablecards);
		royalFlushList.add(player.getCards()[0]);
		royalFlushList.add(player.getCards()[1]);

		List<Card> result = HandUtil.getRoyalFlush(player, tablecards);
		assertTrue(isSameCardList(royalFlushList, result));
	}

	@Test
	public void testIsRoyalFlushNotSequence() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.JACK);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.QUEEN));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.ACE));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.KING));

		List<Card> royalFlushList = new ArrayList<Card>();
		royalFlushList.addAll(tablecards);
		royalFlushList.add(player.getCards()[0]);
		royalFlushList.add(player.getCards()[1]);

		List<Card> result = HandUtil.getRoyalFlush(player, tablecards);
		assertTrue(isSameCardList(royalFlushList, result));
	}

	@Test
	public void testIsNotRoyalFlushNotSameSuit() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.ACE);
		player.getCards()[1] = new Card(SuitEnum.HEARTS, RankEnum.CARD_10);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_2));
		tablecards.add(new Card(SuitEnum.SPADES, RankEnum.CARD_3));
		tablecards.add(new Card(SuitEnum.SPADES, RankEnum.CARD_4));

		assertNull(HandUtil.getRoyalFlush(player, tablecards));
	}

	@Test
	public void testIsStraightFlush() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setStraightFlush(player, tablecards);

		List<Card> straightFlushList = new ArrayList<Card>();
		straightFlushList.addAll(tablecards);
		straightFlushList.add(player.getCards()[0]);
		straightFlushList.add(player.getCards()[1]);

		List<Card> result = HandUtil.getStraightFlush(player, tablecards);
		assertTrue(isSameCardList(straightFlushList, result));
	}

	@Test
	public void testIsStraightFlushNotSequence() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.CARD_2);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_3);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_4));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_8));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_6));

		assertNull(HandUtil.getStraightFlush(player, tablecards));
	}

	@Test
	public void testIsNotStraightFlushNoSameSuit() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.CARD_2);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_3);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_4));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_5));
		tablecards.add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_6));

		assertNull(HandUtil.getStraightFlush(player, tablecards));
	}

	@Test
	public void testIsFourOfAKind() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setFourOfAKind(player, tablecards);

		List<Card> fourOfAKindList = new ArrayList<Card>();
		fourOfAKindList.add(tablecards.get(0));
		fourOfAKindList.add(tablecards.get(2));
		fourOfAKindList.add(player.getCards()[0]);
		fourOfAKindList.add(player.getCards()[1]);

		List<Card> result = HandUtil.getFourOfAKind(player, tablecards);
		assertTrue(isSameCardList(fourOfAKindList, result));
	}

	@Test
	public void testIsNotFourOfAKind() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = new Card(SuitEnum.DIAMONDS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_10));
		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.KING));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		assertNull(HandUtil.getFourOfAKind(player, tablecards));
	}

	@Test
	public void testIsFullHouse() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setFullHouse(player, tablecards);

		List<Card> fullHouseList = new ArrayList<Card>();
		fullHouseList.add(player.getCards()[0]);
		fullHouseList.add(tablecards.get(1));
		fullHouseList.add(tablecards.get(2));
		fullHouseList.add(player.getCards()[1]);
		fullHouseList.add(tablecards.get(0));

		List<Card> result = HandUtil.getFullHouse(player, tablecards);
		assertTrue(isSameCardList(fullHouseList, result));
	}

	@Test
	public void testIsNotFullHouse() {
		Card CardThree1 = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);
		Card CardThree2 = new Card(SuitEnum.HEARTS, RankEnum.ACE);
		Card CardThree3 = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);

		Card CardTwo1 = new Card(SuitEnum.CLUBS, RankEnum.JACK);
		Card CardTwo2 = new Card(SuitEnum.HEARTS, RankEnum.JACK);

		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = CardThree3;
		player.getCards()[1] = CardThree2;

		tablecards.add(CardTwo1);
		tablecards.add(CardThree2);
		tablecards.add(CardThree1);

		List<Card> result = HandUtil.getFullHouse(player, tablecards);
		assertNull(result);
	}

	@Test
	public void testIsFlush() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setFlush(player, tablecards);

		List<Card> flushList = new ArrayList<Card>();
		flushList.addAll(tablecards);
		flushList.add(player.getCards()[0]);
		flushList.add(player.getCards()[1]);

		List<Card> result = HandUtil.getFlush(player, tablecards);
		assertTrue(isSameCardList(flushList, result));
	}

	@Test
	public void testIsNotFlush() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);

		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.CARD_2));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.KING));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		assertNull(HandUtil.getFlush(player, tablecards));
	}

	@Test
	public void testIsStraight() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setStraight(player, tablecards);

		List<Card> straightList = new ArrayList<Card>();
		straightList.addAll(tablecards);
		straightList.add(player.getCards()[0]);
		straightList.add(player.getCards()[1]);

		List<Card> result = HandUtil.getStraight(player, tablecards);
		assertTrue(isSameCardList(straightList, result));
	}

	@Test
	public void testIsNotStraight() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = new Card(SuitEnum.DIAMONDS, RankEnum.CARD_2);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_3);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_8));
		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.CARD_2));
		tablecards.add(new Card(SuitEnum.SPADES, RankEnum.CARD_6));

		assertNull(HandUtil.getStraight(player, tablecards));
	}

	@Test
	public void testIsThreeOfAKind() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setThreeOfAKind(player, tablecards);

		List<Card> listThreeOfAKind = new ArrayList<Card>();

		listThreeOfAKind.add(tablecards.get(1));
		listThreeOfAKind.add(player.getCards()[0]);
		listThreeOfAKind.add(player.getCards()[1]);

		List<Card> result = HandUtil.getThreeOfAKind(player, tablecards);
		assertTrue(isSameCardList(listThreeOfAKind, result));
	}

	@Test
	public void testIsNotThreeOfAKind() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = new Card(SuitEnum.DIAMONDS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_2));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.KING));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		assertNull(HandUtil.getThreeOfAKind(player, tablecards));
	}

	@Test
	public void testIsTwoPair() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setTwoPair(player, tablecards);

		List<Card> fullHouseList = new ArrayList<Card>();
		fullHouseList.add(player.getCards()[0]);
		fullHouseList.add(tablecards.get(0));
		fullHouseList.add(player.getCards()[1]);
		fullHouseList.add(tablecards.get(1));

		List<Card> result = HandUtil.getTwoPair(player, tablecards);
		assertTrue(isSameCardList(fullHouseList, result));
	}

	@Test
	public void testIsNotTwoPair() {
		Card CardThree1 = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);
		Card CardThree2 = new Card(SuitEnum.HEARTS, RankEnum.CARD_10);
		Card CardThree3 = new Card(SuitEnum.SPADES, RankEnum.CARD_10);

		Card CardTwo1 = new Card(SuitEnum.CLUBS, RankEnum.JACK);
		Card CardTwo2 = new Card(SuitEnum.HEARTS, RankEnum.JACK);

		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = CardThree3;
		player.getCards()[1] = CardThree3;

		tablecards.add(CardTwo1);
		tablecards.add(CardThree2);
		tablecards.add(CardThree1);

		List<Card> result = HandUtil.getTwoPair(player, tablecards);
		assertNull(result);
	}

	@Test
	public void testIsOnePair() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		setOnePair(player, tablecards);

		List<Card> listOnePair = new ArrayList<Card>();
		listOnePair.add(player.getCards()[0]);
		listOnePair.add(player.getCards()[1]);

		List<Card> result = HandUtil.getOnePair(player, tablecards);
		assertTrue(isSameCardList(listOnePair, result));
	}

	@Test
	public void testIsNotOnePair() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = new Card(SuitEnum.DIAMONDS, RankEnum.CARD_2);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_3));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.KING));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		assertNull(HandUtil.getOnePair(player, tablecards));
	}

	@Test
	public void testGetHighCardRepeatedCards() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		Card fourCard = new Card(SuitEnum.CLUBS, RankEnum.CARD_4);
		player.getCards()[0] = fourCard;
		player.getCards()[1] = fourCard;

		tablecards.add(fourCard);
		tablecards.add(fourCard);
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_2));

		assertEquals(fourCard, HandUtil.getHighCard(player, tablecards));
	}

	@Test
	public void testGetHighCardAce() {
		List<Card> tablecards = new ArrayList<Card>();
		Player player = new Player("John");
		player.getCards()[0] = new Card(SuitEnum.HEARTS, RankEnum.CARD_9);
		player.getCards()[1] = new Card(SuitEnum.SPADES, RankEnum.CARD_7);

		Card AceCard = new Card(SuitEnum.CLUBS, RankEnum.ACE);
		tablecards.add(AceCard);
		assertEquals(AceCard, HandUtil.getHighCard(player, tablecards));
	}

	public Boolean isSameCardList(List<Card> list1, List<Card> list2) {
		return list1.containsAll(list2) && list1.size() == list2.size();
	}

	private void setRoyalFlush(Player player, List<Card> tablecards) {
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.JACK);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.QUEEN));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.KING));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.ACE));
	}

	private void setStraightFlush(Player player, List<Card> tablecards) {
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.CARD_2);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_3);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_4));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_5));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_6));
	}

	private void setFourOfAKind(Player player, List<Card> tablecards) {
		player.getCards()[0] = new Card(SuitEnum.DIAMONDS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_10));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.KING));
		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.CARD_10));
	}

	private void setFullHouse(Player player, List<Card> tablecards) {
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.HEARTS, RankEnum.JACK);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.JACK));
		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.CARD_10));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_10));
	}

	private void setFlush(Player player, List<Card> tablecards) {
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_3);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_2));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.KING));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.ACE));
	}

	private void setStraight(Player player, List<Card> tablecards) {
		player.getCards()[0] = new Card(SuitEnum.DIAMONDS, RankEnum.CARD_4);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_5);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_2));
		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		tablecards.add(new Card(SuitEnum.SPADES, RankEnum.CARD_6));
	}

	private void setThreeOfAKind(Player player, List<Card> tablecards) {
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.SPADES, RankEnum.CARD_10);
		tablecards.add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.CARD_10));
		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.CARD_2));
	}

	private void setTwoPair(Player player, List<Card> tablecards) {
		player.getCards()[0] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.JACK);

		tablecards.add(new Card(SuitEnum.SPADES, RankEnum.CARD_10));
		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.JACK));
		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.CARD_2));
		tablecards.add(new Card(SuitEnum.HEARTS, RankEnum.CARD_6));
	}

	private void setOnePair(Player player, List<Card> tablecards) {
		player.getCards()[0] = new Card(SuitEnum.DIAMONDS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_10);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_2));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.KING));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.ACE));
	}

	private void setHighCard(Player player, List<Card> tablecards) {
		player.getCards()[0] = new Card(SuitEnum.DIAMONDS, RankEnum.CARD_10);
		player.getCards()[1] = new Card(SuitEnum.CLUBS, RankEnum.CARD_9);

		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.CARD_2));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.KING));
		tablecards.add(new Card(SuitEnum.CLUBS, RankEnum.ACE));
	}
}
