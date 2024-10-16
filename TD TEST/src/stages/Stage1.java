package stages;

import static helpz.Constants.Tiles.WATER_TILE;
import static helpz.Constants.Enemy.OSTER;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Map.Level1;
import enemies.OSTER;
import main.Game;
import managers.EnemyManager;
import scenes.GameScene;
import scenes.SceneMethods;
import scenes.Settings;
import towers.BottomBar;
import ui.ActionBar;
import objects.SoundEffect;
import ui.MyButton;

import javax.imageio.ImageIO;

public class Stage1 extends GameScene implements SceneMethods {

    private int[][] lv1;
//    private ActionBar bottomBar;
    private int mouseX, mouseY;
    private EnemyManager enemyManager;
    private Settings settings;
    private PauseSetting pauseSetting;
    private BottomBar bottomBar;
    private SoundEffect soundEffect;
    private BufferedImage optionButton;
    private MyButton bOption;
    private boolean isPaused = false, isContinue = false;

    public Stage1(Game game, BottomBar bottomBar, Settings settings) {
        super(game);
        MapLoader();
        importImage();
        initButtons();
        this.bottomBar = bottomBar;
        this.settings = settings;

        enemyManager = new EnemyManager(this);
        soundEffect = new SoundEffect();

        pauseSetting = new PauseSetting(37, 15, 560, 555, settings);

    }

    public void initButtons() {
        bOption = new MyButton(null, 2, 2, 50, 50, optionButton);
    }

    private void importImage() {
        try {
            optionButton = ImageIO.read(getClass().getResourceAsStream("/bOption.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void MapLoader() {
        lv1 = Level1.getLevelData1();
    }

    public void setLevel1(int[][] lvl) {
        this.lv1 = lv1;
    }

    public void update() {
        updateTick();
        enemyManager.update();
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        bottomBar.draw(g);
        enemyManager.draw(g);
    }

    private void drawLevel(Graphics g) {

        for (int y = 0; y < lv1.length; y++) {
            for (int x = 0; x < lv1[y].length; x++) {
                int id = lv1[y][x];
                if (isAnimation(id)) {
                    g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                } else
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
        bottomBar.draw(g);
    }

    public int getTileType(int x, int y) {
        int xCol = x / 32;
        int yCol = y / 32;

        if(xCol < 0 || xCol > 19)
            return WATER_TILE;
        if(yCol < 0 || yCol > 19)
            return WATER_TILE;


        int id = lv1[y / 32][x / 32];
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
            pauseSetting.mouseClicked(x, y);

        }

        if (isPaused && pauseSetting.bContinue.getBounds().contains(x, y)) {
            isPaused = false;
            soundEffect.playEffect(1);
        }

        if (isPaused && pauseSetting.bReplay.getBounds().contains(x, y)) {
            //reset game
        }

        if(y>=530) {
            bottomBar.mouseClicked(x, y);
        } else
            enemyManager.addEnemy(OSTER, x, y);
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 530)
            bottomBar.mouseMoved(x, y);
        else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 530) {
            bottomBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        bottomBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void drawButtonPaused(Graphics g) {
        if (!isPaused) {
            bOption.draw(g);
        } else {
            pauseSetting.drawSettings(g);
        }
    }


}