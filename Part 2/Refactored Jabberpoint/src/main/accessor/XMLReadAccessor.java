package main.accessor;

import main.exception.AccessorException;
import main.model.BitmapItem;
import main.model.Presentation;
import main.model.Slide;
import main.model.TextItem;
import main.util.TextEnums;
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

/**
 * This Accessor makes it possible to read XML data
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */
public class XMLReadAccessor implements ReadAccessor {

    private static final Logger logger = Logger.getLogger(XMLReadAccessor.class.getName()); // Logger

    /**
     * Gets the title
     *
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
     *
     * @param presentation Presentation to load into
     * @param filename String filename to load from
     * @throws AccessorException if the file cannot be read
     * @throws IOException if the file cannot be read
     */
    public void loadFile(Presentation presentation, String filename) throws AccessorException, IOException {
        try {
            Document document = parseXmlFile(filename);
            processPresentation(presentation, document);
        } catch (ParserConfigurationException pce) {
            logger.info("Error configuring parser: " + pce.getMessage());
        } catch (SAXException sax) {
            logger.info("Error parsing file: " + sax.getMessage());
        } catch (IOException iox) {
            logger.info("Error loading file: " + iox.getMessage());
        }
    }

    /**
     * Initializes the XML parser
     *
     * @return the document builder
     * @throws ParserConfigurationException if the parser cannot be configured
     */
    private DocumentBuilder initializeXmlParser() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbFactory.setXIncludeAware(false);
        dbFactory.setExpandEntityReferences(false);
        return dbFactory.newDocumentBuilder();
    }

    /**
     * Parses an XML file
     *
     * @param filename String filename to load from
     * @return the document
     * @throws ParserConfigurationException if the parser cannot be configured
     * @throws SAXException if the file cannot be parsed
     * @throws IOException if the file cannot be read
     */
    private Document parseXmlFile(String filename) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = initializeXmlParser();
        return builder.parse(new File(filename));
    }

    /**
     * Processes the presentation
     *
     * @param presentation the presentation
     * @param document the document
     */
    private void processPresentation(Presentation presentation, Document document) {
        Element doc = document.getDocumentElement();
        presentation.setTitle(getTitle(doc, main.util.TextEnums.SHOWTITLE.getName()));

        NodeList slides = doc.getElementsByTagName(main.util.TextEnums.SLIDE.getName());
        for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
            processSlide(presentation, (Element) slides.item(slideNumber));
        }
    }

    /**
     * Processes a slide
     *
     * @param presentation the presentation
     * @param xmlSlide the xml slide
     */
    private void processSlide(Presentation presentation, Element xmlSlide) {
        Slide slide = new Slide();
        slide.setTitle(getTitle(xmlSlide, TextEnums.SLIDETITLE.getName()));
        presentation.append(slide);

        NodeList slideItems = xmlSlide.getElementsByTagName(TextEnums.ITEM.getName());
        for (int itemNumber = 0; itemNumber < slideItems.getLength(); itemNumber++) {
            Element item = (Element) slideItems.item(itemNumber);
            loadSlideItem(slide, item);
        }
    }

    /**
     * Loads a slide item
     *
     * @param slide the slide to load into
     * @param item the item to load
     */
    protected void loadSlideItem(Slide slide, Element item) {
        int level = 1; // default
        NamedNodeMap attributes = item.getAttributes();
        String leveltext = attributes.getNamedItem(TextEnums.LEVEL.getName()).getTextContent();
        if (leveltext != null) {
            try {
                level = Integer.parseInt(leveltext);
            } catch (NumberFormatException nfe) {
                logger.info("Error formatting number: " + nfe.getMessage());
            }
        }
        String type = attributes.getNamedItem(TextEnums.KIND.getName()).getTextContent();
        if (TextEnums.TEXT.getName().equals(type)) {
            slide.appendText(new TextItem(level, item.getTextContent()));
        } else {
            if (TextEnums.IMAGE.getName().equals(type)) {
                slide.appendText(new BitmapItem(level, item.getTextContent()));
            } else {
                logger.info("Unknown element type");
            }
        }
    }

}
