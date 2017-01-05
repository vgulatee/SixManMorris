package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import Model.GamePlay;

public class InputHandler implements MouseListener, ActionListener {
	int mouseFlag=-1;
	int nodeFlag=-1;
	int mode=-1;
	@Override
	public void mouseClicked(MouseEvent ea) {
		if(GamePlay.readGameState()=="Blue Wins" || GamePlay.readGameState()=="Red Wins"){
			ea.consume();
		}else{
		System.out.println(ea.getX());
		System.out.println(ea.getY());
		if(ea.getButton()==MouseEvent.BUTTON3){
			System.out.println("Deselcted Disk: "+mouseFlag);
			GamePlay.diskDeselect();
			mouseFlag=-1;
			
		}
		else{
		if(mouseFlag==-1){
				for(int i=0;i<12;i++){
					if(GamePlay.coordMatch(ea.getX(), ea.getY(), i)){
						if(GamePlay.checkTurn(i) && GamePlay.checkDefault(i)){
						mouseFlag=i;
						GamePlay.diskSelect(i);
						System.out.println("Disk:"+ mouseFlag);
						break;
						}
						else{
							GamePlay.invalidTurn();
							break;
						}
					}
				}
		}else if(mouseFlag>-1){
			if(GamePlay.checkMill()>-1 && GamePlay.coordMatch(ea.getX(), ea.getY(), mouseFlag)){
				GamePlay.setModeOff(mouseFlag);
				mouseFlag=-1;
			}
			else{
			for(int i=0;i<8;i++){
				if (GamePlay.nodeMatch1(ea.getX(), ea.getY(), i)){
					if(GamePlay.checkMove(mouseFlag, i, 1)){
					System.out.println("N1 "+i);
					mouseFlag=-1;
					break;
					}
					
				}
				else if(GamePlay.nodeMatch2(ea.getX(), ea.getY(), i)){
					if(GamePlay.checkMove(mouseFlag, i, 2)){
						System.out.println("N2 "+i);
						mouseFlag=-1;
						break;
						}
				}
			}
			}
		}
		}
		}
	}
	
	

	@Override
	public void mouseEntered(MouseEvent ea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent ea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent ea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent ea) {
		// TODO Auto-generated method stub
		
	}
	public void actionPerformed(ActionEvent a) {
		if(a.getSource()==GameManager.twoP){
			this.mode=2;
		}
		else if (a.getSource()==GameManager.oneP){
			this.mode=1;
		}
		else if(a.getSource()==GameManager.start){
			if(this.mode==2){
			GameManager.TwoPlayer();
			}
			else if(this.mode==1){
				GameManager.OnePlayer();
			}
		}
		else if(a.getSource()==GameManager.load){
			GameManager.LoadGAME();
			mouseFlag=-1;
			nodeFlag=-1;
		}
		else if(a.getSource()==GameManager.save){
			GamePlay.SaveGame();
		}
		else if(a.getSource()==GameManager.back){
			GameManager.BackMenu();
		}
		
	}

}
