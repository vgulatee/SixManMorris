package Model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Controller.GameManager;

//Model Class that represents the various stages of game play and AI for   
public class GamePlay {
	private static Node[] N1 = new Node[8]; //Array of type Node, represents the node spaces on the board that belong to the larger board
	private static Node[] N2 = new Node[8]; //Array of type Node, represents the node spaces on the board that belong to the smaller board 
	private static Disks[] counters = new Disks[12]; //Array of type Disks, represents the 12 colored disks used by two players. 
	private static Mills[] Line = new Mills[8]; //Array 
	private static int BoardPlay; //Represents the number of turns played on the board  
	private static Color Turn; //Represents which players turn it is to move (red or blue)
	private static int redP; //Represents the number of red pieces 
	private static int blueP; //Represents the number of blue pieces
	private static int boardP; //Represents the total number of pieces on the board
	private static int millFound; //Represents which mill is found (0-7) or not (-1)
	public static int selected = -1; //Represents which disk is selected (0-11) by user. Assigns -1 if a disk is not selected
	private static int gameMode=-1;
	//Enumeration of type State 
	public static enum State {
		//Describes the various states of a on-going  game
		INPROGRESS, INVALID, MILLFORMED, BLUEWIN, REDWIN, DISKDESELCTED, DISKSELECTED, PIECEREMOVED, SAVEGAME, LOADGAME
	};

	private static State gameState; //Represents the current state of the game

	//Constructor 
	public GamePlay() {
		//Initializes the characteristics of GamePlay object created
		redP = 0;
		blueP = 0;
		BoardPlay = 0;
		millFound = -1;
		selected = -1;
		CreateNodes(); //Method call to create nodes on the board
		CreateDisks(); //Method call to create disks on the board
		CreateMills(); //Method call to create unformed mills on the board
		gameState = State.INPROGRESS; //Default in progress state of the game
		setTurn(); //Method call to randomly set the player turn loop
		
	}
	public GamePlay(int n){
		gameMode=n;
		redP = 0;
		blueP = 0;
		BoardPlay = 0;
		millFound = -1;
		selected = -1;
		CreateNodes(); 
		CreateDisks(); 
		CreateMills(); 
		gameState = State.INPROGRESS;
		System.out.println("GAME MODE:"+ gameMode );
		setTurn();
		
		
	}
	
	private void CreateMills() {
		//Method that creates objects belonging to array of type mills and define the objects default characteristics 
		//Default Characteristics of Mills: formed=false and mode=0  
		for (int i = 0; i < 8; i++) {
			Line[i] = new Mills();
		}

	}


	private static void CreateNodes() {
		//Method that creates objects belonging to array of type Node and defines the objects default characteristics
		//Default Characteristics of Nodes:  X and Y set according to board specifications and Color=White 
		int i = 1;
		int Y, y;
		int X = Y = 80;
		int x = y = 205;
		N1[0] = new Node(X, Y, Color.WHITE);
		N2[0] = new Node(x, y, Color.WHITE);
		while (i < 8) {  //While loop iterates through all nodes to specify node characteristics. Iterates in the clockwise direction from the boards perspective, starting from the upper left corner
			if (i < 3) { //Line/Mill 0 and 4
				X += 250;
				x += 125;
				N1[i] = new Node(X, Y, Color.WHITE);
				N2[i] = new Node(x, y, Color.WHITE);
			} else if (i >= 3 && i < 5) { //Line/Mill 1 and 5
				Y += 250;
				y += 125;
				N1[i] = new Node(X, Y, Color.WHITE);
				N2[i] = new Node(x, y, Color.WHITE);
			} else if (i >= 5 && i < 7) { //Line/Mill 2 and 6
				X -= 250;
				x -= 125;
				N1[i] = new Node(X, Y, Color.WHITE);
				N2[i] = new Node(x, y, Color.WHITE);
			} else { //Line/Mill 3 and 7
				Y -= 250;
				y -= 125;
				N1[i] = new Node(X, Y, Color.WHITE);
				N2[i] = new Node(x, y, Color.WHITE);
			}
			i++; //Incrementing factor
		}

	}

