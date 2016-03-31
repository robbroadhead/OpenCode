package com.sns.scout.managers;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.torque.TorqueException;
import org.apache.torque.om.BaseObject;
import org.apache.torque.om.ComboKey;
import org.apache.torque.om.DateKey;
import org.apache.torque.om.NumberKey;
import org.apache.torque.om.ObjectKey;
import org.apache.torque.om.SimpleKey;
import org.apache.torque.om.StringKey;
import org.apache.torque.om.Persistent;
import org.apache.torque.util.Criteria;
import org.apache.torque.util.Transaction;

   
      
   
/**
 * This class was autogenerated by Torque on:
 *
 * [Tue Jan 19 16:39:36 CST 2010]
 *
 * You should not use this class directly.  It should not even be
 * extended all references should be to Grouptask
 */
public abstract class BaseGrouptask extends BaseObject
{
    /** The Peer class */
    private static final GrouptaskPeer peer =
        new GrouptaskPeer();

                  
        /**
         * The value for the groupid field
         */
        private int groupid;
              
        /**
         * The value for the taskid field
         */
        private int taskid;
      
      
        /**
         * Get the Groupid
         *
         * @return int
         */
        public int getGroupid()
        {
            return groupid;
        }

                                                      
        /**
         * Set the value of Groupid
         *
         * @param v new value
         */
        public void setGroupid(int v) throws TorqueException
        {
          


         if (this.groupid != v)
        {
             this.groupid = v;
            setModified(true);
        }

                                          
                if (aGroups != null && !(aGroups.getGroupid()==v))
                {
            aGroups = null;
        }
          
                       }


        /**
         * Get the Taskid
         *
         * @return int
         */
        public int getTaskid()
        {
            return taskid;
        }

                                                      
        /**
         * Set the value of Taskid
         *
         * @param v new value
         */
        public void setTaskid(int v) throws TorqueException
        {
          


         if (this.taskid != v)
        {
             this.taskid = v;
            setModified(true);
        }

                                          
                if (aTask != null && !(aTask.getTaskid()==v))
                {
            aTask = null;
        }
          
                       }


 
     
   
             
   
       private Groups aGroups;

    /**
     * Declares an association between this object and a Groups object
     *
     * @param v Groups
     * @throws TorqueException
     */
    public void setGroups(Groups v) throws TorqueException
    {
           if (v == null)
        {
                        setGroupid(0);
                    }
        else
        {
            setGroupid(v.getGroupid());
        }
           aGroups = v;
    }

                 
    /**
     * Get the associated Groups object
     *
     * @return the associated Groups object
     * @throws TorqueException
     */
    public Groups getGroups() throws TorqueException
    {
        if (aGroups == null && (this.groupid > 0))
        {
              aGroups = GroupsPeer.retrieveByPK(SimpleKey.keyFor(this.groupid));
      
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               Groups obj = GroupsPeer.retrieveByPK(this.groupid);
               obj.addGrouptasks(this);
             */
        }
        return aGroups;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
     */
    public void setGroupsKey(ObjectKey key) throws TorqueException
    {
    
                                        setGroupid(((NumberKey) key).intValue());
                    }
 
   
             
   
       private Task aTask;

    /**
     * Declares an association between this object and a Task object
     *
     * @param v Task
     * @throws TorqueException
     */
    public void setTask(Task v) throws TorqueException
    {
           if (v == null)
        {
                        setTaskid(0);
                    }
        else
        {
            setTaskid(v.getTaskid());
        }
           aTask = v;
    }

                 
    /**
     * Get the associated Task object
     *
     * @return the associated Task object
     * @throws TorqueException
     */
    public Task getTask() throws TorqueException
    {
        if (aTask == null && (this.taskid > 0))
        {
              aTask = TaskPeer.retrieveByPK(SimpleKey.keyFor(this.taskid));
      
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               Task obj = TaskPeer.retrieveByPK(this.taskid);
               obj.addGrouptasks(this);
             */
        }
        return aTask;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
     */
    public void setTaskKey(ObjectKey key) throws TorqueException
    {
    
                                        setTaskid(((NumberKey) key).intValue());
                    }
    
        
    
    private static List fieldNames = null;

    /**
     * Generate a list of field names.
     *
     * @return a list of field names
     */
    public static synchronized List getFieldNames()
    {
      if (fieldNames == null)
      {
        fieldNames = new ArrayList();
            fieldNames.add("Groupid");
            fieldNames.add("Taskid");
            fieldNames = Collections.unmodifiableList(fieldNames);
      }
      return fieldNames;
    }

    /**
     * Retrieves a field from the object by name passed in as a String.
     *
     * @param name field name
     * @return value
     */
    public Object getByName(String name)
    {
            if (name.equals("Groupid"))
    {
              return new Integer(getGroupid());
          }
            if (name.equals("Taskid"))
    {
              return new Integer(getTaskid());
          }
            return null;
    }
    /**
     * Retrieves a field from the object by name passed in
     * as a String.  The String must be one of the static
     * Strings defined in this Class' Peer.
     *
     * @param name peer name
     * @return value
     */
    public Object getByPeerName(String name)
    {
            if (name.equals(GrouptaskPeer.GROUPID))
        {
              return new Integer(getGroupid());
          }
            if (name.equals(GrouptaskPeer.TASKID))
        {
              return new Integer(getTaskid());
          }
            return null;
    }

    /**
     * Retrieves a field from the object by Position as specified
     * in the xml schema.  Zero-based.
     *
     * @param pos position in xml schema
     * @return value
     */
    public Object getByPosition(int pos)
    {
            if (pos == 0)
    {
              return new Integer(getGroupid());
          }
            if (pos == 1)
    {
              return new Integer(getTaskid());
          }
                return null;
    }

     


    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
             save(GrouptaskPeer.getMapBuilder()
                .getDatabaseMap().getName());
     }

    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     * Note: this code is here because the method body is
     * auto-generated conditionally and therefore needs to be
     * in this file instead of in the super class, BaseObject.
     *
     * @param dbName
     * @throws TorqueException
     */
    public void save(String dbName) throws TorqueException
    {
        Connection con = null;
         try
        {
            con = Transaction.begin(dbName);
            save(con);
            Transaction.commit(con);
        }
        catch(TorqueException e)
        {
            Transaction.safeRollback(con);
            throw e;
        }

     }

      /** flag to prevent endless save loop, if this object is referenced
        by another object which falls in this transaction. */
    private boolean alreadyInSave = false;
      /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.  This method
     * is meant to be used as part of a transaction, otherwise use
     * the save() method and the connection details will be handled
     * internally
     *
     * @param con
     * @throws TorqueException
     */
    public void save(Connection con) throws TorqueException
    {
        if (!alreadyInSave)
      {
        alreadyInSave = true;



  
        // If this object has been modified, then save it to the database.
        if (isModified())
        {
            if (isNew())
            {
                GrouptaskPeer.doInsert((Grouptask) this, con);
                setNew(false);
            }
            else
            {
                GrouptaskPeer.doUpdate((Grouptask) this, con);
            }
        }

              alreadyInSave = false;
      }
      }




    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
        return null;
    }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
     * It then fills all the association collections and sets the
     * related objects to isNew=true.
     */
    public Grouptask copy() throws TorqueException
    {
        return copyInto(new Grouptask());
    }

    protected Grouptask copyInto(Grouptask copyObj) throws TorqueException
    {
        copyObj.setGroupid(groupid);
        copyObj.setTaskid(taskid);

        

  
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public GrouptaskPeer getPeer()
    {
        return peer;
    }
}