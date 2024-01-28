package main;

import main.accessor.DemoReadAccessor;
import main.accessor.XMLReadAccessor;
import main.exception.AccessorException;
import main.model.Presentation;
import main.ui.SlideViewerFrame;
import main.util.StringResources;

import java.io.IOException;


/**
 * JabberPoint Main Program.
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public class JabberPoint {
    /**
     * The main program
     */
    public static void main(String[] argv) throws AccessorException, IOException {

        Presentation presentation = new Presentation();
        new SlideViewerFrame(StringResources.JABVERSION, presentation);
        if (argv.length == 0) { // No args: use demo
            new DemoReadAccessor().loadFile(presentation, "");
        } else {
            new XMLReadAccessor().loadFile(presentation, argv[0]);
        }
        presentation.setSlideNumber(0);
    }

    /**
     * @param status The exit status
     */
    public static void exitApplication(int status) {
        System.exit(status);
    }
}