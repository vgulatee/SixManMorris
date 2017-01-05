package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.omg.CORBA.portable.InputStream;

import Model.GamePlay;
import Model.Node;
import View.ActionButtons;
import View.RadioButtons;
import View.boardInterface;
import View.infoInterface;



public class GameManager {
	private JFrame window;
	private ImageIcon image1=new ImageIcon(getClass().getResource("/res/stock_wood_texture_1_by_camo_stock.png"));
	private static JLabel background;
	private static JLayeredPane LPane;
	private static JLabel title;
	private static InputHandler MouseControl= new InputHandler();	
	public static RadioButtons oneP= new RadioButtons("One Player", MouseControl);
	public static RadioButtons twoP= new RadioButtons("Two Players", MouseControl);
	public static ActionButtons start= new ActionButtons("Start Game", MouseControl);
	public static ActionButtons load= new ActionButtons("Load Game", MouseControl);
	public static ActionButtons save= new ActionButtons("Save Game", MouseControl);
	public static ActionButtons back= new ActionButtons("Back To Menu", MouseControl);
	//public static ActionButtons resign= new ActionButtons("Draw Game", MouseControl);
	//public static ActionButtons forfeit= new ActionButtons("Forfeit Game", MouseControl);
	private static GamePlay GP;
	private static boardInterface board;
	private static infoInterface info;
	
	public GameManager(){
		 
		GraphicsEnvironment ge= GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			java.io.InputStream I=GameManager.class.getResourceAsStream("/res/GeosansLight.ttf");
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,I ));
			
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		//int windowWidth = (int) (3.00/4.00*(float) gd.getDefaultConfiguration().getBounds().getWidth());
		//int windowHeight = (int) gd.getDefaultConfiguration().getBounds().getHeight();
		int windowWidth=1024;
		int windowHeight=768;
		System.out.println(windowWidth+", "+windowHeight);
		window= new JFrame();
		background= new JLabel(image1);
		title=new JLabel("Six Men's Morris");
		title.updateUI();
	
		title.setFont(new Font("GeosansLight",0,80));
		title.setBounds(250, 150, 600, 200);
		background.add(title);
		//GamePlay GP= new GamePlay();
		//boardInterface board= new boardInterface();
		LPane=new JLayeredPane();
		window.setTitle("Six Men's Morris");
		window.setSize(windowWidth, windowHeight);
		window.add(LPane, BorderLayout.CENTER);
		LPane.setBounds(0,0,windowWidth, windowHeight);
		
		background.setBounds(0, 0, windowWidth, windowHeight );
		background.setOpaque(true);
		background.addMouseListener(MouseControl);
		
		LPane.add(background, new Integer(0), 0);
		
		ButtonGroup Menu=new ButtonGroup();
		oneP.setBounds(370, 384, 130, 50);
		twoP.setBounds(500, 384, 120, 50);
		start.setBounds(370, 449, 250, 50);
		load.setBounds(370, 514, 250, 50);
		/*resign.setBounds(730, 435, 250, 50);
		forfeit.setBounds(730, 500, 250, 50);*/
		save.setBounds(730, 420,250, 50);
		back.setBounds(730, 550, 250, 50);
	
		save.setVisible(false);
		back.setVisible(false);
		/*forfeit.setVisible(false);
		resign.setVisible(false);*/
		Menu.add(oneP);
		Menu.add(twoP);
		
		background.add(oneP);
		background.add(twoP);
		background.add(start);
		background.add(load);
		background.add(save);
		background.add(back);
		/*background.add(forfeit);
		background.add(resign);*/
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);	
	}
	
	public static void TwoPlayer(){
		oneP.setVisible(false);
		twoP.setVisible(false);
		title.setVisible(false);
		start.setVisible(false);
		load.setBounds(730, 485, 250, 50);
		/*forfeit.setVisible(true);
		resign.setVisible(true);*/
		save.setVisible(true);
		back.setVisible(true);
		background.revalidate();
		GP= new GamePlay(2);
		board= new boardInterface(MouseControl);
		info=new infoInterface();
		LPane.add(board, new Integer(1), 0);
		LPane.add(info,new Integer(2), 0) ;
		
	}
	public static void BackMenu(){
		oneP.setVisible(true);
		twoP.setVisible(true);
		title.setVisible(true);
		start.setVisible(true);
		load.setBounds(370, 514, 250, 50);
		save.setVisible(false);
		back.setVisible(false);
		board.setVisible(false);
		info.setVisible(false);
		LPane.remove(board);
		GP=null;
		board=null;
		info=null;
	}

	
	
	
	public static void main (String[] args){
		GameManager GM= new GameManager();
		
	
	}

	public static void OnePlayer() {
		// TODO Auto-generated method stub
		
	}

	public static void LoadGAME() {
		if(twoP.isVisible()==true){
			TwoPlayer();
			GP.LoadGame();
		}else{
		GP.LoadGame();
		}
		
	}


}
