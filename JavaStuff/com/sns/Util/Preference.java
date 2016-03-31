/**
Preferences Class
Written by:Rob Broadhead
           4-7-1998
$Id
*/

package com.sns.Util;

import java.util.Properties;
import java.io.*;

public class Preference
{
  private static Properties defaults_A;
  private static Properties prefs_A;
  private String header_A;

  public Preference ()
  {
  	defaults_A=new Properties();
    prefs_A=new Properties(defaults_A);
    header_A="No Header defined";
  }

  public void SavePrefs (FileOutputStream out)
  {
    try {
   	  prefs_A.store(out,header_A);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setHeader(String val) {
    header_A = val;
  }

  public void LoadPrefs (FileInputStream in) throws IOException
  {
  	prefs_A.load(in);
  }

  public static void setPreference (String item, String val)
  {
		if (prefs_A!=null)
     {
			prefs_A.put(item,val);
		}
  }

  public static String getPreference (String theItem)
  {
  	String retVal;

     retVal="";
		if (prefs_A!=null)
     {
     	retVal=prefs_A.getProperty(theItem);
		}

     return retVal;
  }

  public void newPreference (String name,String val,String defs)
  {
    defaults_A.put(name,defs);
    prefs_A.put(name,val);
  }

  public void ListPreferences ()
  {
  }


}
