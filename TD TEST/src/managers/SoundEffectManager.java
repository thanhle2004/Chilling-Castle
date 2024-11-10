package managers;
import objects.SoundEffect;
import java.util.ArrayList;
import java.util.List;

public class SoundEffectManager {

    private static boolean soundEnabled = true;
    private static List<SoundEffect> soundEffectsList = new ArrayList<>();


    public static void muteAllSounds() {
        soundEnabled = false;
        notifyAllSounds();
    }

    // unmute sound of all classes
    public static void unmuteAllSounds() {
        soundEnabled = true;
        notifyAllSounds();
    }


    public static void registerSound(SoundEffect soundEffect) {
        soundEffectsList.add(soundEffect);
    }


    private static void notifyAllSounds() {
        for (SoundEffect soundEffect : soundEffectsList) {
            soundEffect.updateSoundState(soundEnabled);
        }
    }



}
