package com.sns.Apps.Referee.rmi;

import com.sns.Apps.Referee.db.*;
import java.io.IOException;
import java.rmi.*;

/**
 * Used to attach to a remote version of the Data
 * database class.  All of these methods are described in the Data
 * class.
 *
 * @version 1.0  08-Jul-2000
 * @author Rob Broadhead
 * @see com.sns.Apps.Referee.db.Data
 */
public interface dataRemote extends Remote {
   /**
   * Call this to open an existing database.
   *
   * @param String dbName The name of the database file to open.
   * @exception IOException Thrown if an error occurs while creating the database file.
   * @exception RemoteException Thrown if the RMI system fails.
   */
   public void openDB(String dbName) throws java.rmi.RemoteException,IOException;

   /**
   * Call this to create and open a new database.
   *
   * @param String dbName The name of the database file to open.
   * @param FieldInfo[] fields The field names and sizes to be used in the database.
   * @exception RemoteException Thrown if the RMI system fails.
   * @exception IOException Thrown if an error occurs while creating the database file.
   */
   public void createDB(String dbName,FieldInfo[] fields) throws java.rmi.RemoteException,IOException;

   /**
   * Adds a record to the database.
   *
   * @param String[] theData The values that make up the record to be added.
   * @exception RemoteException Thrown if the RMI system fails.
   * @exception DatabaseException Thrown if an error occurs accessing the database.
   */
   public void add(String[] theData) throws java.rmi.RemoteException,DatabaseException;

   /**
   * Closes the database and flushes any current writes.
   *
   * @exception RemoteException Thrown if the RMI system fails.
   */
   public void close() throws java.rmi.RemoteException;

   /**
   * Returns a group of records matching search criteria.
   * The search string can include multiple field-value combinations.
   *
   * @param String criteria The search string we'll use to search for records
   * @see com.sns.Apps.Referee.db.Data#criteriaFind
   * @return DataInfo[] The list of records that match the search string.
   * @exception RemoteException Thrown if the RMI system fails.
   */
   public DataInfo[] criteriaFind(String criteria) throws java.rmi.RemoteException;

   /**
   * Deletes a record from the database.
   *
   * @param DataInfo toDelete The record to be deleted.
   * @exception RemoteException Thrown if the RMI system fails.
   * @exception DatabaseException Thrown if an error occurs accessing the database.
   */
   public void delete(DataInfo toDelete) throws java.rmi.RemoteException,DatabaseException;

   /**
   * Searches for a record via the key value, which in this
   * case will always be the first field in the database.
   *
   * @param String toMatch The key value to look for in the database.
   * @return DataInfo The record that matches the search string or a null.
   * @exception RemoteException Thrown if the RMI system fails.
   * @exception DatabaseException Thrown if an error occurs accessing the database.
   */
   public DataInfo find(String toMatch) throws java.rmi.RemoteException,DatabaseException;

   /**
   * Returns field information (schema) from a database.
   *
   * @return FieldInfo[] An array of name and size for each of the fields.
   * @exception RemoteException Thrown if the RMI system fails.
   */
   public FieldInfo[] getFieldInfo() throws java.rmi.RemoteException;

   /**
   * Retrieves a specific record from the database.
   *
   * @param int recNum The record number to retrieve (1 based)
   * @return DataInfo The record desired.
   * @exception DatabaseException Thrown if an error occurs accessing the database.
   * @exception RemoteException Thrown if the RMI system fails.
   */
   public DataInfo getRecord(int recNum) throws java.rmi.RemoteException,DatabaseException;

   /**
   * Returns the number of records currently in the database.
   *
   * @return int The number of records currently in the database.
   * @exception RemoteException Thrown if the RMI system fails.
   */
   public int getRecordCount() throws java.rmi.RemoteException;

   /**
   * Locks a given record in the database or all of them if a -1 is
   * sent for the parameter.  This does not time out, but keeps trying to lock
   * the record until it is successful.
   *
   * @param int record The record number to lock.
   * @exception RemoteException Thrown if the RMI system fails.
   */
   public void lock(int record) throws java.rmi.RemoteException,IOException;

   /**
   * Updates a record in the database.  The record must exist.
   *
   * @param DataInfo newData The updated version of the record.
   * @exception RemoteException Thrown if the RMI system fails.
   * @exception DatabaseException Thrown if the modify method fails on the database.
   */
   public void modify(DataInfo newData) throws java.rmi.RemoteException,DatabaseException;

   /**
   * Unlock the given record in the database.
   *
   * @param int record The record number to unlock (1 based)
   * @exception RemoteException Thrown if the RMI system fails.
   */
   public void unlock(int record) throws java.rmi.RemoteException;
}













