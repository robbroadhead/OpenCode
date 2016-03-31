package com.sns.scout.managers.map;

import java.util.Date;
import java.math.BigDecimal;

import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import org.apache.torque.map.MapBuilder;
import org.apache.torque.map.DatabaseMap;
import org.apache.torque.map.TableMap;

/**
  *  This class was autogenerated by Torque on:
  *
  * [Tue Jan 19 16:39:36 CST 2010]
  *
  */
public class TaskMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "com.sns.scout.managers.map.TaskMapBuilder";


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
        dbMap = Torque.getDatabaseMap("scout");

        dbMap.addTable("Task");
        TableMap tMap = dbMap.getTable("Task");

                tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);
        
                tMap.setPrimaryKeyMethodInfo(tMap.getName());
        
                                      tMap.addPrimaryKey("Task.TASKID", new Integer(0));
                                                        tMap.addForeignKey(
                "Task.PROJECTID", new Integer(0) , "Project" ,
                    "projectID");
                                                        tMap.addColumn("Task.CREATED", new Date());
                                                        tMap.addForeignKey(
                "Task.STATUSID", new Integer(0) , "lkpStatus" ,
                    "statusID");
                                                        tMap.addForeignKey(
                "Task.TYPEID", new Integer(0) , "lkpType" ,
                    "typeID");
                                                        tMap.addColumn("Task.SUMMARY", new String());
                                                        tMap.addColumn("Task.DETAIL", new String());
                                                        tMap.addForeignKey(
                "Task.OWNERID", new Integer(0) , "Users" ,
                    "userID");
                                                        tMap.addForeignKey(
                "Task.PRIORITYID", new Integer(0) , "lkpPriority" ,
                    "priorityID");
                                                        tMap.addColumn("Task.STATUSDT", new Date());
                                                        tMap.addForeignKey(
                "Task.ASSIGNED", new Integer(0) , "Users" ,
                    "userID");
                              }
}
