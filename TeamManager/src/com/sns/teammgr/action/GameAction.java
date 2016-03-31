package com.sns.teammgr.action;

import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import com.sns.teammgr.form.GameForm;
import com.sns.teammgr.managers.Game;
import com.sns.teammgr.managers.PlayerPeer;
import com.sns.teammgr.managers.Playerpoints;
import com.sns.util.Utility;

/*
 * Created on Aug 13, 2007
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 * 
 * Copyright 2007 RB Consulting, Inc. All Rights Reserved
 * 
 * Description: This is the editing action code for a Game. 
 *
 */
public final class GameAction extends Action {

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
		GameForm curForm = (GameForm) form;
		Game val = new Game();

		/* Validation is complete so we now save the object */
		val.setGameid(curForm.getGameid());
		val.setBottomline(curForm.getBottomline());
		val.setHighlight(curForm.getHighlight());
		val.setOpponent(curForm.getOpponent());
		val.setOvertime(curForm.getOvertime());
		val.setPeriod1(curForm.getPeriod1());
		val.setPeriod2(curForm.getPeriod2());
		val.setPeriod3(curForm.getPeriod3());
		val.setResult(curForm.getResult());
		val.setScorethem(curForm.getScorethem());
		val.setScoreus(curForm.getScoreus());
		val.setStar1(curForm.getStar1());
		val.setStar2(curForm.getStar2());
		val.setStar3(curForm.getStar3());
		val.setSummary(curForm.getSummary());
		val.setTeamid(curForm.getTeamid());
		
		if (val.getGameid() > 0) {
			val.setNew(false);
		}
		val.save();
		
		// Now pull out the scoring information
		String scoreString = curForm.getScoring();
		StringTokenizer scores = new StringTokenizer(scoreString,",");
		while (scores.hasMoreTokens()) {
			Playerpoints pp = new Playerpoints();
			StringTokenizer goal = new StringTokenizer(scores.nextToken(),"-");
			
			// Grab the goal scorer number first.
			String scorerJersey = goal.nextToken();
			pp.setPlayerid(PlayerPeer.getIdFromJersey(scorerJersey,curForm.getTeamid()));
			pp.setGameid(val.getGameid());
			pp.setPointtype(1);
			pp.setNew(true);
			pp.save();
			
			// Set assists if entered.
			while (goal.hasMoreTokens()) {
				scorerJersey = goal.nextToken();
				pp = new Playerpoints();
				pp.setPlayerid(PlayerPeer.getIdFromJersey(scorerJersey,curForm.getTeamid()));
				pp.setGameid(val.getGameid());
				pp.setPointtype(0);				
				pp.setNew(true);
				pp.save();
			}
		}
		
		curForm.setGameid(val.getGameid());
		System.out.println("Saved Game:" + val.getGameid());
		
		// Remove the Form Bean - don't need to carry values forward 
		request.removeAttribute(mapping.getAttribute());

		// Forward control to the specified success URI
		ActionMessages Messages = new ActionMessages();
		Messages.add("success",new ActionMessage("general.save.success"));
		saveMessages(request,Messages);
		
		// Assume success if we have reached this point
		boolean isAdmin = ((String) request.getSession().getAttribute("teammgr.Admin")).equals("1");
		if (isAdmin) {
			return mapping.findForward("admin");
		} else {
			return mapping.findForward("success");
		}
	}
}
