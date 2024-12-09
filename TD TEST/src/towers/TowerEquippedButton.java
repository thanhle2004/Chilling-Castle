package towers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Map.Level1;
import helpz.Constants;
import scenes.Towers;

public class TowerEquippedButton {

    private Towers towers;
    private BufferedImage img;
    private int posX, posY, width, height, cost, TowerTypes;
    private double DMG, CD, RNG;
    private long lastShotTime;

    private Rectangle bounds;
    private boolean mouseOver, mousePressed, mouseClicked;
    private double baseDmg, currentDmg;
    private int mod;
    private int indexChange;
    private int level;
    public TowerEquippedButton(BufferedImage img, int posX, int posY, int width, int height, int cost, int TowerTypes, int level) {

        this.img = img;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.DMG = Constants.Tower.DmgLv1(TowerTypes);
        this.CD = Constants.Tower.CDLv1(TowerTypes);
        this.RNG = Constants.Tower.RangeLv1(TowerTypes);
        this.cost = Constants.Tower.CoinToBuy(TowerTypes);
        this.TowerTypes = TowerTypes;
        this.lastShotTime = 0;
        this.mod = 1;
        this.indexChange = 1;
        this.level = level;
        initBounds();

    }

    public void draw(Graphics g) {
        if (img != null) {
            g.drawImage(img, posX, posY, width, height, null);
            if (mouseOver) {
                g.drawImage(img, posX - 1, posY - 1, width + 3, height + 3, null);
            }

        } else {
            // Body
            drawBody(g);
            // Border
            drawBorder(g);
        }
    }

    private void initBounds() {
        this.bounds = new Rectangle(posX, posY, width, height);
    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(posX, posY, width, height);
    }

    private void drawBody(Graphics g) {
        if (mouseOver || mouseClicked)
            g.drawImage(img, posX - 1, posY - 1, width + 3, height + 3, null);
        else
            g.setColor(Color.WHITE);
        g.fillRect(posX, posY, width, height);

    }

    public void resetBooleans() {
        this.mouseOver = false;
        this.mousePressed = false;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMouseClicked(boolean mouseClicked) {
        this.mouseClicked = mouseClicked;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getTowerTypes() {
        return TowerTypes;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public double getCD() {
        return CD;
    }

    public double getDMG() {
        return DMG;
    }

    public double getRNG() {
        return RNG;
    }

    public int getCost() {
        return cost;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean canShoot() {
        return (System.currentTimeMillis() - lastShotTime) >= CD * 1000;
    }

    public void resetCooldown() {
        this.lastShotTime = System.currentTimeMillis();
    }


    public double getBaseDamage() {
        return baseDmg;
    }

    public void setDamage(double newDamage) {
        this.currentDmg = newDamage;
    }

    public void setBaseDamage(double baseDamage) {
        this.baseDmg = baseDamage;
        this.currentDmg  = baseDamage;
    }

    public double getCurrentDmg() {
        return currentDmg;
    }

    public void printTowerInfo() {
        System.out.println("Tower Type: " + TowerTypes);
        System.out.println("Base Damage: " + baseDmg);
        System.out.println("Current Damage: " + currentDmg);
    }
    public int GetmodState() {
        return mod;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public void updateStatPerLv() {
        switch (level) {
            case 1:
                this.DMG = Constants.Tower.DmgLv1(TowerTypes);
                this.CD = Constants.Tower.CDLv1(TowerTypes);
                this.RNG = Constants.Tower.RangeLv1(TowerTypes);
                break;
            case 2:
                this.DMG = Constants.Tower.DmgLv2(TowerTypes);
                this.CD = Constants.Tower.CdLv2(TowerTypes);
                this.RNG = Constants.Tower.RangeLv2(TowerTypes);
                break;
            case 3:
                this.DMG = Constants.Tower.DmgLv3(TowerTypes);
                this.CD = Constants.Tower.CdLv3(TowerTypes);
                this.RNG = Constants.Tower.RangeLv3(TowerTypes);
                break;
            default:
                break;
        }
    }

    public int getIndexChange() {
        return indexChange;
    }

    public void setIndexChange(int indexChange) {
        this.indexChange = indexChange;
    }





}
