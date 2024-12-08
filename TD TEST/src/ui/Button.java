package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Button {

	public int x, y, width, height, id;
	protected String text;
	protected Rectangle bounds;
	protected boolean mouseOver, mousePressed, mouseClicked;
	protected BufferedImage img;


	public Button(String text, int x, int y, int width, int height, BufferedImage img) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;

        initBounds();
    }

	public Button(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		initBounds();
	}

	public Button(int x, int y, int width, int height, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;

		initBounds();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getImage() {
		return img;
	}

	private void initBounds() {
		this.bounds = new Rectangle(x, y, width, height);
	}

	public void draw(Graphics g) {
	    if (img != null) {
	        g.drawImage(img, x, y, width, height, null);
	        
	        if (mouseOver) {
	        	g.drawImage(img, x - 1, y - 1, width + 3, height + 3, null);
	        }
	    }
		else {

	        drawBody(g);
	        drawBorder(g);
	        drawText(g);
	    }
	}

	private void drawBorder(Graphics g) {

		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		if (mousePressed) {
			g.drawRect(x + 1, y + 1, width - 2, height - 2);
			g.drawRect(x + 2, y + 2, width - 4, height - 4);
		}

	}

	private void drawBody(Graphics g) {
		if (mouseOver || mouseClicked)
			g.setColor(Color.gray);
		else
			g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);

	}

	public void drawText(Graphics g) {
			int w = g.getFontMetrics().stringWidth(text);
			int h = g.getFontMetrics().getHeight();
			g.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 2);
	}

	public void resetBooleans() {
		this.mouseOver = false;
		this.mousePressed = false;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getId() {
		return id;
	}

	public void setMouseClicked(boolean mouseClicked) { this.mouseClicked = mouseClicked; }

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public void setPosX(int x) {
		this.x = x;
	}

	public void setPosY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}
}
