<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<jsp:include page="ScoutHead.jsp" flush="true" />

<logic:notPresent name="org.apache.struts.action.MESSAGE"
	scope="application">

	<font color="red"> ERROR: Application resources not loaded --
		check servlet container logs for error messages. </font>

</logic:notPresent>

<div id="content">
	<table align=center>
		<!-- Now the main text -->
		<tr>
			<td colspan=2>
				<html:form action="Login.do">
					<table id=login>
						<tr>
							<td>
								<B>Username:
								</B>
							</td>
							<td>
								<html:text size="15" maxlength="32" property="logon" />
							</td>
							<td>
								<B>Password:
								</B>
							</td>
							<td>
								<html:password size="15" maxlength="32" property="password" />
							</td>
							<td>
								<html:submit>
									Login
								</html:submit>
							</td>
						</tr>
						<tr>
							<td colspan=2>
								<html:messages id="username" message="true">
								<font color="red">
									<bean:write name="username" />
									</font>
							    </html:messages>
							</td>
							<td colspan=3>
								<html:messages id="password" message="true">
								<font color="red">
									<bean:write name="password" />
									</font>
							    </html:messages>
							</td>
						</tr>
					</table>
				</html:form>
			</td>
		</tr>
		<tr>
			<td valign=top>
				<H2>
						<div align="center">
							Task Tracker and Productivity Enhancer - Scout
						</div>
				</H2>
			</td>
		</TR>
		<tr>
			<td colspan=2>
				<p>This application is aimed at providing an easy way to track the various tasks you need
				to complete in order to make a project successful. There are options for including task costs
				both estimated and actual as well as being able to assign groups and users to a project and/or
				task.
				</p>
				<br />
				<p>The goal of this is not be all things to all people, but instead to find a happy medium where
				the right amount of information is tracked and can be reported on without too much overhead
				for the users of the system. This is a living project so there will be more features added over time.
				If you have any ideas/comments/suggestions feel free to contact us and we will incorporate those
				that fit our plan and scope into a future release. We are also open to creating a customized version
				for your group or business to address your specific requirements.
				</p>
			</td>
		</tr>
		<tr>
			<td colspan=2>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan=2>
				&nbsp;
			</td>
		</tr>
	</table>
	<jsp:include page="ScoutFoot.jsp" flush="true" />
</div>
