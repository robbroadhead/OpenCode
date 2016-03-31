<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

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
								<b><bean:message key="game.opponent" />
								</b>
								<html:hidden property="gameid" />
								<html:hidden property="teamid" />
							</td>
							<td>
								<html:textarea cols="50" rows="2" property="opponent" />
							</td>
							<td>
								<b><bean:message key="game.result" />
								</b>
								<html:select property="result">
									<html:optionsCollection property="resultlist" label="description" value="id"/>
								</html:select>
							</td>
							<td>
								<b>Goalie: 
								</b>
								<html:select property="goalies">
									<html:optionsCollection property="glist" label="nickname" value="playerid"/>
								</html:select>
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
								<html:text size="10" maxlength="10" property="scoreus" />
							</td>
							<td>
								<b><bean:message key="game.scorethem" />
								</b>
							</td>
							<td>
								<html:text size="10" maxlength="10" property="scorethem" />
							</td>
						</tr>
						<tr>
							<td colspan=4>
								<b>Scoring (comma separate each goal, start with goal and then dashes for assists eg. 1-2-3 for a goal
								from player number 1 that is assisted by player 2 and player 3.)
								</b>
							</td>
						</tr>
						<tr>
							<td colspan=4>
								<html:textarea cols="80" rows="3" property="scoring" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.summary" />
								</b>
							</td>
							<td colspan=3>
								<html:textarea cols="60" rows="2" property="summary" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.period1" />
								</b>
							</td>
							<td colspan=3>
								<html:textarea cols="50" rows="5" property="period1" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.period2" />
								</b>
							</td>
							<td colspan=3>
								<html:textarea cols="50" rows="5" property="period2" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.period3" />
								</b>
							</td>
							<td colspan=3>
								<html:textarea cols="50" rows="5" property="period3" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.overtime" />
								</b>
							</td>
							<td colspan=3>
								<html:textarea cols="50" rows="5" property="overtime" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.bottomline" />
								</b>
							</td>
							<td colspan=3>
								<html:textarea cols="50" rows="5" property="bottomline" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.star1" />
								</b>
							</td>
							<td colspan=3>
								<html:textarea cols="50" rows="5" property="star1" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.star2" />
								</b>
							</td>
							<td colspan=3>
								<html:textarea cols="50" rows="5" property="star2" />
							</td>
						</tr>
						<tr>
							<td>
								<b><bean:message key="game.star3" />
								</b>
							</td>
							<td colspan=3>
								<html:textarea cols="50" rows="5" property="star3" />
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
