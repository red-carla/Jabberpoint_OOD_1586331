package main.model;

import main.ui.SlideViewerComponent;

import java.util.ArrayList;


/**
 * Presentations keep track of the slides in a presentation
 * Only one instance of this class is available.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public class Presentation {
    private String showTitle; //The title of the presentation
    private ArrayList<Slide> showList = new ArrayList<>(); //An ArrayList with slides
    private int currentSlideNumber = 0; //The number of the current slide
    private SlideViewerComponent slideViewComponent = null; //The view component of the slides

    /**
     * Constructor for Presentation
     */
    public Presentation() {

    }

    /**
     * Constructor for Presentation
     * @param slideViewerComponent the SlideViewerComponent to be controlled
     */
    public Presentation(SlideViewerComponent slideViewerComponent) {
        this.slideViewComponent = slideViewerComponent;
        clear();
    }

    /**
     * get the number of slides in the presentation
     * @return the number of slides in the presentation
     */
    public int getSize() {
        return showList.size();
    }

    /**
     * get the title of the presentation
     * @return the title of the presentation
     */
    public String getTitle() {
        return showTitle;
    }

    /**
     * set the title of the presentation
     * @param nt title of the presentation
     */
    public void setTitle(String nt) {
        showTitle = nt;
    }

    /**
     * set the slide viewer component to control
     * @param slideViewerComponent the SlideViewerComponent to be controlled
     */
    public void setShowView(SlideViewerComponent slideViewerComponent) {
        this.slideViewComponent = slideViewerComponent;
    }

    /**
     * get the current slide number
     * @return the current slide number
     */
    public int getCurrentSlideNumber() {
        return currentSlideNumber;
    }

    /**
     * set the current slide number
     * @param currentSlideNumber the current slide number
     */
    public void setCurrentSlideNumber(int currentSlideNumber) {
        this.currentSlideNumber = currentSlideNumber;
    }

    /**
     * set the slide number
     * @param number the number of the slide to be shown
     */
    public void setSlideNumber(int number) {
        currentSlideNumber = number;
        if (slideViewComponent != null) {
            slideViewComponent.update(this, getCurrentSlide());
        }
    }

    /**
     *  Clear the presentation
     */
    public void clear() {
        showList = new ArrayList<>();
        setSlideNumber(-1);
    }

    /**
     * add a slide to the presentation
     * @param slide the slide to be added
     */
    public void append(Slide slide) {
        showList.add(slide);
    }


    /**
     * get the slide with the given number
     * @param number the number of the slide to be returned
     * @return the slide with the given number
     */
    public Slide getSlide(int number) {
        if (number < 0 || number >= getSize()) {
            return null;
        }
        return showList.get(number);
    }

    /**
     * get the current slide
     * @return the current slide
     */
    public Slide getCurrentSlide() {
        return getSlide(currentSlideNumber);
    }

    /**
     * exit the application
     * @param s the exit status
     */
    public void exit(int s) {
        System.exit(s);
    }
}