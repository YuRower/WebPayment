<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Actions" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr><td class="content center">
			<h3><fmt:message key="admin.actions.header"/></h3>
			<p>
			<fmt:message key="admin.actions.lu"/>
			</p>
			<form action="controller" method="POST">
			<input type="hidden" name="command" value="adminAction" />
			<input type="hidden" name="action" value="lockUser" />
			<input type = "number" name="userId" placeholder="User ID"/>
			<input type="submit" value="<fmt:message key="admin.actions.lu"/>" /></form>
			<p>
			<fmt:message key="admin.actions.uu"/>
			</p>
			<form action="controller" method="POST">
			<input type="hidden" name="command" value="adminAction" />
			<input type="hidden" name="action" value="unlockUser" />
			<input type ="number" name="userId" placeholder="User ID"/>
			<input type="submit" value="<fmt:message key="admin.actions.uu"/>" /></form>
			<p>
			<fmt:message key="admin.actions.la"/>
			</p>
			<form action="controller" method="POST">
			<input type="hidden" name="command" value="adminAction" />
			<input type="hidden" name="action" value="lockAccount" />
			<input type = "number" name="accountId" placeholder="Account ID"/>
			<input type="submit" value="<fmt:message key="admin.actions.la"/>" /></form>
			<p>
			<fmt:message key="admin.actions.ua"/>
			</p>
			<form action="controller" method="POST">
			<input type="hidden" name="command" value="adminAction" />
			<input type="hidden" name="action" value="unlockAccount" />
			<input type = "number" name="accountId" placeholder="Account ID"/>
			<input type="submit" value="<fmt:message key="admin.actions.ua"/>" /></form>
			
		
			
		</td></tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>