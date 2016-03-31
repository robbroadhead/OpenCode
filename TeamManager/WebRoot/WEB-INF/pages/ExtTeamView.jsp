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
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<jsp:include page="ManagerHead.jsp" flush="false" />
<div id="content">

	<%
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
							<td width=20%>&nbsp;
								
							</td>
							<td width=30%>&nbsp;
								
							</td>
							<td width=20%>&nbsp;
								
							</td>
							<td width=30%>&nbsp;
								
							</td>
						</tr>
						<tr>
							<td colspan=2>
								<img src='<bean:write name="team" property="piclogo" />' />
							</td>
							<td colspan=2>
								<b><bean:write name="team" property="season" /> </b>
								<br />
								<b>Primary Color: <bean:write name="team"
										property="primarycolor" /> </b>
								<br />
								<b>Secondary Color: <bean:write name="team"
										property="secondarycolor" /> </b>
							</td>
						</tr>
						<tr>
							<td colspan=4>&nbsp;
								
							</td>
						</tr>
						<tr>
							<td>&nbsp;
								
							</td>
							<td>
								Coach:
								<bean:write name="team" property="coach" />
							</td>
							<td colspan=2>
								Assistant:
								<bean:write name="team" property="acoach" />
							</td>
						</tr>
						<tr>
							<td colspan=4>&nbsp;
								
							</td>
						</tr>
						<tr>
							<td colspan=4>Wins: <%=TeamPeer.getStat(curTeam.getTeamid(),1) %>&nbsp;|&nbsp;Losses: <%=TeamPeer.getStat(curTeam.getTeamid(),2) %>
							&nbsp;|&nbsp;Ties: <%=TeamPeer.getStat(curTeam.getTeamid(),3) %>&nbsp;|&nbsp;Goals For: 
							<%=TeamPeer.getGoalsFor(curTeam.getTeamid()) %>&nbsp;|&nbsp;Goals Against: <%=TeamPeer.getGoalsAgainst(curTeam.getTeamid()) %>							
							</td>
						</tr>
						<tr>
							<td colspan=4>&nbsp;
								
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
														<th width=7%>
															#
														</th>
														<th width=7%>
															G
														</th>
														<th width=7%>
															A
														</th>
														<th width=7%>
															Tot
														</th>
														<th width=7%>
															Pos.
														</th>
														<th width=65%>
															Name (nickname)
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
																		+ roster[i].getGoals()
																		+ "</td><td>"
																		+ roster[i].getAssists()
																		+ "</td><td>"
																		+ (roster[i].getAssists() + roster[i].getGoals())
																		+ "</td><td>"
																		+ LkppositionPeer.retrieveByPK(
																				roster[i].getPosition()).getName()
																		+ "</td><td>" + roster[i].getFirstname() + " "
																		+ roster[i].getLastname() + " ("
																		+ roster[i].getNickname() + ")" + "</td>";
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
							<td colspan=4 align="center">
								<H3>
									Goalies
								</H3>
							</td>
						</tr>
						<tr>
							<td colspan=4>
								<div id="contentreport">
									<table border=1 bordercolor="GRAY" cellpadding=0 cellspacing=0
										rules="none" align="center" width=700>
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
															+ goalies[i].getFirstname() + " "
															+ goalies[i].getLastname() + " ("
															+ goalies[i].getNickname() + ")" + "</td>";
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
				</td>
			</tr>
			<tr>
				<td colspan=4>&nbsp;
					
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
									<table border=1 bordercolor="GRAY" cellpadding=0 cellspacing=0
										rules="none" align="center" width=700>
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
															+ "<a href='PublicGameView.do?gameid="
															+ games[i].getGameid() + "'>[View]</a></td><td>"
															+ games[i].getScoreus() + "</td><td>"
															+ games[i].getScorethem() + "</td><td>"
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
				<td colspan=4>&nbsp;
					
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
									<table border=1 bordercolor="GRAY" cellpadding=0 cellspacing=0
										rules="none" align="center" width=700>
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
															+ events[i].getEnddate() + "</td><td>"
															+ events[i].getTitle() + "</td>";
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
				<td colspan=4>&nbsp;
					
				</td>
			</tr>
			<tr>
				<td colspan=4 align="center">
					<html:button onclick="go('Logout.do')" property="Logout">Back to Home Page</html:button>
				</td>
			</tr>
			<tr>
				<td colspan=4>&nbsp;
					
				</td>
			</tr>
		</html:form>
	</table>
	<jsp:include page="ManagerFoot.jsp" flush="true" />
	</div>
