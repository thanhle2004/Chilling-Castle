package managers;

import enemies.Enemy;
import towers.Bullet;
import towers.TowerEquippedButton;
import ui.TowerBar;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.List;
import static helpz.Constants.Tower.*;

public class TowerManager {

    private StageManager stageManager;
    private TowerBar towerBar;
    private Map<Point, TowerEquippedButton> towerMap = new HashMap<>();
    private int towerAmount = 0;
    private TowerEquippedButton towerOnMap = null;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Enemy currentTarget;

    public TowerManager(StageManager stageManager, TowerBar towerBar) {
        this.stageManager = stageManager;
        this.towerBar = towerBar;
    }

    public boolean addTower(TowerEquippedButton selectedTower, int xPos, int yPos) {
        Point position = new Point(xPos, yPos);


        if (towerMap.containsKey(position)) {
            return false;
        }

        TowerEquippedButton newTower = new TowerEquippedButton(
                towerBar.getTowerFrame(selectedTower.getTowerTypes()),
                xPos, yPos,
                32, 32,
//                selectedTower.getDMG(),
//                selectedTower.getCD(),
//                selectedTower.getRNG(),
                selectedTower.getCost(),
                selectedTower.getTowerTypes()
        );
        newTower.setBaseDamage(selectedTower.getDMG());
        towerMap.put(position, newTower);
        towerAmount++;
        return true;
    }

    public void shootBullet(TowerEquippedButton tower, Enemy target) {
        Bullet bullet = new Bullet(towerBar.getTowerBullet(tower.getTowerTypes()), tower.getPosX(), tower.getPosY(), 5, target, tower);
        bullets.add(bullet);
    }


    public void update() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
            if (bullet.isDestroyed()) {
                iterator.remove();
            }
        }

        for (TowerEquippedButton tower : towerMap.values()) {
            if (tower.getTowerTypes() == BUFF_TOWER) {
                applyBuff(tower);
            } else {
                Enemy target = ChosenTarget(tower.getPosX(), tower.getPosY(), tower.getRNG(), tower.getTowerTypes());
                if (target == null) {
//                System.out.println("No target found for tower at: " + tower.getPosX() + ", " + tower.getPosY());
                } else if (tower.canShoot() && tower.getTowerTypes() < 4) { //nạp đạn
                    shootBullet(tower, target);
                    tower.resetCooldown();
                }
            }

        }
        if (currentTarget != null && currentTarget.dead()) {
            currentTarget = null;
        }


    }

    private void drawTower(Graphics g, TowerEquippedButton tower) {
        g.drawImage(tower.getImg(), tower.getPosX(), tower.getPosY(), 32, 32, null);
    }

    private void drawRange(Graphics g, TowerEquippedButton tower) {
        int radius = (int) tower.getRNG();
        int centerX = tower.getPosX() + 16;
        int centerY = tower.getPosY() + 16;


        int x = centerX - radius;
        int y = centerY - radius;

        // Vẽ hình tròn
        g.drawArc(x, y, radius * 2, radius * 2, 0, 360);
    }


    public void draw(Graphics g) {
        for (TowerEquippedButton t : towerMap.values()) {
            drawTower(g, t);
        }

        if (towerOnMap != null) {
            drawRange(g, towerOnMap);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }


    public TowerEquippedButton getTowerAt(int x, int y) {
        return towerMap.get(new Point(x, y));
    }

    public void clearTowers() {
        towerMap.clear();
        towerAmount = 0;
    }

    public void mouseClicked(int x, int y) {
        towerOnMap = null;

        for (TowerEquippedButton tower : towerMap.values()) {
            if (x >= tower.getPosX() && x <= tower.getPosX() + 32 &&
                    y >= tower.getPosY() && y <= tower.getPosY() + 32) {
                towerOnMap = tower;
                break;
            }
        }
    }

    public Enemy ChosenTarget   (int x, int y, double range,int TowerTypes) {
        switch (TowerTypes) {
            case ICE_TOWER:
                return getFirstE(x, y, range);
            case FIRE_TOWER:
                return getStrongestEnemy(x, y, range);
            case LIGHT_TOWER:
                return getRandomEnemy(x, y, range);
        }
        return null;
    }

    public Enemy getFirstE (int x, int y, double range) {
        //set the first one which go in range first
        if (currentTarget != null) {
            double distance = Math.hypot(currentTarget.getX() - x, currentTarget.getY() - y);
            if (distance <= range) {
                return currentTarget;
            } else {
                currentTarget = null;
            }
        }

        //Find new enemy
        Enemy closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : stageManager.getEnemyManager().getEnemies()) {
            double distance = Math.hypot(enemy.getX() - x, enemy.getY() - y);
            if (distance <= range && distance < closestDistance) {
                closest = enemy;
                closestDistance = distance;
            }
        }

        currentTarget = closest;
        return closest;
    }


    private Enemy getRandomEnemy(int x, int y, double range) {
        List<Enemy> enemiesInRange = new ArrayList<>();
        for (Enemy enemy : stageManager.getEnemyManager().getEnemies()) {
            double distance = Math.hypot(enemy.getX() - x, enemy.getY() - y);
            if (distance <= range) {
                enemiesInRange.add(enemy);
            }
        }
        if (!enemiesInRange.isEmpty()) {
            return enemiesInRange.get(new Random().nextInt(enemiesInRange.size()));
        }
        return null;
    }

    private Enemy getStrongestEnemy(int x, int y, double range) {
        Enemy strongest = null;
        int highestHealth = Integer.MIN_VALUE;

        for (Enemy enemy : stageManager.getEnemyManager().getEnemies()) {
            double distance = Math.hypot(enemy.getX() - x, enemy.getY() - y);
            if (distance <= range && enemy.getHealth() > highestHealth) {
                strongest = enemy;
                highestHealth = enemy.getHealth();
            }
        }
        return strongest;
    }

    private void applyBuff(TowerEquippedButton buffTower) {
        for (TowerEquippedButton tower : towerMap.values()) {
            if (tower != buffTower) {
                double distance = Math.hypot(tower.getPosX() - buffTower.getPosX(), tower.getPosY() - buffTower.getPosY());
                if (distance <= buffTower.getRNG()) {
                    double newDamage = tower.getBaseDamage() * 2;
                    tower.setDamage(newDamage);
                    tower.printTowerInfo();
                }
            }
        }
    }

}
