package ui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class TowerButton extends Button{

    public TowerButton(String text, int x, int y, int width, int height, BufferedImage img) {
        super(text,x,y,width,height,img);

    }

    private void drawBody(Graphics g) {
        if (mouseOver || mouseClicked)
            g.drawImage(img, x - 1, y - 1, width + 3, height + 3, null);
        else
            g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);

    }
    @Override
    public void drawText(Graphics g) {
        super.drawText(g);
    }
}