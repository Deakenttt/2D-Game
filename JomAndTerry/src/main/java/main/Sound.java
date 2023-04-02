package main;

import javax.sound.sampled.*;

/**
 * This is a Sound class for dealing with all the sound files and attributes.
 */
public class Sound {

    private Clip clip;
    private final String[] soundFiles = {
            "/assets/Sound/themesong.wav",
            "/assets/Sound/doorunlock.wav",
            "/assets/Sound/gamelost.wav",
            "/assets/Sound/gamewon.wav",
            "/assets/Sound/gotcheese.wav",
            "/assets/Sound/gotsteak.wav",
            "/assets/Sound/mousecaught.wav",
            "/assets/Sound/mousetrap.wav"
    };

    /**
     * This is a method for setting up the sound files to the Clip objects.
     *
     * @param i index for accessing the soundFiles array.
     */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(soundFiles[i]));
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            e.printStackTrace();
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
