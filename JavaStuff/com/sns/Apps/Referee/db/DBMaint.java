package com.sns.Apps.Referee.db;

import java.util.*;
import java.io.*;

/**
* This class provides database creation and loading services.
* All services are specific to the referee application.
*
* @see com.sns.Apps.Referee.db.Data
* @version 1.0 27-Mar-2001
* @author Rob Broadhead
*/
public class DBMaint {
/**
* Just the default no-args constructor
*/
public DBMaint () {}

/**
* This method loads data into the database supplied in the first
* parameter.  It assumes the file uses a caret (^) symbol to
* separate the fields and each line defines a row.
*
* @param Data dest The name of the database to load.
* @param String src The name of the file to load data from.
* @throws IOException Thrown if an error occurs reading in the data from the file.
*/
public void loadData (Data dest,String src) throws IOException {
   /* Variables we'll use */
   String[] recString=new String[15];
   String curLine;
   StringTokenizer fields;
   BufferedReader srcFile;
   InputStreamReader io;
   FileInputStream fStream;
   int i, theCount;

   /* Open the source file */
   try {
      fStream = new FileInputStream(src);
      io = new InputStreamReader(fStream);
      srcFile = new BufferedReader(io);
   } catch (FileNotFoundException e) {
      System.out.println("The source file [" + src + "] does not exist");
      throw new IOException();
   }

   /* We need the db structure to properly pad fields */
//   dbInfo = dest.getFieldInfo();
   theCount = 0;
   /* Get a line */
   do {
      curLine = srcFile.readLine();
      if (curLine != null) {

        /* Parse the line */
        fields = new StringTokenizer(curLine,"^");

        /* Create the String array */
        i=0;
        recString[i++] = "L" + theCount++;
        while (fields.hasMoreTokens()) {
          recString[i++] = fields.nextToken();
        }

        /* Add the record */
        try {
          dest.add(recString);
        } catch (Exception e) {
          System.out.println("Error loading record:");
          System.out.println(curLine);
        }
      }
   } while (curLine!=null);
} /* End of loadData(Data) */

/**
* This method loads data into the database supplied in the first
* parameter.  It assumes the file uses a caret (^) symbol to
* separate the fields and each line defines a row.
*
* @param Data dest The name of the database to load.
* @param String src The name of the file to load data from.
* @throws IOException Thrown if an error occurs reading in the data from the file.
*/
public void loadRefData (Data dest,String src) throws IOException {
   /* Variables we'll use */
   String[] recString=new String[14];
   String[] loadString=new String[3];
   String curLine;
   StringTokenizer fields;
   BufferedReader srcFile;
   InputStreamReader io;
   FileInputStream fStream;
   int i, theCount;

   /* Open the source file */
   try {
      fStream = new FileInputStream(src);
      io = new InputStreamReader(fStream);
      srcFile = new BufferedReader(io);
   } catch (FileNotFoundException e) {
      System.out.println("The source file [" + src + "] does not exist");
      throw new IOException();
   }

   /* We need the db structure to properly pad fields */
   theCount = 0;
   /* Get a line */
   do {
      curLine = srcFile.readLine();
      if (curLine != null) {

        /* Parse the line */
        fields = new StringTokenizer(curLine,"^");

        /* Create the String array */
        i=0;
        recString[0] = "L" + theCount++;
        while (fields.hasMoreTokens()) {
          loadString[i++] = fields.nextToken();
        }

        recString[1]=loadString[0];
        recString[2]=loadString[1];
        recString[3]=loadString[2];
        recString[4]="1/1/2001";
        recString[5]="U8G";
        recString[6]="U8G";
        recString[7]="8";
        recString[8]="N";
        recString[9]="Y";
        recString[10]="WCSA";
        recString[11]="NA";
        recString[12]="None";
        recString[13]="Loaded Data";

        /* Add the record */
        try {
          dest.add(recString);
        } catch (Exception e) {
          System.out.println("Error loading record: " + recString);
          e.printStackTrace();
          System.out.println(curLine);
          System.exit(-1);
        }
      }
   } while (curLine!=null);
} /* End of loadRefData(Data) */

/**
* This method creates a database with the structure required for the referee
* application.
*
* @param String dbName The name of the database to create.
* @param FieldInfo[] fields Array of fields to create the database structure.
* @throws IOException Thrown if database already exists.
*/
public void createDB (String dbName, FieldInfo[] fields) throws IOException {
	/* Create a file */
	Data theDB;

   /* Assign to null for consistency sake */
   theDB = null;

   /* Create the database */
   theDB = new Data(dbName,fields);
   theDB.close();
} /* End of createDB(String) */

/**
* This method creates the structure used by the Referee application by way
* of creating an array of FieldInfo to be used in the createDB method.
*
* @return FieldInfo[] An array of FieldInfo that defines the structure
* @see FieldInfo
*/
public FieldInfo[] createGameStructure () {
	 /* Our local variables */
	 FieldInfo fields[]=new FieldInfo[15];
	 FieldInfo temp;

	 /* Create the database structure */
	 temp = new FieldInfo("Key",6);
	 fields[0]=temp;
	 temp = new FieldInfo("Home",25);
	 fields[1]=temp;
	 temp = new FieldInfo("Visitor",25);
	 fields[2]=temp;
	 temp = new FieldInfo("Home Score",2);
	 fields[3]=temp;
	 temp = new FieldInfo("Vis Score",2);
	 fields[4]=temp;
	 temp = new FieldInfo("Referee 1",25);
	 fields[5]=temp;
	 temp = new FieldInfo("Referee 2",25);
	 fields[6]=temp;
	 temp = new FieldInfo("Date",11);
	 fields[7]=temp;
	 temp = new FieldInfo("Time",5);
	 fields[8]=temp;
	 temp = new FieldInfo("Association",4);
	 fields[9]=temp;
	 temp = new FieldInfo("Pay",6);
	 fields[10]=temp;
	 temp = new FieldInfo("Paid",1);
	 fields[11]=temp;
	 temp = new FieldInfo("Age",4);
	 fields[12]=temp;
	 temp = new FieldInfo("Center",1);
	 fields[13]=temp;
	 temp = new FieldInfo("Indoor",1);
	 fields[14]=temp;

  return fields;
} /* End of createGameStructure() */


/**
* This method creates the structure used by the Referee application by way
* of creating an array of FieldInfo to be used in the createDB method.
*
* @return FieldInfo[] An array of Fiel dInfo that defines the structure
* @see FieldInfo
*/
public FieldInfo[] createRefStructure () {
	 /* Our local variables */
	 FieldInfo fields[]=new FieldInfo[14];
	 FieldInfo temp;

	 /* Create the database structure */
	 temp = new FieldInfo("Key",6);
	 fields[0]=temp;
	 temp = new FieldInfo("Last Name",15);
	 fields[1]=temp;
	 temp = new FieldInfo("First Name",15);
	 fields[2]=temp;
	 temp = new FieldInfo("Phone",12);
	 fields[3]=temp;
	 temp = new FieldInfo("Birth Date",10);
	 fields[4]=temp;
	 temp = new FieldInfo("Top Center",4);
	 fields[5]=temp;
	 temp = new FieldInfo("Top Line",4);
	 fields[6]=temp;
	 temp = new FieldInfo("Grade",2);
	 fields[7]=temp;
	 temp = new FieldInfo("Indoor",1);
	 fields[8]=temp;
	 temp = new FieldInfo("Active",1);
	 fields[9]=temp;
	 temp = new FieldInfo("Association",4);
	 fields[10]=temp;
	 temp = new FieldInfo("2nd Phone",12);
	 fields[11]=temp;
	 temp = new FieldInfo("Email",50);
	 fields[12]=temp;
	 temp = new FieldInfo("Notes",80);
	 fields[13]=temp;

  return fields;
} /* End of createRefStructure() */
} /* End of class DBMaint definition */
