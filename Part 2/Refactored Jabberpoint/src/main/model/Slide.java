package main.model;

import main.util.Style;
import main.util.StyleCollection;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Presentations keep track of the slides in a presentation
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public class Slide {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    protected String title; //The title is kept separately
    protected ArrayList<SlideItem> items;
    private final StyleCollection styleCollection;

    /**
     * Constructor for Slide
     */
    public Slide() {
        items = new ArrayList<>();
        this.styleCollection = new StyleCollection();
    }

    /**
     * add an item to the slide
     * @param anItem the SlideItem to be added
     */
    public void appendText(SlideItem anItem) {
        items.add(anItem);
    }

    /**
     * get the title of the Slide
     * @return the slide title
     */
    public String getTitle() {
        return title;
    }

    /**
     * set the new title of the slide
     * @param newTitle the new title of the slide
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    /**
     * add a text item to the slide
     * @param level  the level of the text item
     * @param message the message of the text item
     */
    public void appendText(int level, String message) {
        appendText(new TextItem(level, message));
    }

    /**
     * get all the SlideItems in a List
     * @return all the SlideItems in a List
     */
    public List<SlideItem> getSlideItems() {
        return items;
    }


    /**
     * get the size of the slideItems collection
     * @return the number of SlideItems
     */
    public int getSize() {
        return items.size();
    }

    /**
     * draw the slide
     * @param g The graphics object
     * @param area The area to draw in
     * @param view The view
     */
    public void draw(Graphics g, Rectangle area, ImageObserver view) {
        float scale = getScale(area);
        int y = area.y;
        //The title is treated separately
        SlideItem slideItem = new TextItem(0, getTitle());
        Style style = styleCollection.getStyle(slideItem.getLevel());
        slideItem.draw(area.x, y, scale, g, style, view);
        y += slideItem.getBoundingBox(g, view, scale, style).height;
        for (int number = 0; number < getSize(); number++) {
            slideItem = getSlideItems().get(number);
            style = styleCollection.getStyle(slideItem.getLevel());
            slideItem.draw(area.x, y, scale, g, style, view);
            y += slideItem.getBoundingBox(g, view, scale, style).height;
        }
    }

    /**
     * Calculates the scale to draw a slide
     * @param area The area to draw in
     * @return the scale to draw a slide
     */
    private float getScale(Rectangle area) {
        return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
    }
}