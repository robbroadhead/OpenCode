package com.hungrymutt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import com.hungrymutt.managers.Ratings;
import com.hungrymutt.managers.RatingsPeer;
import com.hungrymutt.form.RateForm;
import com.sns.util.Utility;

/**
 * The controller class for the login form of the Referee application
 */

public final class SaveRatingAction extends Action {

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
		RateForm curForm = (RateForm) form;
		Ratings r = new Ratings();

		/* Make sure this IP address has not rated this recipe again. */
		if (RatingsPeer.hasEnteredRating(curForm.getIpaddress(),curForm.getRatingid())) {
			ActionErrors errors = new ActionErrors();
			errors.add("success",new ActionError("rating.onceonly"));
			saveErrors(request,errors);
			return mapping.findForward("failure");
		}
		
		/* Validation is complete so we now save the object */
		r.setDaterated((Utility.StrToDateTime(curForm.getDaterated())));
		r.setIpaddress(request.getRemoteAddr());
		r.setRating(curForm.getRating());
		r.setRecipeid(curForm.getRecipeid());
		r.setRatingid(curForm.getRatingid());
		
		/* These values may need to be added to the form, but we can assume these for now */
		if (r.getRatingid() > 0) {
			r.setNew(false);
		}
		r.save();
		
		// Remove the Form Bean - don't need to carry values forward 
		request.removeAttribute(mapping.getAttribute());

		// Forward control to the specified success URI
		ActionErrors errors = new ActionErrors();
		errors.add("success",new ActionError("general.save.success"));
		saveErrors(request,errors);
		
		// Assume success if we have reached this point
		return mapping.findForward("success");
	}
}
