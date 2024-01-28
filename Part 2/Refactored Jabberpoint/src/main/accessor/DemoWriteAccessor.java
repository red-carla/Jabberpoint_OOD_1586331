package main.accessor;

import main.model.Presentation;

/**
 * A write accessor for a demo presentation in Jabberpoint
 *
 *  @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 *  @version 1.61 2024/01/10 Carla Redmond
 */
public class DemoWriteAccessor implements WriteAccessor {
    /**
     * Save a presentation to a file
     * @param presentation The presentation to save
     * @param unusedFilename The filename to save to
     */
    public void saveFile(Presentation presentation, String unusedFilename) {
        throw new IllegalStateException("Save As->Demo! called");
    }
}
