package com.sns.Apps;

import java.io.File;

class ListRoots {
public static void main (String args[]) {
	/* Create a file */
	File[] rootArr = File.listRoots();

	for (int i=1;i<rootArr.length;i++) {
                System.out.println(rootArr[i]);
	}

}
}
