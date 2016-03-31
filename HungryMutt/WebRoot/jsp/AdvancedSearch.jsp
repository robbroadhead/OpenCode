<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<head>
<title><bean:message key="welcome.title"/></title>
</head>
<jsp:include page="MuttHead.jsp" flush="false" />
<jsp:include page="MuttMenu.jsp" flush="false" />
<div id="content">

<html:form action="recipeAdvancedSearch.do">
<table align=left width=645 cellpadding=0 cellspacing=0 border=0>
<!-- Now the main text -->
<tr><th colspan=3><bean:message key="recipe.heading.find"/></th></TR>
<tr><td width=15%>&nbsp</td><td width=55%>&nbsp<html:errors property="success" /><html:errors property="failure" /></td><td width=30%>&nbsp</td></tr>
<tr><td><b><bean:message key="recipe.keyword"/></b></td>
	<td><html:text size="50" maxlength="50" property="keyword"/></td>
	<td>&nbsp<html:errors property="keyword" /></td></tr>
<tr><td><b><bean:message key="recipe.category"/></b></td>
	<td><html:text size="50" maxlength="50" property="category"/></td>
	<td>&nbsp<html:errors property="category" /></td></tr>
<tr><td><b><bean:message key="recipe.rating"/></b></td>
	<td><html:text size="10" maxlength="10" property="rating"/></td>
	<td>&nbsp<html:errors property="rating" /></td></tr>
<tr><td colspan=3>&nbsp</td></tr>
<tr><td colspan=3 align=center><button type="submit" name="submit" value="submit"><bean:message key="general.search"/></button><html:reset><bean:message key="general.reset"/></html:reset></td></tr>
<tr><td colspan=3>&nbsp</td></tr>
</html:form>
<tr><td colspan=3>&nbsp</td></tr>
<tr><td colspan=3></div><jsp:include page="MuttFoot.jsp" flush="true" /></td></tr>
</table>