	private static void CreateDisks() {
		//Method that creates objects belonging to array of type Disk and defines the objects default characteristics
		//Default Characteristics of Disk Objects: x, y, defx and defy set according to board specifications (iterating using a loop) and color= Blue(0-5) or Red(6-11) 
		int X = 80;
		for (int i = 0; i < 6; i++) { //Loop to iterate through all the disk objects
			//Disks are set in order from left to right 
			counters[i] = new Disks(X, 25, Color.BLUE); //Blue disk objects start from 0 to 5
			counters[i + 6] = new Disks(X, 599, Color.RED); //Red disk objects start from 6 to 11
			X += 94;
		}

	}
	
	private void setTurn() {
		//Method is a random number generator that generates either 1 or 2
		//1 represents the color blue and 2 represents the color red
		int x = (int) ((Math.random()) * 2);
		if (x == 1) {
			Turn = Color.BLUE;
			;
		} else {
			Turn = Color.RED;
			
		}
	}

	public static void swap(int mouseFlag, int i, int j) {
		/*Method swap takes integers representing the disk, node space and node type as parameters and 
		provides the function of moving the position of the disk, such that it occupies the node space*/
		Color state = counters[mouseFlag].getColor(); 
		if (j == 1 && N1[i].getState() == Color.WHITE) { //For node type 1, changes the position of disk to occupy node space 'i' of N1
			counters[mouseFlag].setCoord(N1[i].getX() - 17, N1[i].getY() - 17);
			N1[i].ChangeState(state); //node space is given the color of the disk, so as to indicate its occupation by that colored disk 
			if (BoardPlay < 12) {
				addDisk(state); //If the disk is being placed on the board for the first time, it increments the count of the disk of that color by 1 
			}
			selected = -1; //Deselects selected disk
			gameState = State.INPROGRESS; 
			updateMill(); //Method call checks to see if any mills have been formed and updates mill properties
			switchTurn(); //Method call alternates the color object Turn to indicate a change in player turn 
			BoardPlay++; //Turn count is incremented by one signaling end of turn 
			
		} else if (j == 2 && N2[i].getState() == Color.WHITE) {//For node type 2, changes the position of disk to occupy node space 'i' of N2
			counters[mouseFlag].setCoord(N2[i].getX() - 17, N2[i].getY() - 17);
			N2[i].ChangeState(state); //node space is given the color of the disk, so as to indicate its occupation by that colored disk 
			if (BoardPlay < 12) {
				addDisk(state);
			}
			selected = -1;                                 /* Same as above if block */
			gameState = State.INPROGRESS;                           //*
			updateMill();											//*
			switchTurn();											//*			
			BoardPlay++;											//*
		} else {
			gameState = State.INVALID; //Indicates invalid turn 
			System.out.println("Invalid Turn 1");
		}

	}

	public static boolean checkDefault(int i) {
		//Method that checks to see if based on a given case/scenario  of the game the disk selected for play or removal is in its default position or not
		if (millFound > -1) { //In the case of a new mill being formed
			if (counters[i].getX() != counters[i].getdefX() && counters[i].getY() != counters[i].getdefY()) {
				//The above conditional statement verifies that the disk to be removed is on the board and not in its default position only then will it be true
				return true;
			} else {
				System.out.println("False Defualt");
				return false;
			}
		} else {
			if (BoardPlay < 12) { //In the case that less than 12 turns are played
				if (counters[i].getX() == counters[i].getdefX() && counters[i].getY() == counters[i].getdefY()) {
					//Conditional statement verifies that disk selected is at its default (home) location
					return true;
				}
				

			} else { //In the case that more than 11 turns are played
				if (counters[i].getX() != counters[i].getdefX() && counters[i].getY() != counters[i].getdefY()) {
					//Conditional statement verifies the disk selected is on the board
					return true;
				}
			}
			return false;
		}
	}

	private static void addDisk(Color state) {
		//Method that increments the count of each colored disk (Red and Blue) as each disk is placed on the board
		//Method takes object state of type Color as a parameter. During method call the color object indicates which colored disk in being placed on the board
		if (state == Color.BLUE) { //Conditional block verifies color and provides function to increase count
			
			blueP++; //blueP represents the number of blue disks placed on the board

			
		} else if (state == Color.RED) {
			
			redP++; //redP represents the number of red disks placed on the board
			
		}
		boardP++; //boardP represents the total number of disks placed on the board
		
		

	}

