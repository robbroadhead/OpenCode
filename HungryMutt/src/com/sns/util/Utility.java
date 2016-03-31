package com.sns.util;

import java.sql.*;
import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * This class stores utility functions used throughout RB Consulting web applications
 * and is not intended to be instantiated. All of the methods here should be
 * designed to stand on their own and not hold any state.
 *
 * @version 1.0 2-17-2004
 * @author Rob Broadhead (Rob@rb-sns.com)
 *
 *******************************************************************
 * Copyright Notice:
 *   Copyright © 2004 RB Consulting, Inc.
 *   All Rights Reserved.
 *
 *   This computer program is protected by copyright law and
 *   international treaties.  Unauthorized use or distribution of
 *   this program, or any portion of it is strictly prohibited.
 *   Violation may result in severe civil or criminal penalties.
 ******************************************************************
 *
 */

public class Utility {

	/**
	 * This method tests whether a string is null, empty or contains only
	 * white space characters. Empty in the case of this method includes
	 * the string " " or a string that contains a line feed, carriage return
	 * or other white space characters.
	 *
	 * @param val The string we want to test.
	 * @return true if the string is empty or null
	 */
	static public boolean isEmptyString(String val) {
		boolean retVal = true;

		if (val != null) {
			if (val.trim().length() > 0) {
				retVal = false;
			}
		}

		return retVal;
	}

