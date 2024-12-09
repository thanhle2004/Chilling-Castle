package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.ImgFix;
import helpz.LoadPathImage;
import objects.Tile;
import static helpz.Constants.Tiles.*;

public class TileManager {

	public Tile GRASS, WATER, ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R, BL_WATER_CORNER, TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER, T_WATER, R_WATER, B_WATER,
			L_WATER, TL_ISLE, TR_ISLE, BR_ISLE, BL_ISLE, N_ROAD, N_CORNER;

	
	//OLD
	private BufferedImage atlas;
	//NEW
	private BufferedImage NewAtlas;
	//Main ArrayList
	public ArrayList<Tile> tiles = new ArrayList<>();
	//Sub-ArrayList
	public ArrayList<Tile> roadsS = new ArrayList<>();
	public ArrayList<Tile> roadsC = new ArrayList<>();
	public ArrayList<Tile> corners = new ArrayList<>();
	public ArrayList<Tile> beaches = new ArrayList<>();
	public ArrayList<Tile> islands = new ArrayList<>();
	

	public TileManager() {
		loadAtalas();
		loadNewAtalas();
		createTiles();
	}

	private void createTiles() {

		int id = 0;
		//OLD
		tiles.add(GRASS = new Tile(getSprite(9, 0), id++, GRASS_TILE)); //0
		tiles.add(WATER = new Tile(getAniSprites(0, 0), id++, WATER_TILE)); //1
		
		//NEW
		tiles.add(N_ROAD = new Tile(getRoadSprite(8, 4) ,id++, ROAD_TILE ));//2
		tiles.add(N_CORNER = new Tile(getRoadSprite(8, 0) ,id++, ROAD_TILE ));//3
		
		roadsS.add(ROAD_LR = new Tile(getSprite(8, 0), id++, ROAD_TILE)); 
		roadsS.add(ROAD_TB = new Tile(ImgFix.getRotImg(getSprite(8, 0), 90), id++, ROAD_TILE)); 

		roadsC.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, ROAD_TILE));
		roadsC.add(ROAD_L_TO_B = new Tile(ImgFix.getRotImg(getSprite(7, 0), 90), id++, ROAD_TILE));
		roadsC.add(ROAD_L_TO_T = new Tile(ImgFix.getRotImg(getSprite(7, 0), 180), id++, ROAD_TILE));
		roadsC.add(ROAD_T_TO_R = new Tile(ImgFix.getRotImg(getSprite(7, 0), 270), id++, ROAD_TILE));

		corners.add(BL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(5, 0), 0), id++, WATER_TILE));
		corners.add(TL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(5, 0), 90), id++, WATER_TILE));
		corners.add(TR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(5, 0), 180), id++, WATER_TILE));
		corners.add(BR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(5, 0), 270), id++, WATER_TILE));

		beaches.add(T_WATER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(6, 0), 0), id++, WATER_TILE));
		beaches.add(R_WATER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(6, 0), 90), id++, WATER_TILE));
		beaches.add(B_WATER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(6, 0), 180), id++, WATER_TILE));
		beaches.add(L_WATER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(6, 0), 270), id++, WATER_TILE));

		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(4, 0), 0), id++, WATER_TILE));
		islands.add(TR_ISLE = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(4, 0), 90), id++, WATER_TILE));
		islands.add(BR_ISLE = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(4, 0), 180), id++, WATER_TILE));
		islands.add(BL_ISLE = new Tile(ImgFix.getBuildRotImg(getAniSprites(0, 0), getSprite(4, 0), 270), id++, WATER_TILE));

		tiles.addAll(roadsS);
		tiles.addAll(roadsC);
		tiles.addAll(corners);
		tiles.addAll(beaches);
		tiles.addAll(islands);
	}

	
	


	private void loadAtalas() {
		atlas = LoadPathImage.getSpriteAtlas();
	}
	
	private void loadNewAtalas() {
		NewAtlas = LoadPathImage.getRoadAtlas();
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
	
	private BufferedImage getRoadSprite(int xCord, int yCord) {
		return NewAtlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
	}

	public boolean isSpriteAnimation(int spriteID) {
		return tiles.get(spriteID).isAnimation();
	}


}
