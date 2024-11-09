package managers;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.DUDE;
import enemies.Enemy;
import enemies.OSTER;
import enemies.PINKY;
import enemies.SLIME;
import helpz.LoadPathImage;
import main.GameStates;
import scenes.GameScene;
import scenes.Stages;
import stages.Stage2;
import stages.Stage1;

import static helpz.Constants.Enemy.*;

public class EnemyManager {
	private Stage1 stage1;
	private Stage2 stage2;
	BufferedImage[] enemyImgs; 
	private ArrayList<Enemy> enemies = new ArrayList<>(); 
	private MoveManager moveManager;
	private BufferedImage atlas;
	private int xTarget, yTarget;
	private Stages stages;

    // Constructor to initialize the EnemyManager with a stage
	public EnemyManager(GameScene stage) {
	    enemyImgs = new BufferedImage[6];
	    if (stage instanceof Stage1) {
	        moveManager = new MoveManager((Stage1) stage);
	        loadEnemyImgs(GameStates.STAGE1);
			addEnemy(OSTER, 0, 14 * 32);
			xTarget = 19*32;
			yTarget = 1*32;
	    }
	   	if (stage instanceof Stage2) {
	        moveManager = new MoveManager((Stage2) stage);
	        loadEnemyImgs(GameStates.STAGE2);
	        addEnemy(DUDE, 0, 1 * 32);
	        xTarget = 19*32;
	        yTarget = 1*32;
	    }
	}

   
    private void loadEnemyImgs(GameStates gameStates) {
    	
    	switch (gameStates) {
		case STAGE1:
			atlas = LoadPathImage.getOSTERPath(); 
			break;
		case STAGE2:
			atlas = LoadPathImage.getDudePath(); 
			break;
		}

        for(int i = 0 ; i < 6; i++) {
            enemyImgs[i] = atlas.getSubimage(i * 32, 0, 32, 32); 
        }
    }

   
    public void update() {
    	//Không xóa trực tiếp trong enemies vì có thể xảy ra lỗi kích thước của arraylist
    	// add vào toremove để tạm thời, sau đó xóa toàn bộ nó đi. vì e trong enemies và toRemove có chung địa chỉ.
    	ArrayList<Enemy> toRemove = new ArrayList<>();
        for (Enemy e : enemies) {
            if (Math.abs(e.getX() - xTarget) < 1 && Math.abs(e.getY() - yTarget) < 1) {
            	 toRemove.add(e);
            } else {
            	e.updateAnimation(); 
                moveManager.isNextTileRoad(e, xTarget, yTarget); 
            }
        }
        enemies.removeAll(toRemove); 
    }    
    // Add a new enemy to the list
    public void addEnemy(int EnemyTypes, int PointSpawnX, int PointSpawnY) {
        switch(EnemyTypes) {
		case SLIME:
			enemies.add(new SLIME(PointSpawnX, PointSpawnY, 0));
			break;
		case DUDE:
			enemies.add(new DUDE(PointSpawnX, PointSpawnY, 0));
			break;
		case OSTER:
			enemies.add(new OSTER(PointSpawnX, PointSpawnY, 0));
			break;
		case PINKY:
			enemies.add(new PINKY(PointSpawnX, PointSpawnY, 0));
			break;
        }
    }

  
    public void draw(Graphics g) {
			for (Enemy e : enemies)
				drawEnemy(e, g);
			System.out.println("Draw failure at");



    }

    
    private void drawEnemy(Enemy e, Graphics g) {
        int imgIndex = e.getImgIndex(); 
        // 1 tile is 32*32, monster is 23*23 so it takes an offset of 4*4
        g.drawImage(enemyImgs[imgIndex], (int) e.getX(), (int) e.getY(), null); 
    }

	public void resetEnemies() {
		enemies.clear();
	}

}




