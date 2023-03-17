package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This is a Sound class for dealing with all the sound files and attributes.
 */
public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/assets/Sound/themesong.wav");
        soundURL[1] = getClass().getResource("/assets/Sound/doorunlock.wav");
        soundURL[2] = getClass().getResource("/assets/Sound/gamelost.wav");
        soundURL[3] = getClass().getResource("/assets/Sound/gamewon.wav");
        soundURL[4] = getClass().getResource("/assets/Sound/gotcheese.wav");
        soundURL[5] = getClass().getResource("/assets/Sound/gotsteak.wav");
        soundURL[6] = getClass().getResource("/assets/Sound/mousecaught.wav");
        soundURL[7] = getClass().getResource("/assets/Sound/mousetrap.wav");
    }

    /**
     * This is a method for setting up the sound files to the Clip objects.
     *
     * @param i index for accessing the URL soundURL array.
     */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
        }
    }

    /**
     * Method for playing the clip.
     */
    public void play() {
        clip.start();
    }

    /**
     * Method for looping the clip.
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Method for stopping the clip.
     */
    public void stop() {
        clip.stop();
    }

}
