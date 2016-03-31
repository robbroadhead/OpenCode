package com.sns.Apps.Referee.db;

import java.io.*;

/**
 * This is a controller for all database files to be used by an application.
 * This will attempt to open each and be the access point for any other classes
 * that need to use any of these data files.
 *
 * @version 1.0  20-May-2001
 */
public class DataManager implements Serializable {
  /** The game data file */
  public static Data gameDB;
  /** The referee data file */
  public static Data refDB;
  /** The structure of the game DB.  This is stored to speed up work with the DB.
  * @serial theFields */
  public static FieldInfo[] gameFields;
  /** The structure of the ref DB.  This is stored to speed up work with the DB.
  * @serial theFields */
  public static FieldInfo[] refFields;

  /**
   */
  public DataManager() {
  }

  /**
  * Initialize storage variables to speed up data access.
  */
  public void Initialize() {
     gameFields = gameDB.getFieldInfo();
     refFields = refDB.getFieldInfo();
  }
}
