package towers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import scenes.Towers;
import ui.TowerButton;

public class TowerEquippedButton {

    private Towers towers;
    private BufferedImage img;
    private int posX, posY, width, height, cost, num;
    private double DMG, CD, RNG;

    private Rectangle bounds;
    private boolean mouseOver, mousePressed, mouseClicked;

    private TowerButton[] towersEquipped = new TowerButton[3];
    private TowerInfo[] towersEquipInfo = new TowerInfo[3];

    private int maxEquippedTowers = 3;

    public TowerEquippedButton(BufferedImage img, int posX, int posY, int width, int height, double DMG, double CD, double RNG, int cost, int num) {

        this.img = img;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.DMG = DMG;
        this.CD = CD;
        this.RNG = RNG;
        this.cost = cost;
        this.num = num;

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

    public int getTowerNum() {
        return num;
    }

    public BufferedImage getImg() {
        return img;
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
}
