package Tiles;

import java.awt.image.BufferedImage;

public class Tile {
	
	private String nameString;
	private int ID;
	private BufferedImage image;
	
	public Tile(String name, int ID, BufferedImage image) {
		this.nameString = name;
		this.ID = ID;
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	

}
