package scenes;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Game;
import main.Sound;
import managers.SoundEffectManager;
import objects.SoundEffect;
import ui.SettingButton;
import ui.SettingBoardUI;

import static main.GameStates.*;

public class Settings extends GameScene implements SceneMethods {

	private SettingButton bMenu, bMusic, bSound, bMusicHandle, bSoundHandle;

	private Sound sound;

	private Menu menu;

	private SoundEffect soundEffect;

	private BufferedImage background, menuButton, MusicStatus, SoundStatus, onButton, offButton;
	private BufferedImage sliderHandle, slider1, slider2;

	public int musicX = 500, musicY = 190;
	public int soundX = 500, soundY = 312;

	private SettingBoardUI pauseSetting;

	public Settings(Game game, Menu menu) {
		super(game);
		this.menu = menu;
		sound = new Sound();
		soundEffect = new SoundEffect();
		importImg();
		initButtons();

		sound.stop();

		bMusicHandle = new SettingButton(null, musicX, musicY, 20, 20, sliderHandle);

		bSoundHandle = new SettingButton(null, soundX, soundY, 20, 20, sliderHandle);


	}

	private void initButtons() {
		bMenu = new SettingButton("Menu", 530, 40, 65, 65, menuButton);
		bMusic = new SettingButton(null, 275, 141, 60, 30, MusicStatus);
		bSound = new SettingButton(null, 275, 263, 60, 30, SoundStatus);
	}


	@Override
	public void render(Graphics g) {

		drawBackground(g);

		drawButtons(g);

	}


	private void drawBackground(Graphics g) {
		g.drawImage(background, 0, 0, game);
	}

	private void drawButtons(Graphics g) {

		bMenu.draw(g);

		bMusic.setImg(MusicStatus);
		bMusic.draw(g);

		if(MusicStatus == onButton) {
			drawMusicSlider1(g);
			bMusicHandle.setPosX(musicX);
			bMusicHandle.setPosY(musicY);
			onMusicBackground();
			drawMusicHandle(g);
		} else {
			offMusicBackground();
		}

		bSound.setImg(SoundStatus);
		bSound.draw(g);

		if(SoundStatus == onButton) {
			drawSoundSlider1(g);
			bSoundHandle.setPosX(soundX);
			bSoundHandle.setPosY(soundY);
			SoundEffectManager.unmuteAllSounds();
			drawSoundHandle(g);
		} else {
			SoundEffectManager.muteAllSounds();
		}
	}

	public void drawMusicSlider1(Graphics g) {
		g.drawImage(slider1, 273, 185, 250, 30, game);
	}

	public void drawSoundSlider1(Graphics g) {
		g.drawImage(slider1, 273, 307, 250, 30, game);
	}

	public void drawMusicHandle(Graphics g) {
		bMusicHandle.draw(g);
	}

	public void drawSoundHandle (Graphics g) {
		bSoundHandle.draw(g);
	}

	private void importImg() {

		try {

			background = ImageIO.read(getClass().getResourceAsStream("/SettingBoard.png"));
			menuButton = ImageIO.read(getClass().getResourceAsStream("/bMenu.png"));
			onButton = ImageIO.read(getClass().getResourceAsStream("/onButton.png"));
			offButton = ImageIO.read(getClass().getResourceAsStream("/offButton.png"));

			slider1 = ImageIO.read(getClass().getResourceAsStream("/slider1.png"));
			slider2 = ImageIO.read(getClass().getResourceAsStream("/slider2.png"));
			sliderHandle = ImageIO.read(getClass().getResourceAsStream("/sliderHandle.png"));


		} catch (Exception e) {
			e.printStackTrace();
		}

		MusicStatus = onButton;
		SoundStatus = onButton;

	}

	private float calculateVolume(int i) {
		float volume = (float)(i - 280) / (500 - 280);
		volume = Math.max(0.0f, Math.min(volume, 1.0f)); // Chắc chắn rằng âm lượng nằm trong khoảng [0, 1]
		return volume;
	}


	@Override
	public void mouseClicked(int x, int y) {

		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
			soundEffect.playEffect(1);;
		}

		if(bMusic.getBounds().contains(x, y)) {

			if(MusicStatus == onButton) {
				musicX = 280;
				musicY = 190;
				MusicStatus = offButton;
				soundEffect.playEffect(1);

			} else {
				musicX = 500;
				musicY = 190;
				MusicStatus = onButton;
				soundEffect.playEffect(1);

			}
		}

		if(bSound.getBounds().contains(x, y)) {

			if(SoundStatus == onButton) {
				SoundStatus = offButton;
				soundX = 280;
				soundY = 312;

			} else {
				SoundStatus = onButton;
				soundX = 500;
				soundY = 312;

				soundEffect.playEffect(1);
			}

		}

	}

	@Override
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);

	}

	@Override
	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMousePressed(true);
		}
		if(bMusicHandle.getBounds().contains(x, y)) {
			bMusicHandle.setMousePressed(true);
		}

	}

	@Override
	public void mouseDragged(int x, int y) {
		if (x <= 500 && x >= 279 && y >= 190 && y <= 220) {
			musicX = x;
			musicY = 190;

			float musicVolume = calculateVolume(musicX);
			menu.setMusicVolume(musicVolume);

			int volumePercent = (int) (musicVolume * 100);
			System.out.println("Music Volume: " + volumePercent + "%");
		}

		if (x <= 500 && x >= 279 && y >= 310 && y <= 330) {
			soundX = x;
			soundY = 312;

			float effectVolume = calculateVolume(soundX);


			int volumePercent = (int) (effectVolume * 100);
			System.out.println("Sound Effect Volume: " + volumePercent + "%");



		}
	}

	@Override
	public void keyPressed(int key) {

	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();

	}

	private void resetButtons() {
		bMenu.resetBooleans();

	}

	//Getter
	public int getMusicX() {
		return musicX;
	}

	public int getMusicY() {
		return musicY;
	}

	public int getSoundX() {
		return soundX;
	}

	public int getSoundY() {
		return soundY;
	}

	public BufferedImage getMusicStatus() {
		return MusicStatus;
	}

	public BufferedImage getSoundStatus() {
		return SoundStatus;
	}

	//Setter
	public void setMusicX(int musicX) {
		this.musicX = musicX;
	}

	public void setMusicY(int musicY) {
		this.musicY = musicY;
	}

	public void setSoundX(int soundX) {
		this.soundX = soundX;
	}

	public void setSoundY(int soundY) {
		this.soundY = soundY;
	}

	public void setMusicStatus(BufferedImage img) {
		this.MusicStatus = img;
	}

	public void setSoundStatus(BufferedImage img) {
		this.SoundStatus = img;
	}

	public void offMusicBackground() {
		menu.setMusicBackgroundStop();
	}

	public void onMusicBackground() {
		menu.setMusicBackgroundPlay();
	}

	public BufferedImage getOnStatus() {
		return onButton;
	}

	public BufferedImage getOffStatus() {
		return offButton;
	}

}
