package com.sns.teammgr.managers;

import org.apache.torque.util.Criteria;
import java.util.List;
import java.util.Iterator;

/** 
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Fri Aug 03 14:22:15 CDT 2007]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
public class GamecommentsPeer 
    extends com.sns.teammgr.managers.BaseGamecommentsPeer
{

	static public void delete(String theId) {
		int currentId = Integer.parseInt(theId);
	
		Criteria crit = new Criteria();
	
		try {
			crit.add(GamecommentsPeer.COMMENTID, currentId);
			crit.setAll();
			GamecommentsPeer.doDelete(crit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This returns a list of Gamecomments objects where each 
	 * Gamecomments object maps to a row in the database. This is primarily
	 * for drop down lists in the GUI or Gamecomments listings. 
	 * 
	 * @return Lkptype[] 
	 */
	static public Gamecomments [] getMessages(int teamId) {
		Gamecomments theList[] = null;
		int count=0;
		
		Criteria crit = new Criteria();
	
		try {
			crit.add(GamecommentsPeer.GAMEID,teamId);
			List result = GamecommentsPeer.doSelect(crit);
			Iterator it = result.iterator();
			theList = new Gamecomments[result.size()];
			
			while (it.hasNext()) {
				theList[count++] = (Gamecomments) it.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return theList;
	}
}