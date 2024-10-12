package main;

import java.awt.Graphics;

public class Render {

	private Game game;

	public Render(Game game) {
		this.game = game;

	}

	public void render(Graphics g) {

		switch (GameStates.gameState) {

		case MENU:
			game.getMenu().render(g);

			break;
		case STAGE1:

			game.getStage1().render(g);

			break;
		case STAGE2:

			game.getStage2().render(g);

			break;
		case SETTINGS:

			game.getSettings().render(g);

			break;
		default:
			break;

		}

	}

}