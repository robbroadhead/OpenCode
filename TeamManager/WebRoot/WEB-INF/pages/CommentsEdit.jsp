<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<jsp:include page="ManagerHead.jsp" flush="false" />
<div id="content">

	<table align=center width=100% border=0 cellspacing=0 cellpadding=0>
		<!-- Now the main text -->
		<tr>
			<th>Add a Game Comment
			</th>
		</TR>
		<tr>
			<td>
				<html:form action="Comments.do">
					<html:messages property="success" id="success"/>
					<table width=100%>
						<tr><td align=center>
								<html:hidden property="commentid" />
								<html:hidden property="gameid" />
								<html:textarea cols="50" rows="5" property="message" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;	
							</td>
						</tr>
						<tr>
							<td align=center>
								<html:submit>
									<bean:message key="general.save" />
								</html:submit>
								<html:reset>
									<bean:message key="general.reset" />
								</html:reset>
							</td>
						</tr>
						<tr>
							<td>&nbsp;								
							</td>
						</tr>
					</table>
				</html:form>
			</td>
		</tr>
	</table>
	<jsp:include page="ManagerFoot.jsp" flush="true" />
</div>
