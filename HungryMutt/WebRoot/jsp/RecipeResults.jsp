<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<% java.util.Iterator searchresults; %>
<% searchresults = ((java.util.List) request.getSession().getAttribute("resultList")).iterator(); %>
<% int resultcount = ((java.util.List) request.getSession().getAttribute("resultList")).size(); %>
<% String resultString = (String) request.getSession().getAttribute("resultString"); %>
<% int maxRecord; %>

<head>
<title><bean:message key="welcome.title"/></title>
</head>
<jsp:include page="MuttHead.jsp" flush="false" />
<jsp:include page="MuttMenu.jsp" flush="false" />
<div id="content">

<table align=center width=650>
<!-- Now the main text -->
	<tr><th><bean:message key="recipe.heading.results"/></th></TR>
	<tr align="left" width=90%><td><a href="/HungryMutt/advancedSearch.do"><bean:message key="general.searchagain"/></a></td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr><td><%=resultString %></td></tr>
	<tr><td>
		<pg:pager maxIndexPages="15" items="<%= resultcount %>" maxPageItems="30" export="offset,currentPageNumber=pageNumber" index="center" scope="request">
		<pg:param name="pg"/>
		<pg:param name="q"/>
		<input type="hidden" name="pager.offset" value="<%= offset %>">
		<jsp:include page="/jsp/altavista.jsp" />
		<hr>
		<table>
			<% if (offset.intValue()  + 30 > resultcount ) { %>
			<% maxRecord = resultcount; } else { maxRecord = offset.intValue() + 30; } %>
			<TR><TD colspan=4>Displaying items <%= offset.intValue() + 1 %> to <%= maxRecord %> of <%= resultcount %></TD></TR>
			<TR><TD colspan=4>&nbsp;</TD></TR>
			<tr><th width=50%>Title</th><th width=10%>Avg. Rating</th><th width=20%>Category</th><th width=20%>Action</th></tr>
			<% while (searchresults.hasNext()) { %>
				<% String tempStr = (String) searchresults.next(); %>
				<pg:item><tr><%= tempStr %></tr></pg:item>
			<% } %>
		</table>
		</pg:pager>
	</td></tr>
	<tr><td>&nbsp;</td></tr>

<tr><td><jsp:include page="MuttFoot.jsp" flush="true" /></td></tr></table>
</div>