package Scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Game;
import MapLevel.Level1;
import MapLevel.Level2;
import Tiles.TilesManager;
import static Main.GameStates.PLAYING;
public class Playing extends GameScenes implements SceneMethod{
	public int[][] lv1,lv2,lv3,lv4;
	private TilesManager tilesManager;
	public Playing(Game game) {
		super(game);	
		MapLoader();
		tilesManager = new TilesManager();

	}
	


	@Override
	public void render(Graphics g) {
		for(int i = 0; i < lv1.length; i++) {
			for(int j = 0; j < lv1[i].length; j++) {
				int ID = lv1[i][j];
				g.drawImage(getImageFromID(ID),j*32,i*32,null);
			}
		}
		
	}
	
	private void MapLoader() {
		lv1 = Level1.getLevel1Data();
		lv2 = Level2.getLevel2Data();
		//...........
	}
	
	private BufferedImage getImageFromID(int ID) {
		return tilesManager.getID(ID);
	}

	@Override
	public void mouseClicked(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
