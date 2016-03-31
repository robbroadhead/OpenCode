package com.sns.Apps;

import com.sns.Util.Tools;

/**
 * 
 * @version 1.0 21-Mar-2002
 * @author Rob Broadhead
 */
public class FileCompare {
	/**
	 * This method runs the application, it is a console-based application.
	 * 
	 * @param String
	 *            arg[0] This is either a <code>-?</code> to display help or the
	 *            file name data will be loaded from.
	 * @param String
	 *            arg[1] This is the file that will be compared against the src
	 *            file.
	 * 
	 */
	public static void main(String args[]) {
		Tools util = new Tools();

		/* Check out the arguments */
		if ((args.length != 2) || (args[0].equals("-?"))) {
			/* Let the user know how to use the app */
			System.out.println("--> FileCompare version 1.0 ---");
			System.out.println("--> written by: Rob Broadhead");
			System.out.println("Purpose: to compare files.");
			System.out
					.println("Usage: java FileCompare [-?] [oldfile] [newfile]");
			System.out.println(" oldfile --> The original file");
			System.out
					.println(" newfile --> The file generated from the new DB");
			System.out.println(" -? --> displays this help screen");
			System.out.println("");
			System.out
					.println("Example: java FileCompare hold/File.dat File.dat");
			return;
		} else {
			try {
				util.loadData(args[0], args[1]);
				System.out.println("Program Completed Successfully");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Program failed while loading data.");
			}
		}
	}
}
