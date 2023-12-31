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

public class UIFormattingLinesTest {
    private UI ui;
    Font font;
    String text;
    int screenWidth;

    @Mock Graphics2D g2;   
    FontMetrics metrics;
    FontMetrics fontMetrics;

    @Before
    public void setup(){
        ui = new UI(new GamePanel());
        font = new Font("Arial", Font.PLAIN, 12);
        screenWidth = 48*20; 
        // Initialize the mock
        MockitoAnnotations.openMocks(this);
        fontMetrics = mock(FontMetrics.class);
        when(g2.getFontMetrics()).thenReturn(fontMetrics);
        
    }

    @Test
    public void testGetLines() {
        FontMetrics metrics = new Canvas().getFontMetrics(font);
        int maxWidth = 100;
        text = "This is a long text string that should be split into multiple lines when it exceeds the maximum width.";
        String[] expectedLines = {"This is a long text", "string that should", "be split into", "multiple lines", "when it exceeds", "the maximum", "width."};
        String[] actualLines = ui.getLines(text, metrics, maxWidth);
        assertArrayEquals(expectedLines, actualLines);
    }

    @Test
    public void testGetLinesWithNullMetrics() {
        int maxWidth = 100;
        String text = "This is a long text string that should be split into multiple lines when it exceeds the maximum width.";
        String[] expectedLines = {"This is a long text string that should", "be split into multiple lines when it exceeds the",  "maximum width."};
        String[] actualLines = ui.getLines(text, metrics, maxWidth);
        
        assertArrayEquals(expectedLines, actualLines);
    }

    //The starting point should be half of the screen width minus half of the length of the text, which is (920-1/2) - (96/2) = 912.
    @Test
    public void testGetXforCenteredText() {
        // Set up the expected values
        text = "Hello, world!";
        when(fontMetrics.getStringBounds(text, g2)).thenReturn(new Rectangle(0, 0, 100, 20));

        // Call the method and assert the result
        int result = ui.getXforCenteredText(text, g2);
        assertEquals(((screenWidth - 1)/2) - (100/2), result);
    }

    @Test
    public void testGetXforCenteredTextWithEmptyText(){
        text = "";
        when(fontMetrics.getStringBounds(text, g2)).thenReturn(new Rectangle(0, 0, 0, 20));
        int result = ui.getXforCenteredText(text,g2);
        assertEquals((screenWidth-1) / 2, result);
    }
    

}
