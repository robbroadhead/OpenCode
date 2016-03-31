<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<div id="sidemenu">
<TABLE id="SideMenu" valign="top" align=center border=1 cellspacing=0 borderwidth=0 bordercolor=#BEE0DF>
<!-- The menu -->
<TR><TD><a href="/HungryMutt/Welcome.do"><bean:message key="menu.general.home"/></a></TD></TR>
<TR><TD><a href="/HungryMutt/advancedSearch.do"><bean:message key="menu.general.advancedsearch"/></a></TD></TR>
<TR><TD><a href="/HungryMutt/submitRecipe.do"><bean:message key="menu.general.submitrecipe"/></a></TD></TR>
</TABLE>
</div>