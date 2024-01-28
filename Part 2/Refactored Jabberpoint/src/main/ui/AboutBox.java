package main.ui;

import javax.swing.*;

/**
 * About box for JabberPoint
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.61 2024/01/10 Carla Redmond
 */

class AboutBox {
    /**
     * Utility class
     * Prevents instantiation
     */
    private AboutBox() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Show the AboutBox
     *
     * @param parent The parent frame
     */
    public static void show(JFrame parent) {
        JOptionPane.showMessageDialog(parent, """
                        JabberPoint is a primitive slide-show program in Java(tm).
                        It is freely copyable as long as you keep this notice and the splash screen intact.
                        Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.
                        Adapted by Gert Florijn (version 1.1) and Sylvia Stuurman (version 1.2 and higher)
                        for the Open University of the Netherlands, 2002 -- now.
                        Author's version available from https://www.darwinsys.com/""", "About JabberPoint",
                JOptionPane.INFORMATION_MESSAGE);
    }
}