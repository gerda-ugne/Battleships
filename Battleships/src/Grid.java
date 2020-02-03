import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Class that holds information about the game grid.
 * 
 * Grid holds information about the user's ships.
 * Displayed grid holds information about the shots users makes on the enemy grid.
 * Enemy grid holds information about the AI placed ships and is not accessible by the user.
 * 
 * Data about fired shots and successful shots is recorded for both user and AI separately.
 * 
 * Grid class holds objects from Ship class:
 * each ship object holds information about a different ship type.
 * 
 * @author Gerda Ugne Pupelyte
 * @version 1.0
 */


public class Grid {
	
	//Grid that holds information about user's ships
	private String[][] grid;
	
	//Counters for fired shots
	private int shotsFired;
	private int AIShotsFired;
	
	//Counters for successful shots
	private int successfulShots;
	private int AISuccessfulShots;
	
	//Enemy grid to which enemy ship positions are saved
	private String[][] enemyGrid;
	
	//Displayed grid that shows user's shots
	private String[][] displayedGrid;
	
	//Size of the grids
	private final static int GRID_SIZE = 10;
	
	
	//Initiates an array to hold all types of ships
	Ship allShips[] = new Ship[4];
	
	//Initiates different types of ships
	Ship battleship = new Ship();
	Ship cruiser = new Ship();
	Ship destroyer = new Ship();
	Ship submarine = new Ship();
	
	
	/**
	 * Constructor of the grid class: sets empty values for grids and counters
	 */
	public Grid()
	{
		shotsFired = 0;
		AIShotsFired = 0;
		
		
		successfulShots = 0;
		AISuccessfulShots = 0;
		
		//All grids are the same size
		grid = new String[GRID_SIZE][GRID_SIZE];
		enemyGrid = new String[GRID_SIZE][GRID_SIZE];
		displayedGrid = new String [GRID_SIZE][GRID_SIZE];
		
		//Grids are set to be empty when created
		for(int i=0; i<grid.length;i++)
		{
			for(int j=0; j<grid[i].length;j++)
			{
				grid[i][j] = ".";
				enemyGrid[i][j] = ".";
				displayedGrid[i][j] = ".";
			}
		}
		
		

		// Sets values for all default ship types
		// First number - ship size, second number - ship quantity
		battleship.setShipValues(4,1);
		cruiser.setShipValues(3,2);
		destroyer.setShipValues(2,3);
		submarine.setShipValues(1, 3);
		
		//Puts all the ships in the ship array for easier access
		allShips[0] = battleship;
		allShips[1] = cruiser;
		allShips[2] = destroyer;
		allShips[3] = submarine;
		
	}
	
	

/**
 * A method that sets all values on the board to be empty and prepares it for a new game.
 * The default character for a blank field is ".".
 * Successful and fired shots are set to 0.
 * 
 */
public void setEmptyValues()
{	
	for(int i=0; i<grid.length;i++)
	{
		for(int j=0; j<grid[i].length;j++)
		{
			grid[i][j] = ".";
			enemyGrid[i][j] = ".";
			displayedGrid[i][j] = ".";
		}
	}
	
	shotsFired = 0;
	successfulShots = 0;
	
	AIShotsFired = 0;
	AISuccessfulShots = 0;
			
}


/**
 * A method that displays the user's board.
 * In order to improve readability, a line of letters is printed at the top of the board
 * and index numbers are printed vertically on the left side.
 */
public void displayUsersBoard()
{
	// Array for letters for user's guidance
	char[] letters = {'0','A','B','C','D','E','F','G','H','I','J'};
	
	//Number index to display as a vertical row next to the board
	int numberIndex = 1;
	
	//Letters are printed at the top of the board 
	for(int i=0; i<grid.length+1; i++)
	{
		if(i==0) System.out.print(letters[i] + "  ");
		else System.out.print(letters[i] + " ");
	}
	
	System.out.println();
	
	//Board contents are printed 
	for(int i=0; i<grid.length;i++)
	{
		//If number index is below 10, two spaces are left for better readability
		if(!(numberIndex == 10)) System.out.print(numberIndex + "  ");
		else System.out.print(numberIndex + " ");
		numberIndex++;
		
		for(int j=0; j<grid[i].length;j++)
		{
			System.out.print(grid[i][j] + " ");
		}
		
		System.out.println();
	}
	
	
}


/**
 * A method that displays the board of the AI, used for testing purposes.
 * In order to improve readability, a line of letters is printed at the top of the board
 * and index numbers are printed vertically on the left side.
 */

public void displayEnemyBoard()
{
	// Array for letters for user's guidance
	char[] letters = {'0','A','B','C','D','E','F','G','H','I','J'};
	
	//Number index to display as a vertical row next to the board
	int numberIndex = 1;
	
	//Letters are printed at the top of the board 
	for(int i=0; i<enemyGrid.length+1; i++)
	{
		if(i==0) System.out.print(letters[i] + "  ");
		else System.out.print(letters[i] + " ");
	}
	
	System.out.println();
	
	//Board contents are printed 
	for(int i=0; i<enemyGrid.length;i++)
	{
		//If number index is below 10, two spaces are left for better readability
		if(!(numberIndex == 10)) System.out.print(numberIndex + "  ");
		else System.out.print(numberIndex + " ");
		numberIndex++;
		
		for(int j=0; j<enemyGrid[i].length;j++)
		{
			System.out.print(enemyGrid[i][j] + " ");
		}
		
		System.out.println();
	}
	
}

/**
 * Method that allows user to fire a shot.
 * 
 * User is prompted to choose a field to fire at.
 * If user enters -1, they are returned to the main menu to access options.
 * 
 * If a ship is hit, the board is marked with #;
 * If the shot is missed, the board is marked with o.
 * 
 * Successful and total shots are counted.
 * 
 * @return true or false whether the option menu has to be opened
 */
public boolean fireShot()
{

	Scanner s = new Scanner(System.in);
	
	//Variable to hold input of the user
	String userInput = null;
	
	//Array to hold split user input
	String coordinates[];
	
	//Coordinates
	int x = 0, y = 0;
	
	//Boolean to retry input collection in case user enters invalid input
	boolean retryInputCollection = true;
	
	//Boolean to collect data whether the option menu has to be opened
	boolean openOptions = false;
	
	
	while (retryInputCollection == true)
	{
		try
		{
			
			System.out.println("Please choose a field to shoot (eg. 1 A)");
			System.out.println("If you wish to access options, type -1.");
			//User input is read
			userInput = s.nextLine();
			
			//If user wishes to access options, they are returned to the menu
			if(userInput.equals("-1")) {
				openOptions = true;
				return openOptions;
			}
			
			//The input is split into two separate variables
			coordinates = userInput.split(" ");
			
			// String x is converted into integer x
			// -1 operation is needed to match the displayed board since the array index starts at 0 and not at 1
			x = Integer.parseInt(coordinates[0]) - 1;
			
			//If the coordinate is out of board boundaries, an exception is thrown
			if (x>9 || x<0) throw new InputMismatchException();
			
			
			
			
			char letterY;		
			//String y is converted into char Y
			letterY = coordinates[1].charAt(0);
			
			//Length of y input is determined in case user entered more than one letter
			int inputLength = coordinates[1].length();
			
			//If user has entered more than one letter, exception is thrown to recollect the input
			if(inputLength > 1) throw new InputMismatchException();
			
			//Char Y is converted into a corresponding number
			y = convertLetterIntoNumber(letterY);
			
			//If the coordinate is out of board boundaries, an exception is thrown
			if(y>9 || y<0) throw new InputMismatchException();
			
			
			//If user already chose the field before, they are prompted to choose another one
			if(!(displayedGrid[x][y].equals(".")))
			{
				System.out.println("You already chose this field before. Please make a new choice");
				continue;
			}
			retryInputCollection = false;
			
		}
		catch(NumberFormatException e) //If the format is incorrect, an exception is thrown
		{
			System.out.println("Please make sure your input meets the format (eg. 1 A)");
		}
		catch(InputMismatchException e) //If the coordinate is out of boundaries, an exception is thrown
		{
			System.out.println("Please make sure provided number is between 1 and 10 and the letter is from A to J");
		}
		catch(ArrayIndexOutOfBoundsException e) //If the input is out of boundaries, an exception is thrown
		{
			System.out.println("Please check your input");
		}
		
	}
	// If the user misses, the grid is updated with o symbol
	if(enemyGrid[x][y].equals("."))
	{
		System.out.println("Shot missed!");
		displayedGrid[x][y] = "o";
		
		
		shotsFired++;
	}
	
	//If the user successfully hits a ship, the grid is updated with # symbol
	else if (enemyGrid[x][y].equals("X"))
	{
		System.out.println("Ship hit!");
		displayedGrid[x][y] = "#";
		
		shotsFired++;
		//Successful shots are counted
		successfulShots++;
	}
	

	
	return openOptions;
	
}


	
/**
 * Method that places all computer ships down on the enemy grid.
 * placeAComputerShip method is called for every different type of ship.
 * 
 */
public void placeAllComputerShips()
{
	
	for(int i=0; i < allShips.length; i++)
	{
		//Ship fields are accessed with accessor methods and sent to another method to place a single type of ships
		placeAComputerShip(allShips[i].getShipSize(),allShips[i].getShipQuantity());
	}

}


/**
 * Method that places all one type of AI ships randomly on the enemy grid.
 * @param shipSize - the amount of fields the ship will take up
 * @param shipQuantity - the amount of ships to be placed of the provided ship size
 */
public void placeAComputerShip(int shipSize, int shipQuantity)
{
	
	// Randomly generated coordinates where the ship will be attempted to place
	Random rand = new Random();
	
	int rngX = rand.nextInt(10);
	int rngY = rand.nextInt(10);
	
	// Randomly generates position (vertical or horizontal)
	List<String> basePosition = new ArrayList<String>();
	basePosition.add("Horizontal");
	basePosition.add("Vertical");
	
	
	Random randomizer = new Random();
	String  position = basePosition.get(randomizer.nextInt(basePosition.size()));

	//Variable to hold information about occupied squares
	int occupiedSquares = 0;
	
	//Variable to determine whether ship is being placed out of boundaries of the grid
	boolean outOfBoundaries = false;
	
	//Variable to retry if there is an error
	boolean retry = true;
	
	
	//For loop for every ship of the given size
	for(int i=0; i < shipQuantity; i++)
	{	
		//Values are reset for each new ship
		occupiedSquares = 0;
		outOfBoundaries = false;
		retry = true;
		
		while(retry==true)
		{
			try
			{
				do
				{
					occupiedSquares = 0;
					outOfBoundaries = false;
					
					//If randomly generated position is horizontal
					if(position == "Horizontal")
					{
						//Loop checks if the ship can be placed
						for(int j=0; j<shipSize; j++)
						{
							//If there are any occupied squares, the loop is stopped
							if(!(enemyGrid[rngX][rngY+j] == ".")) 
								{
									occupiedSquares++;
									break;
								}
							
							//If the coordinate steps out of boundaries, the loop is stopped
							if((rngY+j) > 10) 
								{	
									outOfBoundaries = true;
									break;
								}
						}
						
						//if any squares are occupied or the coordinate went out of boundaries, coordinates are regenerated;
						if(occupiedSquares > 0 || outOfBoundaries == true) 
						{
							rngX = rand.nextInt(10);
							rngY = rand.nextInt(10);
							retry = true;
						
						}
						//Otherwise if no conflicts found, the ship is placed down on the enemy grid
						else
						{
							for(int j=0; j<shipSize; j++)
							{
								enemyGrid[rngX][rngY+j] = "X";
								
								
							}
							
							//Coordinates are regenerated for the next ship placement
							rngX = rand.nextInt(10);
							rngY = rand.nextInt(10);
							
							retry = false;
						}
						
					}
					
					
					//If randomly generated position is vertical
					else if(position == "Vertical")
					{
						//Loop checks if the ship can be placed
						for(int j=0; j<shipSize; j++)
						{
							//If there are any occupied squares, the loop is stopped
							if(!(enemyGrid[rngX+j][rngY] == ".")) 
							{
								occupiedSquares++;
								break;
							}
						
							//If the coordinate steps out of boundaries, the loop is stopped
							if((rngY+j) > 10) 
							{	
								outOfBoundaries = true;
								break;
							}
					}
					
					//if any squares are occupied or the coordinate went out of boundaries, coordinates are regenerated;
					if(occupiedSquares > 0 || outOfBoundaries == true) 
						{
							rngX = rand.nextInt(10);
							rngY = rand.nextInt(10);
							retry = true;
						
						}
					//Otherwise if no conflicts found, the ship is placed down on the enemy grid
					else
					{
						for(int j=0; j<shipSize; j++)
						{
							enemyGrid[rngX+j][rngY] = "X";
						}
						
						
						//Coordinates are regenerated for the next ship placement
						rngX = rand.nextInt(10);
						rngY = rand.nextInt(10);
						
						retry = false;
					}
					
					}
					
					
				} while (occupiedSquares > 0 || outOfBoundaries == true);
				
			}
			catch (ArrayIndexOutOfBoundsException e) //If randomly generated coordinates are out of boundaries, they are regenerated
			{
				outOfBoundaries = true;
				retry = true;
				
				rngX = rand.nextInt(10);
				rngY = rand.nextInt(10);
			}
			
		}

		// Position is regenerated for the next ship
		position = basePosition.get(randomizer.nextInt(basePosition.size()));
		
	};


}

/**
 * Method that places all user's ships down on the grid.
 * placeAShipByUser method is called for every different type of ship.
 *  
 */
public void placeAllUserShips()
{
	for(int i=0; i < allShips.length; i++)
	{
		//Ship fields are accessed with accessor methods and sent to another method to place a single type of ships
		if(i==0) System.out.println("Place your battleship");
		else if(i==1) System.out.println("Place your cruisers");
		else if(i==2) System.out.println("Place your destroyers");
		else if(i==3) System.out.println("Place your submarines");
		
		placeAShipByUser(allShips[i].getShipSize(),allShips[i].getShipQuantity());
	}

}

/**
 * Method that places all one type of user ships on the user's grid.
 * The position and place of the ship is chosen by the user.
 * 
 * 
 * @param shipSize - the amount of fields the ship will take up
 * @param shipQuantity - the amount of ships to be placed of the provided ship size
 */
public void placeAShipByUser(int shipSize, int shipQuantity)
{
	Scanner s = new Scanner(System.in);
	Scanner s2 = new Scanner (System.in);
	
	//Variable to hold declared ship position
	String position = "Horizontal";
		
	//Variable to hold information about occupied squares
	int occupiedSquares = 0;
	
	//Variable to determine whether ship is being placed out of boundaries of the grid
	boolean outOfBoundaries = false;
	
	//Values to retry if any errors occur
	boolean retry = true;
	boolean retryInputCollection = true;
	
	//Array to hold split user's input
	String coordinates[] = new String [2];
	
	//Variable to hold user's input
	String input;
	
	int x = 0, y = 0;
	
	//For loop for every ship of the given size
	for(int i=0; i < shipQuantity; i++)
	{	
		occupiedSquares = 0;
		outOfBoundaries = false;
		retry = true;
		retryInputCollection = true;
		
		//User is asked for input for starting x and y coordinates
		while (retryInputCollection == true)
		{
			try
			{
				
				
				System.out.println("Enter the starting coordinate for ship placement (eg. 1 A)");
				input = s.nextLine();
				
				//User input is split
				coordinates = input.split(" ");
				
				//Since x is displayed as an integer on board, it's converted into a real integer from string
				//-1 operation is needed since the index starts at 0 whereas the numbering on the displayed board starts with 1
				x = Integer.parseInt(coordinates[0]) - 1;
				
				//If x is out of boundaries, an exception is thrown
				if (x>9 || x<0) throw new InputMismatchException();
				
				char letterY;		
				//String y is converted into char Y
				letterY = coordinates[1].charAt(0);
				
				//Length of y input is determined in case user entered more than one letter
				int inputLength = coordinates[1].length();
				
				//If user has entered more than one letter, exception is thrown to recollect the input
				if(inputLength > 1) throw new InputMismatchException();
				
				//Char Y is converted into a corresponding number
				y = convertLetterIntoNumber(letterY);
				
				//If the coordinate is out of board boundaries, an exception is thrown
				if(y>9 || y<0) throw new InputMismatchException();
				
				retryInputCollection = false;
				
			}
			catch(InputMismatchException e) //If the input does not match
			{
				System.out.println("Please make sure the coordinate is an integer between 1 and 10");
			}
			catch(NumberFormatException e) //If the format entered is wrong
			{
				System.out.println("Please make sure your input meets the format (eg. 1 A)");
			}
			catch(ArrayIndexOutOfBoundsException e) //If the input is out of boundaries
			{
				System.out.println("Please check your input");
			}
			
		}
			
		
		retryInputCollection = true;
		
		// User is asked to provide the position of the ship
		while (retryInputCollection == true)
		{
			try
			{
				//If the ship is a size of one square, no position needs to be determined
				if(shipSize == 1) {retryInputCollection = false; continue;};
				System.out.println("Enter the ship position (h for horizontal or v for vertical):");

				position = s2.nextLine();
				
				//If the position does not equal horizontal or vertical, an exception is thrown
				if((!(position.equals("h")))&&(!(position.equals("v")))) throw new InputMismatchException();
				
				retryInputCollection = false;
				
			}
			catch(InputMismatchException e)
			{
				System.out.println("Please make sure the position is either Horizontal or Vertical");
			}
			
			
		}
		

		while(retry==true)
		{
			try
			{
				do
				{
					occupiedSquares = 0;
					outOfBoundaries = false;
					
					//For ships of size 1
					if(shipSize == 1)
					{
						//Position is irrelevant due to size
						position = "none";
						
						//If the square is occupied, it's counted
						if(!(grid[x][y] == "."))
						{
							occupiedSquares++;
		
						}
						
				
						if(occupiedSquares > 0)
						{
							//If squares are occupied user is prompted to re-enter
							System.out.println("Please re-enter the coordinates as the ship cannot be placed in the location you provided:");
							
							retryInputCollection = true;
							
							while(retryInputCollection == true)
							{
								try
								{
									
									
									System.out.println("Enter another coordinates for ship placement (eg. 1 A)");
									input = s.nextLine();
									
									//Input is split and converted into integers
									coordinates = input.split(" ");
									
									
									x = Integer.parseInt(coordinates[0]) - 1;
									if (x>9 || x<0) throw new InputMismatchException();
									
									char letterY;
									letterY = coordinates[1].charAt(0);
									y = convertLetterIntoNumber(letterY);
									if(y>9 || y<0) throw new InputMismatchException();
									
									retryInputCollection = false;
									
								}
								catch(InputMismatchException e) 
								{
									System.out.println("Please make sure the coordinate is an integer between 1 and 10");
								}
								catch(NumberFormatException e)
								{
									System.out.println("Please make sure your input meets the format (eg. 1 A)");
								}
								catch(ArrayIndexOutOfBoundsException e)
								{
									System.out.println("Please check your input");
								}
								
							}

							
						}
						
						else
						{
							//If input is correct, the ship is placed
							System.out.println("Placed");
							grid[x][y] = "X";
							}

							retry = false;
							displayUsersBoard();
						}
					
					
					
					
					//If position is horizontal
					if(position.equals("h"))
					{
						//Loop checks if the ship can be placed
						for(int j=0; j<shipSize; j++)
						{
							//If there are any occupied squares, the loop is stopped
							if(!(grid[x][y+j] == ".")) 
								{
									occupiedSquares++;
									break;
								}
							
							//If the coordinate steps out of boundaries, the loop is stopped
							if((y+j) > 10) 
								{	
									outOfBoundaries = true;
									break;
								}
						}
						
						//if any squares are occupied or the coordinate went out of boundaries, user is prompted to enter other coordinates
						if(occupiedSquares > 0 || outOfBoundaries == true) {
							
							System.out.println("Please re-enter the coordinates as the ship cannot be placed in the location you provided:");
							
							retryInputCollection = true;
							
							while (retryInputCollection == true)
							{
								try
								{
									
									
									System.out.println("Enter another coordinates for ship placement (eg. 1 A");
									input = s.nextLine();
									
									//Input is split
									coordinates = input.split(" ");
									
									//Input is converted to integers
									x = Integer.parseInt(coordinates[0]) - 1;
									if (x>9 || x<0) throw new InputMismatchException();
									
									char letterY;
									letterY = coordinates[1].charAt(0);
									y = convertLetterIntoNumber(letterY);
									if(y>9 || y<0) throw new InputMismatchException();
									
									retryInputCollection = false;
									
								}
								catch(InputMismatchException e) 
								{
									System.out.println("Please make sure the coordinate is an integer between 1 and 10");
								}
								catch(NumberFormatException e)
								{
									System.out.println("Please make sure your input meets the format (eg. 1 A");
								}
								catch(ArrayIndexOutOfBoundsException e)
								{
									System.out.println("Please check your input");
								}
						}
						}
							
							
						//Otherwise if no conflicts found, the ship is placed down on the enemy grid
						else
						{
							for(int j=0; j<shipSize; j++)
							{
								grid[x][y+j] = "X";
								
								
							}

							retry = false;
							displayUsersBoard();
						}
						
					}
						
					
					
					//If randomly generated position is vertical
					else if(position.equals("v"))
					{
						//Loop checks if the ship can be placed
						for(int j=0; j<shipSize; j++)
						{
							//If there are any occupied squares, the loop is stopped
							if(!(grid[x+j][y] == ".")) 
							{
								occupiedSquares++;
								break;
							}
						
							//If the coordinate steps out of boundaries, the loop is stopped
							if((y+j) > 10) 
							{	
								outOfBoundaries = true;
								break;
							}
						}
					
					//if any squares are occupied or the coordinate went out of boundaries, user is prompted to re-enter coordinates
						if(occupiedSquares > 0 || outOfBoundaries == true) {
							
							System.out.println("Please re-enter coordinates as the ship cannot be placed in the location you provided:");
							
							retryInputCollection = true;
							while (retryInputCollection == true)
							{
								try
								{
									
									
									System.out.println("Enter another coordinates for ship placement (eg. 1 A)");
									input = s.nextLine();
										
									//Input is split
									coordinates = input.split(" ");
									
									
									//Input is converted into integers
									x = Integer.parseInt(coordinates[0]) - 1;
									if (x>9 || x<0) throw new InputMismatchException();
									
									char letterY;
									letterY = coordinates[1].charAt(0);
									y = convertLetterIntoNumber(letterY);
									if(y>9 || y<0) throw new InputMismatchException();
									
									retryInputCollection = false;
									
								}
								catch(InputMismatchException e) 
								{
									System.out.println("Please make sure the coordinate is an integer between 1 and 10");
								}
								catch(NumberFormatException e)
								{
									System.out.println("Please make sure your input meets the format (eg. 1 A)");
								}
								catch(ArrayIndexOutOfBoundsException e)
								{
									System.out.println("Please check your input");
								}
							}
							
						}
					//Otherwise if no conflicts found, the ship is placed down on the enemy grid
					else
					{
						for(int j=0; j<shipSize; j++)
						{
							grid[x+j][y] = "X";
						}
						
						
						retry = false;
						displayUsersBoard();
					}
					
					}
					
					
				} while (occupiedSquares > 0 || outOfBoundaries == true);
				
			}
			catch (ArrayIndexOutOfBoundsException e)
			{

				outOfBoundaries = true;
				retry = true;
				
				System.out.println("The ship cannot be placed within the provided coordinates due to its size.");
				System.out.println("Please re-enter the values");
				
				retryInputCollection = true;
				while (retryInputCollection == true)
				{
					try
					{
						
						
						System.out.println("Enter another coordinates for ship placement (eg. 1 A)");
						input = s.nextLine();
							
						coordinates = input.split(" ");
						
						x = Integer.parseInt(coordinates[0]) - 1;
						if (x>9 || x<0) throw new InputMismatchException();
						
						char letterY;
						letterY = coordinates[1].charAt(0);
						y = convertLetterIntoNumber(letterY);
						if(y>9 || y<0) throw new InputMismatchException();
						
						retryInputCollection = false;
						
					}
					catch(InputMismatchException er) 
					{
						System.out.println("Please make sure the coordinate is an integer between 1 and 10");
					}
					catch(NumberFormatException er)
					{
						System.out.println("Please make sure your input meets the format (eg. 1 A)");
					}
					catch(ArrayIndexOutOfBoundsException er)
					{
						System.out.println("Please check your input");
					}
				}
				
			}
			
		}
		
		
	};


}

/**
 * Method that converts part of the user's input into a coordinate.
 * The method is needed to improve the workflow with the grid array
 * since user sees the horizontal top row as letters. 
 * 
 * @param letter - received letter from the user 
 * @return - converted letter is returned as a y coordinate
 */
public int convertLetterIntoNumber (char letter)
{
	int result = 0;
	
	if(letter == 'A' || letter == 'a') result = 0;
	else if(letter == 'B' || letter == 'b') result = 1;
	else if(letter == 'C' || letter == 'c') result = 2;
	else if(letter == 'D' || letter == 'd') result = 3;
	else if(letter == 'E' || letter == 'e') result = 4;
	else if(letter == 'F' || letter == 'f') result = 5;
	else if(letter == 'G' || letter == 'g') result = 6;
	else if(letter == 'H' || letter == 'h') result = 7;
	else if(letter == 'I' || letter == 'i') result = 8;
	else if(letter == 'J' || letter == 'j') result = 9;
	else result = -1;

	return result;

}

/**
 * Method that saves current game progress into a file.
 * 
 * Saved game data is stored in savedGame.txt file.
 * 
 * Current fired and successful shots are saved;
 * Information of all grids is saved.
 * 
 */
public void saveGameToFile()
{
	//FileOutputStream and PrintWriter for writing to file
	FileOutputStream outputStream = null;
	PrintWriter printWriter = null;
	
	try
	{	
		//Saved game data is stored in savedGame.txt
		outputStream = new FileOutputStream("savedGame.txt");
		printWriter = new PrintWriter(outputStream);
		
		
		//Current contents of grids are saved into the text file
		for(int i=0; i<grid.length;i++)
		{
			for (int j=0; j < grid[i].length; j++)
			{
				printWriter.print(grid[i][j]);
			}
			
			printWriter.println();
		}
		
		for(int i=0; i<enemyGrid.length;i++)
		{
			for (int j=0; j < enemyGrid[i].length; j++)
			{
				printWriter.print(enemyGrid[i][j]);
			}
			
			printWriter.println();
		}
		
		for(int i=0; i<displayedGrid.length;i++)
		{
			for (int j=0; j < displayedGrid[i].length; j++)
			{
				printWriter.print(displayedGrid[i][j]);
			}
			
			printWriter.println();
		}
		
		
		//Record the number of shots fired
		printWriter.println(shotsFired);
		printWriter.println(successfulShots);
		
		printWriter.println(AIShotsFired);
		printWriter.println(AISuccessfulShots);
		
		
	}
	
	
	catch (IOException e) // In case of error, an error message is displayed
	{
		System.out.println("Error:" + e);
	}
	finally // PrintWriter is closed
	{
		if(printWriter != null) printWriter.close();
	}
}



/**
 * Method that loads a previously saved game information into class fields.
 * 
 * Information from savedGame.txt file is read.
 * All grids are updated with the information from the file.
 * Successful and total shots are updated as well.
 * 
 */

public void loadGameFromFile()
{
	FileReader fileReader = null;
	BufferedReader bufferedReader = null;
	
	
	try
	{
		//Contents from last saved game will be loaded
		fileReader = new FileReader("savedGame.txt");
		bufferedReader = new BufferedReader(fileReader);
		
		//Variable to hold information about one read line
		String line;
		
		//Variable to convert the line into an array of chars
		char[] charArray;

		//Variable to re-convert an element from char array into a string
		String field;
		
		
		for(int i=0; i<grid.length;i++)
		{
			//A single line is read
			line = bufferedReader.readLine();
			//Line is transfered into a char array
			charArray = line.toCharArray();
			
			
			for(int j=0; j<grid[i].length; j++)
			{
				//Char at place of j is converted into string
				field = String.valueOf(charArray[j]);
				
				//Value is transfered onto the board
				grid[i][j]  = field;
				
			}
		}
		
		for(int i=0; i<enemyGrid.length;i++)
		{
			//A single line is read
			line = bufferedReader.readLine();
			//Line is transfered into a char array
			charArray = line.toCharArray();
			
			for(int j=0; j<enemyGrid[i].length; j++)
			{
				//Char at place of j is converted into string
				field = String.valueOf(charArray[j]);
				
				//Value is transfered onto the enemy board
				enemyGrid[i][j]  = field;
				
			}
		}
		
		for(int i=0; i<displayedGrid.length;i++)
		{
			//A single line is read
			line = bufferedReader.readLine();
			//Line is transfered into a char array
			charArray = line.toCharArray();
			
			for(int j=0; j<displayedGrid[i].length; j++)
			{
				//Char at place of j is converted into string
				field = String.valueOf(charArray[j]);
				
				//Value is transfered onto the enemy board
				displayedGrid[i][j]  = field;
				
			}
		}
		
		// Variables to hold information about shots
		String shots;
		String ssShots;
		
		shots = bufferedReader.readLine();
		
		//String is converted into integer
		shotsFired =  Integer.parseInt(shots);
		
		ssShots = bufferedReader.readLine();
		//String is converted into integer
		successfulShots =  Integer.parseInt(ssShots);
		
		
		
		
		// Variables to hold information about shots
		String AIShots;
		String AIssShots;
			
		
		AIShots = bufferedReader.readLine();
		//String is converted into integer
		AIShotsFired =  Integer.parseInt(AIShots);
		
		AIssShots = bufferedReader.readLine();
		//String is converted into integer
		AISuccessfulShots =  Integer.parseInt(AIssShots);
	
			
	}
	catch (IOException e) //In case of error, an error message is displayed
	{
		System.out.println("Error: " + e);
	}
	finally // Buffered reader is closed
	{
		try
		{
			if(bufferedReader != null ) bufferedReader.close();
		}
		catch (IOException e)
		{
			System.out.println("Error: " + e);
		}
}
}

