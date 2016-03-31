package com.sns.palm.apps.referee;

/*
 * Game.java
 * Copyright (c) 2001 Sleepless Nights Software
 * Author:       Rob Broadhead
*/
import javax.microedition.rms.*;
import java.util.Vector;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import com.sns.palm.base.Reportable;
import com.sns.palm.util.*;

public class Game extends Reportable {

  public String Home;
  public String Visitor;
  public int HomeScore;
  public int VisScore;
  public long PlayDate;
  public boolean Indoor;
  public boolean Paid;
  public boolean Center;
  public int Ref1;
  public int Ref2;
  public int Age;
  public int assoc;
  public int payd;
  public int payc;

  // These are for the lists and searches
  static public long startDate;
  static public long endDate;

  public Game() {
    super();

    rsName = "GameData";
    recordSize = 75 + BASESIZE;
    Home = "Unknown";
    Visitor = "Unknown";
    HomeScore = 0;
    VisScore = 0;
    PlayDate = new Date().getTime();
    Indoor = false;
    Paid = false;
    Center = false;
    Ref1 = 1;
    Ref2 = 1;
    Age = 5;
    assoc=0;
    payd = 0;
    payc = 0;
  }

  /* Methods required for the Reportable interface */
  public String shortDisplay() {
    return Home.trim() + " vs. " + Visitor.trim();
  }

  public String Display() {
    String retVal;
    String month,day,ageStr,ageID;

    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone("CST"));
    cal.setTime(new Date(PlayDate));
    if (cal.get(Calendar.MONTH) < 10) {
      month = "0" + cal.get(Calendar.MONTH);
    } else {
      month = "" + cal.get(Calendar.MONTH);
    }

