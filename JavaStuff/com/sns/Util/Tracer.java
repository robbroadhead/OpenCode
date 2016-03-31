package com.sns.Util;
/**
Tracer Class
@Version 1.0
@Author Rob Broadhead
            04-16-2000
$Id
*/


import java.io.*;
import java.util.*;

/**
* The Tracer class is used as a way to log information within a program
* to a file along with a time stamp for each occurance.  This can be used
* to check values, display messages, or for any other purpose you can find.
* You can leave the logging code in the program and just turn it on and
* off through the setActive method.
*
* The file is opened and closed within the log method so it does not leave
* the file open nor does it have to be closed.  This can be cumbersome so
* it can effect program performance.
*/
public class Tracer {
  /**
  * This just sets the file we will be writing to it does not open
  * the file.
  *
  * @param String name File that will be logged to.
  */
  public static void setFile(String name) {
    logFile_A = name;
  }

  /**
  * Deletes the file that is set for the tracer.
  */
  public static void resetLog() {
    File theFile = new File(logFile_A);
    theFile.delete();
  }

  /**
  * Stores the string sent in as a line in the log with the current
  * date and time prepended to the string.
  *
  * @param String msg The text you wish to write to the log.
  */
  public static void log(String msg) {
    // Open the file
    if (active_A == true) {
      try {
        FileWriter out = new FileWriter(logFile_A,true);
        Date dt = new Date();

        dt.getTime();
        out.write(dt + "-->" + msg + "\n");
        out.close();
      } catch (Exception e) {
      }
    }
  }

  /**
  * Set to true to enable writing log messages and to false to ignore
  * calls to the log method.
  *
  * @param boolean val Value you wish to set the active flag to.
  */
  public static void setActive(boolean val) {
    active_A = val;
  }

  // Properties
  /** A flag that is used to determine whether or not we will
  * ignore calls to the log method. */
  static private boolean active_A;
  /** The name of the file that will be logged to */
  static private String logFile_A;
} // End of class Tracer
