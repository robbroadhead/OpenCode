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
public class RecipeForm extends BaseForm{
	private int recipeid;
	private String title;
	private String submittedby;
	private String recipebody;
	private String category;
	
	private boolean visible;
	private String dateSubmitted;
	
	/* Values we want to capture */
	public void setRecipeid(int val) { recipeid = val; }
	public int getRecipeid() { return recipeid; }
	public void setTitle(String val) { title = val; }
	public String getTitle() { return title; }
	public void setSubmittedby(String val) { submittedby = val; }
	public String getSubmittedby() { return submittedby; }
	public void setRecipebody(String val) { recipebody = val; }
	public String getRecipebody() { return recipebody; }
	public void setCategory(String val) { category = val; }
	public String getCategory() { return category; }
	public void setDatesubmitted(String val) { dateSubmitted = val; }
	public String getDatesubmitted() { return dateSubmitted; }
	
	public void setIsvisible(boolean val) { visible = val; }
	public boolean getIsvisible() { return visible; }


	public void reset(ActionMapping mapping, HttpServletRequest request) {
		recipeid = 0;
		title = "";
		submittedby = "";
		recipebody = "";
		category = "";
		dateSubmitted = Utility.DateTimeToStr(new Date());
		visible=false;
	}
	

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
	    if (isEmptyString(title)) {
			errors.add("title", new ActionError("recipe.title.required"));
		}
	    if (isEmptyString(category)) {
			errors.add("category", new ActionError("recipe.category.required"));
		}
	    if (isEmptyString(submittedby)) {
			errors.add("submittedby", new ActionError("recipe.submittedby.required"));
		}
	    if (isEmptyString(dateSubmitted)) {
			errors.add("datesubmitted", new ActionError("recipe.datesubmitted.required"));
		}

		return errors;
	}

}