package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import texasholdem.Card;
import texasholdem.Game;
import texasholdem.Player;
import texasholdem.RankEnum;
import texasholdem.Round;
import texasholdem.RoundUtil;
import texasholdem.SuitEnum;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class WinnerTest extends TestCase {
	
	Game game;
	Round round;
	
	@Before
	public void initGame(){
		
		game = new Game(25, 100000);
		game.addPlayer(new Player("John"));
		game.addPlayer(new Player("Clara"));
		
		round = new Round();
		round.setPlayers(game.getPlayers());
		round.setTableCards(new ArrayList<Card>());
	}
	
	@Test
	public void testHighCard() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.QUEEN));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_5));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_8),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),1);

		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	
	@Test
	public void testSameHighCard() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_8),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_8),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.JACK),1);


		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}

	@Test
	public void testOnePair() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_2),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),1);

		
		

		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testOnePairWithHighCard() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.JACK),1);

		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testOnePairWithNoHighCard() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);

		
		

		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList,round.getPlayers());
	}
	
	@Test
	public void testTwoPair() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.ACE),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_3),1);
		

		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testTwoSamePair() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_4),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_2),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),1);
		

		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testThreeOfAKind() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_2));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_7));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_8));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.KING),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_7),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		

		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testSameThreeOfAKind() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_2));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_8));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.JACK),1);
		

		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	
	@Test
	public void testStraight() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_5));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_6));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_2),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		
		game.addPlayer(new Player("Clark"));
		round.getPlayers().get(2).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		round.getPlayers().get(2).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		

		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		List<Player> winners = new ArrayList<Player>();
		winners.add(round.getPlayers().get(1));
		winners.add(round.getPlayers().get(2));
		
		assertEquals(winnerList,winners);
	}
	
	
	
	@Test
	public void testFlush() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_5));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_6));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_2),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_7),1);
		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testFlushSameHighCard() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_5));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_6));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_8),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testFullWithSameThreeOfAKind() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_5));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_6));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_6),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_9),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_5),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testFull() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.ACE));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_5));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_5));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.ACE),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_6),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.KING),1);
		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testFourOfAKind() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_5));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.ACE),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),1);
		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testSameFourOfAKind() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.ACE));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.ACE));
		round.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_5));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_3),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.QUEEN),1);
		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testStraightFlush() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_5));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_6));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.KING));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_2),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.QUEEN),1);
		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testSameStraightFlush() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_3));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_4));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_5));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_6));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_7));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.QUEEN),1);
		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList,round.getPlayers());
	}
	
	@Test
	public void testRoyalFlush() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.ACE));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.QUEEN));
		round.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.JACK));
		round.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_7));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_10),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.QUEEN),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_2),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),1);
		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList.get(0),round.getPlayers().get(0));
	}
	
	@Test
	public void testSameRoyalFlush() {
		//Basic tests
		initGame();
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.QUEEN));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.JACK));
		round.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_10));

		round.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_2),0);
		round.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.QUEEN),1);
		
		
		round.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_3),0);
		round.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.QUEEN),1);
		
		List<Player> winnerList = RoundUtil.getWinner(round.getPlayers(), round.getTableCards());
		
		assertEquals(winnerList,round.getPlayers());
	}
	
}
