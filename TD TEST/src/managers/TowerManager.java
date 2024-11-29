package managers;

import towers.TowerEquippedButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TowerManager {

    private StageManager stageManager;
    private Map<Point, TowerEquippedButton> towerMap = new HashMap<>();
    private int towerAmount = 0;

    public TowerManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }


    public boolean addTower(TowerEquippedButton selectedTower, int xPos, int yPos) {
        Point position = new Point(xPos, yPos);

        // Kiểm tra nếu đã có tháp tại vị trí này
        if (towerMap.containsKey(position)) {
            return false; // Không thể thêm tháp
        }

        TowerEquippedButton newTower = new TowerEquippedButton(
                selectedTower.getImg(),
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
        return true; // Thêm thành công
    }

    public void update() {
    }

    private void drawTower(Graphics g, TowerEquippedButton tower) {
        g.drawImage(tower.getImg(), tower.getPosX(), tower.getPosY(), 32, 32, null);
    }

    public void draw(Graphics g) {
        for (TowerEquippedButton t : towerMap.values()) {
            drawTower(g, t);
        }
    }

    public boolean removeTower(int x, int y) {
        Point position = new Point(x, y);
        if (towerMap.remove(position) != null) {
            towerAmount--;
            return true; // Xóa thành công
        }
        return false; // Không có tháp để xóa
    }

    public TowerEquippedButton getTowerAt(int x, int y) {
        return towerMap.get(new Point(x, y));
    }

}
