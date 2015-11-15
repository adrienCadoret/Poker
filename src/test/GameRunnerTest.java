package test;

import texasholdem.GameRunner;

public class GameRunnerTest {

	public static void main(String[] args) {

		GameRunner gameRunner = new GameRunner();
		
        System.out.println("\u2665 This should be a Hearts suit symbol.");
        System.out.println("\u2666 This should be a Diamonds suit symbol.");
        System.out.println("\u2663 This should be a Clubs suit symbol.");
        System.out.println("\u2660 This should be a Spades suit symbol.");
        
        System.out.print("\u2666");
		
		gameRunner.run();
	
	}

}
