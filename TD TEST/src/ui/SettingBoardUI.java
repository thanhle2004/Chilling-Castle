package ui;

import managers.SoundEffectManager;
import objects.SoundEffect;
import scenes.SceneMethods;
import scenes.Settings;
import stages.Stage1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class SettingBoardUI {

    private int x, y, width, height;

    private BufferedImage img, MusicStatus, SoundStatus, onButton, offButton;
    private BufferedImage sliderHandle, slider1, HomeButton, ContinueButton, ReplayButton;

    private SettingButton bMusic, bSound, bMusicHandle, bSoundHandle;
    public SettingButton bHome, bContinue, bReplay;


    private Settings settings;
    private SoundEffect soundEffect;

    public int musicX, musicY;
    public int soundX, soundY;

    public SettingBoardUI(int x, int y, int width, int height, Settings settings) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.settings = settings;
        this.MusicStatus = settings.getMusicStatus();
        this.SoundStatus = settings.getSoundStatus();

        soundEffect = new SoundEffect();

        importImage();
        initButtons();

        this.musicX = settings.getMusicX();
        this.musicY = settings.getMusicY();
        this.soundX = settings.getSoundX();
        this.soundY = settings.getSoundY();

        bMusicHandle = new SettingButton(null, musicX, musicY, 20, 20, sliderHandle);
        bSoundHandle = new SettingButton(null, soundX, soundY, 20, 20, sliderHandle);
        bHome = new SettingButton(null, 90, 380, 130, 75, HomeButton);
        bReplay = new SettingButton(null, 255, 380, 130, 75, ReplayButton);
        bContinue = new SettingButton(null, 420, 380, 130, 75, ContinueButton);
    }

    private void importImage() {
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/InGameSetting.png"));
//            onButton = ImageIO.read(getClass().getResourceAsStream("/onButton.png"));
//            offButton = ImageIO.read(getClass().getResourceAsStream("/offButton.png"));
            onButton = settings.onButton;
            offButton = settings.offButton;

            slider1 = ImageIO.read(getClass().getResourceAsStream("/slider1.png"));
//            slider2 = ImageIO.read(getClass().getResourceAsStream("/slider2.png"));
            sliderHandle = ImageIO.read(getClass().getResourceAsStream("/sliderHandle.png"));

            HomeButton = ImageIO.read(getClass().getResourceAsStream("/HomeButton.png"));
            ContinueButton = ImageIO.read(getClass().getResourceAsStream("/ContinueButton.png"));
            ReplayButton = ImageIO.read(getClass().getResourceAsStream("/ReplayButton.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void drawSettings(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
        drawButtons(g);
    }

    private void initButtons() {
        bMusic = new SettingButton(null, 275, 141, 60, 30, MusicStatus);
        bSound = new SettingButton(null, 275, 263, 60, 30, SoundStatus);
    }

    private void drawButtons(Graphics g) {

        bMusic.setImg(settings.getMusicStatus());
        bMusic.draw(g);

        if(settings.getMusicStatus() == settings.getOnButton()) {
            drawMusicSlider1(g);
            bMusicHandle.setPosX(settings.getMusicX());
            bMusicHandle.setPosY(settings.getMusicY());
            drawMusicHandle(g);
        }

        bSound.setImg(settings.getSoundStatus());
        bSound.draw(g);

        if(settings.getSoundStatus() == settings.getOnButton()) {
            drawSoundSlider1(g);
            bSoundHandle.setPosX(settings.getSoundX());
            bSoundHandle.setPosY(settings.getSoundY());
            drawSoundHandle(g);
        }

        bHome.draw(g);
        bContinue.draw(g);
        bReplay.draw(g);
    }



    public void mouseClicked(int x, int y) {
        if(bHome.getBounds().contains(x, y)) {
            SetGameState(MENU);
        }

        if(bMusic.getBounds().contains(x, y)) {
			if(settings.getMusicStatus() == settings.getOnButton()) {
				musicX = 280;
				musicY = 190;
				settings.setMusicStatus(offButton);
				soundEffect.playEffect(1);
                settings.offMusicBackground();


			} else {
				musicX = 500;
				musicY = 190;
                settings.setMusicStatus(onButton);
				soundEffect.playEffect(1);
                settings.onMusicBackground();
			}
		}

		if(bSound.getBounds().contains(x, y)) {
			if(settings.getSoundStatus() == settings.getOnButton()) {
				settings.setSoundStatus(offButton);
				soundX = 280;
				soundY = 312;
                SoundEffectManager.muteAllSounds();

			} else {
				settings.setSoundStatus(onButton);
				soundX = 500;
				soundY = 312;
				soundEffect.playEffect(1);
			}

		}

    }

    public void mousePressed(int x, int y) {
        if(bMusicHandle.getBounds().contains(x, y)) {
            bMusicHandle.setMousePressed(true);
        }
    }

    public void mouseDragged(int x, int y) {
        settings.mouseDragged(x, y);
    }

    public void drawMusicSlider1(Graphics g) {
        g.drawImage(slider1, 273, 185, 250, 30, null);
    }

    public void drawSoundSlider1(Graphics g) {
        g.drawImage(slider1, 273, 307, 250, 30, null);
    }

    public void drawMusicHandle(Graphics g) {
        bMusicHandle.draw(g);
    }

    public void drawSoundHandle (Graphics g) {
        bSoundHandle.draw(g);
    }


}
