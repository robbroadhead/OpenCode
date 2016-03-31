package com.sns.Apps;

import com.sns.Apps.ui.*;

/**
* This class is the main program for the application.  It uses DirectoryFrame.
*
* @see com.sns.Apps.ui.DirectoryFrame
* @version 1.0 12-Jun-2006
* @author Rob Broadhead
*/
public class Monitor {
/**
* This method loads and shows the Frame.  This is the main frame for the
* application.
*
* @param String arg[0] a <code>-?</code> to display help
* @param String arg[0] a <code>path</code> to traverse and use in the form
*/
  public static void main (String args[]) {
    MonitorFrame theFrame = null;

    if ((args.length > 1 ) || (args.length > 0 ) && (args[0].equals("-?"))) {
       /* Let the user know how to use the app */
       System.out.println("--> Apps Monitor version 1.0 ---");
       System.out.println("--> written by: Rob Broadhead");
       System.out.println("Purpose: Monitor various sites by essentially pinging them.");
       System.out.println("Usage: java Monitor [-?]");
       System.out.println(" -?     --> displays this help screen");
       System.out.println("");
       System.out.println("Example: java Monitor");
       return;
    } else {
       theFrame = new MonitorFrame("Not Used");

       if (theFrame!=null) {
          theFrame.setVisible(true);
       }
    }
  } /* End of main() */
} /* End of DirectoryMain class definition */


