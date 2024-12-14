package scenes;

import static main.GameStates.*;
import static main.GameStates.SetGameState;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.Game;
import managers.EnemyManager;
import managers.StageManager;
import objects.SoundEffect;
import ui.StageButton;
import ui.Button;
import ui.TowerBar;


public class Stages extends GameScene implements SceneMethods {

    private Button bMenu, selectedStage;
    private StageButton[] bStage = new StageButton[4];

    private BufferedImage background, menuButton, warningImage;
    private BufferedImage[] bStageImg;

    private long warningStartTime = 0;

    private TowerBar towerBar;

    private SoundEffect soundEffect;

    public Stages(Game game, TowerBar towerBar) {
        super(game);
        this.towerBar = towerBar;
        soundEffect = new SoundEffect();
        importImg();
        initButtons();

    }

    private void importImg() {
        bStageImg = new BufferedImage[4];

        try {
            menuButton = ImageIO.read(getClass().getResourceAsStream("/bMenu.png"));
            background = ImageIO.read(getClass().getResourceAsStream("/StagesBoard.png"));
            warningImage = ImageIO.read(getClass().getResourceAsStream("/equippedWarning.png"));

            for (int i = 0; i < 4; i++) {
                bStageImg[i] = ImageIO.read(getClass().getResource("/Stage" + (i + 1) + ".png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        bMenu = new Button("Menu", 530, 40, 65, 65, menuButton);

        for (int i = 0; i < 4; i++) {
            bStage[i] = new StageButton("Stage" + (i + 1), 110, 120 + i * 90, 420, 70, bStageImg[i], i);
        }

    }

    @Override
    public void render(Graphics g) {

        drawBackground(g);

        drawButtons(g);

        drawStages(g);

        towerBar.draw(g);

        drawWarning(g);


    }

    private void drawBackground(Graphics g) {
        g.drawImage(background, 0, 0, game);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
    }

    public void drawStages(Graphics g) {
        for (StageButton stage : bStage) {
            stage.draw(g);
        }
    }

    //Warning if not equip tower
    public void drawWarning(Graphics g) {
        if (warningImage != null && warningStartTime > 0) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - warningStartTime < 1500) {
                g.drawImage(warningImage, 170, 30, 300, 60, game);
            } else {

                warningStartTime = 0;
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
            soundEffect.playEffect(1);
        } else {
            for (int i = 0; i < 4; i++) {
                if (bStage[i].getBounds().contains(x, y)) {
                    for (int j = 0; j < 3; j++) {
                        if (towerBar.isEquippedTower() && StageManager.isUnlockStage(i)) {
                            switch (i) {
                                case 0:
                                    SetGameState(STAGE1);
                                    soundEffect.playEffect(1);
                                    game.getStage1().getEnemyManager().setPauseGame(false);
                                    break;
                                case 1:
                                    SetGameState(STAGE2);
                                    soundEffect.playEffect(1);
                                    game.getStage2().getEnemyManager().setPauseGame(false);

                                    break;
                                case 2:
                                    SetGameState(STAGE3);
                                    game.getStage3().getEnemyManager().setPauseGame(false);
                                    break;
                            }
                        } else {
                            soundEffect.playEffect(2);
                            warningStartTime = System.currentTimeMillis();
                        }
                    }

                }
            }
        }
    }


    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        for (int i = 0; i < 4; i++) {
            bStage[i].setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else {
            for (int i = 0; i < 4; i++) {
                if (bStage[i].getBounds().contains(x, y)) {
                    bStage[i].setMouseOver(true);
                }
            }
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else {
            for (int i = 0; i < 4; i++) {
                if (bStage[i].getBounds().contains(x, y)) {
                    bStage[i].setMousePressed(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    private void resetButtons() {
        bMenu.resetBooleans();
        for (int i = 0; i < 4; i++) {
            bStage[i].resetBooleans();
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
    }


    @Override
    public void mouseRightClicked(int x, int y) {

    }


}
