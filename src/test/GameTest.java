package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import texasholdem.Card;
import texasholdem.Game;
import texasholdem.Player;
import texasholdem.RankEnum;
import texasholdem.SuitEnum;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GameTest extends TestCase {
	
	Game game;
	
	@Before
	public void initGame(){
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("John", null));
		players.add(new Player("Clara", null));
		
		game = new Game(players, 25, 50, 100000);
		
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
	}
	
	@Test
	public void testHighCard() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.QUEEN));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_5));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_8),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),1);

		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	
	@Test
	public void testSameHighCard() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_8),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_8),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.JACK),1);


		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}

	@Test
	public void testOnePair() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_2),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),1);

		
		

		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testOnePairWithHighCard() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.JACK),1);

		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testOnePairWithNoHighCard() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);

		
		

		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList,game.getPlayers());
	}
	
	@Test
	public void testTwoPair() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.ACE),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_3),1);
		

		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testTwoSamePair() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_2));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_4),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_2),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),1);
		

		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testThreeOfAKind() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_2));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_7));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_8));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.KING),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_7),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		

		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testSameThreeOfAKind() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_2));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_8));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.JACK),1);
		

		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	
	@Test
	public void testStraight() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_5));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_6));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_2),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		
		game.addPlayer(new Player("Clark", null));
		game.getPlayers().get(2).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		game.getPlayers().get(2).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		

		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		List<Player> winners = new ArrayList<Player>();
		winners.add(game.getPlayers().get(1));
		winners.add(game.getPlayers().get(2));
		
		assertEquals(winnerList,winners);
	}
	
	@Test
	public void testFlush() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_5));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_6));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_2),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_7),1);
		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testFlushSameHighCard() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_5));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_6));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_8),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testFullWithSameThreeOfAKind() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_5));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_6));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_6),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_9),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_5),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testFull() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.ACE));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_5));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_5));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.ACE),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_6),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.KING),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.KING),1);
		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testFourOfAKind() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_5));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.ACE),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),1);
		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testSameFourOfAKind() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.ACE));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.ACE));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.ACE));
		game.getTableCards().add(new Card(SuitEnum.CLUBS, RankEnum.CARD_5));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_3),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.QUEEN),1);
		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testStraightFlush() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_5));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_6));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.KING));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_7),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_2),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.QUEEN),1);
		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testSameStraightFlush() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_3));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_4));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_5));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_6));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_7));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.KING),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.SPADES, RankEnum.QUEEN),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_10),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.DIAMONDS, RankEnum.QUEEN),1);
		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList,game.getPlayers());
	}
	
	@Test
	public void testRoyalFlush() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.ACE));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.QUEEN));
		game.getTableCards().add(new Card(SuitEnum.HEARTS, RankEnum.JACK));
		game.getTableCards().add(new Card(SuitEnum.DIAMONDS, RankEnum.CARD_7));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_10),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.QUEEN),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_2),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.ACE),1);
		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList.get(0),game.getPlayers().get(0));
	}
	
	@Test
	public void testSameRoyalFlush() {
		//Basic tests
		initGame();
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.ACE));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.KING));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.QUEEN));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.JACK));
		game.getTableCards().add(new Card(SuitEnum.SPADES, RankEnum.CARD_10));

		game.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.CARD_2),0);
		game.getPlayers().get(0).setCard(new Card(SuitEnum.HEARTS, RankEnum.QUEEN),1);
		
		
		game.getPlayers().get(1).setCard(new Card(SuitEnum.SPADES, RankEnum.CARD_3),0);
		game.getPlayers().get(1).setCard(new Card(SuitEnum.HEARTS, RankEnum.QUEEN),1);
		
		List<Player> winnerList = game.getWinner(game.getPlayers());
		
		assertEquals(winnerList,game.getPlayers());
	}
	

	/*@Test
	public void testDrawGameTwoPlayers() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_10));
		game.getTableCards().add(new Card(SPADES, ACE));
		dealer.getCards()[0] = new Card(DIAMONDS, CARD_2);
		dealer.getCards()[1] = new Card(SPADES, CARD_2);
		player.getCards()[0] = new Card(CLUBS, CARD_2);
		player.getCards()[1] = new Card(HEARTS, CARD_2);
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(2, winnerList.size());
		assertEquals(ONE_PAIR, dealer.getRankingEnum());
		assertEquals(ONE_PAIR, player.getRankingEnum());
	}

	@Test
	public void testPlayerWinDrawGameBestRanking() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_10));
		game.getTableCards().add(new Card(SPADES, ACE));
		dealer.getCards()[0] = new Card(CLUBS, CARD_10);
		dealer.getCards()[1] = new Card(HEARTS, CARD_2);
		player.getCards()[0] = new Card(CLUBS, CARD_2);
		player.getCards()[1] = new Card(HEARTS, ACE);
		//assertEquals(GameEnum.PLAYER_WINNER_BEST_RANKING, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(player, winnerList.get(0));
		assertEquals(ONE_PAIR, dealer.getRankingEnum());
		assertEquals(ONE_PAIR, player.getRankingEnum());
	}

	@Test
	public void testDealerWinDrawGameHighCard() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_10));
		dealer.getCards()[0] = new Card(CLUBS, CARD_10);
		dealer.getCards()[1] = new Card(HEARTS, ACE);
		player.getCards()[0] = new Card(CLUBS, CARD_2);
		player.getCards()[1] = new Card(HEARTS, CARD_10);
		//assertEquals(GameEnum.DEALER_WINNER_HIGH_CARD, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(dealer, winnerList.get(0));
		assertEquals(ONE_PAIR, dealer.getRankingEnum());
		assertEquals(ONE_PAIR, player.getRankingEnum());
	}

	@Test
	public void testDealerWinStraighFlush() {
		//Basic tests
		Game game = new Game();
		IPlayer player1 = new Player();
		IPlayer player2 = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player1, player2, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_3));
		game.getTableCards().add(new Card(SPADES, CARD_4));
		game.getTableCards().add(new Card(SPADES, CARD_5));
		game.getTableCards().add(new Card(CLUBS, QUEEN));
		dealer.getCards()[0] = new Card(SPADES, CARD_6);
		dealer.getCards()[1] = new Card(SPADES, CARD_7);
		player1.getCards()[0] = new Card(SPADES, CARD_10);
		player1.getCards()[1] = new Card(DIAMONDS, CARD_10);
		player2.getCards()[0] = new Card(SPADES, CARD_2);
		player2.getCards()[1] = new Card(DIAMONDS, CARD_2);
		//assertEquals(GameEnum.DEALER_WINNER, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(dealer, winnerList.get(0));
		assertEquals(STRAIGHT_FLUSH, dealer.getRankingEnum());
		assertEquals(ONE_PAIR, player1.getRankingEnum());
	}

	@Test
	public void testPlayerWinFourOfAKind() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_3));
		game.getTableCards().add(new Card(SPADES, CARD_4));
		game.getTableCards().add(new Card(HEARTS, CARD_10));
		game.getTableCards().add(new Card(CLUBS, CARD_10));
		dealer.getCards()[0] = new Card(SPADES, ACE);
		dealer.getCards()[1] = new Card(SPADES, CARD_2);
		player.getCards()[0] = new Card(SPADES, CARD_10);
		player.getCards()[1] = new Card(DIAMONDS, CARD_10);
		//assertEquals(GameEnum.PLAYER_WINNER, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(player, winnerList.get(0));
		assertEquals(ONE_PAIR, dealer.getRankingEnum());
		assertEquals(FOUR_OF_A_KIND, player.getRankingEnum());
	}

	@Test
	public void testDealerWinFullHouse() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_3));
		game.getTableCards().add(new Card(SPADES, CARD_4));
		game.getTableCards().add(new Card(HEARTS, CARD_10));
		game.getTableCards().add(new Card(CLUBS, CARD_10));
		dealer.getCards()[0] = new Card(HEARTS, CARD_3);
		dealer.getCards()[1] = new Card(CLUBS, CARD_3);
		player.getCards()[0] = new Card(SPADES, CARD_10);
		player.getCards()[1] = new Card(SPADES, CARD_2);
		//assertEquals(GameEnum.DEALER_WINNER, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(dealer, winnerList.get(0));
		assertEquals(FULL_HOUSE, dealer.getRankingEnum());
		assertEquals(THREE_OF_A_KIND, player.getRankingEnum());
	}

	@Test
	public void testPlayerWinFlush() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_3));
		game.getTableCards().add(new Card(SPADES, CARD_4));
		game.getTableCards().add(new Card(SPADES, CARD_7));
		dealer.getCards()[0] = new Card(HEARTS, CARD_5);
		dealer.getCards()[1] = new Card(CLUBS, CARD_6);
		player.getCards()[0] = new Card(SPADES, CARD_10);
		player.getCards()[1] = new Card(SPADES, CARD_2);
		//assertEquals(GameEnum.PLAYER_WINNER, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(player, winnerList.get(0));
		assertEquals(STRAIGHT, dealer.getRankingEnum());
		assertEquals(FLUSH, player.getRankingEnum());
	}

	@Test
	public void testDealerWinStraight() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_3));
		game.getTableCards().add(new Card(SPADES, CARD_4));
		game.getTableCards().add(new Card(SPADES, CARD_7));
		dealer.getCards()[0] = new Card(HEARTS, CARD_5);
		dealer.getCards()[1] = new Card(CLUBS, CARD_6);
		player.getCards()[0] = new Card(CLUBS, CARD_10);
		player.getCards()[1] = new Card(HEARTS, CARD_10);
		//assertEquals(GameEnum.DEALER_WINNER, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(dealer, winnerList.get(0));
		assertEquals(STRAIGHT, dealer.getRankingEnum());
		assertEquals(ONE_PAIR, player.getRankingEnum());
	}

	@Test
	public void testDealerWinThreeOfAKind() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_10));
		game.getTableCards().add(new Card(SPADES, ACE));
		dealer.getCards()[0] = new Card(HEARTS, ACE);
		dealer.getCards()[1] = new Card(CLUBS, ACE);
		player.getCards()[0] = new Card(CLUBS, CARD_6);
		player.getCards()[1] = new Card(HEARTS, QUEEN);
		//assertEquals(GameEnum.DEALER_WINNER, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(dealer, winnerList.get(0));
		assertEquals(THREE_OF_A_KIND, dealer.getRankingEnum());
		assertEquals(HIGH_CARD, player.getRankingEnum());
	}

	@Test
	public void testPlayerWinTwoPair() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_10));
		game.getTableCards().add(new Card(SPADES, ACE));
		dealer.getCards()[0] = new Card(HEARTS, CARD_10);
		dealer.getCards()[1] = new Card(HEARTS, QUEEN);
		player.getCards()[0] = new Card(CLUBS, CARD_10);
		player.getCards()[1] = new Card(HEARTS, ACE);
		//assertEquals(GameEnum.PLAYER_WINNER, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(player, winnerList.get(0));
		assertEquals(ONE_PAIR, dealer.getRankingEnum());
		assertEquals(TWO_PAIR, player.getRankingEnum());
	}

	@Test
	public void testDealerWinOnePair() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_10));
		dealer.getCards()[0] = new Card(CLUBS, CARD_10);
		dealer.getCards()[1] = new Card(HEARTS, CARD_7);
		player.getCards()[0] = new Card(CLUBS, ACE);
		player.getCards()[1] = new Card(HEARTS, QUEEN);
		//assertEquals(GameEnum.DEALER_WINNER, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(dealer, winnerList.get(0));
		assertEquals(ONE_PAIR, dealer.getRankingEnum());
		assertEquals(HIGH_CARD, player.getRankingEnum());
	}

	@Test
	public void testDrawGameOnePairDealerWinSecondHighCard() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(DIAMONDS, CARD_6));
		game.getTableCards().add(new Card(CLUBS, QUEEN));
		game.getTableCards().add(new Card(DIAMONDS, CARD_9));
		game.getTableCards().add(new Card(DIAMONDS, ACE));
		game.getTableCards().add(new Card(CLUBS, CARD_6));
		dealer.getCards()[0] = new Card(DIAMONDS, CARD_10);
		Card highCardDealer = new Card(SPADES, CARD_2);
		dealer.getCards()[1] = highCardDealer;
		player.getCards()[0] = new Card(HEARTS, CARD_10);
		Card highCardPlayer = new Card(SPADES, CARD_3);
		player.getCards()[1] = highCardPlayer;
		//assertEquals(GameEnum.PLAYER_WINNER_HIGH_CARD, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(player, winnerList.get(0));
		assertEquals(ONE_PAIR, dealer.getRankingEnum());
		assertEquals(ONE_PAIR, player.getRankingEnum());
		assertEquals(highCardPlayer, player.getHighCard());
		assertEquals(highCardDealer, dealer.getHighCard());
	}

	@Test
	public void testPlayerWinHighCard() {
		//Basic tests
		Game game = new Game();
		IPlayer player = new Player();
		IPlayer dealer = new Player();
		game.newGame(new Deck(), player, dealer);
		game.deal();
		game.callFlop();
		game.betRiver();
		game.betTurn();
		game.getTableCards().clear();
		game.getTableCards().add(new Card(SPADES, CARD_4));
		dealer.getCards()[0] = new Card(CLUBS, CARD_10);
		dealer.getCards()[1] = new Card(HEARTS, CARD_7);
		player.getCards()[0] = new Card(CLUBS, ACE);
		player.getCards()[1] = new Card(HEARTS, QUEEN);
		//assertEquals(GameEnum.PLAYER_WINNER_BEST_RANKING, game.getWinner());
		List<IPlayer> winnerList = game.getWinner();
		assertEquals(1, winnerList.size());
		assertEquals(player, winnerList.get(0));
		assertEquals(HIGH_CARD, dealer.getRankingEnum());
		assertEquals(HIGH_CARD, player.getRankingEnum());
	} */
}
