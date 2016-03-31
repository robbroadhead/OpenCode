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
<tr><th><bean:message key="rate.heading"/></th></TR>
<tr><td><html:form action="saveRating.do"><table align=center width=100% cellpadding=1 cellspacing=1 border=0>
	<tr><td width=15%>&nbsp;</td><td width=35%>&nbsp;</td><td width=15%>&nbsp;</td><td width=35%>&nbsp;</td></tr>
	<tr><td colspan=4><html:errors property="success" /></td></tr>
	<tr><td><b><bean:message key="rate.rating"/></b><html:hidden property="ratingid"/><html:hidden property="recipeid"/></td>
		<td colspan=3><html:text size="10" maxlength="10" property="rating"/>&nbsp;<font color="maroon"><html:errors property="rating" /></font></td></tr>
	<tr><td colspan=4>&nbsp</td></tr>
	<tr><td>&nbsp;</td><td align=right><button type="submit" name="submit" value="submit"><bean:message key="general.save"/></button><html:reset><bean:message key="general.reset"/></html:reset>
		</html:form>
		</td><td colspan=2>&nbsp;</td></tr>
	<tr><td colspan=4>&nbsp</td></tr>
	</table></td></tr>
<tr><td>&nbsp</td></tr>
<tr><td></div><jsp:include page="MuttFoot.jsp" flush="true" /></td></tr></table>