	private static void switchTurn() {
		// TODO Auto-generated method stub
		if (Turn == Color.BLUE && millFound == -1) {
			Turn = Color.RED;
			
		} else if (Turn == Color.RED && millFound == -1) {
			Turn = Color.BLUE;
			
		}

	}

	public static boolean checkTurn(int i) {
		if (millFound > -1 && counters[i].getColor() != Turn) {
			return true;
		} else if (millFound == -1 && counters[i].getColor() == Turn) {
			return true;
		}
		System.out.println("Turn false");
		System.out.println(millFound);
		System.out.println(counters[i].getColor());
		System.out.println(Turn);
		return false;
		
	}

	public static void diskSelect(int i) {
		selected = i;
		gameState = State.DISKSELECTED;
	}

	public static void diskDeselect() {
		selected = -1;
		gameState = State.DISKDESELCTED;
	}

	public static Color getTurn(int i) {
		return counters[i].getColor();
	}

	public static Color getTurn() {
		return Turn;
	}

	public static void removeSwap(int i) {
		Color state = counters[i].getColor();
		int X1 = counters[i].getX() + 17;
		int Y1 = counters[i].getY() + 17;
		counters[i].setDefault();
		removePiece(X1, Y1);
		subDisk(state);
		gameState = State.PIECEREMOVED;
		selected = -1;
	}

	private static void subDisk(Color state) {
		// TODO Auto-generated method stub
		if (state == Color.BLUE) {
			blueP--;
			boardP--;
		} else {
			redP--;
			boardP--;
		}
	}

	private static void checkWin() {
		if (blueP == 2 && BoardPlay > 12) {
			gameState = State.REDWIN;
		} else if (redP == 2 && BoardPlay > 12) {
			gameState = State.BLUEWIN;
		}
	}

	public static boolean checkMove(int i, int j, int k) {
		if (BoardPlay >= 12) {
			int X1 = counters[i].getX() + 17;
			int Y1 = counters[i].getY() + 17;
			int X2;
			int Y2;
			if (k == 1) {
				X2 = N1[j].getX();
				Y2 = N1[j].getY();
			} else {
				X2 = N2[j].getX();
				Y2 = N2[j].getY();
			}
			if (distanceTo(X1, Y1, X2, Y2) == 250 || distanceTo(X1, Y1, X2, Y2) == 125) {
				removePiece(X1, Y1);
				swap(i, j, k);
				return true;
			} else {
				gameState = State.INVALID;
				System.out.println("Invalid Turn 2");
				return false;
			}

		} else {
			swap(i, j, k);
			return true;
		}

	}

	private static void removePiece(int x1, int y1) {
		for (int i = 0; i < 8; i++) {
			if (N1[i].getX() == x1 && N1[i].getY() == y1) {
				N1[i].ChangeState(Color.WHITE);
				break;
			} else if (N2[i].getX() == x1 && N2[i].getY() == y1) {
				N2[i].ChangeState(Color.WHITE);
				break;
			}
		}

	}

	private static int distanceTo(int x1, int y1, int x2, int y2) {
		int x = (x2 - x1) * (x2 - x1);
		int y = (y2 - y1) * (y2 - y1);
		int distance = (int) Math.sqrt(x + y);
		
		return distance;
	}

	public static int checkMill() {
		
		
		return millFound;
	}

	public static void updateMill() {
		int j = 0;
		for (int i = 1; i < 8; i = i + 2) {
			if (i == 7) {
				if (compareState(N1[i - 1].getState(), N1[i].getState(), N1[i - 7].getState())) {
					if (Line[j].getFormed() == true) {
						if (Line[j].getMode() == 0) {
							millFound = j;
							gameState = State.MILLFORMED;

						}
					} else {
						Line[j].Found();
						millFound = j;
						gameState = State.MILLFORMED;
					}
				} else {
					Line[j].notFormed();
				}
				if (compareState(N2[i - 1].getState(), N2[i].getState(), N2[i - 7].getState())) {
					if (Line[j + 4].getFormed() == true) {
						if (Line[j + 4].getMode() == 0) {
							millFound = j + 4;
							gameState = State.MILLFORMED;
						}
					} else {
						Line[j + 4].Found();
						millFound = j + 4;
						gameState = State.MILLFORMED;
					}
				} else {
					Line[j + 4].notFormed();

				}
				j++;
			} else {
				if (compareState(N1[i - 1].getState(), N1[i].getState(), N1[i + 1].getState())) {
					if (Line[j].getFormed() == true) {
						if (Line[j].getMode() == 0) {
							millFound = j;
							gameState = State.MILLFORMED;

						}
					} else {
						Line[j].Found();
						millFound = j;
						gameState = State.MILLFORMED;
					}
				} else {
					Line[j].notFormed();

				}
				if (compareState(N2[i - 1].getState(), N2[i].getState(), N2[i + 1].getState())) {
					if (Line[j + 4].getFormed() == true) {
						if (Line[j + 4].getMode() == 0) {
							millFound = j + 4;
							gameState = State.MILLFORMED;

						}
					} else {
						Line[j + 4].Found();
						millFound = j + 4;
						gameState = State.MILLFORMED;
					}
				} else {
					Line[j + 4].notFormed();

				}
				j++;
			}
		}
	}

