package com.sns.teammgr.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.torque.Torque;

/**
 * The controller class for the login form of the sns application
 */

public final class LogoutAction extends Action {

	/**
	 * Process the specified HTTP request, and create the corresponding HTTP
	 * response (or forward to another web component that will create it).
	 * Return an <code>ActionForward</code> instance describing where and how
	 * control should be forwarded, or <code>null</code> if the response has
	 * already been completed.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param actionForm
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * 
	 * @exception Exception
	 *                if business logic throws an exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * Not much needed here. Just clear out any session values that are
		 * created by a login or use of the application
		 */
		if (!Torque.isInit()) {
			try {
				Torque.init("/usr/conf/Teammgr.properties");
			} catch (Exception e) {
				System.out.println("Failed to initialize the properties");
			}
		}

		request.getSession().removeAttribute("teammgr.User");
		request.getSession().removeAttribute("teammgr.Userid");
		Torque.shutdown();
		request.getSession().invalidate();

		// Forward control to the specified success URI
		return mapping.findForward("success");

	}
}
