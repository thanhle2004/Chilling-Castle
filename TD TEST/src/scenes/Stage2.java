package scenes;

import static helpz.Constants.Tiles.*;
import static helpz.Constants.Enemy.*;
import java.awt.Graphics;


import Map.Level2;
import enemies.DUDE;
import main.Game;
import managers.EnemyManager;
import ui.ActionBar;

public class Stage2 extends GameScene implements SceneMethods{

	private int[][] lv2;
	private ActionBar bottomBar;
	private int mouseX, mouseY;
	private EnemyManager enemyManager;
	
	public Stage2(Game game) {
		super(game);
		MapLoader();
		bottomBar = new ActionBar(0, 640, 640, 100, this);
		enemyManager = new EnemyManager(this);
	}
	


	private void MapLoader() {
		lv2 = Level2.getLevelData2();     
	}

	public void setLevel2(int[][] lv2) {
		this.lv2 = lv2;
	}

	public void update() {
		updateTick();
		enemyManager.update();
	}



	@Override
	public void render(Graphics g) {
		drawLevel(g);
		bottomBar.draw(g);
		enemyManager.draw(g);	
	}


	private void drawLevel(Graphics g) {

		for (int y = 0; y < lv2.length; y++) {
			for (int x = 0; x < lv2[y].length; x++) {
				int id = lv2[y][x];
				if (isAnimation(id)) {
					g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
				} else
					g.drawImage(getSprite(id), x * 32, y * 32, null);
			}
		}
	}
	
	public int getTileType(int x, int y) {
		int xCol = x / 32;
		int yCol = y / 32;
		
		if(xCol < 0 || xCol > 19)
			return WATER_TILE;
		if(yCol < 0 || yCol > 19)
			return WATER_TILE;
		
		
		int id = lv2[y / 32][x / 32];
		return game.getTileManager().getTile(id).getTileType();
	}


	@Override
	public void mouseMoved(int x, int y) {
		if (y >= 640)
			bottomBar.mouseMoved(x, y);
		else {
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y >= 640) {
			bottomBar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		bottomBar.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		if (y >= 640)
			bottomBar.mouseClicked(x, y);
		else
			enemyManager.addEnemy(DUDE,x, y);
		
	}

}
