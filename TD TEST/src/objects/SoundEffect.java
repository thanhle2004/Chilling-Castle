package objects;

import main.Sound;
import managers.SoundEffectManager;
import scenes.Settings;

public class SoundEffect {

    private Sound sound;
    private Settings settings;

    private int status;
    private boolean soundEnabled;

    public SoundEffect() {
        sound = new Sound();

        this.soundEnabled = true;
        SoundEffectManager.registerSound(this);
    }

    public void playEffect(int i) {

        if(soundEnabled) {
            sound.setFile(i);
            sound.play();
        } else {
            sound.stop();
        }

    }

    public void stop() {
        sound.stop();
    }

    public void setVolume(float volume) {
        sound.setVolume(volume); // Giả sử bạn đã thêm phương thức setVolume trong lớp Sound
    }

    public void updateSoundState(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }
}
