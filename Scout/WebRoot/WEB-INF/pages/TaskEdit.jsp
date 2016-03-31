<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<jsp:include page="ScoutHead.jsp" flush="false" />
<div id="content">

	<table align=center width=100% border=0 cellspacing=0 cellpadding=0>
		<!-- Now the main text -->
		<tr>
			<th>
				<bean:message key="task.heading" />
			</th>
		</TR>
		<tr>
			<td>
			<% String target="Task.do";
			String parm = request.getParameter("task");
			if (parm != null && parm.equals("true")) {
				target="LTask.do";
			} %>
				<html:form action="<%=target %>">
					<table width=90%>
						<logic:messagesPresent message="true">
							<html:messages id="success" message="true">
								<bean:write name="success" />
								<br />
							</html:messages>
						</logic:messagesPresent>
						<tr>
							<td colspan=2>
								<b><bean:message key="task.summary" />
								</b>
								<html:hidden property="id" />
								<html:hidden property="estid" />
								<html:hidden property="actid" />&nbsp;
								<html:text size="60" maxlength="80" property="summary" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="task.project" />
								</b>
							</td><td>
								<html:select property="project">
									<html:optionsCollection property="projectlist" label="name" value="projectid"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="task.status" />
								</b>
							</td><td>
								<html:select property="taskstatus">
									<html:optionsCollection property="statuslist" label="description" value="statusid"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="task.user" />
								</b>&nbsp;
								<html:select property="user">
									<html:optionsCollection property="userlist" label="displayname" value="userid"/>
								</html:select>
							</td><td>
								<b><bean:message key="task.group" />
								</b>&nbsp;
								<html:select property="group">
									<html:optionsCollection property="grouplist" label="displayname" value="groupid"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td colspan=2>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan=2>
								<b><bean:message key="task.detail" />
								</b>
								<html:textarea cols="80" rows="5" property="detail" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="task.created" />
								</b>&nbsp;
								<html:text size="20" maxlength="20" property="createdate" />
							</td>
							<td>
								<b><bean:message key="task.type" />
								</b>&nbsp;
								<html:select property="tasktype">
									<html:optionsCollection property="typelist" label="description" value="typeid"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td colspan=2>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td><bean:message key="task.est.title" /><br/><table border=1 cellpadding=0 cellspacing=0>
							<tr><td><b><bean:message key="task.est.start" /></b></td><td><html:text size="20" maxlength="20" property="eststart" /></td></tr>
							<tr><td><b><bean:message key="task.est.end" /></b></td><td><html:text size="20" maxlength="20" property="estend" /></td></tr>
							<tr><td><b><bean:message key="task.est.priority" /></b></td><td><html:select property="estpriority">
									<html:optionsCollection property="prioritylist" label="description" value="priorityid"/>
								</html:select>
							</td></tr>
							<tr><td><b><bean:message key="task.est.measure" /></b></td><td><html:select property="estmeasure">
									<html:optionsCollection property="measurelist" label="description" value="measureid"/>
								</html:select>
							</td></tr>
							<tr><td><b><bean:message key="task.est.amount" /></b></td><td><html:text size="20" maxlength="20" property="estamount" /></td></tr>
							</table>
							</td>
							<td><bean:message key="task.act.title" /><br/><table border=1 cellpadding=0 cellspacing=0>
							<tr><td><b><bean:message key="task.act.start" /></b></td><td><html:text size="20" maxlength="20" property="actstart" /></td></tr>
							<tr><td><b><bean:message key="task.act.end" /></b></td><td><html:text size="20" maxlength="20" property="actend" /></td></tr>
							<tr><td><b><bean:message key="task.act.priority" /></b></td><td><html:select property="actpriority">
									<html:optionsCollection property="prioritylist" label="description" value="priorityid"/>
								</html:select>
							</td></tr>
							<tr><td><b><bean:message key="task.act.measure" /></b></td><td><html:select property="actmeasure">
									<html:optionsCollection property="measurelist" label="description" value="measureid"/>
								</html:select>
							</td></tr>
							<tr><td><b><bean:message key="task.act.amount" /></b></td><td><html:text size="20" maxlength="20" property="actamount" /></td></tr>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan=2>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan=2 align=center>
								<html:submit>
									<bean:message key="general.save" />
								</html:submit>
								<html:reset>
									<bean:message key="general.reset" />
								</html:reset>
							</td>
						</tr>
						<tr>
							<td colspan=2>
								&nbsp;
							</td>
						</tr>
					</table>
				</html:form>
			</td>
		</tr>
	</table>
	<jsp:include page="ScoutFoot.jsp" flush="true" />
</div>
