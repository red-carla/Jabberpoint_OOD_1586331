package main.util;

import java.awt.*;

/**
 * Style represents Indent, Color, Font and Leading
 * The link between a style number and an item level is hard-linked:
 * in Slide the style is grabbed for an item
 * with a style number the same as the item level.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public class Style {

    private final int indent; // The indent
    private final Color color; // The color
    private final Font font; // The font
    private final int fontSize; // The font size
    private final int leading; // The leading

    /**
     * @param indent the indent
     * @param color the color
     * @param fontSize the font size
     * @param leading the leading
     */
    public Style(int indent, Color color, int fontSize, int leading) {
        this.indent = indent;
        this.color = color;
        font = new Font(TextEnums.FONT_NAME.getName(), Font.BOLD, fontSize);
        this.leading = leading;
        this.fontSize = fontSize;
    }

    /**
     * @return indent
     */
    public int getIndent() {
        return indent;
    }

    /**
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return font
     */
    public Font getFont() {
        return font;
    }

    /**
     * @return font size
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * @return font leading
     */
    public int getLeading() {
        return leading;
    }

    /**
     * @param scale the scale
     * @return the derived font
     */
    public Font getDerivedFont(float scale) {
        return font.deriveFont(fontSize * scale);
    }

    /**
     * @return string representation of the style
     */
    public String toString() {
        return "[" + indent + "," + color + "; " + fontSize + " on " + leading + "]";
    }

}