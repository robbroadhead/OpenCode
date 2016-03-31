<jsp:directive.page import="com.sns.scout.managers.Project" />
<jsp:directive.page import="com.sns.scout.managers.ProjectPeer" />
<jsp:directive.page import="com.sns.scout.managers.TaskPeer" />
<jsp:directive.page import="com.sns.scout.managers.LkpstatusPeer" />
<jsp:directive.page import="com.sns.scout.managers.LkptypePeer" />
<jsp:directive.page import="com.sns.scout.managers.Task" />
<jsp:directive.page import="com.sns.util.Utility" />
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<jsp:include page="ScoutHead.jsp" flush="false" />
<div id="content">

	<%
		Project curProject = (Project) request.getSession().getAttribute("curProject");

		// Get the list of tasks
		Task[] items = null;
		if (curProject != null) {
			items = TaskPeer.getTasksByProject(curProject.getProjectid());
		}
	%>

<h2 align="center"><bean:message key="project.heading" /></h2>
	<table align=center width=100% border=0 cellspacing=0 cellpadding=0>
		<!-- Now the main text -->
		<tr>
			<td>
				<html:form action="Project.do">
					<table width=100%><tr><td colspan=2>
								<html:messages id="name" message="true">
								<font color="red">
									<bean:write name="name" />
									</font>
							    </html:messages>
							</td></tr>
						<tr>
							<td>
								<b><bean:message key="project.name" /> </b>
								<html:hidden property="id" />
							</td>
							<td>
								<html:text size="25" maxlength="50" property="name" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="project.created" /> </b>
							</td>
							<td>
								<html:text size="20" maxlength="20" property="createdate" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="project.started" /> </b>
							</td>
							<td>
								<html:text size="20" maxlength="20" property="startdate" />
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
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan=2>
								<div id="contentreport">

									<table align=center width=100% border=0 cellspacing=0
										cellpadding=0>
										<!-- Now the main text -->
										<tr>
											<td colspan=4 align="center">
												<H3 align="center">
													Project Tasks
												</H3>
											</td>
										</tr>
										<tr>
											<td colspan=4>
												<table border=1 bordercolor="GRAY" cellpadding=0
													cellspacing=0 rules="none" align="center" width=100%>
													<tr>
														<th width=20%>
															Create Date
														</th>
														<th width=10%>
															Status
														</th>
														<th width=10%>
															Type
														</th>
														<th width=65%>
															Summary
														</th>
													</tr>
													<%
														String tempStr = "";
														boolean odd = true;

														if (items == null || items.length < 1) {
															tempStr = "<td colspan=4>No tasks have been assigned to this project yet.</td>";
													%>
													<tr class="ODD">
														<%=tempStr%>
													</tr>
													<%
														} else {

														for (int i = 0; i < items.length; i++) {
															tempStr = "<td>"
															+ Utility.DateTimeToStr(items[i].getCreated())
															+ "</td><td>"
															+ LkpstatusPeer.retrieveByPK(items[i].getStatusid()).getName()
															+ "</td><td>"
															+ LkptypePeer.retrieveByPK(items[i].getTypeid()).getName() + "</td><td><a href='TaskEdit.do?id=" + items[i].getTaskid()
															+ "'>" + items[i].getSummary() + "</a></td>";
															if (odd) {
													%>
													<tr class="ODD">
														<%=tempStr%>
													</tr>
													<%
															} else {
													%>
													<tr class="EVEN">
														<%=tempStr%>
													</tr>
													<%
															}
															odd = !odd;
															}
														}
													%>
												</table>
											</td>
										</tr>
									</table>
								</div>
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
								</html:submit>&nbsp;|&nbsp;
								<html:reset>
									<bean:message key="general.reset" />
								</html:reset>&nbsp;|&nbsp;
											<html:button onclick="go('TaskEdit.do')"
												property="AddTask">Add Task</html:button>&nbsp;|&nbsp;
											<html:button onclick="go('Logout.do')" property="Logout">Logout</html:button>
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
