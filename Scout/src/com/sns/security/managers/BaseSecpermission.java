package com.sns.security.managers;



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
 * [Fri Mar 07 11:01:56 CST 2008]
 *
 * You should not use this class directly.  It should not even be
 * extended all references should be to Secpermission
 */
public abstract class BaseSecpermission extends BaseObject
{
    /** The Peer class */
    private static final SecpermissionPeer peer =
        new SecpermissionPeer();

                  
        /**
         * The value for the permid field
         */
        private int permid;
                                                                                      
        /**
         * The value for the permname field
         */
        private String permname = "";
                                                                                      
        /**
         * The value for the note field
         */
        private String note = "";
      
      
        /**
         * Get the Permid
         *
         * @return int
         */
        public int getPermid()
        {
            return permid;
        }

                                                                              
        /**
         * Set the value of Permid
         *
         * @param v new value
         */
        public void setPermid(int v) throws TorqueException
        {
          


         if (this.permid != v)
        {
             this.permid = v;
            setModified(true);
        }

                  
                                                  
              // update associated Secroleperm
              if (collSecroleperms != null)
              {
                  for (int i = 0; i < collSecroleperms.size(); i++)
                  {
                      ((Secroleperm) collSecroleperms.get(i))
                          .setPermid(v);
                  }
              }
                                                              
              // update associated Secuserperm
              if (collSecuserperms != null)
              {
                  for (int i = 0; i < collSecuserperms.size(); i++)
                  {
                      ((Secuserperm) collSecuserperms.get(i))
                          .setPermid(v);
                  }
              }
                                                              
              // update associated Secappperm
              if (collSecappperms != null)
              {
                  for (int i = 0; i < collSecappperms.size(); i++)
                  {
                      ((Secappperm) collSecappperms.get(i))
                          .setPermid(v);
                  }
              }
                                   }


        /**
         * Get the Permname
         *
         * @return String
         */
        public String getPermname()
        {
            return permname;
        }

                                            
        /**
         * Set the value of Permname
         *
         * @param v new value
         */
        public void setPermname(String v) 
        {
          


         if (!ObjectUtils.equals(this.permname, v))
        {
             this.permname = v;
            setModified(true);
        }

                  
                       }


        /**
         * Get the Note
         *
         * @return String
         */
        public String getNote()
        {
            return note;
        }

                                            
        /**
         * Set the value of Note
         *
         * @param v new value
         */
        public void setNote(String v) 
        {
          


         if (!ObjectUtils.equals(this.note, v))
        {
             this.note = v;
            setModified(true);
        }

                  
                       }


 
        
                
      
    /**
     * Collection to store aggregation of collSecroleperms
     */
    protected List collSecroleperms;

    /**
     * Temporary storage of collSecroleperms to save a possible db hit in
     * the event objects are add to the collection, but the
     * complete collection is never requested.
     */
    protected void initSecroleperms()
    {
        if (collSecroleperms == null)
        {
            collSecroleperms = new ArrayList();
        }
    }

    /**
     * Method called to associate a Secroleperm object to this object
     * through the Secroleperm foreign key attribute
     *
     * @param l Secroleperm
     * @throws TorqueException
     */
    public void addSecroleperm(Secroleperm l) throws TorqueException
    {
        getSecroleperms().add(l);
        l.setSecpermission((Secpermission) this);
    }

