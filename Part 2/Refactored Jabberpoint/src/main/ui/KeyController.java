package main.ui;

import main.JabberPoint;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * KeyController handles key events for the SlideViewerComponent
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public class KeyController extends KeyAdapter {
    private final SlideViewerComponent slideViewerComponent; //The SlideViewerComponent to be controlled

    /**
     * Constructor for KeyController
     *
     * @param slideViewerComponent the SlideViewerComponent to be controlled
     */
    public KeyController(SlideViewerComponent slideViewerComponent) {
        this.slideViewerComponent = slideViewerComponent;
    }

    /**
     * Process a key press event
     *
     * @param keyEvent the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER, '+' -> slideViewerComponent.nextSlide();
            case KeyEvent.VK_PAGE_UP, KeyEvent.VK_UP, '-' -> slideViewerComponent.prevSlide();

            case 'q', 'Q' -> JabberPoint.exitApplication(0);
            default -> {
                //should not be reached
            }

        }
    }
}