package Scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Game;
import MapLevel.Level1;
import MapLevel.Level2;
import Tiles.TilesManager;
import UI.BottomBar;
import UI.Buttons;

public class Playing extends GameScenes implements SceneMethod {
    public int[][] lv1, lv2;
    private TilesManager tilesManager;
    private int currentMap = 0;
    private int[][] currentLevel;
    private BottomBar bottomBar;
    public Playing(Game game) {
        super(game);
        MapLoader();
        tilesManager = new TilesManager();
        bottomBar = new BottomBar(0,640,960,100, this);
        changeStage(); 
    }

    private void changeStage() {
        switch (currentMap) {
            case 0:
                currentLevel = lv1;
                break;
            case 1:
                currentLevel = lv2;
                break;
            default:
                currentMap = 0; 
                currentLevel = lv1;
                break;
        }
    }


    @Override
    public void render(Graphics g) {
        for (int i = 0; i < currentLevel.length; i++) {
            for (int j = 0; j < currentLevel[i].length; j++) {
                int ID = currentLevel[i][j]; 
                g.drawImage(getImageFromID(ID), j * 32, i * 32, null);
            }
        }
        bottomBar.draw(g);
    }

    public void switchMap() {
        System.out.println("Switching map from: " + currentMap); 
        currentMap += 1; 
        if (currentMap >= 2) {
            currentMap = 0; 
        }
        changeStage(); 
        game.repaint();
    }



    private void MapLoader() {
        lv1 = Level1.getLevel1Data();
        lv2 = Level2.getLevel2Data();
        
    }

    private BufferedImage getImageFromID(int ID) {
        return tilesManager.getID(ID);
    }

    @Override
    public void mouseClicked(int x, int y) {
            }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640) {
        	bottomBar.mouseMoved(x,y);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        
    }

    @Override
    public void mouseReleased(int x, int y) {
        
    }

    @Override
    public void mouseDragged(int x, int y) {
        
    }
}
