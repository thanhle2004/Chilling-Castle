package towers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TowerInfo {

    private String towerName;
    private double DMG, CD, RNG;
    private int id, posX, posY, cost;
    private BufferedImage img;

    public TowerInfo(BufferedImage img, String towerName, int posX, int posY, double DMG, double CD, double RNG, int cost, int id) {

        this.img = img;
        this.towerName = towerName;
        this.posX = posX;
        this.posY = posY;
        this.DMG = DMG;
        this.CD = CD;
        this.RNG = RNG;
        this.cost = cost;
        this.id = id;

    }



    public void drawInfo(Graphics g) {
        if(posX <= 400) {
            g.drawImage(img, posX + 67, posY + 3, 150, 100, null);
        } else {
            g.drawImage(img, posX - 140, posY + 3, 150, 100, null);
        }

    }

    public String getTowerName() {
        return towerName;
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

    public int getId() {
        return id;
    }

    public int getTowerCost() {
        return cost;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

}