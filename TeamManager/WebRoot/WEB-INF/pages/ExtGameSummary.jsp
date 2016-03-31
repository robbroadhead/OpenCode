<jsp:directive.page import="com.sns.teammgr.managers.Player" />
<jsp:directive.page import="com.sns.teammgr.managers.Game" />
<jsp:directive.page import="com.sns.teammgr.managers.Event" />
<jsp:directive.page import="com.sns.teammgr.managers.Team" />
<jsp:directive.page import="com.sns.teammgr.managers.TeamPeer" />
<jsp:directive.page import="com.sns.teammgr.managers.TeammsgPeer" />
<jsp:directive.page import="com.sns.teammgr.managers.GamePeer" />
<jsp:directive.page import="com.sns.teammgr.managers.EventPeer" />
<jsp:directive.page import="com.sns.teammgr.managers.Teammsg"/>
<jsp:directive.page import="com.sns.teammgr.managers.LkpresulttypePeer"/>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<jsp:include page="ManagerHead.jsp" flush="false" />
<div id="content">

	<%
		int gameid = Integer.parseInt(request.getParameter("gameid"));
		Game curGame = (Game) GamePeer.retrieveByPK(gameid);
	%>

	<table align=center width=100% border=0 cellspacing=0 cellpadding=0>
		<!-- Now the main text -->
		<tr><th>Game Summary [vs <%= curGame.getOpponent() %>]</th></tr>
		<tr><td>Score was <%= curGame.getScoreus() %> for us and <%= curGame.getScorethem() %> for them.</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>Summary: <%= curGame.getSummary() %></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>1st Period: <%= curGame.getPeriod1() %></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>2nd Period: <%= curGame.getPeriod2() %></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>3rd Period: <%= curGame.getPeriod3() %></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>Bottom Line: <%= curGame.getBottomline() %></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>First Star: <%= curGame.getStar1() %></td></tr>
		<tr><td>Second Star: <%= curGame.getStar2() %></td></tr>
		<tr><td>Third Star: <%= curGame.getStar3() %></td></tr>
		<tr><td>&nbsp;</td></tr>
			<tr>
				<td align="center">
					<html:button onclick="go('PublicTeamView.do?id=<%= curGame.getTeamid() %>')" property="Submit">Back to Team View</html:button>
				</td>
			</tr>
		<tr><td>&nbsp;</td></tr>
	</table>
	<jsp:include page="ManagerFoot.jsp" flush="true" />
</div>
