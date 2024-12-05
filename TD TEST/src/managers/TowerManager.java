package managers;

import enemies.Enemy;
import towers.Bullet;
import towers.TowerEquippedButton;
import ui.TowerBar;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TowerManager {

    private StageManager stageManager;
    private TowerBar towerBar;
    private Map<Point, TowerEquippedButton> towerMap = new HashMap<>();
    private int towerAmount = 0;
    private TowerEquippedButton towerOnMap = null;
    private ArrayList<Bullet> bullets = new ArrayList<>();

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
                towerBar.getTowerFrame(selectedTower.getTowerNum()),
                xPos, yPos,
                32, 32,
                selectedTower.getDMG(),
                selectedTower.getCD(),
                selectedTower.getRNG(),
                selectedTower.getCost(),
                selectedTower.getTowerNum()
        );
        towerMap.put(position, newTower);
        towerAmount++;
        return true;
    }

    public void shootBullet(TowerEquippedButton tower, Enemy target) {
        Bullet bullet = new Bullet(towerBar.getTowerBullet(tower.getTowerNum()), tower.getPosX(), tower.getPosY(), 5, target);
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
            Enemy target = stageManager.getClosestEnemy(tower.getPosX(), tower.getPosY(), tower.getRNG());
            if (target == null) {
//                System.out.println("No target found for tower at: " + tower.getPosX() + ", " + tower.getPosY());
            } else if (tower.canShoot() && tower.getTowerNum() < 4) {
                shootBullet(tower, target);
                tower.resetCooldown();
            }
        }
    }

    private void drawTower(Graphics g, TowerEquippedButton tower) {
        g.drawImage(tower.getImg(), tower.getPosX(), tower.getPosY(), 32, 32, null);
    }

    private void drawRange(Graphics g, TowerEquippedButton tower) {
        g.drawArc(tower.getPosX() - 32 * 2, tower.getPosY() - 32 * 2, 160, 160, 0, 360);
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

}
