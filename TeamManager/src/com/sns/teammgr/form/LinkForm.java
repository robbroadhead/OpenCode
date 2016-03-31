package com.sns.teammgr.form;

import java.util.Date;

import javax.servlet.http.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.sns.teammgr.managers.Team;
import com.sns.util.Utility;

/*
 * Created on Aug 9, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Link form. 
 *
 */
public class LinkForm extends BaseForm {
	
    private int linkid;
    private int teamid;
    private String title;
    private String link;

    public void setLinkid(int val) { linkid = val; }
    public void setTeamid(int val) { teamid = val; }
    public void setTitle(String val) { title = val; }
    public void setLink(String val) { link = val; }
    
	public int getLinkid() { return linkid; }
	public int getTeamid() { return teamid; }
	public String getTitle() { return title; }
	public String getLink() { return link; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    linkid = 0;
		Team curTeam = (Team) request.getSession().getAttribute("team");
		if (curTeam != null) {
			teamid = curTeam.getTeamid();
		} else {
			teamid = 0;
		}
	    title = "";
	    link = "";
	}
	
	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    if (isEmptyString(link)) {
			messages.add("link", new ActionMessage("link.required"));
		}
	    if (isEmptyString(title)) {
			messages.add("title", new ActionMessage("title.required"));
		}
		
		return messages;
	}

}