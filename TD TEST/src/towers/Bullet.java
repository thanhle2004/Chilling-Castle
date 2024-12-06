package towers;

import enemies.Enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

import static helpz.Constants.Tower.ICE_TOWER;

public class Bullet {
    private double x, y; // Vị trí hiện tại của viên đạn
    private double speed; // Tốc độ của viên đạn
    private double directionX, directionY; // Vector hướng di chuyển
    private BufferedImage image;
    private Enemy target; // Mục tiêu của viên đạn
    private boolean destroyed = false;
    private Rectangle hitbox;
    private TowerEquippedButton tower;
    public Bullet(BufferedImage image, double startX, double startY, double speed, Enemy target, TowerEquippedButton tower) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.target = target;
        this.image = image;
        // Tính toán vector hướng
        double deltaX = (target.getX() + 5) - startX;
        double deltaY = (target.getY() + 5) - startY;
        double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        this.directionX = deltaX / length;
        this.directionY = deltaY / length;
        this.hitbox = new Rectangle((int) (x + 10), (int) y + 8, 10, 8);
        this.tower = tower;
    }

    public void update() {
        x += directionX * speed;
        y += directionY * speed;


        hitbox.setLocation((int) x + 10, (int) y + 8);

        if (hitbox.intersects(target.getHitbox())) {
            target.takeDamage( tower); //health--
            destroy();
        }
//        if (Math.hypot(x - (target.getX() + 5), y - (target.getY() + 5)) < 7) {
//            target.takeDamage(TowerTypes); //health--
//            destroy();
//        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, 32, 32, null);

    }

    public void destroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}

