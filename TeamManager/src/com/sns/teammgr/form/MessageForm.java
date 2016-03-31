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
 * Description: This is the form bean for the Message form. 
 *
 */
public class MessageForm extends BaseForm {
	
    private int msgid;
    private int teamid;
    private String title;
    private String link;
    private String msg;
    private String expires;
          
    /**
     * The value for the expiredate field
     */
    private Date expiredate;

    public void setMsgid(int val) { msgid = val; }
    public void setTeamid(int val) { teamid = val; }
    public void setTitle(String val) { title = val; }
    public void setLink(String val) { link = val; }
    public void setMessage(String val) { msg = val; }
    public void setExpires(String val) { expires = val; }
    
	public int getMsgid() { return msgid; }
	public int getTeamid() { return teamid; }
	public String getTitle() { return title; }
	public String getLink() { return link; }
	public String getMessage() { return msg; }
	public String getExpires() { return expires; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		Team curTeam = (Team) request.getSession().getAttribute("team");
		if (curTeam != null) {
			teamid = curTeam.getTeamid();
		} else {
			teamid = 0;
		}
		
	    msgid = 0;
	    title = "";
	    link = "";
	    msg = "";
	    expires = Utility.DateTimeToStr(new Date());
	}
	
	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    if (isEmptyString(msg)) {
			messages.add("msg", new ActionMessage("msg.required"));
		}
	    if (isEmptyString(title)) {
			messages.add("title", new ActionMessage("title.required"));
		}
		
		return messages;
	}

}