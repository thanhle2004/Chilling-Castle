package stages;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Rectangle;

public class StageBoard {

    private BufferedImage img;
    private int x, y, width, height, id;
    private String text;
    private Rectangle bounds;
    private boolean mouseOver, mousePressed, mouseClicked;

    public StageBoard(String text, int x, int y, int width, int height, BufferedImage img, int id) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
        this.id = id;

        initBounds();
    }

    private void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        if(img != null) {
            g.drawImage(img, x, y, width, height, null);
            if (mouseOver) {

                g.drawImage(img, x - 1, y - 1, width + 3, height + 3, null);
            }
        } else {

            // Body
            drawBody(g);

            // Border
            drawBorder(g);

            // Text
            drawText(g);

        }
    }

    private void drawBorder(Graphics g) {

        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
        if (mousePressed) {
            g.drawRect(x + 1, y + 1, width - 2, height - 2);
            g.drawRect(x + 2, y + 2, width - 4, height - 4);
        }

    }

    private void drawBody(Graphics g) {
        if (mouseOver)
            g.drawImage(img, x - 1, y - 1, width + 3, height + 3, null);
        else
            g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);

    }

    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 2);

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

    public String getName() {
        // TODO Auto-generated method stub
        return text;
    }

    public int getId() {
        return id;
    }

}
