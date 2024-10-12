package Tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class TilesManager {
    public Tile grassTile, waterTile, roadTile;
    public BufferedImage sprites;
    public ArrayList<Tile> tiles = new ArrayList<>();
    public ArrayList<Tile> towers = new ArrayList<>();
    private String[] nameTower;
    public BufferedImage towerSprites;

    public TilesManager() {
        nameTower = new String[] {"jester.png", "king.png", "shady_guy.png", "stranger.png"};
        getTileImage();
        getTowerImage(); 
    }


    public void getTileImage() {
        sprites = getPathTileImage(); 
        int id = 0;
        tiles.add(grassTile = new Tile("Grass", id++, getSubImage(8, 1, sprites))); 
        tiles.add(waterTile = new Tile("Water", id++, getSubImage(0, 0, sprites)));
        tiles.add(roadTile = new Tile("Road", id++, getSubImage(9, 0, sprites))); 
    }



    public static BufferedImage getPathTileImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(TilesManager.class.getClassLoader().getResourceAsStream("Ground/spriteatlas.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

   
    public void getTowerImage() {
        int id = 0;
        for (int i = 0; i < nameTower.length; i++) {
            towerSprites = getPathTowerImage(nameTower[i]); 
            towers.add(new Tile(nameTower[i], id++, getSubImage(0, 0, towerSprites))); 
        }
    }


    public static BufferedImage getPathTowerImage(String nameTower) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(TilesManager.class.getClassLoader().getResourceAsStream(nameTower));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    private BufferedImage getSubImage(int x, int y, BufferedImage image) {
        if (image != null) {
            return image.getSubimage(x * 32, y * 32, 32, 32); 
        } else {
            System.out.println("Image does not exist! Check and fix.");
            return null;
        }
    }

   
    public BufferedImage getID(int id) {
        return tiles.get(id).getImage();
    }
}
