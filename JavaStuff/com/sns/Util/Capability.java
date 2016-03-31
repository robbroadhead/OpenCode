// Title:        Capability
// Version:      1.0
// Copyright:    Copyright (c) 1999
// Author:       Rob Broadhead
// Company:      Sleepless Nights Software
// Description:  A class to store integer statistics or counters with mx
//               and current values
package com.sns.Util;

import java.io.*;

/** Stores integer statistics or counters with max and current
 * values.
 * @author Rob Broadhead
 * @version 1.0   */
public class Capability implements Serializable {

   /** The constructor, notice there is not a no argument constructor
   * @param int mx The absolute maximum value allowed.
   */
   public Capability(int mx) {
      max_A=mx;
      full_A=0;
      cur_A=0;
   } // End of Capability Constructor


   public Capability(int mx,int full) {
      max_A=mx;
      full_A=full;
      cur_A=full;
   } // End of Capability Constructor


   /** Converts the class to a string "M:nn C:nn F:nn"
   * @return The class values converted to a string.
   */
   public String toString() {
      String retVal="";

      retVal="M: " + max_A + " C: " + cur_A + " F:" + full_A;
      return retVal;
   } // End of toString()


   /** Sets the current value equal to the full value */
   public void restore() {
      cur_A=full_A;
   } // End of Restore()


   /** Adjusts the current value by theVal.  The current value can not go
   below 0 nor above the full value.
   @param theVal A positive or negative value for adjusting the current value. */
   public void setCurrent(int theVal) {
      cur_A+=theVal;
      if (cur_A > full_A) {
         cur_A=full_A;
      }
   } // End of AdjustCurrent()


   /** Adjusts the Full value by theVall.  The full value cannot go below
    * 0 nor above the full value.
    * @param theVal A positive or negative value for adjusting the full value.*/
   public void setFull(int theVal) {
      full_A+=theVal;
      if (full_A > max_A) {
         full_A=max_A;
      }

      if (full_A < cur_A) {
         cur_A=full_A;
      }
   } // End of AdjustFull()


   /**
   @return the current value as an integer.
   */
   public int getCurrent() {
      return cur_A;
   } // End of GetCurrent()


   /**
   @return the Max value as an integer.
   */
   public int getMax() {
      return max_A;
   } // End of GetMax()


   /**
   @return the full value as an integer.
   */
   public int getFull() {
      return full_A;
   } // End of GetFull()


   /** Write the class properties to the output stream given.
   @param out A DataOutput stream the properties are to be written to.
   @exception java.IOException See the write@ functions for more info.
   */
   public void save(DataOutput out) throws IOException {
         out.writeInt(cur_A);
         out.writeInt(max_A);
         out.writeInt(full_A);
   } // End of Save()


   /** Write the class properties to the input stream given.
   @param in A DataIntput stream the properties are to be read from.
   @exception java.IOException See the write@ functions for more info.
   */
   public void load(DataInput in) throws IOException {
         cur_A = in.readInt();
         max_A = in.readInt();
         full_A = in.readInt();
   } // End of Load()


/* Properties for the class */
   private int cur_A;
   private int max_A;
   private int full_A;
} // End of Stats class.

