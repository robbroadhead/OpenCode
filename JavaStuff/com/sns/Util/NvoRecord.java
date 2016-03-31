package com.sns.Util;
//Title:        NvoRecord
//Version:      1.0
//Copyright:    Copyright (c) 1998
//Author:       Rob Broadhead
//Company:      Sleepless Nights Software
//Description:  Base object for storing infornation
//  all data objects should inherit from this in
//  order to have consistant, unique key values

import java.io.*;

public class NvoRecord {

  public NvoRecord() {
  } // End of NvoRecord() constructor.

  public NvoRecord(String val) {
  	keyName_A=val;
  } // End of NvoRecord() constructor.

  public void readData(DataInput in) throws IOException {
  	key_A=in.readDouble();
  } // End of readData() method.

  public void writeData(DataOutput out) throws IOException {
  	out.writeDouble(key_A);
  } // End of writeData() method.

  public boolean equals(NvoRecord test) {
		return (key_A==test.GetKey());
  } // End of equals() method.

  private final String GenID() {
  	return com.sns.Util.Preference.getPreference(keyName_A);
  } // End of GenID() method.

  public final double GetKey() {
		return key_A;
  } // End of GetKey() method.

  public final int GetStatus() {
    return status_A;
  } // End of GetStatus() method.

  protected final void SetStatus(int val) {
  /***************************/
  /* Valid Status values:
  /*       0 --> Up-to-date
  /*       1 --> New
  /*       2 --> Altered
  /*       3 --> Removed
  /***************************/
		if ((status_A>0) && (status_A<4))
		{
			status_A=val;
		}
  } // End of SetStatus() method.

  public final NvoRecord GetNext() {
		return next_A;
  } // End of GetNext() method.

  public final NvoRecord GetPrevious() {
		return prev_A;
  } // End of GetPrevious() method.

  public final void SetNext(NvoRecord val) {
  /* Assume nothing, only set next pointer. */
  	next_A=val;
  } // End of SetNext() method.

  public final void SetPrevious(NvoRecord val) {
  /* Assume nothing, only set prev pointer. */
  	prev_A=val;
  } // End of SetPrevious() method.

  public String toString() {
		return Double.toString(key_A);
  } // End of toString() method.

  // The Class Properties
  private double key_A=-1;
  private NvoRecord prev_A;
  private NvoRecord next_A;
  private int status_A=0;
  private String keyName_A;
  // End of property definitions.
}
