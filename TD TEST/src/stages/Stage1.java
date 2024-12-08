package stages;

import Map.Level1;
import main.Game;
import managers.StageManager;
import scenes.Settings;
import ui.NotificationLoseGame;
import ui.NotificationWinGame;
import ui.SettingBoardUI;
import ui.TowerBar;

public class Stage1 extends StageManager  {

    public Stage1(Game game, TowerBar towerBar, Settings settings) {
        super(game, towerBar, settings);
        MapLoader();
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
    protected NotificationWinGame createNotificationWinGame() {
        return  new NotificationWinGame(120,120,400,400,this);
    }

    @Override
    protected void MapLoader() {
        level = Level1.getLevelData1();
    }

    @Override
    public void resetGame() {
        super.resetGame();
    }


}
