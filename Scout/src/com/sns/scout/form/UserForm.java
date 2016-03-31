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
public class UserForm extends BaseForm {
    private String firstname = "";
    private String lastname = "";
    private int userid;
    private int externalid;
    private String displayname = "";
    private String detail = "";
    private Date createdate;
    
    public void setId(int val) { userid = val; }
    public void setExternal(int val) { externalid = val; }
    public void setCreatedate(String val) { createdate = Utility.StrToDateTime(val); }
	public void setDisplayname(String val) { displayname = val; }
	public void setDetail(String val) { detail = val; }
	public void setFname(String val) { firstname = val; }
	public void setLname(String val) { lastname = val; }


	public int getId() { return userid; }
	public int getExternal() { return externalid; }
	public String getCreatedate() { return Utility.DateTimeToStr(createdate); }
	public String getDetail() { return detail; }
	public String getDisplayname() { return displayname; }
	public String getFname() { return firstname; }
	public String getLname() { return lastname; }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    userid = -1;
	    externalid = -1;
	    detail = "";
	    firstname = "";
	    lastname = "";
	    createdate = new Date();	    
	}
	

	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    if (isEmptyString(firstname)) {
			messages.add("firstname", new ActionMessage("user.first.required"));
		}
	    if (isEmptyString(lastname)) {
			messages.add("lastname", new ActionMessage("user.last.required"));
		}
		
		return messages;
	}

}