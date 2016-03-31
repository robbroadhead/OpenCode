/*
 * Created on Jan 29, 2004
 *
 * @Version 1.0
 * @Author Rob Broadhead (Rob@rb-sns.com)
 *
 * Copyright 2004 RB Consulting, Inc. All Rights Reserved
 */
package com.sns.scout.form;

import org.apache.struts.action.ActionForm;

/**
 * @author Rob Broadhead
 *
 * Description: This is the base form for sns forms based on Struts.
 *   This gives a form where common validation can be stored as suggested
 *   in the Struts kickstart book.
 *
 */

public class BaseForm extends ActionForm {
	/**
	 * This method tests whether a string is null, empty or contains only
	 * white space characters. Empty in the case of this method includes
	 * the string " " or a string that contains a line feed, carriage return
	 * or other white space characters.
	 *
	 * @param val The string we want to test.
	 * @return true if the string is empty or null
	 */
	protected boolean isEmptyString(String val) {
		boolean retVal = true;

		if (val != null) {
			if (val.trim().length() > 0) {
				retVal = false;
			}
		}

		return retVal;
	}

}
