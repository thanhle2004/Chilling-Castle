package UI;

import java.awt.Color;
import java.awt.Graphics;

import Scenes.Playing;

public class ButtonBottomBar {
	
	private int x,y,Width, Height;
	public ButtonBottomBar(int x, int y, int Width, int Height ,Playing playing) {
		this.x = x;
		this.y = y;
		this.Width = Width;
		this.Height = Height;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, Width, Height);
	}

}
