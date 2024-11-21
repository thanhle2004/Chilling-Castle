package objects;

import main.Sound;
import managers.SoundEffectManager;


public class SoundEffect {

    private Sound sound;
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

    public void updateSoundState(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public Sound getSoundStop() {
        return sound;
    }
}
