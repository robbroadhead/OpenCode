package com.hungrymutt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.hungrymutt.form.RecipeForm;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import com.hungrymutt.managers.RecipesPeer;
import com.hungrymutt.managers.RatingsPeer;
import com.hungrymutt.managers.Recipes;


/**
 * The controller class for the Gamelog search action of the Referee application
 */

public final class SearchAction extends Action {

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
		/* This is just to allow us basically two functions. If we have a query parameter sent
		 * in then we will load the proper client data. If we have no parameter then assume we
		 * are saving data
		 */
		RecipeForm curForm = (RecipeForm) form;

		/* Validation is complete so we now perform the search */
		List results = RecipesPeer.basicSearch(curForm.getTitle());
		ArrayList retVal = new ArrayList();
		Iterator it = results.iterator();
		
		// Build the html string for each search item so we can display to the
		// user the search we have made.
		String resultString = "Results for search where title or body contains " + curForm.getTitle();
		request.getSession().setAttribute("resultString",resultString);
		
		// Now we walk through the results and build a html line to display the Client data.
		Recipes current = null;
		StringBuffer sb = null;
		while (it.hasNext()) {
			sb = new StringBuffer();
			current = (Recipes) it.next();
			sb.append("<td>" + current.getTitle() + "</td>");
			sb.append("<td>" + RatingsPeer.averageRating(current.getRecipeid()) + "</td>");
			sb.append("<td>" + current.getRecipecategory() + "</td>");
			sb.append("<td><a id='LinkRecipeRate' href='/HungryMutt/rateRecipe.do?id=" + current.getRecipeid() + "'>Rate</a>, <a id= 'LinkRecipeView' href='/HungryMutt/viewRecipe.do?id=" + current.getRecipeid() + "'>View</a></td>");
			retVal.add(sb.toString());										
		}

		// Remove the Form Bean - don't need to carry values forward 
		request.removeAttribute(mapping.getAttribute());

		// Store the results so the form can find them		
		request.getSession().setAttribute("resultList",retVal);

		// Assume success if we have reached this point
		return mapping.findForward("success");
	}
}
