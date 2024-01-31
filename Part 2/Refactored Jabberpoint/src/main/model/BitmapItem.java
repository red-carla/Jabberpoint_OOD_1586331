package main.model;

import main.util.Style;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Class for drawing Bitmap images
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public class BitmapItem extends SlideItem {
    private static final Logger logger = Logger.getLogger(BitmapItem.class.getName()); // Logger
    public final String imagePath; // The path to the image
    private BufferedImage bufferedImage; // The image

    /**
     * Constructor for BitmapItem
     *
     * @param level     The level of this item
     * @param imagePath The path to the image
     */
    public BitmapItem(int level, String imagePath) {
        super(level);
        this.imagePath = imagePath;
        loadImage(imagePath);
    }

    /**
     * Loads the image
     *
     * @param imagePath The path to the image
     */
    private void loadImage(String imagePath) {
        try {
            bufferedImage = ImageIO.read(new File(imagePath));
            if (bufferedImage == null) {
                throw new IOException("Unsupported image file format or corrupted file.");
            }
        } catch (IOException e) {
            bufferedImage = null;
            logger.info("Error loading image: " + e.getMessage());
        }
    }

    /**
     * get the bounding box
     *
     * @param g        The graphics object
     * @param observer The image observer
     * @param scale    The scale
     * @param myStyle  The style
     * @return Rectangle
     */
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
        return new Rectangle((int) (myStyle.getIndent() * scale), 0,
                (int) (bufferedImage.getWidth(observer) * scale),
                ((int) (myStyle.getLeading() * scale)) +
                        (int) (bufferedImage.getHeight(observer) * scale));
    }

    /**
     * Draws the image
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param scale    The scale
     * @param g        The graphics object
     * @param myStyle  The style
     * @param observer The image observer
     */
    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
        int width = x + (int) (myStyle.getIndent() * scale);
        int height = y + (int) (myStyle.getLeading() * scale);
        g.drawImage(bufferedImage, width, height, (int) (bufferedImage.getWidth(observer) * scale),
                (int) (bufferedImage.getHeight(observer) * scale), observer);
    }

    /**
     * String conversion for debug purposes
     * @return String
     */
    public String toString() {
        return "BitmapItem[" + getLevel() + "," + imagePath + "]";
    }
}
