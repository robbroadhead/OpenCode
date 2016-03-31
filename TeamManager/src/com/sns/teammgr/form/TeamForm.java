package com.sns.teammgr.form;

import java.util.Date;

import javax.servlet.http.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.sns.teammgr.managers.LkpteamtypePeer;
import com.sns.teammgr.managers.Lkpteamtype;
import com.sns.util.Utility;

/*
 * Created on Aug 9, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Team form. 
 *
 */
public class TeamForm extends BaseForm {
	
    private int teamid;
    private String username = "";
    private String pwd = "";
    private String adminname = "";
    private String adminpwd = "";
    private String teamname = "";
    private String season = "";
    private String pcolor = "";
    private String scolor = "";
    private String piclogo = "";
    private String coach = "";
    private String acoach = "";
    private String phone = "";
    private String aphone = "";
    private int teamtype;

    private Lkpteamtype[] typelist;
    
	/* Values for gui lists */
	public void setTypelist(Lkpteamtype[] val) { typelist = val; }
	public Lkpteamtype[] getTypelist() { return typelist; }
	
    public void setTeamid(int val) { teamid = val; }
    public void setUsername(String val) { username = val; }
    public void setPassword(String val) { pwd = val; }
    public void setAdminname(String val) { adminname = val; }
    public void setAdminpassword(String val) { adminpwd = val; }
    public void setTeamname(String val) { teamname = val; }
    public void setSeason(String val) { season = val; }
    public void setColor1(String val) { pcolor = val; }
    public void setColor2(String val) { scolor = val; }
    public void setPiclogo(String val) { piclogo = val; }
    public void setCoach(String val) { coach = val; }
    public void setCoachphone(String val) { phone = val; }
    public void setAssist(String val) { acoach = val; }
    public void setAssistphone(String val) { aphone = val; }
    public void setTeamtype(int val) { teamtype = val; }
    
	public int getTeamid() { return teamid; }
	public int getTeamtype() { return teamtype; }
	public String getUsername() { return username; }
	public String getPassword() { return pwd; }
	public String getAdminname() { return adminname; }
	public String getAdminpassword() { return adminpwd; }
	public String getTeamname() { return teamname; }
	public String getSeason() { return season; }
	public String getColor1() { return pcolor; }
	public String getColor2() { return scolor; }
	public String getPiclogo() { return piclogo; }
	public String getCoach() { return coach; }
	public String getCoachphone() { return phone; }
	public String getAssist() { return acoach; }
	public String getAssistphone() { return aphone; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    teamid = 0;
	    username = "";
	    pwd = "";
	    adminname = "";
	    adminpwd = "";
	    teamname = "";
	    season = "";
	    pcolor = "";
	    scolor = "";
	    piclogo = "";
	    coach = "";
	    acoach = "";
	    phone = "";
	    aphone = "";
	    teamtype = -1;
	    typelist = LkpteamtypePeer.getTypesList();
	}
	
	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    if (isEmptyString(username)) {
			messages.add("username", new ActionMessage("name.required"));
		}
	    if (isEmptyString(pwd)) {
			messages.add("password", new ActionMessage("password.required"));
		}
	    if (isEmptyString(teamname)) {
			messages.add("teamname", new ActionMessage("teamname.required"));
		}
		
		return messages;
	}

}