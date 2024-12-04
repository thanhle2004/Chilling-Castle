package stages;


import Map.Level1;
import Map.Level3;
import main.Game;
import main.GameStates;
import managers.StageManager;
import scenes.Settings;
import ui.NotificationGameOver;
import ui.SettingBoardUI;
import ui.TowerBar;

import java.awt.*;

public class Stage3 extends StageManager{

    public Stage3(Game game, TowerBar towerBar, Settings settings) {
        super(game, towerBar, settings, false);
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
        level = Level3.getLevelData3();
    }

    @Override
    public void resetGame() {
        super.resetGame();
    }


}

