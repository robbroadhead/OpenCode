package com.sns.palm.apps.defect;
/**
 * <p>Title: Project</p>
 * <p>Description: This is for maintaining a project record for the Defect
 * tracker application.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */
import com.sns.palm.util.*;
import com.sns.palm.base.Reportable;

public class Project extends Reportable {

  public String name;
  public String mjVer;
  public String mnVer;
  public String note;
  public int userid;

  /**
   * Simple no args constructor
   */
  public Project() {
    super();

    name="X";
    mjVer="1";
    mnVer="0";
    note="";
    userid=-1;
    rsName = "RBProjects";
    recordSize = 95 + BASESIZE;
  }

  /**
   * A toString replacement that displays the record ina short format.
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
    return name.trim() + " " + mjVer.trim() + "." + mnVer.trim();
  }

  /**
   * Checks to see if the first and last name fields have been populated
   * for this record and returns false if information is missing.
   *
   * @return true if the record is a valid Project
   */
  public boolean isValid() {
    return !(name.equals("") || userid==-1);
  }

  /**
   * This takes a formatted byte array and parses through it to populate
   * the fields of the current record.
   *
   * @param tempIn the byte array to parse and populate the record.
   */
  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte verBytes[] = new byte[4];
    byte noteBytes[] = new byte[60];
    byte nameBytes[] = new byte[20];
    int x = BASESIZE;

    for (int count = 0;count < 20;count++) {
       nameBytes[count] = tempIn[count + x];
    }
    name = Utility.ByteToStr(nameBytes);

    for (int count = 0;count < 4;count++) {
       verBytes[count] = tempIn[count + 20 + x];
    }
    mjVer = Utility.ByteToStr(verBytes);
    for (int count = 0;count < 4;count++) {
       verBytes[count] = tempIn[count + 24 + x];
    }
    mnVer = Utility.ByteToStr(verBytes);
    for (int count = 0;count < 60;count++) {
       noteBytes[count] = tempIn[count + 28 + x];
    }
    note = Utility.ByteToStr(noteBytes);

    /* Convert the int to a Hex string for storage. */
    for (int count = 0;count < 4;count++) {
       verBytes[count] = tempIn[count + x + 88];
    }
    String theValue = Utility.ByteToStr(verBytes).trim();
    try {
      userid = Integer.parseInt(theValue,16);
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
    Tracer.setActive(true);
Tracer.log("Entered Project::toByteArray",Tracer.LOG);
    name = Utility.padString(name,20);
    tempOut = Utility.StrToByte(name);
    for (int i=0;i<20;i++) {
      output[i+x] = tempOut[i];
    }
    mjVer = Utility.padString(mjVer,4);
    tempOut = Utility.StrToByte(mjVer);
    for (int i=0;i<4;i++) {
      output[i+20 + x] = tempOut[i];
    }
    mnVer = Utility.padString(mnVer,4);
    tempOut = Utility.StrToByte(mnVer);
    for (int i=0;i<4;i++) {
      output[i+24 + x] = tempOut[i];
    }
    note = Utility.padString(note,60);
    tempOut = Utility.StrToByte(note);
    for (int i=0;i<60;i++) {
      output[i+28 + x] = tempOut[i];
    }
    String theInt = Utility.padString(Integer.toHexString(userid),4);
    tempOut = Utility.StrToByte(theInt);
    for (int i=0;i<4;i++) {
      output[i + 88 + x] = tempOut[i];
    }
Tracer.log("Leaving Project::toByteArray",Tracer.LOG);
    return output;
  }
}