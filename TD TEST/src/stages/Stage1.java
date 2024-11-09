package stages;

import Map.Level1;
import inputs.KeyboardListener;
import main.Game;
import managers.EnemyManager;
import managers.StageManager;
import scenes.Settings;
import ui.TowerBar;

import static helpz.Constants.Enemy.OSTER;

public class Stage1 extends StageManager  {

    public Stage1(Game game, TowerBar towerBar, Settings settings) {
        super(game, towerBar, settings);
        MapLoader();

    }

    @Override
    protected void MapLoader() {
        level = Level1.getLevelData1();
    }


    @Override
    protected void resetGame() {
        super.resetGame();
        enemyManager.addEnemy(OSTER, 0 * 32, 14 * 32);
    }

}
