package com.sns.Apps.Referee.db;

import java.io.*;
import java.util.*;
import java.text.*;
import com.sns.Util.Tracer;

/**
 * This class provides the basic database services. It uses two
 * other support classes: DataInfo and FieldInfo.
 *
 * @version 1.2  27-Mar-2001
 */
public class Data {
	/** Constant value to mark a live record */
	private static final byte LIVE_RECORD = 0;
	/** Constant value to mark a deleted record */
	private static final byte DELETED_RECORD = 1;
	/** Constant value to mark a locked record */
	private static final byte LOCKED_RECORD = 2;
	/** Constant value to mark a database created by this class. */
	private static final int MAGIC = 0xC0C0BABE;

	private static final String UNEXPECTED = "Data: Unexpected database access problem";

	/** Definition of the database we are currently using. */
	private FieldInfo[] description;
	/** Length of the header information for the database */
	private int headerLen;
	/** Length of a record in the database. */
	private int recordLen = 1;
	/** Number of records in the database. */
	private int recordCount;
	/** The database itself. */
	private RandomAccessFile db;
	/** Variable in the original code.  I assume this is used to differentiate
	* provided classes from those created to complete the assignment. */
	final static char sc = 'A';

	/**
	* We need a no-args constructor so descending classes can have their
	* own constructors.
	*/
	public Data() {
	}

