package com.sns.scout.form;

import javax.servlet.http.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import com.sns.util.Utility;

/*
 * Created on Jan 7, 2004
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2004 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Login form (Welcome.jsp) 
 *
 */
public class LoginForm extends BaseForm {
	private String logon;
	private String password;
	
	public void setLogon(String val) { logon = val; }
	public void setPassword(String val) { password = val; }
	public String getLogon() { return logon; }
	public String getPassword() { return password; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		logon = "";
		password = "";
	}
	

	public ActionErrors  validate(ActionMapping mapping, HttpServletRequest request) {
	    ActionErrors messages = new ActionErrors();
		
		return messages;
	}
}