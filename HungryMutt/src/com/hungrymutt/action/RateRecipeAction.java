package com.hungrymutt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.hungrymutt.form.RateForm;

/**
 * The controller class for the recipe form of the HungryMutt application.
 * This takes an id, if given, and loads the proper recipe into the form.
 */

public final class RateRecipeAction extends Action {

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

		// We need to look for a parameter on the query string and populate the
		// Recipe form if we have a valid recipeid. 
		String parm = request.getParameter("id");
		if (parm != null) { 
			RateForm rf = (RateForm) form;
			rf.setRecipeid(Integer.parseInt(parm));
		}

		// Forward control to the specified success URI
		return mapping.findForward("success");
	}
}
