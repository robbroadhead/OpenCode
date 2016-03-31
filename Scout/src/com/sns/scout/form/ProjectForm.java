package com.sns.scout.form;

import java.util.Date;

import javax.servlet.http.*;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.sns.scout.managers.Groups;
import com.sns.scout.managers.GroupsPeer;
import com.sns.scout.managers.Lkpmeasure;
import com.sns.scout.managers.Lkppriority;
import com.sns.scout.managers.Lkpstatus;
import com.sns.scout.managers.Lkptype;
import com.sns.scout.managers.Project;
import com.sns.scout.managers.Users;
import com.sns.scout.managers.UsersPeer;
import com.sns.util.Utility;

/*
 * Created on Jul 27, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Project form (.jsp) 
 *
 */
public class ProjectForm extends BaseForm {
    private int projectid;
    private int userid;
    private int groupid;
    private String name = "";
    private Date startdate;
    private Date enddate;

    private Users[] userlist;
    private Groups[] grouplist;

    public void setId(int val) { projectid = val; }
    public void setUser(int val) { userid = val; }
    public void setGroup(int val) { groupid = val; }
    public void setStartdate(String val) { startdate = Utility.StrToDateTime(val); }
    public void setCreatedate(String val) { enddate = Utility.StrToDateTime(val); }
	public void setName(String val) { name = val; }
	
	public int getId() { return projectid; }
	public int getUser() { return userid; }
	public int getGroup() { return groupid; }
	public String getName() { return name; }
	public String getStartdate() { return Utility.DateTimeToStr(startdate); }
	public String getCreatedate() { return Utility.DateTimeToStr(enddate); }

	/* Values for gui lists */
	public void setUserlist(Users[] val) { userlist = val; }
	public Users[] getUserlist() { return userlist; }
	public void setGrouplist(Groups[] val) { grouplist = val; }
	public Groups[] getGrouplist() { return grouplist; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		name = "";
		startdate = new Date();
		enddate = new Date();
		projectid = -1;
	    userlist = UsersPeer.getUsersList();
	    grouplist = GroupsPeer.getGroupsList();
	}
	
	public ActionErrors  validate(ActionMapping mapping, HttpServletRequest request) {
	    ActionErrors messages = new ActionErrors();
		
		return messages;
	}
}