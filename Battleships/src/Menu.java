import java.util.Scanner;
/**
 * Main class of the Battleships project that contains the controls.
 * 
 * 
 * Menu processes user choices and calls methods from Grid class to complete the operations.
 * 
 * Menu holds a field of a Grid object to call Grid class methods.
 * 
 *  
 * @author Gerda Ugne Pupelyte
 * @version 1.0
 *
 */
public class Menu {
	
	//Initiated object game based on Grid class
	private Grid game;
	
	/**
	 * Constructor of the menu class
	 */
	public Menu()
	{
		game = new Grid();
		
	}
	/** 
	 * Main method for Battleships project
	 */
	public static void main(String[] args) {
		

		Menu mainMenu = new Menu();
		mainMenu.processUserChoices(mainMenu);

	}
	

	/**
	 * A method that displays available menu options
	 * to choose from
	 * 
	 * 
	 * 1. Start a new game - starts a new game against a non-responsive AI;
	 * 2. Load previously saved game - loads the last game user had saved and allows to play from that point;
	 * 3. Save game - saves current game progress into a text file;
	 * 4. Start a new game against functional AI - starts a new game against a responsive AI.
	 */
	public void displayMenu()
	{
		System.out.println("1. Start a new game against non-responsive AI");
		System.out.println("2. Load previously saved game");
		System.out.println("3. Save game");
		System.out.println("4. Start a new game against responsive AI");
		System.out.println("0. Exit");
		
	}
	
	/**
	 * A method that allows user to make menu choices
	 * and processes them by calling appropriate methods
	 * 
	 * @param menu - a menu object for calling other menu methods
	 */
	public void processUserChoices(Menu menu)
	{
		Scanner s = new Scanner(System.in);
		
		//Local variable to hold user's input
		String usersInput = null;
		
		//Boolean that stores input whether to open optional menu
		boolean openOptions = false;
		
		//User is allowed to make choices until they exit the game
		do
		{
			//User is prompted to make a choice
			System.out.println("Please choose one of the menu options:");
			
			//Menu choices are displayed
			displayMenu();
			usersInput = s.nextLine();
			
			// Choice 1 - Start a new game
			if(usersInput.equals("1"))
			{
				//All grid values are cleared to start a new game
				game.setEmptyValues();
				
				//AI ships are randomly placed on the enemy board
				game.placeAllComputerShips();
				
				//Displays the board to the user
				game.displayedBoard();
				
				while (game.getSuccessfulShots()<19) // as 19 shots are needed to destroy the entire enemy fleet
				{
					//User is prompted to shoot
					openOptions = game.fireShot();
					
					//If user chose to open options by entering -1,
					// additional options are opened
					if(openOptions == true)
					{
						openAdditionalOptions(menu);						
					}
					
					//The updated board is displayed after each shot
					game.displayedBoard();
					
					//If the user has made 19 successful shots they win
					if(game.getSuccessfulShots() == 19) break;
					

				}
				
				//Statistics are printed at the end of game
				System.out.println("Congratulations, you won!");
				System.out.println("You made " + game.getShotsFired() + " shots, of which " + game.getSuccessfulShots() + " were successful.");

			}
			
			//Choice 2 - Load previously saved game
			else if(usersInput.equals("2"))
			{
				//Saved game contents are loaded onto the board
				game.loadGameFromFile();
				System.out.println("Game loaded!");
				
				//The board is displayed to the user
				game.displayedBoard();
				
				//The system determines which game mode was saved: 
				// if there is no data about AI shots, it's determined to be the mode against non-responsive AI;
				// else the game mode is against a responsive AI.
				
				//If the mode is against a responsive AI:
				if(game.getAIShotsFired() > 0)
				{
					do
					{
						//User is prompted to shoot
						openOptions = game.fireShot();
						
						//If user destroyed the entire enemy fleet the loop is broken since they won
						if(game.getSuccessfulShots()== 19) break;
						
						//If user chose to open options by entering -1,
						// additional options are opened
						if(openOptions == true)
						{
							openAdditionalOptions(menu);						
						}
									
						//Board is updated and player is shown the board with the shots they made
						System.out.println("Your shots:");
						game.displayedBoard();
						
						//AI has their turn and shoots
						game.AIFireShot();
						
						//If AI destroyed the entire fleet the loop is broken since it won
						if(game.getAISuccessfulShots() == 19) break;
						
						//User's board is updated and the player is shown where the AI made the shot
						System.out.println("AI shots:");
						game.displayUsersBoard();
						
						
						if(game.getSuccessfulShots() == 19 || game.getAISuccessfulShots() == 19) break; 
						
					} while (game.getAISuccessfulShots()<19 && game.getSuccessfulShots()<19); //Loops until one wins
					
					// Statistics are printed depending on the outcome of the game
					
					if (game.getSuccessfulShots() == 19) 
					{
						System.out.println("Congratulations, you won!");
						System.out.println("You made " + game.getShotsFired() + " shots, of which " + game.getSuccessfulShots() + " were successful.");
						System.out.println("AI made " + game.getAIShotsFired() + " shots, of which " + game.getAISuccessfulShots() + " were succesful.");
					}
					else if(game.getAISuccessfulShots() == 19)
					{
						System.out.println("You lost!");
						System.out.println("You made " + game.getShotsFired() + " shots, of which " + game.getSuccessfulShots() + " were successful.");
						System.out.println("AI made " + game.getAIShotsFired() + " shots, of which " + game.getAISuccessfulShots() + " were succesful.");
					}
					
				}
				
				//If the mode is against non-responsive AI:
				else
				{
				
					
					while (game.getSuccessfulShots()<19) // as 19 shots are needed to destroy the enemy fleet
					{
						
						//User is prompted to shoot
						openOptions = game.fireShot();
						
						//If user chose to open options by entering -1,
						// additional options are opened
						if(openOptions == true)
						{
							openAdditionalOptions(menu);						
						}
						
						//The updated board is displayed after each shot
						game.displayedBoard();
						
						//If the user has made 19 successful shots they win
						if(game.getSuccessfulShots() == 19) break;

					}
					
					//Statistics are printed at the end of the game
					System.out.println("Congratulations, you won!");
					System.out.println("You made " + game.getShotsFired() + " shots, of which " + game.getSuccessfulShots() + " were successful.");
					
				}
					

				
			}
			//Choice 3 - Save game
			else if(usersInput.equals("3"))
			{
				//Method to save game is called
				game.saveGameToFile();
				System.out.println("Game saved!");
			}
			
			//Choice 0 - Exit game
			else if(usersInput.equals("0"))
			{
				System.out.println("Goodbye");
				
				//Game is closed
				System.exit(1);
			}
			
			//Choice 4 - Start a new game against responsive AI
			else if (usersInput.equals("4"))
			{
				//The board is cleared for a new game
				game.setEmptyValues();
				
				//AI randomly places the fleet
				game.placeAllComputerShips();
				
				//User places their ships
				game.placeAllUserShips();
				
				//Displays the board to the user
				game.displayedBoard();

				
				do
				{	
					
					//User is prompted to shoot
					openOptions = game.fireShot();
					
					//If user destroyed the entire enemy fleet the loop is broken since they won
					if(game.getSuccessfulShots()== 19) break;
					
					//If user chose to open options by entering -1,
					// additional options are opened
					if(openOptions == true)
					{
						openAdditionalOptions(menu);						
					}
								
					//Board is updated and player is shown the board with the shots they made
					System.out.println("Your shots:");
					game.displayedBoard();
					
					//AI has their turn and shoots
					game.AIFireShot();
					
					//If AI destroyed the entire fleet the loop is broken since it won
					if(game.getAISuccessfulShots() == 19) break;
					
					//User's board is updated and the player is shown where the AI made the shot
					System.out.println("AI shots:");
					game.displayUsersBoard();
					
					
					if(game.getSuccessfulShots() == 19 || game.getAISuccessfulShots() == 19) break; 
					
				} while (game.getAISuccessfulShots()<19 && game.getSuccessfulShots()<19); //Loops until one wins
				
				// Statistics are printed depending on the outcome of the game
				
				if (game.getSuccessfulShots() == 19) 
				{
					System.out.println("Congratulations, you won!");
					System.out.println("You made " + game.getShotsFired() + " shots, of which " + game.getSuccessfulShots() + " were successful.");
					System.out.println("AI made " + game.getAIShotsFired() + " shots, of which " + game.getAISuccessfulShots() + " were succesful.");
				}
				else if(game.getAISuccessfulShots() == 19)
				{
					System.out.println("You lost!");
					System.out.println("You made " + game.getShotsFired() + " shots, of which " + game.getSuccessfulShots() + " were successful.");
					System.out.println("AI made " + game.getAIShotsFired() + " shots, of which " + game.getAISuccessfulShots() + " were succesful.");
				}
				
			}
			else
			{
				//If the entered value is invalid, the user is prompted to re-enter
				System.out.println ("Invalid value. Please try again");
			}
			
			
		} while (!((usersInput.equals("0"))));
	}
	
