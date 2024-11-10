package managers;

import main.Game;
import objects.SoundEffect;
import scenes.GameScene;
import scenes.SceneMethods;
import scenes.Settings;
import ui.SettingBoardUI;
import ui.Button;
import ui.TowerBar;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    public static boolean isPaused = false;

    public StageManager(Game game, TowerBar towerBar, Settings settings) {
        super(game);
        MapLoader();
        importImage();
        initButtons();
        MapLoader();

        this.towerBar = towerBar;
        this.settings = settings;
        enemyManager = new EnemyManager(this);
        soundEffect = new SoundEffect();
        SettingBoardUI = new SettingBoardUI(37, 15, 560, 555, settings);
        
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
        updateTick();
        enemyManager.update();
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        towerBar.draw(g);
        enemyManager.draw(g);
        drawButtonPaused(g);

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
        // return tileManager.getTile(id).getTileType()
        // return Tile.get(id).getTileType()
        // return tile[x].getTileType()
        // return tileType.
    }

    @Override
    public void mouseClicked(int x, int y) {

        if (bOption.getBounds().contains(x, y)) {
            isPaused = !isPaused;
            soundEffect.playEffect(1);
        }

        if(isPaused && x >= 30 && x <= 590 && y >= 15 && y <= 570) {
            soundEffect.playEffect(1);
            SettingBoardUI.mouseClicked(x, y);
        }

        if (isPaused && SettingBoardUI.bContinue.getBounds().contains(x, y)) {
            isPaused = false;
            soundEffect.playEffect(1);
        }

        if (isPaused && SettingBoardUI.bReplay.getBounds().contains(x, y)) {
            soundEffect.playEffect(1);
            resetGame();
        }

        if(y>=530) {
            towerBar.mouseClicked(x, y);
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

    private void drawButtonPaused(Graphics g) {
        if (!isPaused) {
            bOption.draw(g);
        } else {
            SettingBoardUI.drawSettings(g);
        }
    }


    protected void resetGame() {
        enemyManager.resetEnemies();
    }

    @Override
    public void keyPressed(int key) {}



}
