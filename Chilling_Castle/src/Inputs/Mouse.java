package Inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Main.Game;
import Main.GameScreen;
import Main.GameStates;

public class Mouse implements MouseListener, MouseMotionListener{
	
	private Game game;
	private GameStates gameStates;
	public Mouse(Game game) {
		this.game = game;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(gameStates == GameStates.PLAYING) {
			game.getPlaying().mouseMoved(e.getX(), e.getY());
			System.out.println("X:" + e.getX() + "Y" + e.getY());
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
