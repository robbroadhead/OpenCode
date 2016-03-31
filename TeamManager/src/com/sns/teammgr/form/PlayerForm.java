package com.sns.teammgr.form;

import java.util.Date;

import javax.servlet.http.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.sns.teammgr.managers.Lkpteamtype;
import com.sns.teammgr.managers.Lkpposition;
import com.sns.teammgr.managers.LkppositionPeer;
import com.sns.teammgr.managers.Team;
import com.sns.util.Utility;

/*
 * Created on Aug 9, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the form bean for the Player form. 
 *
 */
public class PlayerForm extends BaseForm {
	
    private int playerid;
    private int teamid;
    private int jerseynum;
    private int position;
    private String firstname = "";
    private String lastname = "";
    private String nickname = "";
    private String email = "";
    private String phone = "";
    private String phone2 = "";
    private String parentnames = "";

    private Lkpposition[] typelist;
    
	/* Values for gui lists */
	public void setPoslist(Lkpposition[] val) { typelist = val; }
	public Lkpposition[] getPoslist() { return typelist; }

	public void setPlayerid(int val) { playerid = val; }
    public void setTeamid(int val) { teamid = val; }
    public void setJersey(int val) { jerseynum = val; }
    public void setPosition(int val) { position = val; }
    public void setFirstname(String val) { firstname = val; }
    public void setLastname(String val) { lastname = val; }
    public void setNickname(String val) { nickname = val; }
    public void setEmail(String val) { email = val; }
    public void setPhone(String val) { phone = val; }
    public void setPhone2(String val) { phone2 = val; }
    public void setParentnames(String val) { parentnames = val; }
    
	public int getPlayerid() { return playerid; }
	public int getTeamid() { return teamid; }
	public int getJersey() { return jerseynum; }
	public int getPosition() { return position; }
	public String getFirstname() { return firstname; }
	public String getLastname() { return lastname; }
	public String getNickname() { return nickname; }
	public String getEmail() { return email; }
	public String getPhone() { return phone; }
	public String getPhone2() { return phone2; }
	public String getParentnames() { return parentnames; }

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    playerid = 0;
		Team curTeam = (Team) request.getSession().getAttribute("team");
		if (curTeam != null) {
			teamid = curTeam.getTeamid();
		} else {
			teamid = 0;
		}
	    jerseynum = 0;
	    firstname = "";
	    nickname = "";
	    lastname = "";
	    phone = "";
	    phone2 = "";
	    email = "";
	    position = 0;
	    parentnames = "";

	    typelist = LkppositionPeer.getPositionList();
	}
	
	public ActionMessages validateForm(ActionMapping mapping, HttpServletRequest request) {
	    ActionMessages messages = new ActionMessages();
	    if (isEmptyString(lastname)) {
			messages.add("lastname", new ActionMessage("lastname.required"));
		}
		
		return messages;
	}
}