package com.sns.Apps;

import com.sns.Util.*;
import java.io.*;

/** Basic shortcut program */
class Jump {
	private Preference prefs;
	private FileInputStream in;
	static private final String fileName = "c:/utility/jumpstore.dat";
	
	public Jump() {
		prefs = new Preference();
		try {
			in = new FileInputStream(fileName);
			prefs.LoadPrefs(in);
		} catch (FileNotFoundException e) {
			File f;
			f = new File(fileName);
			try {
				f.createNewFile();
				in = new FileInputStream(fileName);
				prefs.LoadPrefs(in);
			} catch (Exception ex) {
				System.out.println("Big Problem: File IO");
			}
		} catch (IOException e) {
			System.out.println("Error loading preferences");
		}
	}
	
	public void add(String shortStr,String longStr) throws IOException {
		prefs.newPreference(shortStr,longStr,"/");
	}
	
public static void main (String args[]) {
	/* Check out the arguments */
	if ((args.length > 0) && (args[0].equals("-?"))) {
		/* Let the user know how to use the app */
		System.out.println("--> Jump version 1.0 ---");
		System.out.println("--> written by: Rob Broadhead");
		System.out.println("Purpose: Use a shortcut to jump (cd) to a directory");
		System.out.println("Usage: jump [-a] shortname [longname]");
		System.out.println(" -? --> displays this help screen");
		System.out.println("");
		System.out.println("Example: java jump home");
		return;
	} else {
		try {
			if (args[0].equals("-a")) {
				// add a shortcut
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Program Completed Successfully");
	}
}
}
