package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import objects.SoundEffect;
import ui.TowerBar;
import ui.TowerButton;
import towers.TowerInfo;

import static helpz.Constants.Tower.*;
import static main.GameStates.*;

public class Towers extends GameScene implements SceneMethods {

    private TowerButton bMenu, bTowerSelected;
    private TowerButton[] bTower = new TowerButton[5];

    private BufferedImage[] towerImages, towerInfo;
    private BufferedImage background, menuButton, towerFrame;

    private int towerNumber, selectedTowerNumber;

    private TowerInfo[] tower = new TowerInfo[5];
    private TowerInfo towerSeclectedInfo;

    private TowerBar towerBar;

    private SoundEffect soundEffect;

    public Towers(Game game) {
        super(game);
        importImg();
        initButtons();
        showTowerInfo();
        createTowerSelected();

        soundEffect = new SoundEffect();

        towerBar = new TowerBar(0, 520, 640, 140, this);

    }

    private void createTowerSelected() {
        bTowerSelected = new TowerButton(null, 0, 0, 0, 0, towerFrame);
        towerSeclectedInfo = new TowerInfo(null, null, 0, 0, 0, -1, 0);
    }

    private void importImg() {
        towerImages = new BufferedImage[5];
        towerInfo = new BufferedImage[5];

        try {

            background = ImageIO.read(getClass().getResourceAsStream("/TowersBoard.png"));
            menuButton = ImageIO.read(getClass().getResourceAsStream("/bMenu.png"));
            towerFrame = ImageIO.read(getClass().getResourceAsStream("/TowerFrame.png"));

            for (int i = 0; i < towerImages.length; i++) {
                towerImages[i] = ImageIO.read(getClass().getResourceAsStream("/tower" + (i + 1) + ".png"));
                towerInfo[i] = ImageIO.read(getClass().getResourceAsStream("/InfoTower" + (i + 1) + ".png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initButtons() {
        bMenu = new TowerButton("Menu", 530, 40, 65, 65, menuButton);

        for (int i = 0; i < 5; i++) {
            bTower[i] = new TowerButton("Tower" + (i + 1), 90 + i * 95, 120, 80, 80, towerImages[i]);
        }


    }

    private void showTowerInfo() {

        tower[0] = new TowerInfo(towerInfo[0], "Fire Tower", bTower[0].getX(), bTower[0].getY(),  25, FIRE_TOWER,1);
        tower[1] = new TowerInfo(towerInfo[1], "Ice Tower", bTower[1].getX(), bTower[1].getY(), 35, ICE_TOWER,1);
        tower[2] = new TowerInfo(towerInfo[2], "Lighting Tower", bTower[2].getX(), bTower[2].getY(), 45, LIGHT_TOWER,1);
        tower[3] = new TowerInfo(towerInfo[3], "Support Tower", bTower[3].getX(), bTower[3].getY(),  50, BUFF_TOWER,1);
        tower[4] = new TowerInfo(towerInfo[4], "Cooming Soon", bTower[4].getX(), bTower[4].getY(),  0, SUMMON_TOWER,1);
    }

    @Override
    public void render(Graphics g) {

        drawBackground(g);

        drawButtons(g);

        drawTowerInfo(g);

        towerBar.draw(g);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(background, 0, 0, game);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);

        for (int i = 0; i < bTower.length; i++) {
            bTower[i].draw(g);
        }
    }

    private void drawTowerInfo(Graphics g) {
        if (towerNumber != 0) {
            switch (towerNumber) {
                case 1:
                    tower[0].drawInfo(g);

                    break;

                case 2:
                    tower[1].drawInfo(g);

                    break;

                case 3:
                    tower[2].drawInfo(g);

                    break;

                case 4:
                    tower[3].drawInfo(g);

                    break;

                case 5:
                    tower[4].drawInfo(g);

                    break;

                default:
                    break;
            }

        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        selectedTowerNumber = 0;
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
            soundEffect.playEffect(1);
        } else {
            for (int i = 0; i < 4; i++) {
                if (bTower[i].getBounds().contains(x, y)) {
                    bTower[i].setMouseClicked(true);
                    selectedTowerNumber = i + 1;
                    towerSeclectedInfo = tower[i];
                    bTowerSelected = bTower[i];
                    soundEffect.playEffect(1);

                    //Check if tower equipped
                    if (isTowerAlreadyEquipped(selectedTowerNumber)) {
                        System.out.println("Tower is already equipped.");
                        break;
                    }

                    // Check if an empty slot is available
                    int slot = findEmptySlot();
                    if (slot != -1) {
                        towerBar.equipTower(towerSeclectedInfo, bTowerSelected, slot);
                    }
                    break;
                }
            }

            //Remove tower
            for (int i = 0; i < 3; i++) {
                if (towerBar.getEquippedTower(i).getBounds().contains(x, y)) {

                    soundEffect.playEffect(1);
                    towerBar.removeTower(i);

                    break;
                }
            }
        }
    }

    private boolean isTowerAlreadyEquipped(int towerNum) {
        for (int i = 0; i < 3; i++) {
            if (towerBar.getEquippedTower(i).getTowerTypes() == towerNum) {
                return true;  //already equipped
            }
        }
        return false;
    }

    private int findEmptySlot() {

        for (int i = 0; i < 3; i++) {
            if (towerBar.getEquippedTower(i).getTowerTypes() == 0) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        for(int i = 0; i < 5; i++) {
            bTower[i].setMouseOver(false);
        }
        towerNumber = 0;

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }else {

            for (int i = 0; i < 5; i++) {
                if (bTower[i].getBounds().contains(x, y)) {
                    bTower[i].setMouseOver(true);
                    towerNumber = i + 1;
                    break;
                }
            }
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    private void resetButtons() {
        bMenu.resetBooleans();
    }

//    //Getter
//    public TowerInfo getTowerSeclectedInfo() {
//        return towerSeclectedInfo;
//    }
//
//    public TowerButton getBTowerSeclected() {
//        return bTowerSelected;
//    }

    public TowerBar getBottomBar() {
        return towerBar;
    }

    @Override
    public void mouseDragged(int x, int y) {}

    @Override
    public void mouseRightClicked(int x, int y) {

    }

}
