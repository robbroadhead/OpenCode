package com.sns.palm.apps.referee;
/*
 * Association.java
 * Copyright (c) 2002 Sleepless Nights Software
 * Author:       Rob Broadhead
*/
import com.sns.palm.base.Reportable;
import com.sns.palm.util.Utility;

public class Association extends Reportable {

  public String Name;
  public String Assignor;
  public String Phone;

  public Association() {
    super();

    Name = "Unknown";
    Assignor = "Unknown";
    Phone = "Unknown";
    rsName = "AssocData";
    recordSize = 76 + BASESIZE;
  }

  public String shortDisplay() {
    return Name.trim();
  }

  public String Display() {
    return Name.trim();
  }

  public boolean isValid() {
    return !Name.equals("");
  }

  /** Convert the class to a byte array */
  public byte[] toByteArray(byte[] output) {
    /* Variables used */
    byte tempOut[];
    int x = BASESIZE;

    Name = Utility.padString(Name,30);
    tempOut = Utility.StrToByte(Name);
    for (int i=0;i<30;i++) {
      output[i+x] = tempOut[i];
    }
    Phone = Utility.padString(Phone,15);
    tempOut = Utility.StrToByte(Phone);
    for (int i=0;i<15;i++) {
      output[i+30 + x] = tempOut[i];
    }

    Assignor = Utility.padString(Assignor,30);
    tempOut = Utility.StrToByte(Assignor);
    for (int i=0;i<30;i++) {
      output[i+45 + x] = tempOut[i];
    }

    return output;
  }

  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte stringBytes[] = new byte[30];
    byte phoneBytes[] = new byte[15];
    int x = BASESIZE;

    for (int count = 0;count < 30;count++) {
       stringBytes[count] = tempIn[count + x];
    }
    Name = Utility.ByteToStr(stringBytes);

    for (int count = 0;count < 15;count++) {
       phoneBytes[count] = tempIn[count + 30 + x];
    }
    Phone = Utility.ByteToStr(phoneBytes);

    for (int count = 0;count < 30;count++) {
       stringBytes[count] = tempIn[count + 45 + x];
    }
    Assignor = Utility.ByteToStr(stringBytes);
  }
}