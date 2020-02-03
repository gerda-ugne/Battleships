/**
 * Class that holds ship objects.
 * 
 * Every object determines a ship type.
 * 
 * Ship size determines the amount of fields the ship takes up on the board;
 * Ship quantity determines the amount of ships with the same ship size property.
 * 
 * @author Gerda Ugne Pupelyte
 * @version  1.0
 *
 */
public class Ship {
	
	private int shipSize;
	private int shipQuantity;
	
	public Ship() {
		// TODO Auto-generated constructor stub
		shipSize = 0;
		shipQuantity = 0;
		
	}
	
	/**
	 * Sets the size of the ship
	 * @param size - the amount of fields ship takes up on the board
	 */
	public void setShipSize(int size)
	{
		shipSize = size;
	}
	
	/**
	 * Sets the amount of ships with the same ship size
	 * @param quantity - number of ships
	 */
	public void setShipQuantity (int quantity)
	{
		shipQuantity = quantity;
	}
	
	/**
	 * Sets both ship size and quantity
	 * @param size - the amount of fields ship takes up on the board
	 * @param quantity - number of ships
	 */
	public void setShipValues(int size, int quantity)
	{
		setShipSize(size);
		setShipQuantity(quantity);
	}
	
	/**
	 * Method to get the ship size
	 * @return ship size
	 */
	public int getShipSize()
	{
		return shipSize;
	}
	
	/**
	 * Method to get the ship quantity
	 * @return ship quantity of same properties
	 */
	public int getShipQuantity()
	{
		return shipQuantity;
	}

}
