package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Scenes.Playing;

public class Buttons {
	
	private int x,y,Width, Height;
	private boolean isMouseOver;
	private Rectangle boundsRectangle;
	public Buttons(int x, int y, int Width, int Height) {
		this.x = x;
		this.y = y;
		this.Width = Width;
		this.Height = Height;
		setBounds();
	}
	
	private void setBounds() {
		boundsRectangle = new Rectangle(x, y, Width, Height);
	}
	
	public void draw(Graphics g) {
		drawBody(g);
		drawBorder(g);
	}
	
	private void drawBody(Graphics g) {
		if(isMouseOver) {
			g.setColor(Color.RED);
		}
		else {
			g.setColor(Color.WHITE);
		}
		
		g.fillRect(x, y, Width, Height);
	}
	
	private void drawBorder(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(x, y, Width, Height);
	}

//	public boolean isMouseOver() {
//		return isMouseOver;
//	}

	public void setMouseOver(boolean isMouseOver) {
		this.isMouseOver = isMouseOver;
	}
	
	
	public Rectangle checkBounds() {
		return boundsRectangle;
	}
	
	
}
