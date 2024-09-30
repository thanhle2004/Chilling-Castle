package Tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public  class TilesManager {
	public Tile grassTile, waterTile, roadTile;
	public BufferedImage sprites;
	public ArrayList<Tile> tiles = new ArrayList<>();
	
	public TilesManager() {
		getImage();
	}

	public void getImage() {
		sprites = getPathImage();
		int id = 0;
		tiles.add(grassTile = new Tile("Grass",id++ , getSubImag(8, 1)));
		tiles.add(waterTile = new Tile("Grass",id++ , getSubImag(0, 0)));
		tiles.add(roadTile = new Tile("Grass",id++ , getSubImag(9, 0)));
		
	}
	
	public static BufferedImage getPathImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(TilesManager.class.getClassLoader().getResourceAsStream("Ground/spriteatlas.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
		
	}
	
	private BufferedImage getSubImag(int x, int y) {
		if(sprites != null) {
			return sprites.getSubimage(x*32, y*32, 32, 32);
		} else {
			System.out.println("The sprites are not exist!. Check and fix");
			return null;
		}
	}
	
	public BufferedImage getID(int id) {
		return tiles.get(id).getImage();
	}
	
	

}
