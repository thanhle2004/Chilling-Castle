package managers;


import helpz.LoadPathImage;
import main.Game;
import main.GameStates;
import objects.SoundEffect;
import scenes.GameScene;
import scenes.SceneMethods;
import scenes.Settings;
import towers.TowerEquippedButton;
import ui.NotificationGameOver;
import ui.SettingBoardUI;
import ui.Button;
import ui.TowerBar;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static helpz.Constants.Tiles.GRASS_TILE;
import static helpz.Constants.Tiles.WATER_TILE;

public abstract class StageManager extends GameScene implements SceneMethods {

    protected int[][] level;
    protected int mouseX, mouseY;
    protected EnemyManager enemyManager;
    protected Settings settings;
    protected SettingBoardUI SettingBoardUI;
    protected TowerBar towerBar;
    protected SoundEffect soundEffect;
    protected BufferedImage optionButton;
    protected Button bOption;
    public  boolean isPaused = false;
    public boolean isLose;
    private NotificationGameOver notiBoard;
    private TowerManager towerManager;
    protected abstract NotificationGameOver createNotificationGameOver();
    protected abstract SettingBoardUI createSettingBoardUI();

    public StageManager(Game game, TowerBar towerBar, Settings settings, boolean isLose, boolean isPaused) {
        super(game);
        MapLoader();
        importImage();
        initButtons();
        MapLoader();
        this.towerBar = towerBar;
        this.settings = settings;
        this.isLose = isLose;
        enemyManager = new EnemyManager(this);
        soundEffect = new SoundEffect();
        towerManager = new TowerManager(this);
        SettingBoardUI = createSettingBoardUI();
        notiBoard = createNotificationGameOver();

    }

    private void initButtons() {
        bOption = new Button(null, 2, 2, 50, 50, optionButton);
    }

    private void importImage() {
        try {
            optionButton = ImageIO.read(getClass().getResourceAsStream("/bOption.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void MapLoader();

    public void update() {
        if(!isPaused && !isLose) {
            updateTick();
            enemyManager.update();
            loseGame();
            towerManager.update();
        }
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        enemyManager.draw(g);
        towerManager.draw(g);
        drawSelectedTower(g);
        if (GameStates.GetGameState() == GameStates.STAGE1) {
            game.getStage1().drawButtonPaused(g);
        } else if (GameStates.GetGameState() == GameStates.STAGE2) {
            game.getStage2().drawButtonPaused(g);
        }

        towerBar.draw(g);
        drawTestHouse(g);

    }

    private void drawSelectedTower(Graphics g) {
        if (towerBar.getSelectedTower() != null) {
            g.drawImage(towerBar.getSelectedTowerImg(), mouseX, mouseY, 32, 32, null);
        }
    }

    private void drawTestHouse(Graphics g) {
        BufferedImage testHouse = LoadPathImage.getTestHouse();
        g.drawImage(testHouse, 18*32, 224, null);
    }


    private void drawLevel(Graphics g) {
        for (int y = 0; y < level.length; y++) {
            for (int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                if (isAnimation(id)) {
                    g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                } else
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
        towerBar.draw(g);
    }

    public int getTileType(int x, int y) {
        int xCol = x / 32;
        int yCol = y / 32;

        if(xCol < 0 || xCol > 19)
            return WATER_TILE;
        if(yCol < 0 || yCol > 19)
            return WATER_TILE;


        int id = level[y / 32][x / 32];
        return game.getTileManager().getTile(id).getTileType();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(isLose) {
            notiBoard.mouseClicked(x, y);

        }
        if (bOption.getBounds().contains(x, y)) {
            isPaused = !isPaused;
            SettingBoardUI.setIsOpen(true);
            soundEffect.playEffect(1);
        }

        if(isPaused && x >= 30 && x <= 590 && y >= 15 && y <= 570) {
            soundEffect.playEffect(1);
            SettingBoardUI.mouseClicked(x, y);
        }

        if(y>=530) {
            towerBar.mouseClicked(GameStates.STAGE1,x, y);
        }else {

            if (towerBar.getSelectedTower() != null) {
                // Trying to place a tower
                if (isTileGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        towerManager.addTower(towerBar.getSelectedTower(), mouseX, mouseY);
                        towerBar.setTowerSelected(null);
                    }
                }
            } else {
                // Not trying to place a tower
                // Checking if a tower exists at x,y
                TowerEquippedButton t = getTowerAt(mouseX, mouseY);
            }
        }
    }

//    @Override
//    public void mouseMoved(int x, int y) {
//        if (y >= 530)
//            towerBar.mouseMoved(x, y);
//        else {
//            towerBar.mouseMoved(x, y);
//        }
//    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 530)
            towerBar.mouseMoved(x, y);
        else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 530) {
            towerBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        towerBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
        SettingBoardUI.mouseDragged(x,y);
    }

    public void drawButtonPaused(Graphics g) {
        if (isLose) {
            notiBoard.draw(g);
        }
        if (!isPaused) {
            bOption.draw(g);
        } else {
            if(SettingBoardUI.getIsOpen())
                SettingBoardUI.drawSettings(g);
        }
    }

    public void resetGame() {
        enemyManager.resetEnemies();
    }

    @Override
    public void keyPressed(int key) {}

    public void loseGame() {
        if(enemyManager.getLifeBar() <= 0) {
            isLose = true;
        }
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
    private TowerEquippedButton getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    private boolean isTileGrass(int x, int y) {
        int id = level[y / 32][x / 32];
        int tileType = game.getTileManager().getTile(id).getTileType();
        return tileType == GRASS_TILE;
    }

    @Override
    public void mouseRightClicked(int x, int y) {
        towerBar.mouseRightClicked(x, y);
    }
}
