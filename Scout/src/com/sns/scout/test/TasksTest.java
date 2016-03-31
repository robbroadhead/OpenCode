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
package com.sns.scout.test;

import junit.framework.TestCase;
import java.util.ArrayList;

import com.sns.scout.managers.Project;
import com.sns.scout.managers.ProjectPeer;
import org.apache.torque.Torque;
import org.apache.torque.util.Criteria;

import java.util.Date;
import java.util.Calendar;

public class TasksTest extends TestCase {
	/**
	 * Configure the connection and other generally needed variables. 
	 */
	protected void setUp() throws Exception {
		super.setUp();
		if (!Torque.isInit()) {
			Torque.init("/usr/conf/Scout.properties");
		}
	}

	public void test1ProjectCreate() {
		Project p = new Project();
		Calendar c = Calendar.getInstance();
		c.set(2007, 3, 15, 10, 30, 0);

		// Start with a count
		int theCount = ProjectPeer.countAll();

		p.setCreated(new Date());
		p.setName("Unit Test Project");
		p.setNew(true);
		p.setStartdate(c.getTime());
		try {
			p.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Count after the insert
		int count2 = ProjectPeer.countAll();
		
		// Try to login with a valid userid and password.
		assertEquals(count2,theCount + 1);
	}
	
	public void test2ProjectDelete() {
		Criteria crit = new Criteria();

		try {
			crit.add(ProjectPeer.TABLE_NAME,ProjectPeer.NAME,"Unit Test Project",Criteria.EQUAL);
			crit.setAll();
			ProjectPeer.doDelete(crit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
