package com.sns.palm.apps.referee.assign;

/*
 * Pay.java
 * Copyright (c) 2002 Sleepless Nights Software
 * Author:       Rob Broadhead
*/
import com.sns.palm.base.Reportable;

public class Pay extends Reportable {
  public byte centerd;
  public byte assistd;
  public byte fourthd;
  public byte centerc;
  public byte assistc;
  public byte fourthc;
  public byte ageBracket;

  public Pay() {
    super();

    rsName = "PayData";
    centerd=0;
    centerc=0;
    assistd=0;
    assistc=0;
    fourthd=0;
    fourthc=0;
    ageBracket=0;
    recordSize = 10 + BASESIZE;
  }

  /* Methods required for the Reportable interface */
  public String shortDisplay() {
    String retVal,cbase,abase;

    cbase = "$" + centerd + "." + centerc;
    abase = "$" + assistd + "." + assistc;

    return ageBracket + "|" + cbase + "|" + abase;
  }

  public String screenDisplay(byte x,byte y ) {
    if (y > 9) {
      return x + "." + y;
    } else {
      return x + ".0" + y;
    }
  }

  public String Display() {
    String retVal,cbase,abase,fbase;
    Age bracket = new Age();
    bracket.ID = ageBracket;
    bracket.load();

    cbase = "Cen: $" + centerd + "." + centerc;
    abase = "Ast: $" + assistd + "." + assistc;
    fbase = "Fth: $" + fourthd + "." + fourthc;

    return ageBracket + "|" + cbase + "|" + abase + "|" + fbase;
  }
  /* End of Reportable methods */

  /**
   * Convert the class to a byte array
   *
   * @param output the byte array to populate
   * @return the populated array
   */
  public byte[] toByteArray(byte[] output) {
    int x = Reportable.BASESIZE;

    /* Build the output array */
    output[x] = centerd;
    output[x + 1] = centerc;
    output[x + 2] = assistd;
    output[x + 3] = assistc;
    output[x + 4] = fourthd;
    output[x + 5] = fourthc;
    output[x + 6] = ageBracket;

    return output;
  }


  /**
   * This is paired with the toByteArray method as a way to convert the class to
   * a byte array and back again.
   *
   * @param tempIn the array to use for class population
   */
  public void parseStream(byte[] tempIn) {
    int x = Reportable.BASESIZE;

    centerd = tempIn[x];
    centerc = tempIn[x + 1];
    assistd = tempIn[x + 2];
    assistc = tempIn[x + 3];
    fourthd = tempIn[x + 4];
    fourthc = tempIn[x + 5];
    ageBracket = tempIn[x + 6];
  }
}
