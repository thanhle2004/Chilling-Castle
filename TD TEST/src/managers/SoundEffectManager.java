package managers;

import main.Sound;
import objects.SoundEffect;

import java.util.ArrayList;
import java.util.List;

public class SoundEffectManager {

    private static boolean soundEnabled = true;
    private static List<SoundEffect> soundEffectsList = new ArrayList<>();

    // mute sound of all classes
    public static void muteAllSounds() {
        soundEnabled = false;
//        System.out.println("All sounds have been muted.");
        notifyAllSounds();  // notify to all class
    }

    // unmute sound of all classes
    public static void unmuteAllSounds() {
        soundEnabled = true;
//        System.out.println("All sounds have been unmuted.");
        notifyAllSounds();  // notify to all class
    }

    //register sound effect
    public static void registerSound(SoundEffect soundEffect) {
        soundEffectsList.add(soundEffect);
    }

    // Phương thức thông báo cho tất cả các object
    private static void notifyAllSounds() {
        for (SoundEffect soundEffect : soundEffectsList) {
            soundEffect.updateSoundState(soundEnabled);
        }
    }

}
