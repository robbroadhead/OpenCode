<jsp:directive.page import="com.sns.scout.managers.TaskPeer" />
<jsp:directive.page import="com.sns.scout.managers.Task" />
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%
	int maxRecord;
	java.util.Iterator searchresults;
	java.util.List results = null;

	// Get the request parm if it exists, otherwise we just wanted a new page of results.
	results = (java.util.List) request.getSession().getAttribute(
			"curlist");
	if (results == null) {
		results = TaskPeer.getOpenTasks();
		request.getSession().setAttribute("curlist", results);
	}
	searchresults = results.iterator();
	int resultcount = results.size();
%>

<jsp:include page="ScoutHead.jsp" flush="true" />
<div id="content">
<div id="contentreport">
		<!-- Now the main text -->
<h2 align="center"><bean:message key="task.list.heading"/></h2>
<pg:pager maxIndexPages="20" items="<%=resultcount%>"
	maxPageItems="25" export="offset,currentPageNumber=pageNumber"
	index="center" scope="request">
<pg:param name="pg" />
<pg:param name="q" />
<input type="hidden" name="pager.offset" value="<%=offset%>">
<jsp:include page="/pages/altavista.jsp" />
<p align="center"><table border=1 bordercolor="GRAY" cellpadding=0 cellspacing=0 rules="none" width=90% id="report">
					<tr>
						<th width=15%>
							Action
						</th>
						<th width=85%>
							Name
						</th>
					</tr>
					<%
						if (offset.intValue() + 25 > resultcount) {
										maxRecord = resultcount;
									} else {
										maxRecord = offset.intValue() + 25;
									}
									boolean odd = true;
									while (searchresults.hasNext()) {
										Task curTask = (Task) searchresults.next();
										if (odd) {
					%>
					<pg:item>
						<tr class="ODD">
							<td>
								<a
									href="/Scout/TaskEdit.do?id=<%=curTask.getTaskid()%>">Edit</a>
							</td>
							<td><%=curTask.getSummary()%></td>
						</tr>
					</pg:item>
					<%
						} else {
					%>
					<pg:item>
						<tr class="EVEN">
							<td>
								<a
									href="/Scout/TaskEdit.do?id=<%=curTask.getTaskid()%>">Edit</a>
							</td>
							<td><%=curTask.getSummary()%></td>
						</tr>
					</pg:item>
					<%
						}
										odd = !odd;
									}
					%>
</table></p>
<% if (resultcount > 0) { %>
	<p align="center">Displaying items <%=offset.intValue() + 1%> to <%=maxRecord%> of <%=resultcount%></p>
<% } else { %>
	<p align="center">No Records Found</p>
<% } %><br/>
		</pg:pager>
	<jsp:include page="ScoutFoot.jsp" flush="true" />
</div></div>
