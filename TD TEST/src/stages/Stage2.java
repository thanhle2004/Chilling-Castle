package stages;

import Map.Level2;
import main.Game;

import managers.StageManager;
import scenes.Settings;
import ui.NotificationLoseGame;

import ui.NotificationWinGame;
import ui.SettingBoardUI;
import ui.TowerBar;



public class Stage2 extends StageManager {

	public Stage2(Game game, TowerBar towerBar, Settings settings) {
		super(game, towerBar, settings);
		MapLoader();
	}

	@Override
	protected NotificationWinGame createNotificationWinGame() {
		return new NotificationWinGame(120,120,400,400,this);
	}

	@Override
	protected NotificationLoseGame createNotificationGameOver() {
		return new NotificationLoseGame(120, 120, 400, 400, this);
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
