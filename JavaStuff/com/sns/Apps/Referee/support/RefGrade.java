package com.sns.Apps.Referee.support;

import java.util.Properties;
import java.io.*;
import java.lang.Exception;
import java.util.Enumeration;
import com.sns.Apps.Referee.*;

/** RefGrade.java
 *  @author Rob Broadhead */
public class RefGrade {

   private static Properties theVals;

   static {
      try {
         /* Set up default values */
         Preference thePrefs = new Preference();
         try {
           thePrefs.loadPrefs("prefs.ini");
         } catch (Exception e) {}
         String dbFile = Preference.getPreference("Data Directory");
         File temp = new File(dbFile + "/RefGrade");
         temp.createNewFile();
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

   public RefGrade() {
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
         FileOutputStream os = new FileOutputStream(new File("RefGrade"));

         theVals.store(os,"RefGrade for Referee 1.0");
         os.close();
      } catch (Exception e) {
         System.out.println("Failed to save RefGrade");
      }
   }

   public static Properties getAll() {
      return theVals;
   }

   public static void main (String args[]) {
      theVals = new Properties();
      theVals.put("10","USSF Linesman      ");
      theVals.put("9","USSF Associate Ref.");
      theVals.put("8","Ref. Class 2       ");
      theVals.put("7","Ref. Class 1       ");
      theVals.put("6","State Ref. Class 2 ");
      theVals.put("5","State Ref. Class 1 ");
      theVals.put("4","National Referee   ");
      theVals.put("3","National Ref. (IPC)");
      theVals.put("2","International Line ");
      theVals.put("1","International Ref. ");
      save();
   }
}
