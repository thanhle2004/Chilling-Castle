package helpz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


import javax.imageio.ImageIO;




public class LoadPathImage {

	public static BufferedImage getSpriteAtlas() {
		BufferedImage img = null;
		InputStream is = LoadPathImage.class.getClassLoader().getResourceAsStream("spriteatlas.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
    
    
	public static BufferedImage getRoadAtlas() {
		BufferedImage imgs = null;
		InputStream is = LoadPathImage.class.getClassLoader().getResourceAsStream("terrain_tiles_v2.png");

		try {
			imgs = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgs;
	}
	
	public static BufferedImage getOSTERPath() {
		BufferedImage img = null;
		InputStream is = LoadPathImage.class.getClassLoader().getResourceAsStream("OsterWalk.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage getSlimePath() {
		BufferedImage img = null;
		InputStream is = LoadPathImage.class.getClassLoader().getResourceAsStream("U_Walk.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage getPinkPath() {
		BufferedImage img = null;
		InputStream is = LoadPathImage.class.getClassLoader().getResourceAsStream("Pink_Monster_Walk_6.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage getDudePath() {
		BufferedImage img = null;
		InputStream is = LoadPathImage.class.getClassLoader().getResourceAsStream("Dude_Monster_Walk_6.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static BufferedImage getTowerPath() {
		BufferedImage img = null;
		InputStream is = LoadPathImage.class.getClassLoader().getResourceAsStream("S_Attack.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static BufferedImage getLifeHeart() {
		BufferedImage img = null;
		InputStream is = LoadPathImage.class.getClassLoader().getResourceAsStream("hearts16x16.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static BufferedImage getTestHouse() {
		BufferedImage img = null;
		InputStream is = LoadPathImage.class.getClassLoader().getResourceAsStream("TestHouse.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}




}
