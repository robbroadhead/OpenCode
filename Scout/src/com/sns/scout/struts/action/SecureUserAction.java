package com.sns.scout.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionErrors;

import org.apache.torque.util.Criteria;
import java.util.List;
import com.sns.scout.form.UserForm;
import com.sns.scout.managers.Users;
import com.sns.scout.managers.UsersPeer;
import com.sns.util.Utility;
import org.apache.torque.Torque;


/**
 * The controller class for the project form.
 */

public final class SecureUserAction extends Action {

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
    	if (SecureAction.ValidateUser(request.getSession())==false) {
      		return mapping.findForward("failure");
    	}

		// The user is logged in so we need to look for a parameter on the query
		// string and populate the Client form if we have a valid clientid. 
		String parm = request.getParameter("id");
		if (parm != null) { 
			// Grab current project if sent a -1
			if (parm.equals("-1")) {
				parm = "" + ((Users) request.getSession().getAttribute("curUsers")).getUserid();
			}
			UserForm cf = (UserForm) form;
			Users cli = null;

			Criteria crit = new Criteria();
			try {
				crit.add(UsersPeer.USERID,Integer.parseInt(parm));
				crit.setAll();
				List result = UsersPeer.doSelect(crit);

				if (result.size() < 1) {
					ActionErrors errors = new ActionErrors();
					saveMessages(request,errors);
					return mapping.findForward("success");
				}
				cli = (Users) result.get(0);
				cf.setCreatedate(Utility.DateTimeToStr(cli.getCreated()));
				cf.setFname(cli.getFirstname());
				cf.setLname(cli.getLastname());
				cf.setDetail(cli.getDetail());
				cf.setDisplayname(cli.getDisplayname());
				cf.setExternal(cli.getExternalid());
				cf.setId(cli.getUserid());
				request.getSession().setAttribute("curUser", cli);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Forward control to the specified success URI
		return mapping.findForward("success");
	}
}
