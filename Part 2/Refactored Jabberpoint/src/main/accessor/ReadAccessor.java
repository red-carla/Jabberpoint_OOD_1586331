package main.accessor;

import main.exception.AccessorException;
import main.model.Presentation;

import java.io.IOException;

/**
 * This Accessor is an interface for reading data
 * Subclasses should implement the load method
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

public interface ReadAccessor {

    /**
     * Loads the presentation from a file
     * @param p Presentation to load into
     * @param fn String filename to load from
     * @throws AccessorException if the file cannot be read
     * @throws IOException       if the file cannot be read
     */
    void loadFile(Presentation p, String fn) throws AccessorException, IOException;

}