	private static boolean compareState(Color state, Color state2, Color state3) {
		// TODO Auto-generated method stub
		if (state == state2 && state2 == state3 && state != Color.WHITE) {
			return true;
		} else {
			return false;
		}
	}

	public static void setModeOff(int i) {
		// TODO Auto-generated method stub
		removeSwap(i);
		Line[millFound].OffMode();
		millFound = -1;
		checkWin();
		switchTurn();
		updateMill();

	}

	public static String readGameState() {
		// TODO Auto-generated method stub
		if (gameState == State.BLUEWIN) {
			return "Blue Wins";

		} else if (gameState == State.REDWIN) {
			return "Red Wins";
		} else if (gameState == State.INVALID) {
			return "Invalid Turn";
		} else if (gameState == State.DISKSELECTED) {
			return "Disk Selected";
		} else if (gameState == State.DISKDESELCTED) {
			return "Disk De-selected";
		} else if (gameState == State.MILLFORMED) {
			return "Mill Formed\nRemove One Piece Of Opponent";
		} else if (gameState == State.PIECEREMOVED) {
			return " Disk Removed\n Game In Progress";
		} 
		else if (gameState==State.SAVEGAME){
			return "Game Saved\n Game In Progress";
		}
		else if (gameState==State.LOADGAME){
			return "Loaded Saved Game\n Game In Progress";
		}
		else {
			return "Game In Progress";
		}
	}

	public static void invalidTurn() {
		// TODO Auto-generated method stub
		gameState = State.INVALID;
		System.out.println("Invalid Turn 3");
	}

	public static boolean nodeMatch1(int x1, int y1, int f) {
		int x2 = N1[f].getX();
		int y2 = N1[f].getY();
		if (((x1 >= x2 - 7) && (x1 <= x2 + 7)) && ((y1 >= y2 - 7) && (y1 <= y2 + 7))) {
			return true;
		}
		return false;
	}

	public static boolean nodeMatch2(int x1, int y1, int f) {
		int x2 = N2[f].getX();
		int y2 = N2[f].getY();
		if (((x1 >= x2 - 7) && (x1 <= x2 + 7)) && ((y1 >= y2 - 7) && (y1 <= y2 + 7))) {
			return true;
		}
		return false;

	}

	public static boolean coordMatch(int x1, int y1, int f) {
		int x2 = counters[f].getX();
		int y2 = counters[f].getY();
		if (((x1 >= x2) && (x1 <= x2 + 35)) && ((y1 >= y2) && (y1 <= y2 + 35))) {
			return true;
		}
		return false;
	}

