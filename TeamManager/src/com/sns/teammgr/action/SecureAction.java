/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.sns.teammgr.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.torque.Torque;

/**
 * The controller class for the login form of the sns application
 */

public final class SecureAction
    extends Action {

  static public boolean ValidateUser(HttpSession s) {
    boolean retVal = true;
    // Make sure we have initialized the Torque system
    if (!Torque.isInit()) {
    	try {
			Torque.init("/usr/conf/Teammgr.properties");
    	} catch (Exception e) {
    		System.out.println("Failed to initialize the properties - First Try");
    		e.printStackTrace();
    	}
    }

    // Make sure the user has logged in.
    if (s.getAttribute("teammgr.User") == null) {
      retVal = false;
    }
    
    // Clear up some sort of temp variables
	s.setAttribute("curlist",null);
    return retVal;
  }

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
                               HttpServletResponse response) throws Exception {
    /*
     * Validate the request parameters specified by the user
     * Note: Basic field validation done in HelloForm.java
     *       Business logic validation done in HelloAction.java
     */
    if (SecureAction.ValidateUser(request.getSession())==false) {
      return mapping.findForward("failure");
    }
	// Store the redirect if needed
	if (request.getQueryString() != null) {
		System.out.println("Setting the session redirect");
		request.getSession().setAttribute("teammgr.redirect",request.getParameter("redirect"));
	}

    // Forward control to the specified success URI
    return mapping.findForward("success");
  }
}
