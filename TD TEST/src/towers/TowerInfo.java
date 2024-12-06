package towers;

import helpz.Constants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class TowerInfo {

    private String towerName;
    private double DMG, CD, RNG;
    private int  posX, posY, cost;
    private BufferedImage img;
    private int TowerTypes;
    public TowerInfo(BufferedImage img, String towerName, int posX, int posY, int cost, int TowerType) {
        this.TowerTypes = TowerType;
        this.img = img;
        this.towerName = towerName;
        this.posX = posX;
        this.posY = posY;
        this.DMG = Constants.Tower.Dmg(TowerTypes);
        this.CD = Constants.Tower.CD(TowerTypes);
        this.RNG = Constants.Tower.Range(TowerTypes);
        this.cost = cost;


    }

    public void drawInfo(Graphics g) {
        if(posX <= 400) {
            g.drawImage(img, posX + 67, posY + 3, 150, 100, null);
        } else {
            g.drawImage(img, posX - 140, posY + 3, 150, 100, null);
        }

    }

    public double getTowerDamage() {
        return DMG;
    }

    public double getTowerCooldown() {
        return CD;
    }

    public double getTowerRange() {
        return RNG;
    }

    public int getNumber() {
        return TowerTypes;
    }

    public int getTowerCost() {
        return cost;
    }

}