package com.sns.scout.managers;

import java.util.List;

import org.apache.torque.util.Criteria;
import java.util.Iterator;
import com.sns.util.*;

/**
 * The skeleton for this class was autogenerated by Torque on:
 * 
 * [Sun Jun 17 00:39:47 CDT 2007]
 * 
 * You should add additional methods to this class to meet the application
 * requirements. This class will only be generated as long as it does not
 * already exist in the output directory.
 */
public class ProjectPeer extends com.sns.scout.managers.BaseProjectPeer {
	static public int countAll() {
		List result = null;

		Criteria crit = new Criteria();

		try {
			crit.setAll();
			result = ProjectPeer.doSelect(crit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.size();
	}
	
	/**
	 * This method returns a list of Project objects where each Project object
	 * maps to a row in the database. This is primarily for drop down lists
	 * in the GUI. 
	 *
	 * @return Project[] 
	 */
	static public Project [] getProjectList() {
		Project theList[] = new Project[Utility.numRows("Project")];
		int count=0;
		
		Criteria crit = new Criteria();

		try {
			List result = ProjectPeer.doSelect(crit);
			Iterator it = result.iterator();
			
			while (it.hasNext()) {
				theList[count++] = (Project) it.next();  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return theList;
	}
	
	/**
	 * This method returns a list of Project objects where each Project object
	 * maps to a row in the database. This is primarily for drop down lists
	 * in the GUI. 
	 *
	 * @return Project[] 
	 */
	static public List getProjects() {
		List retVal = null;
		
		Criteria crit = new Criteria();

		try {
			crit.setAll();
			retVal = ProjectPeer.doSelect(crit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retVal;
	}

}
