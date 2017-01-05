package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import Controller.InputHandler;
import Model.Disks;
import Model.GamePlay;
import Model.Node;

public class boardInterface extends JPanel {
	private final Color peach= new Color(255, 228, 184);
	private final Color brown= new Color(102, 51, 0);
	private final Color Lblue= new Color(102, 178, 255);
	private final Color Lred= new Color(255, 102, 102);
	public boardInterface(InputHandler MouseContorl){
		this.setBackground(peach); //Sets background color to peach 
		this.setBounds(20, 40, 660, 660);
		this.setOpaque(true); 
		this.addMouseListener(MouseContorl);
		this.paintComponent(null);
		}
	
	
	public void paintComponent(Graphics g){//This is the method that takes in the graphics and draws the board, pieces and other components of the game onto the Game Window
		try{ 
			super.paintComponent(g);
			drawBorder(g);
			drawBoard(g);
			drawNodes(g);
			drawDisks(g);
			drawSelect(g);
			draw();
		}catch(NullPointerException e){

		}
	}
	public void drawBorder(Graphics g){
		try{ 
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				    RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(brown);
			g2.setStroke(new BasicStroke(25));
			g2.drawRect(0, 0, 660, 660);
			
		}catch(NullPointerException e){

		}
	}
	public void drawBoard(Graphics g){ 
		try{
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				    RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(5));
			g2.setColor(Color.BLACK);
			g2.drawRect(80, 80, 500, 500);
			g2.drawRect(205, 205, 250, 250);
			g2.drawLine(330, 80, 330, 205);
			g2.drawLine(80, 330, 205, 330);
			g2.drawLine(330, 580, 330, 455);
			g2.drawLine(580, 330, 455, 330);
		}catch(NullPointerException e){

		}
	}
	public void drawNodes(Graphics g){ 
		
		try{
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				    RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(5));
			g2.setColor(Color.BLACK);
			try{
				for(int i=0;i<8;i++){
					g2.drawOval(GamePlay.getN1X(i)-5,GamePlay.getN1Y(i)-5,10, 10);
					g2.drawOval(GamePlay.getN2X(i)-5,GamePlay.getN2Y(i)-5,10, 10);
				}
				}catch(NullPointerException ex){
					
				}
			
		}catch(NullPointerException e){
	
		}
		}
	public void drawSelect(Graphics g){
		if (GamePlay.selected>-1){
			try{
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setStroke(new BasicStroke(5));
				if(GamePlay.getTurn(GamePlay.selected)==Color.BLUE){
				g2.setColor(Lblue);
				}
				else{
					g2.setColor(Lred);
				}
				g2.drawOval(GamePlay.countersgetX(GamePlay.selected), GamePlay.countersgetY(GamePlay.selected), 35, 35);;
		}
			catch(NullPointerException e){
				
			}
		}
	}
	public void drawDisks(Graphics g){
		try{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(5));
		int X=80;
		for(int i=0;i<6;i++){
			g2.setColor(Color.BLUE);
			g2.fillOval(GamePlay.countersgetX(i), GamePlay.countersgetY(i), 35, 35);
			g2.setColor(Color.RED);
			g2.fillOval(GamePlay.countersgetX(i+6), GamePlay.countersgetY(i+6), 35, 35);
		}
		}
		catch(NullPointerException e){
			
		}
		}	
	public void draw(){//once new components are added the revalidated and repaint functions are called to redraw the board
		super.revalidate();
		super.repaint ();

	}
	
	
}
