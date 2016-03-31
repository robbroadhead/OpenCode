package com.sns.teammgr.action;

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
import com.sns.teammgr.form.TeamForm;
import com.sns.teammgr.managers.Team;
import com.sns.teammgr.managers.TeamPeer;
import com.sns.teammgr.Constants;
import com.sns.util.Utility;
import org.apache.torque.Torque;

/**
 * The controller class for the project form.
 */

public final class SecureTeamAction extends Action {

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
		if (SecureAction.ValidateUser(request.getSession()) == false) {
			return mapping.findForward("failure");
		}

		// The user is logged in so we need to look for a parameter on the query
		// string and populate the Client form if we have a valid clientid.
		String parm = request.getParameter("id");

		// Try the current id if no parm was passed.
		if (parm == null) {
			if (request.getSession().getAttribute("curId") != null) {
				parm = ((Integer) request.getSession().getAttribute("curId"))
						.toString();
				request.getSession().setAttribute("curId", null);
			}
		}

		if (parm != null) {
			// Use current if we got a -1 id.
			if (parm.equals("-1")) {
				parm = "" + ((Team) request.getSession().getAttribute("team")).getTeamid();
			}
			TeamForm cf = (TeamForm) form;
			Team cli = null;

			Criteria crit = new Criteria();
			try {
				crit.add(TeamPeer.TEAMID, Integer.parseInt(parm));
				crit.setAll();
				List result = TeamPeer.doSelect(crit);

				if (result.size() < 1) {
					ActionErrors errors = new ActionErrors();
					saveMessages(request, errors);
					return mapping.findForward("success");
				}
				cli = (Team) result.get(0);
				cf.setAdminname(cli.getAdminname());
				cf.setAdminpassword(cli.getAdminpwd());
				cf.setColor1(cli.getPrimarycolor());
				cf.setColor2(cli.getSecondarycolor());
				cf.setPassword(cli.getPwd());
				cf.setPiclogo(cli.getPiclogo());
				cf.setCoach(cli.getCoach());
				cf.setCoachphone(cli.getPhone());
				cf.setAssist(cli.getAcoach());
				cf.setAssistphone(cli.getAphone());
				cf.setSeason(cli.getSeason());
				cf.setTeamid(cli.getTeamid());
				cf.setTeamname(cli.getTeamname());
				Constants.LOG.info("Loading team:" + cf.getTeamname());
				cf.setTeamtype(cli.getTeamtype());
				cf.setUsername(cli.getUsername());
				request.getSession().setAttribute("team", cli);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Forward control to the specified success URI
		return mapping.findForward("success");
	}
}
