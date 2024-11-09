package ui;

import scenes.Towers;
import towers.TowerEquippedButton;
import towers.TowerInfo;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TowerBar extends Bar{

    private BufferedImage towerFrame;
    private TowerEquippedButton[] towerEquippedButtons = new TowerEquippedButton[3];
    private Towers towers;

    public TowerBar(int x, int y, int width, int height, Towers towers) {
        super(x,y,width,height);
        this.towers = towers;
        importImg();
        initButtons();
    }

    private void importImg() {
        try {
            towerFrame = ImageIO.read(getClass().getResourceAsStream("/TowerFrame.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        // Initialize tower buttons with empty state
        for (int i = 0; i < 3; i++) {
            towerEquippedButtons[i] = new TowerEquippedButton(towerFrame, 180 + i * 100, 530, 80, 80, 0, 0, 0, 0, 0);
        }
    }

    public void equipTower(TowerInfo selectedTower, TowerBoard bSelectedTower, int position) {
        if (position >= 0 && position < towerEquippedButtons.length) {
            towerEquippedButtons[position] = new TowerEquippedButton(bSelectedTower.getImage(), 180 + position * 100, 530, 80, 80, selectedTower.getTowerDamage(), selectedTower.getTowerCooldown(), selectedTower.getTowerRange(), selectedTower.getTowerCost(), selectedTower.getNumber());
        }
    }

    public void draw(Graphics g) {
        drawButtons(g);
    }

    public void removeTower(int slot) {
        if (slot >= 0 && slot < 3) {
            towerEquippedButtons[slot] = new TowerEquippedButton(towerFrame, 180 + slot * 100, 530, 80, 80, 0, 0, 0, 0, 0);
        }
    }

    private void drawButtons(Graphics g) {
        for (int i = 0; i < towerEquippedButtons.length; i++) {
            g.drawImage(towerFrame, 180 + i * 100, 530, 80, 80, null);
            towerEquippedButtons[i].draw(g);
        }
    }

    public void mouseClicked(int x, int y) {

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
            if(towerEquippedButtons[i].getTowerNum() != 0) {
                return true;
            }
        }
        return false;
    }
}
