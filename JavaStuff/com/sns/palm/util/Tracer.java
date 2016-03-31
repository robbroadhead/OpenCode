package com.sns.palm.util;
/**
Tracer Class
@Version 1.0
@Author Rob Broadhead
            04-16-2000
$Id
*/
import javax.microedition.rms.*;

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
  static public final byte MISC=0;
  static public final byte MSG=1;
  static public final byte LOG=2;
  static public final byte ERROR=3;
  static public final byte SEVERE=4;

  // Properties
  /** A flag that is used to determine whether or not we will
  * ignore calls to the log method. */
  static private boolean active_A;
  /** The name of the file that will be logged to */
  static private String logFile_A;

  /* Set some default values for the class.*/
  {
    active_A = false;
    logFile_A = "defaultLog";
  }

  /**
  * This just sets the file we will be writing to it does not open
  * the file.
  *
  * @param name File that will be logged to.
  */
  public static void setFile(String name) {
    logFile_A = name;
  }

  /**
  * Deletes the file that is set for the tracer.
  */
  public static void resetLog() {
    try {
      RecordStore rs = RecordStore.openRecordStore(logFile_A,true);
      rs.closeRecordStore();
      RecordStore.deleteRecordStore(logFile_A);
      rs.closeRecordStore();
    } catch (Exception e) {
      // Just eat the exception since we have nowhere to store it.
      Tracer.log("Failed to delete " + logFile_A,(byte) -1);
    }
  }

  /**
  * Stores the string sent in as a line in the log with the current
  * date and time prepended to the string.
  *
  * @param msg The text you wish to write to the log.
  * @param code the byte code for categorizing the log message
  */
  public static void log(String msg,byte code) {
    // Open the file
    if (active_A == true) {
      RecordStore rs = Utility.openRecordStore(logFile_A);
      try {
        msg = "  " + msg;
        msg = Utility.padString(msg,42);
        byte[] out = Utility.StrToByte(msg);
        out[0] = (byte) rs.getNumRecords();
        out[1] = code;
        rs.addRecord(out,0,out.length);
        rs.closeRecordStore();
      } catch (Exception e) {
        // Just eat the exception since we have nowhere to store it.
      }
    }
  }

  /**
   * This is used to list items that have been stored to a log file. It only
   * displays up to 40 characters in order to ease reading the report.
   *
   * @param table the record store to use to build the list.
   * @return the string array representing the items.
   */
  static public String[] listItems(String table) {
    /* Initialize local variables */
    String[] retVal = null;
    byte textBytes[] = new byte[40];
    int numRecs=0;
    RecordStore temprs;

    temprs = Utility.openRecordStore(table);
    if (temprs == null) {
      retVal = new String[1];
      retVal[0] = "Error opening file";
      return retVal;
    }

    /* Return if we don't have any records */
    try {
      numRecs = temprs.getNumRecords();
      if (numRecs == 0) {
        retVal = new String[1];
        retVal[0] = "No records Entered";
        return retVal;
      }
    } catch (Exception e) {
      retVal = new String[1];
      retVal[0] = "Error getting records";
      return retVal;
    }
    retVal = new String[numRecs];

    for (int i=0;i < numRecs;i++) {
      try {
        byte[] input = temprs.getRecord(i + 1);
        for (int count = 0;count < 40;count++) {
           textBytes[count] = input[count + 2];
        }
        retVal[i] = "ID:" + input[1] + "->" + Utility.ByteToStr(textBytes);
      } catch (Exception e) {
        retVal[i] = "Error getting record:" + i;
        return retVal;
      }
    }

    try {
      temprs.closeRecordStore();
    } catch (Exception e) {}

    return retVal;
  }

  /**
   * Builds a string array of the codes available to a logged message.
   *
   * @return the string array of codes
   */
  static public String[] loadCodes() {
    /* Initialize local variables */
    String[] retVal;
    retVal=new String[5];

    retVal[0] = "Misc";
    retVal[1] = "Msg";
    retVal[2] = "Log";
    retVal[3] = "Error";
    retVal[4] = "Severe";


    return retVal;
  }

  /**
  * Set to true to enable writing log messages and to false to ignore
  * calls to the log method.
  *
  * @param val Value you wish to set the active flag to.
  */
  public static void setActive(boolean val) {
    active_A = val;
  }
} // End of class Tracer
