package com.sns.palm.util;

/**
 * This class stores utility functions used by refeee palm java
 * applications. It is intended to be static so all methods
 * need to be static. RefereeUtils functions that are specific to
 * a single application need to be stored somewhere else.
 *
 * @version 1.0 09-Apr-2003
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
 * This is sample code and will not compile without the SNS Object for
 * records named Reportable. If you wish to use any of these methods by
 * themselves then email Develop@RB-SNS.com for permission.
 */

public class RefereeUtils {
  /**
   * This method is used for building a list of referee grades.
   *
   * @return a string array of the referee grades from grade 10 to 1.
   */
  static public String[] LoadGrades() {
    /* Initialize local variables */
    String[] retVal;
    retVal=new String[10];

    retVal[0] = "USSF Linesman (10)";
    retVal[1] = "USSF Associate Ref. (9)";
    retVal[2] = "Ref. Class 2 (8)";
    retVal[3] = "Ref. Class 1 (7)";
    retVal[4] = "State Ref. Class 2 (6)";
    retVal[5] = "State Ref. Class 1 (5)";
    retVal[6] = "National Referee (4)";
    retVal[7] = "National Ref. (IPC) (3)";
    retVal[8] = "International Line (2)";
    retVal[9] = "International Ref. (1)";

    return retVal;
  }

  /**
   * This is for building a list of age brackets. An attempt is made to order
   * them from easiest to hardest level to referee.
   *
   * @return a string array of age brackets from youngest to oldest.
   */
  public static String[] LoadAges() {
    /* Initialize local variables */
    String[] retVal;
    retVal=new String[25];

    retVal[0] = "U5";
    retVal[1] = "U6";
    retVal[2] = "U7";
    retVal[3] = "U8";
    retVal[4] = "U9";
    retVal[5] = "U10";
    retVal[6] = "U11";
    retVal[7] = "U12";
    retVal[8] = "U13";
    retVal[9] = "Middle School";
    retVal[10] = "U14";
    retVal[11] = "U15";
    retVal[12] = "U16";
    retVal[13] = "U17";
    retVal[14] = "U18";
    retVal[15] = "U19";
    retVal[16] = "High School";
    retVal[17] = "U30";
    retVal[18] = "College";
    retVal[19] = "O30";
    retVal[20] = "O35";
    retVal[21] = "Semi-Pro";
    retVal[22] = "Pro";
    retVal[23] = "Indoor";
    retVal[24] = "Other";

    return retVal;
  }
} // End of RefereeUtils class.

