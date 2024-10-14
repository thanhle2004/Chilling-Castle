package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {

	private MyButton bStage1, bSettings, bStage2 , bPlaying, bTowers;
	private BufferedImage  background, bPlayImg, bTowersImg, bSettingImg;
	
	public Menu(Game game) {
		super(game);
		importImg();
		initButtons();

	}

	
	private void importImg() {

		InputStream bg = getClass().getResourceAsStream("/BackgroundLobby.png");
		InputStream bplay = getClass().getResourceAsStream("/bPlay.png");
		InputStream btowers = getClass().getResourceAsStream("/bTowers.png");
		InputStream bsetting = getClass().getResourceAsStream("/bSetting.png");

		try {
			background = ImageIO.read(bg);
			bPlayImg = ImageIO.read(bplay);
			bTowersImg = ImageIO.read(btowers);
			bSettingImg = ImageIO.read(bsetting);		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void initButtons() {

		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 150;
		int yOffset = 90;

		bStage1 = new MyButton("Stage1", x, y, w, h,bPlayImg);
		bStage2 = new MyButton("Stage2", x, y + yOffset, w, h, bPlayImg);
		bPlaying = new MyButton("Play", x, y+ yOffset * 2, w, h, bPlayImg);
		bSettings = new MyButton("Settings", x, y + yOffset * 3, w, h, bSettingImg);
		bTowers = new MyButton("Towers", x, y + yOffset * 4, w, h, bTowersImg);

	}

	@Override
	public void render(Graphics g) {
		drawBackground(g);
		drawButtons(g);

	}

	private void drawButtons(Graphics g) {
		bStage1.draw(g);
		bStage2.draw(g);
		bSettings.draw(g);
		bTowers.draw(g);
		bPlaying.draw(g);

	}

	@Override
	public void mouseClicked(int x, int y) {

		if (bStage1.getBounds().contains(x, y))
			SetGameState(STAGE1);
		else if (bStage2.getBounds().contains(x, y))
			SetGameState(STAGE2);
		else if (bSettings.getBounds().contains(x, y))
			SetGameState(SETTINGS);
	
	}

	@Override
	public void mouseMoved(int x, int y) {
		bStage1.setMouseOver(false);
		bSettings.setMouseOver(false);

		if (bStage1.getBounds().contains(x, y))
			bStage1.setMouseOver(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMouseOver(true);

	}

	@Override
	public void mousePressed(int x, int y) {		
		if (bStage1.getBounds().contains(x, y))
			bStage1.setMousePressed(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMousePressed(true);
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bStage1.resetBooleans();
		bSettings.resetBooleans();


	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub

	}
	
	private void drawBackground(Graphics g) {
		g.drawImage(background, 0, 0, game);
	}

}