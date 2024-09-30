package Main;

import java.awt.Graphics;


public class Renders {
	
	Game game;
	public Renders (Game game) {
		this.game = game;
	}
	
    
	//RENDER IMAGE 
	public void renders(Graphics g) {
		
		switch(GameStates.gameStates) {
//		case MENU:
//			game.getMenu().render(g);	       
//			break;
		case PLAYING:
			game.getPlaying().render(g);
			break;
//		case SETTING:
//			game.getSetting().render(g);
//			break;
		}		
	}
}
