package com.hungrymutt.form;

import javax.servlet.http.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import com.sns.util.Utility;
import java.util.Date;

/*
 * Created on Jan 7, 2004
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2004 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Recipe form. 
 *
 */
public class RateForm extends BaseForm{
	private int ratingid;
	private int recipeid;
	private int rating;
	private String ipaddress;
	private String dateRated;
	
	/* Values we want to capture */
	public void setRecipeid(int val) { recipeid = val; }
	public int getRecipeid() { return recipeid; }
	public void setRatingid(int val) { ratingid = val; }
	public int getRatingid() { return ratingid; }
	public void setRating(int val) { rating = val; }
	public int getRating() { return rating; }
	public void setIpaddress(String val) { ipaddress = val; }
	public String getIpaddress() { return ipaddress; }
	public void setDaterated(String val) { dateRated = val; }
	public String getDaterated() { return dateRated; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		recipeid = 0;
		ratingid = 0;
		rating = 0;
		ipaddress = "000.000.000.000";
		dateRated = Utility.DateTimeToStr(new Date());
	}
	

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
	    if (isEmptyString(ipaddress)) {
			errors.add("ipaddress", new ActionError("recipe.ipaddress.required"));
		}
	    if (isEmptyString(dateRated)) {
			errors.add("daterated", new ActionError("recipe.daterated.required"));
		}

		return errors;
	}

}