package main.accessor;

import main.model.BitmapItem;
import main.model.Presentation;
import main.model.Slide;

/**
 * A read accessor for a demo presentation in Jabberpoint
 *
 *  @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 *  @version 1.61 2024/01/10 Carla Redmond
 */
public class DemoReadAccessor implements ReadAccessor {
    /**
     * Load a presentation from a file
     * @param presentation The presentation to load
     * @param unusedFilename The filename to load from
     */
    public void loadFile(Presentation presentation, String unusedFilename) {
        presentation.setTitle("Demo Presentation");
        Slide slide;
        slide = new Slide();
        slide.setTitle("JabberPoint");
        slide.appendText(1, "The Java presentation tool");
        slide.appendText(2, "Copyright (c) 1996-2000: Ian Darwin");
        slide.appendText(2, "Copyright (c) 2000-now:");
        slide.appendText(2, "Gert Florijn and Sylvia Stuurman");
        slide.appendText(4, "Calling Jabberpoint without a filename");
        slide.appendText(4, "will show this presentation");
        slide.appendText(1, "Navigate:");
        slide.appendText(3, "Next slide: PgDn or Enter");
        slide.appendText(3, "Previous slide: PgUp or up-arrow");
        slide.appendText(3, "Quit: q or Q");
        presentation.append(slide);

        slide = new Slide();
        slide.setTitle("Demonstration of levels and styles");
        slide.appendText(1, "Level 1");
        slide.appendText(2, "Level 2");
        slide.appendText(1, "Again level 1");
        slide.appendText(1, "Level 1 has style number 1");
        slide.appendText(2, "Level 2 has style number 2");
        slide.appendText(3, "This is how level 3 looks like");
        slide.appendText(4, "And this is level 4");
        presentation.append(slide);

        slide = new Slide();
        slide.setTitle("The third slide");
        slide.appendText(1, "To open a new presentation,");
        slide.appendText(2, "use File->Open from the menu.");
        slide.appendText(1, " ");
        slide.appendText(1, "This is the end of the presentation.");
        slide.appendText(new BitmapItem(1, "Part 2/Refactored Jabberpoint/resources/img/JabberPoint.jpg"));
        presentation.append(slide);
    }

}
