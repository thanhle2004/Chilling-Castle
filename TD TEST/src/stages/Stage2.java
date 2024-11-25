package stages;




import Map.Level2;
import main.Game;

import managers.StageManager;
import scenes.Settings;
import ui.NotificationGameOver;

import ui.SettingBoardUI;
import ui.TowerBar;



public class Stage2 extends StageManager {

	public Stage2(Game game, TowerBar towerBar, Settings settings) {
		super(game, towerBar, settings, false, false);
		MapLoader();
	}

	@Override
	protected NotificationGameOver createNotificationGameOver() {
		return new NotificationGameOver(0, 0, 100, 100, this);
	}

	@Override
	protected ui.SettingBoardUI createSettingBoardUI() {
		return new SettingBoardUI(37, 15, 560, 555, settings, this);
	}

	@Override
	protected void MapLoader() {
		level = Level2.getLevelData2();
	}

	@Override
	public void resetGame() {
		super.resetGame();
	}


}
