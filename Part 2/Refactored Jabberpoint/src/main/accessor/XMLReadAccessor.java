package main.accessor;

import main.exception.AccessorException;
import main.model.BitmapItem;
import main.model.Presentation;
import main.model.Slide;
import main.model.TextItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import main.util.StringResources;

/**
 *  This Accessor makes it possible to read XML data
 *
 *  @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 *  @version 1.61 2024/01/10 Carla Redmond
 */
public class XMLReadAccessor implements ReadAccessor {

    private static final Logger logger = Logger.getLogger(XMLReadAccessor.class.getName());

    /**
     * Gets the title
     * @param element the element
     * @param tagName the tag name
     * @return the title
     */
    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    /**
     * Loads the presentation from a file
     * @param presentation Presentation to load into
     * @param filename     String filename to load from
     * @throws AccessorException if the file cannot be read
     * @throws IOException     if the file cannot be read
     */
    public void loadFile(Presentation presentation, String filename) throws AccessorException, IOException {
        int slideNumber, itemNumber, max = 0, maxItems = 0;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbFactory.setXIncludeAware(false);
            dbFactory.setExpandEntityReferences(false);
            DocumentBuilder builder = dbFactory.newDocumentBuilder();

            Document document = builder.parse(new File(filename)); //Create a JDOM document
            Element doc = document.getDocumentElement();
            presentation.setTitle(getTitle(doc, main.util.StringResources.SHOWTITLE));

            NodeList slides = doc.getElementsByTagName(main.util.StringResources.SLIDE);
            max = slides.getLength();
            for (slideNumber = 0; slideNumber < max; slideNumber++) {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide();
                slide.setTitle(getTitle(xmlSlide, StringResources.SLIDETITLE));
                presentation.append(slide);

                NodeList slideItems = xmlSlide.getElementsByTagName(StringResources.ITEM);
                maxItems = slideItems.getLength();
                for (itemNumber = 0; itemNumber < maxItems; itemNumber++) {
                    Element item = (Element) slideItems.item(itemNumber);
                    loadSlideItem(slide, item);
                }
            }
        } catch (ParserConfigurationException pce) {
            logger.info("Error configuring parser: " + pce.getMessage());
        } catch (SAXException sax) {
            logger.info("Error parsing file: " + sax.getMessage());
        } catch (IOException iox) {
            logger.info("Error loading file: " + iox.getMessage());
        }
    }

    /**
     * Loads a slide item
     * @param slide the slide to load into
     * @param item the item to load
     */
    protected void loadSlideItem(Slide slide, Element item) {
        int level = 1; // default
        NamedNodeMap attributes = item.getAttributes();
        String leveltext = attributes.getNamedItem(StringResources.LEVEL).getTextContent();
        if (leveltext != null) {
            try {
                level = Integer.parseInt(leveltext);
            } catch (NumberFormatException nfe) {
                logger.info("Error formatting number: " + nfe.getMessage());
            }
        }
        String type = attributes.getNamedItem(StringResources.KIND).getTextContent();
        if (StringResources.TEXT.equals(type)) {
            slide.appendText(new TextItem(level, item.getTextContent()));
        } else {
            if (StringResources.IMAGE.equals(type)) {
                slide.appendText(new BitmapItem(level, item.getTextContent()));
            } else {
                logger.info("Unknown element type");
            }
        }
    }

}
