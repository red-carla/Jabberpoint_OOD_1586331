package main.model;

import main.util.Style;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * The abstract class for items in a slide
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public abstract class SlideItem {
    private final int level;

    /**
     * Constructor for SlideItem
     *
     * @param lev the level of the item
     */
    protected SlideItem(int lev) {
        level = lev;
    }

    /**
     * get the level of the item
     *
     * @return the level of the item
     */
    public int getLevel() {
        return level;
    }

    /**
     * gets the bounding box
     *
     * @param g        the graphics object
     * @param observer the image observer
     * @param scale    the scale
     * @param style    the style
     * @return Rectangle
     */
    public abstract Rectangle getBoundingBox(Graphics g,
                                             ImageObserver observer, float scale, Style style);

    /**
     * draws the item
     *
     * @param x        the x coordinate
     * @param y        the y coordinate
     * @param scale    the scale
     * @param g        the graphics object
     * @param style    the style
     * @param observer the image observer
     */

    public abstract void draw(int x, int y, float scale,
                              Graphics g, Style style, ImageObserver observer);
}