package main.ui;

import main.model.Presentation;
import main.util.StringResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;

/**
 * The application window for a slide view component
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */
public class SlideViewerFrame extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    @Serial
    private static final long serialVersionUID = 3227L;

    /**
     * Constructor for SlideViewerFrame
     * @param title       the title of the frame
     * @param presentation the presentation to be shown
     */
    public SlideViewerFrame(String title, Presentation presentation) {
        super(title);
        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
        presentation.setShowView(slideViewerComponent);
        setupWindow(slideViewerComponent, presentation);
    }

    /**
     * Set up the window
     * @param slideViewerComponent the SlideViewerComponent to be controlled
     * @param presentation        the presentation to be shown
     */
    public void setupWindow(SlideViewerComponent
                                    slideViewerComponent, Presentation presentation) {
        setTitle(StringResources.JABTITLE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        getContentPane().add(slideViewerComponent);
        addKeyListener(new KeyController(slideViewerComponent)); //Add a controller
        setMenuBar(new MenuController(this, slideViewerComponent));    //Add another controller
        setSize(new Dimension(WIDTH, HEIGHT)); //Same sizes a slide has
        setVisible(true);
    }
}