package com.sns.teammgr.managers;

import org.apache.torque.util.Criteria;
import org.apache.torque.Torque;
import org.apache.torque.TorqueException;

import com.sns.util.Utility;
import com.sns.teammgr.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

/**
 * The skeleton for this class was autogenerated by Torque on:
 * 
 * [Fri Aug 03 14:22:15 CDT 2007]
 * 
 * You should add additional methods to this class to meet the application
 * requirements. This class will only be generated as long as it does not
 * already exist in the output directory.
 */
public class TeamPeer extends com.sns.teammgr.managers.BaseTeamPeer {
	static public void delete(String theId) {
		int currentId = Integer.parseInt(theId);

		Criteria crit = new Criteria();

		try {
			// Delete the messages
			crit.add(TeammsgPeer.TEAMID, currentId);
			crit.setAll();
			TeammsgPeer.doDelete(crit);

			// Delete the links
			crit = new Criteria();
			crit.add(LinkPeer.TEAMID, currentId);
			crit.setAll();
			LinkPeer.doDelete(crit);

			// Delete the events
			crit = new Criteria();
			crit.add(EventPeer.TEAMID, currentId);
			crit.setAll();
			EventPeer.doDelete(crit);

			// Delete the games
			GamePeer.deleteForTeam(theId);

			// Delete the players
			PlayerPeer.deleteForTeam(theId);

			// now we can delete the record
			crit.add(TeamPeer.TEAMID, currentId);
			crit.setAll();
			TeamPeer.doDelete(crit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static public int getStat(int id,int result) {
		int retVal = 0;
		Connection con = null;
		try {
			PreparedStatement wstmt,lstmt,tstmt;
			con = Torque.getConnection();
			wstmt = con.prepareStatement("select count(*) from Game g where result=" + result + " and teamid=" + id);
			ResultSet wrs = wstmt.executeQuery();
			wrs.next();
			retVal = wrs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Torque.closeConnection(con);
		}
		
		return retVal;
	}
	
	static public int getGoalsFor(int id) {
		int retVal = 0;
		Connection con = null;
		try {
			PreparedStatement wstmt,lstmt,tstmt;
			con = Torque.getConnection();
			wstmt = con.prepareStatement("select sum(scoreus) from Game g where teamid=" + id);
			ResultSet wrs = wstmt.executeQuery();
			wrs.next();
			retVal = wrs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Torque.closeConnection(con);
		}
		
		return retVal;
	}
	
	static public int getGoalsAgainst(int id) {
		int retVal = 0;
		Connection con = null;
		try {
			PreparedStatement wstmt,lstmt,tstmt;
			con = Torque.getConnection();
			wstmt = con.prepareStatement("select sum(scorethem) from Game g where teamid=" + id);
			ResultSet wrs = wstmt.executeQuery();
			wrs.next();
			retVal = wrs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Torque.closeConnection(con);
		}
		
		return retVal;
	}
	
	static public int loginUser(String login, String pwd) {
		int retVal = -1;

		Criteria crit = new Criteria();
		crit.add(TeamPeer.USERNAME, login);
		crit.add(TeamPeer.PWD, pwd);
		crit.setAll();

		try {
			List result = TeamPeer.doSelect(crit);

			if (result.size() > 0) {
				retVal = ((Team) result.get(0)).getTeamid();
			}
		} catch (TorqueException e) {
			Constants.LOG.error("Error doing select during login...");
		}
		Constants.LOG.info("TeamPeer::loginUser: Team Id=" + retVal);

		return retVal;
	}

	static public int loginAdmin(String login, String pwd) {
		int retVal = -1;

		Criteria crit = new Criteria();
		crit.add(TeamPeer.ADMINNAME, login);
		crit.add(TeamPeer.ADMINPWD, pwd);
		crit.setAll();

		try {
			List result = TeamPeer.doSelect(crit);

			if (result.size() > 0) {
				retVal = ((Team) result.get(0)).getTeamid();
			}
		} catch (TorqueException e) {
			Constants.LOG.error("Error doing select during login...");
		}
		Constants.LOG.info("TeamPeer::loginAdmin: Team Id=" + retVal);

		return retVal;
	}

	/**
	 * This returns a list of Player objects where each Player object maps to a
	 * row in the database. This is primarily for drop down lists in the GUI or
	 * roster listings.
	 * 
	 * @return Lkptype[]
	 */
	static public Player[] getRoster(int teamId) {
		Player theList[] = null;
		int count = 0;

		Criteria crit = new Criteria();

		try {
			crit.add(PlayerPeer.TEAMID, teamId);
			crit.addAscendingOrderByColumn(PlayerPeer.JERSEYNUM);
			List result = PlayerPeer.doSelect(crit);
			Iterator it = result.iterator();
			theList = new Player[result.size()];

			while (it.hasNext()) {
				theList[count++] = (Player) it.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return theList;
	}

	/**
	 * This returns a list of Player objects where each Player object maps to a
	 * row in the database. This is primarily for drop down lists in the GUI or
	 * roster listings.
	 * 
	 * @return Lkptype[]
	 */
	static public Player[] getGoalies(int teamId) {
		Player theList[] = null;
		int count = 0;

		Criteria crit = new Criteria();

		try {
			crit.add(PlayerPeer.TEAMID, teamId);
			crit.add(PlayerPeer.POSITION, 1);
			crit.addAscendingOrderByColumn(PlayerPeer.JERSEYNUM);
			List result = PlayerPeer.doSelect(crit);
			Iterator it = result.iterator();
			theList = new Player[result.size()];

			while (it.hasNext()) {
				theList[count++] = (Player) it.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return theList;
	}

	/**
	 * This returns a list of Player objects where each Player object maps to a
	 * row in the database. This is primarily for drop down lists in the GUI or
	 * roster listings.
	 * 
	 * @return Lkptype[]
	 */
	static public Player[] getSkaters(int teamId) {
		Player theList[] = null;
		int count = 0;

		Criteria crit = new Criteria();

		try {
			crit.add(PlayerPeer.TEAMID, teamId);
			crit.add(PlayerPeer.POSITION, 1,Criteria.NOT_EQUAL);
			crit.addAscendingOrderByColumn(PlayerPeer.JERSEYNUM);
			List result = PlayerPeer.doSelect(crit);
			Iterator it = result.iterator();
			theList = new Player[result.size()];

			while (it.hasNext()) {
				theList[count++] = (Player) it.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return theList;
	}

	/**
	 * This returns a list of Team objects where each Team object maps to a row
	 * in the database. This is primarily for drop down lists in the GUI or
	 * roster listings.
	 * 
	 * @return Lkptype[]
	 */
	static public Team[] getTeams() {
		Team theList[] = null;
		int count = 0;

		Criteria crit = new Criteria();
		crit.setAll();

		try {
			List result = TeamPeer.doSelect(crit);
			Iterator it = result.iterator();
			if (result.size() > 0) {
				theList = new Team[result.size()];

				while (it.hasNext()) {
					theList[count++] = (Team) it.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return theList;
	}
}
