package com.sns.palm.apps.defect;
/**
 * <p>Title: Defect.java</p>
 * <p>Description:
 * The driving class for the Defect tracking application. This handles record
 * storage and retrieval as well as most utility and maintenance functions for
 * a defect.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sleepless Nights Software</p>
 * @author Rob Broadhead
 * @version 1.0
 */
import com.sns.palm.util.*;
import com.sns.palm.base.Reportable;
import com.sns.palm.util.DefectUtils;

public class Defect extends Reportable {

  public long enterDate;
  public long statusDate;
  public int application;
  public int file;
  public String title;
  public String desc;
  public String notes;
  public int ver;
  public int ver_min;
  public byte priority;
  public byte status;
  public byte category;
  public int assigned;
  static public int theStatus;

  /**
   * This constructor sets all values to 0 except for the application, status
   * and assigned field which are all set to -1.
   */
  public Defect() {
    super();
    file = 0;
    title = "New Defect";
    desc = "";
    notes = "";

    enterDate = 0;
    statusDate = 0;

    application=-1;
    ver=0;
    ver_min=0;
    priority=-1;
    status=-1;
    category=-1;
    assigned=-1;
    rsName = "DefectData";
    recordSize = 945 + BASESIZE;
  }

  /**
   *  A shorter display for this item.
   *
   * @return the short string version of this class.
   */
  public String shortDisplay() {
    return title.trim();
  }

  /** Typical report line format for this item.
   *
   * @return the regular string version of this class.
   */
  public String Display() {
    return ID + "|" + title.trim() + "-->" + DefectUtils.StatusString(status).trim() + ":Priority " + priority;
  }

  /**
   * This method converts the class into a string for display or debugging
   * purposes.
   *
   * @return the class as a string
   */
  public String toString() {
    String retVal;

    retVal = title.trim() + "-->" + DefectUtils.StatusString(status).trim() + ":Priority " + priority;
    return retVal;
  }

  /**
   * This is used to filter records.
   *
   * @return true if the instance matches the filter value.
   */
  public boolean checker() {
    return (status == Defect.theStatus);
  }

  public boolean isValid() {
    return !(title.equals("") || desc.equals(""));
  }

  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte textBytes[] = new byte[60];
    byte longBytes[] = new byte[400];
    byte eightBytes[] = new byte[8];
    int x = BASESIZE;

    ver = (int) tempIn[x];
    ver_min = (int) tempIn[1 + x];
    priority = tempIn[2 + x];
    status = tempIn[3 + x];

    eightBytes[0] = tempIn[4 + x];
    eightBytes[1] = tempIn[5 + x];
    eightBytes[2] = tempIn[6 + x];
    eightBytes[3] = tempIn[7 + x];
    eightBytes[4] = tempIn[8 + x];
    eightBytes[5] = tempIn[9 + x];
    eightBytes[6] = tempIn[10 + x];
    eightBytes[7] = tempIn[11 + x];
    enterDate = Utility.ByteToLong(eightBytes);

    eightBytes[0] = tempIn[12 + x];
    eightBytes[1] = tempIn[13 + x];
    eightBytes[2] = tempIn[14 + x];
    eightBytes[3] = tempIn[15 + x];
    eightBytes[4] = tempIn[16 + x];
    eightBytes[5] = tempIn[17 + x];
    eightBytes[6] = tempIn[18 + x];
    eightBytes[7] = tempIn[19 + x];
    statusDate = Utility.ByteToLong(eightBytes);

	assigned = (int) tempIn[5 + x];
	application = (int) tempIn[x];
	file = (int) tempIn[x];

    for (int count = 0;count < 60;count++) {
       textBytes[count] = tempIn[count + 22 + x];
    }
    title = Utility.ByteToStr(textBytes);

    for (int count = 0;count < 400;count++) {
       longBytes[count] = tempIn[count + 142 + x];
    }
    desc = Utility.ByteToStr(longBytes);

    for (int count = 0;count < 400;count++) {
       longBytes[count] = tempIn[count + 542 + x];
    }
    notes = Utility.ByteToStr(longBytes);
    category = tempIn[942 + x];
  }

  public byte[] toByteArray(byte[] output) {
    /* Build the output array */
    byte tempOut[];
    int x = BASESIZE;

    output[x] = (byte) application;
    output[1 + x] = (byte) ver;
    output[2 + x] = (byte) ver_min;
    output[3 + x] = (byte) priority;
    output[4 + x] = (byte) status;
    output[5 + x] = (byte) assigned;

    tempOut = Utility.LongToByte(enterDate);
    for (int i=0;i<8;i++) {
      output[i+ 6 + x] = tempOut[i];
    }

    tempOut = Utility.LongToByte(statusDate);
    for (int i=0;i<8;i++) {
      output[i+14 + x] = tempOut[i];
    }

    title = Utility.padString(title,60);
    tempOut = Utility.StrToByte(title);
    for (int i=0;i<60;i++) {
      output[i+22 + x] = tempOut[i];
    }

//    file = Utility.padString(file,60);
    //tempOut = Utility.StrToByte(file);
    for (int i=0;i<60;i++) {
      output[i+82 + x] = tempOut[i];
    }

    desc = Utility.padString(desc,400);
    tempOut = Utility.StrToByte(desc);
    for (int i=0;i<400;i++) {
      output[i+142 + x] = tempOut[i];
    }

    notes = Utility.padString(notes,400);
    tempOut = Utility.StrToByte(notes);
    for (int i=0;i<400;i++) {
      output[i+542 + x] = tempOut[i];
    }
    output[942 + x] = category;

    return output;
  }
}