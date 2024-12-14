package helpz;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImgFix {

	// Rotate
	public static BufferedImage getRotImg(BufferedImage img, int rotAngle) {

		int w = img.getWidth();
		int h = img.getHeight();

		BufferedImage newImg = new BufferedImage(w, h, img.getType());
		Graphics2D g2d = newImg.createGraphics();

		g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();

		return newImg;

	}

	// Rotate Second img only + animation
	public static BufferedImage[] getBuildRotImg(BufferedImage[] imgs, BufferedImage secondImage, int rotAngle) {
		int w = imgs[0].getWidth();
		int h = imgs[0].getHeight();

		BufferedImage[] arr = new BufferedImage[imgs.length];

		for (int i = 0; i < imgs.length; i++) {
			BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
			Graphics2D g2d = newImg.createGraphics();

			g2d.drawImage(imgs[i], 0, 0, null);
			g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
			g2d.drawImage(secondImage, 0, 0, null);
			g2d.dispose();

			arr[i] = newImg;
		}

		return arr;
	}

	public static BufferedImage imageFlip(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();


		BufferedImage flippedImage = new BufferedImage(width, height, image.getType());

		Graphics2D g = flippedImage.createGraphics();
		AffineTransform transform = AffineTransform.getScaleInstance(-1, 1); // Lật ngang
		transform.translate(-width, 0);
		g.drawImage(image, transform, null);
		g.dispose();

		return flippedImage;
	}

}
