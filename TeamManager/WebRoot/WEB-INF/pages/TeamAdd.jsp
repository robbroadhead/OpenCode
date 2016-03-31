<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<jsp:include page="ManagerHead.jsp" flush="false" />
<div id="content">

	<table align=center width=100% border=0 cellspacing=0 cellpadding=0>
		<!-- Now the main text -->
		<tr>
			<th>
				<bean:message key="team.heading" />
			</th>
		</TR>
		<tr>
			<td>
				<html:form action="TeamAdd.do">
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
								<b><bean:message key="team.name" />
								</b>
								<html:hidden property="teamid" />
							</td>
							<td>
								<html:text size="32" maxlength="40" property="teamname" />
							</td>
							<td>
								<b><bean:message key="team.color" />
								</b>
							</td>
							<td>
								Primary: <html:text size="15" maxlength="20" property="color1" />
								Secondary: <html:text size="15" maxlength="20" property="color2" />
							</td>
						</tr>
						<tr>
							<td colspan=4><html:messages id="teamname" message="true">
							</html:messages>
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="team.admin" />
								</b>
							</td>
							<td>
								<html:text size="32" maxlength="32" property="adminname" />
							</td>
							<td>
								<b><bean:message key="team.adminpwd" />
								</b>
							</td>
							<td>
								<html:password size="32" maxlength="32" property="adminpassword" />
							</td>
						</tr>
						<tr>
							<td colspan=2><html:messages id="name" message="true">
							</html:messages>
							</td>
							<td colspan=2><html:messages id="password" message="true">
							</html:messages>
							</td>
						</tr>
						<tr>
							<td>
								<b>Coach:
								</b>
							</td>
							<td>
								<html:text size="32" maxlength="80" property="coach" />
							</td>
							<td>
								<b>Coach Phone:
								</b>
							</td>
							<td>
								<html:text size="32" maxlength="80" property="coachphone" />
							</td>
						</tr>
						<tr>
							<td>
								<b>Assistant Name:
								</b>
							</td>
							<td>
								<html:text size="32" maxlength="80" property="assist" />
							</td>
							<td>
								<b>Assistant Phone:
								</b>
							</td>
							<td>
								<html:text size="32" maxlength="80" property="assistphone" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="team.guest" />
								</b>
							</td>
							<td>
								<html:text size="32" maxlength="32" property="username" />
							</td>
							<td>
								<b><bean:message key="team.guestpwd" />
								</b>
							</td>
							<td>
								<html:password size="32" maxlength="32" property="password" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="team.logo" />
								</b>
							</td>
							<td>
								<html:text size="32" maxlength="200" property="piclogo" />
							</td>
							<td>
								<b><bean:message key="team.season" />
								</b>
							</td>
							<td>
								<html:text size="32" maxlength="40" property="season" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="team.type" />
								</b>
							</td>
							<td colspan=3>
								<html:select property="teamtype">
									<html:optionsCollection property="typelist" label="description" value="id"/>
								</html:select>
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
