package UI;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.security.cert.X509Certificate;

import Scenes.Playing;
import Tiles.TilesManager;

public class BottomBar {
	
	private int x,  y,  Height,  Width;
	private Playing playing;
	private Buttons menuButtons,towerButtons;
	private TilesManager tilesManager;
	
	public BottomBar(int x, int y, int Width, int Height,Playing playhing) {
		this.x = x;
		this.y = y;
		this.Width = Width;
		this.Height = Height;
		this.playing = playing;
		tilesManager = new TilesManager();
		menuButtons = new Buttons(0, 640, 50, 50); 
		
	}
	

    public void draw(Graphics g) {
    	drawSetColorBottom(g);
    	drawMenuButton(g);
    	drawTowerButton(g);
    }
    
    private void drawSetColorBottom(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
        Color colorStart = new Color(45, 45, 45); // Dark gray
        Color colorEnd = new Color(85, 85, 85);   // Lighter gray
        GradientPaint gradient = new GradientPaint(0, y, colorStart, 0, y + Height, colorEnd);

        g2d.setPaint(gradient);
        g2d.fillRect(x, y, Width, Height);
    }
    
    private void drawMenuButton(Graphics g) {
    	 
    	menuButtons.draw(g);
    }
    
    private void drawTowerButton(Graphics g) {
    	int x = 60;
    	int y = 640;
    	int Width = 50;
    	int Height = 50;
    	int xOff = (int) (Width * 1.1f);
    	int countButton = tilesManager.towers.size();
    	for(int i = 0; i < countButton; i++) {
    		towerButtons = new Buttons(x, y, Width, Height);
    		towerButtons.draw(g);   		
    		BufferedImage imgBufferedImage = tilesManager.towers.get(i).getImage();
    		g.drawImage(imgBufferedImage, x, y, Width, Height, null);
    		x += xOff;
    	}	
    }
    
    
    public void mouseMoved(int x, int y) {
    	menuButtons.setMouseOver(false);
    	if(menuButtons.checkBounds().contains(x, y)) {
    		menuButtons.setMouseOver(true);
    	}
    }
    

}
