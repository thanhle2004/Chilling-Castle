package managers;

import enemies.Enemy;
import towers.Bullet;
import towers.TowerEquippedButton;
import ui.Button;
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
    private Button First, Last, Strongest, Closet;
    private boolean OpenModButton    = false;
    public TowerManager(StageManager stageManager, TowerBar towerBar) {
        this.stageManager = stageManager;
        this.towerBar = towerBar;
        createButtonChooseMod();
    }

    public void createButtonChooseMod() {
        First = new Button("First" , 500, 525, 50, 50);
        Last = new Button("Last" , 500 + 50 + 10, 525, 50, 50);
        Closet  = new Button("Closet" , 500, 525 + 10 + 50, 50, 50);
        Strongest = new Button("Strongest" , 500 + 10 + 50, 525 + 10 + 50, 50, 50);
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
                Enemy target = ChosenTarget(tower.getPosX(), tower.getPosY(), tower.getRNG(), tower.GetmodState());
                if (target == null) {
                System.out.println("No target found for tower at: " + tower.getPosX() + ", " + tower.getPosY());
                } else if (tower.canShoot() && tower.getTowerTypes() < 4) { //nạp đạn
                    shootBullet(tower, target);
                    tower.resetCooldown();
                }
            }

        }
        if (currentTarget != null && currentTarget.dead()) {
            currentTarget = null;
        }

        if (stageManager.isLose) {
            OpenModButton = false;
        }
    }

    public void draw(Graphics g) {
        for (TowerEquippedButton t : towerMap.values()) {
            drawTower(g, t);
        }

        if (towerOnMap != null) {
            drawRange(g, towerOnMap);
            if (OpenModButton) {
                drawChooseMod(g);
            }
        }

        for (Bullet bullet : bullets) {
            bullet.draw(g);
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

        g.drawArc(x, y, radius * 2, radius * 2, 0, 360);
    }

    public void drawChooseMod(Graphics g) {
        First.draw(g);
        Last.draw(g);
        Closet.draw(g);
        Strongest.draw(g);
    }


    public TowerEquippedButton getTowerAt(int x, int y) {
        return towerMap.get(new Point(x, y));
    }

    public void clearTowers() {
        towerMap.clear();
        towerAmount = 0;
        towerOnMap = null;
        currentTarget = null;
    }

    public void mouseClicked(int x, int y) {
        boolean clickedOnTower = false;

        // Check if clicked on any tower
        for (TowerEquippedButton tower : towerMap.values()) {
            if (x >= tower.getPosX() && x <= tower.getPosX() + 32 &&
                    y >= tower.getPosY() && y <= tower.getPosY() + 32) {
                towerOnMap = tower;
                clickedOnTower = true;
                break;
            }
        }


        boolean clickedOnModArea = isClickOnModArea(x, y);

        if ((clickedOnTower || clickedOnModArea) && towerOnMap.getTowerTypes() != BUFF_TOWER) {
            OpenModButton = true;
            if (clickedOnModArea) {
                canClicked(x, y);
            }
        } else {
            OpenModButton = false;
            towerOnMap = null;
        }
    }

    private boolean isClickOnModArea(int x, int y) {
        return First.getBounds().contains(x, y) ||
                Last.getBounds().contains(x, y) ||
                Closet.getBounds().contains(x, y) ||
                Strongest.getBounds().contains(x, y);
    }

    private void canClicked(int x, int y) {
        if (towerOnMap == null) {
            return;
        }
        if (First.getBounds().contains(x, y)) {
            System.out.println("Changing to First");
            towerOnMap.setMod(FIRSTE);
            OpenModButton = true;
        } else if (Last.getBounds().contains(x, y)) {
            System.out.println("Changing to Last");
            towerOnMap.setMod(LASTE);
            OpenModButton = true;
        } else if (Closet.getBounds().contains(x, y)) {
            System.out.println("Changing to Closet");
            towerOnMap.setMod(CLOSET);
            OpenModButton = true;
        } else if (Strongest.getBounds().contains(x, y)) {
            System.out.println("Changing to Strongest");
            towerOnMap.setMod(STRONGEST);
            OpenModButton = true;
        }
    }

    public Enemy ChosenTarget (int x, int y, double range,int Mod) {
        switch (Mod) {
            case CLOSET:
                return getClosetTarget(x, y, range);
            case STRONGEST:
                return getStrongestEnemy(x, y, range);
            case FIRSTE:
                return getFirstE(x, y, range);
            case LASTE:
                return getLastEnemy(x,y,range);
            case RANDOM:
                return getRandomEnemy(x, y, range);
        }
        return null;
    }

    private Enemy getLastEnemy(int x, int y, double range) {
        for (int i = stageManager.getEnemyManager().getEnemies().size() - 1; i >= 0; i--) {
            Enemy enemyTarget = stageManager.getEnemyManager().getEnemies().get(i);
            if (isInRange(enemyTarget,x,y,range)){
                return enemyTarget;
            }

        }
        return null;
    }

    public Enemy getFirstE (int x, int y, double range) {
        if(currentTarget == null || currentTarget.dead() ||  !isInRange(currentTarget ,x ,y,range)) {
            currentTarget = null;
            for (Enemy enemy : stageManager.getEnemyManager().getEnemies()) {
                if (isInRange(enemy,x,y,range)) {
                    currentTarget = enemy;
                    break;
                }
            }
        }
        return currentTarget;

    }

    private boolean isInRange(Enemy currentTarget, int x, int y, double range) {
        double distance = Math.hypot(currentTarget.getX() - x, currentTarget.getY() - y);
        return distance <= range;
    }

    private Enemy getClosetTarget (int x, int y, double range) {
        Enemy closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : stageManager.getEnemyManager().getEnemies()) {
            double distance = Math.hypot(enemy.getX() - x, enemy.getY() - y);
            if (distance <= range && distance < closestDistance) {
                closest = enemy;
                closestDistance = distance;
            }
        }

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
                }
            }
        }
    }




}
