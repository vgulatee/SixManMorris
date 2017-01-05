package Model;

import java.awt.Color;
// Helper Class that represents properties and features of each disk used in the game
public class Disks {
	
	private int x; //Represents x-coordinate of the location of the disk
	private int y; //Represents y-coordinate of the location of the disk
	private int defx; //Represents x-coordinate of the starting location of the disk
	private int defy; //Represents y-coordinate of the starting location of the disk
	private Color color; //Represents the color of the disk that represents which players set the disk belongs to 
	
	//Constructor 
	public Disks(int x1, int y1, Color col){
		//Method that takes the location coordinates in space and color of representation as parameters
		defx=x=x1;
		defy=y=y1;
		color=col;
	}
	
	//Accessors
	public int getX() {
		//Method returns the value of x-coordinate of the location of the disk object
		return x;
	}
	public int getY() {
		//Method returns the value of y-coordinate of the location of the disk object 
		return y;
	}
	public Color getColor(){
		//Method returns the color of the disk object 
		return color;
	}
	public int getdefX() {
		//Method returns the value of  x-coordinate of the default location of the disk object
		return defx;
	}
	public int getdefY() {
		//Method returns the value of y-coordinate of the default location of the disk object
		return defy;
	}
	
	//Mutators
	public void setCoord(int x1, int y1){
		//Method sets the coordinates of the disk with the values of x and y that are passed as parameters
		this.x=x1;
		this.y=y1; 
	}
	public void setDefault(){
		//Method sets the coordinates of the disk with the values of defx and defy .ie the default values
		this.x=defx;
		this.y=defy;
	}

	

	
}
