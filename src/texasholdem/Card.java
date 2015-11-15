package texasholdem;
/**
 * A card in a deck for a poker game without jokers
 * 
 * @author Cadoret Adrien
 */

public class Card{

	/**
	 * The suit 
	 */
	private SuitEnum suit;
	
	/**
	 * The rank
	 */
	private RankEnum rank;

	/**
	 * Card constructor with a suit and a rank
	 * @param suit
	 * @param rank
	 */
	public Card(SuitEnum suit, RankEnum rank) {
		this.suit = suit;
		this.rank = rank;
	}

	/**
	 * Gets the card suit
	 * @return the suit
	 */
	public SuitEnum getSuit() {
		return suit;
	}

	/**
	 * Gets the card rank
	 * @return the rank
	 */
	public RankEnum getRank() {
		return rank;
	}

	/**
	 * Gets the card rank order referring to RankEnum Enum
	 * @return the card rank order
	 */
	public Integer getRankToInt() {
		return rank.ordinal();
	}

	
	/**
	 * Compares two Cards
	 * @return true if equality, else false
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (!(obj instanceof Card)) {
			return false;
		} else {
			Card card2 = (Card) obj;
			return rank.equals(card2.getRank()) && suit.equals(card2.getSuit());
		}
	}

	/**
	 * @return displayed card string
	 */
	@Override
	public String toString() {
		switch (suit) {
		case HEARTS:
			return "\u0003" + rank.toString();
		case DIAMONDS:
			return "\u0004" + rank.toString();
		case CLUBS:
			return "\u0005" + rank.toString();
		case SPADES:
			return "\u0006" + rank.toString();
		}
		return null;
	}
}
