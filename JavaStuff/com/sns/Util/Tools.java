package com.sns.Util;

import java.io.*;

/**
* @version 1.0 21-Mar-2002
* @author Rob Broadhead
*/
public class Tools {
/**
* Just the default no-args constructor
*/
public Tools () {}

/**
* This method compares the data using string comparisons, line by line, in the
* target file (newFile) to the data in the source file (newFile). Any lines that
* do not compared are sent to standard out.
*
* @param String oldFile
* @param String newFile
* @throws IOException Thrown if an error occurs reading in the data from the file.
*/
public void loadData (String oldFile,String newFile) throws IOException {
  /* Variables we'll use */
  String[] recString=new String[15];
  String oldLine;
  String newLine;
  BufferedReader oldBR,newBR;
  InputStreamReader ioOld;
  InputStreamReader ioNew;
  FileInputStream oldStream;
  FileInputStream newStream;
  int i, theCount;
  boolean error=false;

  /* Open the old file */
  try {
    oldStream = new FileInputStream(oldFile);
    ioOld = new InputStreamReader(oldStream);
    oldBR = new BufferedReader(ioOld);
  } catch (FileNotFoundException e) {
    System.out.println("The file [" + oldFile + "] does not exist");
    throw new IOException();
  }

  /* Open the new file */
  try {
    newStream = new FileInputStream(newFile);
    ioNew = new InputStreamReader(newStream);
    newBR = new BufferedReader(ioNew);
  } catch (FileNotFoundException e) {
    System.out.println("The file [" + newFile + "] does not exist");
    throw new IOException();
  }

  /* Get each line */
  do {
    oldLine = oldBR.readLine();
    newLine = newBR.readLine();

    if ((oldLine != null) && (newLine != null)) {
      if (!oldLine.trim().equals(newLine.trim())) {
        error=true;
      }
    }
    if ((error) && (oldLine.indexOf("----")==-1)) {
      System.out.println("Error " + oldFile +":" + oldLine.trim() + "\n\r" + newFile+ ":" + newLine.trim());
    }

    error=false;
  } while (oldLine!=null);
} /* End of loadData(oldFile,newFile) */


/**
* This method removes blank lines from the source file (file) and prints out the
* remaining lines to standard out. A blank line is a line that conatins only white
* space.
*
* @param String file
* @throws IOException Thrown if an error occurs reading in the data from the file.
*/
public void RemoveLine (String file) throws IOException {
  /* Variables we'll use */
  String curLine;
  BufferedReader BR;
  InputStreamReader io;
  FileInputStream curStream;
  int i, theCount;
  boolean error=false;

  /* Open the file */
  try {
    curStream = new FileInputStream(file);
    io = new InputStreamReader(curStream);
    BR = new BufferedReader(io);
  } catch (FileNotFoundException e) {
    System.out.println("The file [" + file + "] does not exist");
    throw new IOException();
  }

  curLine = BR.readLine();
  /* Get each line */
  do {
    if (curLine.trim().length()>2) {
      System.out.println(curLine.trim());
    }
    curLine = BR.readLine();

    error=false;
  } while (curLine!=null);
} /* End of RemoveLine(file) */

} /* End of class Tools definition */
