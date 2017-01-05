package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import Controller.GameManager;
import Controller.InputHandler;
;

public class ActionButtons extends JButton {
	
	public ActionButtons(String name, InputHandler MouseControl){
		
		this.setText(name);
		this.setBackground(new Color(255, 228, 184));
		Border border = new LineBorder(new Color(102, 51, 0), 5);
		this.setBorder(border);
		this.setFont(new Font("GeosansLight",1,15));
		this.addActionListener(MouseControl);
		this.updateUI();
	}
}
