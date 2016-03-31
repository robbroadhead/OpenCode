package com.sns.palm.base;

/**
 * <p>Title: Reportable</p>
 * <p>Description: This is a base class for any object that will be stored
 * in a RecordStore. This gives the item an ID and update flag as well as
 * being central to several of the utilities that work with records and
 * display them.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */
import javax.microedition.rms.*;
import com.sns.palm.util.*;

abstract public class Reportable {
  public int ID;
  public int updateFlag;
  public String rsName;
  protected int recordSize;
  public String msg;
  public byte version;
  protected int MAXRECORDS;

  /** The version of this record. This is used to upgrade a data structure */
  static public final byte CUR_VERSION = 1;

  /** Length of basic information for a record such as ID, version, etc. */
  static public final byte BASESIZE = 6;

  public Reportable() {
    ID = 0;
    updateFlag = 0;
    recordSize = BASESIZE;
    version = CUR_VERSION;
    /* This is -1 for the full version of an application */
    MAXRECORDS = -1;
    msg = "";
  }

  /**
   * A shorter display for this item.
   *
   * @return the shortened string version of this record
   */
  abstract public String shortDisplay();

  /** Typical report line format for this item.
   *
   * @return the string version of this record
   */
  abstract public String Display();

  /**
   * Load the object from the record store.
   */
  public void load() {
    RecordStore rs;

    /* Declare the variables to be used */
    byte tempIn[] = new byte[recordSize];
    int numRecs = 0;

    rs = Utility.openRecordStore(rsName);
    if (rs == null) {
      msg = "RS Exception load:null store";
      return;
    }

    /* Return if we don't have any records */
    try {
      numRecs = rs.getNumRecords();
      if (numRecs == 0) {
        msg = "No Records";
        return;
      }

      if (ID <= 0) {
        ID = numRecs;
      } else if (ID > numRecs) {
        ID = 1;
      }

      tempIn = rs.getRecord(ID);
    } catch (Exception e) {
      msg = "Failed to get record";
      return;
    }

    /* Convert the int to a Hex string for storage. */
    byte stringBytes[] = new byte[4];
    for (int count = 0;count < 4;count++) {
       stringBytes[count] = tempIn[count];
    }
    String theValue = Utility.ByteToStr(stringBytes).trim();
    try {
      ID = Integer.parseInt(theValue,16);
    } catch (NumberFormatException nfe) {
      /* It seems that an exception is always thrown. */
    }

    updateFlag = (int) tempIn[4];
    version = tempIn[5];

    parseStream(tempIn);

    try {
      rs.closeRecordStore();
    } catch (Exception e) {}
//    Tracer.log("Completed Load",Tracer.LOG);
  }

  /**
   * This parses through the remainder of the byte array and stores the values
   * in the proper class variables.
   *
   * @param tempIn the stream that is to be parsed into fields.
   */
  public abstract void parseStream(byte[] tempIn);

  /**
   * This is used to allow the developer to add logic that determines
   * whether a record is valid.
   *
   * @return true if the record is valid
   */
  public boolean isValid() { return true; }

  /**
   * Convert the class to a byte array
   *
   * @param output the byte array that is to be populated by the values of
   * this record.
   *
   * @return the record as a byte array
   */
  public byte[] toByteArray(byte[] output) {
    return output;
  }

  /**
   * This is used as a filter for displaying records. If it returns true
   * then the given record will be added to a display list.
   *
   * @return whether or not the value is ok
   */
  public boolean checker() {
    return true;
  }

  public String[] listItems(String table,Reportable temp) {
    /* Initialize local variables */
    int numRecs=0;
    RecordStore temprs;
    String[] retVal = new String[1];

    temprs = Utility.openRecordStore(table);
    if (temprs == null) {
      msg = "Error opening file";
      retVal[0]=msg;
      return retVal;
    }

    /* Return if we don't have any records */
    try {
      numRecs = temprs.getNumRecords();
      if (numRecs == 0) {
        msg = "No records Entered";
        retVal[0]=msg;
        return retVal;
      }
    } catch (Exception e) {
      msg = "Error getting records";
      retVal[0]=msg;
      return retVal;
    }
    retVal = new String[numRecs];

    for (int i=1;i <= numRecs;i++) {
      temp.ID = i;
      temp.load();
      if (temp.checker()) {
        retVal[i-1] = temp.Display();
      }
    }

    try {
      temprs.closeRecordStore();
    } catch (Exception e) {}
    return retVal;
  }

  /** Save the object to the record store. */
  public void save() {
    RecordStore rs;
    boolean newRec=false;
//    Tracer.log("Entered Save",Tracer.LOG);

    /* Open the Store */
    rs = null;
    if (!isValid()) {
      msg = "Null Record save attempt";
      return;
    }

    rs = Utility.openRecordStore(rsName);
    if (rs == null) {
      msg = "Failed RS Open";
      return;
    }

    if (ID<1) {
      try {
        ID = rs.getNumRecords() + 1;
        if ((MAXRECORDS > 0) && (ID > MAXRECORDS)) {
          msg = "At maximum records for this item, register your software";
          return;
        }
        newRec = true;
      } catch (Exception e) {
        msg = "RS Get Num Records Exception";
        return;
      }
    }

    /* Build the output array */
    byte output[] = new byte[recordSize];

    byte tempOut[];
    String theInt = Utility.padString(Integer.toHexString(ID),4);
    tempOut = Utility.StrToByte(theInt);
    for (int i=0;i<4;i++) {
      output[i] = tempOut[i];
    }
    output[4] = (byte) updateFlag;
    output[5] = version;
    output = toByteArray(output);

    try {
      if (newRec) {
        rs.addRecord(output,0,output.length);
      } else {
        rs.setRecord(ID,output,0,output.length);
      }
    } catch (Exception e) {
      msg = "Add Record Exception:" + e.getClass().getName();
    }

    try {
      rs.closeRecordStore();
    } catch (Exception e) {}
//    Tracer.log("Completed Save",Tracer.LOG);
  }
}
