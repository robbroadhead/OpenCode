<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<!-- Now the main text -->
<div id="sidemenu">
<%
    if (session.getAttribute("scout.User") != null) {
    	
 %>
	<table>
		<tr><td><%=session.getAttribute("scout.User") %> is logged in.</td></tr>
		<tr><td>&nbsp;</td></tr>		
		<tr><th>Projects</th></tr>
		<tr><td><a href="ProjectFind.do">View Projects</a></td></tr>
		<tr><td><a href="ProjectEdit.do">Create Project</a></td></tr>
		<tr><td><a href="ProjectStats.do">Statistics</a></td></tr>
		<tr><td>&nbsp;</td></tr>		
		<tr><th>Tasks</th></tr>
		<tr><td><a href="TaskList.do">My Tasks</a></td></tr>
		<tr><td><a href="TaskEdit.do">Create Task</a></td></tr>
		<tr><td><a href="TaskFind.do">Open Tasks</a></td></tr>
		<tr><td>&nbsp;</td></tr>		
		<tr><th>Admin</th></tr>
		<tr><td><a href="UserEdit.do">Add User</a></td></tr>
		<tr><td><a href="GroupEdit.do">Add Group</a></td></tr>
		<tr><td><a href="UserList.do">List Users</a></td></tr>
		<tr><td><a href="GroupList.do">List Groups</a></td></tr>
		<tr><td>&nbsp;</td></tr>		
		<tr><td><a href="Logout.do">Logout</a></td></tr>
	</table>
<%
	} else {
%>
<br/><p>
Login to manage your projects and tasks.</p>
<%
	}
%>
</div>