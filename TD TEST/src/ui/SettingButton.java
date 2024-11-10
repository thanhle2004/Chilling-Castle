package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SettingButton extends Button {

    public SettingButton(String text, int x, int y, int width, int height, BufferedImage img) {
        super(text,x,y,width,height,img);
    }

    @Override
    public void drawText(Graphics g) {
        if(text != null){
            super.drawText(g);
        }
    }
}
