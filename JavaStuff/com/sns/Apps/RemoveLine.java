package com.sns.Apps;

import com.sns.Util.Tools;

/**
*
* @version 1.0 24-Mar-2002
* @author Rob Broadhead
*/
public class RemoveLine {
/**
* This method runs the application, it is a console-based application.
*
* @param String arg[0] This is either a <code>-?</code> to display help or the file
*               name data will be loaded from.
*
*/
public static void main (String args[]) {
  Tools util = new Tools();

  /* Check out the arguments */
  if ((args.length != 1) || (args[0].equals("-?"))) {
     /* Let the user know how to use the app */
     System.out.println("--> RemoveLine version 1.0 ---");
     System.out.println("--> written by: Rob Broadhead");
     System.out.println("Purpose: remove blank lines from a file and display the results");
     System.out.println("         redirect (>) the results to create a new file.");
     System.out.println("Usage: java RemoveLine [-?] [oldfile] [newfile]");
     System.out.println(" oldfile --> The original file");
     System.out.println(" newfile --> The file generated from the new DB");
     System.out.println(" -? --> displays this help screen");
     System.out.println("");
     System.out.println("Example: java RemoveLine File.dat");
     System.out.println("Example: java RemoveLine File.dat > NewFile.dat");
     return;
  } else {
    try {
      util.RemoveLine(args[0]);
      System.out.println("Program Completed Successfully:" + args[0]);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Program failed while loading data.");
    }
  }
}
}