	public static void SaveGame() {
		// TODO Auto-generated method stub
		try{
			PrintWriter writer =  new PrintWriter(new File(GameManager.class.getResource("/res/GameSave.txt").getPath()));
			  for(int i=0;i<12;i++){
				writer.println(counters[i].getX()+", "+counters[i].getY());
			  }
			  for (int i=0;i<8;i++){
				if( N1[i].getState()==Color.BLUE){
					writer.println(1);
				}else if( N1[i].getState()==Color.RED){
					writer.println(2);
				}
				else{
					writer.println(0);
				}
			  }
			  for(int i=0;i<8;i++){
				  if( N2[i].getState()==Color.BLUE){
						writer.println(1);
					}else if( N2[i].getState()==Color.RED){
						writer.println(2);
					}
					else{
						writer.println(0);
					}
			  }
			  for(int i=0;i<8;i++){
				  writer.println(Line[i].getFormed()+", "+Line[i].getMode());
			  }
			  writer.println(BoardPlay);
			  if( Turn==Color.BLUE){
					writer.println(1);
				}else{
					writer.println(2);
				}
			  writer.println(redP);
			  writer.println(blueP);
			  writer.println(boardP);
			  writer.println(millFound);
			  writer.println(selected);
			  writer.println(gameState);
			  writer.close();
			
		}catch(IOException e){
			
		}
		gameState=State.SAVEGAME;
	}
	public void LoadGame(){
		 InputStreamReader isReader=  new InputStreamReader(GameManager.class.getResourceAsStream("/res/GameSave.txt"));   
		 BufferedReader br = new BufferedReader(isReader);   
		int count=0;
		int[] param= new int[3];
		String line;
		try {
			while((line=br.readLine())!=null){
				
				if(count<12){
					String[] splitter= line.split(", ");
					counters[count].setCoord(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1]));
				}
				else if(count>=12 && count<20){
					if(Integer.parseInt(line)==1){
					N1[count-12].ChangeState(Color.BLUE);
					}
					else if(Integer.parseInt(line)==2){
						N1[count-12].ChangeState(Color.RED);
					}
					else{
						N1[count-12].ChangeState(Color.WHITE);
					}
				}
				else if(count>=20 && count<28){
					if(Integer.parseInt(line)==1){
						N2[count-20].ChangeState(Color.BLUE);
						}
						else if(Integer.parseInt(line)==2){
							N2[count-20].ChangeState(Color.RED);
						}
						else{
							N2[count-20].ChangeState(Color.WHITE);
						}
					/*String[] splitter= line.split("\\[");
					String[] splitter1=splitter[1].split(",");
					for(int i=0;i<splitter1.length;i++){
						String[] splitter2=splitter1[i].split("=");
						if(i==2){
							param[i]=Integer.parseInt(splitter2[1].substring(0, splitter2[1].length()-1));
			
						}
						else{
							param[i]=Integer.parseInt(splitter2[1]);
						}
					}
					N2[count-20].ChangeState(new Color(param[0], param[1], param[2]));*/
				}
				else if(count>=28 && count<36){
					String[] splitter= line.split(", ");
					Line[count-28].setMill(Boolean.parseBoolean(splitter[0]), Integer.parseInt(splitter[1]));
				}else if(count==36){
					BoardPlay=Integer.parseInt(line);
				
				}
			else if(count==37){
			/*	String[] splitter= line.split("\\[");
				String[] splitter1=splitter[1].split(",");
				for(int i=0;i<splitter1.length;i++){
					String[] splitter2=splitter1[i].split("=");
					if(i==2){
						param[i]=Integer.parseInt(splitter2[1].substring(0, splitter2[1].length()-1));
		
					}
					else{
						param[i]=Integer.parseInt(splitter2[1]);
					}
				}
				Turn= new Color(param[0], param[1], param[2]);*/
				if(Integer.parseInt(line)==1){
					Turn=Color.BLUE;
					}
					else{
						Turn=Color.RED;
					}
			}
			else if(count==38){
				redP=Integer.parseInt(line);
			}
			else if(count==39){
				blueP=Integer.parseInt(line);
			}
			else if(count==40){
				boardP=Integer.parseInt(line);
			}
			else if(count==41){
				millFound=Integer.parseInt(line);
			}
				count++;
			 }
			selected=-1;
			gameState=State.LOADGAME;
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getN1Y(int i) {
		// TODO Auto-generated method stub
		return N1[i].getY() ;
	}

	public static int getN1X(int i) {
		// TODO Auto-generated method stub
		return N1[i].getX();
	}

	public static int getN2X(int i) {
		// TODO Auto-generated method stub
		return N2[i].getX();
	}

	public static int getN2Y(int i) {
		// TODO Auto-generated method stub
		return N2[i].getY();
	}

	public static int countersgetX(int s) {
		// TODO Auto-generated method stub
		return counters[s].getX();
	}
	public static int countersgetY(int s) {
		// TODO Auto-generated method stub
		return counters[s].getY();
	}
	public static void turnblue() {
		// TODO Auto-generated method stub
		Turn=Color.BLUE;
	}

}
