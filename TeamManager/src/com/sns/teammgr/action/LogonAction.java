package com.sns.teammgr.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.torque.Torque;
import com.sns.teammgr.managers.TeamPeer;
import com.sns.teammgr.Constants;

/**
 * The controller class for the login form of the sns application
 */

public final class LogonAction extends Action {

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
		/*
		* This Action is executed either by calling
		*
		* /login.do  - when loading the initial page
		* - or -
		* /login.do?action=getAuth - whenever we post the form
		*
		*/

		if (!Torque.isInit()) {
			Torque.init(Constants.TorqueHome);
		}

		/*
		 * Validate the request parameters specified by the user
		 * Note: Basic field validation done in HelloForm.java
		 *       Business logic validation done in HelloAction.java
		 */
		ActionMessages errors = new ActionMessages();
		String login = (String) PropertyUtils.getSimpleProperty(form, "logon");
		String pwd = (String) PropertyUtils.getSimpleProperty(form, "password");

		/*
		 * Having received and validated the data submitted from the View,
		 * we now update the model
		 */
		boolean isAdmin = false;
		if (login == null ||  pwd == null || login.trim().length() < 1 || pwd.trim().length() < 1) {
			errors.add("username", new ActionMessage("login.username"));
			this.saveMessages(request,errors);
			return mapping.findForward("failure");
		} else {
			int theId = -2;
			
			// Check for admin login
			theId = TeamPeer.loginAdmin(login, pwd);
			
			// Check for norml login
			if (theId < 0) {
				theId = TeamPeer.loginUser(login, pwd);
			} else {
				isAdmin = true;
			}
			
			// Store the key values
			if (theId < 0) {
				errors.add("username", new ActionMessage("login.failed"));
				this.saveMessages(request,errors);
				return mapping.findForward("failure");
			}

			request.getSession().setAttribute( "teammgr.User", login);
			request.getSession().setAttribute( "teammgr.Userid", theId);
			request.getSession().setAttribute( "curId", theId);
		}


		// Remove the Form Bean - don't need to carry values forward
		request.removeAttribute(mapping.getAttribute());

		// Forward control to the specified success URI
		if (isAdmin) {
			request.getSession().setAttribute( "teammgr.Admin", "1");
			return mapping.findForward("admin");
		} else {
			request.getSession().setAttribute( "teammgr.Admin", "0");
			return mapping.findForward("success");
		}
	}
}