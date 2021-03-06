package com.hungrymutt.managers.map;

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
  * [Sun Dec 12 11:27:33 CST 2004]
  *
  */
public class RecipesMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "com.hungrymutt.managers.map.RecipesMapBuilder";


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
        dbMap = Torque.getDatabaseMap("hungrymutt");

        dbMap.addTable("Recipes");
        TableMap tMap = dbMap.getTable("Recipes");

                tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);
        
                tMap.setPrimaryKeyMethodInfo(tMap.getName());
        
                                      tMap.addPrimaryKey("Recipes.RECIPEID", new Integer(0));
                                                        tMap.addColumn("Recipes.TITLE", new String());
                                                        tMap.addColumn("Recipes.SUBMITTEDBY", new String());
                                                        tMap.addColumn("Recipes.RECIPEBODY", new Object());
                                                        tMap.addColumn("Recipes.RECIPECATEGORY", new String());
                                                        tMap.addColumn("Recipes.VISIBLE", new Boolean(true));
                                                        tMap.addColumn("Recipes.DATESUBMITTED", new Date());
                              }
}
