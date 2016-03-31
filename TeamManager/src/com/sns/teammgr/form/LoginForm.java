package com.sns.teammgr.form;

import javax.servlet.http.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.sns.teammgr.form.BaseForm;

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
	

	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    if (isEmptyString(logon)) {
			messages.add("username", new ActionMessage("login.username.required"));
		}
		
		if (isEmptyString(password)) {
			messages.add("password", new ActionMessage("login.password.required"));
		} else if (password.length() < 6) {
			messages.add("password", new ActionMessage("login.password.minlength"));
		}
		
		return messages;
	}

}