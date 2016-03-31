<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<jsp:include page="ManagerHead.jsp" flush="false" />
<div id="content">

	<table align=center width=100% border=0 cellspacing=0 cellpadding=0>
		<!-- Now the main text -->
		<tr>
			<th>
				<bean:message key="player.heading" />
			</th>
		</TR>
		<tr>
			<td>
				<html:form action="Player.do">
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
								<b><bean:message key="player.fname" />
								</b>
								<html:hidden property="playerid" />
								<html:hidden property="teamid" />
							</td>
							<td>
								<html:text size="35" maxlength="40" property="firstname" />
							</td>
							<td>
								<b><bean:message key="player.lname" />
								</b>
							</td>
							<td>
								<html:text size="35" maxlength="40" property="lastname" />
							</td>
						</tr>
						<tr>
							<td colspan=4><html:messages id="playername" message="true">
							</html:messages>
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="player.nickname" />
								</b>
							</td>
							<td>
								<html:text size="35" maxlength="120" property="nickname" />
							</td>
							<td>
								<b><bean:message key="player.email" />
								</b>
							</td>
							<td>
								<html:text size="35" maxlength="150" property="email" />
							</td>
						</tr>
						<tr>
							<td colspan=2><html:messages id="nickname" message="true">
							</html:messages>
							</td>
							<td colspan=2><html:messages id="email" message="true">
							</html:messages>
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="player.parents" />
								</b>
							</td>
							<td>
								<html:text size="35" maxlength="200" property="parentnames" />
							</td>
							<td>
								<b><bean:message key="player.position" /> | 
								<bean:message key="player.number" />
								</b>
							</td>
							<td>
									<html:select property="position">
									<html:optionsCollection property="poslist" label="description"
										value="id" />
								</html:select> | 
								<html:text size="10" maxlength="10" property="jersey" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="player.phone" />
								</b>
							</td>
							<td>
								<html:text size="20" maxlength="20" property="phone" />
							</td>
							<td>
								<b><bean:message key="player.phone2" />
								</b>
							</td>
							<td>
								<html:text size="20" maxlength="20" property="phone2" />
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
