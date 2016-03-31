// Title:        Stats
// Version:      1.0
// Copyright:    Copyright (c) 1998
// Author:       Rob Broadhead
// Company:      Sleepless Nights Software
// Description:  A class to store integer statistics or counters
package com.sns.Util;

import java.io.*;
import java.lang.Math;

public class Stats {

   private boolean validIndex(int idx) {
      return ((idx >=0) && (idx < size_A));
   } // End of validIndex(int)

   public Stats(int size) {
      /* Declare variables to be used */
      int count;

      size_A=size;
      stats_A=new int[size];
      names_A=new String[size];

      /* Set everything to 0 */
      for (count=0;count < size_A;count++) {
         stats_A[count]=0;
         names_A[count]="Stat" + count;
      }
   } // End of Stats Constructor


   public String toString() {
      int count;

      String retVal="";

      for (count=0;count < size_A;count++) {
         retVal=retVal + "\n" + names_A[count] + ": " + stats_A[count];
      }

      return retVal;
   } // End of DisplayStat(int)


   public int getStat(int whichone) {
      int retValue;

      if (this.validIndex(whichone)) {
         retValue=stats_A[whichone];
      } else {
         retValue=-99;
      }

      return retValue;
   } // End of GetStat(int)


   public void setStat(int whichone,int value) {
      if (validIndex(whichone)) {
         stats_A[whichone]=value;
      }
   } //  End of SetStat(int,int)


   public void incStat(int whichone,int value) {
      if (validIndex(whichone)) {
         stats_A[whichone]+=value;
      }
   } //  End of SetStat(int,int)


   public void decStat(int whichone,int value) {
      if (validIndex(whichone)) {
         stats_A[whichone]-=value;
      }
   }

   public void save(DataOutput out) throws IOException {
      /* Declare variables to be used */
      int count;

      /* Set everything to 0 */
      for (count=0;count < size_A;count++) {
         out.writeUTF(names_A[count]);
         out.writeInt(stats_A[count]);
      }
   } // End of Save()


   public void load(DataInput in) throws IOException {
      /* Declare variables to be used */
      int count;

      /* Set everything to 0 */
      for (count=0;count < size_A;count++) {
         names_A[count] = in.readUTF();
         stats_A[count] = in.readInt();
      }
   } // End of Load()


   public void initialize(int roll) {
      /* Declare variables to be used */
      int count;

      /* Generate a value for each */
      if (roll < 0) {
         roll = 0 - roll;
         for (count=0;count < size_A;count++) {
            stats_A[count]=(int) Math.round(roll * Math.random());
         }
      } else {
         for (count=0;count < size_A;count++) {
            stats_A[count]=roll;
         }
      }
   } // End of Initialize(int)

   public void setName(int whichone,String theName) {
      if (validIndex(whichone)) {
         names_A[whichone] = theName;
      }
   } // End of SetName(int,String)

/* Properties for the class */
   private int stats_A[];
   private String names_A[];
   private int size_A;
} // End of Stats class.