	/**
	* Opens an existing database given the name
	* of the disk file containing it.
	*
	* @param String dbname The name of the database file to open.
	* @exception java.io.IOException
	*/
	public Data(String dbname) throws IOException {
		File f = new File(dbname);
		if (f.exists() && f.canRead() && f.canWrite()) {
			db = new RandomAccessFile(f, "rw");
			headerLen = db.readInt();
			int nFields = db.readInt();
			recordCount = db.readInt();
			description = new FieldInfo[nFields];
			for (int i = 0; i < nFields; i++) {
				description[i] = new FieldInfo(db.readUTF(), db.readInt());
				recordLen += description[i].getLength();
			}

			if (db.readInt() != MAGIC) {
				throw new IOException(
					"Data: corrupted database file.  " + "Magic not found in file " + dbname);
			}

			if (db.getFilePointer() != headerLen) {
				throw new IOException(
					"Data: corrupted database file.  " + "Header length incorrect in file " + dbname);
			}
		} else {
			throw new IOException(
				"Data: request to open non-existant or " + "inaccessible file" + dbname);
		}
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
	public Data(String dbname, FieldInfo[] fields) throws IOException {
		File f = new File(dbname);
		if (!f.exists()) {
			db = new RandomAccessFile(f, "rw");
			db.writeInt(LIVE_RECORD); // filler for header length
			db.writeInt(fields.length);
			recordCount = 0;
			db.writeInt(recordCount);

			for (int i = 0; i < fields.length; i++) {
				db.writeUTF(fields[i].getName());
				db.writeInt(fields[i].getLength());
				recordLen += fields[i].getLength();
			}
			description = fields;
			db.writeInt(MAGIC);
			headerLen = (int) db.getFilePointer();
			db.seek(0);
			db.writeInt(headerLen);
		} else {
			throw new IOException("Data: request to create pre-existing " + "file" + dbname);
		}
	}

	/**
	* Returns a description of the database schema, as an
	* array of FieldInfo objects.
	*
	* @return FieldInfo[] The array of FieldInfo objects that comprise
	*          the schema to this database.
	*/
	public FieldInfo[] getFieldInfo() {
		return description;
	}

	/**
	* Gets the number of records stored in the database.
	* @return int The number of records.
	*/
	public int getRecordCount() {
		return recordCount;
	}

	/**
	* Gets a requested record from the database based on record number.
	* @param recNum The number of the record to read (first record is 1).
	* @return DataInfo for the record or null if the record has been marked for
	*    deletion.
	* @exception DatabaseException Thrown if database file cannot be accessed.
	*/
	public synchronized DataInfo getRecord(int recNum) throws DatabaseException {
		try {
			if (recNum < 1) {
				throw new DatabaseException("Record number must be greater than 1");
			}

			seek(recNum);

			String records[] = readRecord();
			if (records == null) {
				return null;
			}

			return new DataInfo(recNum, description, records);
		} catch (IOException ex) {
			throw new DatabaseException(UNEXPECTED);
		}
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
		DataInfo curRec = null;
		Vector found = new Vector();
		int numRecs;
		boolean match;
		/* check each record for a match*/
		numRecs = getRecordCount() + 1;
		for (int i = 1; i < numRecs; i++) {
			try {
				curRec = getRecord(i);
			} catch (DatabaseException e) {
				throw new RuntimeException("Internal Error in criteriaFind()");
			}

			if (curRec != null) {
				/* parse the criteria string */
				StringTokenizer critList;
				critList = new StringTokenizer(criteria, ",");

				match = true;
				while (critList.hasMoreTokens() && (match == true)) {
					match = testMatch(curRec, critList.nextToken());
				}

				if (match == true) {
					found.add(curRec);
				}
			}
		}

		/* Build the array */
		retVal = new DataInfo[found.size()];
		found.toArray(retVal);

		return retVal;
	} /* End of criteriaFind(String) */

	/**
	* Checks values in a record to see if the match an expression sent
	* in as the other parameter.  The expression includes a field name which is the
	* field whose value is checked.  If the field name does not exist this method
	* will return false.
	*
	* @param DataInfo rec The record we are testing to see if the expression is true.
	* @param String exp The expression we are testing against the record
	* @return boolean This is true if the expression matches the data in the record.
	*/
	private boolean testMatch(DataInfo rec, String exp) {
		boolean retVal = false;
		FieldInfo[] flds;
		String fld, val, temp; /* For storing the parts of the expression */
		int curPos, idx, type;

		/* parse the string */
		curPos = exp.indexOf("=");
		type = 0;
		/* Check for greater than */
		if (curPos < 0) {
			type = 1;
			curPos = exp.indexOf(">");
		}

		/* Check for less than */
		if (curPos < 0) {
			type = 2;
			curPos = exp.indexOf("<");
		}

		fld = exp.substring(0, curPos);
		curPos = exp.indexOf("'") + 1;
		val = exp.substring(curPos, (exp.length() - 1));

		/* Find the field name */
		flds = rec.getFields();
		idx = 0;
		while ((idx < flds.length) && (!flds[idx].getName().equals(fld))) {
			idx++;
		}

		if (idx == flds.length) {
			return retVal;
		}

		/* Test for the match */
		temp = rec.getValues()[idx];
		switch (type) {
			case 0 :
				if ((temp.trim().equals(val.trim())) | (val.trim().toUpperCase().equals("ANY"))) {
					retVal = true;
				}
				break;
			case 1 :
				if (val.trim().toUpperCase().equals("ANY")) {
					retVal = true;
				} else {
					DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
					try {
						Date tempDate = df.parse(val.trim());
						Date compDate = df.parse(temp.trim());

						if (tempDate.compareTo(compDate) <= 0) {
							retVal = true;
						}
					} catch (Exception e) {
						Tracer.log("Date Format error:" + val.trim() + " or " + temp.trim());
						retVal = false;
					}
				}
				break;
			case 2 :
				if (val.trim().toUpperCase().equals("ANY")) {
					retVal = true;
				} else {
					DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
					try {
						Date tempDate = df.parse(val.trim());
						Date compDate = df.parse(temp.trim());
						if (tempDate.compareTo(compDate) >= 0) {
							retVal = true;
						}
					} catch (Exception e) {
						Tracer.log("Date Format error:" + val.trim() + " or " + temp.trim());
						retVal = false;
					}
				}
				break;
		}

		return retVal;
	} /* End of testMatch(DataInfo,String) */

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
		invariant();
		try {
			seek(1);

			DataInfo rv = null;
			String[] values = null;
			boolean found = false;
			int r;

			for (r = 1; r <= recordCount; r++) {
				values = readRecord();

				if ((values != null) && (values[0].equals(toMatch))) {
					found = true;
					break;
				}
			}

			if (found) {
				rv = new DataInfo(r, description, values);
			}
			return rv;
		} catch (IOException e) {
			throw new DatabaseException(UNEXPECTED + e);
		}
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
	public synchronized void add(String[] newData) throws DatabaseException {
		invariant();
		if (find(newData[0]) != null) {
			throw new DatabaseException("Attempt to add a duplicate key");
		}

		try {
			seek(++recordCount);
			writeRecord(newData);
			db.seek(8);
			db.writeInt(recordCount);
		} catch (IOException e) {
			throw new DatabaseException(UNEXPECTED + e);
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
		invariant();
		DataInfo test = find((newData.getValues())[0]);
		if ((test != null) && (test.getRecordNumber() != newData.getRecordNumber())) {
			throw new DatabaseException("Attempt to create a " + "duplicate key by modification");
		}

		try {
			seek(newData.getRecordNumber());
			writeRecord(newData.getValues());
		} catch (IOException e) {
			throw new DatabaseException(UNEXPECTED + e);
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
		invariant();
		try {
			seek(toDelete.getRecordNumber());
			db.write(DELETED_RECORD);
		} catch (IOException e) {
			throw new DatabaseException(UNEXPECTED + e);
		}
	}

	/**
	* Closes the database, flushing any outstanding
	* writes at the same time. Any attempt to access the
	* database after this results in a IOException.
	*/
	public synchronized void close() {
		try {
			db.close();
		} catch (IOException e) {
			throw new RuntimeException("Error Closing Database File!");
		}

		db = null;
	}

	/** We need to make sure we close the database when the class is destroyed. */
	protected void finalize() {
		if (db != null) {
			close();
		}
	}

	/**
	* Reads a record from the current cursor position of the underlying random
	* access file.
	* @return The array of strings that make up a database record.
	* @exception IOException Generated if the RandomAccessFile cannot read from
	*        the database file.
	*/
	private synchronized String[] readRecord() throws IOException {
		int offset = 1;
		String[] rv = null;
		byte[] buffer = new byte[recordLen];

		db.read(buffer);
		if ((buffer[0] == LIVE_RECORD) || (buffer[0] == LOCKED_RECORD)) {
			rv = new String[description.length];
			for (int i = 0; i < description.length; i++) {
				rv[i] = new String(buffer, offset, description[i].getLength());
				offset += description[i].getLength();
			}
		}
		return rv;
	}

	/**
	* Writes a new record to the database using the current location of the
	* underlying random access file.
	* @param newData An array of strings in the database specified order.
	* @exception IOException Generated if the RandomAccessFile cannot write to
	*        the file or the wrong number of fields are specified.
	*/
	private void writeRecord(String[] newData) throws IOException {
		if (newData.length != description.length) {
			throw new IOException(
				"Data: Wrong number of fields in writeRecord() "
					+ newData.length
					+ " given, "
					+ description.length
					+ " required");
		}

		int size, space, toCopy, recCount;
		byte[] buffer = new byte[recordLen];
		byte marker;
		long pos;

		/* Make sure the record isn't locked */
		pos = db.getFilePointer();
		recCount = getRecordCount();
		if ((recCount > 1) && (pos != headerLen + (recordLen * (recCount - 1)))) {
			marker = db.readByte();
			db.seek(pos);
			if (marker == LOCKED_RECORD) {
				throw new IOException("Lock: Attempted to write to a locked record");
			}
		}

		marker = LIVE_RECORD;
		db.write(marker);
		int offset = 1;

		for (int i = 0; i < description.length; i++) {
			space = description[i].getLength();

			/* This loop is to make sure each field is padded out to its full */
			/* size.  This is needed to keep the length calculations correct. */
			while (newData[i].length() < space) {
				newData[i] = newData[i] + " ";
			}
			size = newData[i].length();
			toCopy = (size <= space) ? size : space;

			buffer = newData[i].getBytes();
			offset += toCopy;
			db.write(buffer);
		}

	}

	/**
	* Moves the current database record pointer to the specified record.
	* @param recno The record number to position the cursor.
	* @exception IOException If the record position is invalid.
	*/
	private synchronized void seek(int recno) throws IOException {
		db.seek(headerLen + (recordLen * (recno - 1)));
	}

	/**
	* Lock the requested record. If the argument is -1, lock the whole
	* database. This method blocks until the lock succeeds. No timeouts
	* are defined for this.
	* @param recno The record number to lock.
	* @exception IOException If the record position is invalid.
	*/
	public void lock(int record) throws IOException {
		boolean success;
		byte marker;
		long pos;
		int recCount;

		success = false;

		while (success == false) {
			if (record == -1) {
				int count, recCnt;
				recCnt = getRecordCount();
				for (count = 0; count < recCnt; count++) {
					lock(count + 1);
					success = true;
				}
			} else {
				db.seek(headerLen + (recordLen * (record - 1)));
				try {
					/* Make sure the record isn't locked */
					synchronized (db) {
						pos = db.getFilePointer();
						recCount = getRecordCount();
						if ((recCount > 1) && (pos <= headerLen + (recordLen * (recCount - 1)))) {
							marker = db.readByte();
							db.seek(pos);

							if (marker != LOCKED_RECORD) {
								db.writeByte(LOCKED_RECORD);
								success = true;
							}
						}
					}
				} catch (Exception e) {
				}
			}
			Thread.yield();
		}

		if (success == false) {
			Thread.yield();
			lock(record);
			return;
		}
	}

	/**
	* Unlock the requested record. Ignored if the caller does not have
	* a current lock on the requested record.
	*
	* @param int record The record to be unlocked.
	*/
	public void unlock(int record) {
		try {
			db.seek(headerLen + (recordLen * (record - 1)));
			db.writeByte(LIVE_RECORD);
		} catch (IOException e) {
			throw new RuntimeException("Attempted to unlock invalid record");
		}
	}

	/**
	* Ensures that the database structure is valid.
	* @exception RuntimeException If structure has become corrupted.
	*/
	protected final synchronized void invariant() {
		boolean ok = false;
		try {
			if (db.length() == headerLen + (recordLen * recordCount)) {
				ok = true;
			} else {
				if ((recordCount == 0) && (db.length() == headerLen)) {
					ok = true;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(UNEXPECTED + e);
		}

		if (!ok) {
			throw new RuntimeException("Data: Internal error");
		}
	}
}
