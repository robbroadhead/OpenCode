
package com.sns.scout.managers;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import java.sql.ResultSet;

import org.apache.torque.Torque;
import org.apache.torque.om.Persistent;
import org.apache.torque.util.Criteria;

/** 
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Fri Mar 14 14:37:34 CDT 2008]
 *
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 */
public  class Project 
    extends com.sns.scout.managers.BaseProject
    implements Persistent
{
	public int taskCount() {
		int count = 0;
		Criteria crit = new Criteria();

		try {
			crit.add(BaseTaskPeer.PROJECTID,this.getProjectid());
			List result = BaseTaskPeer.doSelect(crit);
			count = result.size();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}

	public int closedCount() {
		int count = 0;
		Criteria crit = new Criteria();

		try {
			crit.add(BaseTaskPeer.PROJECTID,this.getProjectid());
			//TO-DO Note that this uses a hard coded status id. It should be adjusted at some point. 
			crit.add(BaseTaskPeer.STATUSID,103);
			List result = BaseTaskPeer.doSelect(crit);
			count = result.size();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}

	public int lateCount() {
		int count = 0;

		try {
			// Delete the points, much easier in SQL.
			PreparedStatement stmt;
			Connection con = Torque.getConnection();
			stmt = con
					.prepareStatement("select * from TaskEstimate where enddate < sysdate() and taskID in (select distinct taskID from Task where projectID=" + this.getProjectid() + ")");
			ResultSet rs = stmt.executeQuery();

			rs.next();
			count = rs.getInt(0);
			stmt.close();
			Torque.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
}
