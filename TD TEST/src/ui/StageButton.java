package ui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class StageButton extends Button {

    public StageButton(String text, int x, int y, int width, int height, BufferedImage img, int id) {
        super(text, x, y, width, height, img);
    }

    @Override
    public void drawText(Graphics g) {
        super.drawText(g);
    }

}
