package com.sns.palm.apps.referee.assign;

/*
 * Age.java
 * Copyright (c) 2002 Sleepless Nights Software
 * Author:       Rob Broadhead
*/
import com.sns.palm.base.Reportable;
import com.sns.palm.util.Utility;

public class Age extends Reportable {
  public String name;
  public byte field;
  public byte duration;
  public byte qtr;
  public byte ageEquiv;

  public Age() {
    super();

    rsName = "AgeData";
    field=-1;
    duration=30;
    qtr=0;
    recordSize = 25 + BASESIZE;
    ageEquiv=-1;
    name = "Unknown";
  }

  /* Methods required for the Reportable interface */
  public String shortDisplay() {
    return name.trim();
  }

  public String Display() {
    String retVal,base;

    base = name.trim() + ":" + duration + " min. ";

    if (qtr==0) {
      retVal = base + "quarters";
    } else if (qtr==1) {
      retVal = base + "halves";
    } else {
      retVal = base;
    }

    return retVal;
  }
  /* End of Reportable methods */

  public boolean isValid() {
    return !(name.equals("") || (duration <= 0));
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

    name = Utility.padString(name,20);
    tempOut = Utility.StrToByte(name);
    for (int i=0;i<20;i++) {
      output[i+x] = tempOut[i];
    }
    output[20 + x] = field;
    output[21 + x] = duration;
    output[22 + x] = qtr;
    output[23 + x] = ageEquiv;

    return output;
  }


  /**
   * This is paired with the toByteArray method as a way to convert the class to
   * a byte array and back again.
   *
   * @param tempIn the array to use for class population
   */
  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte stringBytes[] = new byte[20];
    int x = Reportable.BASESIZE;

    for (int count = 0;count < 20;count++) {
       stringBytes[count] = tempIn[count + x];
    }
    name = (Utility.ByteToStr(stringBytes)).trim();
    field = tempIn[20 + x];
    duration = tempIn[21 + x];
    qtr = tempIn[22 + x];
    ageEquiv = tempIn[23 + x];
  }
}
