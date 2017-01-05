package Model;

import java.awt.Color;
//Helper class that represents properties and features of all the 16 nodes present on the board
public class Node {
	
	private int x; //Represents x-coordinate of the location of the Node object
	private int y; //Represents y-coordinate of the location of the Node object
	private Color State; //Represents the state of the Node object by assigning a color (Blue and Red depending on the color of the disk occupying the node and white if the node isn't occupied) 
	
	//Constructor
	public Node(int x2, int y2, Color color) {
		//Method constructs a Node object and assigns the value of the parameters to the values of the private variable of the Node object 
		x=x2;
		y=y2;
		State=color;
	}
	
	//Accessors
	public int getX() {
		//Method returns the value of x-coordinate of the location of the Node object
		return x;
	}
	public int getY() {
		//Method returns the value of y-coordinate of the location of the Node object
		return y;
	}
	public Color getState() {
		//Method returns the value of the State (as a color object) that represents whether or not a node space is occupied by a disk 
		return State;
	}
	
	//Mutator
	public void ChangeState(Color c){
		//Method that assigns the color of the disk (or not) to State that is passed as a parameter of the method
		State=c;
	}

}
