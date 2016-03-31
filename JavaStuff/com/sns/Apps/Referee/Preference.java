package com.sns.Apps.Referee;

import java.util.Properties;
import java.util.Enumeration;
import java.io.*;

/**
* The class for storing and checking preference values.  It currently
* only allows String preference values.
*
* @version 1.0 20-Sep-2000
* @author Rob Broadhead
*/
public class Preference
{
  /** Default values for the preferences. */
  private static Properties defaults;
  /** Values for the preferences. */
  private static Properties prefs;
  /** The file to store the preferences in. */
  private String filename;
  /** Header string for the prefs and defaults classes. */
  private String header;

  /**
  * Constructor that defaults the header to "No header defined"
  */
  public Preference () {
    header="No Header defined";
  }

  /**
  * A constructor for building preferences.  The preference values that will be
  * available need to be defined in the properties object sent in as a parameter.
  *
  * @param Properties def A properties class containing keys and default values
  *        for the preferences.
  * @param String hdr Just a header string for the properties classes.
  */
  public Preference (Properties def,String hdr) {
  	 defaults=def;
    prefs=new Properties(defaults);
    header=hdr;
  }

  /**
  * This stores the preferences (properties classes) to the file given as a
  * parameter.  The file is ascii text and more or less human readable.
  *
  * @param String filename The file the properties will be stored to.
  * @exception IOException Thrown if the file can not be written to.
  */
  public void savePrefs (String name) throws IOException {
    filename = name;
    FileOutputStream out = new FileOutputStream(name);
    defaults.store(out,header);
    prefs.store(out,header);
  }

  /**
  * This stores the preferences (properties classes) to the filename set in this
  * class.  The file is ascii text and more or less human readable.
  *
  * @exception IOException Thrown if the file can not be written to.
  */
  public void savePrefs () throws IOException {
    if (filename == null) {
      filename="prefs.ini";
    }
    savePrefs(filename);
  }

  /**
  * This loads preferences from a file and completely overrides any data that
  * is currently stored within the current Preference object.
  *
  * @param String filename The file to load the preferences from.
  * @exception IOException Thrown if the file can not be found or read.
  */
  public void loadPrefs (String filename) throws IOException {
    FileInputStream in = new FileInputStream(filename);
    defaults = new Properties();
    defaults.load(in);
    prefs = new Properties(defaults);
    prefs.load(in);
  }

  /**
  * The header string is just for readability within the file the properties are
  * stored in.  The header is used for both the prefs and the defaults properties.
  *
  * @param String val The header string to set.
  */
  public void setHeader(String val) {
    header = val;
  }

  /**
  * This returns the header currently assigned to the Preference object.
  * @return String The header stored in the Preference class.
  */
  public String getHeader() {
    return header;
  }

  /**
  * This sets a given preference value to a new value.
  * @param String item The preference key to set.
  * @param String val The value the preference should be set to.
  */
  public static void setPreference (String item, String val) {
	if (prefs!=null) {
		prefs.put(item,val);
	}
  }

  /**
  * This simply returns the current value of a given preference or the default
  * value for the preference if no value has been assigned.
  * @param String theItem The key of the preference to refer to.
  * @return String The value of the specified preference.
  */
  public static String getPreference (String theItem) {
  	 String retVal;

	retVal="";
	if (prefs!=null) {
		retVal=prefs.getProperty(theItem,defaults.getProperty(theItem));
	}

    return retVal;
  }

  /**
  * This is used to add a new preference key, value, and default value to the
  * preference object.
  * @param String name The key value of the new preference.
  * @param String val The value of the new preference.
  * @param String defs The default value for the new preference.
  */
  public void newPreference (String name,String val,String defs) {
    defaults.put(name,defs);
    prefs.put(name,val);
  }

  /**
  * This returns an enumeration of the keys that are in this Preference object.
  * This is based on the defaults since the actual preferences may not have
  * values and the keys() method does not return any value in that case.
  * @return Enumeration The keys contained in this Preference
  *         object.
  */
  public Enumeration listPreferences () {
    return defaults.keys();
  }

  /**
  * This returns the number of key values in the Preference by checking the
  * properties class for the defaults.
  * @return int THe number of preferences contained in this class.
  */
  public int count () {
    return defaults.size();
  }
}
