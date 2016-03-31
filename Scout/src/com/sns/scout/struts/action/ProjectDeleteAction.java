package com.sns.scout.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.torque.Torque;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import java.sql.*;
import java.net.URLDecoder;

/**
 * The controller class for the Project delete action
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

public final class ProjectDeleteAction extends Action {

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
			Torque.init("/home/rbhead/usr/conf/Scout.properties");
		}

		// Make sure the user has logged in.
	    if (request.getSession().getAttribute("scout.User") == null) {
			return mapping.findForward("failure");
	      }

		/* Criteria method complains about lack of keys so use sql to delete. */
		try {
			PreparedStatement stmt;
			Connection con = Torque.getConnection();
			stmt = con.prepareStatement("Delete from Project where ProjectID = ?");
			stmt.setInt(1,Integer.parseInt(request.getParameter("pid")));
			stmt.execute();
			stmt.close();
			Torque.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
