<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%
	int curId = Integer.parseInt((String) request.getSession().getAttribute("mutt.recipeid"));
	
%>
<head>
<title><bean:message key="welcome.title"/></title>
</head>
<jsp:include page="MuttHead.jsp" flush="false" />
<jsp:include page="MuttMenu.jsp" flush="false" />
<div id="content">

<table align=center width=100% cellpadding=0 cellspacing=0 border=0>
<!-- Now the main text -->
<tr><th><bean:message key="recipe.heading"/></th></TR>
<tr><td><html:form action="saveRecipe.do"><table align=center width=100% cellpadding=1 cellspacing=1 border=0>
	<tr><td width=15%>&nbsp;</td><td width=35%>&nbsp;</td><td width=15%>&nbsp;</td><td width=35%>&nbsp;</td></tr>
	<tr><td colspan=4><html:errors property="success" /><html:hidden property="recipeid"/></td></tr>
	<tr><td><b><bean:message key="recipe.title"/></b></td>
		<td><html:text size="30" readonly="true" maxlength="50" property="title"/>&nbsp;<font color="maroon"><html:errors property="title" /></font></td>
		<td><b><bean:message key="recipe.category"/></b></td>
		<td><html:text size="30" readonly="true" maxlength="50" property="category"/>&nbsp;<font color="maroon"><html:errors property="category" /></font></td></tr>
	<tr><td><b><bean:message key="recipe.submittedby"/></b></td>
		<td><html:text size="30" readonly="true" maxlength="50" property="submittedby"/>&nbsp;<font color="maroon"><html:errors property="submittedby" /></font></td>
		<td><b><bean:message key="recipe.datesubmitted"/></b></td>
		<td><html:text size="20" readonly="true" maxlength="20" property="datesubmitted"/>&nbsp;<font color="maroon"><html:errors property="datesubmitted" /></font></td></tr>
	<tr><td colspan=4><b><bean:message key="recipe.body"/></b></td></tr>
	<tr><td colspan=4><html:textarea readonly="true" rows="10" cols="80" property="recipebody"/></td></tr>
	<tr><td colspan=4>&nbsp</td></tr>
	<tr><td colspan=4><a href="/HungryMutt/rateRecipe.do?id=<%=curId%>">Rate this recipe...</a></td></tr>
	<tr><td colspan=4>&nbsp</td></tr>
	</table></html:form></td></tr>
<tr><td>&nbsp</td></tr>
<tr><td></div><jsp:include page="MuttFoot.jsp" flush="true" /></td></tr></table>