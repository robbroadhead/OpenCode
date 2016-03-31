<jsp:directive.page import="com.sns.teammgr.managers.Player" />
<jsp:directive.page import="com.sns.teammgr.managers.Game" />
<jsp:directive.page import="com.sns.teammgr.managers.Event" />
<jsp:directive.page import="com.sns.teammgr.managers.Team" />
<jsp:directive.page import="com.sns.teammgr.managers.TeamPeer" />
<jsp:directive.page import="com.sns.teammgr.managers.TeammsgPeer" />
<jsp:directive.page import="com.sns.teammgr.managers.GamePeer" />
<jsp:directive.page import="com.sns.teammgr.managers.EventPeer" />
<jsp:directive.page import="com.sns.teammgr.managers.Teammsg" />
<jsp:directive.page import="com.sns.teammgr.managers.LkpresulttypePeer" />
<jsp:directive.page import="com.sns.teammgr.managers.LkppositionPeer" />
<jsp:directive.page import="com.sns.teammgr.managers.Lkpposition" />
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<jsp:include page="ManagerHead.jsp" flush="false" />
<div id="content">

	<%
		// Get the list of players
		Team curTeam = (Team) request.getSession().getAttribute("team");

		// Get the list of players
		Player[] roster = TeamPeer.getSkaters(curTeam.getTeamid());
		Player[] goalies = TeamPeer.getGoalies(curTeam.getTeamid());

		// Get the list of Games
		Game[] games = GamePeer.getGames(curTeam.getTeamid());

		// Get the list of Events
		Event[] events = EventPeer.getEvents(curTeam.getTeamid());

		// Get the list of Messages
		Teammsg[] msgs = TeammsgPeer.getMessages(curTeam.getTeamid());
	%>

	<table align=center width=100% border=0 cellspacing=0 cellpadding=0>
		<!-- Now the main text -->
		<html:form action="Team.do">
			<tr>
				<th>
					<logic:present name="team" scope="session">
						<bean:write name="team" property="teamname" />
					</logic:present>
					<html:hidden property="teamid" />
					<html:hidden property="username" />
					<html:hidden property="password" />
					<html:hidden property="adminname" />
					<html:hidden property="adminpassword" />
					<html:hidden property="teamname" />
				</th>
			</TR>
			<tr>
				<td>
					<table width=100%>
						<tr>
							<td width=20%>
								&nbsp;
							</td>
							<td width=30%>
								&nbsp;
							</td>
							<td width=20%>
								&nbsp;
							</td>
							<td width=30%>
								&nbsp;
							</td>
						</tr>
						<logic:present name="team" scope="session">
							<tr>
								<td colspan=4 align=center>
									<img src='<bean:write name="team" property="piclogo" />' />
								</td>
							</tr>
						</logic:present>
						<tr>
							<td>
								<b><bean:message key="team.season" /> </b>
							</td>
							<td>
								<html:text size="32" maxlength="40" property="season" />
							</td>
							<td colspan=2>
								<b><bean:message key="team.color" /> </b> Primary:
								<html:text size="15" maxlength="20" property="color1" />
								Secondary:
								<html:text size="15" maxlength="20" property="color2" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="team.logo" /> </b>
							</td>
							<td>
								<html:text size="32" maxlength="200" property="piclogo" />
							</td>
							<td>
								<b><bean:message key="team.type" /> </b>
							</td>
							<td>
								<html:select property="teamtype">
									<html:optionsCollection property="typelist" label="description"
										value="id" />
								</html:select>
							</td>
						</tr>
						<tr>
							<td>
								<b>Coach: </b>
							</td>
							<td>
								<html:text size="32" maxlength="80" property="coach" />
							</td>
							<td>
								<b>Coach Phone: </b>
							</td>
							<td>
								<html:text size="32" maxlength="80" property="coachphone" />
							</td>
						</tr>
						<tr>
							<td>
								<b>Assistant Name: </b>
							</td>
							<td>
								<html:text size="32" maxlength="80" property="assist" />
							</td>
							<td>
								<b>Assistant Phone: </b>
							</td>
							<td>
								<html:text size="32" maxlength="80" property="assistphone" />
							</td>
						</tr>
						<tr>
							<td colspan=4>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan=4>
								<div id="contentreport">

									<table align=center width=100% border=0 cellspacing=0
										cellpadding=0>
										<!-- Now the main text -->
										<tr>
											<td colspan=4 align="center">
												<H3>
													Player Roster
												</H3>
											</td>
										</tr>
										<tr>
											<td colspan=4>
												<table border=1 bordercolor="GRAY" cellpadding=0
													cellspacing=0 rules="none" align="center" width=700>
													<tr>
														<th width=10%>
															Number
														</th>
														<th width=10%>
															Position
														</th>
														<th width=30%>
															Name (nickname)
														</th>
														<th width=20%>
															Phone
														</th>
														<th width=30%>
															Parents
														</th>
													</tr>
													<%
														String tempStr = "";
															boolean odd = true;

															if (roster.length < 1) {
																tempStr = "<td colspan=5>No Players have been added to this team yet.</td>";
													%>
													<tr class="ODD">
														<%=tempStr%>
													</tr>
													<%
														}

															for (int i = 0; i < roster.length; i++) {
																tempStr = "<td>"
																		+ roster[i].getJerseynum()
																		+ "</td><td>"
																		+ LkppositionPeer.retrieveByPK(
																				roster[i].getPosition()).getName()
																		+ "</td><td><a href='PlayerEdit.do?id="
																		+ roster[i].getPlayerid() + "'>"
																		+ roster[i].getFirstname() + " "
																		+ roster[i].getLastname() + " ("
																		+ roster[i].getNickname() + ")</a>" + "</td><td>"
																		+ roster[i].getPhone() + "</td><td>"
																		+ roster[i].getParentnames() + "</td>";
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
													%>
												</table>
											</td>
										</tr>
										<tr>
											<td colspan=4>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td colspan=4 align="center">
												<H3>
													Goalies
												</H3>
											</td>
										</tr>
										<tr>
											<td colspan=4>
												<div id="contentreport">
													<table border=1 bordercolor="GRAY" cellpadding=0
														cellspacing=0 rules="none" align="center" width=700>
														<tr>
															<th width=5%>
																#
															</th>
															<th width=7%>
																Shutouts
															</th>
															<th width=8%>
																Shots Against
															</th>
															<th width=80%>
																Name (nickname)
															</th>
														</tr>
														<%
															tempStr = "";
																odd = true;

																if (roster.length < 1) {
																	tempStr = "<td colspan=5>No Goalies have been added to this team yet.</td>";
														%>
														<tr class="ODD">
															<%=tempStr%>
														</tr>
														<%
															}

																for (int i = 0; i < goalies.length; i++) {
																	tempStr = "<td>" + goalies[i].getJerseynum() + "</td><td>"
																			+ goalies[i].getShutouts() + "</td><td>"
																			+ goalies[i].getShotsAgainst() + "</td><td>"
																		+ "<a href='PlayerEdit.do?id="
																		+ goalies[i].getPlayerid() + "'>"
																			+ goalies[i].getFirstname() + " "
																			+ goalies[i].getLastname() + " ("
																			+ goalies[i].getNickname() + ") </a></td>";
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
														%>
													</table>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan=4>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan=4>
								<div id="contentreport">

									<table align=center width=100% border=0 cellspacing=0
										cellpadding=0>
										<!-- Now the main text -->
										<tr>
											<td colspan=4 align="center">
												<H3>
													Games Played
												</H3>
											</td>
										</tr>
										<tr>
											<td colspan=4>
												<table border=1 bordercolor="GRAY" cellpadding=0
													cellspacing=0 rules="none" align="center" width=700>
													<tr>
														<th width=10%>
															Result
														</th>
														<th width=10%>
															Us
														</th>
														<th width=10%>
															Them
														</th>
														<th width=70%>
															Summary
														</th>
													</tr>
													<%
														tempStr = "";
															odd = true;

															if (games.length < 1) {
																tempStr = "<td colspan=4>No games have been played yet.</td>";
													%>
													<tr class="ODD">
														<%=tempStr%>
													</tr>
													<%
														}

															for (int i = 0; i < games.length; i++) {
																tempStr = "<td>"
																		+ LkpresulttypePeer.retrieveByPK(
																				games[i].getResult()).getDescription()
																		+ "</td><td>" + games[i].getScoreus() + "</td><td>"
																		+ games[i].getScorethem()
																		+ "</td><td><a href='GameEdit.do?id="
																		+ games[i].getGameid() + "'>"
																		+ games[i].getSummary() + "</a></td>";
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
													%>
												</table>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan=4>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan=4>
								<div id="contentreport">

									<table align=center width=100% border=0 cellspacing=0
										cellpadding=0>
										<!-- Now the main text -->
										<tr>
											<td colspan=4 align="center">
												<H3>
													Events
												</H3>
											</td>
										</tr>
										<tr>
											<td colspan=4>
												<table border=1 bordercolor="GRAY" cellpadding=0
													cellspacing=0 rules="none" align="center" width=700>
													<tr>
														<th width=15%>
															Starts
														</th>
														<th width=15%>
															Ends
														</th>
														<th width=70%>
															Title
														</th>
													</tr>
													<%
														tempStr = "";
															odd = true;

															if (events.length < 1) {
																tempStr = "<td colspan=3>No events have been scheduled yet.</td>";
													%>
													<tr class="ODD">
														<%=tempStr%>
													</tr>
													<%
														}

															for (int i = 0; i < events.length; i++) {
																tempStr = "<td>" + events[i].getStartdate() + "</td><td>"
																		+ events[i].getEnddate()
																		+ "</td><td><a href='EventEdit.do?id="
																		+ events[i].getEventid() + "'>"
																		+ events[i].getTitle() + "</a></td>";
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
													%>
												</table>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan=4>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan=4>
								<div id="contentreport">

									<table align=center width=100% border=0 cellspacing=0
										cellpadding=0>
										<!-- Now the main text -->
										<tr>
											<td colspan=4 align="center">
												<H3>
													Messages
												</H3>
											</td>
										</tr>
										<tr>
											<td colspan=4>
												<table border=1 bordercolor="GRAY" cellpadding=0
													cellspacing=0 rules="none" align="center" width=700>
													<tr>
														<th width=20%>
															Expiration Date
														</th>
														<th width=80%>
															Title
														</th>
													</tr>
													<%
														tempStr = "";
															odd = true;

															if (msgs.length < 1) {
																tempStr = "<td colspan=3>No messages have been created yet.</td>";
													%>
													<tr class="ODD">
														<%=tempStr%>
													</tr>
													<%
														}

															for (int i = 0; i < msgs.length; i++) {
																tempStr = "<td>" + msgs[i].getExpiredate()
																		+ "</td><td><a href='MessageEdit.do?id="
																		+ msgs[i].getMsgid() + "'>" + msgs[i].getTitle()
																		+ "</a></td>";
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
													%>
												</table>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan=4>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan=4 align="center">
								<html:submit>
									<bean:message key="general.save" />
								</html:submit>
								&nbsp;|&nbsp;
								<html:button onclick="go('PlayerEdit.do')" property="AddPlayer">Add Player</html:button>
								&nbsp;|&nbsp;
								<html:button onclick="go('EventEdit.do')" property="AddEvent">Add Event</html:button>
								&nbsp;|&nbsp;
								<html:button onclick="go('MessageEdit.do')" property="AddNotice">Add Notice</html:button>
								&nbsp;|&nbsp;
								<html:button onclick="go('GameEdit.do')" property="AddNotice">Add Game</html:button>
								&nbsp;|&nbsp;
								<html:button onclick="go('Logout.do')" property="Logout">Logout</html:button>
							</td>
						</tr>
						<tr>
							<td colspan=4>
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</html:form>
	</table>
	<jsp:include page="ManagerFoot.jsp" flush="true" />
</div>
