<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<jsp:include page="ScoutHead.jsp" flush="false" />
<div id="content">

	<table align=center width=100% border=0 cellspacing=0 cellpadding=0>
		<!-- Now the main text -->
		<tr>
			<th>
				<bean:message key="user.heading" />
			</th>
		</TR>
		<tr>
			<td>
				<html:form action="User.do">
					<table width=100%>
						<logic:messagesPresent message="true">
							<html:messages id="success" message="true">
								<bean:write name="success" />
								<br />
							</html:messages>
						</logic:messagesPresent>
						<tr>
							<td colspan=2>
								<b><bean:message key="user.displayname" />
								</b>
								<html:hidden property="id" />
								<html:text size="80" maxlength="80" property="displayname" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="user.firstname" />
								</b>
								<html:text size="40" maxlength="40" property="fname" />
							</td>
							<td>
								<b><bean:message key="user.lastname" />
								</b>
								<html:text size="40" maxlength="40" property="lname" />
							</td>
						</tr>
						<tr>
							<td colspan=2>
								<b><bean:message key="user.detail" />
								</b>
								<html:text size="80" maxlength="80" property="detail" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="user.created" />
								</b>&nbsp;
								<html:text size="20" maxlength="20" property="createdate" />
							</td>
							<td>
								<b><bean:message key="user.external" />
								</b>&nbsp;
								<html:text size="40" maxlength="40" property="external" />
							</td>
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
