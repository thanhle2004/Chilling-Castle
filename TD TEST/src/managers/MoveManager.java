package managers;

import Map.Level3;
import enemies.Enemy;
import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.*;

import stages.Stage2;

import Map.Level2;
import stages.Stage1;
import Map.Level1;
import stages.Stage3;


public class MoveManager {
    private Stage1 stage1;
    private Stage2 stage2;
    private Stage3 stage3;
    private float speed = 0.5f;
    private int tileValue = 0;
    private int[][] level ;

    public MoveManager(Stage1 stage1) {
        this.stage1 = stage1;
        level = Level1.getLevelData1();

    }
    public MoveManager(Stage2 stage2) {
        this.stage2 = stage2;
        level = Level2.getLevelData2();

    }
    public MoveManager(Stage3 stage3) {
        this.stage3 = stage3;
        level = Level3.getLevelData3();
    }


    // Check if the next tile the enemy will move to is a road
    public void isNextTileRoad(Enemy e, int xTarget, int yTarget) {
        if (e.getLastDir() == -1)
        	 setNewDirectionAndMove(e,xTarget,yTarget);

        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir()));

        if (getTileType(newX, newY) == ROAD_TILE) {
        	MoveToTarget(e, xTarget, yTarget);
        } else {
            setNewDirectionAndMove(e,xTarget,yTarget);
        }

    }



    private void setNewDirectionAndMove(Enemy e, int xTarget, int yTarget) {
        int dir = e.getLastDir();
        int xCord = (int) (e.getX() / 32);
        int yCord = (int) (e.getY() / 32);

        fixEnemyOffsetTile(e, dir, xCord, yCord);
        int newX1 = (int) (e.getX() + getSpeedAndWidth(dir));
        int newY1 = (int) (e.getY() + getSpeedAndHeight(dir));


        int mapWidth = 20;
        int mapHeight = 20;

        if (newX1 / 32 >= 0 && newX1 / 32 < mapWidth &&
            newY1 / 32 >= 0 && newY1 / 32 < mapHeight) {
            tileValue = level[yCord][xCord];
        }
        else {
        	if (dir == LEFT || dir == RIGHT) {
                int newY = (int) (e.getY() + getSpeedAndHeight(UP));
                if (getTileType((int) e.getX(), newY) == ROAD_TILE && yTarget < e.getY())
                    e.move(speed, UP);
                else
                    e.move(speed, DOWN);
            } else {
                int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT));
                if (getTileType(newX, (int) e.getY()) == ROAD_TILE && xTarget > e.getX())
                    e.move(speed, RIGHT);
                else
                    e.move(speed, LEFT);
            }
        }

        if (dir == LEFT || dir == RIGHT) {
            int newY = (int) (e.getY() + getSpeedAndHeight(UP));
            if (getTileType((int) e.getX(), newY) == ROAD_TILE )
                e.move(speed, UP);
            else
                e.move(speed, DOWN);
        } else {
            int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT));
            if (getTileType(newX, (int) e.getY()) == ROAD_TILE )
                e.move(speed, RIGHT);
            else
                e.move(speed, LEFT);
        }
    }

    // Move towards the target based on the best direction
    private void MoveToTarget(Enemy e, int xTarget, int yTarget) {
        int xEnemy = (int) e.getX();
        int yEnemy = (int) e.getY();
        float currentDistance = calculateDistance(xEnemy, yEnemy, xTarget, yTarget);
        int bestDir = getBestDir(currentDistance, xEnemy, yEnemy, xTarget, yTarget, e);
        e.move(speed, bestDir);
    }

    private int getBestDir(float currentDistance, int xEnemy, int yEnemy, int xTarget, int yTarget, Enemy e) {
        int[] dir = {UP, DOWN, RIGHT, LEFT};


        for (int direction : dir) {
            int newX = (int) (xEnemy+ getSpeedAndWidth(direction));
            int newY = (int) (yEnemy + getSpeedAndHeight(direction));

            if (getTileType(xEnemy, yEnemy) == ROAD_TILE) {
                float newDistance = calculateDistance(xEnemy, yEnemy, xTarget, yTarget);

                if (newDistance < currentDistance) {
                    if (getTileType(newX, newY) != ROAD_TILE) {
                        setNewDirectionAndMove(e,xTarget,yTarget);
                    }
                }
            }
        }
        return e.getLastDir();
    }


    private float getSpeedAndHeight(int dir) {
        if (dir == UP)
            return -speed;
        else if (dir == DOWN)
            return speed + 32;
        return 0;
    }

    // Calculate speed and width based on direction
    private float getSpeedAndWidth(int dir) {
        if (dir == LEFT)
            return -speed;
        else if (dir == RIGHT)
            return speed + 32;
        return 0;
    }


    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
        switch (dir) {
            case RIGHT:
                if (xCord < 19)
                    xCord++;
                break;
            case DOWN:
                if (yCord < 19)
                    yCord++;
                break;
        }
        e.setPos(xCord * 32, yCord * 32);
    }


    private boolean isAtEnd(Enemy e) {
        return false;
    }


    private int getTileType(int x, int y) {
        if (stage1 != null) {
            return stage1.getTileType(x, y);
        }
        if (stage2 != null) {
            return stage2.getTileType(x, y);
        }
        if (stage3 != null) {
            return stage3.getTileType(x, y);
        }
        return -1;
    }


	// Calculate distance between two points
    private float calculateDistance(int xEnemy, int yEnemy, int xTarget, int yTarget) {
    	return Math.abs(xTarget - xEnemy) + Math.abs(yTarget - yEnemy);
    }

}
