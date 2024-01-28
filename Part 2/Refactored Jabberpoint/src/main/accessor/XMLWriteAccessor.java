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
 *  @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 *  @version 1.61 2024/01/10 Carla Redmond
 */
public class XMLWriteAccessor implements WriteAccessor {

    private static final Logger logger = Logger.getLogger(XMLWriteAccessor.class.getName());

    /**
     * Saves the presentation to a file
     * @param presentation Presentation to save
     * @param filename     String filename to save to
     * @throws AccessorException if the file cannot be written to
     * @throws IOException    if the file cannot be written to
     */
    public void saveFile(Presentation presentation, String filename) throws AccessorException, IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        out.println("<presentation>");
        out.print("<showtitle>");
        out.print(presentation.getTitle());
        out.println("</showtitle>");
        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
            Slide slide = presentation.getSlide(slideNumber);
            out.println("<slide>");
            out.println("<title>" + slide.getTitle() + "</title>");
            List<SlideItem> slideItems = slide.getSlideItems();
            for (SlideItem slideItem : slideItems) {
                out.print("<item kind=");
                if (slideItem instanceof TextItem textItem) {
                    out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
                    out.print((textItem).getText());
                } else {
                    if (slideItem instanceof BitmapItem bitmapItem) {
                        out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
                        out.print((bitmapItem).imagePath);
                    } else {
                        logger.info("Ignoring unknown SlideItem type");
                    }
                }
                out.println("</item>");
            }
            out.println("</slide>");
        }
        out.println("</presentation>");
        out.close();
    }
}