	/**
	 * This method takes a table name and checks for the number of rows in that table.
	 *
	 * @param tableName This is the name of the table we are checking
	 *
	 * @return int The number of rows in the table
	 *
	 */
	static public int numRows(String tableName) {
		int retVal = 0;

		PreparedStatement stmt;
		ResultSet rs;
		try {
			Connection con = Torque.getConnection();

			stmt =
				con.prepareStatement(
					"Select count(*) as theCount from " + tableName);
			stmt.executeQuery();

			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				retVal = rs.getInt("theCount");
			}
			rs.close();
			stmt.close();
			Torque.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (TorqueException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * This method takes a table name and a value and checks whether
	 * that value already exists in the code column.
	 *
	 * @param tableName This is the name of the table we are checking
	 * @param tableName The value we are looking for
	 *
	 * @return int The number of rows in the table
	 *
	 */
	static public boolean isValidCode(String tableName, String val) {
		boolean retVal = true;
		PreparedStatement stmt;
		ResultSet rs;
		try {
			Connection con = Torque.getConnection();
			stmt =
				con.prepareStatement(
					"Select count(*) as theCount from "
						+ tableName
						+ " where Code = ?");
			stmt.setString(1, val);

			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				if (rs.getInt("theCount") > 0) {
					retVal = false;
				}
			}
			rs.close();
			stmt.close();
			Torque.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (TorqueException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * This checks whether a user has a permission. The id of the user
	 * and the name of the permission are sent in and this checks the
	 * roles the user belongs to and the permissions assigned to those
	 * roles.
	 *
	 * @param int user The id of the user to check
	 * @param String val the permission we are checking for
	 * @return boolean This is true if the user has been assigned the permission.
	 */
	static public boolean hasPermission(int user, String val) {
		boolean retVal = false;
		Statement stmt, permstmt;
		ResultSet rs, permRS;
		int permID;

		try {
			Connection cn = Torque.getConnection();
			stmt = cn.createStatement();
			// Get the permission id
			rs =
				stmt.executeQuery(
					"Select permissionid from secpermission where name='"
						+ val
						+ "'");
			rs.next();
			permID = rs.getInt(1);

			// Get all of the roles assigned the user
			rs =
				stmt.executeQuery(
					"Select r.roleid from SecUserGroupRole r where r.userid="
						+ user);
			while (rs.next() && retVal == false) {
				// Now find the permissions for each role.
				permstmt = cn.createStatement();
				permRS =
					permstmt.executeQuery(
						"Select count(*) from secRolePermission srp where srp.permissionid="
							+ permID
							+ " and srp.roleid="
							+ rs.getInt(1));
				permRS.next();
				if (permRS.getInt(1) > 0) {
					retVal = true;
				}
				permstmt.close();
				permRS.close();
			}

			rs.close();
			stmt.close();
			Torque.closeConnection(cn);
		} catch (Exception e) {
			System.out.println("Exception retrieving Permissions for Role");
			e.printStackTrace();
		}

		return retVal;
	}

	/**
	 * This method tests whether a string is in a valid zip code format.
	 * This includes making sure the value is a number and does not contain
	 * alpha characters.
	 *
	 * @param val The string we want to test.
	 * @return true if the string is a valid format
	 */
	static public boolean isValidZip(String val) {
		boolean retVal = true;
		String part1, part2;

		if (val == null || val.trim().length() < 1) {
			return false;
		}

		StringTokenizer st = new StringTokenizer(val, "-");
		part1 = st.nextToken();
		// This should be 5 characters in length and numeric
		if (part1.trim().length() == 5) {
			try {
				Integer.parseInt(part1);
			} catch (NumberFormatException e) {
				retVal = false;
			}
		} else {
			retVal = false;
		}

		// Check four digit portion if needed.
		if (retVal == true && st.hasMoreTokens()) {
			part2 = st.nextToken();
			if (part2.trim().length() == 4) {
				try {
					Integer.parseInt(part2);
				} catch (NumberFormatException e) {
					retVal = false;
				}
			} else {
				retVal = false;
			}
		}

		// If we failed then try a Canadian zip
		if (retVal == false) {
			st = new StringTokenizer(val, " ");
			part1 = st.nextToken();
			if (st.hasMoreTokens()) {
				part2 = st.nextToken();
				if ((part1.trim().length() == 3)
					&& (part2.trim().length() == 3)) {
					retVal = true;
				}
			}
		}

		return retVal;
	}

	/**
	 * This method tests whether a string is in a valid phone number format.
	 * This includes making sure the value is a number and does not contain
	 * alpha characters.
	 *
	 * @param val The string we want to test.
	 * @return true if the string is a valid format
	 */
	static public boolean isValidPhone(String val) {
		boolean retVal = true;
		String part1, part2, part3;

		if (val == null || val.trim().length() < 1) {
			return retVal;
		}

		StringTokenizer st = new StringTokenizer(val, "-");
		part1 = st.nextToken();
		// This should be 3 characters in length and numeric
		if (part1.trim().length() == 3) {
			try {
				Integer.parseInt(part1);
			} catch (NumberFormatException e) {
				retVal = false;
			}
		} else {
			retVal = false;
		}

		// Check base portion
		if (retVal == true && st.hasMoreTokens()) {
			part2 = st.nextToken();
			if (part2.trim().length() == 3) {
				try {
					Integer.parseInt(part2);
				} catch (NumberFormatException e) {
					retVal = false;
				}
			} else {
				retVal = false;
			}
		} else {
			retVal = false;
		}

		// Check extension portion
		if (retVal == true && st.hasMoreTokens()) {
			part3 = st.nextToken();
			if (part3.trim().length() == 4) {
				try {
					Integer.parseInt(part3);
				} catch (NumberFormatException e) {
					retVal = false;
				}
			} else {
				retVal = false;
			}
		} else {
			retVal = false;
		}

		return retVal;
	}

	/**
	 * This method tests whether a string is in a valid date format. This
	 * format is mm/dd/yyyy for this method
	 *
	 * @param val The string we want to test.
	 * @return true if the string is a valid format
	 */
	static public boolean isValidDate(String val) {
		boolean retVal = true;
		String part1 = "", part2 = "", part3 = "";

		if (val == null || val.trim().length() < 1) {
			return retVal;
		}

		StringTokenizer st = new StringTokenizer(val, "/");
		// First we make sure we have month day and year parts (3)
		part1 = st.nextToken();
		if (st.hasMoreTokens()) {
			part2 = st.nextToken();
		} else {
			retVal = false;
		}
		if (st.hasMoreTokens()) {
			part3 = st.nextToken();
		} else {
			retVal = false;
		}
		// Month must be 1 - 12
		if (retVal = true) {
			try {
				int temp = Integer.parseInt(part1);
				if (temp < 1 || temp > 12) {
					retVal = false;
				}
			} catch (NumberFormatException e) {
				retVal = false;
			}
		}

		// Day must be 1 - 31 we won't worry about 6/31/xxxx or 2/31/xxxx, etc for now
		if (retVal = true) {
			try {
				int temp = Integer.parseInt(part2);
				if (temp < 1 || temp > 31) {
					retVal = false;
				}
			} catch (NumberFormatException e) {
				retVal = false;
			}
		}
		// Year just has to be a number we'll assume the user knows what they are doing.
		if (retVal = true) {
			try {
				int temp = Integer.parseInt(part3);
				if (temp > 9999) {
						retVal = false;
				}
			} catch (NumberFormatException e) {
				retVal = false;
			}
		}

		return retVal;
	}

	/**
	 * Convert a string date in the YYYY-MM-DD format into a java.sql.Date
	 * object.
	 *
	 * @param val The date string to be converted
	 * @return A date object matching the string sent in
	 */
	static public java.util.Date StrToDate(String val) {
		Calendar cal = Calendar.getInstance();

		if (val != null) {
			StringTokenizer st = new StringTokenizer(val, "-");

			int temp = Integer.parseInt(st.nextToken());
			if (temp < 100) {
				if (temp > 70) {
					temp+=1900;
				} else {
					temp+=2000;
				}
			}
			cal.set(Calendar.YEAR, temp);
			cal.set(Calendar.MONTH, Integer.parseInt(st.nextToken()) - 1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));
		}

		return new Date(cal.getTimeInMillis());
	}

	/**
	 * Convert a string date in the YYYY-MM-DD HH:MM format into a java.util.Date
	 * object.
	 *
	 * @param val The date string to be converted
	 * @return A date object matching the string sent in
	 */
	static public java.util.Date StrToDateTime(String val) {
		Calendar cal = Calendar.getInstance();

		if (val != null) {
			StringTokenizer main = new StringTokenizer(val, " ");
			StringTokenizer st = new StringTokenizer(main.nextToken(), "-");

			// First grab the date
			cal.set(Calendar.YEAR, Integer.parseInt(st.nextToken()));
			cal.set(Calendar.MONTH, Integer.parseInt(st.nextToken()) - 1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));

			// Now grab the time
			StringTokenizer temptime = new StringTokenizer(main.nextToken(), ":");
			int hour = Integer.parseInt(temptime.nextToken());
			cal.set(Calendar.MINUTE,Integer.parseInt(temptime.nextToken()));

			if (main.hasMoreTokens()) {
				cal.set(Calendar.HOUR,hour);
				int merid = Calendar.AM;
				if (main.nextToken().equals("PM")) {
					merid = Calendar.PM;
				}
				cal.set(Calendar.AM_PM,merid);
			} else {
				cal.set(Calendar.HOUR_OF_DAY,hour);
			}
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MILLISECOND,0);
		}
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * Convert a string date in the YYYY-MM-DD HH:MM format into a java.sql.Date
	 * object.
	 *
	 * @param val The date string to be converted
	 * @return A date object matching the string sent in
	 */
	static public java.sql.Date StrToSqlDate(String val) {
		Calendar cal = Calendar.getInstance();

		if (val != null) {
			StringTokenizer main = new StringTokenizer(val, " ");
			StringTokenizer st = new StringTokenizer(main.nextToken(), "-");

			// First grab the date
			cal.set(Calendar.YEAR, Integer.parseInt(st.nextToken()));
			cal.set(Calendar.MONTH, Integer.parseInt(st.nextToken()) - 1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));

			// Now grab the time
			StringTokenizer temptime = new StringTokenizer(main.nextToken(), ":");
			int hour = Integer.parseInt(temptime.nextToken());
			cal.set(Calendar.MINUTE,Integer.parseInt(temptime.nextToken()));

			if (main.hasMoreTokens()) {
				cal.set(Calendar.HOUR,hour);
				int merid = Calendar.AM;
				if (main.nextToken().equals("PM")) {
					merid = Calendar.PM;
				}
				cal.set(Calendar.AM_PM,merid);
			} else {
				cal.set(Calendar.HOUR_OF_DAY,hour);
			}
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MILLISECOND,0);
		}
		return new Date(cal.getTimeInMillis());
	}

