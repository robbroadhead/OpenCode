package com.hungrymutt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import org.apache.torque.util.Criteria;
import java.util.List;
import com.hungrymutt.form.RecipeForm;
import com.hungrymutt.managers.Recipes;
import com.hungrymutt.managers.RecipesPeer;
import com.sns.util.Utility;

/**
 * The controller class for the recipe form of the HungryMutt application. This
 * takes an id, if given, and loads the proper recipe into the form.
 */

public final class RandomRecipeAction extends Action {

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

	    // Make sure we have initialized the Torque system
	    if (!org.apache.torque.Torque.isInit()) {
	    	try {
				org.apache.torque.Torque.init("/usr/conf/hungrymutt.properties");
	    	} catch (Exception e) {
	    		System.out.println("Failed to initialize the properties - First Try");
	    		e.printStackTrace();
	    	}
	    }

	    // We need to look for a parameter on the query string and populate the
		// Recipe form if we have a valid recipeid.
		RecipeForm rf = (RecipeForm) form;
		Recipes r = null;

		Criteria crit = new Criteria();
		try {
			int curId = RecipesPeer.getRandomRecipeId();
			if (curId < 0) {
				ActionErrors errors = new ActionErrors();
				errors.add("success", new ActionError("recipe.notfound"));
				saveErrors(request, errors);
				return mapping.findForward("success");
			}
			crit.add(RecipesPeer.RECIPEID, curId);
			crit.setAll();
			List result = RecipesPeer.doSelect(crit);

			if (result.size() < 1) {
				ActionErrors errors = new ActionErrors();
				errors.add("success", new ActionError("recipe.notfound"));
				saveErrors(request, errors);
				return mapping.findForward("success");
			}

			r = (Recipes) result.get(0);
			rf.setCategory(r.getRecipecategory());
			rf.setDatesubmitted(Utility.DateTimeToStr(r.getDatesubmitted()));
			rf.setIsvisible(r.getVisible());
			rf.setRecipebody(new String(r.getRecipebody()));
			rf.setRecipeid(r.getRecipeid());
			rf.setSubmittedby(r.getSubmittedby());
			rf.setTitle(r.getTitle());
			request.getSession().setAttribute("mutt.recipeid",
					r.getRecipeid() + "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Forward control to the specified success URI
		return mapping.findForward("success");
	}
}