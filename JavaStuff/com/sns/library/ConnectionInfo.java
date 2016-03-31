/*******************************************************************
 ** @(#)$Workfile$
 **
 ** $Author: robbroadhead $
 ** $Revision: 1.1 $ $Date: 2007/12/27 15:23:12 $
 ** 
 ** General description -  
 **  This is used to store details for a connection to a database.
 ** 
 ** See also:
 **  @see {??use for cross-reference to other package, class, etc.}
 ******************************************************************* 
 ** Copyright Notice:
 **   Copyright © 2000 MDL Information Systems, Inc.
 **   All Rights Reserved.
 **
 **   This computer program is protected by copyright law and 
 **   international treaties.  Unauthorized use or distribution of
 **   this program, or any portion of it is strictly prohibited.
 **   Violation may result in severe civil or criminal penalties.
 ******************************************************************
 ** Revision Log:
 **
 ** $Log: ConnectionInfo.java,v $
 ** Revision 1.1  2007/12/27 15:23:12  robbroadhead
 ** Initial Check-in to new repository.
 **
 ** Revision 1.1  2005/02/28 00:57:19  Rob
 ** Initial Check-in to CVS
 **
 ******************************************************************
 ** $NoKeywords$
 ******************************************************************/
package com.sns.library;
 
import java.io.*;

/**
* This is used to store details for a connection to a database.
*
* @version 1.0 30-Sep-2000
* @author Rob Broadhead
* @see ConnectionFrame
*/
public class ConnectionInfo implements Serializable {
   /** Properties for the class */
   private String name;
   private String urlAddress;
   private String driver;
   private String userName;
   private String password;

   /** 
   * The no-args constructor is just here in case of inheritance.
   */
   public ConnectionInfo() {
   }

   /** 
   * A constructor that includes all of the properties required for an object
   * of this type.
   */
   public ConnectionInfo(String n, String d, String url, String usr, String pwd) {
      urlAddress = url;
      driver = d;
      userName = usr;
      password = pwd;
      name = n;
   }

   /**
   * Method for retrieving the user login name for this connection.
   * @return String The user used to login for the connection.
   */
   public String getUser () {
      return userName;
   }

   /**
   * Method for retrieving the server address this connection.
   * @return String The address for the server for this connection.
   */
   public String getAddress () {
      return urlAddress;
   }

   /**
   * Method for retrieving the password used for this connection.
   * @return String The password used for this connection.
   */
   public String getPassword () {
      return password;
   }

   /**
   * Method for retrieving the driver used in this connection.
   * @return String The driver used with this connection.
   */
   public String getDriver () {
      return driver;
   }

   /**
   * Method for retrieving the name used to identify the connection.
   * @return String The name used to identify this connection.
   */
   public String getName () {
      return name;
   }

   /**
   * This sets the name for the connection.
   * @param String val The new value for the connection name.
   */
   public void setName(String val) {
      name = val;
   }

   /**
   * This sets the login name for the connection.
   * @param String val The new value for the login name.
   */
   public void setUser(String val) {
      userName = val;
   }

   /**
   * This sets the login name for the connection.
   * @param String val The new value for the login name.
   */
   public void setAddress(String val) {
      urlAddress = val;
   }

   /**
   * This sets the login password for the connection.
   * @param String val The new value for the password.
   */
   public void setPassword(String val) {
      password = val;
   }

   /**
   * This sets the driver for the connection.
   * @param String val The new value for the driver.
   */
   public void setDriver(String val) {
      driver = val;
   }
}
