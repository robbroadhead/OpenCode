package com.sns.Apps.Referee.rmi;

/** DataServer.java */
import java.rmi.server.*;
import java.io.*;
import java.rmi.*;
import com.sns.Apps.Referee.db.*;

/**
 * The server application for a database.  RMI was used to
 * keep the networking functionality simple and easier to read and maintain.
 *
 * @version 1.1  17-Nov-1997
 */
public class DataServer extends UnicastRemoteObject implements dataRemote {
   /** The database used by the server.  This is set when the server is created.
   * @serial theDB The database used by the server. */
   private Data theDB;
   /** The name of the server.  This is an IP address.
   * @serial serverAddr The ip address for the server. */
   private String serverAddr;

   /**
   * This method runs the server application.  This a command line server
   * since no interface is currently required.  No parameters are required.
   */
   public static void main (String[] args) {
      System.setSecurityManager(new RMISecurityManager());

      try {
         System.out.println("Connecting Server...");
         DataServer srv = new DataServer();
         Naming.rebind("dbsrv",srv);
         System.out.println("Server is now active");
      } catch (Exception e) {
         System.out.println("Startup Exception: " + e.getMessage());
         e.printStackTrace();
      }
   }

   /**
   * Just a no args cinstructor.
   * @exception RemoteException Thrown if rmi service fails.
   */
   public DataServer() throws java.rmi.RemoteException {
      super();
   }

   /**
   * This method is called by the a client to open a database.  This server
   * can currently only handle one database open at a time.
   *
   * @param String dbName The name of the database file.
   * @exception RemoteException Thrown if rmi service fails.
   */
   public void openDB(String dbName) throws java.rmi.RemoteException,IOException {
      theDB = new Data(dbName);
      System.out.println("Client opened database: " + dbName);
   }

   /**
   * This method can be called by a client to create a new database.
   *
   * @param String dbname The name of the database file to open.
   * @param FieldInfo[] fields The list of fields for the schema of
   *          this database.
   *
   * @exception IOException Thrown if cannot create database file.
   * @exception RemoteException Thrown if rmi service fails.
   */
   public void createDB(String dbName,FieldInfo[] fields) throws java.rmi.RemoteException,IOException {
      theDB = new Data(dbName,fields);
      System.out.println("Client created database: " + dbName);
   }

   /**
   * This method is called by the client to add a record to the database.
   *
   * @param String[] theData The record that we will be adding.
   *
   * @exception RemoteException Thrown if rmi service fails.
   * @exception DatabaseException Thrown if there is an error adding the record.
   */
   public void add(String[] theData) throws java.rmi.RemoteException,DatabaseException {
      theDB.add(theData);
   }

   /**
   * This method closes the database file.
   *
   * @exception RemoteException Thrown if rmi service fails.
   */
   public void close() throws java.rmi.RemoteException{
      theDB.close();
      System.out.println("Client closed the database");
   }

   /**
   * This method searches the database for all records that match the values
   * given in the parameter string.  The parameter is a string consisting of
   * field names an '=' and a value in single quotes with each field name
   * and value pair being separated by commas (eg. "Carrier='SpeedyAir',Origin
   * ='SFO'").  The fields specified will be checked in each record to see if
   * they match the corresponding value.  All matches found will be returned
   * in the DataInfo array.  Data is returned so each client can have a different
   * filter currently in effect.
   *
   * @param criteria a string of comma separated field name-value pairs to
   *        search for records with the field matching the value.
   * @return DataInfo[] returns all records matching the criteria.
   *
   * @exception RemoteException Thrown if rmi service fails.
   */
   public DataInfo[] criteriaFind(String criteria) throws java.rmi.RemoteException{
      return theDB.criteriaFind(criteria);
   }

   /**
   * This method removes the given record from the database.
   *
   * @param DataInfo toDelete The record to be removed.
   *
   * @exception RemoteException Thrown if rmi service fails.
   * @exception DatabaseException Thrown if the delete method fails.
   */
   public void delete(DataInfo toDelete) throws java.rmi.RemoteException,DatabaseException {
      theDB.delete(toDelete);
   }

   /**
   * This method is used to retrieve a specific record from the database based
   * on the key field which is passed in.
   *
   * @param String toMatch The key value to be matched.
   *
   * @return DataInfo The record matching the key value.
   *
   * @exception RemoteException Thrown if rmi service fails.
   * @exception DatabaseException Thrown if find method fails.
   */
   public DataInfo find(String toMatch) throws java.rmi.RemoteException,DatabaseException {
      DataInfo retVal;

      retVal = theDB.find(toMatch);

      return retVal;
   }

   /**
   * This method returns the fields that make up the database.
   *
   * @return FieldInfo[] An array of the field info for this database.
   *
   * @exception RemoteException Thrown if rmi service fails.
   */
   public FieldInfo[] getFieldInfo() throws java.rmi.RemoteException {
      return theDB.getFieldInfo();
   }

   /**
   * This method is used to retrieve a record given a row number in the
   * database.
   *
   * @param int recNum The record number of the record to retrieve.
   *
   * @return DataInfo The record requested.
   *
   * @exception RemoteException Thrown if rmi service fails.
   * @exception DatabaseException Thrown if getRecord fails.
   */
   public DataInfo getRecord(int recNum) throws java.rmi.RemoteException,DatabaseException {
      DataInfo retVal;

      retVal = theDB.getRecord(recNum);

      return retVal;
   }

   /**
   * This method retrieves the number of records in the database.
   *
   * @return int Number of records in database.
   *
   * @exception RemoteException Thrown if rmi service fails.
   */
   public int getRecordCount() throws java.rmi.RemoteException {
      return theDB.getRecordCount();
   }

   /**
   * This method locks a specified record in the database.
   *
   * @param int record The record to lock.
   *
   * @exception IOException Thrown if record lock fails.
   * @exception RemoteException Thrown if rmi service fails.
   */
   public void lock(int record) throws java.rmi.RemoteException,IOException {
      theDB.lock(record);
   }

   /**
   * This method is used to update information for a specific record.
   *
   * @param DataInfo newData The new record information to use.
   *
   * @exception RemoteException Thrown if rmi service fails.
   * @exception DatabaseException Thrown if rmi service fails.
   */
   public void modify(DataInfo newData) throws java.rmi.RemoteException,DatabaseException {
      theDB.modify(newData);
   }

   /**
   * This method is used to unlock a record in the database.
   *
   * @param int record The record to be unlocked.
   *
   * @exception RemoteException Thrown if rmi service fails.
   */
   public void unlock(int record) throws java.rmi.RemoteException{
      theDB.unlock(record);
   }
}
