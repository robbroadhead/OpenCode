package com.sns.palm.apps.referee.assign;

/*
 * Field.java
 * Copyright (c) 2002 Sleepless Nights Software
 * Author:       Rob Broadhead
*/
import com.sns.palm.base.Reportable;
import com.sns.palm.util.*;

public class Field extends Reportable {
  public String name;
  public byte fieldSize;

  public Field() {
    super();

    rsName = "FieldData";
    fieldSize=0;
    recordSize = 30 + BASESIZE;
    name = "No Name";
  }

  /* Methods required for the Reportable interface */
  public String shortDisplay() {
    return name.trim();
  }

  static public String[] loadSizes() {
    /* Initialize local variables */
    String retVal[];
    retVal=new String[7];

    retVal[0] = "Small-Sided";
    retVal[1] = "U7-U8";
    retVal[2] = "U9-U11";
    retVal[3] = "U12-U15";
    retVal[4] = "Full Size";
    retVal[5] = "FIFA Regulation";
    retVal[6] = "Indoor";

    return retVal;
  }

  static public String sizeString(byte val) {
    /* Initialize local variables */
    String retVal[];
    retVal = new String[7];

    retVal[0] = "Small-Sided";
    retVal[1] = "U7-U8";
    retVal[2] = "U9-U11";
    retVal[3] = "U12-U15";
    retVal[4] = "Full Size";
    retVal[5] = "FIFA Regulation";
    retVal[6] = "Indoor";

    return retVal[val];
  }

  public String Display() {
    return name.trim() + " Field (" + sizeString(fieldSize) + ")";
  }
  /* End of Reportable methods */

  public boolean isValid() {
    return !(name.equals("") || (fieldSize < 0));
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
    int x = BASESIZE;

    name = Utility.padString(name,25);
    tempOut = Utility.StrToByte(name);
    for (int i=0;i<25;i++) {
      output[i+x] = tempOut[i];
    }
    output[25 + x] = fieldSize;

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
    byte stringBytes[] = new byte[25];
    int x = BASESIZE;

    for (int count = 0;count < 25;count++) {
       stringBytes[count] = tempIn[count + x];
    }
    name = (Utility.ByteToStr(stringBytes)).trim();
    fieldSize = tempIn[25 + x];
  }
}
