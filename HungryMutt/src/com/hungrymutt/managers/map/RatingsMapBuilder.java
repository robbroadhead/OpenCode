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
public class RatingsMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "com.hungrymutt.managers.map.RatingsMapBuilder";


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

        dbMap.addTable("Ratings");
        TableMap tMap = dbMap.getTable("Ratings");

                tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);
        
                tMap.setPrimaryKeyMethodInfo(tMap.getName());
        
                                      tMap.addPrimaryKey("Ratings.RATINGID", new Integer(0));
                                                        tMap.addForeignKey(
                "Ratings.RECIPEID", new Integer(0) , "Recipes" ,
                    "RecipeID");
                                                        tMap.addColumn("Ratings.RATING", new Integer(0));
                                                        tMap.addColumn("Ratings.IPADDRESS", new String());
                                                        tMap.addColumn("Ratings.DATERATED", new Date());
                              }
}
