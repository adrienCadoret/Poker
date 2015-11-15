package texasholdem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generates the deck of a game with 52 cards
 * @author Cadoret Adrien
 *
 */

public class Deck{

	/**
	 * Cards list
	 */
	private List<Card> cards;
	
	/** 
	 * Random object using to shuffle a deck
	 */
	private Random random;

	/**
	 * Constructs a deck without shuffling
	 */
	public Deck() {
		random = new Random();
		fillDeck();
		shuffle();
	}

	/**
	 * Fill the deck with 52 cards
	 */
	private void fillDeck() {
		cards = new ArrayList<Card>();
		for (SuitEnum suit : SuitEnum.values()) {
			for (RankEnum rank : RankEnum.values()) {
				cards.add(new Card(suit, rank));
			}
		}
	}
	
	/**
	 * Gets the size deck. Helps to know if a deck is empty in a game.
	 * @return the deck size
	 */
	public int getSize(){
		return this.cards.size();
	}
	
	/**
	 * Shuffles the deck
	 */
	public void shuffle(){
		Collections.shuffle(this.cards, random);
	}
	
	/**
	 * Pop a card of a deck
	 * @throws IllegalArgumentException if deck is empty
	 * @return the Card deck
	 */
	public Card pop() {
		if(this.getSize() == 0) throw new IllegalArgumentException("Deck is empty");
		return cards.remove(0);
	}
	
	/**
	 * Get the cards of the deck
	 * @return the card list
	 */
	public List<Card> getCards(){
		return cards;
	}
}
