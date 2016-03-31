package com.sns.Apps.Referee.support;

import java.util.Properties;
import java.io.*;
import java.lang.Exception;
import java.util.Enumeration;
import com.sns.Apps.Referee.*;

public class Associations {

   private static Properties theVals;

   static {
      try {
         /* Set up default values */
         Preference thePrefs = new Preference();
         try {
           thePrefs.loadPrefs("prefs.ini");
         } catch (Exception e) {}
         String dbFile = Preference.getPreference("Data Directory");
         File temp = new File(dbFile + "/RefAssoc");
         FileInputStream is = new FileInputStream(temp);
         theVals = new Properties();
         theVals.load(is);
         is.close();
         thePrefs=null;
         dbFile=null;
      } catch (Exception e) {
         e.printStackTrace();
         System.exit(0);
      }
   }

   public Associations() {
   }

   public static String[] getList() {
      String result[] = new String[theVals.size()];
      int index = 0;

      Enumeration all = theVals.elements();
      while (all.hasMoreElements()) {
         result[index++] = (String) all.nextElement();
      }

      return result;
   }

   public static String[] getKeys() {
      String result[] = new String[theVals.size()];
      int index = 0;

      Enumeration all = theVals.keys();
      while (all.hasMoreElements()) {
         result[index++] = (String) all.nextElement();
      }

      return result;
   }

   public static void setAll(Properties vals) {
      theVals = vals;
   }

   public static void addValue(String key,String value) {
      theVals.put(key,value);
   }

   public static String getValue(String key) {
      return (String) theVals.get(key);
   }

   public static String getKey(String value) {
      Enumeration allKeys = theVals.keys();
      String retVal = "UNDF";
      String curVal;
      boolean done = false;

      while (allKeys.hasMoreElements() && !done) {
         curVal = (String) allKeys.nextElement();
         if ((theVals.get(curVal)).equals(value)) {
            retVal = curVal;
            done = true;
         }
      }

      return retVal;
   }

   public static void save() {
      try {
         FileOutputStream os = new FileOutputStream(new File("RefAssoc"));

         theVals.store(os,"Associations for Referee 1.0");
         os.close();
      } catch (Exception e) {
         System.out.println("Failed to save associations");
      }
   }

   public static Properties getAll() {
      return theVals;
   }

   public static void main (String args[]) {
      theVals = new Properties();
      theVals.put("WCSA","Williamson County Soccer");
      theVals.put("FSA","Fairview Soccer");
      theVals.put("MID","Middle School");
      theVals.put("TRAV","Travel");
      theVals.put("MAYM","May Madness");
      theVals.put("FC","Fall Classic");
      theVals.put("NORT","North Rutherford");
      theVals.put("NSSA","North Shelby Soccer");
      theVals.put("BJPS","Bartlett Junior Pro");
      save();
   }
}
