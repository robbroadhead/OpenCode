package com.sns.palm.apps.referee.assign;

/*
 * Match.java
 * Copyright (c) 2001 Sleepless Nights Software
 * Author:       Rob Broadhead
*/
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import com.sns.palm.base.Reportable;
import com.sns.palm.util.Utility;

public class Match extends Reportable {
  /* Team Colors */
  static final public byte RED = 0;
  static final public byte BLUE = 1;
  static final public byte GREEN = 2;
  static final public byte WHITE = 3;
  static final public byte BLACK = 4;
  static final public byte GOLD = 5;
  static final public byte TEAL = 6;
  static final public byte PURPLE = 7;
  static final public byte ORANGE = 8;
  static final public byte MAROON = 9;
  static final public byte SILVER = 10;
  static final public byte NAVY = 11;

  static final public String[] POS_LIST = {"Red","Blue","Green","White","Black",
    "Gold","Teal","Purple","Orange","Maroon","Silver","Navy"};
  /* End of static properties*/

  public String home;
  public String visitor;
  public String homeNote;
  public String visNote;
  public long playDate;
  public byte numRefs;
  public byte ageBracket;
  public byte field;
  public byte homeColor;
  public byte visColor;
  public static long startDate;
  public static long endDate;

  public Match() {
    super();

    rsName = "MatchData";
    recordSize = 115 + BASESIZE;
    home = "Unknown";
    visitor = "Unknown";
    homeNote = "NA";
    visNote="NA";
    playDate = 0;
    numRefs=-1;
    ageBracket=-1;
    field=-1;
    homeColor=-1;
    visColor=-1;
  }

  /* Methods required for the Reportable interface */
  public String shortDisplay() {
    return home.trim() + " vs. " + visitor.trim();
  }

  public String Display() {
    String retVal;
    String month,day,time;
    byte[] searchString = new byte[1];
    int ageID;

    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone("CST"));
    cal.setTime(new Date(playDate));
    if (cal.get(Calendar.MONTH) < 9) {
      month = "0" + (cal.get(Calendar.MONTH) + 1);
    } else {
      month = "" + (cal.get(Calendar.MONTH) + 1);
    }

    if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
      day = "0" + cal.get(Calendar.DAY_OF_MONTH);
    } else {
      day = "" + cal.get(Calendar.DAY_OF_MONTH);
    }

    if (cal.get(Calendar.MINUTE) < 10) {
      time = cal.get(Calendar.HOUR) + ":0" + cal.get(Calendar.MINUTE);
    } else {
      time = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);
    }

    Age tempAge = new Age();
    tempAge.ID = ageBracket;
    tempAge.load();

    retVal = month + "/"+ day + "/" + cal.get(Calendar.YEAR) + "-> " + tempAge.shortDisplay() + " @" + time + "| " + home.trim() + " vs. " + visitor.trim();
    return retVal;
  }
  /* End of Reportable methods */

  public boolean checker() {
    return ((Match.startDate == Match.endDate) || (playDate >= Match.startDate) && (playDate <= Match.endDate));
  }

  public boolean isValid() {
    return !(home.equals("") || visitor.equals("") || playDate==0 ||
            numRefs < 0 || ageBracket < 0);
  }

  /** Convert the class to a byte array */
  public byte[] toByteArray(byte[] output) {
    int x = BASESIZE;

    /* Build the output array */
    byte tempOut[];
    home = Utility.padString(home,25);
    tempOut = Utility.StrToByte(home);
    for (int i=0;i<25;i++) {
      output[i+x] = tempOut[i];
    }

    visitor = Utility.padString(visitor,25);
    tempOut = Utility.StrToByte(visitor);
    for (int i=0;i<25;i++) {
      output[i+25 + x] = tempOut[i];
    }

    homeNote = Utility.padString(homeNote,25);
    tempOut = Utility.StrToByte(homeNote);
    for (int i=0;i<25;i++) {
      output[i+50 + x] = tempOut[i];
    }
    visNote = Utility.padString(visNote,25);
    tempOut = Utility.StrToByte(visNote);
    for (int i=0;i<25;i++) {
      output[i+75 +x] = tempOut[i];
    }
    output[100 + x] = numRefs;
    output[101 + x] = ageBracket;
    output[102 + x] = field;
    output[103 + x] = homeColor;
    output[104 + x] = visColor;

    tempOut = Utility.LongToByte(playDate);
    for (int i=0;i<8;i++) {
      output[i+105 + x] = tempOut[i];
    }

    return output;
  }


  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte stringBytes[] = new byte[25];
    byte eightBytes[] = new byte[8];
    int x = BASESIZE;

    for (int count = 0;count < 25;count++) {
       stringBytes[count] = tempIn[count + x];
    }
    home = Utility.ByteToStr(stringBytes);

    for (int count = 0;count < 25;count++) {
       stringBytes[count] = tempIn[count + 25 + x];
    }
    visitor = Utility.ByteToStr(stringBytes);

    for (int count = 0;count < 25;count++) {
       stringBytes[count] = tempIn[count + 50 + x];
    }
    homeNote = Utility.ByteToStr(stringBytes);

    for (int count = 0;count < 25;count++) {
       stringBytes[count] = tempIn[count + 75 + x];
    }
    visNote = Utility.ByteToStr(stringBytes);

    numRefs = tempIn[100 + x];
    ageBracket = tempIn[101 + x];
    field = tempIn[102 + x];
    homeColor = tempIn[103 + x];
    visColor = tempIn[104 + x];

    eightBytes[0] = tempIn[105 + x];
    eightBytes[1] = tempIn[106 + x];
    eightBytes[2] = tempIn[107 + x];
    eightBytes[3] = tempIn[108 + x];
    eightBytes[4] = tempIn[109 + x];
    eightBytes[5] = tempIn[110 + x];
    eightBytes[6] = tempIn[111 + x];
    eightBytes[7] = tempIn[112 + x];

    playDate = Utility.ByteToLong(eightBytes);
  }
}
