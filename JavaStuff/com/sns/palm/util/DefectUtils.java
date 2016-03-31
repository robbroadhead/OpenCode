package com.sns.palm.util;

/**
 * This class stores utility functions used by defect palm java
 * applications. It is intended to be static so all methods
 * need to be static. DefectUtils functions that are specific to
 * a single application need to be stored somewhere else.
 *
 * @version 1.0 20-May-2003
 * @author Rob Broadhead
 *
 *******************************************************************
 * Copyright Notice:
 *   Copyright © 2003 RB Consulting, Inc.
 *   All Rights Reserved.
 *
 *   This computer program is protected by copyright law and
 *   international treaties.  Unauthorized use or distribution of
 *   this program, or any portion of it is strictly prohibited.
 *   Violation may result in severe civil or criminal penalties.
 ******************************************************************
 *
 */

public class DefectUtils {
  /**
   * This method is used for building a list of priorities.
   *
   * @return a string array of the available priorities.
   */
  static public String[] LoadPriority() {
    /* Initialize local variables */
    String[] retVal;
    retVal=new String[5];

    retVal[0] = "Urgent";
    retVal[1] = "High";
    retVal[2] = "Medium";
    retVal[3] = "Low";
    retVal[4] = "Future";

    return retVal;
  } 

  /**
   * This method is used for building a list of statuses.
   *
   * @return a string array of the available statuses.
   */
  static public String[] LoadStatus() {
    /* Initialize local variables */
    String[] retVal;
    retVal=new String[10];

    retVal[0] = "New";
    retVal[1] = "Hold";
    retVal[2] = "Assigned";
    retVal[3] = "In Progress";
    retVal[4] = "Test Build";
    retVal[5] = "Test";
    retVal[6] = "Fix";
    retVal[7] = "Release";
    retVal[8] = "Closed";
    retVal[9] = "Cancel";

    return retVal;
  }

  /**
   * This returns a status string and is used to assist in setting values
   * that have been selected from a list.
   * @param idx the int value representing a status
   * @return the string version of the value
   */
  static public String StatusString(int idx) {
    String[] statList;

    if (idx < 1 || idx > 8) {
      return "Invalid";
    }
    statList = DefectUtils.LoadStatus();
    return statList[idx - 1];
  }

  /**
   * This method is used for building a list of categories.
   *
   * @return a string array of the available categories.
   */
  static public String[] LoadCategory() {
    /* Initialize local variables */
    String[] retVal;
    retVal=new String[8];

    retVal[0] = "Requirement";
    retVal[1] = "Bug Fix";
    retVal[2] = "Research";
    retVal[3] = "Testing";
    retVal[4] = "Documentation";
    retVal[5] = "Maintenance";
    retVal[6] = "Action/To-Do";
    retVal[7] = "Contact";

    return retVal;
  }
} // End of DefectUtils class.

