package com.sns.scout.form;

import java.util.Date;

import javax.servlet.http.*;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.sns.scout.managers.Task;
import com.sns.scout.managers.Project;
import com.sns.scout.managers.Users;
import com.sns.scout.managers.Groups;
import com.sns.scout.managers.ProjectPeer;
import com.sns.scout.managers.UsersPeer;
import com.sns.scout.managers.GroupsPeer;
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
 * Created on Jul 25, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Task form 
 *
 */
public class TaskForm extends BaseForm {
    private int taskid;
    private int projectid;
    private int userid;
    private int groupid;
    private String summary = "";
    private String detail = "";
    private int status = 0;
    private int tasktype = 0;
    private Date createdate;
    private Date estStart;
    private Date estEnd;
    private int estMeasure;
    private int estAmount;
    private int estId;
    private int estPriority;
    private Date actStart;
    private Date actEnd;
    private int actMeasure;
    private int actAmount;
    private int actId;
    private int actPriority;
    
    private Lkptype[] tasklist;
    private Lkpstatus[] statuses;
    private Lkppriority[] prioritylist;
    private Project[] projectlist;
    private Users[] userlist;
    private Groups[] grouplist;
    private Lkpmeasure[] measurelist;

    public void setId(int val) { taskid = val; }
    public void setProject(int val) { projectid = val; }
    public void setUser(int val) { userid = val; }
    public void setGroup(int val) { groupid = val; }
    public void setCreatedate(String val) { createdate = Utility.StrToDateTime(val); }
	public void setSummary(String val) { summary = val; }
	public void setDetail(String val) { detail = val; }
    public void setTasktype(int val) { tasktype = val; }
    public void setTaskstatus(int val) { status = val; }

    public void setEstid(int val) { estId = val; }
    public void setEststart(String val) { estStart = Utility.StrToDateTime(val); }
    public void setEstend(String val) { estEnd = Utility.StrToDateTime(val); }
    public void setEstmeasure(int val) { estMeasure = val; }
    public void setEstamount(int val) { estAmount = val; }
    public void setEstpriority(int val) { estPriority = val; }
    public void setActid(int val) { actId = val; }
    public void setActstart(String val) { actStart = Utility.StrToDateTime(val); }
    public void setActend(String val) { actStart = Utility.StrToDateTime(val); }
    public void setActmeasure(int val) { actMeasure = val; }
    public void setActamount(int val) { actAmount = val; }
    public void setActpriority(int val) { actPriority = val; }

	public int getId() { return taskid; }
	public int getProject() { return projectid; }
	public int getUser() { return userid; }
	public int getGroup() { return groupid; }
	public String getCreatedate() { return Utility.DateTimeToStr(createdate); }
	public String getSummary() { return summary; }
	public String getDetail() { return detail; }
	public int getTasktype() { return tasktype; }
	public int getTaskstatus() { return status; }
	
    public int getEstid() { return estId; }
    public String getEststart() { return Utility.DateTimeToStr(estStart); }
    public String getEstend() { return Utility.DateTimeToStr(estEnd); }
    public int getEstmeasure() { return estMeasure; }
    public int getEstamount() { return estAmount; }
    public int getEstpriority() { return estPriority; }
    public int getActid() { return actId; }
    public String getActstart() { return Utility.DateTimeToStr(actStart); }
    public String getActend() { return Utility.DateTimeToStr(actEnd); }
    public int getActmeasure() { return actMeasure; }
    public int getActamount() { return actAmount; }
    public int getActpriority() { return actPriority; }

	/* Values for gui lists */
	public void setTypelist(Lkptype[] val) { tasklist = val; }
	public Lkptype[] getTypelist() { return tasklist; }
	public void setStatuslist(Lkpstatus[] val) { statuses = val; }
	public Lkpstatus[] getStatuslist() { return statuses; }
	public void setMeasurelist(Lkpmeasure[] val) { measurelist = val; }
	public Lkpmeasure[] getMeasurelist() { return measurelist; }
	public void setPrioritylist(Lkppriority[] val) { prioritylist = val; }
	public Lkppriority[] getPrioritylist() { return prioritylist; }
	public void setProjectlist(Project[] val) { projectlist = val; }
	public Project[] getProjectlist() { return projectlist; }
	public void setUserlist(Users[] val) { userlist = val; }
	public Users[] getUserlist() { return userlist; }
	public void setGrouplist(Groups[] val) { grouplist = val; }
	public Groups[] getGrouplist() { return grouplist; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    taskid = -1;
	    Project theProject = (Project) request.getSession().getAttribute("curProject");
	    if (theProject != null) {
	    	projectid = theProject.getProjectid();
	    } else {
	    	projectid = -1;
	    }
    	groupid = 0;
    	userid = 0;
	    summary = "";
	    detail = "";
	    status = 0;
	    tasktype = 0;
	    createdate = new Date();
	    
	    estStart = new Date();
	    estEnd = new Date();
	    estMeasure = 0;
	    estAmount = 0;
	    estId = -1;
	    estPriority = 0;
	    actStart = new Date();
	    actEnd = new Date();
	    actMeasure = 0;
	    actAmount = 0;
	    actId = -1;
	    actPriority = 0;
	    
	    tasklist = LkptypePeer.getTypesList();
	    statuses = LkpstatusPeer.getStatusList();
	    prioritylist = LkppriorityPeer.getPriorityList();
	    measurelist = LkpmeasurePeer.getMeasureList();
	    projectlist = ProjectPeer.getProjectList();
	    userlist = UsersPeer.getUsersList();
	    grouplist = GroupsPeer.getGroupsList();
	}
}