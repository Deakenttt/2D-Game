package ui;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import main.GamePanel;

public class resetMessageTest {
    GamePlayUI ui;
    FontMetrics fontMetrics;
    Font font;
    String test;
    // FontMetrics
    @Mock
    Graphics2D g2;   

    @Before
    public void setUp() 
    {
        test = "Test message";
        font = new Font("Arial", Font.PLAIN, 12);
        MockitoAnnotations.openMocks(this);
        ui = new GamePlayUI(new GamePanel());
        fontMetrics = mock(FontMetrics.class);
        when(g2.getFontMetrics()).thenReturn(fontMetrics);
        when(fontMetrics.getStringBounds(test, g2)).thenReturn(new Rectangle(0, 0, 100, 20));
        ui.setMessage(test, "cheese");
    }

    @Test
    public void uiMsgCounterIncrementsBelow120() {
        ui.msgCounter = 0;
        ui.drawMessage(g2);
        assertEquals(1, ui.msgCounter);
        assertEquals(true, ui.msgOn);
        assertEquals(test, ui.message);

    }

    @Test
    public void uiMsgCounterAt120() {
        ui.msgCounter = 119;
        ui.drawMessage(g2);
        assertEquals(120, ui.msgCounter);
        assertEquals(true, ui.msgOn);
        assertEquals(test, ui.message);
    }

    @Test
    public void uiMsgCounterResetsAfter120() {
        ui.msgCounter = 120;
        ui.drawMessage(g2);
        assertEquals(0, ui.msgCounter);
        assertEquals(false, ui.msgOn);
        assertEquals(null, ui.currentImage);
        assertEquals("", ui.message);
    }



    
}
