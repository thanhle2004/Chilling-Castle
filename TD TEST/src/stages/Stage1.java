package stages;

import Map.Level1;
import main.Game;
import main.GameStates;
import managers.StageManager;
import scenes.Settings;
import ui.NotificationGameOver;
import ui.SettingBoardUI;
import ui.TowerBar;

import java.awt.*;

public class Stage1 extends StageManager  {

    public Stage1(Game game, TowerBar towerBar, Settings settings) {
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
        level = Level1.getLevelData1();
    }

    @Override
    public void resetGame() {
        super.resetGame();
    }


}
