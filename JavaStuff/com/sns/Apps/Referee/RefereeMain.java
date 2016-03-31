package com.sns.Apps.Referee;

import com.sns.Apps.Referee.ui.*;

/**
* This class is the main program for the RefereeMain application.  It uses RefFrame.
*
* @see com.sns.Apps.Referee.ui.RefFrame
* @version 1.0 01-Apr-2001
* @author Rob Broadhead
*/
public class RefereeMain {
/**
* This method loads and shows the RefFrame.  This is the main frame for the
* Referee system on the client side.
*
* @param String arg[0] a <code>-?</code> to display help
* @param String arg[0] a <code>servername</code> address of the server to use a network DB
*/
  public static void main (String args[]) {
    RefFrame theFrame = null;

    if ((args.length > 1 ) && (args[0].equals("-?"))) {
       /* Let the user know how to use the app */
       System.out.println("--> RefereeMain version 1.0 ---");
       System.out.println("--> written by: Rob Broadhead");
       System.out.println("Purpose: Enter game and Referee data into a local or network DB");
       System.out.println("Usage: java RefereeMain [-?] [db file] [server ip]");
       System.out.println(" -? --> displays this help screen");
       System.out.println(" db file   --> The file name of the database");
       System.out.println(" server ip --> IP address of the location of the network DB");
       System.out.println("");
       System.out.println("Example: java RefereeMain myDB 12.98.14.23");
       return;
    } else {
       if (args.length < 1) {
          theFrame = new RefFrame();
       }

       /* Assume the arg is an ip address if it isnt a -? */
       if ((args.length == 2) && (args[1].length() > 2)) {
          /* Use a networked database */
          theFrame = new RefFrame(true,args[0],args[1]);
       }

       if (args.length == 1) {
          /* Use a local database */
          theFrame = new RefFrame(false,args[0],"");
       }

       if (theFrame!=null) {
          theFrame.setVisible(true);
       }
    }
  } /* End of main() */
} /* End of RefereeMain class definition */


