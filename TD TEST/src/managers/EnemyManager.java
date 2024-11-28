package managers;

import java.awt.*;
																							import java.awt.image.BufferedImage;
																							import java.util.ArrayList;

																							import enemies.DUDE;
																							import enemies.Enemy;
																							import enemies.OSTER;
																							import enemies.PINKY;
																							import enemies.SLIME;
import events.Wave;
import helpz.LoadPathImage;
																							import main.GameStates;
																							import scenes.GameScene;
import stages.Stage2;
																							import stages.Stage1;

																							import static helpz.Constants.Enemy.*;
public class EnemyManager {

BufferedImage[] enemyImgs;
private ArrayList<Enemy> enemies = new ArrayList<>();
private MoveManager moveManager;
private int xTarget, yTarget;
private Wave wave;
private ArrayList<long[]> spawnPoints = new ArrayList<>(); //contains spawnPoint and type enemies
private ArrayList<Long> initSpawnAmount =  new ArrayList<>();
private float lifeBar;
private int tempLife;
public EnemyManager(GameScene stage) {
	wave = new Wave(this);
	enemyImgs = new BufferedImage[6];
	lifeBar = 550;
	tempLife = (int) lifeBar;
	if (stage instanceof Stage1) {
		moveManager = new MoveManager((Stage1) stage);
		spawnPoints.add(new long[]{1, 4, DUDE, 15, 1000, 1000, 2000});
		spawnPoints.add(new long[]{1, 14, OSTER, 5, 2000, 2000, 2000});
		xTarget = 17 * 32;
		yTarget = 9 * 32;
	}
	if (stage instanceof Stage2) {
		moveManager = new MoveManager((Stage2) stage);
		spawnPoints.add(new long[]{0, 10, SLIME, 12,5000,5000,2000});
		xTarget = 19 * 32;
		yTarget = 1 * 32;
	}
}
public void update() {
	if(GameStates.GetGameState() == GameStates.STAGE1 || GameStates.GetGameState() == GameStates.STAGE2){
		spawningInterval();
	}

	ArrayList <Integer> tempLife = new ArrayList<>();
	ArrayList<Enemy> toRemove = new ArrayList<>();
	for (Enemy e : enemies) {
		if (Math.abs(e.getX() - xTarget) < 1 && Math.abs(e.getY() - yTarget) < 1) {
			substractLifeBar(e);
			tempLife.add((int) lifeBar);
			toRemove.add(e);
			lifeBar = tempLife.get(0);
		} else {
			e.updateAnimation();
			moveManager.isNextTileRoad(e, xTarget, yTarget);
		}
	}


	enemies.removeAll(toRemove);
}

	private void spawningInterval() {
		long currentTime = System.currentTimeMillis();

		for (long[] spawnPoint : spawnPoints) {
			long nextSpawnTime = spawnPoint[5];
			long interval = spawnPoint[6];
			long spawnedEnemy = spawnPoint[3];
			initSpawnAmount.add(spawnPoint[3]);
			if (currentTime >= nextSpawnTime && spawnedEnemy > 0) {
				addEnemy((int)spawnPoint[2], (int)spawnPoint[0] * 32, (int)spawnPoint[1] * 32);
				spawnPoint[5] = currentTime + interval;
				spawnPoint[3] = spawnedEnemy - 1;
			}
		}

	}

	public void addEnemy(int EnemyTypes, int PointSpawnX, int PointSpawnY) {
	BufferedImage atlas = null;
	Enemy enemy = null;
	switch (EnemyTypes) {
		case SLIME:
			atlas = LoadPathImage.getSlimePath();
			enemy = new SLIME(PointSpawnX, PointSpawnY, 0);
			break;
		case DUDE:
			atlas = LoadPathImage.getDudePath();
			enemy = new DUDE(PointSpawnX, PointSpawnY, 0);
			break;
		case OSTER:
			atlas = LoadPathImage.getOSTERPath();
			enemy = new OSTER(PointSpawnX, PointSpawnY, 0);
			break;
		case PINKY:
			atlas = LoadPathImage.getPinkPath();
			enemy = new PINKY(PointSpawnX, PointSpawnY, 0);
			break;
	}
	if (enemy != null && atlas != null) {
		BufferedImage[] imgs = new BufferedImage[6];
		for (int i = 0; i < 6; i++) {
			imgs[i] = atlas.getSubimage(i * 32, 0, 32, 32);
		}
		enemy.setImages(imgs);
		enemies.add(enemy);

	}
}
public void draw(Graphics g) {
	for (Enemy e : enemies) {
		drawEnemy(e, g);
		drawHealthBar(e, g);
	}
	g.setColor(Color.RED);
	g.fillRect(60, 2, (int) Math.abs(lifeBar), 20);
	g.setColor(Color.WHITE);
	g.drawString("Life Bar: " + lifeBar, 60, 18);
}
private void drawEnemy(Enemy e, Graphics g) {
	if (e.getImages() != null) {
		int imgIndex = e.getImgIndex();
		g.drawImage(e.getImages()[imgIndex], (int) e.getX(), (int) e.getY(), null);
	}
}
public void resetEnemies() {
	enemies.clear();
	for (int i = 0; i < spawnPoints.size(); i++) {
		spawnPoints.get(i)[3] = initSpawnAmount.get(i);
		spawnPoints.get(i)[5] = spawnPoints.get(i)[4];
	}
	lifeBar = 550;
}


public void drawHealthBar(Enemy enemy, Graphics g) {
	g.setColor(Color.RED);
	g.fillRect((int) enemy.getX(), (int) enemy.getY() - 2, ratioHealth(enemy), 2 );
}
public int ratioHealth(Enemy enemy) {
	float ratio = enemy.getHealth()/ enemy.getMaxHealth() * 100;
	float healtBar = ratio * 32 / 100;
	return (int) healtBar;
}
private void substractLifeBar(Enemy e) {
		lifeBar -= e.getHealth();
		if (lifeBar < 0) {
			lifeBar = 0;
		}

}
public float getLifeBar() {
		return lifeBar;
	}
}