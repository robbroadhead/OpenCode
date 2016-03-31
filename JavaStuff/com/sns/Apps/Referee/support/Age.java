package com.sns.Apps.Referee.support;

import java.util.Properties;
import java.io.*;
import java.lang.Exception;
import java.util.Enumeration;
import com.sns.Apps.Referee.*;

public class Age {

   private static Properties theVals;

   static {
      try {
         /* Set up default values */
         Preference thePrefs = new Preference();
         try {
           thePrefs.loadPrefs("prefs.ini");
         } catch (Exception e) {}
         String dbFile = Preference.getPreference("Data Directory");
         File temp = new File(dbFile + "/RefAge");
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

   public Age() {
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
         FileOutputStream os = new FileOutputStream(new File("RefAge"));

         theVals.store(os,"Age for Referee 1.0");
         os.close();
      } catch (Exception e) {
         System.out.println("Failed to save Age");
      }
   }

   public static Properties getAll() {
      return theVals;
   }

   public static void main (String args[]) {
      theVals = new Properties();
      theVals.put("U7B","Under 7 Boys");
      theVals.put("U7G","Under 7 Girls");
      theVals.put("U8B","Under 8 Boys");
      theVals.put("U8G","Under 8 Girls");
      theVals.put("U9B","Under 9 Boys");
      theVals.put("U9G","Under 9 Girls");
      theVals.put("U10B","Under 10 Boys");
      theVals.put("U10G","Under 10 Girls");
      theVals.put("U11B","Under 11 Boys");
      theVals.put("U11G","Under 11 Girls");
      theVals.put("U12B","Under 12 Boys");
      theVals.put("U12G","Under 12 Girls");
      theVals.put("U13B","Under 13 Boys");
      theVals.put("U13G","Under 13 Girls");
      theVals.put("U14B","Under 14 Boys");
      theVals.put("U14G","Under 14 Girls");
      theVals.put("U15B","Under 15 Boys");
      theVals.put("U15G","Under 15 Girls");
      theVals.put("U16B","Under 16 Boys");
      theVals.put("U16G","Under 16 Girls");
      theVals.put("U17B","Under 17 Boys");
      theVals.put("U17G","Under 17 Girls");
      theVals.put("U18B","Under 18 Boys");
      theVals.put("U18G","Under 18 Girls");
      theVals.put("U19B","Under 19 Boys");
      theVals.put("U19G","Under 19 Girls");
      theVals.put("U30M","Under 30 Men");
      theVals.put("U30W","Under 30 Women");
      theVals.put("O30M","Over 30 Men");
      theVals.put("O30W","Over 30 Women");
      save();
   }
}
