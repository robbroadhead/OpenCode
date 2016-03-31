package com.sns.Apps;

import com.sns.Util.*;

/** Basic echo program */
class Echo {
public static void main (String args[]) {
	/* Get input */
	TextIO console = new TextIO();
	console.setPrompt("-->");
	String curEntry = console.inString();
	
	while (!curEntry.equals("Q")) {
		console.writeLn("Entered: " + curEntry);
		curEntry = console.inString();
	}
}
}
