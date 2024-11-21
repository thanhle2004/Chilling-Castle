package events;


import helpz.LoadPathImage;

import java.awt.image.BufferedImage;

public class LifeSystem {
    private int life;
    private BufferedImage image;
    public LifeSystem() {
        this.life = 3;
        importHeart();
    }
    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }

    public void importHeart() {
        image = LoadPathImage.getLifeHeart().getSubimage(0,0, 16,16);
    }

    public BufferedImage getImage() {
        return image;
    }
}
