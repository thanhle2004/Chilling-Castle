package UI;

import java.awt.Color;
import java.awt.Graphics;

import Scenes.Playing;

public class Buttons {
	
	private int x,y,Width, Height;
	
	public Buttons(int x, int y, int Width, int Height) {
		this.x = x;
		this.y = y;
		this.Width = Width;
		this.Height = Height;
	}
	
	public void draw(Graphics g) {
		drawBody(g);
	}
	
	private void drawBody(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, Width, Height);
	}
	
	private void drawBorder(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(x, y, Width, Height);
	}
	
	
	
}