    /**
     * The criteria used to select the current contents of collSecroleperms
     */
    private Criteria lastSecrolepermsCriteria = null;

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getSecroleperms(new Criteria())
     *
     * @throws TorqueException
     */
    public List getSecroleperms() throws TorqueException
    {
        if (collSecroleperms == null)
        {
            collSecroleperms = getSecroleperms(new Criteria(10));
        }
        return collSecroleperms;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission has previously
     * been saved, it will retrieve related Secroleperms from storage.
     * If this Secpermission is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     *
     * @throws TorqueException
     */
    public List getSecroleperms(Criteria criteria) throws TorqueException
    {
        if (collSecroleperms == null)
        {
            if (isNew())
            {
               collSecroleperms = new ArrayList();
            }
            else
            {
                   criteria.add(SecrolepermPeer.PERMID, getPermid() );
                   collSecroleperms = SecrolepermPeer.doSelect(criteria);
            }
        }
        else
        {
            // criteria has no effect for a new object
            if (!isNew())
            {
                // the following code is to determine if a new query is
                // called for.  If the criteria is the same as the last
                // one, just return the collection.
                   criteria.add(SecrolepermPeer.PERMID, getPermid());
                   if (!lastSecrolepermsCriteria.equals(criteria))
                {
                    collSecroleperms = SecrolepermPeer.doSelect(criteria);
                }
            }
        }
        lastSecrolepermsCriteria = criteria;

        return collSecroleperms;
    }

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getSecroleperms(new Criteria(),Connection)
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getSecroleperms(Connection con) throws TorqueException
    {
        if (collSecroleperms == null)
        {
            collSecroleperms = getSecroleperms(new Criteria(10), con);
        }
        return collSecroleperms;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission has previously
     * been saved, it will retrieve related Secroleperms from storage.
     * If this Secpermission is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getSecroleperms(Criteria criteria, Connection con)
            throws TorqueException
    {
        if (collSecroleperms == null)
        {
            if (isNew())
            {
               collSecroleperms = new ArrayList();
            }
            else
            {
                     criteria.add(SecrolepermPeer.PERMID, getPermid());
                     collSecroleperms = SecrolepermPeer.doSelect(criteria, con);
             }
         }
         else
         {
             // criteria has no effect for a new object
             if (!isNew())
             {
                 // the following code is to determine if a new query is
                 // called for.  If the criteria is the same as the last
                 // one, just return the collection.
                     criteria.add(SecrolepermPeer.PERMID, getPermid());
                     if (!lastSecrolepermsCriteria.equals(criteria))
                 {
                     collSecroleperms = SecrolepermPeer.doSelect(criteria, con);
                 }
             }
         }
         lastSecrolepermsCriteria = criteria;

         return collSecroleperms;
     }

    

     
      
         
          
                            
                
        
        
   
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission is new, it will return
     * an empty collection; or if this Secpermission has previously
     * been saved, it will retrieve related Secroleperms from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Secpermission.
     */
    protected List getSecrolepermsJoinSecpermission(Criteria criteria)
        throws TorqueException
    {
        if (collSecroleperms == null)
        {
            if (isNew())
            {
               collSecroleperms = new ArrayList();
            }
            else
            {
                   criteria.add(SecrolepermPeer.PERMID, getPermid());
                   collSecroleperms = SecrolepermPeer.doSelectJoinSecpermission(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                   criteria.add(SecrolepermPeer.PERMID, getPermid());
               if (!lastSecrolepermsCriteria.equals(criteria))
            {
                collSecroleperms = SecrolepermPeer.doSelectJoinSecpermission(criteria);
            }
        }
        lastSecrolepermsCriteria = criteria;

        return collSecroleperms;
    }
   
      
      
          
                            
                
        
        
   
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission is new, it will return
     * an empty collection; or if this Secpermission has previously
     * been saved, it will retrieve related Secroleperms from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Secpermission.
     */
    protected List getSecrolepermsJoinSecrole(Criteria criteria)
        throws TorqueException
    {
        if (collSecroleperms == null)
        {
            if (isNew())
            {
               collSecroleperms = new ArrayList();
            }
            else
            {
                   criteria.add(SecrolepermPeer.PERMID, getPermid());
                   collSecroleperms = SecrolepermPeer.doSelectJoinSecrole(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                   criteria.add(SecrolepermPeer.PERMID, getPermid());
               if (!lastSecrolepermsCriteria.equals(criteria))
            {
                collSecroleperms = SecrolepermPeer.doSelectJoinSecrole(criteria);
            }
        }
        lastSecrolepermsCriteria = criteria;

        return collSecroleperms;
    }
     



             
      
    /**
     * Collection to store aggregation of collSecuserperms
     */
    protected List collSecuserperms;

    /**
     * Temporary storage of collSecuserperms to save a possible db hit in
     * the event objects are add to the collection, but the
     * complete collection is never requested.
     */
    protected void initSecuserperms()
    {
        if (collSecuserperms == null)
        {
            collSecuserperms = new ArrayList();
        }
    }

    /**
     * Method called to associate a Secuserperm object to this object
     * through the Secuserperm foreign key attribute
     *
     * @param l Secuserperm
     * @throws TorqueException
     */
    public void addSecuserperm(Secuserperm l) throws TorqueException
    {
        getSecuserperms().add(l);
        l.setSecpermission((Secpermission) this);
    }

    /**
     * The criteria used to select the current contents of collSecuserperms
     */
    private Criteria lastSecuserpermsCriteria = null;

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getSecuserperms(new Criteria())
     *
     * @throws TorqueException
     */
    public List getSecuserperms() throws TorqueException
    {
        if (collSecuserperms == null)
        {
            collSecuserperms = getSecuserperms(new Criteria(10));
        }
        return collSecuserperms;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission has previously
     * been saved, it will retrieve related Secuserperms from storage.
     * If this Secpermission is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     *
     * @throws TorqueException
     */
    public List getSecuserperms(Criteria criteria) throws TorqueException
    {
        if (collSecuserperms == null)
        {
            if (isNew())
            {
               collSecuserperms = new ArrayList();
            }
            else
            {
                   criteria.add(SecuserpermPeer.PERMID, getPermid() );
                   collSecuserperms = SecuserpermPeer.doSelect(criteria);
            }
        }
        else
        {
            // criteria has no effect for a new object
            if (!isNew())
            {
                // the following code is to determine if a new query is
                // called for.  If the criteria is the same as the last
                // one, just return the collection.
                   criteria.add(SecuserpermPeer.PERMID, getPermid());
                   if (!lastSecuserpermsCriteria.equals(criteria))
                {
                    collSecuserperms = SecuserpermPeer.doSelect(criteria);
                }
            }
        }
        lastSecuserpermsCriteria = criteria;

        return collSecuserperms;
    }

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getSecuserperms(new Criteria(),Connection)
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getSecuserperms(Connection con) throws TorqueException
    {
        if (collSecuserperms == null)
        {
            collSecuserperms = getSecuserperms(new Criteria(10), con);
        }
        return collSecuserperms;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission has previously
     * been saved, it will retrieve related Secuserperms from storage.
     * If this Secpermission is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getSecuserperms(Criteria criteria, Connection con)
            throws TorqueException
    {
        if (collSecuserperms == null)
        {
            if (isNew())
            {
               collSecuserperms = new ArrayList();
            }
            else
            {
                     criteria.add(SecuserpermPeer.PERMID, getPermid());
                     collSecuserperms = SecuserpermPeer.doSelect(criteria, con);
             }
         }
         else
         {
             // criteria has no effect for a new object
             if (!isNew())
             {
                 // the following code is to determine if a new query is
                 // called for.  If the criteria is the same as the last
                 // one, just return the collection.
                     criteria.add(SecuserpermPeer.PERMID, getPermid());
                     if (!lastSecuserpermsCriteria.equals(criteria))
                 {
                     collSecuserperms = SecuserpermPeer.doSelect(criteria, con);
                 }
             }
         }
         lastSecuserpermsCriteria = criteria;

         return collSecuserperms;
     }

    

     
      
      
          
                            
                
        
        
   
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission is new, it will return
     * an empty collection; or if this Secpermission has previously
     * been saved, it will retrieve related Secuserperms from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Secpermission.
     */
    protected List getSecuserpermsJoinSecuser(Criteria criteria)
        throws TorqueException
    {
        if (collSecuserperms == null)
        {
            if (isNew())
            {
               collSecuserperms = new ArrayList();
            }
            else
            {
                   criteria.add(SecuserpermPeer.PERMID, getPermid());
                   collSecuserperms = SecuserpermPeer.doSelectJoinSecuser(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                   criteria.add(SecuserpermPeer.PERMID, getPermid());
               if (!lastSecuserpermsCriteria.equals(criteria))
            {
                collSecuserperms = SecuserpermPeer.doSelectJoinSecuser(criteria);
            }
        }
        lastSecuserpermsCriteria = criteria;

        return collSecuserperms;
    }
   
      
         
          
                            
                
        
        
   
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission is new, it will return
     * an empty collection; or if this Secpermission has previously
     * been saved, it will retrieve related Secuserperms from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Secpermission.
     */
    protected List getSecuserpermsJoinSecpermission(Criteria criteria)
        throws TorqueException
    {
        if (collSecuserperms == null)
        {
            if (isNew())
            {
               collSecuserperms = new ArrayList();
            }
            else
            {
                   criteria.add(SecuserpermPeer.PERMID, getPermid());
                   collSecuserperms = SecuserpermPeer.doSelectJoinSecpermission(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                   criteria.add(SecuserpermPeer.PERMID, getPermid());
               if (!lastSecuserpermsCriteria.equals(criteria))
            {
                collSecuserperms = SecuserpermPeer.doSelectJoinSecpermission(criteria);
            }
        }
        lastSecuserpermsCriteria = criteria;

        return collSecuserperms;
    }
     



             
      
    /**
     * Collection to store aggregation of collSecappperms
     */
    protected List collSecappperms;

    /**
     * Temporary storage of collSecappperms to save a possible db hit in
     * the event objects are add to the collection, but the
     * complete collection is never requested.
     */
    protected void initSecappperms()
    {
        if (collSecappperms == null)
        {
            collSecappperms = new ArrayList();
        }
    }

    /**
     * Method called to associate a Secappperm object to this object
     * through the Secappperm foreign key attribute
     *
     * @param l Secappperm
     * @throws TorqueException
     */
    public void addSecappperm(Secappperm l) throws TorqueException
    {
        getSecappperms().add(l);
        l.setSecpermission((Secpermission) this);
    }

    /**
     * The criteria used to select the current contents of collSecappperms
     */
    private Criteria lastSecapppermsCriteria = null;

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getSecappperms(new Criteria())
     *
     * @throws TorqueException
     */
    public List getSecappperms() throws TorqueException
    {
        if (collSecappperms == null)
        {
            collSecappperms = getSecappperms(new Criteria(10));
        }
        return collSecappperms;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission has previously
     * been saved, it will retrieve related Secappperms from storage.
     * If this Secpermission is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     *
     * @throws TorqueException
     */
    public List getSecappperms(Criteria criteria) throws TorqueException
    {
        if (collSecappperms == null)
        {
            if (isNew())
            {
               collSecappperms = new ArrayList();
            }
            else
            {
                   criteria.add(SecapppermPeer.PERMID, getPermid() );
                   collSecappperms = SecapppermPeer.doSelect(criteria);
            }
        }
        else
        {
            // criteria has no effect for a new object
            if (!isNew())
            {
                // the following code is to determine if a new query is
                // called for.  If the criteria is the same as the last
                // one, just return the collection.
                   criteria.add(SecapppermPeer.PERMID, getPermid());
                   if (!lastSecapppermsCriteria.equals(criteria))
                {
                    collSecappperms = SecapppermPeer.doSelect(criteria);
                }
            }
        }
        lastSecapppermsCriteria = criteria;

        return collSecappperms;
    }

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getSecappperms(new Criteria(),Connection)
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getSecappperms(Connection con) throws TorqueException
    {
        if (collSecappperms == null)
        {
            collSecappperms = getSecappperms(new Criteria(10), con);
        }
        return collSecappperms;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission has previously
     * been saved, it will retrieve related Secappperms from storage.
     * If this Secpermission is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getSecappperms(Criteria criteria, Connection con)
            throws TorqueException
    {
        if (collSecappperms == null)
        {
            if (isNew())
            {
               collSecappperms = new ArrayList();
            }
            else
            {
                     criteria.add(SecapppermPeer.PERMID, getPermid());
                     collSecappperms = SecapppermPeer.doSelect(criteria, con);
             }
         }
         else
         {
             // criteria has no effect for a new object
             if (!isNew())
             {
                 // the following code is to determine if a new query is
                 // called for.  If the criteria is the same as the last
                 // one, just return the collection.
                     criteria.add(SecapppermPeer.PERMID, getPermid());
                     if (!lastSecapppermsCriteria.equals(criteria))
                 {
                     collSecappperms = SecapppermPeer.doSelect(criteria, con);
                 }
             }
         }
         lastSecapppermsCriteria = criteria;

         return collSecappperms;
     }

    

     
      
      
          
                            
                
        
        
   
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission is new, it will return
     * an empty collection; or if this Secpermission has previously
     * been saved, it will retrieve related Secappperms from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Secpermission.
     */
    protected List getSecapppermsJoinSecapp(Criteria criteria)
        throws TorqueException
    {
        if (collSecappperms == null)
        {
            if (isNew())
            {
               collSecappperms = new ArrayList();
            }
            else
            {
                   criteria.add(SecapppermPeer.PERMID, getPermid());
                   collSecappperms = SecapppermPeer.doSelectJoinSecapp(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                   criteria.add(SecapppermPeer.PERMID, getPermid());
               if (!lastSecapppermsCriteria.equals(criteria))
            {
                collSecappperms = SecapppermPeer.doSelectJoinSecapp(criteria);
            }
        }
        lastSecapppermsCriteria = criteria;

        return collSecappperms;
    }
   
      
         
          
                            
                
        
        
   
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Secpermission is new, it will return
     * an empty collection; or if this Secpermission has previously
     * been saved, it will retrieve related Secappperms from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Secpermission.
     */
    protected List getSecapppermsJoinSecpermission(Criteria criteria)
        throws TorqueException
    {
        if (collSecappperms == null)
        {
            if (isNew())
            {
               collSecappperms = new ArrayList();
            }
            else
            {
                   criteria.add(SecapppermPeer.PERMID, getPermid());
                   collSecappperms = SecapppermPeer.doSelectJoinSecpermission(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                   criteria.add(SecapppermPeer.PERMID, getPermid());
               if (!lastSecapppermsCriteria.equals(criteria))
            {
                collSecappperms = SecapppermPeer.doSelectJoinSecpermission(criteria);
            }
        }
        lastSecapppermsCriteria = criteria;

        return collSecappperms;
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
            fieldNames.add("Permid");
            fieldNames.add("Permname");
            fieldNames.add("Note");
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
            if (name.equals("Permid"))
    {
              return new Integer(getPermid());
          }
            if (name.equals("Permname"))
    {
              return getPermname();
          }
            if (name.equals("Note"))
    {
              return getNote();
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
            if (name.equals(SecpermissionPeer.PERMID))
        {
              return new Integer(getPermid());
          }
            if (name.equals(SecpermissionPeer.PERMNAME))
        {
              return getPermname();
          }
            if (name.equals(SecpermissionPeer.NOTE))
        {
              return getNote();
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
              return new Integer(getPermid());
          }
            if (pos == 1)
    {
              return getPermname();
          }
            if (pos == 2)
    {
              return getNote();
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
             save(SecpermissionPeer.getMapBuilder()
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
                SecpermissionPeer.doInsert((Secpermission) this, con);
                setNew(false);
            }
            else
            {
                SecpermissionPeer.doUpdate((Secpermission) this, con);
            }
        }

                                    
                
          if (collSecroleperms != null)
          {
              for (int i = 0; i < collSecroleperms.size(); i++)
              {
                  ((Secroleperm) collSecroleperms.get(i)).save(con);
              }
          }
                                        
                
          if (collSecuserperms != null)
          {
              for (int i = 0; i < collSecuserperms.size(); i++)
              {
                  ((Secuserperm) collSecuserperms.get(i)).save(con);
              }
          }
                                        
                
          if (collSecappperms != null)
          {
              for (int i = 0; i < collSecappperms.size(); i++)
              {
                  ((Secappperm) collSecappperms.get(i)).save(con);
              }
          }
                  alreadyInSave = false;
      }
      }


                        
    
    

        /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  permid ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        throws TorqueException
    {
                    setPermid(((NumberKey) key).intValue());
            }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) throws TorqueException
    {
                    setPermid(Integer.parseInt(key));
            }


    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
        return SimpleKey.keyFor(getPermid());
    }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
     * It then fills all the association collections and sets the
     * related objects to isNew=true.
     */
    public Secpermission copy() throws TorqueException
    {
        return copyInto(new Secpermission());
    }

    protected Secpermission copyInto(Secpermission copyObj) throws TorqueException
    {
        copyObj.setPermid(permid);
        copyObj.setPermname(permname);
        copyObj.setNote(note);

                      copyObj.setPermid(0);
                    

                                  
                
        List v = getSecroleperms();
        for (int i = 0; i < v.size(); i++)
        {
            Secroleperm obj = (Secroleperm) v.get(i);
            copyObj.addSecroleperm(obj.copy());
        }
                                              
                
        v = getSecuserperms();
        for (int i = 0; i < v.size(); i++)
        {
            Secuserperm obj = (Secuserperm) v.get(i);
            copyObj.addSecuserperm(obj.copy());
        }
                                              
                
        v = getSecappperms();
        for (int i = 0; i < v.size(); i++)
        {
            Secappperm obj = (Secappperm) v.get(i);
            copyObj.addSecappperm(obj.copy());
        }
              
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public SecpermissionPeer getPeer()
    {
        return peer;
    }
}
