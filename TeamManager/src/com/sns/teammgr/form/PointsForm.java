package com.sns.teammgr.form;

import java.util.Date;

import javax.servlet.http.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import com.sns.util.Utility;

/*
 * Created on Aug 9, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Points form. 
 *
 */
public class PointsForm extends BaseForm {
	
    private int playerid;
    private int gameid;
    private int pointtype;

    public void setPlayerid(int val) { playerid = val; }
    public void setGameid(int val) { playerid = val; }
    public void setPoint(int val) { playerid = val; }
    
	public int getPlayerid() { return playerid; }
	public int getGameid() { return gameid; }
	public int getPoint() { return pointtype; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    playerid = 0;
	    gameid = 0;
	    pointtype = 0;
	}
	
	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    
	    // No need to verify any values for this.
	    
		return messages;
	}

}