package com.sns.palm.apps.defect;

/**
 * <p>Title: MIDP Defect Log</p>
 * <p>Description: This class is used to log when a defect status has been
 * completed. It also includes some utilities to assist in working with all
 * of the entries for a specific defect. </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sleepless Nights Software</p>
 * @author Rob Broadhead
 * @version 1.0
 */
import com.sns.palm.base.Reportable;
import com.sns.palm.util.*;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;

public class LogEntry extends Reportable {
  /** The reserved value for the NEW status*/
  public static final byte NEW = 0;
  /** The reserved value for the ASSIGN status*/
  public static final byte ASSIGNED  = 2;
  /** The reserved value for the CLOSE status*/
  public static final byte CLOSED  = 8;
  /** The reserved value for the TEST status*/
  public static final byte TEST  = 5;
  /** The reserved value for the HOLD status*/
  public static final byte HOLD  = 1;
  /** The reserved value for the In Progres status*/
  public static final byte IN_PROGRESS = 3;
  /** The reserved value for the Test Build status*/
  public static final byte TEST_BUILD = 4;
  /** The reserved value for the Fix status*/
  public static final byte FIX = 6;
  /** The reserved value for the Release status*/
  public static final byte RELEASE = 6;
  /** The reserved value for the CANCEL status*/
  public static final byte CANCEL  = 9;

  public int defectID;
  public byte status;
  public long setDate;

  /**
   * No args constructor sets all values to 0
   */
  public LogEntry() {
    super();

    defectID = 0;
    status = 0;
    setDate = 0;

    rsName = "StatusHist";
    recordSize = 15;
  }

  /**
   * This returns a string name for the status represented by the byte value
   * sent as a parameter. If the byte value is not valid then a value of
   * "Invalid" is returned.
   *
   * @param val the byte value representing a status
   * @return the string version of the value
   */
  public String getStatusString(byte val) {
    String[] theArr = DefectUtils.LoadStatus();
    String retVal = "Invalid";

    if (val >= 0 && val < theArr.length) {
      retVal = theArr[val];
    }

    return retVal;
  }

  /**
   * This is over-ridden as a reportable class. It shows the entry as the status
   * as a byte value followed by a colon and then date the status was set.
   * @return The short string version of this class
   */
  public String shortDisplay() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone("CST"));
    cal.setTime(new Date(setDate));
    return status + ":" + cal.toString();
  }

  /**
   * This is over-ridden as a reportable class. It shows the entry as the status
   * as a string value followed by a colon and then date the status was set.
   * @return The string version of this class
   */
  public String Display() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone("CST"));
    cal.setTime(new Date(setDate));
    return getStatusString(status) + ":" + cal.toString();
  }

  /**
   * This method is implemented as part of the reportable class.
   * @param tempIn the byte array to populate with items from this class.
   */
  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte eightBytes[] = new byte[8];

    defectID = (int) tempIn[2];
    status = tempIn[3];
    eightBytes[0] = tempIn[4];
    eightBytes[1] = tempIn[5];
    eightBytes[2] = tempIn[6];
    eightBytes[3] = tempIn[7];
    eightBytes[4] = tempIn[8];
    eightBytes[5] = tempIn[9];
    eightBytes[6] = tempIn[10];
    eightBytes[7] = tempIn[11];
    setDate = Utility.ByteToLong(eightBytes);
  }

  /**
   * This class requires a defectID that is not 0 and a date to be set
   * for this to be valid. This method verifies that the instance is valid
   * or not.
   *
   * @return true if this object is a valid instance.
   */
  public boolean isValid() {
    return !(defectID==0 || setDate==0);
  }

  /** Convert a byte array to a populated output stream so the class can be
   *  stored in a record store.
   *
   *  @param output The output array that needs to be populated. It
   *  should already be declared to the size it will be.
   *  @return the populated output array */
  public byte[] toByteArray(byte[] output) {
    byte tempOut[];

    output[2] = (byte) defectID;
    output[3] = status;
    tempOut = Utility.LongToByte(setDate);
    for (int i=0;i<8;i++) {
      output[i+4] = tempOut[i];
    }

    return output;
  }
}