package main;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.Clip;

public class SoundTest {

    Sound sound;


    @Before
    public void setUp() {
        sound = new Sound();
    }

    @Test
    public void testSetFile() {
        sound.setFile(1);

    }

    @Test
    public void testPlay() {
        sound.setFile(1);
        sound.play();
    }

    @Test
    public void testLoop() {
        sound.setFile(1);
        sound.loop();
    }

}