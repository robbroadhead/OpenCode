package com.sns.teammgr.form;

import java.util.Date;

import javax.servlet.http.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.sns.teammgr.managers.Team;
import com.sns.util.Utility;

/*
 * Created on Aug 11, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Event form. 
 *
 */
public class EventForm extends BaseForm {
	
    private int eventid;
    private int teamid;
    private String title;
    private String link;
    private String msg;
    private Date startdate;
    private Date enddate;

    public void setEventid(int val) { eventid = val; }
    public void setTeamid(int val) { teamid = val; }
    public void setTitle(String val) { title = val; }
    public void setLink(String val) { link = val; }
    public void setMessage(String val) { msg = val; }
    public void setStartdate(String val) { startdate = Utility.StrToDateTime(val); }
    public void setEnddate(String val) { enddate = Utility.StrToDateTime(val); }
    
	public int getEventid() { return eventid; }
	public int getTeamid() { return teamid; }
	public String getTitle() { return title; }
	public String getLink() { return link; }
	public String getMessage() { return msg; }
	public String getStartdate() { return Utility.DateTimeToStr(startdate); }
	public String getEnddate() { return Utility.DateTimeToStr(enddate); }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    eventid = 0;
		Team curTeam = (Team) request.getSession().getAttribute("team");
		if (curTeam != null) {
			teamid = curTeam.getTeamid();
		} else {
			teamid = 0;
		}
	    title = "";
	    link = "";
	    msg = "";
	    startdate = new Date();
	    enddate = new Date();
	}
	
	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    if (isEmptyString(title)) {
			messages.add("title", new ActionMessage("title.required"));
		}
		
		return messages;
	}

}