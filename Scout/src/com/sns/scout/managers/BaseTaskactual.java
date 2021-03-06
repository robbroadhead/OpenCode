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
 * extended all references should be to Taskactual
 */
public abstract class BaseTaskactual extends BaseObject
{
    /** The Peer class */
    private static final TaskactualPeer peer =
        new TaskactualPeer();

                  
        /**
         * The value for the estimateid field
         */
        private int estimateid;
              
        /**
         * The value for the taskid field
         */
        private int taskid;
              
        /**
         * The value for the priorityid field
         */
        private int priorityid;
              
        /**
         * The value for the startdate field
         */
        private Date startdate;
              
        /**
         * The value for the enddate field
         */
        private Date enddate;
              
        /**
         * The value for the timevalue field
         */
        private int timevalue;
              
        /**
         * The value for the measureid field
         */
        private int measureid;
              
        /**
         * The value for the cost field
         */
        private int cost;
      
      
        /**
         * Get the Estimateid
         *
         * @return int
         */
        public int getEstimateid()
        {
            return estimateid;
        }

                                            
        /**
         * Set the value of Estimateid
         *
         * @param v new value
         */
        public void setEstimateid(int v) 
        {
          


         if (this.estimateid != v)
        {
             this.estimateid = v;
            setModified(true);
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


        /**
         * Get the Priorityid
         *
         * @return int
         */
        public int getPriorityid()
        {
            return priorityid;
        }

                                                      
        /**
         * Set the value of Priorityid
         *
         * @param v new value
         */
        public void setPriorityid(int v) throws TorqueException
        {
          


         if (this.priorityid != v)
        {
             this.priorityid = v;
            setModified(true);
        }

                                          
                if (aLkppriority != null && !(aLkppriority.getPriorityid()==v))
                {
            aLkppriority = null;
        }
          
                       }


        /**
         * Get the Startdate
         *
         * @return Date
         */
        public Date getStartdate()
        {
            return startdate;
        }

                                            
        /**
         * Set the value of Startdate
         *
         * @param v new value
         */
        public void setStartdate(Date v) 
        {
          


         if (!ObjectUtils.equals(this.startdate, v))
        {
             this.startdate = v;
            setModified(true);
        }

                  
                       }


        /**
         * Get the Enddate
         *
         * @return Date
         */
        public Date getEnddate()
        {
            return enddate;
        }

                                            
        /**
         * Set the value of Enddate
         *
         * @param v new value
         */
        public void setEnddate(Date v) 
        {
          


         if (!ObjectUtils.equals(this.enddate, v))
        {
             this.enddate = v;
            setModified(true);
        }

                  
                       }


        /**
         * Get the Timevalue
         *
         * @return int
         */
        public int getTimevalue()
        {
            return timevalue;
        }

                                            
        /**
         * Set the value of Timevalue
         *
         * @param v new value
         */
        public void setTimevalue(int v) 
        {
          


         if (this.timevalue != v)
        {
             this.timevalue = v;
            setModified(true);
        }

                  
                       }


        /**
         * Get the Measureid
         *
         * @return int
         */
        public int getMeasureid()
        {
            return measureid;
        }

                                                      
        /**
         * Set the value of Measureid
         *
         * @param v new value
         */
        public void setMeasureid(int v) throws TorqueException
        {
          


         if (this.measureid != v)
        {
             this.measureid = v;
            setModified(true);
        }

                                          
                if (aLkpmeasure != null && !(aLkpmeasure.getMeasureid()==v))
                {
            aLkpmeasure = null;
        }
          
                       }


        /**
         * Get the Cost
         *
         * @return int
         */
        public int getCost()
        {
            return cost;
        }

                                            
        /**
         * Set the value of Cost
         *
         * @param v new value
         */
        public void setCost(int v) 
        {
          


         if (this.cost != v)
        {
             this.cost = v;
            setModified(true);
        }

                  
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
               obj.addTaskactuals(this);
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
 
   
             
   
       private Lkppriority aLkppriority;

    /**
     * Declares an association between this object and a Lkppriority object
     *
     * @param v Lkppriority
     * @throws TorqueException
     */
    public void setLkppriority(Lkppriority v) throws TorqueException
    {
           if (v == null)
        {
                        setPriorityid(0);
                    }
        else
        {
            setPriorityid(v.getPriorityid());
        }
           aLkppriority = v;
    }

                 
    /**
     * Get the associated Lkppriority object
     *
     * @return the associated Lkppriority object
     * @throws TorqueException
     */
    public Lkppriority getLkppriority() throws TorqueException
    {
        if (aLkppriority == null && (this.priorityid > 0))
        {
              aLkppriority = LkppriorityPeer.retrieveByPK(SimpleKey.keyFor(this.priorityid));
      
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               Lkppriority obj = LkppriorityPeer.retrieveByPK(this.priorityid);
               obj.addTaskactuals(this);
             */
        }
        return aLkppriority;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
     */
    public void setLkppriorityKey(ObjectKey key) throws TorqueException
    {
    
                                        setPriorityid(((NumberKey) key).intValue());
                    }
 
   
             
   
       private Lkpmeasure aLkpmeasure;

    /**
     * Declares an association between this object and a Lkpmeasure object
     *
     * @param v Lkpmeasure
     * @throws TorqueException
     */
    public void setLkpmeasure(Lkpmeasure v) throws TorqueException
    {
           if (v == null)
        {
                        setMeasureid(0);
                    }
        else
        {
            setMeasureid(v.getMeasureid());
        }
           aLkpmeasure = v;
    }

                 
    /**
     * Get the associated Lkpmeasure object
     *
     * @return the associated Lkpmeasure object
     * @throws TorqueException
     */
    public Lkpmeasure getLkpmeasure() throws TorqueException
    {
        if (aLkpmeasure == null && (this.measureid > 0))
        {
              aLkpmeasure = LkpmeasurePeer.retrieveByPK(SimpleKey.keyFor(this.measureid));
      
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               Lkpmeasure obj = LkpmeasurePeer.retrieveByPK(this.measureid);
               obj.addTaskactuals(this);
             */
        }
        return aLkpmeasure;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
     */
    public void setLkpmeasureKey(ObjectKey key) throws TorqueException
    {
    
                                        setMeasureid(((NumberKey) key).intValue());
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
            fieldNames.add("Estimateid");
            fieldNames.add("Taskid");
            fieldNames.add("Priorityid");
            fieldNames.add("Startdate");
            fieldNames.add("Enddate");
            fieldNames.add("Timevalue");
            fieldNames.add("Measureid");
            fieldNames.add("Cost");
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
            if (name.equals("Estimateid"))
    {
              return new Integer(getEstimateid());
          }
            if (name.equals("Taskid"))
    {
              return new Integer(getTaskid());
          }
            if (name.equals("Priorityid"))
    {
              return new Integer(getPriorityid());
          }
            if (name.equals("Startdate"))
    {
              return getStartdate();
          }
            if (name.equals("Enddate"))
    {
              return getEnddate();
          }
            if (name.equals("Timevalue"))
    {
              return new Integer(getTimevalue());
          }
            if (name.equals("Measureid"))
    {
              return new Integer(getMeasureid());
          }
            if (name.equals("Cost"))
    {
              return new Integer(getCost());
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
            if (name.equals(TaskactualPeer.ESTIMATEID))
        {
              return new Integer(getEstimateid());
          }
            if (name.equals(TaskactualPeer.TASKID))
        {
              return new Integer(getTaskid());
          }
            if (name.equals(TaskactualPeer.PRIORITYID))
        {
              return new Integer(getPriorityid());
          }
            if (name.equals(TaskactualPeer.STARTDATE))
        {
              return getStartdate();
          }
            if (name.equals(TaskactualPeer.ENDDATE))
        {
              return getEnddate();
          }
            if (name.equals(TaskactualPeer.TIMEVALUE))
        {
              return new Integer(getTimevalue());
          }
            if (name.equals(TaskactualPeer.MEASUREID))
        {
              return new Integer(getMeasureid());
          }
            if (name.equals(TaskactualPeer.COST))
        {
              return new Integer(getCost());
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
              return new Integer(getEstimateid());
          }
            if (pos == 1)
    {
              return new Integer(getTaskid());
          }
            if (pos == 2)
    {
              return new Integer(getPriorityid());
          }
            if (pos == 3)
    {
              return getStartdate();
          }
            if (pos == 4)
    {
              return getEnddate();
          }
            if (pos == 5)
    {
              return new Integer(getTimevalue());
          }
            if (pos == 6)
    {
              return new Integer(getMeasureid());
          }
            if (pos == 7)
    {
              return new Integer(getCost());
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
             save(TaskactualPeer.getMapBuilder()
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
                TaskactualPeer.doInsert((Taskactual) this, con);
                setNew(false);
            }
            else
            {
                TaskactualPeer.doUpdate((Taskactual) this, con);
            }
        }

              alreadyInSave = false;
      }
      }


                
    
    

        /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  estimateid ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
                    setEstimateid(((NumberKey) key).intValue());
            }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
                    setEstimateid(Integer.parseInt(key));
            }


    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
        return SimpleKey.keyFor(getEstimateid());
    }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
     * It then fills all the association collections and sets the
     * related objects to isNew=true.
     */
    public Taskactual copy() throws TorqueException
    {
        return copyInto(new Taskactual());
    }

    protected Taskactual copyInto(Taskactual copyObj) throws TorqueException
    {
        copyObj.setEstimateid(estimateid);
        copyObj.setTaskid(taskid);
        copyObj.setPriorityid(priorityid);
        copyObj.setStartdate(startdate);
        copyObj.setEnddate(enddate);
        copyObj.setTimevalue(timevalue);
        copyObj.setMeasureid(measureid);
        copyObj.setCost(cost);

                      copyObj.setEstimateid(0);
                                        

  
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public TaskactualPeer getPeer()
    {
        return peer;
    }
}
