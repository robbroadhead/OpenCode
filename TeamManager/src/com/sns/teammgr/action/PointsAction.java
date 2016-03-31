package com.sns.teammgr.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import com.sns.teammgr.form.PointsForm;
import com.sns.teammgr.managers.Playerpoints;
import com.sns.teammgr.managers.PlayerpointsPeer;
import com.sns.teammgr.managers.TeammsgPeer;
import com.sns.util.Utility;
import org.apache.torque.util.Criteria;

/*
 * Created on Aug 13, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the editing action code for a Event. 
 *
 */
public final class PointsAction extends Action {

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
		/* This is just to allow us basically two functions. If we have a query parameter sent
		 * in then we will load the proper client data. If we have no parameter then assume we
		 * are saving data
		 */
		PointsForm curForm = (PointsForm) form;
		Playerpoints val = new Playerpoints();

		val.setGameid(curForm.getGameid());
		val.setPlayerid(curForm.getPlayerid());
		val.setPointtype(curForm.getPoint());
		/* Validation is complete so we now save the object */
		try {
			PlayerpointsPeer.doDelete(val);
		} catch(Exception e) {
			// eat the exception and continue.
		}
		val.setNew(true);
		val.save();
		
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
