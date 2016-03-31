package com.sns.scout.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionErrors;

import org.apache.torque.util.Criteria;
import java.util.List;
import com.sns.scout.form.TaskForm;
import com.sns.scout.managers.Grouptask;
import com.sns.scout.managers.GrouptaskPeer;
import com.sns.scout.managers.Task;
import com.sns.scout.managers.Taskactual;
import com.sns.scout.managers.Taskestimate;
import com.sns.scout.managers.TaskPeer;
import com.sns.scout.managers.TaskactualPeer;
import com.sns.scout.managers.TaskestimatePeer;
import com.sns.scout.managers.Usertask;
import com.sns.scout.managers.UsertaskPeer;
import com.sns.util.Utility;
import org.apache.torque.Torque;


/**
 * The controller class for the project form.
 */

public final class SecureTaskAction extends Action {

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
    	if (SecureAction.ValidateUser(request.getSession())==false) {
      		return mapping.findForward("failure");
    	}

		// The user is logged in so we need to look for a parameter on the query
		// string and populate the Client form if we have a valid clientid. 
		String parm = request.getParameter("id");
		if (parm != null) { 
			TaskForm cf = (TaskForm) form;
			Task cli = null;
			Taskactual act = null;
			Taskestimate est = null;

			Criteria crit = new Criteria();
			try {
				int idValue = Integer.parseInt(parm);
				// First grab the task
				crit.add(TaskPeer.TASKID,idValue);
				crit.setAll();
				List result = TaskPeer.doSelect(crit);

				if (result.size() < 1) {
					ActionMessages msgs = new ActionMessages();
					saveMessages(request,msgs);
					return mapping.findForward("success");
				}
				cli = (Task) result.get(0);
				
				// Now grab the estimates data
				crit = new Criteria();
				crit.add(TaskestimatePeer.TASKID,idValue);
				crit.setAll();
				result = TaskestimatePeer.doSelect(crit);

				if (result.size() < 1) {
					ActionMessages msgs = new ActionMessages();
					saveMessages(request,msgs);
					return mapping.findForward("success");
				}
				est = (Taskestimate) result.get(0);
				
				// Finally grab the actuals data
				crit = new Criteria();
				crit.add(TaskactualPeer.TASKID,idValue);
				crit.setAll();
				result = TaskactualPeer.doSelect(crit);

				if (result.size() < 1) {
					ActionMessages msgs = new ActionMessages();
					saveMessages(request,msgs);
					return mapping.findForward("success");
				}
				act = (Taskactual) result.get(0);

				// Load in the form values.
				cf.setActamount(act.getTimevalue());
				cf.setActend(Utility.DateTimeToStr(act.getEnddate()));
				cf.setActid(act.getEstimateid());
				cf.setActmeasure(act.getMeasureid());
				cf.setActpriority(act.getPriorityid());
				cf.setActstart(Utility.DateTimeToStr(act.getStartdate()));
				cf.setCreatedate(Utility.DateTimeToStr(cli.getCreated()));
				cf.setDetail(cli.getDetail());
				cf.setEstamount(est.getTimevalue());
				cf.setEstend(Utility.DateTimeToStr(est.getEnddate()));
				cf.setEstid(est.getEstimateid());
				cf.setEstmeasure(est.getMeasureid());
				cf.setEstpriority(est.getPriorityid());
				cf.setEststart(Utility.DateTimeToStr(est.getStartdate()));
				cf.setId(cli.getTaskid());
				cf.setProject(cli.getProjectid());
				cf.setSummary(cli.getSummary());
				cf.setTaskstatus(cli.getStatusid());
				cf.setTasktype(cli.getTypeid());
				
				// Grab the group/user stuff
				Usertask up = UsertaskPeer.mappingExists(cli.getTaskid());
				if (up != null) {
					cf.setUser(up.getUserid());
				}
				
				Grouptask gp = GrouptaskPeer.mappingExists(cli.getTaskid());
				if (gp != null) {
					cf.setGroup(gp.getGroupid());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Forward control to the specified success URI
		return mapping.findForward("success");
	}
}
