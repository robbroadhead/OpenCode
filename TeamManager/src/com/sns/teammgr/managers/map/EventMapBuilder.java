package com.sns.teammgr.managers.map;

import java.util.Date;


import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import org.apache.torque.map.MapBuilder;
import org.apache.torque.map.DatabaseMap;
import org.apache.torque.map.TableMap;

/**
  *  This class was autogenerated by Torque on:
  *
  * [Tue Feb 19 16:03:50 CST 2008]
  *
  */
public class EventMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "com.sns.teammgr.managers.map.EventMapBuilder";


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
        dbMap = Torque.getDatabaseMap("teammgr");

        dbMap.addTable("Event");
        TableMap tMap = dbMap.getTable("Event");

                tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);
        
                tMap.setPrimaryKeyMethodInfo(tMap.getName());
        
                                      tMap.addPrimaryKey("Event.EVENTID", new Integer(0));
                                                        tMap.addForeignKey(
                "Event.TEAMID", new Integer(0) , "Team" ,
                    "teamID");
                                                        tMap.addColumn("Event.TITLE", new String());
                                                        tMap.addColumn("Event.LINK", new String());
                                                        tMap.addColumn("Event.MSG", new String());
                                                        tMap.addColumn("Event.STARTDATE", new Date());
                                                        tMap.addColumn("Event.ENDDATE", new Date());
                              }
}