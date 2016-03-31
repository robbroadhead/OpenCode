<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<jsp:include page="ScoutHead.jsp" flush="true" />

<!--  TODO -->
<!-- 
3. Project Stats page. (List related tasks)
6. Tasks find/search page (by status, project, etc.)
 -->
 
<div id="content">
	<table align=center>
		<!-- Now the main text -->

		<tr>
			<td valign=top colspan=2>
				<H2>
						<div align="center">
							Home Page
						</div>
				</H2>
			</td>
		</TR>
		<tr><td colspan=2><p>Use the menu on the left to get your projects started or to manage your existing projects.</p></td>
		<tr><td colspan=2>&nbsp;</td>
		</tr>
	</table>
	<jsp:include page="ScoutFoot.jsp" flush="true" />
</div>
