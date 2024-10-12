package scenes;

import java.awt.Graphics;
import main.Game;
import ui.MyButton;
import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {

	private MyButton bStage1, bSettings, bQuit, bStage2;

	public Menu(Game game) {
		super(game);
		initButtons();
	}

	private void initButtons() {

		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 150;
		int yOffset = 100;

		bStage1 = new MyButton("Stage1", x, y, w, h);
		bStage2 = new MyButton("Stage2", x, y + yOffset * 1, w, h);
		bSettings = new MyButton("Settings", x, y + yOffset * 2, w, h);
		bQuit = new MyButton("Quit", x, y + yOffset * 3, w, h);

	}

	@Override
	public void render(Graphics g) {

		drawButtons(g);

	}

	private void drawButtons(Graphics g) {
		bStage1.draw(g);
		bStage2.draw(g);
		bSettings.draw(g);
		bQuit.draw(g);

	}

	@Override
	public void mouseClicked(int x, int y) {

		if (bStage1.getBounds().contains(x, y))
			SetGameState(STAGE1);
		else if (bStage2.getBounds().contains(x, y))
			SetGameState(STAGE2);
		else if (bSettings.getBounds().contains(x, y))
			SetGameState(SETTINGS);
		else if (bQuit.getBounds().contains(x, y))
			System.exit(0);
	}

	@Override
	public void mouseMoved(int x, int y) {
		bStage1.setMouseOver(false);
		bSettings.setMouseOver(false);
		bQuit.setMouseOver(false);

		if (bStage1.getBounds().contains(x, y))
			bStage1.setMouseOver(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMouseOver(true);
		else if (bQuit.getBounds().contains(x, y))
			bQuit.setMouseOver(true);

	}

	@Override
	public void mousePressed(int x, int y) {

		if (bStage1.getBounds().contains(x, y))
			bStage1.setMousePressed(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMousePressed(true);
		else if (bQuit.getBounds().contains(x, y))
			bQuit.setMousePressed(true);

	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bStage1.resetBooleans();
		bSettings.resetBooleans();
		bQuit.resetBooleans();

	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub

	}

}