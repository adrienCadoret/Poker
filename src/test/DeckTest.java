package test;

import static org.junit.Assert.*;

import org.junit.Test;

import texasholdem.Deck;

public class DeckTest {

	@Test(expected = IllegalArgumentException.class)
	public void testFillDeck() {
		Deck deck = new Deck();
		//Test size with full deck (52)
		assertTrue(deck.getSize()==52);
		for (int i = 0; i < 52; i++) {
			assertNotNull(deck.pop());

		}
		deck.pop();
	}
	
	@Test
	public void testShuffle() {
		Deck deck = new Deck();
		String notShuffle = deck.getCards().toString();
		deck.shuffle();
		String shuffle = deck.getCards().toString();
		assertFalse(notShuffle.equals(shuffle));
	}

}
