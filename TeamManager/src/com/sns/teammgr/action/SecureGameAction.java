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
import com.sns.teammgr.form.GameForm;
import com.sns.teammgr.managers.Game;
import com.sns.teammgr.managers.GamePeer;
import com.sns.util.Utility;
import org.apache.torque.Torque;

/**
 * The controller class for the project form.
 */

public final class SecureGameAction extends Action {

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
			GameForm cf = (GameForm) form;
			Game cli = null;

			Criteria crit = new Criteria();
			try {
				crit.add(GamePeer.GAMEID,Integer.parseInt(parm));
				crit.setAll();
				List result = GamePeer.doSelect(crit);

				if (result.size() < 1) {
					ActionErrors errors = new ActionErrors();
					saveMessages(request,errors);
					return mapping.findForward("success");
				}
				cli = (Game) result.get(0);
				cf.setBottomline(cli.getBottomline());
				cf.setGameid(cli.getGameid());
				cf.setTeamid(cli.getTeamid());
				cf.setHighlight(cli.getHighlight());
				cf.setOpponent(cli.getOpponent());
				cf.setOvertime(cli.getOvertime());
				cf.setPeriod1(cli.getPeriod1());
				cf.setPeriod2(cli.getPeriod2());
				cf.setPeriod3(cli.getPeriod3());
				cf.setResult(cli.getResult());
				cf.setScorethem(cli.getScorethem());
				cf.setScoreus(cli.getScoreus());
				cf.setStar1(cli.getStar1());
				cf.setStar2(cli.getStar2());
				cf.setStar3(cli.getStar3());
				cf.setSummary(cli.getSummary());
				request.getSession().setAttribute("game", cli);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Forward control to the specified success URI
		return mapping.findForward("success");
	}
}
