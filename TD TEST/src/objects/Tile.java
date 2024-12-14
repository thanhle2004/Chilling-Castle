package objects;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage[] sprite;
	private int id, tileType;
	private String nameString;
	private BufferedImage image;
	
	public Tile(BufferedImage sprite, int id, int tileType) {
		this.sprite = new BufferedImage[1];
		this.sprite[0] = sprite;
		this.id = id;
		this.tileType = tileType;

	}

	public Tile(BufferedImage[] sprite, int id, int tileType) {
		this.sprite = sprite;
		this.id = id;
		this.tileType = tileType;

	}

//	public Tile(String name, int ID, BufferedImage image) {
//		this.nameString = name;
//		this.id = ID;
//		this.image = image;
//	}

	public int getTileType() {
		return tileType;
	}

	public BufferedImage getSprite(int animationIndex) {
		return sprite[animationIndex];
	}

	public BufferedImage getSprite() {
		return sprite[0];
	}

	public boolean isAnimation() {

		return sprite.length > 1;
	}

//	public int getId() {
//		return id;
//	}

}
