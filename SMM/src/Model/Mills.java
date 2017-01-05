package Model;

//Helper Class that represents the properties of Mills 
public class Mills {  
	private boolean formed; //Indicates if a mill is formed or not 
	private int mode; //Indicates if a mill formed is new(0) or existing(1)
	
	//Constructor
	public Mills(){
		//Creates default mill object for all lines of the board and gives it the properties unformed and mode 0
		formed=false; 
		mode=0; 
	}
	
	//Accessors
	public  boolean getFormed(){
		//Method returns boolean value of formed that represents formed (true) and unformed (false)
		return formed;
	}
	public int getMode() {
		//Method returns integer value of mode 
		return mode;
	}
	
	//Mutators
	public void Found() {
		//Method assigns true to boolean object formed
		formed=true;
	}
	
	public void notFormed() {
		//Method re-initializes parameters of Mill object
		formed=false;
		mode=0;
	}
	public void OffMode() {
		//Method assigns value of mode to 1
		mode=1;
	}
	public void setMill( boolean form, int mod){
		formed=form;
		mode=mod;
	}
}
