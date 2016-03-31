<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<head>
	<title><bean:message key="welcome.title"/></title>
</head>

<jsp:include page="MuttHead.jsp" flush="false" />
<jsp:include page="MuttMenu.jsp" flush="false" />

<logic:notPresent name="org.apache.struts.action.MESSAGE" scope="application">
	
	<font color="red">
		ERROR:  Application resources not loaded -- check servlet container
		logs for error messages.
	</font>
	
</logic:notPresent>

<%
	int curId = Integer.parseInt((String) request.getSession().getAttribute("mutt.recipeid"));	
%>

<div id=content>
<table align=center>
	<!-- Now the main text -->
	<tr><td colspan=2><hr></td></tr>
	<tr><td valign=top><H2><p align="left"><font color="GREEN">
		<div align="center"><bean:message key="welcome.heading"/></td>
		</div></font></H2></TR>
	<tr><td colspan=2>&nbsp</td></tr>
	<tr><td colspan=2>A sample from our database...</td></tr>
	<tr><td colspan=2>&nbsp</td></tr>
	<tr><td colspan=2><html:form action="saveRecipe.do">
		<table align=center width=100% cellpadding=1 cellspacing=1 border=0>
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
		</table></html:form>
	</td></tr>
	<tr><td colspan=2>&nbsp</td></tr>
	<tr><td colspan=2><html:form action="recipeSearch.do">
		<table align=left width=645 cellpadding=0 cellspacing=0 border=0>
		<tr><th colspan=3><bean:message key="recipe.heading.find"/></th></TR>
		<tr><td width=15%>&nbsp</td><td width=55%>&nbsp<html:errors property="success" /><html:errors property="failure" /></td><td width=30%>&nbsp</td></tr>
		<tr><td><b><bean:message key="recipe.keyword"/><html:hidden property="recipeid"/></b></td>
			<td><html:text size="50" maxlength="50" property="title"/></td>
			<td>&nbsp<html:errors property="title" /></td></tr>
		<tr><td colspan=3>&nbsp</td></tr>
		<tr><td colspan=3 align=center><button type="submit" name="submit" value="submit"><bean:message key="general.search"/></button><html:reset><bean:message key="general.reset"/></html:reset></td></tr>
		</html:form></td></tr>
	<tr><td colspan=2>&nbsp</td></tr>
	<tr><td colspan=2>&nbsp</td></tr>
	<tr><td colspan=2><jsp:include page="MuttFoot.jsp" flush="true" /></td></tr></table>
</div>