package managers;

import enemies.Enemy;
import helpz.Constants;
import towers.Bullet;
import towers.TowerEquippedButton;
import ui.Button;
import ui.TowerBar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    private BufferedImage img11, img12, img13, img21, img22, img23, img31, img32, img33, img41, img42, img43;
    private BufferedImage up1, up2, up3, sell1, sell2, sell3, first, last, closest, strongest, weakest, close;
    private Button upgradeButton, sellButton, firstB, lastB, closestB, strongestB, weakestB, closeButton;

    public TowerManager(StageManager stageManager, TowerBar towerBar) {
        this.stageManager = stageManager;
        this.towerBar = towerBar;
        importImg();
        initButtons();
        createButtonChooseMod();
    }

    public void createButtonChooseMod() {
        First = new Button(25, 445, 72, 35, first);
        Last = new Button(25, 445, 72, 35, last);
        Closet  = new Button(25, 445, 72, 35,closest);
        Strongest = new Button(25, 445, 72, 35,strongest);
    }

    public boolean addTower(TowerEquippedButton selectedTower, int xPos, int yPos) {
        Point position = new Point(xPos, yPos);
        int cost = selectedTower.getCost();
        if (towerMap.containsKey(position)) {
            return false;
        }

        TowerEquippedButton newTower = new TowerEquippedButton(
                towerBar.getTowerFrame(selectedTower.getTowerTypes()),
                xPos, yPos,
                32, 32,
                selectedTower.getCost(),
                selectedTower.getTowerTypes(),
                selectedTower.getLevel()
        );
        newTower.setBaseDamage(selectedTower.getDMG());
        towerMap.put(position, newTower);
        towerAmount++;
        deductionCoin(cost);
        return true;
    }

    public void shootBullet(TowerEquippedButton tower, Enemy target) {
        Bullet bullet = new Bullet(towerBar.getTowerBullet(tower.getTowerTypes()), tower.getPosX(), tower.getPosY(), 5, target, tower);
        bullets.add(bullet);
    }




    private void importImg() {
        try {
            img11 = ImageIO.read(getClass().getResourceAsStream("/T1_L1.png"));
            img12 = ImageIO.read(getClass().getResourceAsStream("/T1_L2.png"));
            img13 = ImageIO.read(getClass().getResourceAsStream("/T1_L3.png"));

            img21 = ImageIO.read(getClass().getResourceAsStream("/T2_L1.png"));
            img22 = ImageIO.read(getClass().getResourceAsStream("/T2_L2.png"));
            img23 = ImageIO.read(getClass().getResourceAsStream("/T2_L3.png"));

            img31 = ImageIO.read(getClass().getResourceAsStream("/T3_L1.png"));
            img32 = ImageIO.read(getClass().getResourceAsStream("/T3_L2.png"));
            img33 = ImageIO.read(getClass().getResourceAsStream("/T3_L3.png"));

            img41 = ImageIO.read(getClass().getResourceAsStream("/T4_L1.png"));
            img42 = ImageIO.read(getClass().getResourceAsStream("/T4_L2.png"));
            img43 = ImageIO.read(getClass().getResourceAsStream("/T4_L3.png"));

            up1 = ImageIO.read(getClass().getResourceAsStream("/Up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/Up3.png"));

            sell1 = ImageIO.read(getClass().getResourceAsStream("/SellLv1.png"));
            sell2 = ImageIO.read(getClass().getResourceAsStream("/SellLv2.png"));
            sell3 = ImageIO.read(getClass().getResourceAsStream("/SellLv3.png"));

            first = ImageIO.read(getClass().getResourceAsStream("/TypeAttack1.png"));
            last = ImageIO.read(getClass().getResourceAsStream("/TypeAttack2.png"));
            closest = ImageIO.read(getClass().getResourceAsStream("/TypeAttack3.png"));
            strongest = ImageIO.read(getClass().getResourceAsStream("/TypeAttack4.png"));

            close = ImageIO.read(getClass().getResourceAsStream("/bClose.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getUpImg(TowerEquippedButton tower) {
        switch (tower.getTowerTypes()) {
            case FIRE_TOWER:
                switch (tower.getLevel()) {
                    case 1:
                        return img11;
                    case 2:
                        return img12;
                    case 3:
                        return img13;
                }
            case ICE_TOWER:
                switch (tower.getLevel()) {
                    case 1:
                        return img21;
                    case 2:
                        return img22;
                    case 3:
                        return img23;
                }
            case LIGHT_TOWER:
                switch (tower.getLevel()) {
                    case 1:
                        return img31;
                    case 2:
                        return img32;
                    case 3:
                        return img33;
                }
            case BUFF_TOWER:
                switch (tower.getLevel()) {
                    case 1:
                        return img41;
                    case 2:
                        return img42;
                    case 3:
                        return img43;
                }
        }
        return null;
    }


    private void initButtons() {
        upgradeButton = new Button(null, 25, 410, 150, 35, up1);
        sellButton = new Button(null, 103,445,72, 35, sell1);
        closeButton = new Button(null, 160, 190, 50, 50, close);
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

    }

    public void draw(Graphics g) {
        for (TowerEquippedButton t : towerMap.values()) {
            drawTower(g, t);
        }
        
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        if (towerOnMap != null) {
            drawTowerSet(g, towerOnMap);
        }
    }

    public void drawTypeAttackButton(Graphics g, TowerEquippedButton tower) {
        switch (tower.getIndexChange()) {
            case 1:
                First.draw(g);
                break;
            case 2:
                Last.draw(g);
                break;
            case 3:
                Closet.draw(g);
                break;
            case 4:
                Strongest.draw(g);
                break;
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


    private void drawTowerSet(Graphics g, TowerEquippedButton tower) {
        drawRange(g, tower);
        g.drawImage(getUpImg(tower), 0, 200, 200, 300, null);
        drawUpButton(g, tower);
        drawSellButton(g, tower);
        drawTypeAttackButton(g, tower);
        drawCloseButton(g);
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

    public boolean removeTower(int x, int y) {
        Point position = new Point(x, y);
        if (towerMap.remove(position) != null) {
            towerAmount--;
            return true; // have removed
        }
        return false; // Don't have tower to remove
    }

    public void mouseClicked(int x, int y) {
        boolean clickedOnTower = false;
        List<TowerEquippedButton> towersToRemove = new ArrayList<>();
        // Check if clicked on any tower
        for (TowerEquippedButton tower : towerMap.values()) {
            if (x >= tower.getPosX() && x <= tower.getPosX() + 32 &&
                    y >= tower.getPosY() && y <= tower.getPosY() + 32) {
                towerOnMap = tower;
                clickedOnTower = true;
                upgradeButton.setImage(getUpgradeButtonImg(tower));
                break;
            }
            //Change upgrade image
            if (towerOnMap != null && upgradeButton.getBounds().contains(x, y) && towerOnMap.getLevel() < 3) {
                TowerEquippedButton selectedTower = getTowerAt(towerOnMap.getPosX(), towerOnMap.getPosY());
                if (selectedTower.getLevel() < 3) {
                    int currency = Constants.Tower.CoinUpgradeL(selectedTower.getLevel() + 1);
                    if (canUpgarde(currency)) {
                        selectedTower.setLevel(selectedTower.getLevel() + 1);
                        updateUpgradeButtonImage(selectedTower);
                        deductionCoin(currency);
                        selectedTower.updateStatPerLv();
                    }

                }
                towerOnMap = null;
            }

            //Sell tower
            if(towerOnMap != null && sellButton.getBounds().contains(x, y)) {
                towersToRemove.add(tower);
                getTowerAt(towerOnMap.getPosX(), towerOnMap.getPosY());
                increaseCoin(Constants.Tower.coinReceived(towerOnMap.getLevel()));
                towerOnMap = null;
            }

            if(towerOnMap != null && closeButton.getBounds().contains(x, y)) {
                towerOnMap = null;
            }


        }

        boolean clickedOnModArea = isClickOnModArea(x, y);

        if ((clickedOnTower || clickedOnModArea) && towerOnMap.getTowerTypes() != BUFF_TOWER) {
            if (clickedOnModArea) {
                canClicked(x, y);
            }
        } else {
            towerOnMap = null;
        }
        for (TowerEquippedButton tower : towersToRemove) {
            removeTower(tower.getPosX(), tower.getPosY()); // Xóa tháp sau khi vòng lặp kết thúc
        }
    }

    private boolean canUpgarde(int currency) {
        return stageManager.getCoinValue() >= currency;
    }

    private void deductionCoin(int currency) {
        stageManager.setCoin(stageManager.getCoinValue() - currency);
    }

    private void increaseCoin(int currency) {
        stageManager.setCoin(stageManager.getCoinValue() + currency);
    }



    private void updateUpgradeButtonImage(TowerEquippedButton tower) {
        int level = tower.getLevel();
        switch (level) {
            case 1:
                upgradeButton.setImage(up1);
                break;
            case 2:
                upgradeButton.setImage(up2);
                break;
            case 3:
                upgradeButton.setImage(up3);
                break;
            default:
                upgradeButton.setImage(null);
                break;
        }
    }

    public BufferedImage getUpgradeButtonImg(TowerEquippedButton tower) {
        switch (tower.getLevel()) {
            case 1:
                return up1;
            case 2:
                return up2;
            case 3:
                return up3;
        }
        return null;
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
        if (First.getBounds().contains(x, y) && towerOnMap.getIndexChange() == 1 ) {
            System.out.println("Changing to First");
            towerOnMap.setMod(FIRSTE);
            towerOnMap.setIndexChange(2);

        } else if (Last.getBounds().contains(x, y) && towerOnMap.getIndexChange() == 2 ) {
            System.out.println("Changing to Last");
            towerOnMap.setMod(LASTE);
            towerOnMap.setIndexChange(3);

        } else if (Closet.getBounds().contains(x, y) && towerOnMap.getIndexChange() == 3) {
            System.out.println("Changing to Closet");
            towerOnMap.setMod(CLOSET);
            towerOnMap.setIndexChange(4);

        } else if (Strongest.getBounds().contains(x, y) && towerOnMap.getIndexChange() == 4) {
            System.out.println("Changing to Strongest");
            towerOnMap.setMod(STRONGEST);
            towerOnMap.setIndexChange(1);
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




    public void drawUpButton(Graphics g, TowerEquippedButton tower) {
        g.drawImage(upgradeButton.getImage(), upgradeButton.getX(), upgradeButton.getY(),
                upgradeButton.getWidth(), upgradeButton.getHeight(), null);
    }

    public void drawCloseButton(Graphics g) {
        g.drawImage(closeButton.getImage(), closeButton.getX(), closeButton.getY(), closeButton.getWidth(), closeButton.getHeight(), null);
    }

    public void drawSellButton(Graphics g, TowerEquippedButton tower) {
        switch (tower.getLevel()) {
            case 1:
                g.drawImage(sell1, 103,445,72, 35, null);
                break;
            case 2:
                g.drawImage(sell2, 103,445,72, 35, null);
                break;
            case 3:
                g.drawImage(sell3, 103,445,72, 35, null);
                break;
        }
    }




}
