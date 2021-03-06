package com.hungrymutt.managers;


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
 * [Sun Dec 12 11:27:33 CST 2004]
 *
 * You should not use this class directly.  It should not even be
 * extended all references should be to Ratings
 */
public abstract class BaseRatings extends BaseObject
{
    /** The Peer class */
    private static final RatingsPeer peer =
        new RatingsPeer();

                  
        /**
         * The value for the ratingid field
         */
        private int ratingid;
              
        /**
         * The value for the recipeid field
         */
        private int recipeid;
                                                                            
        /**
         * The value for the rating field
         */
        private int rating = 0;
              
        /**
         * The value for the ipaddress field
         */
        private String ipaddress;
              
        /**
         * The value for the daterated field
         */
        private Date daterated;
      
      
        /**
         * Get the Ratingid
         *
         * @return int
         */
        public int getRatingid()
        {
            return ratingid;
        }

                                            
        /**
         * Set the value of Ratingid
         *
         * @param v new value
         */
        public void setRatingid(int v) 
        {
          


         if (this.ratingid != v)
        {
             this.ratingid = v;
            setModified(true);
        }

                  
                       }


        /**
         * Get the Recipeid
         *
         * @return int
         */
        public int getRecipeid()
        {
            return recipeid;
        }

                                                      
        /**
         * Set the value of Recipeid
         *
         * @param v new value
         */
        public void setRecipeid(int v) throws TorqueException
        {
          


         if (this.recipeid != v)
        {
             this.recipeid = v;
            setModified(true);
        }

                                          
                if (aRecipes != null && !(aRecipes.getRecipeid()==v))
                {
            aRecipes = null;
        }
          
                       }


        /**
         * Get the Rating
         *
         * @return int
         */
        public int getRating()
        {
            return rating;
        }

                                            
        /**
         * Set the value of Rating
         *
         * @param v new value
         */
        public void setRating(int v) 
        {
          


         if (this.rating != v)
        {
             this.rating = v;
            setModified(true);
        }

                  
                       }


        /**
         * Get the Ipaddress
         *
         * @return String
         */
        public String getIpaddress()
        {
            return ipaddress;
        }

                                            
        /**
         * Set the value of Ipaddress
         *
         * @param v new value
         */
        public void setIpaddress(String v) 
        {
          


         if (!ObjectUtils.equals(this.ipaddress, v))
        {
             this.ipaddress = v;
            setModified(true);
        }

                  
                       }


        /**
         * Get the Daterated
         *
         * @return Date
         */
        public Date getDaterated()
        {
            return daterated;
        }

                                            
        /**
         * Set the value of Daterated
         *
         * @param v new value
         */
        public void setDaterated(Date v) 
        {
          


         if (!ObjectUtils.equals(this.daterated, v))
        {
             this.daterated = v;
            setModified(true);
        }

                  
                       }


 
     
   
             
   
       private Recipes aRecipes;

    /**
     * Declares an association between this object and a Recipes object
     *
     * @param v Recipes
     * @throws TorqueException
     */
    public void setRecipes(Recipes v) throws TorqueException
    {
           if (v == null)
        {
                        setRecipeid(0);
                    }
        else
        {
            setRecipeid(v.getRecipeid());
        }
           aRecipes = v;
    }

                 
    /**
     * Get the associated Recipes object
     *
     * @return the associated Recipes object
     * @throws TorqueException
     */
    public Recipes getRecipes() throws TorqueException
    {
        if (aRecipes == null && (this.recipeid > 0))
        {
              aRecipes = RecipesPeer.retrieveByPK(SimpleKey.keyFor(this.recipeid));
      
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               Recipes obj = RecipesPeer.retrieveByPK(this.recipeid);
               obj.addRatingss(this);
             */
        }
        return aRecipes;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
     */
    public void setRecipesKey(ObjectKey key) throws TorqueException
    {
    
                                        setRecipeid(((NumberKey) key).intValue());
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
            fieldNames.add("Ratingid");
            fieldNames.add("Recipeid");
            fieldNames.add("Rating");
            fieldNames.add("Ipaddress");
            fieldNames.add("Daterated");
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
            if (name.equals("Ratingid"))
    {
              return new Integer(getRatingid());
          }
            if (name.equals("Recipeid"))
    {
              return new Integer(getRecipeid());
          }
            if (name.equals("Rating"))
    {
              return new Integer(getRating());
          }
            if (name.equals("Ipaddress"))
    {
              return getIpaddress();
          }
            if (name.equals("Daterated"))
    {
              return getDaterated();
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
            if (name.equals(RatingsPeer.RATINGID))
        {
              return new Integer(getRatingid());
          }
            if (name.equals(RatingsPeer.RECIPEID))
        {
              return new Integer(getRecipeid());
          }
            if (name.equals(RatingsPeer.RATING))
        {
              return new Integer(getRating());
          }
            if (name.equals(RatingsPeer.IPADDRESS))
        {
              return getIpaddress();
          }
            if (name.equals(RatingsPeer.DATERATED))
        {
              return getDaterated();
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
              return new Integer(getRatingid());
          }
            if (pos == 1)
    {
              return new Integer(getRecipeid());
          }
            if (pos == 2)
    {
              return new Integer(getRating());
          }
            if (pos == 3)
    {
              return getIpaddress();
          }
            if (pos == 4)
    {
              return getDaterated();
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
             save(RatingsPeer.getMapBuilder()
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
                RatingsPeer.doInsert((Ratings) this, con);
                setNew(false);
            }
            else
            {
                RatingsPeer.doUpdate((Ratings) this, con);
            }
        }

              alreadyInSave = false;
      }
      }


                
    
    

        /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  ratingid ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
                    setRatingid(((NumberKey) key).intValue());
            }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
                    setRatingid(Integer.parseInt(key));
            }


    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
        return SimpleKey.keyFor(getRatingid());
    }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
     * It then fills all the association collections and sets the
     * related objects to isNew=true.
     */
    public Ratings copy() throws TorqueException
    {
        return copyInto(new Ratings());
    }

    protected Ratings copyInto(Ratings copyObj) throws TorqueException
    {
        copyObj.setRatingid(ratingid);
        copyObj.setRecipeid(recipeid);
        copyObj.setRating(rating);
        copyObj.setIpaddress(ipaddress);
        copyObj.setDaterated(daterated);

                      copyObj.setRatingid(0);
                            

  
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public RatingsPeer getPeer()
    {
        return peer;
    }
}
