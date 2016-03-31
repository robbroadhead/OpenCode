<jsp:directive.page import="com.sns.teammgr.managers.Team" />
<jsp:directive.page import="com.sns.teammgr.Constants" />
<jsp:directive.page import="com.sns.teammgr.managers.TeamPeer" />
<jsp:directive.page import="org.apache.torque.Torque" />
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<jsp:include page="ManagerHead.jsp" flush="true" />

<logic:notPresent name="org.apache.struts.action.MESSAGE"
	scope="application">

	<font color="red"> ERROR: Application resources not loaded --
		check servlet container logs for error messages. </font>

</logic:notPresent>

<div id="content">
	<table align=center>
		<!-- Now the main text -->
		<tr>
			<td colspan=2>
				<html:form action="Login.do">
					<table id=login>
						<tr>
							<td>
								<B>Username:
								</B>
							</td>
							<td>
								<html:text size="15" maxlength="32" property="logon" />
							</td>
							<td>
								<B>Password:
								</B>
							</td>
							<td>
								<html:password size="15" maxlength="32" property="password" />
							</td>
							<td>
								<html:submit>
									Login
								</html:submit>
							</td>
						</tr>
						<tr>
							<td colspan=2>
								<html:errors property="username" />
							</td>
							<td colspan=3>
								<html:errors property="password" />
							</td>
						</tr>
					</table>
				</html:form>
			</td>
		</tr>
		<tr>
			<td valign=top align="center">
				<H2>
							An Easy Way To Manage Your Team.
				</H2>
			</td>
		</TR>
		<tr>
			<td colspan=2>
				<p>Whether you are a team manager or a coach there is a lot of data you need to track in order to keep your team in
				touch and up-to-date on the latest news. This site is aimed at giving every team a single place to keep track of
				information, update information, and even comment on the team events and games.
				</p>
				<p>This is aimed at providing you with a simple to use solution to your team management problems. If you have further
				needs or comments about this site and/or the features provided please contact us so we can work to make a great place
				for your team to go for all of their team related needs.
				</p>
				<p>You can login to view your team details and add comments or you can select from the teams listed below to see how
				other teams are doing.
				</p>
				<form>
				<select name="currentTeam" onChange=javascript:window.location=this.form.currentTeam.options[selectedIndex].value>
					<option value="index.jsp">-- Select a team to View --</option>
				<% 
				if (!Torque.isInit()) {
					Torque.init(Constants.TorqueHome);
				}
	
				Team teams[] = TeamPeer.getTeams();
				for (int i = 0;i < teams.length;i++) {
				%>
					<option value="PublicTeamView.do?id=<%=teams[i].getTeamid() %>"><%=teams[i].getTeamname() %>:<%=teams[i].getSeason() %></option>
				<%
				}
				%>
				</select></form>
				<p>Or you can <a href="NewTeam.do">Click Here</a> to create your own team from scratch 
				</p>
			</td>
		</tr>
		<tr>
			<td colspan=2>&nbsp;
				
			</td>
		</tr>
	</table>
	<jsp:include page="ManagerFoot.jsp" flush="true" />
</div>
