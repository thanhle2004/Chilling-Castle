package ui;

import scenes.Towers;
import towers.TowerEquippedButton;
import towers.TowerInfo;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static helpz.Constants.Tower.*;

public class TowerBar extends Bar{

    private BufferedImage towerFrame, fireT, iceT, lightningT, buffT;
    private BufferedImage fireBullet, iceBullet, lightningBullet;
    private TowerEquippedButton[] towerEquippedButtons = new TowerEquippedButton[3];
    private TowerEquippedButton towerSelected;
    private Towers towers;
    private boolean selected = true;

    public TowerBar(int x, int y, int width, int height, Towers towers) {
        super(x,y,width,height);
        this.towers = towers;
        importImg();
        initButtons();
    }

    private void importImg() {
        try {
            towerFrame = ImageIO.read(getClass().getResourceAsStream("/TowerFrame.png"));
            fireT = ImageIO.read(getClass().getResourceAsStream("/FireT.png"));
            iceT = ImageIO.read(getClass().getResourceAsStream("/IceT.png"));
            lightningT = ImageIO.read(getClass().getResourceAsStream("/LightningT.png"));
            buffT = ImageIO.read(getClass().getResourceAsStream("/BuffT.png"));

            fireBullet = ImageIO.read(getClass().getResourceAsStream("/FireBullet.png"));
            iceBullet = ImageIO.read(getClass().getResourceAsStream("/IceBullet.png"));
            lightningBullet = ImageIO.read(getClass().getResourceAsStream("/LightningBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        // Initialize tower buttons with empty state
        for (int i = 0; i < 3; i++) {
            towerEquippedButtons[i] = new TowerEquippedButton(towerFrame, 180 + i * 100, 530, 80, 80, 0, 0,0);
        }
    }

    public void equipTower(TowerInfo selectedTower, TowerButton bSelectedTower, int position) {
        if (position >= 0 && position < towerEquippedButtons.length) {
            towerEquippedButtons[position] = new TowerEquippedButton(bSelectedTower.getImage(), 180 + position * 100, 530, 80, 80,  selectedTower.getTowerCost(), selectedTower.getNumber(), selectedTower.getLevel());
        }
    }

    public void draw(Graphics g) {
        drawButtons(g);
    }

    public void removeTower(int slot) {
        if (slot >= 0 && slot < 3) {
            towerEquippedButtons[slot] = new TowerEquippedButton(towerFrame, 180 + slot * 100, 530, 80, 80, 0,  0, 0);
        }
    }

    private void drawButtons(Graphics g) {
        for (int i = 0; i < towerEquippedButtons.length; i++) {
            g.drawImage(towerFrame, 180 + i * 100, 530, 80, 80, null);
            towerEquippedButtons[i].draw(g);
        }
    }

    public BufferedImage getTowerFrame(int TowerTypes) {
        switch (TowerTypes) {
            case FIRE_TOWER:
                return fireT;
            case ICE_TOWER:
                return iceT;
            case LIGHT_TOWER:
                return lightningT;
            case BUFF_TOWER:
                return buffT;
        }
        return null;
    }

    public BufferedImage getTowerBullet(int TowerTypes) {
        switch (TowerTypes) {
            case FIRE_TOWER:
                return fireBullet;
            case ICE_TOWER:
                return iceBullet;
            case LIGHT_TOWER:
                return lightningBullet;
        }
        return null;
    }

    public void mouseClicked(int x, int y) {
        if(towerEquippedButtons[0].getBounds().contains(x, y) && towerEquippedButtons[0].getTowerTypes() != 0) {
            towerSelected = towerEquippedButtons[0];
        } else if(towerEquippedButtons[1].getBounds().contains(x, y) && towerEquippedButtons[1].getTowerTypes() != 0) {
            towerSelected = towerEquippedButtons[1];
        } else if(towerEquippedButtons[2].getBounds().contains(x, y) && towerEquippedButtons[2].getTowerTypes() != 0) {
            towerSelected = towerEquippedButtons[2];
        } else if(selected) {
            towerSelected = null;
        }
    }

    public void mouseRightClicked(int x, int y) {
        towerSelected = null;
    }

    public void mouseMoved(int x, int y) {

    }

    public void mousePressed(int x, int y) {

    }

    public void mouseReleased(int x, int y) {

    }

    public TowerEquippedButton getEquippedTower(int i) {
        switch (i) {
            case 0:
                return towerEquippedButtons[0];


            case 1:
                return towerEquippedButtons[1];


            case 2:
                return towerEquippedButtons[2];

            default:
                break;
        }

        return null;
    }

    public boolean isEquippedTower() {
        for(int i = 0; i < 3; i++) {
            if(towerEquippedButtons[i].getTowerTypes() != 0) {
                return true;
            }
        }
        return false;
    }

    public BufferedImage getTowerImg(int i) {
        switch (i) {
            case 0:
                return towerEquippedButtons[0].getImg();


            case 1:
                return towerEquippedButtons[1].getImg();


            case 2:
                return towerEquippedButtons[2].getImg();

            default:
                break;
        }

        return null;
    }

    public TowerEquippedButton getSelectedTower() {
        return towerSelected;
    }

    public void setTowerSelected(TowerEquippedButton selectedTower) {
        this.towerSelected = selectedTower;
    }

    public BufferedImage getSelectedTowerImg() {
        return towerSelected.getImg();
    }

    public int getSelectedTowerNum() {
        return towerSelected.getTowerTypes();
    }
    public void setSelectedTower(TowerEquippedButton tower) {
        this.towerSelected = tower;
    }
}
