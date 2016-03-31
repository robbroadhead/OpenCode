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
public class SearchForm extends BaseForm{
	private String keyword;
	private String category;
	private double rating;
	
	/* Values we want to capture */
	public void setRating(double val) { rating = val; }
	public double getRating() { return rating; }
	public void setKeyword(String val) { keyword = val; }
	public String getKeyword() { return keyword; }
	public void setCategory(String val) { category = val; }
	public String getCategory() { return category; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		rating = 0.0;
		category="";
		keyword="";
	}
}