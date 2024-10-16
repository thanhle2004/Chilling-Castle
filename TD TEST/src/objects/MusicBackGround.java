package objects;

import main.Sound;

public class MusicBackGround {
    private Sound sound;

    public MusicBackGround() {
        sound = new Sound();
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
    }

    public void play() {
        sound.play();
    }

    public void stop() {
        sound.stop();
    }

    public void setVolume(float volume) {
        sound.setVolume(volume);
    }
}
