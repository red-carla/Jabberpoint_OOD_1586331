package main.ui;


import main.JabberPoint;
import main.accessor.XMLReadAccessor;
import main.accessor.XMLWriteAccessor;
import main.exception.AccessorException;
import main.util.StringResources;

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
    private static final long serialVersionUID = 227L;
    private final JFrame parentFrame; //The frame, only used as parent for the Dialogs
    private final SlideViewerComponent slideViewerComponent;
    private static final Logger logger = Logger.getLogger(MenuController.class.getName());
    private final Menu fileMenu = new Menu(StringResources.FILE);
    private final Menu viewMenu = new Menu(StringResources.VIEW);
    private final Menu helpMenu = new Menu(StringResources.HELP);
    private MenuItem menuItem;


    /**
     * Constructor for MenuController
     * @param frame               The parent frame
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
     * @param name The name of the MenuItem
     * @return MenuItem
     */
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }

    /**
     * Open option
     */
    public void openOption() {
        menuItem = mkMenuItem(StringResources.OPEN);
        fileMenu.add(menuItem);
        menuItem.addActionListener(actionEvent -> {
            slideViewerComponent.clear();
            XMLReadAccessor xmlReadAccessor = new XMLReadAccessor();
            try {
                xmlReadAccessor.loadFile(slideViewerComponent.getPresentation(), StringResources.TESTFILE);
            } catch (IOException | AccessorException e) {
                logger.info("Error loading file: " + e.getMessage());
            }
            slideViewerComponent.setSlideNumber(0);
            parentFrame.repaint();
        });
    }

    /**
     * Save option
     */
    public void saveOption() {
        menuItem = mkMenuItem(StringResources.SAVE);
        fileMenu.add(menuItem);
        menuItem.addActionListener(actionEvent -> {
            XMLWriteAccessor xmlWriteAccessor = new XMLWriteAccessor();
            try {
                xmlWriteAccessor.saveFile(slideViewerComponent.getPresentation(), StringResources.SAVEFILE);
            } catch (IOException | AccessorException e) {
                logger.info("Error saving file: " + e.getMessage());
            }
        });
    }

    /**
     * New option
     */
    public void newOption() {
        menuItem = mkMenuItem(StringResources.NEW);
        fileMenu.add(menuItem);
        slideViewerComponent.clear();
        menuItem.addActionListener(e -> parentFrame.repaint());
    }

    /**
     * Exit option
     */
    public void exitOption() {
        menuItem = mkMenuItem(StringResources.EXIT);
        fileMenu.add(menuItem);
        menuItem.addActionListener(e -> JabberPoint.exitApplication(0));
    }

    /**
     *  Navigate to next slide
     */
    private void nextSlide() {
        menuItem = mkMenuItem(StringResources.NEXT);
        viewMenu.add(menuItem);
        menuItem.addActionListener(e -> slideViewerComponent.nextSlide());
    }

    /**
     *  Navigate to previous slide
     */
    private void prevSlide() {
        menuItem = mkMenuItem(StringResources.PREV);
        viewMenu.add(menuItem);
        menuItem.addActionListener(e -> slideViewerComponent.prevSlide());
    }

    /**
     *  Navigate to specific slide
     */
    public void navSlide() {
        menuItem = mkMenuItem(StringResources.GOTO);
        viewMenu.add(menuItem);
        menuItem.addActionListener(actionEvent -> {
            String slideNumberString = JOptionPane.showInputDialog(StringResources.PAGENR);
            int slideNumber = Integer.parseInt(slideNumberString);

            if (slideNumber > 0 && slideNumber <= slideViewerComponent.getPresentation().getSize()) {
                slideViewerComponent.setSlideNumber(slideNumber - 1);
            } else {
                JOptionPane.showMessageDialog(parentFrame, StringResources.PAGENOTFOUND);
            }

        });
    }

    /**
     *  About option
     */
    public void aboutOption() {
        menuItem = mkMenuItem(StringResources.ABOUT);
        helpMenu.add(menuItem);
        menuItem.addActionListener(actionEvent -> AboutBox.show(parentFrame));
    }

}