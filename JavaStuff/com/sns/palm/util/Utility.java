package com.sns.palm.util;

/**
 * This class stores utility functions used by most palm java
 * applications. It is intended to be static so all methods
 * need to be static. Utility functions that are specific to
 * a single application need to be stored somewhere else.
 *
 * @version 1.0 21-Jun-2002
 * @author Rob Broadhead
 *
 *******************************************************************
 * Copyright Notice:
 *   Copyright © 2002 RB Consulting, Inc.
 *   All Rights Reserved.
 *
 *   This computer program is protected by copyright law and
 *   international treaties.  Unauthorized use or distribution of
 *   this program, or any portion of it is strictly prohibited.
 *   Violation may result in severe civil or criminal penalties.
 ******************************************************************
 *
 * This is sample code and will not compile without the SNS Object for
 * records named Reportable. If you wish to use any of these methods by
 * themselves then email Develop@RB-SNS.com for permission.
 */
import javax.microedition.rms.*;
import java.io.*;
import java.util.Vector;
import com.sns.palm.util.Tracer;

public class Utility {
  /**
   *  This method takes a value in the form of a byte array and searches
   *  through a given record store to find the first record that has a
   *  matching byte array. The method is only going to look for a match
   *  at a specific point in the records so it is aimed at searching for
   *  the value in a single field.
   *
   * @param theValue the byte pattern that we are searching for
   * @param start the starting point in the record for the field we
   *   are searching.
   * @param length how many bytes to compare in each record
   * @param theStore the name of the RecordStore
   *
   * @return int The record number that the value is at. A -1 is returned
   *   if no value was found and a -2 is returned if an exception occurred.
   *
   */
  static public int searchStore(byte[] theValue,int start,int length,String theStore) {
    RecordStore rs;
    byte[] curRecord;
    int numRecs,retVal,idx;
    RecordEnumeration re;
    Tracer.setActive(true);

    // Open the store we are searching
    rs=openRecordStore(theStore);
    try {
      numRecs = rs.getNumRecords();
      re = rs.enumerateRecords(null,null,true);
    } catch (RecordStoreNotOpenException e) {
      Tracer.log("searchStore RS Exception:" + e.getMessage(),Tracer.ERROR);
      return -2;
    }

    idx = 0;
    retVal = -1;

    // Search through each record until we get a match
    while ((retVal < 0)&&(re.hasNextElement())) {
      try {
        curRecord = re.nextRecord();
      } catch (Exception e) {
        Tracer.log("searchStore Enum Exception:" + e.getMessage(),Tracer.ERROR);
        return -2;
      }
      // Assume a match and increment the index we are searching.
      retVal = ++idx;

      // Compare bytes until we find one that does not match and then skip
      // to the next record.
      for(int i=0;i<length;i++) {
      	//Tracer.log("searchStore[" + i + "]-->" + curRecord[i+start] + ":" + theValue[i],Tracer.LOG);
        if (curRecord[i+start]!=theValue[i]) {
          retVal = -1;
          break;
        }
      }
    }
    return retVal;
  }

  /**
   *  This method takes a value in the form of a byte array and searches
   *  through a given record store to find all of the records that have a
   *  matching byte array. The method is only going to look for a match
   *  at a specific point in the records so it is aimed at searching for
   *  the value in a single field.
   *
   * @param theValue the byte pattern that we are searching for
   * @param start the starting point in the record for the field we
   *   are searching.
   * @param length how many bytes to compare in each record
   * @param theStore the name of the RecordStore
   *
   * @return int[] The record number that the value is at. A -1 is returned
   *   if no value was found and a -2 is returned if an exception occurred.
   *
   */
  static public Vector multiSearchStore(byte[] theValue,int start,int length,String theStore) {
    RecordStore rs;
    byte[] curRecord;
    int numRecs,idx;
    Vector retVal = new Vector();
    RecordEnumeration re;

    rs=openRecordStore(theStore);
    try {
      numRecs = rs.getNumRecords();
      re = rs.enumerateRecords(null,null,true);
    } catch (RecordStoreNotOpenException e) {
      return retVal;
    }

    idx = 0;
    while (re.hasNextElement()) {
      boolean foundit = true;
      try {
        curRecord = re.nextRecord();
      } catch (Exception e) {
        retVal.addElement(new Integer(-2));
        return retVal;
      }

      // Compare bytes until we find one that does not match.
      for(int i=0;i<length;i++) {
        if (curRecord[i+start]!=theValue[i]) {
          foundit = false;
          break;
        }
      }
      if (foundit) {
        retVal.addElement(new Integer(idx));
      }
      // Increment the index we are searching.
      idx++;
    }

    return retVal;
  }

  /**
   * This method sorts an array of strings using a quicksort algorithm. The
   * sorted array is returned. This assumes that the strings are of a
   * firstname lastname pattern and that we want lastname,firstname for
   * display.
   *
   * @param theList the array of strings that we want to sort.
   *
   * @return String[] the sorted array of strings
   */
  static public String[] sortStringList(String[] theList) {
    /* This isn't real clean, but it is a quick and simple solution for now. */
    for (int i=0;i<theList.length;i++) {
      int idx = theList[i].indexOf(" ");
      if (idx > 0) {
        theList[i] = theList[i].substring(idx,theList[i].length()) + ", " + theList[i].substring(0,idx);
      }
    }


    quicksort(theList,0,theList.length - 1);
    return theList;
  }

