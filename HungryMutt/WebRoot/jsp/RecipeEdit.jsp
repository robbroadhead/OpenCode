<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

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
		<td colspan=3><html:text size="50" maxlength="50" property="title"/>&nbsp;<font color="maroon"><html:errors property="title" /></font></td></tr>
	<tr><td><b><bean:message key="recipe.category"/></b></td>
		<td colspan=3><html:text size="50" maxlength="50" property="category"/>&nbsp;<font color="maroon"><html:errors property="category" /></font></td></tr>
	<tr><td><b><bean:message key="recipe.submittedby"/></b></td>
		<td colspan=3><html:text size="30" maxlength="50" property="submittedby"/>&nbsp;<font color="maroon"><html:errors property="submittedby" /></font></td>
	<tr><td colspan=4><b><bean:message key="recipe.body"/></b></td></tr>
	<tr><td colspan=4><html:textarea rows="10" cols="80" property="recipebody"/></td></tr>
	<tr><td colspan=4>&nbsp</td></tr>
	<tr><td>&nbsp;</td><td align=right><button type="submit" name="submit" value="submit"><bean:message key="general.save"/></button><html:reset><bean:message key="general.reset"/></html:reset>
		</html:form>
		</td><td colspan=2>&nbsp;</td></tr>
	<tr><td colspan=4>&nbsp</td></tr>
	</table></td></tr>
<tr><td>&nbsp</td></tr>
<tr><td></div><jsp:include page="MuttFoot.jsp" flush="true" /></td></tr></table>