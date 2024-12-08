package managers;


import helpz.LoadPathImage;
import main.Game;
import main.GameStates;
import objects.SoundEffect;
import scenes.GameScene;
import scenes.SceneMethods;
import scenes.Settings;
import towers.TowerEquippedButton;
import ui.*;
import ui.Button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
    public boolean isLose = false;
    public boolean isWin = false;
    private NotificationLoseGame notiLosing;
    private NotificationWinGame notiWin;
    private TowerManager towerManager;


    protected abstract NotificationLoseGame createNotificationGameOver();
    protected abstract SettingBoardUI createSettingBoardUI();
    protected abstract NotificationWinGame  createNotificationWinGame();
    private int coinTemp;

    public StageManager(Game game, TowerBar towerBar, Settings settings) {
        super(game);
        MapLoader();
        importImage();
        initButtons();

        this.towerBar = towerBar;
        this.settings = settings;
        enemyManager = new EnemyManager(this);
        soundEffect = new SoundEffect();
        towerManager = new TowerManager(this, towerBar);
        SettingBoardUI = createSettingBoardUI();
        notiLosing = createNotificationGameOver();
        notiWin = createNotificationWinGame();

    }

    private void initButtons() {
        bOption = new Button(null, 2, 2, 50, 50, optionButton);
    }

    private void importImage() {
        try {
            optionButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/bOption.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void MapLoader();

    public void update() {
            enemyManager.update();
            if(!isPaused) {
                updateTick();
                loseGame();
                winGame();
                towerManager.update();
            }
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        enemyManager.draw(g);
        drawSelectedTower(g);
        towerManager.draw(g);
        drawDigitCoin(g);
        drawTestHouse(g);
        if (GameStates.GetGameState() == GameStates.STAGE1) {
            game.getStage1().drawButtonPaused(g);
        } else if (GameStates.GetGameState() == GameStates.STAGE2) {
            game.getStage2().drawButtonPaused(g);
        } else if (GameStates.GetGameState() == GameStates.STAGE3) {
            game.getStage3().drawButtonPaused(g);
        }
    }

    private void drawSelectedTower(Graphics g) {
        if (towerBar.getSelectedTower() != null && towerBar.getSelectedTower().getCost() <= getCoinValue()) {
            g.drawImage(towerBar.getTowerFrame(towerBar.getSelectedTowerNum()), mouseX, mouseY, 32, 32, null);
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
            notiLosing.mouseClicked(x, y);
        }
        if (isWin) {
            notiWin.mouseClicked(x,y);
        }

        if (bOption.getBounds().contains(x, y) && !SettingBoardUI.getIsOpen() && !SettingBoardUI.getOpenConfirmDialog()) {
            isPaused = !isPaused;
            SettingBoardUI.setIsOpen(true);
            enemyManager.setPauseGame(true);
            soundEffect.playEffect(1);
        }

        if(isPaused && x >= 30 && x <= 590 && y >= 15 && y <= 570) {
            soundEffect.playEffect(1);
            SettingBoardUI.mouseClicked(x, y);
        }

        if (SettingBoardUI.getOpenConfirmDialog())  {
            SettingBoardUI.getConfirmDialog().mouseClicked(x, y);
        }


        if (!isPaused && !isLose) {
            if(y>=530) {
                towerBar.mouseClicked(x, y);
            }else {
                if (towerBar.getSelectedTower() != null) {
                    if (towerBar.getSelectedTower().getCost() <= getCoinValue()) {
                        // Trying to place a tower
                        if (isTileGrass(mouseX, mouseY)) {
                            if (getTowerAt(mouseX, mouseY) == null) {
                                towerManager.addTower(towerBar.getSelectedTower(), mouseX, mouseY);
                                towerBar.setTowerSelected(null);
                            }
                        }
                    } else {
                        towerBar.setTowerSelected(null);
                    }
                } else {
                    TowerEquippedButton t = getTowerAt(mouseX, mouseY);
                }
            }
            towerManager.mouseClicked(x, y);
        }


    }


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
            notiLosing.draw(g);
        }
        if (isWin) {
            notiWin.draw(g);
        }

        if (!isPaused) {
            bOption.draw(g);
        } else {
            if(SettingBoardUI.getIsOpen()) {
                SettingBoardUI.drawSettings(g);
            }
        }

        if(SettingBoardUI.getOpenConfirmDialog()) {
            SettingBoardUI.getConfirmDialog().draw(g);
        }

    }

    public void resetGame() {
        enemyManager.resetEnemies();
        towerManager.clearTowers();
    }

    @Override
    public void keyPressed(int key) {}

    public void loseGame() {
        if(enemyManager.getLifeBar() <= 0) {
            isLose = true;
            isPaused = true;
            enemyManager.setPauseGame(true);
        }
    }

    public void winGame() {
        if (enemyManager.getTotalEnemies() == 0) {
            isWin = true;
            isPaused = true;
            enemyManager.setPauseGame(true);
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



    public int getCoinValue() {
        return enemyManager.getCoin();
    }

    public void setCoin(int coin) {
        enemyManager.setCoin(coin);
    }


    private void drawDigitCoin(Graphics g) {
        coinTemp = getCoinValue();
        int coin = coinTemp;
        int count = 0;
        int x = 0;

        // Count the digits
        while (coinTemp != 0) {
            coinTemp /= 10;
            count++;
        }

        if (coin == 0) {
            drawNumCoin(g, 0, x);  // Draw 0 immediately
            return;
        }

        int divisor = (int) Math.pow(10, count - 1);


        while (divisor > 0) {
            int digit = coin / divisor;
            coin %= divisor;
            divisor /= 10;
            x += 24;
            drawNumCoin(g, digit, x);
        }

    }

    private void drawNumCoin(Graphics g, int digit, int x)  {
        BufferedImage img = null;
        BufferedImage dollarImg = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/"+digit + ".png")));
            dollarImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/$.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(dollarImg, 40, 550,24,24, null);
        g.drawImage(img,50 + x,550, 24, 24, null);
    }

    public Game game() {
        return game;
    }


}