	/**
	 * A method that opens additional opens available when the user is in-game.
	 * 
	 * 1. Save game - saves the current game progress into a text file;
	 * 2. Go back to menu - sends user back to main menu;
	 * 3. Exit game - completely exits the system;
	 * 4. Continue - continues the current game.
	 * 
	 * 
	 * @param menu - object of menu to call menu class methods
	 */
	public void openAdditionalOptions(Menu menu)
	{
		Scanner s = new Scanner(System.in);
		//Variable to hold user's input
		String input = null;


			do // Loops until user chooses to continue
			{
				//Options are displayed
				System.out.println("Please choose one of the options below:");
				System.out.println("1. Save game");
				System.out.println("2. Go back to menu");
				System.out.println("3. Exit game");
				System.out.println("0. Continue");
				
				//User input is read
				input = s.nextLine();
				
				//Choice 1 - Save game
				if (input.equals("1"))
				{
					//Calls method to save the game progress
					game.saveGameToFile();
					System.out.println("Game saved!");
				}
				//Choice 2 - Go back to menu
				else if (input.equals("2"))
				{
					//Calls main menu method to process user choices
					menu.processUserChoices(menu);
				}
				//Choice 3 - Exit game
				else if (input.equals("3"))
				{
					System.out.println("Goodbye");
					
					//Exits the system
					System.exit(1);
				}
				//Choice 4 - Continue
				else if (input.equals("0"))
				{
					//The user is returned to the game
					return;
				}
				else 
				{
					//In case of invalid input, user is prompted to re-enter
					System.out.println("Invalid input. Please try again");
				}
				
			} while (!(input.equals("0")));

			
			
	}
	
}
	

