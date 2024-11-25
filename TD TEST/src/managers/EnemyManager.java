package managers;

import java.awt.*;
																							import java.awt.image.BufferedImage;
																							import java.util.ArrayList;

																							import enemies.DUDE;
																							import enemies.Enemy;
																							import enemies.OSTER;
																							import enemies.PINKY;
																							import enemies.SLIME;
																							import events.LifeSystem;
																							import events.Wave;
																							import helpz.Constants;
																							import helpz.LoadPathImage;
																							import main.GameStates;
																							import scenes.GameScene;
																							import scenes.Stages;
																							import stages.Stage2;
																							import stages.Stage1;

																							import static helpz.Constants.Enemy.*;
public class EnemyManager {

BufferedImage[] enemyImgs;
private ArrayList<Enemy> enemies = new ArrayList<>();
private MoveManager moveManager;
private int xTarget, yTarget;
private Wave wave;
private long lastSpawnTime;
private static final long SPAWN_INTERVAL = 2000;
private long tempTime;
private ArrayList<int[]> spawnPoints = new ArrayList<>();
private LifeSystem lifeSystem;
private long lastSpawnTime1;
private int spawnedEnemy = 0;
private float lifeBar;
private int tempLife;

public EnemyManager(GameScene stage) {
	wave = new Wave(this);
	enemyImgs = new BufferedImage[6];
	lifeBar = 550;
	tempLife = (int) lifeBar;
	if (stage instanceof Stage1) {
		moveManager = new MoveManager((Stage1) stage);
		spawnPoints.add(new int[]{1, 4 ,DUDE});
		spawnPoints.add(new int[]{1, 14, OSTER});
		xTarget = 17 * 32;
		yTarget = 9 * 32;
		wave.setNumInOneWave(10);
	}
	if (stage instanceof Stage2) {
		moveManager = new MoveManager((Stage2) stage);
		//spawnPoints.add(new int[]{0, 1 ,DUDE});
		spawnPoints.add(new int[]{0, 10, SLIME});
		xTarget = 19 * 32;
		yTarget = 1 * 32;
		wave.setNumInOneWave(30);
	}
}
public void update() {
	if(GameStates.GetGameState() == GameStates.STAGE1 || GameStates.GetGameState() == GameStates.STAGE2){
		lastSpawnTime = System.currentTimeMillis();
		spawningInterval(wave.numInOneWave());
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
private void spawnEnemyWave() {
	for(int [] spawnPoint : spawnPoints) {
		for (int i = 0; i < wave.numInOneWave(); i++) {
			addEnemy(spawnPoint[2], spawnPoint[0]*32, spawnPoint[1]*32); // Example spawn point and enemy type
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
	spawnedEnemy = 0;
	lifeBar = 550;
}
private void spawningInterval(int numberOfEnemies) {
		long currentTime = System.currentTimeMillis();
		if(tempTime == 0) {
			tempTime = lastSpawnTime;
		}
		if(spawnedEnemy  < numberOfEnemies) {
			if (currentTime - lastSpawnTime1 >= SPAWN_INTERVAL) {
				spawnEnemyWave();
				lastSpawnTime1 = currentTime;
				spawnedEnemy++;
			}
		}
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