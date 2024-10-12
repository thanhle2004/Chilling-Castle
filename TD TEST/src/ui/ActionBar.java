package ui;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Graphics;

import scenes.Stage1;
import scenes.Stage2;

public class ActionBar extends Bar {

	private Stage1 playing1;
	private MyButton bMenu;
	private Stage2 playing2;

	public ActionBar(int x, int y, int width, int height, Stage1 playing) {
		super(x, y, width, height);
		this.playing1 = playing;

		initButtons();
	}
	
	public ActionBar(int x, int y, int width, int height, Stage2 playing) {
		super(x, y, width, height);
		this.playing2 = playing;

		initButtons();
	}

	private void initButtons() {

		bMenu = new MyButton("Menu", 2, 642, 100, 30);

	}

	private void drawButtons(Graphics g) {
		bMenu.draw(g);
	}

	public void draw(Graphics g) {

		// Background
		g.setColor(new Color(220, 123, 15));
		g.fillRect(x, y, width, height);

		// Buttons
		drawButtons(g);
	}

	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			SetGameState(MENU);

	}

	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
	}

	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);

	}

	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();

	}

}
