package com.sns.Apps.Referee.db;

/**
* Used to categorize exceptions related to the database
* functions that use Data.
* @see com.sns.Apps.Referee.db.Data
*/
public class DatabaseException extends Exception {
    /**
    * This is for creating a DatabaseException.
    */
    public DatabaseException() {}

    /**
    * Sets the message while creating a DatabaseException.
    * @param String msg The message you wish to set in the exception.
    */
    public DatabaseException(String msg) {
        super(msg);
    }
}
