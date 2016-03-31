package com.sns.teammgr.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.apache.torque.Torque;
import org.apache.torque.util.Criteria;

/** 
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Fri Aug 03 14:22:15 CDT 2007]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
public class PlayerPeer 
    extends com.sns.teammgr.managers.BasePlayerPeer
{
	static public void deleteForTeam(String theId) {
		int currentId = Integer.parseInt(theId);

		Criteria crit = new Criteria();

		Connection con = null;
		try {
			// Delete the points, much easier in SQL.
			PreparedStatement stmt;
			con = Torque.getConnection();
			stmt = con
					.prepareStatement("Delete from playerpoints where playerid in (select playerid from team where teamid="
							+ currentId + ")");
			stmt.executeUpdate();

			// Delete the players
			crit = new Criteria();
			crit.add(PlayerPeer.TEAMID, currentId);
			crit.setAll();
			PlayerPeer.doDelete(crit);
			
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Torque.closeConnection(con);
		}
	}
	
	static public int getIdFromJersey(String shirtNum, int teamId) {
		int retVal = -1;
		Criteria crit = new Criteria();

		try {
			crit.add(PlayerPeer.JERSEYNUM, Integer.parseInt(shirtNum));
			crit.add(PlayerPeer.TEAMID,teamId);
			crit.setAll();
			List player = PlayerPeer.doSelect(crit);
			if (player.size() > 0) {
				retVal = ((Player) player.get(0)).getPlayerid();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	static public void delete(String theId) {
		int currentId = Integer.parseInt(theId);

		Criteria crit = new Criteria();

		try {
			// Delete the points
			crit.add(PlayerpointsPeer.PLAYERID, currentId);
			crit.setAll();
			PlayerpointsPeer.doDelete(crit);
			
			// now we can delete the record
			crit.add(PlayerPeer.PLAYERID, currentId);
			crit.setAll();
			PlayerPeer.doDelete(crit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}