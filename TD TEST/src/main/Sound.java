package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.net.URL;

public class Sound {

    private Clip Clip;
    URL soundURL[] = new URL[30];
    FloatControl musicVolumeControl, effectVolumeControl;

    public Sound() {

        soundURL[0] = getClass().getResource("/soundLobby.wav");
        System.out.println("Accessed " + soundURL[0]);
        soundURL[1] = getClass().getResource("/soundPress.wav");
        soundURL[2] = getClass().getResource("/soundWarning.wav");

    }

    public void setFile(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            Clip = AudioSystem.getClip();
            Clip.open(ais);
            musicVolumeControl = (FloatControl) Clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void play() {

        Clip.start();
    }


    public void loop() {

        Clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (Clip != null) {
            Clip.stop();
        }
    }

    public void setVolume(float volume) {
        if (musicVolumeControl != null) {
            float min = musicVolumeControl.getMinimum();
            float max = musicVolumeControl.getMaximum();

            float gain = min + (max - min) * volume;
            musicVolumeControl.setValue(gain);
        }
    }




}
