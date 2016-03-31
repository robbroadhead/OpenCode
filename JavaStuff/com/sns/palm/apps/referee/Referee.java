package com.sns.palm.apps.referee;

/*
 * Referee.java
 * Copyright (c) 2002 Sleepless Nights Software
 * Author:       Rob Broadhead
*/
import com.sns.palm.base.Reportable;
import com.sns.palm.util.Utility;

public class Referee extends Reportable {

  public String Name;
  public String Phone;
  public String Email;

  public Referee() {
    super();

    rsName = "RefereeData";
    recordSize = 96 + BASESIZE;
    Name = "Unknown";
    Phone = "Unknown";
    Email = "Unknown";
  }

  public String shortDisplay() {
    return Name.trim() + " phone:" + Phone;
  }

  public String Display() {
    return Name.trim();
  }

  public boolean isValid() {
    return !Name.equals("");
  }

  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte stringBytes[] = new byte[30];
    byte longBytes[] = new byte[50];
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

    for (int count = 0;count < 50;count++) {
       longBytes[count] = tempIn[count + 45 + x];
    }
    Email = Utility.ByteToStr(longBytes);


    return;
  }

  public byte[] toByteArray(byte[] output) {
    /* The variables to be used */
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

    Email = Utility.padString(Email,50);
    tempOut = Utility.StrToByte(Email);
    for (int i=0;i<50;i++) {
      output[i+45 + x] = tempOut[i];
    }

    return output;
  }
}