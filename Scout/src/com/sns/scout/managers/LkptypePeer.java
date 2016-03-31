package com.sns.scout.managers;

import java.util.Iterator;
import java.util.List;

import org.apache.torque.util.Criteria;

import com.sns.util.Utility;

/** 
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Thu Jul 26 15:58:51 CDT 2007]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
public class LkptypePeer 
    extends com.sns.scout.managers.BaseLkptypePeer
{
	/**
	 * This method simply returns a list of Lkptype objects where each 
	 * Lkptype object maps to a row in the database. This is primarily
	 * for drop down lists in the GUI. 
	 * 
	 * @return Lkptype[] 
	 */
	static public Lkptype [] getTypesList() {
		Lkptype theList[] = new Lkptype[Utility.numRows("lkpType")];
		int count=0;
		
		Criteria crit = new Criteria();

		try {
			List result = LkptypePeer.doSelect(crit);
			Iterator it = result.iterator();
			
			while (it.hasNext()) {
				theList[count++] = (Lkptype) it.next();  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return theList;
	}
}