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
import com.sns.scout.form.TaskForm;
import com.sns.scout.managers.Grouptask;
import com.sns.scout.managers.GrouptaskPeer;
import com.sns.scout.managers.Task;
import com.sns.scout.managers.Taskactual;
import com.sns.scout.managers.Taskestimate;
import com.sns.scout.managers.Usertask;
import com.sns.scout.managers.UsertaskPeer;
import com.sns.util.Utility;

/*
 * Created on Jul 26, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the editing action code for a Task. 
 *
 */
public final class TaskAction extends Action {

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
		TaskForm curForm = (TaskForm) form;
		Task val = new Task();

	    if (curForm.getSummary() == null || curForm.getSummary().trim().length() < 1) {
			errors.add("summary", new ActionMessage("task.summary.required"));
			this.saveMessages(request,errors);
			return mapping.findForward("failure");
		}
	    if (curForm.getDetail() == null || curForm.getDetail().trim().length() < 1) {
			errors.add("detail", new ActionMessage("task.detail.required"));
			this.saveMessages(request,errors);
			return mapping.findForward("failure");
		}

		/* Validation is complete so we now save the object */
		val.setTaskid(curForm.getId());
		val.setSummary(curForm.getSummary());
		val.setProjectid(curForm.getProject());
		val.setDetail(curForm.getDetail());
		val.setTypeid(curForm.getTasktype());
		val.setStatusid(curForm.getTaskstatus());
		if (curForm.getCreatedate() == null || curForm.getCreatedate().trim().length() < 1) {
			curForm.setCreatedate(Utility.DateTimeToStr(new Date()));
		}
		val.setCreated(Utility.StrToDateTime(curForm.getCreatedate()));
		
		if (val.getTaskid() > 0) {
			val.setNew(false);
		} else {
			val.setCreated(new Date());
		}
		val.save();
		curForm.setId(val.getTaskid());
		System.out.println("Saved Task:" + val.getTaskid());
		
		// Now add in the estimate information
		Taskactual actual = new Taskactual();
		Taskestimate estimate = new Taskestimate();
		
		actual.setEnddate(Utility.StrToDateTime(curForm.getActend()));
		actual.setEstimateid(curForm.getActid());
		actual.setMeasureid(curForm.getActmeasure());
		actual.setPriorityid(curForm.getActpriority());
		actual.setStartdate(Utility.StrToDateTime(curForm.getActstart()));
		actual.setTaskid(val.getTaskid());
		actual.setTimevalue(curForm.getActamount());
		if (actual.getEstimateid() > 0) {
			actual.setNew(false);
		}
		actual.save();
		
		estimate.setEnddate(Utility.StrToDateTime(curForm.getEstend()));
		estimate.setEstimateid(curForm.getEstid());
		estimate.setMeasureid(curForm.getEstmeasure());
		estimate.setPriorityid(curForm.getEstpriority());
		estimate.setStartdate(Utility.StrToDateTime(curForm.getEststart()));
		estimate.setTaskid(val.getTaskid());
		estimate.setTimevalue(curForm.getEstamount());
		if (estimate.getEstimateid() > 0) {
			estimate.setNew(false);
		}
		estimate.save();

		// Add user or group links
		if (curForm.getUser() > 0) {
			// First see if we already have an assignment for this task.
			
			Usertask up = UsertaskPeer.mappingExists(val.getTaskid());
			
			if (up ==null) {
				up = new Usertask();
				up.setTaskid(curForm.getId());
				up.setNew(true);
			} else {
				up.setNew(false);
			}
			up.setUserid(curForm.getUser());
			up.save();
		}
		
		if (curForm.getGroup() > 0) {
			// First see if we already have an assignment for this task.
			
			Grouptask up = GrouptaskPeer.mappingExists(val.getTaskid());
			
			if (up ==null) {
				up = new Grouptask();
				up.setTaskid(curForm.getId());
				up.setNew(true);
			} else {
				up.setNew(false);
			}
			up.setGroupid(curForm.getGroup());
			up.save();
		}

		// Remove the Form Bean - don't need to carry values forward 
		request.removeAttribute(mapping.getAttribute());

		// Forward control to the specified success URI
		ActionMessages Messages = new ActionMessages();
		Messages.add("success",new ActionMessage("general.save.success"));
		saveMessages(request,Messages);
		
		// Assume success if we have reached this point
		return mapping.findForward("success");
	}
}
