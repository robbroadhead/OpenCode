package com.sns.teammgr.managers;

import java.util.Iterator;
import java.util.List;

import org.apache.torque.util.Criteria;

import com.sns.util.Utility;

/** 
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Sat Sep 29 22:44:49 CDT 2007]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
public class LkpresulttypePeer 
    extends com.sns.teammgr.managers.BaseLkpresulttypePeer
{

	/**
	 * This method simply returns a list of Lkpteamtype objects where each 
	 * Lkptype object maps to a row in the database. This is primarily
	 * for drop down lists in the GUI. 
	 * 
	 * @return Lkptype[] 
	 */
	static public Lkpresulttype [] getResultList() {
		Lkpresulttype theList[] = new Lkpresulttype[Utility.numRows("lkpResultType")];
		int count=0;
		
		Criteria crit = new Criteria();
	
		try {
			List result = LkpresulttypePeer.doSelect(crit);
			Iterator it = result.iterator();
			
			while (it.hasNext()) {
				theList[count++] = (Lkpresulttype) it.next();  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return theList;
	}
}