	/**
	 * Accessor method for shotsFired field
	 * @return shotsFired
	 */
	public int getShotsFired()
	{
		return shotsFired;
	}
	
	/**
	 * Accessor method for successfulShots field
	 * @return successfulShots
	 */
	public int getSuccessfulShots()
	{
		return successfulShots;
	}
	
	/**
	 * Accessor method for AIShotsFired field
	 * @return AIShotsFired
	 */
	public int getAIShotsFired()
	{
		return AIShotsFired;
	}
	
	/**
	 * Accessor method for AISuccesfulShots field
	 * @return AISuccessfulShots
	 */
	public int getAISuccessfulShots()
	{
		return AISuccessfulShots;
	}
	
	/**
	 * Method that allows AI to fire.
	 * AI randomly generates coordinates and shoots.
	 * 
	 * If a ship was hit, it's marked with *;
	 * if it's a miss, it's marked with o.
	 */
	public void AIFireShot()
	{
		Random rand = new Random();
		
		boolean retry = true;
		while (retry == true)
		{
			// Randomly generated coordinates
			int x = rand.nextInt(10);
			int y = rand.nextInt(10);
			
			//If AI already made the shot before, coordinates are regenerated
			if(grid[x][y].equals("-") || grid[x][y].equals("*"))
			{
				continue;
			}
			else if(grid[x][y].equals("X"))
			{
				//If a ship was hit, it's marked as * on board.
				grid[x][y] = "*";
				AISuccessfulShots++;
				AIShotsFired++;
				
				retry = false;
				
			}
			else
			{
				//If ship was not hit, board is marked as "o".
				grid[x][y] = "o";
				AIShotsFired++;
				
				retry = false;
			}
			
		}
		

		
	}
	
