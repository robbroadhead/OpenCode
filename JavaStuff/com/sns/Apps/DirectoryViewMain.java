package com.sns.Apps;

import com.sns.Apps.ui.*;

/**
* This class is the main program for the application.  It uses DirectoryFrame.
*
* @see com.sns.Apps.ui.DirectoryFrame
* @version 1.0 12-Jun-2006
* @author Rob Broadhead
*/
public class DirectoryViewMain {
/**
* This method loads and shows the Frame.  This is the main frame for the
* application.
*
* @param String arg[0] a <code>-?</code> to display help
* @param String arg[0] a <code>path</code> to traverse and use in the form
*/
  public static void main (String args[]) {
    DirectoryFrame theFrame = null;

    if ((args.length < 1 ) ||(args.length > 0 ) && (args[0].equals("-?"))) {
       /* Let the user know how to use the app */
       System.out.println("--> Directory View version 1.0 ---");
       System.out.println("--> written by: Rob Broadhead");
       System.out.println("Purpose: View the structure and stats for a given path");
       System.out.println("Usage: java Directory [-?] [path]");
       System.out.println(" -?     --> displays this help screen");
       System.out.println(" path   --> The path to recurse and display");
       System.out.println("");
       System.out.println("Example: java Directory /usr/local/project");
       return;
    } else {
       if (args.length < 2) {
          theFrame = new DirectoryFrame(args[0]);
       }

       if (theFrame!=null) {
          theFrame.setVisible(true);
       }
    }
  } /* End of main() */
} /* End of DirectoryMain class definition */


