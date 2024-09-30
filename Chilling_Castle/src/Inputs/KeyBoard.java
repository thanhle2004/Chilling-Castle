package Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static Main.GameStates.*;
import Main.GameStates;

public class KeyBoard implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			GameStates.gameStates = MENU;
		}
		else if(e.getKeyCode() == KeyEvent.VK_W) {
			GameStates.gameStates = PLAYING;
		}
		else if(e.getKeyCode() == KeyEvent.VK_E) {
			GameStates.gameStates = SETTING;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
}