	/**
	 * Method that displays the board to the player.
	 * In order to improve readability, a line of letters is printed at the top of the board
	 * and index numbers are printed vertically on the left side.
	 */
	public void displayedBoard()
	{

		// Array for letters for user guidance
		char[] letters = {'0','A','B','C','D','E','F','G','H','I','J'};
		
		//Number index to display as a vertical row next to the board
		int numberIndex = 1;
		
		//Letters are printed at the top of the board 
		for(int i=0; i<displayedGrid.length+1; i++)
		{
			if(i==0) System.out.print(letters[i] + "  ");
			else System.out.print(letters[i] + " ");
		}
		
		System.out.println();
		
		//Board contents are printed 
		for(int i=0; i<displayedGrid.length;i++)
		{
			//If number index is below 10, two spaces are left for better readability
			if(!(numberIndex == 10)) System.out.print(numberIndex + "  ");
			else System.out.print(numberIndex + " ");
			numberIndex++;
			
			for(int j=0; j<displayedGrid[i].length;j++)
			{
				System.out.print(displayedGrid[i][j] + " ");
			}
			
			System.out.println();
		}
		
		//Amount of fired shots is printed
		System.out.println("Fired shots: " + shotsFired);
		System.out.println("Total hits: " + successfulShots);
	}
	

}
	