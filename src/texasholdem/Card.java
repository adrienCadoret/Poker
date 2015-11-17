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
		String suitString = "";
		String rankString = "";
		
		switch (this.suit) {
		case HEARTS:
			// ASCII Code
			suitString = "\u2665";
			break;
		case DIAMONDS:
			suitString = "\u2666";
			break;
		case CLUBS:
			suitString = "\u2663";
			break;
		case SPADES:
			suitString = "\u2660";
			break;
		}
		
		switch (this.rank) {
		case CARD_2:
			rankString = "2";
			break;
		case CARD_3:
			rankString = "3";
			break;
		case CARD_4:
			rankString = "4";
			break;
		case CARD_5:
			rankString = "5";
			break;
		case CARD_6:
			rankString = "6";
			break;
		case CARD_7:
			rankString = "7";
			break;
		case CARD_8:
			rankString = "8";
			break;
		case CARD_9:
			rankString = "9";
			break;
		case CARD_10:
			rankString = "10";
			break;
		case JACK:
			rankString = "JACK";
			break;
		case QUEEN:
			rankString = "QUEEN";
			break;
		case KING:
			rankString = "KING";
			break;
		case ACE:
			rankString = "ACE";
			break;
		}
		
		return suitString+rankString;
	}
}
