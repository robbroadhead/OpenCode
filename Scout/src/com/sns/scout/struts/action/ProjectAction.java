package com.sns.scout.struts.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import com.sns.scout.form.ProjectForm;
import com.sns.scout.managers.Project;
import com.sns.scout.managers.Userproject;
import com.sns.scout.managers.Groupproject;
import com.sns.scout.managers.UserprojectPeer;
import com.sns.scout.managers.GroupprojectPeer;
import com.sns.util.Utility;

/*
 * Created on Jul 23, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the editing action code for a Project. 
 *
 */
public final class ProjectAction extends Action {

	/**
	 * Process the specified HTTP request, and create the corresponding HTTP
	 * response (or forward to another web component that will create it).
	 * Return an <code>ActionForward</code> instance describing where and how
	 * control should be forwarded, or <code>null</code> if the response has
	 * already been completed.
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param actionForm The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 *
	 * @exception Exception if business logic throws an exception
	 */
	public ActionForward execute(ActionMapping mapping,
										 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)
	throws Exception {
		ActionMessages errors = new ActionMessages();
		/* This is just to allow us basically two functions. If we have a query parameter sent
		 * in then we will load the proper client data. If we have no parameter then assume we
		 * are saving data
		 */
		ProjectForm curForm = (ProjectForm) form;
		Project val = new Project();

	    if (curForm.getName() == null || curForm.getName().trim().length() < 1) {
			errors.add("name", new ActionMessage("project.name.required"));
			this.saveMessages(request,errors);
			return mapping.findForward("failure");
		}

		/* Validation is complete so we now save the object */
		val.setProjectid(curForm.getId());
		val.setName(curForm.getName());
		val.setStartdate(Utility.StrToDateTime(curForm.getStartdate()));
		if (curForm.getCreatedate() == null || curForm.getCreatedate().trim().length() < 1) {
			curForm.setCreatedate(Utility.DateTimeToStr(new Date()));
		}
		val.setCreated(Utility.StrToDateTime(curForm.getCreatedate()));
		
		if (val.getProjectid() > 0) {
			val.setNew(false);
		} else {
			val.setCreated(new Date());
		}
		val.save();
		curForm.setId(val.getProjectid());
		Utility.LOG.info("Saved Project:" + val.getProjectid());
		
		// Add user or group links
		if (curForm.getUser() > 0) {
			// First see if we already have an assignment for this project.
			Userproject up = UserprojectPeer.mappingExists(val.getProjectid());
			
			if (up ==null) {
				up = new Userproject();
				up.setProjectid(curForm.getId());
				up.setNew(true);
			} else {
				up.setNew(false);
			}
			up.setUserid(curForm.getUser());
			up.save();
		}
		
		if (curForm.getGroup() > 0) {
			// First see if we already have an assignment for this project.			
			Groupproject up = GroupprojectPeer.mappingExists(val.getProjectid());
			
			if (up ==null) {
				up = new Groupproject();
				up.setProjectid(curForm.getId());
				up.setNew(true);
			} else {
				up.setNew(false);
			}
			up.setGroupid(curForm.getGroup());
			up.save();
		}
		
		// Remove the Form Bean - don't need to carry values forward 
		request.removeAttribute(mapping.getAttribute());
		request.getSession().setAttribute("curProject", val);

		// Forward control to the specified success URI
		ActionMessages Messages = new ActionMessages();
		Messages.add("success",new ActionMessage("general.save.success"));
		saveMessages(request,Messages);
		
		// Assume success if we have reached this point
		return mapping.findForward("success");
	}
}
