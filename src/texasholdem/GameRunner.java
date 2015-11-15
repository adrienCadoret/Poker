package texasholdem;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class GameRunner {
	
	private Game game;

	
	public GameRunner(){
	}
	
	public void run(){
		// Initialize the whole game 
		createGame();
			 
		System.out.println(game.toString());
			 
		addPlayers();
				 
		System.out.println(game.getPlayers());
		
		/*checkGameForRunning();*/
		
		/*startGame();*/
				 
		randomlyInitDealer();
				 
		System.out.println(game.getGameStatistiks());
		
		Round round = game.createRound(); 
		
		game.setRunning(true);
		
		while(game.isRunning()){
			round.run();
			System.out.println(game.getGameStatistiks());
			game.setRunning(false);
		}
				 
		/* System.out.println(game.getPlayerStatistiks(game.getPlayers().get(1)));*/
				 
	}

	private  void checkGameForRunning() {
		// TODO Auto-generated method stub
		
	}

	

	private  void addPlayers() {
		
		boolean addingPlayer = true;
		String name;
		
		while(addingPlayer){
			Scanner scanner = new Scanner(System.in);
	        
	        System.out.println("Enter a player name:  (Enter 'finish' to run game)");
	        name=scanner.nextLine();
	        
	        if(name.equals("finish")){
	        	addingPlayer = false;
	        }
	        else if(name instanceof String){
		        Player player = new Player(name);
		        game.addPlayer(player);
	        }
	        else{
	        	System.out.println("Please enter a string");
	        }

		}
		
		
	}

	private  void createGame() {
		Scanner scanner = new Scanner(System.in);
		
        int smallBlind,bigBlind, initialCash;
        
        System.out.println("Enter Small Blind:");
        smallBlind=scanner.nextInt();
        scanner.nextLine(); //This is needed to pick up the new line
        System.out.println("By default, the Big Blind equals to: "+smallBlind*2);
        System.out.println("Enter Initial Cash gived to each player:");
        initialCash=scanner.nextInt();
        
        game = new Game(smallBlind, initialCash);
		
	}
	
	private  void randomlyInitDealer(){
		Random randomizer = new Random();
		Player dealer = game.getPlayers().get(randomizer.nextInt(game.getPlayers().size()));
		game.setDealer(dealer);
	}
	

}
