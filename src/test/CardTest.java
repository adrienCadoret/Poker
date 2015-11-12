package test;

import org.junit.Test;

import junit.framework.TestCase;
import texasholdem.Card;
import texasholdem.RankEnum;
import texasholdem.SuitEnum;


public class CardTest extends TestCase{

	@Test
	public void testGetRankToInt() {
		// Same Rank
		Card cardA = new Card(SuitEnum.SPADES, RankEnum.ACE);
		Card cardB = new Card(SuitEnum.SPADES, RankEnum.ACE);
		assertEquals(cardA.getRankToInt(), cardB.getRankToInt());
		Card cardC = new Card(SuitEnum.SPADES, RankEnum.KING);
		assertFalse(cardA.getRankToInt().equals(cardC.getRankToInt()));
	}

	@Test
	public void testEquals() {
		// Same instance
		Card cardA = new Card(SuitEnum.SPADES, RankEnum.CARD_2);
		assertEquals(cardA, cardA);
		// Same suit and rank
		Card cardB = new Card(SuitEnum.SPADES, RankEnum.CARD_2);
		assertEquals(cardA, cardB);
		// Different rank
		Card cardC = new Card(SuitEnum.SPADES, RankEnum.CARD_3);
		assertFalse(cardA.equals(cardC));
		// Different suit
		Card cardD = new Card(SuitEnum.CLUBS, RankEnum.CARD_2);
		assertFalse(cardA.equals(cardD));
		// Different suit and rank
		Card cardE = new Card(SuitEnum.HEARTS, RankEnum.KING);
		assertFalse(cardA.equals(cardE));
	}

}
