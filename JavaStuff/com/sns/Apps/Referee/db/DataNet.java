package com.sns.Apps.Referee.db;

import java.io.*;
import com.sns.Apps.Referee.rmi.*;
import java.rmi.*;

/**
 * Provides the basic database services. It uses two
 * other support classes: DataInfo and FieldInfo. It
 * is nearly identical to the Data class.
 *
 * @version 1.1  17-Nov-1997
 */
public class DataNet extends Data {
  /** The interface into the data server. */
  private dataRemote netDB;
  /** Address of the server to attach to. */
  private String serverAddr;

  /**
  * Opens an existing database given the name of the disk file containing it.
  *
  * @param String dbname The name of the database file to open.
  * @exception java.io.IOException
  */
  public DataNet(String dbname,String addr) throws IOException {
     serverAddr = addr;
     connectServer();
     netDB.openDB(dbname);
  }

  /**
  * Creates a new database file, using the name
  * provided for the disk file and using the FieldInfo array to
  * describe the field names and sizes that should be created.
  *
  * @param String dbname The name of the database file to open.
  * @param FieldInfo[] fields The list of fields for the schema of
  *          this database.
  * @exception IOException Thrown if cannot create database file.
  */
  public DataNet(String dbname, FieldInfo[] fields, String addr) throws IOException {
     serverAddr = addr;
     connectServer();
     netDB.createDB(dbname,fields);
  }

  /**
  * Builds the connection to the database rmi server.
  */
  public void connectServer() {
     try {
        netDB = (dataRemote) Naming.lookup("rmi://" + serverAddr + "/dbsrv");
     } catch (Exception e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }
  }

  /**
  * Returns a description of the database schema, as an
  * array of FieldInfo objects.
  *
  * @return FieldInfo[] The array of FieldInfo objects that comprise
  *          the schema to this database.
  */
  public FieldInfo [] getFieldInfo() {
     FieldInfo []retVal;

     retVal = null;

     try {
        retVal = netDB.getFieldInfo();
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }

     return retVal;
  }

  /**
  * Gets the number of records stored in the database.
  * @return int The number of records.
  */
  public int getRecordCount() {
     int retVal;
     retVal = 0;

     try {
        retVal = netDB.getRecordCount();
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }

     return retVal;
  }

  /**
  * Gets a requested record from the database based on record number.
  * @param recNum The number of the record to read (first record is 1).
  * @return DataInfo for the record or null if the record has been marked for
  *    deletion.
  * @exception DatabaseException Thrown if database file cannot be accessed.
  */
  public synchronized DataInfo getRecord(int recNum) throws DatabaseException {
     DataInfo retVal;
     retVal = null;

     try {
        retVal = netDB.getRecord(recNum);
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }

     return retVal;
  }

  /**
  * Searches the database for all records that match the values
  * given in the parameter string.  The parameter is a string consisting of
  * field names an '=' and a value in single quotes with each field name
  * and value pair being separated by commas (eg. "Carrier='SpeedyAir',Origin
  * ='SFO'").  The fields specified will be checked in each record to see if
  * they match the corresponding value.  All matches found will be returned
  * in the DataInfo array.
  *
  * @param criteria a string of comma separated field name-value pairs to
  *        search for records with the field matching the value.
  * @return DataInfo[] returns all records matching the criteria.
  */
  public DataInfo[] criteriaFind(String criteria) {
     DataInfo[] retVal;
     retVal = null;

     try {
        retVal = netDB.criteriaFind(criteria);
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }

     return retVal;
  } /* End of criteriaFind(String) */

  /**
  * Searches the database for an entry which has a first
  * field which exactly matches the string supplied. If the required
  * record cannot be found, this method returns null. For this
  * assignment, the key field is the record number field.
  *
  * @param toMatch The key field value to match upon for
  *           a successful find.
  * @return DataInfo The matching record.
  * @exception DatabaseException Thrown when database file could not be accessed.
  */
  public synchronized DataInfo find(String toMatch) throws DatabaseException {
     DataInfo retVal;
     retVal = null;

     try {
        retVal = netDB.find(toMatch);
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }

     return retVal;
  }

  /**
  * Adds a new record to the database. The array of
  * strings must have exactly the same number of elements as the
  * field count of the database schema, otherwise a RuntimeException
  * is issued. The first field, the key, must be unique in the
  * database or a RuntimeException is thrown.
  *
  * @param newData The elements of the record to add.
  * @exception DatabaseException Attempted to add a duplicate key or
  *        database file could not be accessed.
  */
  public synchronized void add(String [] newData) throws DatabaseException {
     try {
        netDB.add(newData);
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }
  }

  /**
  * Updates the record specified by the record number
  * field in the DataInfo argument. The fields are all modified
  * to reflect the values in that argument. If the key field
  * specified in the argument matches any record other than the
  * one indicated by the record number of the argument, then a
  * RuntimeException is thrown.
  *
  * @param newData The updated record to modify.
  * @exception DatabaseException Thrown if attempting to add a duplicate
  *       key.
  */
  public synchronized void modify(DataInfo newData) throws DatabaseException {
     try {
        netDB.modify(newData);
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }
  }

  /**
  * Deletes the record referred to by the record
  * number in the DataInfo argument.
  *
  * @param DataInfo newData The record to delete.
  * @exception DatabaseException Thrown if database cannot be accessed.
  */
  public synchronized void delete(DataInfo toDelete) throws DatabaseException {
     try {
        netDB.delete(toDelete);
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }
  }

  /**
  * Closes the database, flushing any outstanding
  * writes at the same time. Any attempt to access the
  * database after this results in a IOException.
  */
  public synchronized void close() {
     try {
        netDB.close();
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }
  }

  /**
  * Lock the requested record. If the argument is -1, lock the whole
  * database. This method blocks until the lock succeeds. No timeouts
  * are defined for this.
  * @param recno The record number to lock.
  * @exception IOException If the record position is invalid.
  */
  public void lock(int record) throws IOException {
     try {
        netDB.lock(record);
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }
  }

  /**
  * Unlock the requested record. Ignored if the caller does not have
  * a current lock on the requested record.
  */
  public void unlock(int record) {
     try {
        netDB.unlock(record);
     } catch (RemoteException e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
     }
  }

}
