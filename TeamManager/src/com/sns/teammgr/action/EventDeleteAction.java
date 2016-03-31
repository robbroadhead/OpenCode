package com.sns.teammgr.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.torque.Torque;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.sns.teammgr.managers.EventPeer;

/**
 * The controller class for the Link delete action
 *
 *******************************************************************
 * Copyright Notice:
 *   Copyright © 2007 Sleepless Nights Software, Inc.
 *   All Rights Reserved.
 *
 *   This computer program is protected by copyright law and
 *   international treaties.  Unauthorized use or distribution of
 *   this program, or any portion of it is strictly prohibited.
 *   Violation may result in severe civil or criminal penalties.
 *******************************************************************
 *
 */

public final class EventDeleteAction extends Action {

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
		// Basic cleanup to keep the session as thin as possible
		request.getSession().removeAttribute("curlist");

		if (!Torque.isInit()) {
			Torque.init("/usr/conf/Teammgr.properties");
		}

		// Make sure the user has logged in.
	    if (request.getSession().getAttribute("teammgr.User") == null) {
			return mapping.findForward("failure");
	      }

	    EventPeer.delete(request.getParameter("id"));
		// Remove the Form Bean - don't need to carry values forward
		request.removeAttribute(mapping.getAttribute());

		// Forward control to the specified success URI
		ActionMessages msgs = new ActionMessages();
		msgs.add("success",new ActionMessage("general.delete.success"));
		saveMessages(request,msgs);

		// Assume success if we have reached this point
		return mapping.findForward("success");
	}
}