	static public java.util.Date getDateNextMonth(java.util.Date current) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(current);
		Date retVal=null;

		// Make sure it is not December
		if (cal.get(Calendar.MONTH)==Calendar.DECEMBER) {
			cal.set(Calendar.MONTH,Calendar.JANUARY);
		} else {
			cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) + 1);
		}

		retVal = new Date(cal.getTimeInMillis());
		return retVal;
	}


	/**
	 * Given a date this returns the start date for the current year. This assumes the
	 * year runs from Sept 1 to Aug 31. Thus if the date is Sept 15, 2000 this returns
	 * Sept 1, 2000. if the date is June 12, 2000 then Sept 1 1999 is returned.
	 * 
	 * @return The start date as described.
	 */
	static public java.util.Date getAnnualStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date());
		Date retVal=null;

		// First we get the current year
		int curYear = cal.get(Calendar.YEAR);

		// Two cases, either it is before Sept 1 or after.
		// We only need to adjust the year if it is before Sept 1.
		if (cal.get(Calendar.MONTH) < Calendar.SEPTEMBER) {
			curYear--;
			cal.set(Calendar.YEAR,curYear);
		}

		cal.set(Calendar.MONTH,Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,1);

		retVal = new Date(cal.getTimeInMillis());
		return retVal;
	}

	/**
	 * Given a date this returns the end date for the current year. This assumes the
	 * year runs from Sept 1 to Aug 31. Thus if the date is Sept 15, 2000 this returns
	 * Aug 31, 2001. if the date is June 12, 2000 then Aug 31, 2000 is returned.
	 * 
	 * @return The end date as described.
	 */
	static public java.util.Date getAnnualEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date());
		Date retVal=null;

		// First we get the current year
		int curYear = cal.get(Calendar.YEAR);

		// Two cases, either it is before Sept 1 or after.
		// We only need to adjust the year if it is after Aug 31.
		if (cal.get(Calendar.MONTH) > Calendar.AUGUST) {
			curYear++;
			cal.set(Calendar.YEAR,curYear);
		}

		cal.set(Calendar.MONTH,Calendar.AUGUST);
		cal.set(Calendar.DAY_OF_MONTH,31);
		cal.set(Calendar.HOUR_OF_DAY,22);
		cal.set(Calendar.MINUTE,59);

		retVal = new Date(cal.getTimeInMillis());
		return retVal;
	}

	/**
	 * This converts a java.util.Date to a date string in the form of YYYY-MM-DD. If a null 
	 * is sent in then the current date is used for the conversion and returned
	 * as a string.
	 * 
	 * @param val The date to convert
	 * @return The date converted to a string in the format of YYYY-MM-DD
	 */
	static public String DateToStr(java.util.Date val) {
		Calendar cal = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		String year,month,day;

		if (val != null) {
			cal.setTime(val);
		}

		year = "" + cal.get(Calendar.YEAR);
		month = "" + (cal.get(Calendar.MONTH) + 1);
		day = "" + cal.get(Calendar.DAY_OF_MONTH);

		sb.append(padZeroString(year,4) + "-");
		sb.append(padZeroString(month,2) + "-");
		sb.append(padZeroString(day,2));

		return sb.toString();
	}

	/**
	 * Convert a phone number string in the form of 'XXXXXXXXXX' to the form 'XXX-XXX-XXXX'.
	 *  
	 * @param val
	 * @return
	 */
	static public String StrToPhone(String val) {
		if (val == null || val.trim().length() < 10) {
			return val;
		}
		
		StringBuffer sb = new StringBuffer();
		String pt1,pt2,pt3;

		pt1 = val.substring(0,3);
		pt2 = val.substring(3,6);
		pt3 = val.substring(6);

		sb.append(pt1 +"-");
		sb.append(pt2 + "-");
		sb.append(pt3);
		return sb.toString();
	}

	/**
	 * Convert a phone number string in the form of XXX-XXX-XXXX to the form XXXXXXXXXX
	 *  
	 * @param val
	 * @return
	 */
	static public String PhoneToStr(String val) {
		StringBuffer sb = new StringBuffer();
		String pt1,pt2,pt3;

		pt1 = val.substring(0,3);
		pt2 = val.substring(4,7);
		pt3 = val.substring(8);

		sb.append(pt1);
		sb.append(pt2);
		sb.append(pt3);
		return sb.toString();
	}

	/**
	 * This converts a java.util.Date to a datetime string in the form of YYYY-MM-DD HH:MM.
	 * If a null is sent in then the current datetime is used for the conversion and returned
	 * as a string. The returned form is military time so hours will range from 0 - 23.
	 * 
	 * @param val The date to convert.
	 * @return The date converted to a string in the format of YYYY-MM-DD HH:MM
	 */
	static public String DateTimeToStr(java.util.Date val) {
		Calendar cal = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		String year,month,day,hour,min;

		if (val != null) {
			cal.setTime(val);
		}

		year = "" + cal.get(Calendar.YEAR);
		month = "" + (cal.get(Calendar.MONTH) + 1);
		day = "" + cal.get(Calendar.DAY_OF_MONTH);
		hour = "" + cal.get(Calendar.HOUR_OF_DAY);
		min = "" + cal.get(Calendar.MINUTE);

		sb.append(padZeroString(year,4) + "-");
		sb.append(padZeroString(month,2) + "-");
		sb.append(padZeroString(day,2) + " ");
		sb.append(padZeroString(hour,2) + ":");
		sb.append(padZeroString(min,2));

		return sb.toString();
	}

	/**
   * This simply pads a string with 0s out to a specified length.
   *
   * @param val the string that is to be padded
   * @param size the length the string should be
   * @return the padded string
   */
  static public String padZeroString(String val,int size) {
    /* Initialize local variables */
    int count = 0;
    String retVal;
    
    val = val.trim();
    if (val == null) {
    	val="";
    }    
    StringBuffer result = new StringBuffer(val);

    if (val.length() > size) {
      result.setLength(size);
    }

    /* Build a char array */
    for (count=val.length();count < size;count++) {
      result.insert(0,"0");
    }

    retVal = result.toString();
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

    val = val.trim();
    if (val == null) {
    	val="";
    }
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

} // End of Utility class.
