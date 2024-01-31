package main.util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * StyleCollection is a collection of styles
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */
public class StyleCollection {
    private final Map<Integer, Style> styles; // The styles

    /**
     * Constructor for StyleCollection
     */
    public StyleCollection() {
        this.styles = new HashMap<>();
        initializeDefaultStyles();
    }

    /**
     * Initialize the default styles
     */
    private void initializeDefaultStyles() {
        styles.put(0, new Style(0, Color.red, 48, 20));

        // Style for level 1
        styles.put(1, new Style(20, Color.blue, 40, 10));

        // Style for level 2
        styles.put(2, new Style(50, Color.black, 36, 10));

        // Style for level 3
        styles.put(3, new Style(70, Color.black, 30, 10));

        // Style for level 4
        styles.put(4, new Style(90, Color.black, 24, 10));
    }

    /**
     * Adds a style to the collection
     *
     * @param level the level of the style
     * @param style the style
     */
    public void addStyle(int level, Style style) {
        styles.put(level, style);
    }

    /**
     * Get the style
     *
     * @param level the level of the style
     * @return the style
     */
    public Style getStyle(int level) {
        return styles.get(level);
    }

    /**
     * Updates the style with the given level
     *
     * @param level the level of the style
     * @param style the style
     */
    public void updateStyle(int level, Style style) {
        styles.replace(level, style);
    }

    /**
     * Removes the style with the given level
     *
     * @param level the level of the style
     */
    public void removeStyle(int level) {
        styles.remove(level);
    }

    /**
     * Gets the number of styles
     *
     * @return the number of styles
     */
    public int getNumberOfStyles() {
        return styles.size();
    }

}
