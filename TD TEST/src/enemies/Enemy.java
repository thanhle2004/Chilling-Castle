package enemies;

import helpz.Constants;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static helpz.Constants.Direction.*;

public class Enemy {

	private float x, y;
	private Rectangle bounds;
	private int health;
	private int maxHealth;
	private int ID;
	private int enemyType;
	private int lastDir;
	
	private int imgIndex = 0;
    private int animationSpeed = 5;
    private int animationCounter = 0;
	private BufferedImage[] images;

	public Enemy(float x, float y, int ID, int enemyType) {
		this.x = x;
		this.y = y;
		this.ID = ID;
		this.enemyType = enemyType;
		bounds = new Rectangle((int) x, (int) y, 32, 32);
		lastDir = -1;
		healthBarOfEach();

	}
	private void healthBarOfEach() {
		health = Constants.Enemy.Health(enemyType);
		maxHealth = health;
	}

	public void move(float speed, int dir) {
		lastDir = dir;
		switch (dir) {
		case LEFT:
			this.x -= speed;
			break;
		case UP:
			this.y -= speed;
			break;
		case RIGHT:
			this.x += speed;
			break;
		case DOWN:
			this.y += speed;
			break;
		}
	}

	public void setPos(int x, int y) {
		// Don't use this one for moving the enemy.
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getHealth() {
		return health;
	}

	public int getID() {
		return ID;
	}

	public int getEnemyType() {
		return enemyType;	
	}

	public int getLastDir() {
		return lastDir;
	}
	
    public int getImgIndex() {
        return imgIndex;
    }
    
    public void updateAnimation() {

        animationCounter++;
        if (animationCounter >= animationSpeed) {
            imgIndex = (imgIndex + 1) % 6; 
            animationCounter = 0;
        }
    }

	public void setImages(BufferedImage[] images) {
		this.images = images;
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
}
