package com.sns.Apps.Referee.db;

import java.io.*;

/**
 * Embodies the description of a record from the database.
 * It describes a record number, a list of Strings which reflect
 * the values of the fields in, and a description of each field.
 *
 * @version 1.2  27-Mar-2001
 */
public class DataInfo implements Serializable {
  /** The current record we are working with.
  * @serial recordNumber The current record. */
  private int recordNumber;
  /** Values for each column of the current record.
  * @serial values The values for the current record. */
  private String [] values;
  /** Definition of the fields of the current record.
  * @serial fields The fields for the record. */
  private FieldInfo [] fields;
  /** Variable in the original code.  I assume this is used to differentiate
  * provided classes from those created to complete the assignment. */
  final static char sc = 'A';
  /**
   * Creates a DataInfo object which has null
   * Strings for its field values.
   *
   * @param recordNumber - the unique number for this record.
   * @param fields - the array of FieldInfo objects
   *                             which represent the names and
   *                             lenghts of the fields in this
   *                             DataInfo object.
   */
  public DataInfo(int recordNumber, FieldInfo [] fields) {
    this.recordNumber = recordNumber;
    this.fields = new FieldInfo[fields.length];
    System.arraycopy(fields, 0, this.fields, 0, fields.length);
    this.values = new String[fields.length];
  }

  /**
   * Creates a DataInfo object that contains
   * Strings for its field values.
   *
   * @param recordNumber - the unique number for this record.
   * @param fields - the array of FieldInfo objects
   *                             which represent the names and
   *                             lenghts of the fields in this
   *                             DataInfo object.
   * @param values - The values for the field values.
   */
  public DataInfo(int recordNumber, FieldInfo [] fields, String [] values) {
    this(recordNumber, fields);
    System.arraycopy(values, 0, this.values, 0, values.length);
  }

  /**
   * Returns the record number associated with this
   * DataInfo.
   *
   * @return int - the unique record number of this DataInfo
   */
  public int getRecordNumber() {
    return recordNumber;
  }

  /**
   * Returns an array of Strings, which reflect the
   * values of the fields in this DataItem.
   *
   * @return String[] - String array of all the field values
   *                     of this DataInfo.
   */
  public String [] getValues() {
    return values;
  }

  /**
   * Returns an array of FieldInfo objects. These
   * describe the database schema in terms of the name and
   * width of each field.
   *
   * @return FieldInfo[] - FieldInfo array of all the fields
   *                        of this DataInfo.
   */
  public FieldInfo [] getFields() {
    return fields;
  }

  /**
   * Constructs and returns a String which describes
   * this DataInfo object in a form suitable for debug output.
   *
   * @return String - A textual representation of this DataInfo.
   */
  public String toString() {
    StringBuffer rv = new StringBuffer(super.toString() +
      "recordNumber = " + recordNumber +
      "\nFields:");
    for (int i = 0; i < fields.length; i++) {
      rv.append("Name = " + fields[i].getName() +
	" value = " + values[i] + "\n");
    }
    return rv.toString();
  }
}
