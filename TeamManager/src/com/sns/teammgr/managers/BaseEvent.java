package com.sns.teammgr.managers;



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
 * [Tue Feb 19 16:03:50 CST 2008]
 *
 * You should not use this class directly.  It should not even be
 * extended all references should be to Event
 */
public abstract class BaseEvent extends BaseObject
{
    /** The Peer class */
    private static final EventPeer peer =
        new EventPeer();

                  
        /**
         * The value for the eventid field
         */
        private int eventid;
              
        /**
         * The value for the teamid field
         */
        private int teamid;
              
        /**
         * The value for the title field
         */
        private String title;
              
        /**
         * The value for the link field
         */
        private String link;
              
        /**
         * The value for the msg field
         */
        private String msg;
              
        /**
         * The value for the startdate field
         */
        private Date startdate;
              
        /**
         * The value for the enddate field
         */
        private Date enddate;
      
      
        /**
         * Get the Eventid
         *
         * @return int
         */
        public int getEventid()
        {
            return eventid;
        }

                                            
        /**
         * Set the value of Eventid
         *
         * @param v new value
         */
        public void setEventid(int v) 
        {
          


         if (this.eventid != v)
        {
             this.eventid = v;
            setModified(true);
        }

                  
                       }


        /**
         * Get the Teamid
         *
         * @return int
         */
        public int getTeamid()
        {
            return teamid;
        }

                                                      
        /**
         * Set the value of Teamid
         *
         * @param v new value
         */
        public void setTeamid(int v) throws TorqueException
        {
          


         if (this.teamid != v)
        {
             this.teamid = v;
            setModified(true);
        }

                                          
                if (aTeam != null && !(aTeam.getTeamid()==v))
                {
            aTeam = null;
        }
          
                       }


        /**
         * Get the Title
         *
         * @return String
         */
        public String getTitle()
        {
            return title;
        }

                                            
        /**
         * Set the value of Title
         *
         * @param v new value
         */
        public void setTitle(String v) 
        {
          


         if (!ObjectUtils.equals(this.title, v))
        {
             this.title = v;
            setModified(true);
        }

                  
                       }


        /**
         * Get the Link
         *
         * @return String
         */
        public String getLink()
        {
            return link;
        }

                                            
        /**
         * Set the value of Link
         *
         * @param v new value
         */
        public void setLink(String v) 
        {
          


         if (!ObjectUtils.equals(this.link, v))
        {
             this.link = v;
            setModified(true);
        }

                  
                       }


        /**
         * Get the Msg
         *
         * @return String
         */
        public String getMsg()
        {
            return msg;
        }

                                            
        /**
         * Set the value of Msg
         *
         * @param v new value
         */
        public void setMsg(String v) 
        {
          


         if (!ObjectUtils.equals(this.msg, v))
        {
             this.msg = v;
            setModified(true);
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


 
     
   
             
   
       private Team aTeam;

    /**
     * Declares an association between this object and a Team object
     *
     * @param v Team
     * @throws TorqueException
     */
    public void setTeam(Team v) throws TorqueException
    {
           if (v == null)
        {
                        setTeamid(0);
                    }
        else
        {
            setTeamid(v.getTeamid());
        }
           aTeam = v;
    }

                 
    /**
     * Get the associated Team object
     *
     * @return the associated Team object
     * @throws TorqueException
     */
    public Team getTeam() throws TorqueException
    {
        if (aTeam == null && (this.teamid > 0))
        {
              aTeam = TeamPeer.retrieveByPK(SimpleKey.keyFor(this.teamid));
      
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               Team obj = TeamPeer.retrieveByPK(this.teamid);
               obj.addEvents(this);
             */
        }
        return aTeam;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
     */
    public void setTeamKey(ObjectKey key) throws TorqueException
    {
    
                                        setTeamid(((NumberKey) key).intValue());
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
            fieldNames.add("Eventid");
            fieldNames.add("Teamid");
            fieldNames.add("Title");
            fieldNames.add("Link");
            fieldNames.add("Msg");
            fieldNames.add("Startdate");
            fieldNames.add("Enddate");
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
            if (name.equals("Eventid"))
    {
              return new Integer(getEventid());
          }
            if (name.equals("Teamid"))
    {
              return new Integer(getTeamid());
          }
            if (name.equals("Title"))
    {
              return getTitle();
          }
            if (name.equals("Link"))
    {
              return getLink();
          }
            if (name.equals("Msg"))
    {
              return getMsg();
          }
            if (name.equals("Startdate"))
    {
              return getStartdate();
          }
            if (name.equals("Enddate"))
    {
              return getEnddate();
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
            if (name.equals(EventPeer.EVENTID))
        {
              return new Integer(getEventid());
          }
            if (name.equals(EventPeer.TEAMID))
        {
              return new Integer(getTeamid());
          }
            if (name.equals(EventPeer.TITLE))
        {
              return getTitle();
          }
            if (name.equals(EventPeer.LINK))
        {
              return getLink();
          }
            if (name.equals(EventPeer.MSG))
        {
              return getMsg();
          }
            if (name.equals(EventPeer.STARTDATE))
        {
              return getStartdate();
          }
            if (name.equals(EventPeer.ENDDATE))
        {
              return getEnddate();
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
              return new Integer(getEventid());
          }
            if (pos == 1)
    {
              return new Integer(getTeamid());
          }
            if (pos == 2)
    {
              return getTitle();
          }
            if (pos == 3)
    {
              return getLink();
          }
            if (pos == 4)
    {
              return getMsg();
          }
            if (pos == 5)
    {
              return getStartdate();
          }
            if (pos == 6)
    {
              return getEnddate();
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
             save(EventPeer.getMapBuilder()
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
                EventPeer.doInsert((Event) this, con);
                setNew(false);
            }
            else
            {
                EventPeer.doUpdate((Event) this, con);
            }
        }

              alreadyInSave = false;
      }
      }


                
    
    

        /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  eventid ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
                    setEventid(((NumberKey) key).intValue());
            }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
                    setEventid(Integer.parseInt(key));
            }


    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
        return SimpleKey.keyFor(getEventid());
    }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
     * It then fills all the association collections and sets the
     * related objects to isNew=true.
     */
    public Event copy() throws TorqueException
    {
        return copyInto(new Event());
    }

    protected Event copyInto(Event copyObj) throws TorqueException
    {
        copyObj.setEventid(eventid);
        copyObj.setTeamid(teamid);
        copyObj.setTitle(title);
        copyObj.setLink(link);
        copyObj.setMsg(msg);
        copyObj.setStartdate(startdate);
        copyObj.setEnddate(enddate);

                      copyObj.setEventid(0);
                                    

  
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public EventPeer getPeer()
    {
        return peer;
    }
}
