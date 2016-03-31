<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<jsp:include page="ManagerHead.jsp" flush="false" />
<div id="content">

	<table align=center width=100% border=0 cellspacing=0 cellpadding=0>
		<!-- Now the main text -->
		<tr>
			<th>
				<bean:message key="event.heading" />
			</th>
		</TR>
		<tr>
			<td>
				<html:form action="Event.do">
					<logic:messagesPresent message="true">
						<html:messages id="success" message="true"></html:messages>
					</logic:messagesPresent>
					<table width=100%>
						<tr><td width=20%>&nbsp;</td>
							<td width=30%>&nbsp;</td>
							<td width=20%>&nbsp;</td>
							<td width=30%>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="event.title" />
								</b>
								<html:hidden property="eventid" />
								<html:hidden property="teamid" />
							</td>
							<td>
								<html:text size="35" maxlength="40" property="title" />
							</td>
							<td>
								<b><bean:message key="event.link" />
								</b>
							</td>
							<td>
								<html:text size="35" maxlength="100" property="link" />
							</td>
						</tr>
						<tr>
							<td colspan=4><html:messages id="title" message="true">
							</html:messages>
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="event.startdate" />
								</b>
							</td>
							<td>
								<html:text size="20" maxlength="20" property="startdate" />
							</td>
							<td>
								<b><bean:message key="event.enddate" />
								</b>
							</td>
							<td>
								<html:text size="20" maxlength="20" property="enddate" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="event.message" />
								</b>
							</td>
							<td colspan=3>
								<html:textarea cols="50" rows="5" property="message" />
							</td>
						</tr>
						<tr>
							<td colspan=4>&nbsp;	
							</td>
						</tr>
						<tr>
							<td colspan=4 align=center>
								<html:submit>
									<bean:message key="general.save" />
								</html:submit>
								<html:reset>
									<bean:message key="general.reset" />
								</html:reset>
							</td>
						</tr>
						<tr>
							<td colspan=4>&nbsp;								
							</td>
						</tr>
					</table>
				</html:form>
			</td>
		</tr>
	</table>
	<jsp:include page="ManagerFoot.jsp" flush="true" />
</div>
