
package com.sns.teammgr.managers;


import org.apache.torque.om.Persistent;
import java.sql.*;
import org.apache.torque.*;

/** 
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Tue Feb 19 16:03:50 CST 2008]
 *
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 */
public  class Player 
    extends com.sns.teammgr.managers.BasePlayer
    implements Persistent
{

	public int getAssists() {
		int retVal = 0;
		
		Connection con = null;
		try {
			PreparedStatement stmt;
			con = Torque.getConnection();
			stmt = con.prepareStatement("select count(*) from PlayerPoints pp,Player p where p.playerid=pp.playerid and pointtype=0 and p.playerID =" + getPlayerid());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			retVal = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Torque.closeConnection(con);
		}
		
		return retVal;		
	}

	public int getGoals() {
		int retVal = 0;
		
		Connection con = null;
		try {
			PreparedStatement stmt;
			con = Torque.getConnection();
			stmt = con.prepareStatement("select count(*) from PlayerPoints pp,Player p where p.playerid=pp.playerid and pointtype=1 and p.playerID =" + getPlayerid());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			retVal = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Torque.closeConnection(con);
		}
	
		return retVal;
	}
	
	public int getShotsAgainst() {
		int retVal = 0;
		
		Connection con = null;
		try {
			PreparedStatement stmt;
			con = Torque.getConnection();
			stmt = con.prepareStatement("select count(*) from PlayerPoints pp,Player p where p.playerid=pp.playerid and pointtype=3 and p.playerID =" + getPlayerid());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			retVal = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Torque.closeConnection(con);
		}
	
		return retVal;
	}
	public int getShutouts() {
		int retVal = 0;
		
		Connection con = null;
		try {
			PreparedStatement stmt;
			con = Torque.getConnection();
			stmt = con.prepareStatement("select count(*) from PlayerPoints pp,Player p where p.playerid=pp.playerid and pointtype=2 and p.playerID =" + getPlayerid());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			retVal = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Torque.closeConnection(con);
		}
	
		return retVal;
	}
	
	public int getWins() {
		int retVal = 0;
		
		Connection con = null;
		try {
			PreparedStatement stmt;
			con = Torque.getConnection();
			stmt = con.prepareStatement("select count(*) from Game g where result=1 and goalieid=" + getPlayerid());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			retVal = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Torque.closeConnection(con);
		}
	
		return retVal;
	}
	
	public int getLosses() {
		int retVal = 0;
		
		Connection con = null;
		try {
			PreparedStatement stmt;
			con = Torque.getConnection();
			stmt = con.prepareStatement("select count(*) from Game g where result=2 and goalieid=" + getPlayerid());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			retVal = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Torque.closeConnection(con);
		}
	
		return retVal;
	}

}
