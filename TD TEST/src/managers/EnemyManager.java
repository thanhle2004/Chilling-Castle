package managers;

import java.awt.*;
																							import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
import stages.Stage3;
import ui.SettingBoardUI;

import javax.imageio.ImageIO;

import static helpz.Constants.Enemy.*;
public class EnemyManager {

BufferedImage[] enemyImgs;
private ArrayList<Enemy> enemies = new ArrayList<>();
private MoveManager moveManager;
private int xTarget, yTarget;
private ArrayList<long[]> spawnPoints = new ArrayList<>(); //contains spawnPoint and type enemies
private ArrayList<Long> initSpawnAmount =  new ArrayList<>();
private float lifeBar;
private Wave wave;
private long tempTime;
private long pauseTime;
private int coin;
private boolean pauseGame = false;
private long totalEnemies, totalEnemiesTemp;
private BufferedImage HP, enemiesLeft;

public EnemyManager(GameScene stage) {

	enemyImgs = new BufferedImage[6];
	lifeBar = 550;
	coin = 100;
	wave = new Wave();
	importImg();
	if (stage instanceof Stage1) {
		xTarget = 17 * 32;
		yTarget = 9 * 32;
		moveManager = new MoveManager((Stage1) stage);
		//each enemies = long[] {spawnX, spawnY, EnemyType,numberPerTurn, startTime, nextSpawnTime, timeSpawnInterval}
		spawnPoints.add(wave.WaveInTurn(0, 4, DUDE, 21, 1000,1000,2000));
		spawnPoints.add(wave.WaveInTurn(0, 11, OSTER, 45, 5000,5000,2500));
	}
	if (stage instanceof Stage2) {
		xTarget = 18 * 32;
		yTarget = 3 * 32;
		moveManager = new MoveManager((Stage2) stage);
		spawnPoints.add(wave.WaveInTurn(0, 3, PINKY, 2, 1000,1000,2000));
		spawnPoints.add(wave.WaveInTurn(0, 6, SLIME, 15, 12000,12000,2000));

	}
	if (stage instanceof Stage3) {
		xTarget = 17 * 32;
		yTarget = 9 * 32;
		moveManager = new MoveManager((Stage3) stage);
		spawnPoints.add(wave.WaveInTurn(1, 4, DUDE, 1, 1000,1000,2000));
		spawnPoints.add(wave.WaveInTurn(0, 6, OSTER, 5, 1000,1000,5000));

	}
	calculateTotalEnemies();
}

	private void calculateTotalEnemies() {
		totalEnemies = 0;
		for (long[] enemies : spawnPoints) {
			totalEnemies += enemies[3];
		}
		totalEnemiesTemp = totalEnemies;
	}

	public void update() {

	if (pauseGame) {
		timePauseSyn();
		return;
	}

	if(GameStates.GetGameState() == GameStates.STAGE1
			|| GameStates.GetGameState() == GameStates.STAGE2
			|| GameStates.GetGameState() == GameStates.STAGE3){
		spawningInterval();
	}

	ArrayList<Enemy> toRemove = new ArrayList<>();
	ArrayList<Enemy> deadEnemies = new ArrayList<>();
	for (Enemy e : enemies) {
		e.updateHitbox((int)e.getX(), (int) e.getY());
		if (Math.abs(e.getX() - xTarget) < 1 && Math.abs(e.getY() - yTarget) < 1) {
			substractLifeBar(e);
			toRemove.add(e);
			totalEnemies --;

		}  else {
			e.updateAnimation();
			moveManager.	isNextTileRoad(e, xTarget, yTarget);
		}

		if(e.dead()) {
			deadEnemies.add(e);
			coin += e.getCoin();
			totalEnemies --;
		}
	}

	enemies.removeAll(toRemove);
	enemies.removeAll(deadEnemies);
}




private void spawningInterval() {
	long currentTime = System.currentTimeMillis();

	if (tempTime == 0) {
		tempTime = currentTime;
	}


	for (long[] spawnPoint : spawnPoints) {
		long startTime = spawnPoint[4];
		long nextSpawnTime = spawnPoint[5];
		long interval = spawnPoint[6];
		long spawnedEnemy = spawnPoint[3];
		initSpawnAmount.add(spawnPoint[3]);

		if (currentTime - tempTime >= startTime  && spawnedEnemy > 0 && currentTime >= nextSpawnTime) {
			addEnemy((int)spawnPoint[2], (int)spawnPoint[0] * 32, (int)spawnPoint[1] * 32);
			spawnPoint[5] = currentTime + interval;
			spawnPoint[3] = spawnedEnemy - 1;
		}
		System.out.println("Subtract is " + (currentTime - tempTime));
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
	g.drawImage(HP,45,0, 80, 80 ,null);
	g.drawImage(enemiesLeft,0,550, 170, 170 ,null);
}
private void drawEnemy(Enemy e, Graphics g) {
	if (e.getImages() != null) {
		int imgIndex = e.getImgIndex();
		g.drawImage(e.getImages()[imgIndex], (int) e.getX(), (int) e.getY(), null);
	}
}
public void resetEnemies() {
	enemies.clear();
//	pauseTime = 0;
	for (int i = 0; i < spawnPoints.size(); i++) {
		spawnPoints.get(i)[3] = initSpawnAmount.get(i);
		spawnPoints.get(i)[5] = spawnPoints.get(i)[4];
	}

	tempTime = System.currentTimeMillis();
	lifeBar = 550;
	coin = 100;
	totalEnemies = totalEnemiesTemp;
}


public void drawHealthBar(Enemy enemy, Graphics g) {
	g.setColor(Color.RED);
	g.fillRect((int) enemy.getX(), (int) enemy.getY() - 2, ratioHealth(enemy), 2 );
}
public int ratioHealth(Enemy enemy) {
	float ratio = (float) enemy.getHealth() / enemy.getMaxHealth() * 100;
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


public int getCoin() {
	return coin;
}


//Make time syn

private void timePauseSyn() {
	if (pauseTime == 0) { //pause at one time
		pauseTime = System.currentTimeMillis();
	}

}

public void setPauseGame(boolean pauseGame) {
	this.pauseGame = pauseGame;
	if (!pauseGame) {
		resumeFromPause();
	}
}

	private void resumeFromPause() {
		if (pauseTime > 0) {// avoid case when  first time running the game
			long currentTime = System.currentTimeMillis();
			long pauseDuration = currentTime - pauseTime;

			tempTime = Math.min(tempTime + pauseDuration, currentTime);

			for (long[] spawnPoint : spawnPoints) {
				spawnPoint[5] = spawnPoint[5] + pauseDuration;
			}
			pauseTime = 0;
		}
	}




public ArrayList<Enemy> getEnemies() {
	return enemies;
}

public void setCoin(int coin) {
	this.coin = coin;
}

public long getTotalEnemies() {
	return totalEnemies;
}
private void importImg() {
	try {
		HP = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HP.png")));
		enemiesLeft  = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/EnemiesLeft.png")));
	} catch (IOException e) {
		throw new RuntimeException(e);
	}
}
}