    if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
      day = "0" + cal.get(Calendar.DAY_OF_MONTH);
    } else {
      day = "" + cal.get(Calendar.DAY_OF_MONTH);
    }

    String ageList[] = RefereeUtils.LoadAges();

    if (cal.get(Calendar.YEAR) < 10) {
      retVal = ageList[this.Age] + "|" + month + "/"+ day + "/0" + cal.get(Calendar.YEAR) + ":" + Home.trim() + " vs. " + Visitor.trim() + " [" + HomeScore + "," + VisScore + "] ";
    } else {
      retVal = ageList[this.Age] + "|" + month + "/"+ day + "/" + cal.get(Calendar.YEAR) + ":" + Home.trim() + " vs. " + Visitor.trim() + " [" + HomeScore + "," + VisScore + "] ";
    }

    return retVal;
  }
  /* End of Reportable methods */

  public boolean checker() {
    return ((PlayDate >= Game.startDate) && (PlayDate <= Game.endDate));
  }

  static public String[] SummaryGames(long startDate,long endDate) {
    String[] retVal;
    Vector games=new Vector();
    int numGames=0;
    int curID=0;
    int counter=0;
    int centerA,centerB,centerC,centerD,centerE;
    int lineA,lineB,lineC,lineD,lineE;
    RecordStore rs;
    Game tempGame=new Game();

    centerA = 0;
    centerB = 0;
    centerC = 0;
    centerD = 0;
    centerE = 0;
    lineA = 0;
    lineB = 0;
    lineC = 0;
    lineD = 0;
    lineE = 0;

    rs = Utility.openRecordStore("GameData");
    if (rs == null) {
      retVal = new String[1];
      retVal[0] = "RS Exception load:null store";
      return retVal;
    }

    try {
      numGames = rs.getNumRecords();
      rs.closeRecordStore();

      // If we don't have any games then we're done.
      if (numGames == 0) {
        retVal = new String[1];
        retVal[0] = "No Games entered";
        return retVal;
      }

      // Cycle through each game
      for (curID=1;curID<=numGames;curID++) {
        tempGame.ID=curID;
        tempGame.load();

        // Check to see if the game is within our current date range.
        if ((tempGame.PlayDate >= startDate) || (tempGame.PlayDate <= endDate)) {
          // Check if this is an Under 16 or lower game
          if (tempGame.Age < 13) {
            if (tempGame.Center) {
              centerA++;
            } else {
              lineA++;
            }
          } else if (tempGame.Age < 17) {
            // Under 19 games down to under 17
            if (tempGame.Center) {
              centerB++;
            } else {
              lineB++;
            }
          } else if (tempGame.Age < 21) {
            // Adult Rec
            if (tempGame.Center) {
              centerC++;
            } else {
              lineC++;
            }
          } else if (tempGame.Age < 23) {
            // Other Pro
            if (tempGame.Center) {
              centerE++;
            } else {
              lineE++;
            }
          } else {
            if (tempGame.Center) {
              centerD++;
            } else {
              lineD++;
            }
          }
        }
      }

      retVal = new String[5];
      retVal[0] = "Under 16 games: Center:" + centerA + " Line:" + lineA;
      retVal[1] = "Under 19 games: Center:" + centerB + " Line:" + lineB;
      retVal[2] = "Am Adult games: Center:" + centerC + " Line:" + lineC;
      retVal[3] = "Pro games     : Center:" + centerD + " Line:" + lineD;
      retVal[4] = "Other games   : Center:" + centerE + " Line:" + lineE;
    } catch (Exception e) {
      retVal = new String[1];
      retVal[0] = "Failed to get record";
    }

    return retVal;
  }

  public boolean isValid() {
    return !(Home.equals("") || Visitor.equals(""));
  }

  /** Convert the class to a byte array */
  public byte[] toByteArray(byte[] output) {
    /* Build the output array */
    byte tempOut[];
    int x = Reportable.BASESIZE;

    Home = Utility.padString(Home,25);
    tempOut = Utility.StrToByte(Home);
    for (int i=0;i<25;i++) {
      output[i+x] = tempOut[i];
    }
    output[25+x] = (byte) HomeScore;

    Visitor = Utility.padString(Visitor,25);
    tempOut = Utility.StrToByte(Visitor);
    for (int i=0;i<25;i++) {
      output[26+i+x] = tempOut[i];
    }

    output[51 + x] = (byte) VisScore;
    String theInt = Utility.padString(Integer.toHexString(Ref1),4);
    tempOut = Utility.StrToByte(theInt);
    for (int i=0;i<4;i++) {
      output[52 + x + i] = tempOut[i];
    }

    theInt = Utility.padString(Integer.toHexString(Ref2),4);
    tempOut = Utility.StrToByte(theInt);
    for (int i=0;i<4;i++) {
      output[56 + x + i] = tempOut[i];
    }

    output[60 + x] = Utility.BoolToByte(Indoor);
    output[61 + x] = Utility.BoolToByte(Paid);
    output[62 + x] = Utility.BoolToByte(Center);
    output[63 + x] = (byte) Age;

    tempOut = Utility.LongToByte(PlayDate);
    for (int i=0;i<8;i++) {
      output[i+64+x] = tempOut[i];
    }
    output[72+x] = (byte) assoc;

    return output;
  }


  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte stringBytes[] = new byte[25];
    byte longBytes[] = new byte[30];
    byte eightBytes[] = new byte[8];
    int x = Reportable.BASESIZE;

    for (int count = 0;count < 25;count++) {
       stringBytes[count] = tempIn[count + x];
    }
    Home = Utility.ByteToStr(stringBytes);
    HomeScore = (int) tempIn[25 + x];

    for (int count = 0;count < 25;count++) {
       stringBytes[count] = tempIn[count + 26 + x];
    }
    Visitor = Utility.ByteToStr(stringBytes);
    VisScore = (int) tempIn[51 + x];

    /* Convert the int to a Hex string for storage. */
    byte shortBytes[] = new byte[4];
    for (int count = 0;count < 4;count++) {
       shortBytes[count] = tempIn[count + x + 52];
    }
    String theValue = Utility.ByteToStr(shortBytes).trim();
    try {
      Ref1 = Integer.parseInt(theValue,16);
    } catch (NumberFormatException nfe) {
      /* It seems that an exception is always thrown. */
    }

    /* Convert the int to a Hex string for storage. */
    for (int count = 0;count < 4;count++) {
       shortBytes[count] = tempIn[count + x + 56];
    }
    theValue = Utility.ByteToStr(shortBytes).trim();
    try {
      Ref2 = Integer.parseInt(theValue,16);
    } catch (NumberFormatException nfe) {
      /* It seems that an exception is always thrown. */
    }

    Indoor = Utility.ByteToBool(tempIn[60 + x]);
    Paid = Utility.ByteToBool(tempIn[61 + x]);
    Center = Utility.ByteToBool(tempIn[62 + x]);
    Age = (int) tempIn[63 + x];
    eightBytes[0] = tempIn[64 + x];
    eightBytes[1] = tempIn[65 + x];
    eightBytes[2] = tempIn[66 + x];
    eightBytes[3] = tempIn[67 + x];
    eightBytes[4] = tempIn[68 + x];
    eightBytes[5] = tempIn[69 + x];
    eightBytes[6] = tempIn[70 + x];
    eightBytes[7] = tempIn[71 + x];

    PlayDate = Utility.ByteToLong(eightBytes);
    assoc = (int) tempIn[72 + x];
  }
}
