package ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import main.GamePanel;

public class ScreenSetUITest {
    GamePanel gp = new GamePanel();
    private UI UI = new UI(gp);
    private GameUI gameUI = new GameUI(gp);
    private GamePlayUI gamePlayUI = new GamePlayUI(gp);
                

    Font font = new Font("Arial", Font.PLAIN, 12);
    String text;

    @Mock  Graphics2D g2;
    FontMetrics metrics;
    FontMetrics fontMetrics;

    @Before
    public void setUp() 
    {   
        gp.setUpGame();
        font = new Font("Arial", Font.PLAIN, 12);
        MockitoAnnotations.openMocks(this);
        fontMetrics = mock(FontMetrics.class);
        when(g2.getFontMetrics()).thenReturn(fontMetrics);  
        g2.setFont(font);

    }

    @Test
    public void testUI() {
        UI.draw(g2);
        verify(g2, times(0)).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testGameUI() {
        gameUI.draw(g2);
        verify(g2, times(4)).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testGamePlayUIWithMessage() {
        gamePlayUI.setMessage("cheese", "cheese");
        gamePlayUI.draw(g2);
        assertTrue(gamePlayUI.msgOn);
        verify(g2, times(5)).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testGamePlayUIWithoutMessage() {
        gamePlayUI.draw(g2);
        assertFalse(gamePlayUI.msgOn);
        verify(g2, times(4)).drawString(anyString(), anyInt(), anyInt());
    }

}
