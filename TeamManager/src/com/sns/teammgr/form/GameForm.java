package com.sns.teammgr.form;

import java.util.Date;

import javax.servlet.http.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.sns.teammgr.managers.Lkpresulttype;
import com.sns.teammgr.managers.LkpresulttypePeer;
import com.sns.teammgr.managers.Player;
import com.sns.teammgr.managers.TeamPeer;
import com.sns.teammgr.managers.Team;
import com.sns.util.Utility;

/*
 * Created on Jan 7, 2004
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2004 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Resume form. 
 *
 */
public class GameForm extends BaseForm {
	
    private int gameid;
    private int teamid;
    private int scoreus;
    private int scorethem;
    private int result;
    private int goalies;
    private String opponent;
    private String summary;
    private String scoring;
    private String period1;
    private String period2;
    private String period3;
    private String overtime;
    private String highlight;
    private String bottomline;
    private String star1;
    private String star2;
    private String star3;

    public void setGameid(int val) { gameid = val; }
    public void setTeamid(int val) { teamid = val; }
    public void setScoreus(int val) { scoreus = val; }
    public void setScorethem(int val) { scorethem = val; }
    public void setResult(int val) { result = val; }
    public void setGoalies(int val) { goalies = val; }
    public void setOpponent(String val) { opponent = val; }
    public void setSummary(String val) { summary = val; }
    public void setPeriod1(String val) { period1 = val; }
    public void setPeriod2(String val) { period2 = val; }
    public void setPeriod3(String val) { period3 = val; }
    public void setOvertime(String val) { overtime = val; }
    public void setHighlight(String val) { highlight = val; }
    public void setBottomline(String val) { bottomline = val; }
    public void setStar1(String val) { star1 = val; }
    public void setStar2(String val) { star2 = val; }
    public void setStar3(String val) { star3 = val; }
    public void setScoring(String val) { scoring = val; }
    
	public int getGameid() { return gameid; }
	public int getTeamid() { return teamid; }
	public int getScoreus() { return scoreus; }
	public int getScorethem() { return scorethem; }
	public int getResult() { return result; }
	public int getGoalies() { return goalies; }
	public String getOpponent() { return opponent; }
	public String getSummary() { return summary; }
	public String getPeriod2() { return period2; }
	public String getPeriod3() { return period3; }
	public String getPeriod1() { return period1; }
	public String getOvertime() { return overtime; }
	public String getHighlight() { return highlight; }
	public String getBottomline() { return bottomline; }
	public String getStar2() { return star2; }
	public String getStar3() { return star3; }
	public String getStar1() { return star1; }
	public String getScoring() { return scoring; }

    private Lkpresulttype[] resultlist;
    private Player[] glist;
    
	public void setResultlist(Lkpresulttype[] val) { resultlist = val; }
	public Lkpresulttype[] getResultlist() { return resultlist; }
	public void setGlist(Player[] val) { glist = val; }
	public Player[] getGlist() { return glist; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    gameid = 0;
		Team curTeam = (Team) request.getSession().getAttribute("team");
		if (curTeam != null) {
			teamid = curTeam.getTeamid();
		} else {
			teamid = 0;
		}
	    scoreus = 0;
	    scorethem = 0;
	    result = 0;
	    opponent = "";
	    summary = "";
	    period1 = "";
	    period2 = "";
	    period3 = "";
	    star1 = "";
	    star2 = "";
	    star3 = "";
	    scoring = "";
	    overtime = "";
	    highlight = "";
	    bottomline = "";
	    resultlist = LkpresulttypePeer.getResultList();
	    glist = TeamPeer.getGoalies(curTeam.getTeamid());
	}
	
	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    if (isEmptyString(opponent)) {
			messages.add("opponent", new ActionMessage("opponent.required"));
		}
		
		return messages;
	}

}