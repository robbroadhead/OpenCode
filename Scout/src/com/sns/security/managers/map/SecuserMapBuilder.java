package com.sns.security.managers.map;

import java.util.Date;


import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import org.apache.torque.map.MapBuilder;
import org.apache.torque.map.DatabaseMap;
import org.apache.torque.map.TableMap;

/**
  *  This class was autogenerated by Torque on:
  *
  * [Fri Mar 07 11:01:56 CST 2008]
  *
  */
public class SecuserMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "com.sns.security.managers.map.SecuserMapBuilder";


    /**
     * The database map.
     */
    private DatabaseMap dbMap = null;

    /**
     * Tells us if this DatabaseMapBuilder is built so that we
     * don't have to re-build it every time.
     *
     * @return true if this DatabaseMapBuilder is built
     */
    public boolean isBuilt()
    {
        return (dbMap != null);
    }

    /**
     * Gets the databasemap this map builder built.
     *
     * @return the databasemap
     */
    public DatabaseMap getDatabaseMap()
    {
        return this.dbMap;
    }

    /**
     * The doBuild() method builds the DatabaseMap
     *
     * @throws TorqueException
     */
    public void doBuild() throws TorqueException
    {
        dbMap = Torque.getDatabaseMap("sns");

        dbMap.addTable("secUser");
        TableMap tMap = dbMap.getTable("secUser");

                tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);
        
                tMap.setPrimaryKeyMethodInfo(tMap.getName());
        
                                      tMap.addPrimaryKey("secUser.SECUSERID", new Integer(0));
                                                        tMap.addColumn("secUser.USERNAME", new String());
                                                        tMap.addColumn("secUser.PASSWORD", new String());
                                                        tMap.addColumn("secUser.ISENABLED", new Integer(0));
                                                        tMap.addColumn("secUser.LASTLOGIN", new Date());
                                                        tMap.addColumn("secUser.FAILEDCOUNT", new Integer(0));
                                                        tMap.addColumn("secUser.WHENEXPIRE", new Date());
                                                        tMap.addColumn("secUser.EXPIREDURATION", new Integer(0));
                                                        tMap.addColumn("secUser.CHANGEONLOGIN", new Integer(0));
                                                        tMap.addColumn("secUser.PREVPASSWORD", new String());
                                                        tMap.addColumn("secUser.MUST_CHANGE", new Integer(0));
                              }
}
