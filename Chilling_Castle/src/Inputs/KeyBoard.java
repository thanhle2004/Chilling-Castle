package Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static Main.GameStates.*;

import Main.Game;
import Main.GameStates;
import Scenes.Playing;

public class KeyBoard implements KeyListener {
    private Game game; // Thêm biến đối tượng Playing

    // Constructor để truyền đối tượng Playing
    public KeyBoard(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
    	if(e.getKeyCode() == KeyEvent.VK_N) {
    		game.getPlaying().switchMap();
    	}
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
