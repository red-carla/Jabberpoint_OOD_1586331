package main.ui;

import main.model.Presentation;
import main.model.Slide;
import main.util.TextEnums;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;


/**
 * SlideViewerComponent is a graphical component that can display Slides
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public class SlideViewerComponent extends JComponent {

    @Serial
    private static final long serialVersionUID = 227L; //Serial version UID
    private static final Color BGCOLOR = Color.white; //Background color
    private static final Color COLOR = Color.black; //Color of the text
    private static final String FONT_NAME = "Dialog"; //Font name
    private static final int FONT_STYLE = Font.BOLD; //Font style
    private static final int FONT_HEIGHT = 10; //Font height
    private static final int X_POS = 1100; //Position of page number
    private static final int Y_POS = 20; //Position of page number
    private transient Slide slide; //The current slide
    private Font labelFont = null; //The font for labels
    private transient Presentation presentation = null; //The presentation
    private JFrame frame = null; //The frame

    /**
     * Constructor for SlideViewerComponent
     *
     * @param pres the presentation to be shown
     * @param frame the frame to be used as parent for the Dialogs
     */
    public SlideViewerComponent(Presentation pres, JFrame frame) {
        setBackground(BGCOLOR);
        presentation = pres;
        labelFont = new Font(FONT_NAME, FONT_STYLE, FONT_HEIGHT);
        this.frame = frame;
    }

    /**
     * @return the presentation
     */
    public Presentation getPresentation() {
        return this.presentation;
    }

    /**
     * @return the preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(TextEnums.WIDTH.getValue(), TextEnums.HEIGHT.getValue());
    }

    /**
     * Update the presentation
     *
     * @param presentation the presentation to be shown
     * @param data the slide to be shown
     */
    public void update(Presentation presentation, Slide data) {
        if (data == null) {
            repaint();
            return;
        }

        this.presentation = presentation;
        this.slide = data;
        repaint();
        frame.setTitle(presentation.getTitle());
    }

    /**
     * Clear the presentation
     */
    void clear() {
        presentation.clear();
        repaint();
    }

    /**
     * Set the slide number
     *
     * @param number the number of the slide to be shown
     */
    public void setSlideNumber(int number) {
        this.presentation.setSlideNumber(number);
        this.update(presentation, this.presentation.getCurrentSlide());
    }

    /**
     * Go to the previous slide
     */
    public void prevSlide() {
        if (presentation.getCurrentSlideNumber() > 0 && presentation.getCurrentSlideNumber() <= presentation.getSize() - 1) {
            setSlideNumber(presentation.getCurrentSlideNumber() - 1);
        }
    }

    /**
     * go to the next slide
     */
    public void nextSlide() {
        if (presentation.getCurrentSlideNumber() < (presentation.getSize() - 1)) {
            setSlideNumber(presentation.getCurrentSlideNumber() + 1);
        }
    }

    /**
     * paint the component
     *
     * @param g the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(BGCOLOR);
        g.fillRect(0, 0, getSize().width, getSize().height);
        if (presentation.getCurrentSlideNumber() < 0 || slide == null) {
            return;
        }
        g.setFont(labelFont);
        g.setColor(COLOR);
        g.drawString(String.format("Slide %s of %s", 1 + presentation.getCurrentSlideNumber(), presentation.getSize()), X_POS, Y_POS);
        Rectangle area = new Rectangle(0, Y_POS, getWidth(), (getHeight() - Y_POS));
        slide.draw(g, area, this);
    }

}
