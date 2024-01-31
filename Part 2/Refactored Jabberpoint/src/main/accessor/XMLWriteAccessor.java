package main.accessor;

import main.exception.AccessorException;
import main.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

/**
 * This Accessor makes it possible to write XML data
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */
public class XMLWriteAccessor implements WriteAccessor {

    private static final Logger logger = Logger.getLogger(XMLWriteAccessor.class.getName()); // Logger

    /**
     * Saves the presentation to a file
     *
     * @param presentation Presentation to save
     * @param filename String filename to save to
     * @throws AccessorException if the file cannot be written to
     * @throws IOException if the file cannot be written to
     */
    public void saveFile(Presentation presentation, String filename) throws AccessorException, IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            writePresentationStart(out, presentation);
            writeSlidesInPresentation(out, presentation);
            out.println("</presentation>");
        }
    }

    /**
     * Writes the presentation header
     *
     * @param out The PrintWriter
     * @param presentation The presentation
     */
    private void writePresentationStart(PrintWriter out, Presentation presentation) {
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        out.println("<presentation>");
        out.print("<showtitle>");
        out.print(presentation.getTitle());
        out.println("</showtitle>");
    }

    /**
     * Writes the slides in a presentation
     *
     * @param out The PrintWriter
     * @param presentation The presentation
     */
    private void writeSlidesInPresentation(PrintWriter out, Presentation presentation) {
        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
            Slide slide = presentation.getSlide(slideNumber);
            writeSlideTitle(out, slide);
        }
    }

    /**
     * Writes the slide title
     *
     * @param out The PrintWriter
     * @param slide The slide
     */
    private void writeSlideTitle(PrintWriter out, Slide slide) {
        out.println("<slide>");
        out.println("<title>" + slide.getTitle() + "</title>");
        writeSlideItems(out, slide);
        out.println("</slide>");
    }

    /**
     * Writes the slide items
     *
     * @param out The PrintWriter
     * @param slide The slide
     */
    private void writeSlideItems(PrintWriter out, Slide slide) {
        List<SlideItem> slideItems = slide.getSlideItems();
        for (SlideItem slideItem : slideItems) {
            writeSlideItemType(out, slideItem);
        }
    }

    /**
     * Writes a slide item depending on type
     *
     * @param out The PrintWriter
     * @param slideItem The slide item
     */
    private void writeSlideItemType(PrintWriter out, SlideItem slideItem) {
        out.print("<item kind=");
        if (slideItem instanceof TextItem textItem) {
            out.print("\"text\" level=\"" + textItem.getLevel() + "\">");
            out.print(textItem.getText());
        } else if (slideItem instanceof BitmapItem bitmapItem) {
            out.print("\"image\" level=\"" + bitmapItem.getLevel() + "\">");
            out.print(bitmapItem.imagePath);
        } else {
            logger.info("Ignoring unknown SlideItem type");
        }
        out.println("</item>");
    }
}

