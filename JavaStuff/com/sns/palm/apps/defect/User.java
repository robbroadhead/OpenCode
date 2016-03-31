package com.sns.palm.apps.defect;
/**
 * <p>Title: User</p>
 * <p>Description: This is for maintaining a user record for the Defect
 * tracker application.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */
import com.sns.palm.util.*;
import com.sns.palm.base.Reportable;

public class User extends Reportable {

  public String fname;
  public String lname;
  public String dept;

  /**
   * Simple no args constructor
   */
  public User() {
    super();

    fname="John";
    lname="Doe";
    dept="none";
    rsName = "RBUsers";
    recordSize = 65 + BASESIZE;
  }

  /**
   * A toString replacement that displays the record ina short format.
   *
   * @return a short display version of the record.
   */
  public String shortDisplay() {
    return fname.trim() + " " + lname.trim();
  }

  /**
   * This is essentially a toString replacement.
   *
   * @return a string version of the record.
   */
  public String Display() {
    return fname.trim() + " " + lname.trim();
//    return fname.trim() + " " + lname.trim() + ":" + dept.trim();
  }

  /**
   * Checks to see if the first and last name fields have been populated
   * for this record and returns false if information is missing.
   *
   * @return true if the record is a valid User
   */
  public boolean isValid() {
    return !(fname.equals("") || lname.equals(""));
  }

  /**
   * This takes a formatted byte array and parses through it to populate
   * the fields of the current record.
   *
   * @param tempIn the byte array to parse and populate the record.
   */
  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte textBytes[] = new byte[20];
    int x = BASESIZE;

    for (int count = 0;count < 20;count++) {
       textBytes[count] = tempIn[count + x];
    }
    fname = Utility.ByteToStr(textBytes);

    for (int count = 0;count < 20;count++) {
       textBytes[count] = tempIn[count + 20 + x];
    }
    lname = Utility.ByteToStr(textBytes);
    for (int count = 0;count < 20;count++) {
       textBytes[count] = tempIn[count + 40 + x];
    }
    dept = Utility.ByteToStr(textBytes);
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

    fname = Utility.padString(fname,20);
    tempOut = Utility.StrToByte(fname);
    for (int i=0;i<20;i++) {
      output[i+x] = tempOut[i];
    }
    lname = Utility.padString(lname,20);
    tempOut = Utility.StrToByte(lname);
    for (int i=0;i<20;i++) {
      output[i+20 + x] = tempOut[i];
    }
    dept = Utility.padString(dept,20);
    tempOut = Utility.StrToByte(dept);
    for (int i=0;i<20;i++) {
      output[i+40 + x] = tempOut[i];
    }
    return output;
  }
}