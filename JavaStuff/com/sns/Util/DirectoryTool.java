package com.sns.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class DirectoryTool {
	File rootPath;
	
	public void setRootPath(String thePath) {
		rootPath = new File(thePath);
	}

	public File getRootPath() {
		return rootPath;
	}

	public boolean doesExist() {
		return rootPath.exists();
	}
	
	public boolean isDirectory() {
		return rootPath.isDirectory();
	}
	
	public int countSubDirs() {
		return getSubDirs().size();
	}

	static public int countSubDirsRecurse(File theFile) {
		int numDirs = 0;
		ArrayList mySubs;
		
		mySubs = DirectoryTool.getSubDirs(theFile);
		numDirs = mySubs.size();
		
		Iterator it = mySubs.iterator();
		File curFile = null;
		while (it.hasNext()) {
			curFile = (File) it.next();
			numDirs += countSubDirsRecurse(curFile);
		}
		
		return numDirs;
	}

	public long sizeOfFiles() {
		long totalSize = 0;
		
		File[] curList = rootPath.listFiles();
		for (int i=0;i < curList.length;i++) {
			if (curList[i].isFile()) {
				totalSize+=curList[i].length();
			}
		}
		
		return totalSize;
	}
	
	static public long sizeOfFiles(String fileName) {
		File theFile = new File(fileName);
		long totalSize = 0;
		
		File[] curList = theFile.listFiles();
		for (int i=0;i < curList.length;i++) {
			if (curList[i].isFile()) {
				totalSize+=curList[i].length();
			}
		}
		
		return totalSize;
	}
	
	static public ArrayList getSubDirs(File theFile) {
		ArrayList returnValues = new ArrayList();
		
		File[] curList = theFile.listFiles();
		for (int i=0;i < curList.length;i++) {
			if (curList[i].isDirectory()) {
				returnValues.add(curList[i]);
			}
		}
		
		return returnValues;
	}
		
	public ArrayList getSubDirs() {
		ArrayList returnValues = new ArrayList();
		
		File[] curList = rootPath.listFiles();
		for (int i=0;i < curList.length;i++) {
			if (curList[i].isDirectory()) {
				returnValues.add(curList[i]);
			}
		}
		
		return returnValues;
	}
		
	public static void main(String args[]) {
		/* Create a file */
		DirectoryTool util = new DirectoryTool();
		util.setRootPath(args[0]);

		// Verify it exists
		System.out.println("Exists?: " + util.doesExist());
		
		// Verify its a directory
		System.out.println("Dir?: " + util.isDirectory());
		
		// Count the subdirectories
		System.out.println("Number of Subdirs:" + util.countSubDirs());

		// File Sizes
		System.out.println("Total Size:" + util.sizeOfFiles());

		// Check the subdirectories
		System.out.println("SubDir Count: " + util.countSubDirsRecurse(util.rootPath));

	}
}
