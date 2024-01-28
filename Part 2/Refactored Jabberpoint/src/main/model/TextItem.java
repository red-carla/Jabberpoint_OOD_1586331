package main.model;

import main.util.Style;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

/**
 * A text item with drawing capabilities
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public class TextItem extends SlideItem {
    private final String text;

    /**
     * @param level the level of the text item
     * @param string the message of the text item
     */
    public TextItem(int level, String string) {
        super(level);
        text = string;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text == null ? "" : text;
    }

    /**
     * @param style the style
     * @param scale the scale
     * @return the attributed string
     */
    public AttributedString getAttributedString(Style style, float scale) {
        AttributedString attrStr = new AttributedString(getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(), 0, text.length());
        return attrStr;
    }

    /**
     * @param g        the graphics object
     * @param observer the image observer
     * @param scale    the scale
     * @param style    the style
     * @return Rectangle
     */
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
        List<Dimension> lineDimensions = calculateLineDimensions(g, style, scale, text);
        return calculateTotalBoundingBox(lineDimensions);
    }

    /**
     * @param g the graphics object
     * @param style the style
     * @param scale the scale
     * @param text the text
     * @return the line dimensions
     */
    private List<Dimension> calculateLineDimensions(Graphics g, Style style, float scale, String text) {
        List<Dimension> lineDimensions = new ArrayList<>();
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        AttributedString attributedString = getAttributedString(style, scale);
        AttributedCharacterIterator charIterator = attributedString.getIterator();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIterator, frc);
        float wrappingWidth = calculateWrappingWidth(scale, style);

        while (lineMeasurer.getPosition() < charIterator.getEndIndex()) {
            TextLayout layout = lineMeasurer.nextLayout(wrappingWidth);
            int lineWidth = (int) layout.getAdvance();
            int lineHeight = (int) (layout.getAscent() + layout.getDescent() + layout.getLeading());

            Dimension lineDimension = new Dimension(lineWidth, lineHeight);
            lineDimensions.add(lineDimension);
        }

        return lineDimensions;
    }

    /**
     * @param lineDimensions the line dimensions
     * @return the total bounding box
     */
    private Rectangle calculateTotalBoundingBox(List<Dimension> lineDimensions) {
        int totalWidth = 0;
        int totalHeight = 0;
        for (Dimension dim : lineDimensions) {
            totalWidth = Math.max(totalWidth, dim.width);
            totalHeight += dim.height;
        }
        return new Rectangle(0, 0, totalWidth, totalHeight);
    }


    /**
     * @param x       the x coordinate
     * @param y       the y coordinate
     * @param scale   the scale
     * @param g       the graphics object
     * @param myStyle the style
     * @param o       the image observer
     */
    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o) {
        if (text == null || text.isEmpty()) {
            return;
        }
        Graphics2D g2d = setupGraphicsContext(g, myStyle);
        List<TextLayout> layouts = calculateLayouts(g2d, myStyle, scale);
        renderText(layouts, x, y, g2d);
    }

    /**
     * Set up the graphics context
     * @param g the graphics object
     * @param style the style
     * @return the graphics context
     */
    private Graphics2D setupGraphicsContext(Graphics g, Style style) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(style.getColor());
        g2d.setFont(style.getFont());
        return g2d;
    }

    /**
     *  calculate the width at which the text should be wrapped
     * @param scale the scale
     * @param s the style
     * @return the wrapping width
     */
    private float calculateWrappingWidth(float scale, Style s) {
        return (Slide.WIDTH - -s.getIndent()) * scale;
    }

    /**
     *  calculate the text layouts
     * @param g2d the graphics object
     * @param style the style
     * @param scale the scale
     * @return the text layouts
     */
    private List<TextLayout> calculateLayouts(Graphics2D g2d, Style style, float scale) {
        List<TextLayout> layouts = new ArrayList<>();
        FontRenderContext frc = g2d.getFontRenderContext();
        AttributedString attributedString = getAttributedString(style, scale);
        AttributedCharacterIterator charIterator = attributedString.getIterator();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIterator, frc);
        float wrappingWidth = calculateWrappingWidth(scale, style);
        while (lineMeasurer.getPosition() < charIterator.getEndIndex()) {
            TextLayout layout = lineMeasurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }
        return layouts;
    }

    /**
     * Render the text
     * @param layouts the text layouts
     * @param x the x coordinate
     * @param y the y coordinate
     * @param g2d the graphics object
     */
    private void renderText(List<TextLayout> layouts, int x, int y, Graphics2D g2d) {
        Point pen = new Point(x, y);
        for (TextLayout layout : layouts) {
            pen.y += (int) layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += (int) (layout.getDescent() + layout.getLeading());
        }
    }

    /**
     * @return the string representation of the text item
     */
    public String toString() {
        return "TextItem[" + getLevel() + "," + getText() + "]";
    }
}
