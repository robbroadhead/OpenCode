package com.sns.teammgr.form;

import java.util.Date;

import javax.servlet.http.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import com.sns.util.Utility;

/*
 * Created on Aug 11, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Comments form. 
 *
 */
public class CommentsForm extends BaseForm {
	
    private int commentid;
    private int gameid;
    private String msg;

    public void setCommentid(int val) { commentid = val; }
    public void setGameid(int val) { gameid = val; }
    public void setMessage(String val) { msg = val; }
    
	public int getCommentid() { return commentid; }
	public int getGameid() { return gameid; }
	public String getMessage() { return msg; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    gameid = 0;
	    commentid = 0;
	    msg = "";
	}
	
	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    if (isEmptyString(msg)) {
			messages.add("msg", new ActionMessage("msg.required"));
		}
		
		return messages;
	}

}