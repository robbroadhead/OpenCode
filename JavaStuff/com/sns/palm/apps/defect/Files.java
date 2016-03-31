package com.sns.palm.apps.defect;
/**
 * <p>Title: Files</p>
 * <p>Description: This is for maintaining a Files record for the Defect
 * tracker application.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */
import com.sns.palm.util.*;
import com.sns.palm.base.Reportable;

public class Files extends Reportable {

  public String name;
  public String location;
  public String type;
  public int ownerid;

  /**
   * Simple no args constructor
   */
  public Files() {
    super();

    name="MyFile.doc";
    location="c:\\";
    type="Word";
    ownerid=-1;
    
    rsName = "RBFiles";
    recordSize = 145 + BASESIZE;
  }

  /**
   * A toString replacement that displays the record in a short format.
   *
   * @return a short display version of the record.
   */
  public String shortDisplay() {
    return name.trim();
  }

  /**
   * This is essentially a toString replacement.
   *
   * @return a string version of the record.
   */
  public String Display() {
    return location.trim() + "\\" + name.trim();
  }

  /**
   * Checks to see if the first and last name fields have been populated
   * for this record and returns false if information is missing.
   *
   * @return true if the record is a valid Files object
   */
  public boolean isValid() {
    return !(name.equals("") || location.equals(""));
  }

  /**
   * This takes a formatted byte array and parses through it to populate
   * the fields of the current record.
   *
   * @param tempIn the byte array to parse and populate the record.
   */
  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte idBytes[] = new byte[4];
    byte locBytes[] = new byte[100];
    byte nameBytes[] = new byte[20];
    int x = BASESIZE;

    for (int count = 0;count < 20;count++) {
       nameBytes[count] = tempIn[count + x];
    }
    name = Utility.ByteToStr(nameBytes);

    for (int count = 0;count < 100;count++) {
       locBytes[count] = tempIn[count + 20 + x];
    }
    location = Utility.ByteToStr(locBytes);

    for (int count = 0;count < 20;count++) {
       nameBytes[count] = tempIn[count + 120 + x];
    }
    type = Utility.ByteToStr(nameBytes);

    /* Convert the int to a Hex string for storage. */
    for (int count = 0;count < 4;count++) {
       idBytes[count] = tempIn[count + x + 140];
    }
    String theValue = Utility.ByteToStr(idBytes).trim();
    try {
      ownerid = Integer.parseInt(theValue,16);
    } catch (NumberFormatException nfe) {
      /* It seems that an exception is always thrown.*/
    }
  }

  /**
   * This is used to convert the record to a byte array for storage
   * in a record store.
   *
   * @param output the byte array to be filled in.
   * @return the record as a byte array
   */
  public byte[] toByteArray(byte[] output) {
    /* Build the output array */
    byte tempOut[];
    int x = BASESIZE;

    name = Utility.padString(name,20);
    tempOut = Utility.StrToByte(name);
    for (int i=0;i<20;i++) {
      output[i+x] = tempOut[i];
    }
    location = Utility.padString(location,100);
    tempOut = Utility.StrToByte(location);
    for (int i=0;i<100;i++) {
      output[i+ 20 + x] = tempOut[i];
    }
    type = Utility.padString(type,20);
    tempOut = Utility.StrToByte(type);
    for (int i=0;i<20;i++) {
      output[i+120 + x] = tempOut[i];
    }

    String theInt = Utility.padString(Integer.toHexString(ownerid),4);
    tempOut = Utility.StrToByte(theInt);
    for (int i=0;i<4;i++) {
      output[i + 140 + x] = tempOut[i];
    }
    return output;
  }
}