  /**
   * This quicksort is modified from a Gosling example and simply sorts an
   * array of strings. This is a recursive method.
   *
   * @param a This is the array of strings to be sorted and it is
   *   modified during the sorting process.
   * @param loval the index of the assumed low value for this iteration.
   * @param hival the index of the assumed high value for this iteration.
   */
  static private void quicksort(String a[], int loval, int hival) {
    int lo = loval;
    int hi = hival;

    if ( lo >= hi ) {
      return;
    } else if (lo == hi - 1) {
      if ( a[lo].compareTo(a[hi]) > 0 ) {
        String T = a[lo];
        a[lo] = a[hi];
        a[hi] = T;

        return;
      }
    }

    String pivot = a[(lo + hi)/2];
    a[(lo + hi)/2] = a[hi];
    a[hi] = pivot;

    while ( lo < hi ) {
      while ( a[lo].compareTo(pivot) <= 0 && lo < hi ) {
        lo++;
      }

      while ( pivot.compareTo(a[hi])<=0 && lo < hi ) {
        hi--;
      }

      if ( lo < hi ) {
        String T = a[lo];
        a[lo] = a[hi];
        a[hi] = T;
      }
    }

    a[hival] = a[hi];
    a[hi] = pivot;

    quicksort(a,loval,lo - 1);
    quicksort(a,hi + 1,hival);
  }

  /**
   * Converts a byte value to a boolean. This assumes that a 0 is true
   * and a 1 is false.
   *
   * @param val the value to convert.
   * @return boolean the converted value.
   */
  static public boolean ByteToBool(byte val) {
    if (val == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Converts a boolean value to a byte. This assumes that a 0 is true
   * and a 1 is false.
   *
   * @param val the value to convert.
   * @return byte the converted value.
   */
  static public byte BoolToByte(boolean val) {
    if (val) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   * This is a string to byte array conversion method. The existing java
   * conversion in the String class had some bugs so this is a replacement
   * until the bug gets fixed. It is best to pair this with the ByteToStr
   * method to make sure that a proper conversion is done.
   *
   * @param val The string to be converted
   * @return The byte array that represents the string.
   */
  static public byte[] StrToByte(String val) {
    byte retVal[];
    int count,items;

    /* Initialize the variables */
    items = val.length();
    retVal = new byte[items];

    /* Build a char array */
    char tempArray[] = val.toCharArray();

    /* Convert to a byte array */
    for (count=0;count < items;count++) {
      retVal[count] = (byte) tempArray[count];
    }

    return retVal;
  }

  /**
   * This is for converting long values into a byte array so they can
   * be stored in a RecordStore.
   *
   * @param val the value to be converted to a byte array
   * @return the byte array representing the long value. If an exception
   *   occurred then an array of all zero's will be returned.
   */
  static public byte[] LongToByte(long val) {
    byte retVal[];
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream outputStream = new DataOutputStream(baos);

    retVal = new byte[8];
    try {
      outputStream.writeLong(val);
    } catch (Exception e) {
      for (int i=0;i<8;i++) {
        retVal[i]=0;
      }
    }

    retVal = baos.toByteArray();

    return retVal;
  }

  /**
   * This converts a byte array to a long value based on the LongtoByte
   * conversion method.
   *
   * @param val the byte array to convert
   * @return the converted long value
   */
  static public long ByteToLong(byte[] val) {
    long retVal;
    ByteArrayInputStream bais = new ByteArrayInputStream(val);
    DataInputStream inputStream = new DataInputStream(bais);

    try {
      retVal = inputStream.readLong();
    } catch (Exception e) {
      retVal = -1;
    }

    return retVal;
  }

  /**
   * This converts a byte array into a string. It is based on the StrToByte
   * method.
   *
   * @param val the byte array to convert
   * @return the converted string
   */
  static public String ByteToStr(byte[] val) {
    int count,items;
    char tempArray[];

    /* Initialize local variables */
    items = val.length;
    tempArray = new char[items];

    /* Build a char array */
    for (count=0;count < items;count++) {
      tempArray[count] = (char) val[count];
    }

    String retVal = new String(tempArray);
    return retVal;
  }

  /**
   * This simply pads a string with spaces out to a specified length.
   *
   * @param val the string that is to be padded
   * @param size the length the string should be
   * @return the padded string
   */
  static public String padString(String val,int size) {
    /* Initialize local variables */
    int count = 0;
    String retVal;
    StringBuffer result = new StringBuffer(val);

    if (val.length() > size) {
      result.setLength(size);
    }

    /* Build a char array */
    for (count=val.length();count < size;count++) {
      result.append(" ");
    }

    retVal = result.toString();
    return retVal;
  }

  /**
   * This opens a record store and creates it if it is not found.
   *
   * @param name the record store to open
   * @return the record store in an opened state
   */
  static public RecordStore openRecordStore(String name) {
    RecordStore retVal = null;

    try {
      retVal = RecordStore.openRecordStore(name,false);
    } catch (RecordStoreNotFoundException e) {
      Utility.createRecordStore(name);
      try {
        retVal = RecordStore.openRecordStore(name,false);
      } catch (Exception x) {
        return retVal;
      }
    } catch (Exception ex) {
    }

    return retVal;
  }

  /**
   * This attempts to create a record store and returns a boolean to let
   * the caller know if it was successful. The store is closed after it
   * is created so it can be opened without problems.
   *
   * @param name the record store to create
   * @return true if the record store was properly created
   */
  static public boolean createRecordStore(String name) {
    RecordStore rs = null;
    boolean retVal = false;

    /* Remove the store if it exists */
    try {
      RecordStore.deleteRecordStore(name);
    } catch (Exception e) {
      // We can ignore this since we may be creating a new store.
    }

    /* Attempt to create the record store. */
    try {
      rs = RecordStore.openRecordStore(name,true);
    } catch (RecordStoreException rse) {
      return retVal;
    }

    retVal = true;

    /* The work is done, so just close the store. */
    try {
      rs.closeRecordStore();
    } catch (RecordStoreException rse) {}

    return retVal;
  }

} // End of Utility class.

