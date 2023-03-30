package ui;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import main.GamePanel;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UITest {
    private UI ui;
    Font font;
    @Mock
    Graphics2D g2;   

    @Before
    public void setup(){
        // gp = mock(GamePanel.class);
        ui = new UI(new GamePanel());
        font = new Font("Arial", Font.PLAIN, 12);
    }

    @Test
    public void testGetLines() {
        Font font = new Font("Arial", Font.PLAIN, 12);
        FontMetrics metrics = new Canvas().getFontMetrics(font);
        int maxWidth = 100;
        String text = "This is a long text string that should be split into multiple lines when it exceeds the maximum width.";
        String[] expectedLines = {"This is a long text", "string that should", "be split into", "multiple lines", "when it exceeds", "the maximum", "width."};
        String[] actualLines = ui.getLines(text, metrics, maxWidth);
        assertArrayEquals(expectedLines, actualLines);
    }
    
    //The starting point should be half of the screen width minus half of the length of the text, which is (920-1/2) - (96/2) = 912.
    @Test
    public void testGetXforCenteredText() {
        // Initialize the mock
        MockitoAnnotations.openMocks(this);
        // Set up the expected values
        String text = "Hello, world!";
        int screenWidth = 48*20;
        FontMetrics fontMetrics = mock(FontMetrics.class);
        when(g2.getFontMetrics()).thenReturn(fontMetrics);
        when(fontMetrics.getStringBounds(text, g2)).thenReturn(new Rectangle(0, 0, 100, 20));

        // Call the method and assert the result
        int result = ui.getXforCenteredText(text, g2);
        assertEquals(((screenWidth - 1)/2) - (100/2), result);
    }
    

}
