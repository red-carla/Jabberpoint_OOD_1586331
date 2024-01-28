package main.ui;

import main.model.Presentation;
import main.model.Slide;

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
    private static final long serialVersionUID = 227L;
    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTHEIGHT = 10;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;
    private Slide slide; //The current slide
    private Font labelFont = null; //The font for labels
    private Presentation presentation = null; //The presentation
    private JFrame frame = null;

    /**
     * Constructor for SlideViewerComponent
     * @param pres  the presentation to be shown
     * @param frame the frame to be used as parent for the Dialogs
     */
    public SlideViewerComponent(Presentation pres, JFrame frame) {
        setBackground(BGCOLOR);
        presentation = pres;
        labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
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
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    /**
     * update the presentation
     * @param presentation the presentation to be shown
     * @param data        the slide to be shown
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
     * clear the presentation
     */
    void clear() {
        presentation.clear();
    }

    /**
     *  set the slide number
     * @param number the number of the slide to be shown
     */
    public void setSlideNumber(int number) {
        this.presentation.setSlideNumber(number);
        this.update(presentation, this.presentation.getCurrentSlide());
    }

    /**
     * go to the previous slide
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
     *  paint the component
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
        g.drawString(String.format("Slide %s of %s", 1 + presentation.getCurrentSlideNumber(), presentation.getSize()), XPOS, YPOS);
        Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
        slide.draw(g, area, this);
    }

}
