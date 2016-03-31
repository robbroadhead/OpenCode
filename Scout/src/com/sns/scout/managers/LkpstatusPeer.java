package com.sns.scout.managers;

import java.util.Iterator;
import java.util.List;

import org.apache.torque.util.Criteria;

import com.sns.util.Utility;

/** 
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Tue Jan 19 16:39:36 CST 2010]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
public class LkpstatusPeer 
    extends com.sns.scout.managers.BaseLkpstatusPeer
{
	static public Lkpstatus [] getStatusList() {
		Lkpstatus theList[] = new Lkpstatus[Utility.numRows("lkpStatus")];
		int count=0;
		
		Criteria crit = new Criteria();

		try {
			List result = LkpstatusPeer.doSelect(crit);
			Iterator it = result.iterator();
			
			while (it.hasNext()) {
				theList[count++] = (Lkpstatus) it.next();  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return theList;
	}

}
