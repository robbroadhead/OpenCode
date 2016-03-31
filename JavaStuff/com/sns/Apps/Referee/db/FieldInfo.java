package com.sns.Apps.Referee.db;

import java.io.*;

/**
 * Embodies the name of a field and the maximum width
 * that it may have.
 *
 * @version 1.1  27-Mar-2001
 */
public class FieldInfo implements Serializable {
  /** The name of the field.  Also known as a column name.
  * @serial name The name of the field. */
  private String name;
  /** Size of the field in bytes.
  * @serial length The size of the field. */
  private int length;

  /** Variable in the original code.  I assume this is used to differentiate
  * provided classes from those created to complete the assignment. */
  final static char sc = 'A';

  /**
   * Constructs an initialized FieldInfo object.
   *
   * @param String name - the name of the field.
   * @param int length - the length of the field.
   */
  public FieldInfo(String name, int length) {
    this.name = name;
    this.length = length;
  }

  /**
   * Returns the name of the field.
   *
   * @return String - the name of the field.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the length of the field.
   *
   * @return int - the length of the field.
   */
  public int getLength() {
    return length;
  }
}
