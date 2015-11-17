package texasholdem;


import java.util.Scanner;

/**
 * This class allows to manage the game progress. 
 * @author Cadoret Adrien
 *
 */
public class GameRunner {
	
	/**
	 * The game related to this
	 */
	private Game game;

	/**
	 * Constructor
	 */
	public GameRunner(){
	}
	
	/**
	 * Runs the game and progresses it
	 */
	public void run(){
		
		Round round;
		
		// Initialize the whole game 
		createGame();
		addPlayers();
				 
		System.out.println(game.getPlayers());
		
		checkStartGame();
	
		while(!game.hasWinner()){
			round = game.createRound();
			round.run();
			game.setRoundNumber(game.getRoundNumber()+1);
			game.getGameStatistiks();
		}
		
		System.out.println("THE WINNER IS : "+game.getWinner().getName());
		
		endGame();
				 			 
	}

	/**
	 * Allows to restart a game or quit the console
	 */
	private void endGame() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        String answer;
		
        System.out.println("The game is done. Do you want to restart ? (Say 'YES' or 'NO')");
        answer = scanner.nextLine();
        if(answer.equals("YES")){
        	this.run();
        }
        else if(answer.equals("NO")){
        	System.out.println("Thanks you for playing ! Bye bye");
        	System.exit(0);
        }
        else{
        	this.endGame();
        }
        
        
		
	}

	private void checkStartGame() {
		if(game.getPlayers().size()<2){
			System.out.println("A game must have at less 2 players");
			addPlayers();
		}
	}

	

	private  void addPlayers() {
		
		boolean addingPlayer = true;
		String name;
		
		while(addingPlayer){
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
	        
	        System.out.println("Enter a player name:  (Enter 'start' to run game)");
	        name=scanner.nextLine();
	        
	        if(name.equals("start")){
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
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
        int smallBlind=0, initialCash=0;
        
        System.out.println("Enter Small Blind:");
        smallBlind=scanner.nextInt();
        scanner.nextLine(); //This is needed to pick up the new line
        System.out.println("By default, the Big Blind equals to: "+smallBlind*2);
        while(initialCash < smallBlind*2){
            System.out.println("Enter Initial Cash gived to each player (must be > bigBlind):");
            initialCash=scanner.nextInt();
        }
        game = new Game(smallBlind, initialCash);
		
	}
	
	

}
