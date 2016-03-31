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
import com.sns.scout.form.ProjectForm;
import com.sns.scout.managers.Project;
import com.sns.scout.managers.ProjectPeer;
import com.sns.scout.managers.Userproject;
import com.sns.scout.managers.UserprojectPeer;
import com.sns.scout.managers.Groupproject;
import com.sns.scout.managers.GroupprojectPeer;
import com.sns.util.Utility;
import org.apache.torque.Torque;


/**
 * The controller class for the project form.
 */

public final class SecureProjectAction extends Action {

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
				parm = "" + ((Project) request.getSession().getAttribute("curProject")).getProjectid();
			}
			ProjectForm cf = (ProjectForm) form;
			Project cli = null;

			Criteria crit = new Criteria();
			try {
				crit.add(ProjectPeer.PROJECTID,Integer.parseInt(parm));
				crit.setAll();
				List result = ProjectPeer.doSelect(crit);

				if (result.size() < 1) {
					ActionErrors errors = new ActionErrors();
					saveMessages(request,errors);
					return mapping.findForward("success");
				}
				cli = (Project) result.get(0);
				cf.setCreatedate(Utility.DateTimeToStr(cli.getCreated()));
				cf.setName(cli.getName());
				cf.setId(cli.getProjectid());
				cf.setStartdate(Utility.DateTimeToStr(cli.getStartdate()));
				
				// Grab the group/user stuff
				Userproject up = UserprojectPeer.mappingExists(cli.getProjectid());
				if (up != null) {
					cf.setUser(up.getUserid());
				}
				
				Groupproject gp = GroupprojectPeer.mappingExists(cli.getProjectid());
				if (gp != null) {
					cf.setGroup(gp.getGroupid());
				}

				request.getSession().setAttribute("curProject", cli);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Forward control to the specified success URI
		return mapping.findForward("success");
	}
}
