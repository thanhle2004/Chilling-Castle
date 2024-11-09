package stages;


import static helpz.Constants.Enemy.*;

import Map.Level2;
import main.Game;
import managers.StageManager;
import scenes.Settings;
import ui.TowerBar;

public class Stage2 extends StageManager {

	public Stage2(Game game, TowerBar towerBar, Settings settings) {
		super(game, towerBar, settings);
	}

	@Override
	protected void MapLoader() {
		level = Level2.getLevelData2();
	}

	@Override
	protected void resetGame() {
		super.resetGame();
		enemyManager.addEnemy(DUDE,0, 1*32);
	}

}
