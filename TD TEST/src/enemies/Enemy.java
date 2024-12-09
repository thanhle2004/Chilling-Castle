package enemies;

import helpz.Constants;
import towers.TowerEquippedButton;

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
	private boolean dead;
	private int coinEnemy;
	private Rectangle hitbox;

	public Enemy(float x, float y, int ID, int enemyType) {
		this.x = x;
		this.y = y;
		this.ID = ID;
		this.enemyType = enemyType;
		dead = false;
		bounds = new Rectangle((int) x, (int) y, 32, 32);
		lastDir = -1;
		this.hitbox = new Rectangle((int) x, (int) y, 32, 32);
		healthBarOfEach();
		CoinEachEnemies();

	}

	private void CoinEachEnemies() {
		coinEnemy = Constants.Enemy.CoinReward(enemyType);
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

	public int getEnemyType() {
		return enemyType;	
	}
	public void setLastDir(int dir) {
		lastDir = dir;
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

    public boolean dead() {
		return dead;
    }
	public void setDead(boolean dead) {
		this.dead = dead;
	}


	public int getCoin() {
		return coinEnemy;
	}

	public void takeDamage( TowerEquippedButton tower) {
		health =(int) (health - tower.getCurrentDmg());
		if (health <= 0) {
			dead = true;
		}
	}

	public void updateHitbox(int posX, int posY) {
		hitbox.setLocation((int) posX, (int) posY);
	}

	public Rectangle getHitbox() {
		return hitbox;
	}
}
