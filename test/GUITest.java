import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


 //Test GUI component of The Wiki Game

public class GUITest {

    private GUI gui;

    @BeforeEach
    public void setUp() {
        //Create GUI for testing
        gui = new GUI();
    }

    @AfterEach
    public void tearDown() {
        //Clean up after tests
        if (gui != null) {
            gui.dispose();
        }
    }

    @Test
    public void testGUIInitialization() {
        assertNotNull(gui, "GUI is initialized");
        assertEquals("The Wiki Game", gui.getTitle(), "Title is 'The Wiki Game'");
        assertEquals(JFrame.EXIT_ON_CLOSE, gui.getDefaultCloseOperation(),
                "Default closes on EXIT_ON_CLOSE");
    }

    @Test
    public void testGUISize() {
        Dimension size = gui.getSize();
        assertEquals(800, size.width, "800 Width");
        assertEquals(600, size.height, "600 Height");
    }

    @Test
    public void testGUINotNull() {
        assertNotNull(gui, "GUI instance is not null");
    }

    @Test
    public void testContentPaneExists() {
        assertNotNull(gui.getContentPane(), "Content pane should exist");
    }

    @Test
    public void testGUIIsFrameInstance() {
        assertTrue(gui instanceof JFrame, "GUI is an instance of JFrame");
    }
}

