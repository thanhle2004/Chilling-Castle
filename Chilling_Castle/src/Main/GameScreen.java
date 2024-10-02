package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

import Inputs.KeyBoard;
import Inputs.Mouse;
import java.io.InputStream;

public class GameScreen extends JPanel {

    private Dimension size;    
    private int NumberofImageCol = 30;
    private int NumberofImageRow = 20;
    private final int scale = 1;
    private final int maxScreenHeight =  NumberofImageRow * scale * 32;
    private final int maxScreenWidth =  NumberofImageCol *scale * 32;
    
    Mouse mouse;
    KeyBoard keyboard;
    private Game game;
    
    public GameScreen(Game game) {
    	
    	this.game = game;
        initInputs();
        SetSizeScreen();
        
    }
    
    //Input key and mouse
	private void initInputs() {
    	mouse = new Mouse();
    	keyboard = new KeyBoard(game);
    	addMouseListener(mouse);
    	addMouseMotionListener(mouse);
    	addKeyListener(keyboard);
    	setFocusable(true);
    	requestFocus();
    }

    private void SetSizeScreen() {
    	size = new Dimension(maxScreenWidth,maxScreenHeight+100);
    	setMinimumSize(size);
    	setMaximumSize(size);
    	setPreferredSize(size);
	}



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);       
        // Gọi phương thức render của class Playing
        if (game.getPlaying() != null) {
            game.getPlaying().render(g);
        }
    }



}
