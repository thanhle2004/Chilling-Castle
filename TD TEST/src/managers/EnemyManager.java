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
import stages.Stage3;
import ui.SettingBoardUI;

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


public EnemyManager(GameScene stage) {

	enemyImgs = new BufferedImage[6];
	lifeBar = 550;
	coin = 100;
	wave = new Wave();

	if (stage instanceof Stage1) {
		moveManager = new MoveManager((Stage1) stage);
		spawnPoints.add(wave.WaveInTurn(1, 4, DUDE, 15, 1000,1000,2000));
		spawnPoints.add(wave.WaveInTurn(1, 14, OSTER, 5, 1000,1000,5000));
		xTarget = 17 * 32;
		yTarget = 9 * 32;
	}
	if (stage instanceof Stage2) {
		moveManager = new MoveManager((Stage2) stage);
		spawnPoints.add(wave.WaveInTurn(0, 10, PINKY, 12, 1000,1000,2000));
		spawnPoints.add(wave.WaveInTurn(0, 10, SLIME, 3, 12000,12000,2000));
		xTarget = 19 * 32;
		yTarget = 1 * 32;
	}
	if (stage instanceof Stage3) {
		moveManager = new MoveManager((Stage3) stage);
		spawnPoints.add(wave.WaveInTurn(1, 4, DUDE, 1, 1000,1000,2000));
//		spawnPoints.add(wave.WaveInTurn(1, 14, OSTER, 5, 1000,1000,5000));
		xTarget = 17 * 32;
		yTarget = 9 * 32;
	}
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

//	ArrayList <Integer> tempLife = new ArrayList<>();
	ArrayList<Enemy> toRemove = new ArrayList<>();
	ArrayList<Enemy> deadEnemies = new ArrayList<>();
	for (Enemy e : enemies) {
		if (Math.abs(e.getX() - xTarget) < 1 && Math.abs(e.getY() - yTarget) < 1) {
			substractLifeBar(e);
			toRemove.add(e);

		}  else {
			e.updateAnimation();
			moveManager.isNextTileRoad(e, xTarget, yTarget);
		}

		if(e.dead()) {
			deadEnemies.add(e);
			coin += e.getCoin();

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
//	pauseTime = 0;
	for (int i = 0; i < spawnPoints.size(); i++) {
		spawnPoints.get(i)[3] = initSpawnAmount.get(i);
		spawnPoints.get(i)[5] = spawnPoints.get(i)[4];
	}

	tempTime = System.currentTimeMillis();
	lifeBar = 550;
	coin = 100;
}


public void drawHealthBar(Enemy enemy, Graphics g) {
	g.setColor(Color.RED);
	g.fillRect((int) enemy.getX(), (int) enemy.getY() - 2, ratioHealth(enemy), 2 );
}
public int ratioHealth(Enemy enemy) {
	float ratio = (float) enemy.getHealth() / enemy.getMaxHealth() * 100;
	System.out.println("ratio" + ratio);
	float healtBar = ratio * 32 / 100;
	System.out.println("health" + healtBar);
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


}


