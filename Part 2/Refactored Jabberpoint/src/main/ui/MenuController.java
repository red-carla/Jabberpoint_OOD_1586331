package main.ui;


import main.JabberPoint;
import main.accessor.XMLReadAccessor;
import main.accessor.XMLWriteAccessor;
import main.exception.AccessorException;
import main.util.StringResources;
import main.util.TextEnums;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;
import java.util.logging.Logger;

/**
 * MenuController handles menu events for the SlideViewerComponent
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */
public class MenuController extends MenuBar {

    @Serial
    private static final long serialVersionUID = 227L; //Needed for Serializable
    private static final Logger logger = Logger.getLogger(MenuController.class.getName()); //Logger
    private final JFrame parentFrame; //The frame, only used as parent for the Dialogs
    private final SlideViewerComponent slideViewerComponent; //The SlideViewerComponent to be controlled
    private final Menu fileMenu = new Menu(TextEnums.FILE.getName()); //The file menu
    private final Menu viewMenu = new Menu(TextEnums.VIEW.getName()); //The view menu
    private final Menu helpMenu = new Menu(TextEnums.HELP.getName()); //The help menu
    private MenuItem menuItem; //A menu item


    /**
     * Constructor for MenuController
     *
     * @param frame The parent frame
     * @param slideViewerComponent the SlideViewerComponent to be controlled
     */
    public MenuController(JFrame frame, SlideViewerComponent slideViewerComponent) {
        this.parentFrame = frame;
        this.slideViewerComponent = slideViewerComponent;

        add(fileMenu);
        add(viewMenu);
        openOption();
        newOption();
        saveOption();
        exitOption();
        nextSlide();
        prevSlide();
        navSlide();
        aboutOption();
        setHelpMenu(helpMenu);//Needed for portability (Motif, etc.).
    }

    /**
     * creates a menu item
     *
     * @param name The name of the MenuItem
     * @return MenuItem
     */
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }

    /**
     * Open option
     * Opens a file
     */
    public void openOption() {
        menuItem = mkMenuItem("Open");
        fileMenu.add(menuItem);
        menuItem.addActionListener(actionEvent -> {
            slideViewerComponent.clear();
            XMLReadAccessor xmlReadAccessor = new XMLReadAccessor();
            try {
                xmlReadAccessor.loadFile(slideViewerComponent.getPresentation(), StringResources.TEST_FILE);
            } catch (IOException | AccessorException e) {
                logger.info("Error loading file: " + e.getMessage());
            }
            slideViewerComponent.setSlideNumber(0);
            parentFrame.repaint();
        });
    }

    /**
     * Save option
     * Saves a file
     */
    public void saveOption() {
        menuItem = mkMenuItem(TextEnums.SAVE.getName());
        fileMenu.add(menuItem);
        menuItem.addActionListener(actionEvent -> {
            XMLWriteAccessor xmlWriteAccessor = new XMLWriteAccessor();
            try {
                xmlWriteAccessor.saveFile(slideViewerComponent.getPresentation(), StringResources.SAVE_FILE);
            } catch (IOException | AccessorException e) {
                logger.info("Error saving file: " + e.getMessage());
            }
        });
    }

    /**
     * New option
     * Creates a new file
     */
    public void newOption() {
        menuItem = mkMenuItem(TextEnums.NEW.getName());
        fileMenu.add(menuItem);
        menuItem.addActionListener(e -> slideViewerComponent.clear());
    }

    /**
     * Exit option
     * Exits the application
     */
    public void exitOption() {
        menuItem = mkMenuItem(TextEnums.EXIT.getName());
        fileMenu.add(menuItem);
        menuItem.addActionListener(e -> JabberPoint.exitApplication(0));
    }

    /**
     * Navigate to next slide
     */
    private void nextSlide() {
        menuItem = mkMenuItem(TextEnums.NEXT.getName());
        viewMenu.add(menuItem);
        menuItem.addActionListener(e -> slideViewerComponent.nextSlide());
    }

    /**
     * Navigate to previous slide
     */
    private void prevSlide() {
        menuItem = mkMenuItem(TextEnums.PREV.getName());
        viewMenu.add(menuItem);
        menuItem.addActionListener(e -> slideViewerComponent.prevSlide());
    }

    /**
     * Navigate to specific slide
     */
    public void navSlide() {
        menuItem = mkMenuItem(TextEnums.GOTO.getName());
        viewMenu.add(menuItem);
        menuItem.addActionListener(actionEvent -> {
            String slideNumberString = JOptionPane.showInputDialog(TextEnums.PAGENR.getName());
            int slideNumber = Integer.parseInt(slideNumberString);

            if (slideNumber > 0 && slideNumber <= slideViewerComponent.getPresentation().getSize()) {
                slideViewerComponent.setSlideNumber(slideNumber - 1);
            } else {
                JOptionPane.showMessageDialog(parentFrame, TextEnums.PAGENOTFOUND.getName());
            }
        });
    }

    /**
     * About option
     */
    public void aboutOption() {
        menuItem = mkMenuItem(TextEnums.ABOUT.getName());
        helpMenu.add(menuItem);
        menuItem.addActionListener(actionEvent -> AboutBox.show(parentFrame));
    }

}