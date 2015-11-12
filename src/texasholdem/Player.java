package texasholdem;
import java.util.List;

import actions.Action;

/**
 * Class representing a poker player
 * @author Cadoret Adrien
 *
 */

public class Player{
	
	/**
	 * Player name
	 */
	private String name;
	
	/** Client application responsible for the actual behavior. */
    private final Client client;
	
    private boolean hasCards;
	/**
	 * Player credit/cash
	 */
	private int credit;
	
    /** Current bet. */
    private int bet;
	
	/**
	 * True if is dealer, false else.
	 */
	private boolean isDealer;

	/**
	 * The cards which are in this player hand
	 */
	private Card[] cards = new Card[2];

	/**
	 * The best hand which the player has.
	 */
	private HandEnum HandEnum = null;

	/**
	 * The list which represents the HandEnum
	 */
	private List<Card> rankingList = null;

	/**
	 * Player high card
	 */
	private Card highCard = null;
	
    /** Last action performed. */
    private Action action;
	
	/**
	 * Constructs the player
	 * @param name
	 */
	public Player(String name, Client client){
		this.name = name;
		this.client = client;
		hasCards = false;
	}
	
	/**
	 * Get high card
	 * @return high card 
	 */
	public Card getHighCard() {
		return highCard;
	}

	/**
	 * Sets player high card
	 * @param highCard
	 */
	public void setHighCard(Card highCard) {
		this.highCard = highCard;
	}

	/**
	 * Gets the HandEnum
	 * @return the HandEnum
	 */
	public HandEnum getHandEnum() {
		return HandEnum;
	}

	/**
	 * Sets the HandEnum (used when the game is running)
	 * @param HandEnum
	 */
	public void setHandEnum(HandEnum HandEnum) {
		this.HandEnum = HandEnum;
	}

	/**
	 * Gets player ranking list
	 * @return card list representing the ranking
	 */
	public List<Card> getRankingList() {
		return rankingList;
	}

	/**
	 * Sets the ranking list
	 * @param rankingList
	 */
	public void setRankingList(List<Card> rankingList) {
		this.rankingList = rankingList;
	}

	/**
	 * Gets the two card of the player
	 * @return
	 */
	public Card[] getCards() {
		return cards;
	}

	/**
	 * Sets the player card. This method is used whenever the game is starting
	 * @param cards
	 */
	public void setCards(Card[] cards) {
		if (cards != null) {
            if (cards.length == 2) {
                this.cards = cards;
                hasCards = true;
                System.out.format("[CHEAT] %s's cards:\t%s\n", name, cards);
            } else {
                throw new IllegalArgumentException("Invalid number of cards");
            }
        }
	}
	
	/**
     * Prepares the player for another hand.
     */
    public void resetHandAndCards() {
        this.setRankingList(null);
        this.setCard(null,0);
        this.setCard(null,1);
        this.setHandEnum(null);
        this.setHighCard(null);
        this.setBet(0);
        hasCards = false;
    }
	
	/**
	 * Sets a card with index 0 or 1 (used for testing)
	 * @param card
	 * @param index
	 * @exception IllegalArgumentException if index is false
	 */
	public void setCard(Card card, int index) {
		if(index != 1 && index != 0)
			throw new IllegalArgumentException("No index being 0 or 1");
		this.cards[index] = card;
	}

	/**
	 * Gets the player name
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
     * Returns the client.
     * 
     * @return The client.
     */
    public Client getClient() {
        return client;
    }

	/**
	 * Gets the credit 
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}

	/**
	 * Sets the credit of a player (pay or get pot)
	 * @param credit
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}

	/**
	 * Knows if a player is the game dealer
	 * @return true if he is, false else.
	 */
	public boolean isDealer() {
		return isDealer;
	}

	/**
	 * Sets the player dealer 
	 * @param isDealer
	 */
	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	   /**
     * Indicates whether this player is all-in.
     * 
     * @return True if all-in, otherwise false.
     */
    public boolean isAllIn() {
        return hasCards() && (this.getCredit() == 0);
    }
    
    /**
     * Returns whether the player has his hole cards dealt.
     * 
     * @return True if the hole cards are dealt, otherwise false.
     */
    public boolean hasCards() {
        return hasCards;
    }


    /**
     * Posts the small blind.
     * 
     * @param blind
     *            The small blind.
     */
    public void postSmallBlind(int blind) {
        action = Action.SMALL_BLIND;
        this.credit -= blind;
        bet += blind;
    }

    /**
     * Posts the big blinds.
     * 
     * @param blind
     *            The big blind.
     */
    public void postBigBlind(int blind) {
        action = Action.BIG_BLIND;
        this.credit -= blind;
        bet += blind;
    }
    
    /**
     * Pays an amount of cash.
     * 
     * @param amount
     *            The amount of cash to pay.
     */
    public void payCash(int amount) {
        if (amount > this.credit) {
            throw new IllegalStateException("Player asked to pay more cash than he owns!");
        }
        this.credit -= amount;
    }
    
    /**
     * Wins an amount of money.
     * 
     * @param amount
     *            The amount won.
     */
    public void win(int amount) {
        credit += amount;
    }
	
	
}
