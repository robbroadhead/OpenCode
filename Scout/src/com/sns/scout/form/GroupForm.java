package com.sns.scout.form;

import java.util.Date;

import javax.servlet.http.*;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.sns.scout.managers.Task;
import com.sns.scout.managers.Project;
import com.sns.scout.managers.ProjectPeer;
import com.sns.scout.managers.LkptypePeer;
import com.sns.scout.managers.LkpstatusPeer;
import com.sns.scout.managers.Lkptype;
import com.sns.scout.managers.Lkppriority;
import com.sns.scout.managers.Lkpmeasure;
import com.sns.scout.managers.LkppriorityPeer;
import com.sns.scout.managers.LkpmeasurePeer;
import com.sns.scout.managers.Lkpstatus;
import com.sns.util.Utility;

/*
 * Created on Mar 18, 2008
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2008 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the User form 
 *
 */
public class GroupForm extends BaseForm {

	private int groupid;
    private Date created;
    private String displayname = "";                                                                                  
    private String detail = "";
        
    public void setId(int val) { groupid = val; }
    public void setCreatedate(String val) { created = Utility.StrToDateTime(val); }
	public void setDisplayname(String val) { displayname = val; }
	public void setDetail(String val) { detail = val; }

	public int getId() { return groupid; }
	public String getCreatedate() { return Utility.DateTimeToStr(created); }
	public String getDetail() { return detail; }
	public String getDisplayname() { return displayname; }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    groupid = -1;
	    detail = "";
	    displayname = "";
	    created = new Date();	    
	}
	

	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    if (isEmptyString(displayname)) {
			messages.add("displayname", new ActionMessage("group.name.required"));
		}
		
		return messages;
	}

}