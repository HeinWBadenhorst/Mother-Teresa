// This snippet creates a new class that extends JInternalFrame.  Use it
// together with the DesktopFrame snippet to create the structure of an MDI
// (multiple document interface) application.

// Instructions:
// 1. Create a DesktopPane snippet.  Be sure to specify the "Name of Internal
//    Frame" parameter.  The snippet has a main method, so is usually placed
//    in a new, empty project.
// 2. Create an InternalFrame snippet, specifying the same class name.
// 3. You can now compile and run the project. Use the File menu to open new
//    internal frames.

//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:

package  motherteresa;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InternalFrame extends JInternalFrame {
  BorderLayout borderLayout1 = new BorderLayout();

  public InternalFrame() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void jbInit() throws Exception {
    this.setClosable(true);
    this.setIconifiable(true);
    this.setMaximizable(true);
    this.setResizable(true);
    this.getContentPane().setLayout(borderLayout1);
  }

}
 