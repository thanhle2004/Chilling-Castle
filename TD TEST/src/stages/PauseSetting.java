package stages;

import main.Game;
import managers.SoundEffectManager;
import objects.SoundEffect;
import scenes.Settings;
import setting.SettingBoard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class PauseSetting {

    private int x, y, width, height;

    private BufferedImage img, MusicStatus, SoundStatus, onButton, offButton;
    private BufferedImage sliderHandle, slider1, slider2, HomeButton, ContinueButton, ReplayButton;

    private SettingBoard bMusic, bSound, bMusicHandle, bSoundHandle;
    public SettingBoard bHome, bContinue, bReplay;


    private Settings settings;
    private SoundEffect soundEffect;
    private SoundEffectManager soundEffectManager;

    public int musicX, musicY;
    public int soundX, soundY;

    private Stage1 stage1;

    public PauseSetting(int x, int y, int width, int height, Settings settings) {

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

        musicX = settings.getMusicX();
        musicY = settings.getMusicY();
        soundX = settings.getSoundX();
        soundY = settings.getSoundY();

        bMusicHandle = new SettingBoard(null, musicX, musicY, 20, 20, sliderHandle);

        bSoundHandle = new SettingBoard(null, soundX, soundY, 20, 20, sliderHandle);

        bHome = new SettingBoard(null, 90, 380, 130, 75, HomeButton);
        bReplay = new SettingBoard(null, 255, 380, 130, 75, ReplayButton);
        bContinue = new SettingBoard(null, 420, 380, 130, 75, ContinueButton);
    }

    private void importImage() {
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/InGameSetting.png"));
            onButton = ImageIO.read(getClass().getResourceAsStream("/onButton.png"));
            offButton = ImageIO.read(getClass().getResourceAsStream("/offButton.png"));

            slider1 = ImageIO.read(getClass().getResourceAsStream("/slider1.png"));
            slider2 = ImageIO.read(getClass().getResourceAsStream("/slider2.png"));
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
        bMusic = new SettingBoard(null, 275, 141, 60, 30, MusicStatus);
        bSound = new SettingBoard(null, 275, 263, 60, 30, SoundStatus);
    }

    private void drawButtons(Graphics g) {
        MusicStatus = settings.getMusicStatus();
        SoundStatus = settings.getSoundStatus();

        bMusic.setImg(MusicStatus);
        bMusic.draw(g);

        if(MusicStatus.equals(onButton)) {
            drawMusicSlider1(g);
            bMusicHandle.setPosX(settings.getMusicX());
            bMusicHandle.setPosY(settings.getMusicY());
            drawMusicHandle(g);

        }

        bSound.setImg(SoundStatus);
        bSound.draw(g);

        if(SoundStatus.equals(onButton)) {
            drawSoundSlider1(g);
            bSoundHandle.setPosX(settings.getSoundX());
            bSoundHandle.setPosY(settings.getSoundY());
            drawSoundHandle(g);
        }

        bHome.draw(g);
        bContinue.draw(g);
        bReplay.draw(g);
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

    public void mouseClicked(int x, int y) {
        if(bHome.getBounds().contains(x, y)) {
            SetGameState(MENU);
        }

        if(bMusic.getBounds().contains(x, y)) {

            if(MusicStatus == onButton) {
                musicX = 280;
                musicY = 190;
                MusicStatus = offButton;
                soundEffect.playEffect(1);
                settings.offMusicBackground();
                settings.setMusicX(musicX);
                settings.setMusicY(musicY);
                settings.setMusicStatus(MusicStatus);
            } else {
                musicX = 500;
                musicY = 190;
                MusicStatus = onButton;
                soundEffect.playEffect(1);
                settings.onMusicBackground();
                settings.setMusicX(musicX);
                settings.setMusicY(musicY);
                settings.setMusicStatus(MusicStatus);
            }
        }

        if(bSound.getBounds().contains(x, y)) {

            if(SoundStatus == onButton) {
                SoundStatus = offButton;
                soundX = 280;
                soundY = 312;

                SoundEffectManager.muteAllSounds();
                settings.setSoundX(soundX);
                settings.setSoundY(soundY);
                settings.setSoundStatus(SoundStatus);
            } else {
                SoundStatus = onButton;
                soundX = 500;
                soundY = 312;

                SoundEffectManager.unmuteAllSounds();
                soundEffect.playEffect(1);
                settings.setSoundX(soundX);
                settings.setSoundY(soundY);
                settings.setSoundStatus(SoundStatus);
            }

        }

    }

    public void mouseMoved(int x, int y) {

    }

    public void mousePressed(int x, int y) {

        if(bMusicHandle.getBounds().contains(x, y)) {
            bMusicHandle.setMousePressed(true);
        }

    }

    public void mouseDragged(int x, int y) {
        if (x <= 500 && x >= 279 && y >= 190 && y <= 220) {
            musicX = x;
            musicY = 190;

            settings.setMusicX(musicX);

        }

        if (x <= 500 && x >= 279 && y >= 310 && y <= 330) {
            soundX = x;
            soundY = 312;

            settings.setSoundX(soundX);
        }
    }

    public void mouseReleased(int x, int y) {
        resetButtons();

    }

    private void resetButtons() {

    }

}
