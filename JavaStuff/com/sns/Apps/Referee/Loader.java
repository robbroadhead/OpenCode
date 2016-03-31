package com.sns.Apps.Referee;

import java.io.*;
import com.sns.Apps.Referee.db.*;

/**
* This class is the main program for the Loader application.  It uses Data,
* FieldInfo and DBMaint.
*
* @see com.sns.Apps.Referee.db.Data
* @see com.sns.Apps.Referee.db.FieldInfo
* @see com.sns.Apps.Referee.db.DBMaint
* @version 1.0 01-Apr-2001
* @author Rob Broadhead
*/
public class Loader {
/**
* This method runs the application, it is a console-based application.
*
* @param String arg[0] This is either a <code>-?</code> to display help or the file
*               name data will be loaded from.
* @param String arg[1] This is the database to load data into.
*
*/
public static void main (String args[]) {
  DBMaint dbUtil = new DBMaint();
  Data theDB;
  FieldInfo[] theStruct = new FieldInfo[9];

  /* Check out the arguments */
  if ((args.length != 2) || (args[0].equals("-?"))) {
     /* Let the user know how to use the app */
     System.out.println("--> Loader version 1.0 ---");
     System.out.println("--> written by: Rob Broadhead");
     System.out.println("Purpose: to load files into a database defined in Data.java");
     System.out.println("Usage: java Loader [-?] [src] [db]");
     System.out.println(" src --> The file to be loaded");
     System.out.println(" db --> The database to load data into");
     System.out.println(" -? --> displays this help screen");
     System.out.println("");
     System.out.println("Example: java Loader data.dat refMain");
     return;
  } else {
    /* Create the database if we need to */
    theStruct = dbUtil.createGameStructure();
    try {
      dbUtil.createDB(args[1], theStruct);
    } catch (IOException e) {
      System.out.println("Unable to create the database.");
      System.exit(0);
    }

    try {
      theDB = new Data(args[1]);
      dbUtil.loadData(theDB,args[0]);
      System.out.println("Program Completed Successfully");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Program failed while loading data.");
    }
  }
}
}
