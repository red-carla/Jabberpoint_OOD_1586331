package main.accessor;

import main.exception.AccessorException;
import main.model.Presentation;

import java.io.IOException;

/**
 * This Accessor is an interface for writing data
 * Subclasses should implement the save method
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public interface WriteAccessor {

    /**
     * Saves the presentation to a file
     *
     * @param p Presentation to save
     * @param fn String filename to save to
     * @throws AccessorException if the file cannot be written to
     * @throws IOException if the file cannot be written to
     */
    void saveFile(Presentation p, String fn) throws AccessorException, IOException;


}


