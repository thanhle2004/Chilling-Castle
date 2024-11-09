package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.Game;


public class KeyboardListener implements KeyListener {
	private Game game;

	public KeyboardListener(Game game) {
		this.game = game;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}

