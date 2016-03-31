package com.rb.gmail;

import com.rb.gmail.ui.*;

/**
* This class is the main program for the application.  It uses ViewerFrame.
*
* @see com.rb.gmail.ui.ViewerFrame.java
* @version 1.0 2-Feb-2010
* @author Rob Broadhead
*/
public class GmailViewMain {
/**
* This method loads and shows the Frame.  This is the main frame for the
* application.
*
* @param String arg[0] a <code>-?</code> to display help
*/
  public static void main (String args[]) {
    ViewerFrame theFrame = null;

    if ((args.length > 0 ) && (args[0].equals("-?"))) {
       /* Let the user know how to use the app */
       System.out.println("--> Gmail Viewer version 1.0 ---");
       System.out.println("--> written by: Rob Broadhead");
       System.out.println("Purpose: View Gmail mails and search.");
       System.out.println("Usage: java GmailViewMain [-?] [path]");
       System.out.println(" -?     --> displays this help screen");
       System.out.println("");
       System.out.println("Example: java GmailViewMain");
       return;
    } else {
       if (args.length < 2) {
          theFrame = new ViewerFrame();
       }

       if (theFrame!=null) {
          theFrame.setVisible(true);
       }
    }
  } /* End of main() */
} /* End of DirectoryMain class definition */


