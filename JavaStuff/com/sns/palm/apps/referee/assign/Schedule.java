package com.sns.palm.apps.referee.assign;

/*
 * Schedule.java
 * Copyright (c) 2002 Sleepless Nights Software
 * Author:       Rob Broadhead
*/
import com.sns.palm.base.Reportable;
import com.sns.palm.util.Utility;
import com.sns.palm.util.Tracer;

public class Schedule extends Reportable {
  public int game;
  public int center;
  public int AR1;
  public int AR2;
  public int Fourth;

  public Schedule() {
    super();

    rsName = "ScheduleData";
    recordSize = 22 + Reportable.BASESIZE;
    game = -1;
    center= -1;
    AR1 = 0;
    AR2 = 0;
    Fourth = 0;
  }

  /* Methods required for the Reportable interface */
  public String shortDisplay() {
    return "[" + game + "," + center + "]";
  }

  public String Display() {
    return "[" + game + "," + center + "," + AR1 + "," + AR2 + "]";
  }
  /* End of Reportable methods */

  public boolean isValid() {
    return !((center < -1) || (game < -1));
  }

  /**
   * Convert the class to a byte array
   *
   * @param output the byte array to populate
   * @return the populated array
   */
  public byte[] toByteArray(byte[] output) {
    /* Build the output array */
    byte tempOut[];
    int x = Reportable.BASESIZE;
    Tracer.setActive(true);
    Tracer.log("Saving Schedule",Tracer.LOG);
    String theInt = Utility.padString(Integer.toHexString(game),4);
    tempOut = Utility.StrToByte(theInt);
    for (int i=0;i<4;i++) {
      output[i + x] = tempOut[i];
    }

    theInt = Utility.padString(Integer.toHexString(center),4);
    tempOut = Utility.StrToByte(theInt);
    for (int i=0;i<4;i++) {
      output[i + x + 4] = tempOut[i];
    }

    theInt = Utility.padString(Integer.toHexString(AR1),4);
    tempOut = Utility.StrToByte(theInt);
    for (int i=0;i<4;i++) {
      output[i + x + 8] = tempOut[i];
    }

    theInt = Utility.padString(Integer.toHexString(AR2),4);
    tempOut = Utility.StrToByte(theInt);
    for (int i=0;i<4;i++) {
      output[i + x + 12] = tempOut[i];
    }

    theInt = Utility.padString(Integer.toHexString(Fourth),4);
    tempOut = Utility.StrToByte(theInt);
    for (int i=0;i<4;i++) {
      output[i + x + 16] = tempOut[i];
    }
    Tracer.log("Saved Schedule",Tracer.LOG);
    Tracer.log(Display(),Tracer.LOG);
    Tracer.log("ID:" + this.ID,Tracer.LOG);

    return output;
  }


  /**
   * This is paired with the toByteArray method as a way to convert the class to
   * a byte array and back again.
   *
   * @param tempIn the array to use for class population
   */
  public void parseStream(byte[] tempIn) {
    int x = BASESIZE;

    Tracer.log("Loading Schedule",Tracer.LOG);
    /* Convert the int to a Hex string for storage. */
    byte stringBytes[] = new byte[4];
    for (int count = 0;count < 4;count++) {
       stringBytes[count] = tempIn[count + x];
    }
    String theValue = Utility.ByteToStr(stringBytes);
    try {
      game = Integer.parseInt(theValue,16);
    } catch (NumberFormatException nfe) {
    }

    for (int count = 0;count < 4;count++) {
       stringBytes[count] = tempIn[count + x + 4];
    }
    theValue = Utility.ByteToStr(stringBytes);
    try {
      center = Integer.parseInt(theValue,16);
    } catch (NumberFormatException nfe) {
    }

    for (int count = 0;count < 4;count++) {
       stringBytes[count] = tempIn[count + x + 8];
    }
    theValue = Utility.ByteToStr(stringBytes);
    try {
      AR1 = Integer.parseInt(theValue,16);
    } catch (NumberFormatException nfe) {
    }

    for (int count = 0;count < 4;count++) {
       stringBytes[count] = tempIn[count + x + 12];
    }
    theValue = Utility.ByteToStr(stringBytes);
    try {
      AR2 = Integer.parseInt(theValue,16);
    } catch (NumberFormatException nfe) {
    }

    for (int count = 0;count < 4;count++) {
       stringBytes[count] = tempIn[count + x + 16];
    }
    theValue = Utility.ByteToStr(stringBytes);
    try {
      Fourth = Integer.parseInt(theValue,16);
    } catch (NumberFormatException nfe) {
    }
    Tracer.log("Loaded Schedule",Tracer.LOG);
    Tracer.log(Display(),Tracer.LOG);
    Tracer.log("ID:" + this.ID,Tracer.LOG);
  }
}
