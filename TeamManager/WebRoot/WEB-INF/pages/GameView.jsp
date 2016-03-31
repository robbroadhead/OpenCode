<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<jsp:directive.page import="com.sns.teammgr.managers.Game" />
<jsp:directive.page import="com.sns.teammgr.managers.Playerpoints" />
<jsp:directive.page import="com.sns.teammgr.managers.GamePeer" />
<jsp:directive.page import="com.sns.teammgr.managers.Gamecomments" />
<jsp:directive.page import="com.sns.teammgr.managers.GamecommentsPeer" />
<jsp:directive.page import="com.sns.teammgr.managers.LkpresulttypePeer"/>

<%
	Game curGame = (Game) request.getSession().getAttribute("game");
	java.util.List scoringSummary = GamePeer.getSummary(curGame.getGameid());
	
	// Get the list of Comments
	Gamecomments[] msgs = GamecommentsPeer.getMessages(curGame.getGameid());
	
	String result = LkpresulttypePeer.retrieveByPK(curGame.getResult()).getDescription();
%>
		
<jsp:include page="ManagerHead.jsp" flush="false" />
<div id="content">

	<table align=center width=100% border=0 cellspacing=0 cellpadding=0>
		<!-- Now the main text -->
		<tr>
			<th>
				<bean:message key="game.heading" />
			</th>
		</TR>
		<tr>
			<td>
				<html:form action="Game.do">
					<table width=100%>
						<tr><td width=20%>&nbsp;</td>
							<td width=30%>&nbsp;</td>
							<td width=20%>&nbsp;</td>
							<td width=30%>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.opponent" />
								</b>
								<html:hidden property="gameid" />
								<html:hidden property="teamid" />
							</td>
							<td>
								<bean:write name="game" property="opponent" />
							</td>
							<td>
								<b><bean:message key="game.result" />
								</b>
							</td>
							<td>
								<%=result %>
							</td>
						</tr>
						<tr>
							<td colspan=4><html:messages id="gamename" message="true">
							</html:messages>
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.scoreus" />
								</b>
							</td>
							<td>
								<bean:write name="game" property="scoreus" />
							</td>
							<td>
								<b><bean:message key="game.scorethem" />
								</b>
							</td>
							<td>
								<bean:write name="game" property="scorethem" />
							</td>
						</tr>
						<tr>
							<td>
								<b>Scoring
								</b>
							</td>
							<td colspan=3><table><tr><td>
													<%
														String tempStr = "";
														if (scoringSummary.size() < 1) {
															tempStr = "<tr><td>No scoring information has been entered.</td></tr>";
														}

														for (int i = 0; i < scoringSummary.size() - 1; i++) {
															tempStr = scoringSummary.get(i) + ", ";
															%><%=tempStr%><%
														}
														tempStr = (String) scoringSummary.get(scoringSummary.size() - 1);
														%><%=tempStr%><%
													%>
								</td></tr></table>
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.summary" />
								</b>
							</td>
							<td colspan=3>
								<bean:write name="game" property="summary" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.period1" />
								</b>
							</td>
							<td colspan=3>
								<bean:write name="game" property="period1" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.period2" />
								</b>
							</td>
							<td colspan=3>
								<bean:write name="game" property="period2" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.period3" />
								</b>
							</td>
							<td colspan=3>
								<bean:write name="game" property="period3" />
							</td>
						</tr>
<% if (curGame.getOvertime().trim().length() > 0) { %>
						<tr>
							<td>
								<b><bean:message key="game.overtime" />
								</b>
							</td>
							<td colspan=3>
								<bean:write name="game" property="overtime" />
							</td>
						</tr>
<% } %>
						<tr>
							<td>
								<b><bean:message key="game.bottomline" />
								</b>
							</td>
							<td colspan=3>
								<bean:write name="game" property="bottomline" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.star1" />
								</b>
							</td>
							<td colspan=3>
								<bean:write name="game" property="star1" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.star2" />
								</b>
							</td>
							<td colspan=3>
								<bean:write name="game" property="star2" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.star3" />
								</b>
							</td>
							<td colspan=3>
								<bean:write name="game" property="star3" />
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
											<td colspan=4>
												<table border=1 bordercolor="GRAY" cellpadding=0
													cellspacing=0 rules="none" align="center" width=700>
													<tr>
														<th>
															Comments
														</th>
													</tr>
													<%
														tempStr = "";
														boolean odd = true;

														if (msgs.length < 1) {
 															tempStr = "<td>No comments have been posted yet.</td>";
													%>
													<tr class="ODD">
														<%=tempStr%>
													</tr>
													<%
													  }

														for (int i = 0; i < msgs.length; i++) {
															tempStr = "<td>" + msgs[i].getMsg() + "<br/></td>";
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
							<td colspan=4><html:button onclick="go('MemberTeamView.do')" property="AddEvent">Back to Team View</html:button> | <html:button onclick="go('CommentsAdd.do')" property="AddEvent">Post Comment</html:button>&nbsp;
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
