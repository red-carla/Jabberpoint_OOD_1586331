package main.model;

import main.util.Style;
import main.util.TextEnums;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A text item with drawing capabilities
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public class TextItem extends SlideItem {
    private final String text; // The text

    /**
     * Constructor for TextItem
     *
     * @param level  the level of the text item
     * @param string the message of the text item
     */
    public TextItem(int level, String string) {
        super(level);
        text = string;
    }

    /**
     * Attributed String
     *
     * @param style the style
     * @return the AttributedString for the Item
     */
    public AttributedString getAttributedString(Style style) {
        AttributedString attrStr = new AttributedString(getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(), 0, text.length());
        return attrStr;
    }

    /**
     * Returns the bounding box of an Item
     *
     * @param g        the graphics object
     * @param observer the image observer
     * @param scale    the scale
     * @param myStyle  the style
     * @return Rectangle
     */
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer,
                                    float scale, Style myStyle) {
        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        int xsize = 0, ysize = (int) (myStyle.getLeading() * scale);
        Iterator<TextLayout> iterator = layouts.iterator();
        while (iterator.hasNext()) {
            TextLayout layout = iterator.next();
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > xsize) {
                xsize = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0) {
                ysize += (int) bounds.getHeight();
            }
            ysize += (int) (layout.getLeading() + layout.getDescent());
        }
        return new Rectangle((int) (myStyle.getIndent() * scale), 0, xsize, ysize);
    }

    /**
     * Returns the layouts of the text
     *
     * @param g     the graphics object
     * @param s     the style
     * @param scale the scale
     * @return List<TextLayout>
     */
    private List<TextLayout> getLayouts(Graphics g, Style s, float scale) {
        List<TextLayout> layouts = new ArrayList<>();
        AttributedString attrStr = getAttributedString(s);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        float wrappingWidth = (TextEnums.WIDTH.getValue() - s.getIndent()) * scale;
        while (measurer.getPosition() < getText().length()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }
        return layouts;
    }

    /**
     * Getter for text
     *
     * @return the text
     */
    public String getText() {
        return text == null ? "" : text;
    }

    /**
     * @param x       the x coordinate
     * @param y       the y coordinate
     * @param scale   the scale
     * @param g       the graphics object
     * @param myStyle the style
     * @param o       the image observer
     */
    public void draw(int x, int y, float scale, Graphics g,
                     Style myStyle, ImageObserver o) {
        if (text == null || text.isEmpty()) {
            return;
        }
        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        Point pen = new Point(x + (int) (myStyle.getIndent() * scale),
                y + (int) (myStyle.getLeading() * scale));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(myStyle.getColor());
        for (TextLayout layout : layouts) {
            pen.y += (int) layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += (int) layout.getDescent();

        }
    }

    /**
     * @return the string representation of the text item
     */
    public String toString() {
        return "TextItem[" + getLevel() + "," + getText() + "]";
    }
}
