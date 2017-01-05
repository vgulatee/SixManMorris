package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Model.GamePlay;

public class infoInterface extends JPanel {
	private final Color peach= new Color(255, 228, 184);
	private final Color brown= new Color(102, 51, 0);
	private final Color lbrown = new Color(153, 102, 51);
	private JTextPane stateText=new JTextPane();
	//private JTextArea gameText=new JTextArea();
	//private JScrollPane scrollPane= new JScrollPane(gameText);
	private String turn=" ";
	private String game=" ";
	
	
	public infoInterface(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setBackground(peach); //Sets background color to peach 
		this.setBounds(730, 125, 250, 250);
		this.setOpaque(true); 
		
		SimpleAttributeSet attribs = new SimpleAttributeSet();  
		  StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_CENTER);  
		 stateText.setParagraphAttributes(attribs,true);
		 StyledDocument doc = stateText.getStyledDocument();
		 StyleConstants.setFontFamily(attribs, "GeosansLight");
		 StyleConstants.setFontSize(attribs, 16);
		StyleConstants.setBold(attribs, true);
		try
		{
		    doc.insertString(0, "Start of text\n", null );
		    doc.insertString(doc.getLength(), "\nEnd of text", attribs );
		}
		catch(Exception e) { System.out.println(e); }
		stateText.setBackground(peach);
		stateText.setEditable(false);
		stateText.setPreferredSize(new Dimension(230, 50));
		/*gameText.setBackground(peach);
		scrollPane.setBackground(brown);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setPreferredSize(new Dimension(230, 330));*/
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.ipady=20;
		c.gridy = 0;
		c.weightx=0.5;
		c.weighty=0.5;
		this.add(stateText,c );
		/*c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 350;
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(2,0,0,0);
		c.gridx = 0;
		c.gridy = 1;
		c.weighty=0.55;
		//this.add(scrollPane, c);*/
		Border border = new LineBorder(new Color(102, 51, 0), 5);
		this.setBorder(border);
		this.paintComponent(null);
		}
	public void paintComponent(Graphics g){//This is the method that takes in the graphics and draws the board, pieces and other components of the game onto the Game Window
		try{ 
			super.paintComponent(g);
			writeTurn();
			draw();
		}catch(NullPointerException e){

		}
	}
	private void writeTurn() {
		// TODO Auto-generated method stub
		if(GamePlay.getTurn()==Color.BLUE){
			turn="Turn Blue\n";
			
			
		}
		else if(GamePlay.getTurn()==Color.RED){
			turn="Turn Red\n";
			
		}
		if(GamePlay.readGameState()=="Blue Wins"){
			//stateText.set
			turn="Game Over\n";
			game="Blue Wins";
		}
		else if(GamePlay.readGameState()=="Red Wins"){
			turn="Game Over\n";
			game="Red Wins";
		}
		else{
			game=GamePlay.readGameState();
		}
		stateText.setText(turn+game);
	}
	public void draw(){//once new components are added the revalidated and repaint functions are called to redraw the board
		super.revalidate();
		super.repaint ();
	}
	
}
