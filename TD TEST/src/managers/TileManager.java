package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.ImgFix;
import helpz.LoadPathImage;
import objects.Tile;
import static helpz.Constants.Tiles.*;

public class TileManager {

	public Tile GRASS, WATER,ROAD,CORNER_LD,CORNER_LU,CORNER_RU,CORNER_RD,EDGE;

	
	//OLD
	private BufferedImage atlas;
	//NEW
	private BufferedImage NewAtlas;
	//Main ArrayList
	public ArrayList<Tile> tiles = new ArrayList<>();
	//Sub-ArrayList

	

	public TileManager() {
		loadAtalas();
		loadNewAtalas();
		createTiles();
	}

	private void createTiles() {

		int id = 0;
		//Grass
		tiles.add(GRASS = new Tile(getSpritev2(2, 9), id++, GRASS_TILE)); //0
		tiles.add(GRASS = new Tile(getSpritev2(5, 5), id++, GRASS_TILE)); //1
		//ROAD
		tiles.add(ROAD = new Tile(getSpritev2(3, 1) ,id++, ROAD_TILE ));//2
		tiles.add(ROAD = new Tile(getSpritev2(1, 3) ,id++, ROAD_TILE ));//3
		tiles.add(ROAD = new Tile(getSpritev2(3, 3) ,id++, ROAD_TILE ));//4
		tiles.add(ROAD = new Tile(ImgFix.getRotImg(getSpritev2(3,3),180),id++,ROAD_TILE));//5
		tiles.add(ROAD = new Tile(ImgFix.getRotImg(getSpritev2(3,3),270),id++,ROAD_TILE));//6
		tiles.add(ROAD = new Tile(ImgFix.imageFlip(getSpritev2(3,3)),id++,ROAD_TILE));//7
		tiles.add(ROAD = new Tile(getSpritev2(2,3),id++,ROAD_TILE));//8
		//EDGE
		tiles.add(EDGE = new Tile(getSpritev2(3,5), id++, GRASS_TILE));//9
		//WATER
		tiles.add(WATER = new Tile(getAniSprites(0, 0), id++, WATER_TILE)); //10



	}


	private void loadAtalas() {
		atlas = LoadPathImage.getSpriteAtlas();
	}
	
	private void loadNewAtalas() {
		NewAtlas = LoadPathImage.getAtlasV2();
	}

	public Tile getTile(int id) {
		return tiles.get(id);
	}

	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}

	public BufferedImage getAniSprite(int id, int animationIndex) {

		return tiles.get(id).getSprite(animationIndex);
	}

	private BufferedImage[] getAniSprites(int xCord, int yCord) {
		BufferedImage[] arr = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			arr[i] = getSprite(xCord + i, yCord);
		}
		return arr;
	}

	private BufferedImage getSprite(int xCord, int yCord) {
		return atlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
	}
	
	private BufferedImage getSpritev2(int xCord, int yCord) {
		return NewAtlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
	}

	public boolean isSpriteAnimation(int spriteID) {

		return tiles.get(spriteID).isAnimation();
	}


}
